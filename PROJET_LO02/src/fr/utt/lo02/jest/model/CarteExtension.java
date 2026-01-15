package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.model.Couleur;

/**
 * Carte spéciale provenant d'une extension.
 * <p>
 * Étend la classe Carte de base avec un nom et une description
 * pour les effets spéciaux.
 * </p>
 * 
 * 
 */
public class CarteExtension extends Carte {
    
    private String nomSpecial;
    private String effetDescription;
    
    /**
     * Constructeur d'une carte d'extension.
     */
    public CarteExtension(Valeur valeur, Couleur couleur, String nomSpecial, String effetDescription) {
        super(valeur, couleur);
        this.nomSpecial = nomSpecial;
        this.effetDescription = effetDescription;
    }
    
    public String getNomSpecial() {
        return nomSpecial;
    }
    
    public String getEffetDescription() {
        return effetDescription;
    }
    
    /**
     * Vérifie si cette carte est une carte d'extension.
     */
    public boolean estCarteExtension() {
        return true;
    }
    
    @Override
    public String toString() {
        return nomSpecial + " (" + super.toString() + ")";
    }
}
