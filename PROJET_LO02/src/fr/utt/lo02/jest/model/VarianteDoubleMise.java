package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Couleur;
import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Variante "Double Mise" du jeu Jest.
 * <p>
 * Règles modifiées :
 * <ul>
 * <li>3 trophées pour tous (plus de cartes en jeu comme bonus)</li>
 * <li>Bonus de fin : +3 points pour le joueur avec le plus de Piques</li>
 * <li>Malus de fin : -3 points pour le joueur avec le plus de Carreaux</li>
 * </ul>
 * </p>
 * 
 * 
 */
public class VarianteDoubleMise implements Variante {

    @Override
    public String getNom() {
        return "Double Mise";
    }

    @Override
    public String getDescription() {
        return "3 trophées. Bonus +3 pour majorité Piques, Malus -3 pour majorité Carreaux.";
    }

    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return 3; // Plus de trophées
    }

    @Override
    public int getCartesParTour() {
        return 2;
    }

    @Override
    public boolean offresSimultanees() {
        return true;
    }

    @Override
    public void appliquerReglesFinales(List<Joueur> joueurs) {
        // Trouver le joueur avec le plus de Piques
        Joueur maxPiques = null;
        int maxCountPiques = 0;
        
        // Trouver le joueur avec le plus de Carreaux
        Joueur maxCarreaux = null;
        int maxCountCarreaux = 0;
        
        for (Joueur j : joueurs) {
            int countPiques = 0;
            int countCarreaux = 0;
            
            for (Carte c : j.getJest()) {
                if (c.getCouleur() == Couleur.PIQUE) countPiques++;
                if (c.getCouleur() == Couleur.CARREAU) countCarreaux++;
            }
            
            if (countPiques > maxCountPiques) {
                maxCountPiques = countPiques;
                maxPiques = j;
            }
            if (countCarreaux > maxCountCarreaux) {
                maxCountCarreaux = countCarreaux;
                maxCarreaux = j;
            }
        }
        
        // Appliquer bonus/malus
        if (maxPiques != null && maxCountPiques > 0) {
            maxPiques.setScore(maxPiques.getScore() + 3);
            System.out.println("Bonus Double Mise : " + maxPiques.getNom() + " gagne +3 (majorité Piques)");
        }
        if (maxCarreaux != null && maxCountCarreaux > 0) {
            maxCarreaux.setScore(maxCarreaux.getScore() - 3);
            System.out.println("Malus Double Mise : " + maxCarreaux.getNom() + " perd -3 (majorité Carreaux)");
        }
    }
}
