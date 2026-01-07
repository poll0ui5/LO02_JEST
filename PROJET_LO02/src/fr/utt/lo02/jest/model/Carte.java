package fr.utt.lo02.jest.model;

import java.io.Serializable;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Représente une carte du jeu Jest avec valeur, couleur et visibilité.
 * <p>
 * Une carte possède :
 * <ul>
 * <li>Une valeur (1 à 4, ou Joker)</li>
 * <li>Une couleur (Pique, Trèfle, Carreau, Cœur)</li>
 * <li>Un état de visibilité (face visible ou cachée)</li>
 * </ul>
 * Les cartes peuvent être comparées selon leur valeur et couleur.
 * </p>
 * 
 * @see Valeur
 * @see Couleur
 * @author LO02 Project
 */
public class Carte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Couleur couleur;
    private Valeur valeur;
    private boolean faceVisible;

    /**
     * Constructeur d'une carte.
     * <p>
     * La carte est créée face cachée par défaut.
     * </p>
     * 
     * @param valeur La valeur de la carte
     * @param couleur La couleur de la carte
     */
    public Carte(Valeur valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
        this.faceVisible = false;
    }

    /**
     * @return La couleur de la carte
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * @return La valeur de la carte
     */
    public Valeur getValeur() {
        return valeur;
    }

    /**
     * @return true si la carte est face visible, false sinon
     */
    public boolean estFaceVisible() {
        return faceVisible;
    }

    /**
     * Rend la carte visible.
     */
    public void show() {
        this.faceVisible = true;
    }

    /**
     * Cache la carte.
     */
    public void hide() {
        this.faceVisible = false;
    }

    /**
     * Définit la visibilité de la carte.
     * 
     * @param visible true pour rendre visible, false pour cacher
     */
    public void setFaceVisible(boolean visible) {
        this.faceVisible = visible;
    }

    /**
     * Compare deux cartes pour déterminer laquelle est supérieure.
     * <p>
     * Ordre de comparaison :
     * <ol>
     * <li>Valeur faciale (4 > 3 > 2 > 1)</li>
     * <li>En cas d'égalité, couleur (Pique > Trèfle > Carreau > Cœur)</li>
     * </ol>
     * </p>
     * 
     * @param c2 La carte à comparer
     * @return true si cette carte est supérieure à c2, false sinon
     */
    public boolean estSuperieureA(Carte c2) {
        if (c2 == null)
            return true;
        if (this.valeur.getValeurFaciale() > c2.valeur.getValeurFaciale())
            return true;
        if (this.valeur.getValeurFaciale() < c2.valeur.getValeurFaciale())
            return false;
        if (this.couleur == null)
            return false;
        if (c2.couleur == null)
            return true;
        return this.couleur.ordinal() > c2.couleur.ordinal();
    }

    /**
     * Vérifie si la carte est un Joker.
     * 
     * @return true si c'est un Joker, false sinon
     */
    public boolean estJoker() {
        return this.valeur == Valeur.JOKER;
    }

    /**
     * Accepte un visiteur (pattern Visitor).
     * 
     * @param visitor Le visiteur à accepter
     */
    public void accept(Visitor visitor) {
    }

    @Override
    public String toString() {
        return estJoker() ? "Joker" : valeur + " de " + couleur;
    }
}