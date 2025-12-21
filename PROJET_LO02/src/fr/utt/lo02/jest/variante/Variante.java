package fr.utt.lo02.jest.variante;

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
     * @return Le nom affiché au joueur.
     */
    String getNom();
    
    /**
     * Retourne la description des règles modifiées.
     * @return La description de la variante.
     */
    String getDescription();
    
    /**
     * Modifie le nombre de trophées selon la variante.
     * @param nbJoueurs Le nombre de joueurs dans la partie.
     * @return Le nombre de trophées à distribuer.
     */
    int getNombreTrophees(int nbJoueurs);
    
    /**
     * Modifie le nombre de cartes distribuées par tour.
     * @return Le nombre de cartes par joueur par tour.
     */
    int getCartesParTour();
    
    /**
     * Indique si les offres sont simultanées ou séquentielles.
     * @return true si les offres sont simultanées (règle de base).
     */
    boolean offresSimultanees();
    
    /**
     * Applique des règles spéciales en fin de partie (bonus/malus).
     * @param joueurs La liste des joueurs.
     */
    void appliquerReglesFinales(List<Joueur> joueurs);
}
