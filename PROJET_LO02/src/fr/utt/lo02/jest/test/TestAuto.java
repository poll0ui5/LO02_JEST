package fr.utt.lo02.jest.test;

import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.strategy.*;
import fr.utt.lo02.jest.variante.*;
import fr.utt.lo02.jest.visitor.VisitorScore;
import java.util.ArrayList;
import java.util.List;

/**
 * Test automatisé du jeu Jest avec bots uniquement.
 */
public class TestAuto {
    
    public static void main(String[] args) {
        System.out.println("=== TEST AUTOMATISÉ DU JEU JEST ===\n");
        
        int testsReussis = 0;
        int testsTotal = 0;
        
        // Test 1: Variante Classique 3 joueurs
        testsTotal++;
        if (testPartieComplete("Classique", 3)) testsReussis++;
        
        // Test 2: Variante Classique 4 joueurs
        testsTotal++;
        if (testPartieComplete("Classique", 4)) testsReussis++;
        
        // Test 3: Variante Sans Trophée 3 joueurs
        testsTotal++;
        if (testPartieComplete("Sans Trophée", 3)) testsReussis++;
        
        // Test 4: Variante Double Mise 4 joueurs
        testsTotal++;
        if (testPartieComplete("Double Mise", 4)) testsReussis++;
        
        System.out.println("\n=== RÉSULTAT FINAL ===");
        System.out.println("Tests réussis: " + testsReussis + "/" + testsTotal);
        
        if (testsReussis == testsTotal) {
            System.out.println("✅ TOUS LES TESTS PASSENT !");
        } else {
            System.out.println("❌ CERTAINS TESTS ONT ÉCHOUÉ");
        }
    }
    
    private static boolean testPartieComplete(String nomVariante, int nbJoueurs) {
        System.out.println("\n--- Test: " + nomVariante + " avec " + nbJoueurs + " joueurs ---");
        
        try {
            // Créer la variante
            Variante variante;
            switch (nomVariante) {
                case "Sans Trophée": variante = new VarianteSansTrophee(); break;
                case "Double Mise": variante = new VarianteDoubleMise(); break;
                default: variante = new VarianteClassique();
            }
            
            // Créer les joueurs (tous bots)
            ArrayList<Joueur> joueurs = new ArrayList<>();
            for (int i = 0; i < nbJoueurs; i++) {
                Strategie strat = (i % 2 == 0) ? new StrategieOffensive() : new StrategieDefensive();
                joueurs.add(new JoueurVirtuel("Bot" + (i+1), strat));
            }
            
            // Créer la pioche
            JeuCartes pioche = new JeuCartes();
            pioche.melanger();
            
            // Distribuer les trophées
            ArrayList<Carte> trophees = new ArrayList<>();
            int nbTrophees = variante.getNombreTrophees(nbJoueurs);
            for (int i = 0; i < nbTrophees && !pioche.estVide(); i++) {
                Carte t = pioche.distribuerUneCarte();
                t.show();
                trophees.add(t);
            }
            System.out.println("Trophées: " + trophees.size());
            
            // Jouer les manches
            int manche = 1;
            while (!pioche.estVide()) {
                // Vérifier qu'il y a assez de cartes
                if (pioche.getTasCartes().size() < nbJoueurs * 2) {
                    System.out.println("Fin: pas assez de cartes pour manche " + manche);
                    break;
                }
                
                System.out.println("Manche " + manche + " (pioche: " + pioche.getTasCartes().size() + ")");
                
                // Distribution
                for (Joueur j : joueurs) {
                    if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
                    if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
                }
                
                // Offres
                for (Joueur j : joueurs) {
                    j.faireOffre();
                }
                
                // Prises
                ArrayList<Joueur> joues = new ArrayList<>();
                Joueur actuel = trouverMeilleureOffre(joueurs);
                
                while (joues.size() < joueurs.size()) {
                    Joueur cible = actuel.choisirAdversaire(joueurs);
                    Carte carte = actuel.prendreCarteDansOffre(cible);
                    if (carte != null) {
                        actuel.ajouterAuJest(carte);
                    }
                    joues.add(actuel);
                    
                    if (!joues.contains(cible)) {
                        actuel = cible;
                    } else {
                        ArrayList<Joueur> restants = new ArrayList<>();
                        for (Joueur j : joueurs) {
                            if (!joues.contains(j)) restants.add(j);
                        }
                        if (!restants.isEmpty()) {
                            actuel = trouverMeilleureOffre(restants);
                        }
                    }
                }
                manche++;
            }
            
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
            
            // Appliquer règles finales
            variante.appliquerReglesFinales(joueurs);
            
            // Afficher résultats
            System.out.println("Résultats:");
            for (Joueur j : joueurs) {
                System.out.println("  " + j.getNom() + ": " + j.getScore() + " pts, Jest: " + j.getJest().size() + " cartes");
            }
            
            System.out.println("✅ Test réussi!");
            return true;
            
        } catch (Exception e) {
            System.out.println("❌ ERREUR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private static Joueur trouverMeilleureOffre(List<Joueur> candidats) {
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
}
