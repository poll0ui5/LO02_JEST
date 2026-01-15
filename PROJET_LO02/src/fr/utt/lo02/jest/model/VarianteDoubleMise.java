package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Couleur;
import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Mode avec bonus/malus doublés sur certaines cartes spécifiques.
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
public class VarianteDoubleMise implements Variante {

    /**
     * Retourne le nom de la variante.
     * 
     * @return "Double Mise"
     */
    @Override
    public String getNom() {
        return "Double Mise";
    }

    /**
     * Retourne la description des règles modifiées.
     * 
     * @return Description textuelle de la variante
     */
    @Override
    public String getDescription() {
        return "3 trophées. Bonus +3 pour majorité Piques, Malus -3 pour majorité Carreaux.";
    }

    /**
     * Détermine le nombre de trophées pour cette variante.
     * 
     * @param nbJoueurs Le nombre de joueurs (non utilisé ici)
     * @return Toujours 3 trophées
     */
    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return 3; // Plus de trophées
    }

    /**
     * Retourne le nombre de cartes distribuées par tour.
     * 
     * @return 2 cartes par tour
     */
    @Override
    public int getCartesParTour() {
        return 2;
    }

    /**
     * Indique si les offres sont faites simultanément.
     * 
     * @return true
     */
    @Override
    public boolean offresSimultanees() {
        return true;
    }

    /**
     * Applique les règles finales : bonus/malus sur Piques/Carreaux.
     * <p>
     * Ajoute +3 au joueur avec le plus de Piques.
     * Soustrait -3 au joueur avec le plus de Carreaux.
     * </p>
     * 
     * @param joueurs Liste de tous les joueurs
     */
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
