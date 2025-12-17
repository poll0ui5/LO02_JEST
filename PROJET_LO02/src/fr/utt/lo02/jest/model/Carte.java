package fr.utt.lo02.jest.model;

import java.io.Serializable;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Classe représentant une carte du jeu Jest.
 * <p>
 * Une carte possède une {@link Valeur}, une {@link Couleur} et un état de visibilité.
 * Elle implémente la logique de comparaison spécifique au Jest (Valeur puis Couleur).
 * </p>
 * @author Projet LO02
 * @version 2.0
 */
public class Carte implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Couleur couleur;
    private Valeur valeur;
    private boolean faceVisible;

    /**
     * Constructeur d'une carte.
     * Par défaut, une carte est distribuée face cachée.
     * @param valeur La valeur faciale (Joker, As, 2, 3, 4)
     * @param couleur La couleur (Pique, Trèfle, Carreau, Cœur)
     */
    public Carte(Valeur valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
        this.faceVisible = false; // Cachée par défaut [cite: 114]
    }

    // --- Getters et Setters ---

    public Couleur getCouleur() {
        return couleur;
    }

    public Valeur getValeur() {
        return valeur;
    }
    
    /**
     * Rend la carte visible (face visible).
     */
    public void show() {
        this.faceVisible = true;
    }

    /**
     * Cache la carte (face cachée).
     */
    public void hide() {
        this.faceVisible = false;
    }
    
    /**
     * Définit directement la visibilité.
     * @param visible true pour visible, false pour caché.
     */
    public void setFaceVisible(boolean visible) {
        this.faceVisible = visible;
    }
    
    public boolean estFaceVisible() {
        return faceVisible;
    }

    /**
     * Compare cette carte à une autre pour déterminer l'ordre de jeu.
     * <p>
     * Règle du Jest : On compare d'abord la valeur faciale.
     * Si les valeurs sont égales, on compare la force de la couleur :
     * Pique > Trèfle > Carreau > Cœur.
     * </p>
     * @param c2 La carte à comparer.
     * @return true si cette carte est plus forte que c2, false sinon.
     * [cite: 125, 126]
     */
    public boolean estSuperieureA(Carte c2) {
        if (c2 == null) return true;
        
        // 1. Comparaison des valeurs faciales
        if (this.valeur.getValeurFaciale() > c2.valeur.getValeurFaciale()) {
            return true;
        } else if (this.valeur.getValeurFaciale() < c2.valeur.getValeurFaciale()) {
            return false;
        } else {
            // 2. En cas d'égalité (Tie-break), comparaison des couleurs
            // Le Joker n'a pas de couleur (null), il perd le tie-break
            if (this.couleur == null) return false;
            if (c2.couleur == null) return true;
            return this.couleur.ordinal() > c2.couleur.ordinal();
        }
    }
    
    /**
     * Vérifie si cette carte est le Joker.
     * @return true si c'est le Joker.
     */
    public boolean estJoker() {
        return this.valeur == Valeur.JOKER;
    }

    /**
     * Méthode accept du pattern Visitor.
     * Permet à un visiteur (calculateur de score) de traiter cette carte.
     */
    public void accept(Visitor visitor) {
        // Note: Le visiteur visite généralement le Joueur, mais visiter la carte peut servir
        // pour des affichages ou des inspections spécifiques.
        // Si ton interface Visitor n'a pas visit(Carte), tu peux commenter cette ligne.
        // visitor.visit(this); 
    }   

    @Override
    public String toString() {
        if (this.valeur == Valeur.JOKER) {
            return "Joker";
        }
        return valeur + " de " + couleur;
    }
}