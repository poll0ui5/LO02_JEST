package fr.utt.lo02.jest.controller;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.strategy.*;
import fr.utt.lo02.jest.variante.*;
import fr.utt.lo02.jest.extension.*;
import fr.utt.lo02.jest.sauvegarde.*;
import fr.utt.lo02.jest.view.Terminal;
import fr.utt.lo02.jest.visitor.VisitorScore;

/**
 * Contrôleur principal du jeu Jest (pattern MVC).
 * Gère l'initialisation, les tours de jeu et le calcul des scores.
 * @author Projet LO02
 */
public class Partie {
    private ArrayList<Joueur> joueurs = new ArrayList<>();
    private JeuCartes pioche = new JeuCartes();
    private ArrayList<Carte> trophees = new ArrayList<>();
    private Terminal view = new Terminal();
    private boolean partieTerminee = false;
    private int numeroManche = 1;
    private Variante variante;
    private Extension extension;

    /** Lance une nouvelle partie. */
    public void demarrer() {
        initialiserJeu();
        while (!pioche.estVide() && !partieTerminee) jouerUnTour();
        conclurePartie();
    }

    /** Configuration : variante, extension, joueurs. */
    public void initialiserJeu() {
        view.afficherMessage("\n=== CONFIGURATION ===");
        view.afficherMessage("Variante : 1.Classique 2.Sans Trophée 3.Double Mise");
        int v = view.lireEntier("Choix", 1, 3);
        switch(v) {
            case 2: variante = new VarianteSansTrophee(); break;
            case 3: variante = new VarianteDoubleMise(); break;
            default: variante = new VarianteClassique();
        }
        view.afficherMessage("Variante : " + variante.getNom());

        view.afficherMessage("Extension : 1.Non 2.Cartes Spéciales");
        if (view.lireEntier("Choix", 1, 2) == 2) {
            extension = new ExtensionCartesSpeciales();
            extension.setActive(true);
            for (Carte c : extension.getCartesExtension()) pioche.getTasCartes().add(c);
        }

        int nbJoueurs = view.demanderNombreJoueurs();
        int nbHumains = view.lireEntier("Joueurs humains ?", 1, nbJoueurs);
        
        for (int i = 0; i < nbHumains; i++)
            joueurs.add(new JoueurHumain(view.demanderNomJoueur(i + 1)));
        for (int i = 0; i < nbJoueurs - nbHumains; i++)
            joueurs.add(new JoueurVirtuel("Bot " + (i+1), i%2==0 ? new StrategieOffensive() : new StrategieDefensive()));

        pioche.melanger();
        int nbTrophees = variante.getNombreTrophees(nbJoueurs);
        for (int i = 0; i < nbTrophees && !pioche.estVide(); i++) {
            Carte t = pioche.distribuerUneCarte();
            t.show();
            trophees.add(t);
        }
        if (nbTrophees > 0) view.afficherTrophees(trophees);
    }

    /** Un tour : distribution, offres, prises. */
    private void jouerUnTour() {
        view.afficherMessage("\n--- MANCHE " + numeroManche + " ---");
        for (Joueur j : joueurs) {
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
        }
        for (Joueur j : joueurs) { j.faireOffre(); view.afficherOffre(j); }
        
        ArrayList<Joueur> joues = new ArrayList<>();
        Joueur actuel = trouverMeilleureOffre(joueurs);
        
        while (joues.size() < joueurs.size()) {
            view.afficherMessage("Tour de " + actuel.getNom());
            Joueur cible = actuel.choisirAdversaire(joueurs);
            Carte carte = actuel.prendreCarteDansOffre(cible);
            actuel.ajouterAuJest(carte);
            joues.add(actuel);
            view.afficherMessage(actuel.getNom() + " prend " + carte + " chez " + cible.getNom());
            
            if (!joues.contains(cible)) actuel = cible;
            else {
                ArrayList<Joueur> restants = new ArrayList<>();
                for (Joueur j : joueurs) if (!joues.contains(j)) restants.add(j);
                if (!restants.isEmpty()) actuel = trouverMeilleureOffre(restants);
            }
        }
        numeroManche++;
        if (pioche.estVide()) partieTerminee = true;
    }

    private Joueur trouverMeilleureOffre(List<Joueur> candidats) {
        Joueur meilleur = candidats.get(0);
        for (Joueur j : candidats) {
            Carte c1 = j.getCarteVisibleDeLOffre(), c2 = meilleur.getCarteVisibleDeLOffre();
            if (c1 != null && (c2 == null || c1.estSuperieureA(c2))) meilleur = j;
        }
        return meilleur;
    }

    /** Fin de partie : trophées, scores, gagnant. */
    private void conclurePartie() {
        view.afficherMessage("\n--- FIN ---");
        for (Joueur j : joueurs) j.recupererDerniereCarteDeLOffre();
        attribuerTrophees();
        VisitorScore calc = new VisitorScore();
        for (Joueur j : joueurs) j.accept(calc);
        variante.appliquerReglesFinales(joueurs);
        for (Joueur j : joueurs) view.afficherScore(j);
        Joueur gagnant = joueurs.get(0);
        for (Joueur j : joueurs) if (j.getScore() > gagnant.getScore()) gagnant = j;
        view.afficherMessage("\n*** Gagnant : " + gagnant.getNom() + " (" + gagnant.getScore() + " pts) ***");
    }

    private void attribuerTrophees() {
        for (Carte trophee : trophees) {
            Joueur gagnant = null;
            int max = -1;
            for (Joueur j : joueurs) {
                int count = 0;
                if (trophee.estJoker()) {
                    for (Carte c : j.getJest()) if (c.getCouleur() == Couleur.COEUR) count++;
                } else {
                    for (Carte c : j.getJest()) if (c.getValeur() == trophee.getValeur()) count++;
                }
                if (count > max) { max = count; gagnant = j; }
            }
            if (gagnant != null) {
                gagnant.ajouterAuJest(trophee);
                view.afficherMessage("Trophée " + trophee + " -> " + gagnant.getNom());
            }
        }
    }

    /** Sauvegarde l'état de la partie. */
    public boolean sauvegarderPartie(String nom) {
        EtatPartie etat = new EtatPartie();
        etat.setJoueurs(joueurs);
        etat.setPioche(pioche.getTasCartes());
        etat.setTrophees(trophees);
        etat.setNumeroManche(numeroManche);
        etat.setPartieTerminee(partieTerminee);
        if (variante != null) etat.setNomVariante(variante.getNom());
        if (extension != null && extension.estActive()) etat.setNomExtension(extension.getNom());
        return GestionnaireSauvegarde.sauvegarder(etat, nom);
    }

    /** Charge une partie sauvegardée. */
    public boolean chargerPartie(String nom) {
        Object obj = GestionnaireSauvegarde.charger(nom);
        if (!(obj instanceof EtatPartie)) return false;
        EtatPartie etat = (EtatPartie) obj;
        joueurs = etat.getJoueurs();
        pioche.getTasCartes().clear();
        pioche.getTasCartes().addAll(etat.getPioche());
        trophees = etat.getTrophees();
        numeroManche = etat.getNumeroManche();
        partieTerminee = etat.isPartieTerminee();
        switch (etat.getNomVariante()) {
            case "Sans Trophée": variante = new VarianteSansTrophee(); break;
            case "Double Mise": variante = new VarianteDoubleMise(); break;
            default: variante = new VarianteClassique();
        }
        if (etat.getNomExtension() != null) { extension = new ExtensionCartesSpeciales(); extension.setActive(true); }
        return true;
    }

    public static void main(String[] args) {
        Partie partie = new Partie();
        Terminal view = new Terminal();
        String[] saves = GestionnaireSauvegarde.listerSauvegardes();
        
        if (saves.length > 0) {
            view.afficherMessage("\n=== JEST ===\nSauvegardes :");
            for (int i = 0; i < saves.length; i++) view.afficherMessage((i+1) + ". " + saves[i]);
            view.afficherMessage((saves.length+1) + ". Nouvelle partie");
            int choix = view.lireEntier("Choix", 1, saves.length + 1);
            if (choix <= saves.length) {
                partie.chargerPartie(saves[choix - 1]);
                while (!partie.pioche.estVide() && !partie.partieTerminee) partie.jouerUnTour();
                partie.conclurePartie();
                return;
            }
        }
        partie.demarrer();
    }
}
