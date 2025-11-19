package fr.utt.lo02.jest.model;

/**
 * Joueur virtuel (bot) qui utilise une stratégie de jeu.
 * Hérite de Joueur et délègue son comportement de jeu à une stratégie.
 * 
 * La stratégie peut être changée dynamiquement via setStrategie().
 * 
 * Pattern de conception : Strategy (contexte)
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class JoueurVirtuel extends Joueur {
    
    private Strategie botStrategie;
    
    /**
     * Constructeur d'un joueur virtuel
     * @param nom Le nom du joueur virtuel
     * @param strategie La stratégie de jeu à utiliser
     */
    public JoueurVirtuel(String nom, Strategie strategie) {
        super(nom);
        this.botStrategie = strategie;
    }
    
    /**
     * Le joueur virtuel joue selon sa stratégie
     */
    @Override
    public Carte jouerCarte() {
        if (botStrategie != null) {
            botStrategie.jouer(this);
            return getDerniereCarteJouee();
        } else {
            // Si pas de stratégie définie, comportement par défaut
            return super.jouerCarte();
        }
    }
    
    /**
     * Permet de changer la stratégie du joueur virtuel en cours de jeu
     * @param strategie La nouvelle stratégie
     */
    public void setStrategie(Strategie strategie) {
        this.botStrategie = strategie;
    }
    
    /**
     * Récupère la stratégie actuelle
     * @return La stratégie du bot
     */
    public Strategie getStrategie() {
        return this.botStrategie;
    }
}
