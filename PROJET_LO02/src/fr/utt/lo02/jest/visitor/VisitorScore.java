package fr.utt.lo02.jest.visitor;

import java.util.HashMap;
import java.util.Map;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.Trophee;

/**
 * Visiteur concret pour calculer les scores des joueurs.
 * Implémente les règles de scoring du jeu Jest.
 * 
 * Parcourt les cartes d'un joueur et calcule son score total.
 * Les scores sont stockés dans une Map pour consultation ultérieure.
 * 
 * Pattern de conception : Visitor (implémentation concrète)
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class VisitorScore implements Visitor {
    
    private int scoreActuel;
    private Map<String, Integer> scoresJoueurs;
    
    public VisitorScore() {
        this.scoreActuel = 0;
        this.scoresJoueurs = new HashMap<>();
    }
    
    /**
     * Calcule le score d'un joueur en fonction de ses cartes
     * @param joueur Le joueur dont on calcule le score
     */
    @Override
    public void visit(Joueur joueur) {
        scoreActuel = 0;
        
        // Parcourt toutes les cartes du joueur
        for (Carte carte : joueur.getMain()) {
            visit(carte);
        }
        
        // Sauvegarde le score du joueur
        scoresJoueurs.put(joueur.getNom(), scoreActuel);
        
        System.out.println("Score de " + joueur.getNom() + " : " + scoreActuel + " points");
    }
    
    /**
     * Calcule les points d'une carte selon les règles du jeu Jest
     * @param carte La carte à évaluer
     */
    @Override
    public void visit(Carte carte) {
        // Règles de base du jeu Jest (à adapter selon vos règles exactes)
        switch (carte.getValeur()) {
            case DEUX:
                scoreActuel += 7;
                break;
            case TROIS:
                scoreActuel += 8;
                break;
            case QUATRE:
                scoreActuel += 9;
                break;
            case AS:
                scoreActuel += 1;
                break;
            default:
                break;
        }
    }
    
    /**
     * Traite un trophée (peut modifier le score selon les règles)
     * @param trophee Le trophée à traiter
     */
    @Override
    public void visit(Trophee trophee) {
        // Implémentation selon les règles des trophées
        // À compléter selon les règles spécifiques du jeu
    }
    
    /**
     * Récupère le score actuel calculé
     * @return Le score actuel
     */
    public int getScoreActuel() {
        return scoreActuel;
    }
    
    /**
     * Récupère tous les scores calculés
     * @return Map des scores par nom de joueur
     */
    public Map<String, Integer> getScoresJoueurs() {
        return scoresJoueurs;
    }
    
    /**
     * Récupère le score d'un joueur spécifique
     * @param nomJoueur Le nom du joueur
     * @return Le score du joueur, ou 0 s'il n'existe pas
     */
    public int getScore(String nomJoueur) {
        return scoresJoueurs.getOrDefault(nomJoueur, 0);
    }
}
