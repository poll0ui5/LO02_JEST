package fr.utt.lo02.jest.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.utt.lo02.jest.model.*;

/**
 * Implémentation du calcul de score selon les règles du Jest.
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
public class VisitorScore implements Visitor {
    
    /** Map stockant les scores calculés pour chaque joueur */
    private Map<String, Integer> scores = new HashMap<>();

    /**
     * Calcule et attribue le score au joueur visité.
     * <p>
     * Analyse le Jest du joueur et applique toutes les règles de scoring.
     * Le score est ensuite stocké dans le joueur et dans une map interne.
     * </p>
     * 
     * @param joueur Le joueur dont on calcule le score
     */
    @Override
    public void visit(Joueur joueur) {
        List<Carte> jest = joueur.getJest();
        int score = 0;

        boolean aJoker = false;
        int nbCoeurs = 0;
        Map<Couleur, Integer> compteursCouleur = new HashMap<>();
        for (Couleur c : Couleur.values())
            compteursCouleur.put(c, 0);
        Map<Integer, int[]> pairesNoires = new HashMap<>();

        for (Carte c : jest) {
            if (c.getValeur() == Valeur.JOKER)
                aJoker = true;
            if (c.getCouleur() == Couleur.COEUR && c.getValeur() != Valeur.JOKER)
                nbCoeurs++;
            if (c.getValeur() != Valeur.JOKER && c.getCouleur() != null) {
                compteursCouleur.put(c.getCouleur(), compteursCouleur.get(c.getCouleur()) + 1);
            }
            if (c.getCouleur() == Couleur.PIQUE || c.getCouleur() == Couleur.TREFLE) {
                int val = c.getValeur().getValeurFaciale();
                pairesNoires.putIfAbsent(val, new int[] { 0, 0 });
                if (c.getCouleur() == Couleur.PIQUE)
                    pairesNoires.get(val)[0]++;
                else
                    pairesNoires.get(val)[1]++;
            }
        }

        for (Carte c : jest) {
            if (c.getValeur() == Valeur.JOKER || c.getCouleur() == null) continue;
            int valeurCarte = c.getValeur().getValeurFaciale();
            if (c.getValeur() == Valeur.AS && compteursCouleur.get(c.getCouleur()) == 1)
                valeurCarte = 5;

            switch (c.getCouleur()) {
                case PIQUE:
                case TREFLE:
                    score += valeurCarte;
                    break;
                case CARREAU:
                    score -= valeurCarte;
                    break;
                case COEUR:
                    if (aJoker)
                        score -= (nbCoeurs == 4) ? -valeurCarte : valeurCarte;
                    break;
            }
        }

        if (aJoker && nbCoeurs == 0)
            score += 4;
        for (int[] counts : pairesNoires.values()) {
            if (counts[0] > 0 && counts[1] > 0)
                score += Math.min(counts[0], counts[1]) * 2;
        }

        joueur.setScore(score);
        scores.put(joueur.getNom(), score);
        System.out.println("Score " + joueur.getNom() + " : " + score);
    }

    /**
     * Visite une carte (non utilisé dans ce visiteur).
     * <p>
     * Cette méthode est présente pour implémenter l'interface Visitor
     * mais n'effectue aucun traitement sur les cartes.
     * </p>
     * 
     * @param carte La carte visitée
     */
    @Override
    public void visit(Carte carte) {
    }

    /**
     * Visite un trophée (non utilisé dans ce visiteur).
     * <p>
     * Cette méthode est présente pour implémenter l'interface Visitor
     * mais n'effectue aucun traitement sur les trophées.
     * </p>
     * 
     * @param trophee Le trophée visité
     */
    @Override
    public void visit(Trophee trophee) {
    }

    /**
     * Retourne la map des scores calculés.
     * <p>
     * Contient les scores de tous les joueurs visités jusqu'à présent.
     * </p>
     * 
     * @return Map associant chaque nom de joueur à son score
     */
    public Map<String, Integer> getScores() {
        return scores;
    }
}