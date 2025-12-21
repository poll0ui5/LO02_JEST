package fr.utt.lo02.jest.extension;

import fr.utt.lo02.jest.model.Carte;
import java.util.List;

/**
 * Interface définissant une extension de cartes pour le jeu Jest.
 * <p>
 * Une extension ajoute de nouvelles cartes avec des effets spéciaux
 * au jeu de base.
 * </p>
 * 
 * 
 */
public interface Extension {
    
    /**
     * Retourne le nom de l'extension.
     */
    String getNom();
    
    /**
     * Retourne la description de l'extension.
     */
    String getDescription();
    
    /**
     * Retourne les cartes ajoutées par cette extension.
     */
    List<Carte> getCartesExtension();
    
    /**
     * Indique si l'extension est activée.
     */
    boolean estActive();
    
    /**
     * Active ou désactive l'extension.
     */
    void setActive(boolean active);
}
