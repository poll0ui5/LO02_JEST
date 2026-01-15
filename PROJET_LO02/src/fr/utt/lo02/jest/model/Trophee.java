package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Visitor;

/**
 * Gère les trophées attribués au meilleur joueur en fin de partie.
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
public class Trophee {
    
    /** La carte représentant ce trophée */
    private Carte carte;
    
    /** Condition pour obtenir ce trophée */
    private String condition;
    
    /** État du trophée : true si gagné par un joueur */
    private boolean estGagne;
    
    /** Visibilité du trophée : true si visible par tous */
    private boolean estVisible;
    
    /**
     * Constructeur d'un trophée
     */
    public Trophee(Carte carte) {
        this.carte = carte;
        this.estGagne = false;
        this.estVisible = true;
        this.condition = "Condition par défaut";
    }
    
    /**
     * Constructeur complet d'un trophée
     */
    public Trophee(Carte carte, String condition) {
        this.carte = carte;
        this.condition = condition;
        this.estGagne = false;
        this.estVisible = true;
    }
    
    /**
     * Vérifie si la condition du trophée est remplie pour un joueur.
     * <p>
     * Examine le Jest du joueur pour vérifier si la condition
     * de ce trophée est satisfaite.
     * </p>
     * 
     * @param joueur Le joueur à vérifier
     * @return true si le joueur satisfait les conditions du trophée, false sinon
     */
    public boolean verifierCondition(Joueur joueur) {
        // À implémenter selon les règles spécifiques du jeu
        // Exemple : vérifier si le joueur a certaines cartes
        return false;
    }
    
    /**
     * Accepte un visiteur (pattern Visitor).
     * <p>
     * Permet au visiteur d'accéder au trophée pour effectuer un traitement.
     * </p>
     * 
     * @param visitor Le visiteur à accepter
     */
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    
    // Getters et Setters
    
    public Carte getCarte() {
        return carte;
    }
    
    public void setCarte(Carte carte) {
        this.carte = carte;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public boolean isEstGagne() {
        return estGagne;
    }
    
    public void setEstGagne(boolean estGagne) {
        this.estGagne = estGagne;
    }
    
    public boolean isEstVisible() {
        return estVisible;
    }
    
    public void setEstVisible(boolean estVisible) {
        this.estVisible = estVisible;
    }
    
    @Override
    public String toString() {
        return "Trophée [" + carte + ", condition=" + condition + 
               ", gagné=" + estGagne + ", visible=" + estVisible + "]";
    }
}
