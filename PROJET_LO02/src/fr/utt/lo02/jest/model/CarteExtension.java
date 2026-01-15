package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.model.Couleur;

/**
 * Carte spéciale avec effets bonus (As Doré, Cœur Maudit).
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
public class CarteExtension extends Carte {
    
    /** Le nom spécial/convivial de la carte (ex: "As Doré") */
    private String nomSpecial;
    
    /** Description de l'effet spécial appliqué par cette carte */
    private String effetDescription;
    
    /**
     * Constructeur d'une carte d'extension.
     * <p>
     * Crée une carte avec valeur, couleur et des attributs spéciaux.
     * </p>
     * 
     * @param valeur La valeur de la carte
     * @param couleur La couleur de la carte
     * @param nomSpecial Le nom convivial de cette carte spéciale
     * @param effetDescription La description de son effet spécial
     */
    public CarteExtension(Valeur valeur, Couleur couleur, String nomSpecial, String effetDescription) {
        super(valeur, couleur);
        this.nomSpecial = nomSpecial;
        this.effetDescription = effetDescription;
    }
    
    /**
     * Retourne le nom spécial de la carte.
     * 
     * @return Le nom convivial (ex: "As Doré")
     */
    public String getNomSpecial() {
        return nomSpecial;
    }
    
    /**
     * Retourne la description de l'effet spécial.
     * 
     * @return La description textuelle de l'effet
     */
    public String getEffetDescription() {
        return effetDescription;
    }
    
    /**
     * Vérifie si cette carte est une carte d'extension.
     * <p>
     * Utile pour différencier les cartes standard des cartes avec effets.
     * </p>
     * 
     * @return true (toujours true pour une CarteExtension)
     */
    public boolean estCarteExtension() {
        return true;
    }
    
    /**
     * Retourne une représentation textuelle avec le nom spécial.
     * 
     * @return String contenant le nom spécial et les informations de base
     */
    @Override
    public String toString() {
        return nomSpecial + " (" + super.toString() + ")";
    }
}
