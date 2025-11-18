package fr.utt.lo02.jest.model;

/**
 * Classe représentant un trophée dans le jeu Jest
 * Un trophée a une carte associée et des conditions pour être gagné
 */
public class Trophee {
    
    private Carte carte;
    private String condition;
    private boolean estGagne;
    private boolean estVisible;
    
    /**
     * Constructeur d'un trophée
     * @param carte La carte associée au trophée
     */
    public Trophee(Carte carte) {
        this.carte = carte;
        this.estGagne = false;
        this.estVisible = true;
        this.condition = "Condition par défaut";
    }
    
    /**
     * Constructeur complet d'un trophée
     * @param carte La carte associée au trophée
     * @param condition La condition pour gagner le trophée
     */
    public Trophee(Carte carte, String condition) {
        this.carte = carte;
        this.condition = condition;
        this.estGagne = false;
        this.estVisible = true;
    }
    
    /**
     * Vérifie si la condition du trophée est remplie
     * @param joueur Le joueur à vérifier
     * @return true si le joueur remplit la condition
     */
    public boolean verifierCondition(Joueur joueur) {
        // À implémenter selon les règles spécifiques du jeu
        // Exemple : vérifier si le joueur a certaines cartes
        return false;
    }
    
    /**
     * Méthode accept du pattern Visitor
     * @param visitor Le visiteur qui va traiter ce trophée
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
