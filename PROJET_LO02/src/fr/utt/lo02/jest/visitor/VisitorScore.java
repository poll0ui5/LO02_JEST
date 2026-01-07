package fr.utt.lo02.jest.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.utt.lo02.jest.model.*;

/**
 * Visiteur calculant le score d'un joueur selon les règles du Jest (pattern Visitor).
 * <p>
 * Règles de calcul :
 * <ul>
 * <li><b>Pique et Trèfle</b> : +valeur de la carte</li>
 * <li><b>Carreau</b> : -valeur de la carte</li>
 * <li><b>Cœur</b> : 0 points (sauf si Joker présent)</li>
 * <li><b>As seul</b> : 5 points au lieu de 1</li>
 * <li><b>Joker seul</b> (sans Cœur) : +4 points</li>
 * <li><b>Joker avec Cœurs</b> : inverse les Cœurs (+ au lieu de 0)</li>
 * <li><b>Paires noires</b> : +2 points par paire (Pique + Trèfle de même valeur)</li>
 * </ul>
 * </p>
 * 
 * @see Visitor
 * @see Joueur
 * @author LO02 Project
 */
public class VisitorScore implements Visitor {
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
     * 
     * @param carte La carte visitée
     */
    @Override
    public void visit(Carte carte) {
    }

    /**
     * Retourne la map des scores calculés.
     * 
     * @return Map associant chaque nom de joueur à son score
     */
    public Map<String, Integer> getScores() {
        return scores;
    }
}