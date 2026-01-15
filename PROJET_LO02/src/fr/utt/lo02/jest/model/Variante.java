package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Interface définissant une variante de règles du jeu Jest.
 * <p>
 * Le pattern Strategy est utilisé pour permettre de changer les règles
 * du jeu dynamiquement au début de la partie.
 * </p>
 * 
 * 
 */
public interface Variante {
    
    /**
     * Retourne le nom de la variante.
     */
    String getNom();
    
    /**
     * Retourne la description des règles modifiées.
     */
    String getDescription();
    
    /**
     * Modifie le nombre de trophées selon la variante.
     */
    int getNombreTrophees(int nbJoueurs);
    
    /**
     * Modifie le nombre de cartes distribuées par tour.
     */
    int getCartesParTour();
    
    /**
     * Indique si les offres sont simultanées ou séquentielles.
     */
    boolean offresSimultanees();
    
    /**
     * Applique des règles spéciales en fin de partie (bonus/malus).
     */
    void appliquerReglesFinales(List<Joueur> joueurs);
}
