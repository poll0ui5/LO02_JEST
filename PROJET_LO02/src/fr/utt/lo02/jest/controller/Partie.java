package fr.utt.lo02.jest.controller;

import java.util.ArrayList;
import java.util.List;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Couleur;
import fr.utt.lo02.jest.model.JeuCartes;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurHumain;
import fr.utt.lo02.jest.model.JoueurVirtuel;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.strategy.StrategieOffensive;
import fr.utt.lo02.jest.strategy.StrategieDefensive;
import fr.utt.lo02.jest.variante.Variante;
import fr.utt.lo02.jest.variante.VarianteClassique;
import fr.utt.lo02.jest.variante.VarianteSansTrophee;
import fr.utt.lo02.jest.variante.VarianteDoubleMise;
import fr.utt.lo02.jest.extension.Extension;
import fr.utt.lo02.jest.extension.ExtensionCartesSpeciales;
import fr.utt.lo02.jest.sauvegarde.EtatPartie;
import fr.utt.lo02.jest.sauvegarde.GestionnaireSauvegarde;
import fr.utt.lo02.jest.view.Terminal;
import fr.utt.lo02.jest.visitor.VisitorScore;

/**
 * Contrôleur principal de l'application Jest.
 * <p>
 * Cette classe orchestre le déroulement complet d'une partie selon le patron MVC.
 * Elle gère le cycle de vie du jeu : initialisation, boucle principale des tours
 * (distribution, offres, prises), et calcul final des scores via le patron Visitor.
 * </p>
 * * @see fr.utt.lo02.jest.model.Joueur
 * @see fr.utt.lo02.jest.visitor.VisitorScore
 * * @author Projet LO02
 * @version 2.0
 */
public class Partie {

    /**
     * La liste des joueurs participant à la partie (3 ou 4 joueurs).
     */
    private ArrayList<Joueur> joueurs;

    /**
     * Le système de pioche (Deck) contenant les cartes non distribuées.
     */
    private JeuCartes pioche;

    /**
     * La liste des cartes Trophées mises en jeu (visibles de tous).
     * Ces cartes définissent des conditions de bonus pour la fin de partie.
     */
    private ArrayList<Carte> trophees;

    /**
     * L'interface de vue (Console/Terminal) permettant d'interagir avec l'utilisateur.
     */
    private Terminal view;

    /**
     * Indicateur de l'état de la partie.
     * Vaut {@code true} si la pioche est vide et le dernier tour terminé.
     */
    private boolean partieTerminee;

    /**
     * Numéro de la manche actuelle (à titre indicatif).
     */
    private int numeroManche;
    
    /**
     * La variante de règles choisie pour cette partie.
     */
    private Variante variante;
    
    /**
     * L'extension de cartes (optionnelle).
     */
    private Extension extension;

    /**
     * Constructeur de la classe Partie.
     * <p>
     * Initialise les listes de joueurs et de trophées, instancie la vue
     * et prépare la pioche.
     * </p>
     */
    public Partie() {
        this.joueurs = new ArrayList<Joueur>();
        this.pioche = new JeuCartes(); 
        this.trophees = new ArrayList<Carte>();
        this.view = new Terminal();
        this.partieTerminee = false;
        this.numeroManche = 1;
    }

    /**
     * Point d'entrée pour lancer la logique du jeu.
     * <p>
     * Cette méthode exécute séquentiellement :
     * <ol>
     * <li>L'initialisation (création joueurs, trophées).</li>
     * <li>La boucle de jeu (tant qu'il y a des cartes).</li>
     * <li>La conclusion (calcul des scores et vainqueur).</li>
     * </ol>
     * </p>
     */
    public void demarrer() {
        initialiserJeu();

        // Boucle principale : tant que la pioche permet de distribuer ou qu'il reste des tours
        while (!pioche.estVide() && !partieTerminee) {
            jouerUnTour();
        }

        conclurePartie();
    }

    /**
     * Initialise les paramètres de la partie.
     * <p>
     * Demande le nombre de joueurs via la vue, crée les instances de {@link Joueur},
     * mélange la pioche et distribue les trophées selon la règle :
     * <ul>
     * <li>3 joueurs : 2 trophées distribués.</li>
     * <li>4 joueurs : 1 trophée distribué.</li>
     * </ul>
     * </p>
     */

    /**
     * Initialise les paramètres de la partie.
     * Demande la variante, le nombre de joueurs, leurs types et noms.
     */
    public void initialiserJeu() {
        // 1. Choix de la variante
        view.afficherMessage("\n=== CONFIGURATION DE LA PARTIE ===");
        view.afficherMessage("Choisissez une variante :");
        view.afficherMessage("  1. Classique - Règles de base");
        view.afficherMessage("  2. Sans Trophée - Toutes les cartes jouées");
        view.afficherMessage("  3. Double Mise - 3 trophées + bonus/malus couleurs");
        
        int choixVariante = view.lireEntier("Votre choix", 1, 3);
        switch (choixVariante) {
            case 1: this.variante = new VarianteClassique(); break;
            case 2: this.variante = new VarianteSansTrophee(); break;
            case 3: this.variante = new VarianteDoubleMise(); break;
            default: this.variante = new VarianteClassique();
        }
        view.afficherMessage("Variante choisie : " + variante.getNom());
        view.afficherMessage(variante.getDescription());
        
        // 2. Choix de l'extension (optionnel)
        view.afficherMessage("\nVoulez-vous activer une extension ?");
        view.afficherMessage("  1. Non - Jeu de base uniquement");
        view.afficherMessage("  2. Cartes Spéciales - Ajoute 2 cartes bonus");
        
        int choixExtension = view.lireEntier("Votre choix", 1, 2);
        if (choixExtension == 2) {
            this.extension = new ExtensionCartesSpeciales();
            this.extension.setActive(true);
            // Ajouter les cartes de l'extension à la pioche
            for (Carte c : extension.getCartesExtension()) {
                pioche.getTasCartes().add(c);
            }
            view.afficherMessage("Extension activée : " + extension.getNom());
        }
        
        // 3. Nombre total de joueurs (3 ou 4)
        int nbJoueurs = view.demanderNombreJoueurs();
        
        // 3. Nombre de joueurs humains
        int nbHumains = view.lireEntier("Combien de joueurs humains ?", 1, nbJoueurs);
        int nbVirtuels = nbJoueurs - nbHumains;
        
        // 4. Création des joueurs humains
        for (int i = 0; i < nbHumains; i++) {
            String nom = view.demanderNomJoueur(i + 1);
            this.joueurs.add(new JoueurHumain(nom));
        }
        
        // 5. Création des joueurs virtuels avec stratégie alternée
        for (int i = 0; i < nbVirtuels; i++) {
            String nomBot = "Bot " + (i + 1);
            if (i % 2 == 0) {
                this.joueurs.add(new JoueurVirtuel(nomBot, new StrategieOffensive()));
            } else {
                this.joueurs.add(new JoueurVirtuel(nomBot, new StrategieDefensive()));
            }
            view.afficherMessage("Joueur virtuel ajouté : " + nomBot);
        }

        // 6. Mélange des cartes
        this.pioche.melanger();

        // 7. Mise en place des trophées selon la variante
        int nbTrophees = variante.getNombreTrophees(nbJoueurs);

        for (int i = 0; i < nbTrophees; i++) {
            if (!pioche.estVide()) {
                Carte t = pioche.distribuerUneCarte();
                t.show();
                this.trophees.add(t);
            }
        }
        
        // Affichage initial
        view.afficherMessage("\nLa partie commence avec " + nbHumains + " humain(s) et " + nbVirtuels + " bot(s).");
        if (nbTrophees > 0) {
            view.afficherTrophees(trophees);
        } else {
            view.afficherMessage("Aucun trophée dans cette variante.");
        }
    }

    /**
     * Orchestre un tour de jeu complet (une manche).
     * <p>
     * Un tour se décompose en trois phases strictes :
     * <ol>
     * <li>Distribution : 2 cartes par joueur.</li>
     * <li>Offre : Chaque joueur choisit sa carte visible et cachée.</li>
     * <li>Prise : Les joueurs récupèrent des cartes tour à tour.</li>
     * </ol>
     * </p>
     */
    private void jouerUnTour() {
        view.afficherMessage("\n--- DEBUT MANCHE " + numeroManche + " ---");

        // --- Phase 1 : Distribution ---
        distribuerCartes();

        // --- Phase 2 : Mise en place des offres ---
        faireLesOffres();

        // --- Phase 3 : Prise des cartes ---
        gererPhaseDePrise();

        // Fin de manche
        numeroManche++;
        if (pioche.estVide()) {
            this.partieTerminee = true;
        }
    }

    /**
     * Distribue deux cartes à chaque joueur depuis la pioche.
     * <p>
     * Si la pioche est vide au milieu de la distribution, la distribution s'arrête.
     * </p>
     */
    private void distribuerCartes() {
        for (Joueur j : joueurs) {
            // Règle : Chaque joueur reçoit 2 cartes [cite: 114]
            // On vérifie à chaque fois si la pioche n'est pas vide
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
        }
        view.afficherMessage("Distribution terminée.");
    }

    /**
     * Demande à chaque joueur de préparer son offre.
     * <p>
     * Chaque joueur possède 2 cartes en main. Il doit en choisir une à mettre
     * face visible, l'autre restera face cachée.
     * </p>
     * @see fr.utt.lo02.jest.model.Joueur#faireOffre()
     */
    private void faireLesOffres() {
        for (Joueur j : joueurs) {
            // Le joueur (ou l'IA) décide quelle carte montrer
            j.faireOffre(); 
            view.afficherOffre(j); // Affiche : "Joueur X propose [Visible] et [Cachée]"
        }
    }

    /**
     * Gère la mécanique complexe de prise de cartes.
     * <p>
     * L'ordre de jeu est déterminé dynamiquement :
     * <ul>
     * <li>Le premier joueur est celui avec la carte visible la plus forte.</li>
     * <li>Le joueur suivant est celui qui vient de se faire prendre une carte.</li>
     * <li>Si ce joueur a déjà joué, on regarde la plus forte carte visible restante.</li>
     * </ul>
     * </p>
     */
    private void gererPhaseDePrise() {
        ArrayList<Joueur> joueursAyantJoue = new ArrayList<>();
        
        // 1. Déterminer le premier joueur (Meilleure carte visible) [cite: 123]
        Joueur joueurActuel = trouverJoueurAvecMeilleureOffre(this.joueurs);

        // Boucle : tant que tout le monde n'a pas pris une carte
        while (joueursAyantJoue.size() < this.joueurs.size()) {
            
            view.afficherMessage("C'est au tour de " + joueurActuel.getNom());

            // 2. Le joueur choisit une cible (un autre joueur)
            // Note: Si c'est le dernier joueur et qu'il reste seulement son offre, il se prend lui-même.
            Joueur cible = joueurActuel.choisirAdversaire(this.joueurs);
            
            // 3. Le joueur choisit une carte dans l'offre de la cible
            Carte carteChoisie = joueurActuel.prendreCarteDansOffre(cible);
            
            // 4. Ajout au Jest (le tas de cartes gagnées)
            joueurActuel.ajouterAuJest(carteChoisie);
            joueursAyantJoue.add(joueurActuel);
            
            view.afficherMessage(joueurActuel.getNom() + " a pris " + carteChoisie + " chez " + cible.getNom());

            // 5. Déterminer qui joue au prochain tour [cite: 133-135]
            if (!joueursAyantJoue.contains(cible)) {
                // Règle : C'est la victime qui joue, si elle n'a pas encore joué
                joueurActuel = cible;
            } else {
                // Sinon, on cherche le meilleur parmi ceux qui n'ont pas joué
                ArrayList<Joueur> restants = new ArrayList<>();
                for(Joueur j : this.joueurs) {
                    if(!joueursAyantJoue.contains(j)) {
                        restants.add(j);
                    }
                }
                
                if (!restants.isEmpty()) {
                    joueurActuel = trouverJoueurAvecMeilleureOffre(restants);
                }
            }
        }
    }

    /**
     * Trouve le joueur possédant la carte face visible la plus forte.
     * <p>
     * Utilise l'ordre des couleurs (Pique > Trèfle > Carreau > Cœur) en cas d'égalité de valeur.
     * </p>
     * * @param candidats La liste des joueurs parmi lesquels chercher.
     * @return Le joueur ayant l'offre la plus forte.
     */
    private Joueur trouverJoueurAvecMeilleureOffre(List<Joueur> candidats) {
        Joueur meilleur = candidats.get(0);
        for (Joueur j : candidats) {
            Carte c1 = j.getCarteVisibleDeLOffre(); // Retourne CarteOffre (sous-classe de Carte)
            Carte c2 = meilleur.getCarteVisibleDeLOffre();
            
            // Gestion des cas null (offre vide)
            if (c1 == null) continue;
            if (c2 == null || c1.estSuperieureA(c2)) { 
                meilleur = j;
            }
        }
        return meilleur;
    }

    /**
     * Finalise la partie une fois la pioche vide.
     * <p>
     * Cette méthode effectue les actions de fin de jeu :
     * <ol>
     * <li>Chaque joueur récupère la dernière carte restante de son offre[cite: 142].</li>
     * <li>Calcul des scores via le patron Visitor ({@link VisitorScore}).</li>
     * <li>Affichage des résultats et du vainqueur.</li>
     * </ol>
     * </p>
     */
    private void conclurePartie() {
        view.afficherMessage("\n--- FIN DE LA PARTIE ---");

        // 1. Récupération des dernières cartes
        for (Joueur j : joueurs) {
            j.recupererDerniereCarteDeLOffre();
        }

        // 2. Attribution des trophées selon les règles du Jest
        attribuerTrophees();

        // 3. Calcul des scores (Pattern Visitor)
        VisitorScore calculateur = new VisitorScore();

        for (Joueur j : joueurs) {
            j.accept(calculateur); 
        }
        
        // 4. Application des règles finales de la variante
        variante.appliquerReglesFinales(joueurs);
        
        // 5. Affichage des scores
        for (Joueur j : joueurs) {
            view.afficherScore(j);
        }

        // 6. Annonce du gagnant
        Joueur gagnant = determinerGagnant();
        view.afficherMessage("\n*** Le gagnant est : " + gagnant.getNom() + " avec " + gagnant.getScore() + " points ! ***");
    }
    
    /**
     * Attribue les trophées aux joueurs selon les conditions de chaque carte.
     * <p>
     * Règles d'attribution :
     * <ul>
     * <li>Joker : Joueur avec le plus de cartes Cœur (ou meilleur Jest si égalité)</li>
     * <li>As : Joueur avec le plus de cartes de cette valeur</li>
     * <li>2, 3, 4 : Joueur avec le plus de cartes de cette valeur</li>
     * </ul>
     * </p>
     */
    private void attribuerTrophees() {
        view.afficherMessage("\n--- ATTRIBUTION DES TROPHÉES ---");
        
        for (Carte trophee : trophees) {
            Joueur gagnantTrophee = determinerGagnantTrophee(trophee);
            
            if (gagnantTrophee != null) {
                gagnantTrophee.ajouterAuJest(trophee);
                view.afficherMessage("Trophée " + trophee + " attribué à " + gagnantTrophee.getNom());
            }
        }
    }
    
    /**
     * Détermine quel joueur gagne un trophée donné.
     * La condition dépend de la carte du trophée (bande orange dans les règles).
     */
    private Joueur determinerGagnantTrophee(Carte trophee) {
        Joueur gagnant = null;
        int meilleurScore = -1;
        
        for (Joueur j : joueurs) {
            int scoreCondition = calculerScoreConditionTrophee(j, trophee);
            
            if (scoreCondition > meilleurScore) {
                meilleurScore = scoreCondition;
                gagnant = j;
            } else if (scoreCondition == meilleurScore && gagnant != null) {
                // Tie-break : joueur avec la carte de plus haute valeur dans la couleur concernée
                gagnant = departagerTrophee(gagnant, j, trophee);
            }
        }
        
        return gagnant;
    }
    
    /**
     * Calcule le score d'un joueur pour une condition de trophée.
     * Règle : Majorité de cartes d'une certaine valeur ou couleur.
     */
    private int calculerScoreConditionTrophee(Joueur joueur, Carte trophee) {
        int count = 0;
        
        if (trophee.estJoker()) {
            // Joker : compte les Cœurs
            for (Carte c : joueur.getJest()) {
                if (c.getCouleur() == Couleur.COEUR) count++;
            }
        } else {
            // Autres cartes : compte les cartes de même valeur
            Valeur valeurCible = trophee.getValeur();
            for (Carte c : joueur.getJest()) {
                if (c.getValeur() == valeurCible) count++;
            }
        }
        
        return count;
    }
    
    /**
     * Départage deux joueurs à égalité pour un trophée.
     * Règle : Le joueur avec la carte de plus haute valeur (puis couleur) gagne.
     */
    private Joueur departagerTrophee(Joueur j1, Joueur j2, Carte trophee) {
        Carte meilleureJ1 = trouverMeilleureCarteDuJest(j1);
        Carte meilleureJ2 = trouverMeilleureCarteDuJest(j2);
        
        if (meilleureJ1 == null) return j2;
        if (meilleureJ2 == null) return j1;
        
        return meilleureJ1.estSuperieureA(meilleureJ2) ? j1 : j2;
    }
    
    /**
     * Trouve la carte de plus haute valeur dans le Jest d'un joueur.
     */
    private Carte trouverMeilleureCarteDuJest(Joueur joueur) {
        Carte meilleure = null;
        for (Carte c : joueur.getJest()) {
            if (meilleure == null || c.estSuperieureA(meilleure)) {
                meilleure = c;
            }
        }
        return meilleure;
    }

    /**
     * Détermine le joueur gagnant en comparant les scores finaux.
     * * @return Le joueur ayant le score le plus élevé.
     */
    private Joueur determinerGagnant() {
        Joueur gagnant = joueurs.get(0);
        for (Joueur j : joueurs) {
            if (j.getScore() > gagnant.getScore()) {
                gagnant = j;
            }
        }
        return gagnant;
    }

    // ==================== SAUVEGARDE / CHARGEMENT ====================
    
    /**
     * Sauvegarde l'état actuel de la partie.
     * @param nomFichier Le nom du fichier de sauvegarde.
     * @return true si la sauvegarde a réussi.
     */
    public boolean sauvegarderPartie(String nomFichier) {
        EtatPartie etat = new EtatPartie();
        etat.setJoueurs(this.joueurs);
        etat.setPioche(this.pioche.getTasCartes());
        etat.setTrophees(this.trophees);
        etat.setNumeroManche(this.numeroManche);
        etat.setPartieTerminee(this.partieTerminee);
        
        if (this.variante != null) {
            etat.setNomVariante(this.variante.getNom());
        }
        if (this.extension != null && this.extension.estActive()) {
            etat.setNomExtension(this.extension.getNom());
        }
        
        return GestionnaireSauvegarde.sauvegarder(etat, nomFichier);
    }
    
    /**
     * Charge une partie depuis un fichier de sauvegarde.
     * @param nomFichier Le nom du fichier à charger.
     * @return true si le chargement a réussi.
     */
    public boolean chargerPartie(String nomFichier) {
        Object obj = GestionnaireSauvegarde.charger(nomFichier);
        
        if (obj instanceof EtatPartie) {
            EtatPartie etat = (EtatPartie) obj;
            
            this.joueurs = etat.getJoueurs();
            this.pioche.getTasCartes().clear();
            this.pioche.getTasCartes().addAll(etat.getPioche());
            this.trophees = etat.getTrophees();
            this.numeroManche = etat.getNumeroManche();
            this.partieTerminee = etat.isPartieTerminee();
            
            // Restaurer la variante
            switch (etat.getNomVariante()) {
                case "Sans Trophée": this.variante = new VarianteSansTrophee(); break;
                case "Double Mise": this.variante = new VarianteDoubleMise(); break;
                default: this.variante = new VarianteClassique();
            }
            
            // Restaurer l'extension si présente
            if (etat.getNomExtension() != null) {
                this.extension = new ExtensionCartesSpeciales();
                this.extension.setActive(true);
            }
            
            view.afficherMessage("Partie chargée avec succès !");
            return true;
        }
        
        return false;
    }
    
    /**
     * Propose à l'utilisateur de sauvegarder la partie en cours.
     */
    private void proposerSauvegarde() {
        view.afficherMessage("\nVoulez-vous sauvegarder la partie ?");
        view.afficherMessage("  1. Oui");
        view.afficherMessage("  2. Non");
        
        int choix = view.lireEntier("Votre choix", 1, 2);
        if (choix == 1) {
            view.afficherMessage("Entrez un nom pour la sauvegarde :");
            String nom = view.demanderNomJoueur(0).replace(" ", "_");
            if (nom.isEmpty()) nom = "partie_" + System.currentTimeMillis();
            sauvegarderPartie(nom);
        }
    }

    /**
     * Point d'entrée statique de l'application (Main).
     * Propose de charger une partie existante ou d'en commencer une nouvelle.
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        Partie partie = new Partie();
        Terminal view = new Terminal();
        
        // Vérifier s'il existe des sauvegardes
        String[] sauvegardes = GestionnaireSauvegarde.listerSauvegardes();
        
        if (sauvegardes.length > 0) {
            view.afficherMessage("\n=== JEST - JEU DE CARTES ===");
            view.afficherMessage("Sauvegardes disponibles :");
            for (int i = 0; i < sauvegardes.length; i++) {
                view.afficherMessage("  " + (i + 1) + ". " + sauvegardes[i]);
            }
            view.afficherMessage("  " + (sauvegardes.length + 1) + ". Nouvelle partie");
            
            int choix = view.lireEntier("Votre choix", 1, sauvegardes.length + 1);
            
            if (choix <= sauvegardes.length) {
                partie.chargerPartie(sauvegardes[choix - 1]);
                // Reprendre la partie chargée
                while (!partie.pioche.estVide() && !partie.partieTerminee) {
                    partie.jouerUnTour();
                    partie.proposerSauvegarde();
                }
                partie.conclurePartie();
                return;
            }
        }
        
        // Nouvelle partie
        partie.demarrer();
    }
}