package fr.utt.lo02.jest.model;

import java.io.Serializable;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Représente une carte du jeu Jest avec valeur, couleur et visibilité.
 * 
 * 
 */
public class Carte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Couleur couleur;
    private Valeur valeur;
    private boolean faceVisible;

    public Carte(Valeur valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
        this.faceVisible = false;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public Valeur getValeur() {
        return valeur;
    }

    public boolean estFaceVisible() {
        return faceVisible;
    }

    public void show() {
        this.faceVisible = true;
    }

    public void hide() {
        this.faceVisible = false;
    }

    public void setFaceVisible(boolean visible) {
        this.faceVisible = visible;
    }

    /**
     * Compare deux cartes : valeur d'abord, puis couleur (Pique > Trèfle > Carreau
     * > Cœur).
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

    public boolean estJoker() {
        return this.valeur == Valeur.JOKER;
    }

    public void accept(Visitor visitor) {
    }

    @Override
    public String toString() {
        return estJoker() ? "Joker" : valeur + " de " + couleur;
    }
}