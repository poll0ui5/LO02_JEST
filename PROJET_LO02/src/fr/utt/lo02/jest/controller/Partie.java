package fr.utt.lo02.jest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.JeuCartes; // Ou "Pioche" selon ta classe
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurHumain;
// Importez JoueurVirtuel si nécessaire
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
     * Demande le nombre de joueurs et leurs noms via la vue.
     */
    public void initialiserJeu() {
        // 1. On utilise la vue pour demander le nombre de joueurs (3 ou 4)
        int nbJoueurs = view.demanderNombreJoueurs();
        
        // 2. On crée les joueurs en demandant leur nom
        for (int i = 0; i < nbJoueurs; i++) {
            String nom = view.demanderNomJoueur(i + 1);
            this.joueurs.add(new JoueurHumain(nom));
        }

        // 3. Mélange des cartes
        this.pioche.melanger();

        // 4. Mise en place des trophées
        // Règle : 1 trophée pour 4 joueurs, sinon 2 trophées.
        int nbTrophees = (this.joueurs.size() == 4) ? 1 : 2;

        for (int i = 0; i < nbTrophees; i++) {
            if (!pioche.estVide()) {
                Carte t = pioche.distribuerUneCarte();
                // Les trophées sont face visible par défaut
                t.show(); // Important : on rend le trophée visible !
                this.trophees.add(t);
            }
        }
        
        // Affichage initial
        view.afficherMessage("\nLa partie commence avec " + nbJoueurs + " joueurs.");
        view.afficherTrophees(trophees);
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
            Carte c1 = j.getCarteVisibleDeLOffre(); // Supposons cette méthode dans Joueur
            Carte c2 = meilleur.getCarteVisibleDeLOffre();
            
            // On suppose que Carte implémente une méthode de comparaison ou comparable
            // Logique : Valeur > Valeur ? Sinon Couleur > Couleur
            if (c1.estSuperieureA(c2)) { 
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

        // 2. Calcul des scores (Pattern Visitor)
        VisitorScore calculateur = new VisitorScore();
        
        // On pourrait injecter les trophées dans le visiteur ici si nécessaire
        // calculateur.setTrophees(this.trophees);

        for (Joueur j : joueurs) {
            // Le visiteur "visite" le joueur pour inspecter son Jest et calculer les points
            j.accept(calculateur); 
            view.afficherScore(j);
        }

        // 3. Annonce du gagnant
        Joueur gagnant = determinerGagnant();
        view.afficherMessage("Le gagnant est : " + gagnant.getNom() + " avec " + gagnant.getScore() + " points !");
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

    /**
     * Point d'entrée statique de l'application (Main).
     * * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        Partie partie = new Partie();
        partie.demarrer();
    }
}