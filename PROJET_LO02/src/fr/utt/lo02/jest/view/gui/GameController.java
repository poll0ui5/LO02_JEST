package fr.utt.lo02.jest.view.gui;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.strategy.*;
import fr.utt.lo02.jest.variante.*;
import fr.utt.lo02.jest.visitor.VisitorScore;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

// Gère la logique du jeu et fait le lien avec l'interface
public class GameController {
    
    private JestApp app;
    private ArrayList<Joueur> joueurs;
    private JeuCartes pioche;
    private ArrayList<Carte> trophees;
    private Variante variante;
    private int numeroManche;
    private boolean partieTerminee;
    
    // Configuration
    private int nbJoueurs = 3;
    private int nbHumains = 1;
    private String[] nomsJoueurs;
    private String varianteChoisie = "Classique";
    
    // État du tour
    private Joueur joueurActuel;
    private ArrayList<Joueur> joueursAyantJoue;
    private GameScreen gameScreen;
    
    public GameController(JestApp app) {
        this.app = app;
        this.joueurs = new ArrayList<>();
        this.trophees = new ArrayList<>();
        this.joueursAyantJoue = new ArrayList<>();
    }
    
    // --- Configuration ---
    
    public void setNbJoueurs(int nb) { this.nbJoueurs = nb; }
    public void setNbHumains(int nb) { this.nbHumains = nb; }
    public void setNomsJoueurs(String[] noms) { this.nomsJoueurs = noms; }
    public void setVariante(String v) { this.varianteChoisie = v; }
    
    public int getNbJoueurs() { return nbJoueurs; }
    public int getNbHumains() { return nbHumains; }
    public String getVarianteChoisie() { return varianteChoisie; }
    
    // --- Initialisation ---
    
    public void demarrerPartie() {
        joueurs.clear();
        trophees.clear();
        numeroManche = 1;
        partieTerminee = false;
        
        // Créer la variante
        switch (varianteChoisie) {
            case "Sans Trophée": variante = new VarianteSansTrophee(); break;
            case "Double Mise": variante = new VarianteDoubleMise(); break;
            default: variante = new VarianteClassique();
        }
        
        // Créer les joueurs
        for (int i = 0; i < nbHumains; i++) {
            String nom = (nomsJoueurs != null && i < nomsJoueurs.length) ? nomsJoueurs[i] : "Joueur " + (i+1);
            joueurs.add(new JoueurHumain(nom));
        }
        for (int i = 0; i < nbJoueurs - nbHumains; i++) {
            Strategie strat = (i % 2 == 0) ? new StrategieOffensive() : new StrategieDefensive();
            joueurs.add(new JoueurVirtuel("Bot " + (i+1), strat));
        }
        
        // Créer et mélanger la pioche
        pioche = new JeuCartes();
        pioche.melanger();
        
        // Distribuer les trophées
        int nbTrophees = variante.getNombreTrophees(nbJoueurs);
        for (int i = 0; i < nbTrophees && !pioche.estVide(); i++) {
            Carte t = pioche.distribuerUneCarte();
            t.show();
            trophees.add(t);
        }
        
        // Afficher l'écran de jeu
        app.showGameScreen();
    }
    
    // --- Logique de jeu ---
    
    public void demarrerManche() {
        if (pioche.getTasCartes().size() < joueurs.size() * 2) {
            partieTerminee = true;
            conclurePartie();
            return;
        }
        
        // Réinitialiser les offres et mains
        for (Joueur j : joueurs) {
            j.getMain().clear();
            j.getOffre()[0] = null;
            j.getOffre()[1] = null;
        }
        
        // Distribution
        for (Joueur j : joueurs) {
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
            if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
        }
        
        // Les bots font leur offre automatiquement
        for (Joueur j : joueurs) {
            if (j instanceof JoueurVirtuel) {
                j.faireOffre();
            }
        }
        
        joueursAyantJoue.clear();
        
        // Mettre à jour l'affichage
        if (gameScreen != null) {
            gameScreen.updateDisplay();
        }
        
        // Vérifier si tous les joueurs ont fait leur offre
        boolean tousOntFaitOffre = true;
        for (Joueur j : joueurs) {
            if (j.getOffre()[0] == null && j.getOffre()[1] == null && j.getMain().size() > 0) {
                tousOntFaitOffre = false;
                break;
            }
        }
        
        // Si tous ont fait leur offre, démarrer la phase de prise
        if (tousOntFaitOffre) {
            joueurActuel = trouverMeilleureOffre(joueurs);
            if (gameScreen != null) {
                gameScreen.updateDisplay();
                gameScreen.showMessage("C'est au tour de " + joueurActuel.getNom() + " de choisir");
            }
            
            // Si c'est un bot, il joue automatiquement avec un délai
            if (joueurActuel instanceof JoueurVirtuel) {
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> jouerTourBot());
                pause.play();
            }
        }
    }
    
    public void joueurFaitOffre(Joueur joueur, int indexVisible) {
        int indexCachee = (indexVisible == 0) ? 1 : 0;
        joueur.creerOffre(indexVisible, indexCachee);
        
        // Vérifier si tous les joueurs ont fait leur offre
        boolean tousOntFaitOffre = true;
        for (Joueur j : joueurs) {
            if (j.getOffre()[0] == null && j.getOffre()[1] == null && j.getMain().size() > 0) {
                tousOntFaitOffre = false;
                break;
            }
        }
        
        if (tousOntFaitOffre) {
            // Commencer la phase de prise
            joueurActuel = trouverMeilleureOffre(joueurs);
            if (gameScreen != null) {
                gameScreen.updateDisplay();
                gameScreen.showMessage("C'est au tour de " + joueurActuel.getNom() + " de choisir");
            }
            
            // Si c'est un bot, il joue automatiquement avec un délai
            if (joueurActuel instanceof JoueurVirtuel) {
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> jouerTourBot());
                pause.play();
            }
        }
    }
    
    public void joueurPrendCarte(Joueur cible, int indexCarte) {
        if (joueurActuel == null) return;
        
        CarteOffre[] offre = cible.getOffre();
        Carte carte = offre[indexCarte];
        if (carte == null) return;
        
        offre[indexCarte] = null;
        joueurActuel.ajouterAuJest(carte);
        joueursAyantJoue.add(joueurActuel);
        
        // Déterminer le prochain joueur
        if (!joueursAyantJoue.contains(cible)) {
            joueurActuel = cible;
        } else {
            ArrayList<Joueur> restants = new ArrayList<>();
            for (Joueur j : joueurs) {
                if (!joueursAyantJoue.contains(j)) restants.add(j);
            }
            if (!restants.isEmpty()) {
                joueurActuel = trouverMeilleureOffre(restants);
            } else {
                // Fin de la manche
                finirManche();
                return;
            }
        }
        
        // Si c'est un bot, il joue automatiquement avec un délai
        if (joueurActuel instanceof JoueurVirtuel) {
            if (gameScreen != null) {
                gameScreen.updateDisplay();
                gameScreen.showMessage(joueurActuel.getNom() + " réfléchit...");
            }
            
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> jouerTourBot());
            pause.play();
        } else if (gameScreen != null) {
            gameScreen.updateDisplay();
            gameScreen.showMessage("C'est à " + joueurActuel.getNom() + " de choisir une carte");
        }
    }
    
    private void jouerTourBot() {
        JoueurVirtuel bot = (JoueurVirtuel) joueurActuel;
        Joueur cible = bot.choisirAdversaire(joueurs);
        Carte carte = bot.prendreCarteDansOffre(cible);
        
        if (carte != null) {
            bot.ajouterAuJest(carte);
        }
        joueursAyantJoue.add(bot);
        
        if (gameScreen != null) {
            gameScreen.showMessage(bot.getNom() + " prend une carte de " + cible.getNom());
        }
        
        // Déterminer le prochain joueur
        if (!joueursAyantJoue.contains(cible)) {
            joueurActuel = cible;
        } else {
            ArrayList<Joueur> restants = new ArrayList<>();
            for (Joueur j : joueurs) {
                if (!joueursAyantJoue.contains(j)) restants.add(j);
            }
            if (!restants.isEmpty()) {
                joueurActuel = trouverMeilleureOffre(restants);
            } else {
                finirManche();
                return;
            }
        }
        
        // Continuer si c'est encore un bot avec un délai
        if (joueurActuel instanceof JoueurVirtuel) {
            if (gameScreen != null) {
                gameScreen.updateDisplay();
            }
            
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> jouerTourBot());
            pause.play();
        } else if (gameScreen != null) {
            gameScreen.updateDisplay();
            gameScreen.showMessage("C'est à " + joueurActuel.getNom() + " de choisir une carte");
        }
    }
    
    private void finirManche() {
        numeroManche++;
        
        if (pioche.estVide() || pioche.getTasCartes().size() < joueurs.size() * 2) {
            partieTerminee = true;
            conclurePartie();
        } else {
            // Nouvelle manche
            demarrerManche();
        }
    }
    
    private void conclurePartie() {
        // Récupérer les cartes restantes
        for (Joueur j : joueurs) {
            j.recupererDerniereCarteDeLOffre();
        }
        
        // Attribuer les trophées
        for (Carte trophee : trophees) {
            Joueur gagnant = null;
            int max = -1;
            for (Joueur j : joueurs) {
                int count = 0;
                if (trophee.estJoker()) {
                    for (Carte c : j.getJest()) {
                        if (c.getCouleur() == Couleur.COEUR) count++;
                    }
                } else {
                    for (Carte c : j.getJest()) {
                        if (c.getValeur() == trophee.getValeur()) count++;
                    }
                }
                if (count > max) {
                    max = count;
                    gagnant = j;
                }
            }
            if (gagnant != null) {
                gagnant.ajouterAuJest(trophee);
            }
        }
        
        // Calculer les scores
        VisitorScore calc = new VisitorScore();
        for (Joueur j : joueurs) {
            j.accept(calc);
        }
        
        // Appliquer règles finales de la variante
        variante.appliquerReglesFinales(joueurs);
        
        // Afficher l'écran des résultats
        app.showResultScreen();
    }
    
    private Joueur trouverMeilleureOffre(List<Joueur> candidats) {
        Joueur meilleur = candidats.get(0);
        for (Joueur j : candidats) {
            Carte c1 = j.getCarteVisibleDeLOffre();
            Carte c2 = meilleur.getCarteVisibleDeLOffre();
            if (c1 != null && (c2 == null || c1.estSuperieureA(c2))) {
                meilleur = j;
            }
        }
        return meilleur;
    }
    
    // --- Getters ---
    
    public ArrayList<Joueur> getJoueurs() { return joueurs; }
    public ArrayList<Carte> getTrophees() { return trophees; }
    public Variante getVariante() { return variante; }
    public int getNumeroManche() { return numeroManche; }
    public boolean isPartieTerminee() { return partieTerminee; }
    public Joueur getJoueurActuel() { return joueurActuel; }
    public JeuCartes getPioche() { return pioche; }
    
    public void setGameScreen(GameScreen screen) { this.gameScreen = screen; }
    
    public Joueur getGagnant() {
        if (joueurs.isEmpty()) return null;
        Joueur gagnant = joueurs.get(0);
        for (Joueur j : joueurs) {
            if (j.getScore() > gagnant.getScore()) {
                gagnant = j;
            }
        }
        return gagnant;
    }
    
    public void retourMenu() {
        app.showMenuScreen();
    }
}
