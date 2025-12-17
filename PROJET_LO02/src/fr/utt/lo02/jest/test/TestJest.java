package fr.utt.lo02.jest.test;

import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.strategy.*;
import fr.utt.lo02.jest.variante.*;
import fr.utt.lo02.jest.extension.*;
import fr.utt.lo02.jest.visitor.*;
import fr.utt.lo02.jest.sauvegarde.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test automatisé pour le jeu Jest.
 * Teste toutes les fonctionnalités du moteur de jeu.
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class TestJest {
    
    private static int testsReussis = 0;
    private static int testsEchoues = 0;
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           TESTS AUTOMATISÉS - JEU JEST                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Tests des modèles
        testCartes();
        testCouleurs();
        testValeurs();
        testJeuCartes();
        
        // Tests des joueurs
        testJoueurVirtuel();
        testStrategies();
        
        // Tests des variantes
        testVariantes();
        
        // Tests des extensions
        testExtensions();
        
        // Tests du calcul de score (Visitor)
        testVisitorScore();
        
        // Tests de la sauvegarde
        testSauvegarde();
        
        // Résumé
        afficherResume();
    }
    
    // ==================== TESTS DES CARTES ====================
    
    private static void testCartes() {
        System.out.println("=== TEST DES CARTES ===\n");
        
        // Test création carte normale
        Carte carte = new Carte(Valeur.AS, Couleur.PIQUE);
        test("Création carte AS de PIQUE", 
             carte.getValeur() == Valeur.AS && carte.getCouleur() == Couleur.PIQUE);
        
        // Test visibilité
        test("Carte cachée par défaut", !carte.estFaceVisible());
        carte.show();
        test("Carte visible après show()", carte.estFaceVisible());
        carte.hide();
        test("Carte cachée après hide()", !carte.estFaceVisible());
        
        // Test Joker (couleur null)
        Carte joker = new Carte(Valeur.JOKER, null);
        test("Joker créé avec couleur null", joker.getCouleur() == null);
        test("Joker.estJoker() retourne true", joker.estJoker());
        test("Carte normale.estJoker() retourne false", !carte.estJoker());
        
        // Test comparaison
        Carte quatre = new Carte(Valeur.QUATRE, Couleur.PIQUE);
        Carte deux = new Carte(Valeur.DEUX, Couleur.PIQUE);
        test("4 de Pique > 2 de Pique", quatre.estSuperieureA(deux));
        test("2 de Pique < 4 de Pique", !deux.estSuperieureA(quatre));
        
        // Test tie-break couleur
        Carte asPique = new Carte(Valeur.AS, Couleur.PIQUE);
        Carte asCoeur = new Carte(Valeur.AS, Couleur.COEUR);
        test("As de Pique > As de Coeur (tie-break)", asPique.estSuperieureA(asCoeur));
        
        // Test toString
        test("toString() carte normale", carte.toString().contains("AS") && carte.toString().contains("PIQUE"));
        test("toString() Joker", joker.toString().equals("Joker"));
        
        System.out.println();
    }
    
    // ==================== TESTS DES COULEURS ====================
    
    private static void testCouleurs() {
        System.out.println("=== TEST DES COULEURS ===\n");
        
        // Test ordre des couleurs (Pique > Trèfle > Carreau > Coeur)
        test("PIQUE.ordinal() > TREFLE.ordinal()", 
             Couleur.PIQUE.ordinal() > Couleur.TREFLE.ordinal());
        test("TREFLE.ordinal() > CARREAU.ordinal()", 
             Couleur.TREFLE.ordinal() > Couleur.CARREAU.ordinal());
        test("CARREAU.ordinal() > COEUR.ordinal()", 
             Couleur.CARREAU.ordinal() > Couleur.COEUR.ordinal());
        
        System.out.println();
    }
    
    // ==================== TESTS DES VALEURS ====================
    
    private static void testValeurs() {
        System.out.println("=== TEST DES VALEURS ===\n");
        
        test("JOKER valeur faciale = 0", Valeur.JOKER.getValeurFaciale() == 0);
        test("AS valeur faciale = 1", Valeur.AS.getValeurFaciale() == 1);
        test("DEUX valeur faciale = 2", Valeur.DEUX.getValeurFaciale() == 2);
        test("TROIS valeur faciale = 3", Valeur.TROIS.getValeurFaciale() == 3);
        test("QUATRE valeur faciale = 4", Valeur.QUATRE.getValeurFaciale() == 4);
        
        System.out.println();
    }
    
    // ==================== TESTS DU JEU DE CARTES ====================
    
    private static void testJeuCartes() {
        System.out.println("=== TEST DU JEU DE CARTES ===\n");
        
        JeuCartes jeu = new JeuCartes();
        
        test("Jeu contient 17 cartes", jeu.getTasCartes().size() == 17);
        test("Jeu non vide au départ", !jeu.estVide());
        
        // Vérifier qu'il y a bien un Joker
        boolean aJoker = false;
        for (Carte c : jeu.getTasCartes()) {
            if (c.estJoker()) aJoker = true;
        }
        test("Jeu contient un Joker", aJoker);
        
        // Test distribution
        Carte premiere = jeu.distribuerUneCarte();
        test("Distribution retire une carte", jeu.getTasCartes().size() == 16);
        test("Carte distribuée non null", premiere != null);
        
        // Test mélange (ne doit pas lever d'exception)
        try {
            jeu.melanger();
            test("Mélange fonctionne", true);
        } catch (Exception e) {
            test("Mélange fonctionne", false);
        }
        
        // Vider le jeu
        while (!jeu.estVide()) {
            jeu.distribuerUneCarte();
        }
        test("Jeu vide après distribution complète", jeu.estVide());
        
        System.out.println();
    }
    
    // ==================== TESTS DES JOUEURS VIRTUELS ====================
    
    private static void testJoueurVirtuel() {
        System.out.println("=== TEST DES JOUEURS VIRTUELS ===\n");
        
        Strategie offensive = new StrategieOffensive();
        JoueurVirtuel bot = new JoueurVirtuel("Bot Test", offensive);
        
        test("Nom du bot correct", bot.getNom().equals("Bot Test"));
        test("Stratégie assignée", bot.getStrategie() == offensive);
        
        // Test changement de stratégie
        Strategie defensive = new StrategieDefensive();
        bot.setStrategie(defensive);
        test("Changement de stratégie", bot.getStrategie() == defensive);
        
        // Test ajout de cartes
        Carte c1 = new Carte(Valeur.AS, Couleur.PIQUE);
        Carte c2 = new Carte(Valeur.QUATRE, Couleur.COEUR);
        bot.ramasserCarte(c1);
        bot.ramasserCarte(c2);
        test("Main contient 2 cartes", bot.getMain().size() == 2);
        
        // Test création offre
        bot.faireOffre();
        test("Offre créée (main vidée)", bot.getMain().isEmpty());
        test("Offre contient 2 CarteOffre", 
             bot.getOffre()[0] != null && bot.getOffre()[1] != null);
        
        // Test ajout au Jest
        Carte c3 = new Carte(Valeur.TROIS, Couleur.TREFLE);
        bot.ajouterAuJest(c3);
        test("Jest contient 1 carte", bot.getJest().size() == 1);
        
        System.out.println();
    }
    
    // ==================== TESTS DES STRATÉGIES ====================
    
    private static void testStrategies() {
        System.out.println("=== TEST DES STRATÉGIES ===\n");
        
        // Test stratégie offensive (montre la carte forte)
        JoueurVirtuel botOffensif = new JoueurVirtuel("Offensif", new StrategieOffensive());
        botOffensif.ramasserCarte(new Carte(Valeur.AS, Couleur.COEUR));      // Faible
        botOffensif.ramasserCarte(new Carte(Valeur.QUATRE, Couleur.PIQUE)); // Fort
        botOffensif.faireOffre();
        
        CarteOffre visibleOff = botOffensif.getCarteVisibleDeLOffre();
        test("Stratégie offensive montre carte forte (4 de Pique)", 
             visibleOff != null && visibleOff.getValeur() == Valeur.QUATRE);
        
        // Test stratégie défensive (cache la carte forte)
        JoueurVirtuel botDefensif = new JoueurVirtuel("Défensif", new StrategieDefensive());
        botDefensif.ramasserCarte(new Carte(Valeur.AS, Couleur.COEUR));      // Faible
        botDefensif.ramasserCarte(new Carte(Valeur.QUATRE, Couleur.PIQUE)); // Fort
        botDefensif.faireOffre();
        
        CarteOffre visibleDef = botDefensif.getCarteVisibleDeLOffre();
        test("Stratégie défensive montre carte faible (As de Coeur)", 
             visibleDef != null && visibleDef.getValeur() == Valeur.AS);
        
        System.out.println();
    }
    
    // ==================== TESTS DES VARIANTES ====================
    
    private static void testVariantes() {
        System.out.println("=== TEST DES VARIANTES ===\n");
        
        // Variante Classique
        Variante classique = new VarianteClassique();
        test("Variante Classique - nom", classique.getNom().equals("Classique"));
        test("Variante Classique - 2 trophées pour 3 joueurs", classique.getNombreTrophees(3) == 2);
        test("Variante Classique - 1 trophée pour 4 joueurs", classique.getNombreTrophees(4) == 1);
        
        // Variante Sans Trophée
        Variante sansTrophee = new VarianteSansTrophee();
        test("Variante Sans Trophée - nom", sansTrophee.getNom().equals("Sans Trophée"));
        test("Variante Sans Trophée - 0 trophées", sansTrophee.getNombreTrophees(3) == 0);
        
        // Variante Double Mise
        Variante doubleMise = new VarianteDoubleMise();
        test("Variante Double Mise - nom", doubleMise.getNom().equals("Double Mise"));
        test("Variante Double Mise - 3 trophées", doubleMise.getNombreTrophees(3) == 3);
        
        System.out.println();
    }
    
    // ==================== TESTS DES EXTENSIONS ====================
    
    private static void testExtensions() {
        System.out.println("=== TEST DES EXTENSIONS ===\n");
        
        Extension ext = new ExtensionCartesSpeciales();
        
        test("Extension nom", ext.getNom().equals("Cartes Spéciales"));
        test("Extension inactive par défaut", !ext.estActive());
        
        ext.setActive(true);
        test("Extension activée", ext.estActive());
        
        List<Carte> cartes = ext.getCartesExtension();
        test("Extension contient 2 cartes", cartes.size() == 2);
        
        // Vérifier que ce sont des CarteExtension
        boolean toutesExtension = true;
        for (Carte c : cartes) {
            if (!(c instanceof CarteExtension)) toutesExtension = false;
        }
        test("Cartes sont des CarteExtension", toutesExtension);
        
        System.out.println();
    }
    
    // ==================== TESTS DU VISITOR SCORE ====================
    
    private static void testVisitorScore() {
        System.out.println("=== TEST DU CALCUL DE SCORE (VISITOR) ===\n");
        
        VisitorScore visitor = new VisitorScore();
        
        // Test 1: Pique et Trèfle positifs
        JoueurVirtuel j1 = new JoueurVirtuel("Test1", new StrategieOffensive());
        j1.ajouterAuJest(new Carte(Valeur.QUATRE, Couleur.PIQUE));  // +4
        j1.ajouterAuJest(new Carte(Valeur.TROIS, Couleur.TREFLE)); // +3
        visitor.visit(j1);
        test("Score Pique+Trèfle = 7", j1.getScore() == 7);
        
        // Test 2: Carreau négatif
        JoueurVirtuel j2 = new JoueurVirtuel("Test2", new StrategieOffensive());
        j2.ajouterAuJest(new Carte(Valeur.QUATRE, Couleur.PIQUE));   // +4
        j2.ajouterAuJest(new Carte(Valeur.TROIS, Couleur.CARREAU)); // -3
        visitor.visit(j2);
        test("Score Pique-Carreau = 1", j2.getScore() == 1);
        
        // Test 3: Coeur vaut 0 sans Joker
        JoueurVirtuel j3 = new JoueurVirtuel("Test3", new StrategieOffensive());
        j3.ajouterAuJest(new Carte(Valeur.QUATRE, Couleur.COEUR)); // 0
        visitor.visit(j3);
        test("Score Coeur sans Joker = 0", j3.getScore() == 0);
        
        // Test 4: Joker sans Coeur = +4
        JoueurVirtuel j4 = new JoueurVirtuel("Test4", new StrategieOffensive());
        j4.ajouterAuJest(new Carte(Valeur.JOKER, null)); // +4
        visitor.visit(j4);
        test("Score Joker sans Coeur = 4", j4.getScore() == 4);
        
        // Test 5: As seul de sa couleur = 5
        JoueurVirtuel j5 = new JoueurVirtuel("Test5", new StrategieOffensive());
        j5.ajouterAuJest(new Carte(Valeur.AS, Couleur.PIQUE)); // +5 (seul Pique)
        visitor.visit(j5);
        test("Score As seul = 5", j5.getScore() == 5);
        
        // Test 6: Paire noire bonus +2
        JoueurVirtuel j6 = new JoueurVirtuel("Test6", new StrategieOffensive());
        j6.ajouterAuJest(new Carte(Valeur.TROIS, Couleur.PIQUE));  // +3
        j6.ajouterAuJest(new Carte(Valeur.TROIS, Couleur.TREFLE)); // +3 + bonus 2
        visitor.visit(j6);
        test("Score Paire noire = 8 (3+3+2)", j6.getScore() == 8);
        
        System.out.println();
    }
    
    // ==================== TESTS DE LA SAUVEGARDE ====================
    
    private static void testSauvegarde() {
        System.out.println("=== TEST DE LA SAUVEGARDE ===\n");
        
        // Créer un état de partie
        EtatPartie etat = new EtatPartie();
        etat.setNumeroManche(3);
        etat.setNomVariante("Double Mise");
        
        ArrayList<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new JoueurVirtuel("Bot1", new StrategieOffensive()));
        etat.setJoueurs(joueurs);
        
        // Sauvegarder
        String nomTest = "test_sauvegarde_" + System.currentTimeMillis();
        boolean sauvegarde = GestionnaireSauvegarde.sauvegarder(etat, nomTest);
        test("Sauvegarde réussie", sauvegarde);
        
        // Vérifier existence
        test("Fichier existe", GestionnaireSauvegarde.sauvegardeExiste(nomTest));
        
        // Charger
        Object obj = GestionnaireSauvegarde.charger(nomTest);
        test("Chargement réussi", obj != null);
        test("Objet est EtatPartie", obj instanceof EtatPartie);
        
        if (obj instanceof EtatPartie) {
            EtatPartie charge = (EtatPartie) obj;
            test("Numéro manche restauré", charge.getNumeroManche() == 3);
            test("Variante restaurée", charge.getNomVariante().equals("Double Mise"));
            test("Joueurs restaurés", charge.getJoueurs().size() == 1);
        }
        
        // Lister sauvegardes
        String[] liste = GestionnaireSauvegarde.listerSauvegardes();
        test("Liste sauvegardes non vide", liste.length > 0);
        
        System.out.println();
    }
    
    // ==================== UTILITAIRES ====================
    
    private static void test(String description, boolean resultat) {
        if (resultat) {
            System.out.println("  ✓ " + description);
            testsReussis++;
        } else {
            System.out.println("  ✗ " + description + " [ÉCHEC]");
            testsEchoues++;
        }
    }
    
    private static void afficherResume() {
        int total = testsReussis + testsEchoues;
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    RÉSUMÉ DES TESTS                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  Tests réussis  : %3d / %3d                              ║%n", testsReussis, total);
        System.out.printf("║  Tests échoués  : %3d / %3d                              ║%n", testsEchoues, total);
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        
        if (testsEchoues == 0) {
            System.out.println("║  ★★★ TOUS LES TESTS SONT PASSÉS ! ★★★                    ║");
        } else {
            System.out.println("║  ⚠ Certains tests ont échoué                             ║");
        }
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
