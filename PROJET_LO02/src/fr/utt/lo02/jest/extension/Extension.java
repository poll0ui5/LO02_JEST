package fr.utt.lo02.jest.extension;

import fr.utt.lo02.jest.model.Carte;
import java.util.List;

/**
 * Interface définissant une extension de cartes pour le jeu Jest.
 * <p>
 * Une extension ajoute de nouvelles cartes avec des effets spéciaux
 * au jeu de base.
 * </p>
 * @author Projet LO02
 * @version 1.0
 */
public interface Extension {
    
    /**
     * Retourne le nom de l'extension.
     * @return Le nom affiché au joueur.
     */
    String getNom();
    
    /**
     * Retourne la description de l'extension.
     * @return La description des cartes ajoutées.
     */
    String getDescription();
    
    /**
     * Retourne les cartes ajoutées par cette extension.
     * @return La liste des nouvelles cartes.
     */
    List<Carte> getCartesExtension();
    
    /**
     * Indique si l'extension est activée.
     * @return true si l'extension est active.
     */
    boolean estActive();
    
    /**
     * Active ou désactive l'extension.
     * @param active true pour activer.
     */
    void setActive(boolean active);
}
