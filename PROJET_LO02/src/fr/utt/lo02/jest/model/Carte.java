package fr.utt.lo02.jest.model;

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
public class Carte {
    
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
        // 1. Comparaison des valeurs faciales
        if (this.valeur.getValeurFaciale() > c2.valeur.getValeurFaciale()) {
            return true;
        } else if (this.valeur.getValeurFaciale() < c2.valeur.getValeurFaciale()) {
            return false;
        } else {
            // 2. En cas d'égalité (Tie-break), comparaison des couleurs
            // On utilise l'ordinal de l'enum ou une méthode dédiée
            // Supposons l'ordre enum: COEUR(0), CARREAU(1), TREFLE(2), PIQUE(3)
            return this.couleur.ordinal() > c2.couleur.ordinal();
        }
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
        // PLUS DE CONDITION IF ICI !
        return valeur + " de " + couleur;
    }
}