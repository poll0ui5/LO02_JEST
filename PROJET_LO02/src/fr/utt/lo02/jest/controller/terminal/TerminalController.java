package fr.utt.lo02.jest.controller.terminal;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.view.terminal.TerminalView;
import fr.utt.lo02.jest.model.VisitorScore;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Contrôleur gérant le flux du jeu en mode terminal (pattern MVC).
 * 
 * <p>
 * Ce projet implémente le jeu de cartes Jest avec une architecture MVC stricte,
 * permettant deux modes de jeu : interface graphique (Swing) et terminal.
 * </p>
 * 
 * <p><b>Architecture MVC :</b></p>
 * <ul>
 *   <li><b>Model</b> : Logique métier (cartes, joueurs, stratégies, variantes)</li>
 *   <li><b>View</b> : Interfaces utilisateur (Terminal, Swing)</li>
 *   <li><b>Controller</b> : Coordination du flux de jeu</li>
 * </ul>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-15
 * 
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public class TerminalController {
    private ArrayList<Joueur> joueurs = new ArrayList<>();
    private JeuCartes pioche = new JeuCartes();
    private ArrayList<Carte> trophees = new ArrayList<>();
    private TerminalView view = new TerminalView();
    private boolean partieTerminee = false;
    private int numeroManche = 1;
    private Variante variante;
    private Extension extension;

    /**
     * Lance une nouvelle partie de Jest.
     * <p>
     * Initialise le jeu, puis exécute les tours jusqu'à épuisement de la pioche
     * ou fin anticipée de la partie. Termine par le calcul des scores.
     * </p>
     */
    public void demarrer() {
        initialiserJeu();
        while (!pioche.estVide() && !partieTerminee)
            jouerUnTour();
        conclurePartie();
    }

    /**
     * Configure et initialise une nouvelle partie.
     * <p>
     * Demande au joueur de choisir :
     * <ul>
     * <li>La variante de jeu (Classique, Sans Trophée, Double Mise)</li>
     * <li>L'extension (Cartes Spéciales optionnelle)</li>
     * <li>Le nombre de joueurs humains et virtuels</li>
     * </ul>
     * Puis distribue les trophées selon la variante choisie.
     * </p>
     */
    public void initialiserJeu() {
        view.afficherMessage("\n=== CONFIGURATION ===");
        view.afficherMessage("Variante : 1.Classique 2.Sans Trophée 3.Double Mise");
        int v = view.lireEntier("Choix", 1, 3);
        switch (v) {
            case 2:
                variante = new VarianteSansTrophee();
                break;
            case 3:
                variante = new VarianteDoubleMise();
                break;
            default:
                variante = new VarianteClassique();
        }
        view.afficherMessage("Variante : " + variante.getNom());

        view.afficherMessage("Extension : 1.Non 2.Cartes Spéciales");
        if (view.lireEntier("Choix", 1, 2) == 2) {
            extension = new ExtensionCartesSpeciales();
            extension.setActive(true);
            for (Carte c : extension.getCartesExtension())
                pioche.getTasCartes().add(c);
        }

        int nbJoueurs = view.demanderNombreJoueurs();
        int nbHumains = view.lireEntier("Joueurs humains ?", 1, nbJoueurs);

        for (int i = 0; i < nbHumains; i++)
            joueurs.add(new JoueurHumain(view.demanderNomJoueur(i + 1)));
        for (int i = 0; i < nbJoueurs - nbHumains; i++)
            joueurs.add(new JoueurVirtuel("Bot " + (i + 1),
                    i % 2 == 0 ? new StrategieOffensive() : new StrategieDefensive()));

        pioche.melanger();
        int nbTrophees = variante.getNombreTrophees(nbJoueurs);
        for (int i = 0; i < nbTrophees && !pioche.estVide(); i++) {
            Carte t = pioche.distribuerUneCarte();
            t.show();
            trophees.add(t);
        }
        if (nbTrophees > 0)
            view.afficherTrophees(trophees);
    }

    /**
     * Joue un tour complet (une manche).
     * <p>
     * Déroulement d'un tour :
     * <ol>
     * <li>Distribution de 2 cartes à chaque joueur</li>
     * <li>Chaque joueur fait son offre (1 carte visible, 1 carte cachée)</li>
     * <li>Le joueur avec la meilleure offre commence</li>
     * <li>Chacun prend une carte dans l'offre d'un adversaire</li>
     * <li>La cible devient le joueur actif (sauf si déjà joué)</li>
     * </ol>
     * </p>
     */
    private void jouerUnTour() {
        view.afficherMessage("\n--- MANCHE " + numeroManche + " ---");
        
        // Vérifier qu'il y a assez de cartes pour distribuer
        int cartesNecessaires = joueurs.size() * 2;
        if (pioche.getTasCartes().size() < cartesNecessaires) {
            view.afficherMessage("Pas assez de cartes pour une nouvelle manche.");
            partieTerminee = true;
            return;
        }
        
        for (Joueur j : joueurs) {
            if (!pioche.estVide())
                j.ramasserCarte(pioche.distribuerUneCarte());
            if (!pioche.estVide())
                j.ramasserCarte(pioche.distribuerUneCarte());
        }
        for (Joueur j : joueurs) {
            j.faireOffre();
            view.afficherOffre(j);
        }

        ArrayList<Joueur> joues = new ArrayList<>();
        Joueur actuel = trouverMeilleureOffre(joueurs);

        while (joues.size() < joueurs.size()) {
            view.afficherMessage("Tour de " + actuel.getNom());
            Joueur cible = actuel.choisirAdversaire(joueurs);
            Carte carte = actuel.prendreCarteDansOffre(cible);
            actuel.ajouterAuJest(carte);
            joues.add(actuel);
            view.afficherMessage(actuel.getNom() + " prend " + carte + " chez " + cible.getNom());

            if (!joues.contains(cible))
                actuel = cible;
            else {
                ArrayList<Joueur> restants = new ArrayList<>();
                for (Joueur j : joueurs)
                    if (!joues.contains(j))
                        restants.add(j);
                if (!restants.isEmpty())
                    actuel = trouverMeilleureOffre(restants);
            }
        }
        numeroManche++;
        if (pioche.estVide())
            partieTerminee = true;
    }

    /**
     * Trouve le joueur ayant la meilleure offre visible parmi les candidats.
     * <p>
     * Compare les cartes visibles selon leur valeur faciale, puis leur couleur
     * (Pique > Trèfle > Carreau > Cœur) en cas d'égalité.
     * </p>
     * 
     * @param candidats Liste des joueurs à comparer
     * @return Le joueur avec la meilleure offre visible
     */
    private Joueur trouverMeilleureOffre(List<Joueur> candidats) {
        Joueur meilleur = candidats.get(0);
        for (Joueur j : candidats) {
            Carte c1 = j.getCarteVisibleDeLOffre(), c2 = meilleur.getCarteVisibleDeLOffre();
            if (c1 != null && (c2 == null || c1.estSuperieureA(c2)))
                meilleur = j;
        }
        return meilleur;
    }

    /**
     * Conclut la partie et détermine le gagnant.
     * <p>
     * Étapes finales :
     * <ol>
     * <li>Récupération des cartes restantes dans les offres</li>
     * <li>Attribution des trophées (majorité de valeur ou couleur)</li>
     * <li>Calcul des scores avec le Visitor Pattern</li>
     * <li>Application des règles spéciales de la variante</li>
     * <li>Annonce du gagnant</li>
     * </ol>
     * </p>
     */
    private void conclurePartie() {
        view.afficherMessage("\n--- FIN ---");
        for (Joueur j : joueurs)
            j.recupererDerniereCarteDeLOffre();
        attribuerTrophees();
        VisitorScore calc = new VisitorScore();
        for (Joueur j : joueurs)
            j.accept(calc);
        variante.appliquerReglesFinales(joueurs);
        for (Joueur j : joueurs)
            view.afficherScore(j);
        Joueur gagnant = joueurs.get(0);
        for (Joueur j : joueurs)
            if (j.getScore() > gagnant.getScore())
                gagnant = j;
        view.afficherMessage("\n*** Gagnant : " + gagnant.getNom() + " (" + gagnant.getScore() + " pts) ***");
    }

    /**
     * Attribue les trophées aux joueurs ayant la majorité correspondante.
     * <p>
     * Pour chaque trophée :
     * <ul>
     * <li>Si c'est un Joker : compte les Cœurs dans le Jest</li>
     * <li>Sinon : compte les cartes de même valeur dans le Jest</li>
     * </ul>
     * Le joueur avec le plus de cartes correspondantes remporte le trophée.
     * </p>
     */
    private void attribuerTrophees() {
        for (Carte trophee : trophees) {
            Joueur gagnant = null;
            int max = -1;
            for (Joueur j : joueurs) {
                int count = 0;
                if (trophee.estJoker()) {
                    for (Carte c : j.getJest())
                        if (c.getCouleur() == Couleur.COEUR)
                            count++;
                } else {
                    for (Carte c : j.getJest())
                        if (c.getValeur() == trophee.getValeur())
                            count++;
                }
                if (count > max) {
                    max = count;
                    gagnant = j;
                }
            }
            if (gagnant != null) {
                gagnant.ajouterAuJest(trophee);
                view.afficherMessage("Trophée " + trophee + " -> " + gagnant.getNom());
            }
        }
    }

    /**
     * Sauvegarde l'état actuel de la partie dans un fichier.
     * 
     * @param nom Nom de la sauvegarde
     * @return true si la sauvegarde a réussi, false sinon
     */
    public boolean sauvegarderPartie(String nom) {
        EtatPartie etat = new EtatPartie();
        etat.setJoueurs(joueurs);
        etat.setPioche(pioche.getTasCartes());
        etat.setTrophees(trophees);
        etat.setNumeroManche(numeroManche);
        etat.setPartieTerminee(partieTerminee);
        if (variante != null)
            etat.setNomVariante(variante.getNom());
        if (extension != null && extension.estActive())
            etat.setNomExtension(extension.getNom());
        return GestionnaireSauvegarde.sauvegarder(etat, nom);
    }

    /**
     * Charge une partie précédemment sauvegardée.
     * <p>
     * Restaure tous les éléments de la partie : joueurs, pioche, trophées,
     * numéro de manche, variante et extension.
     * </p>
     * 
     * @param nom Nom de la sauvegarde à charger
     * @return true si le chargement a réussi, false sinon
     */
    public boolean chargerPartie(String nom) {
        Object obj = GestionnaireSauvegarde.charger(nom);
        if (!(obj instanceof EtatPartie))
            return false;
        EtatPartie etat = (EtatPartie) obj;
        joueurs = etat.getJoueurs();
        pioche.getTasCartes().clear();
        pioche.getTasCartes().addAll(etat.getPioche());
        trophees = etat.getTrophees();
        numeroManche = etat.getNumeroManche();
        partieTerminee = etat.isPartieTerminee();
        switch (etat.getNomVariante()) {
            case "Sans Trophée":
                variante = new VarianteSansTrophee();
                break;
            case "Double Mise":
                variante = new VarianteDoubleMise();
                break;
            default:
                variante = new VarianteClassique();
        }
        if (etat.getNomExtension() != null) {
            extension = new ExtensionCartesSpeciales();
            extension.setActive(true);
        }
        return true;
    }

    public static void main(String[] args) {
        TerminalController partie = new TerminalController();
        TerminalView view = new TerminalView();
        String[] saves = GestionnaireSauvegarde.listerSauvegardes();

        if (saves.length > 0) {
            view.afficherMessage("\n=== JEST ===\nSauvegardes :");
            for (int i = 0; i < saves.length; i++)
                view.afficherMessage((i + 1) + ". " + saves[i]);
            view.afficherMessage((saves.length + 1) + ". Nouvelle partie");
            int choix = view.lireEntier("Choix", 1, saves.length + 1);
            if (choix <= saves.length) {
                partie.chargerPartie(saves[choix - 1]);
                while (!partie.pioche.estVide() && !partie.partieTerminee)
                    partie.jouerUnTour();
                partie.conclurePartie();
                return;
            }
        }
        partie.demarrer();
    }
}
