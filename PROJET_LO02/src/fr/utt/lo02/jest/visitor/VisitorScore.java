package fr.utt.lo02.jest.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Couleur;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.Valeur;

/**
 * Visiteur concret implémentant les règles de calcul de points du Jest.
 * <p>
 * Règles implémentées :
 * <ul>
 * <li>Pique et Trèfle : + Valeur faciale[cite: 167].</li>
 * <li>Carreau : - Valeur faciale[cite: 168].</li>
 * <li>Cœur : 0 point, sauf si Joker présent[cite: 169].</li>
 * <li>Joker : +4 si aucun Cœur, transforme les Cœurs en négatif sinon [cite: 171-172].</li>
 * <li>As : Vaut 5 s'il est seul de sa couleur, sinon 1.</li>
 * <li>Paires Noires : Bonus +2 pour paire (Pique, Trèfle) de même rang[cite: 178].</li>
 * </ul>
 * </p>
 */
public class VisitorScore implements Visitor {

    /**
     * Stocke les scores finaux pour consultation.
     */
    private Map<String, Integer> scores;

    public VisitorScore() {
        this.scores = new HashMap<>();
    }

    /**
     * Algorithme principal de calcul des points.
     * Analyse le "Jest" (cartes gagnées) du joueur.
     */
    @Override
    public void visit(Joueur joueur) {
        List<Carte> jest = joueur.getJest(); // ATTENTION : On regarde le Jest, pas la main !
        int score = 0;

        // --- ÉTAPE 1 : ANALYSE DU JEU (Statistiques) ---
        boolean aJoker = false;
        int nbCoeurs = 0;
        
        // Compteurs pour la règle de l'As solitaire (Combien de cartes par couleur ?)
        Map<Couleur, Integer> compteursCouleur = new HashMap<>();
        for (Couleur c : Couleur.values()) compteursCouleur.put(c, 0);

        // Compteurs pour la règle des Paires Noires (Combien de Piques/Trèfles par valeur ?)
        // Format : "VALEUR_FACIALE" -> [nbPiques, nbTrefles]
        Map<Integer, int[]> pairesNoires = new HashMap<>();

        for (Carte c : jest) {
            // Détection Joker
            if (c.getValeur() == Valeur.JOKER) {
                aJoker = true;
            }
            
            // Comptage Cœurs
            if (c.getCouleur() == Couleur.COEUR && c.getValeur() != Valeur.JOKER) {
                nbCoeurs++;
            }
            
            // Mise à jour compteur couleur (pour l'As)
            // Le Joker n'a pas de couleur (null), on l'ignore
            if (c.getValeur() != Valeur.JOKER && c.getCouleur() != null) {
                compteursCouleur.put(c.getCouleur(), compteursCouleur.get(c.getCouleur()) + 1);
            }

            // Préparation détection Paires Noires
            if (c.getCouleur() == Couleur.PIQUE || c.getCouleur() == Couleur.TREFLE) {
                int val = c.getValeur().getValeurFaciale();
                pairesNoires.putIfAbsent(val, new int[]{0, 0}); // index 0=Pique, 1=Trèfle
                
                if (c.getCouleur() == Couleur.PIQUE) pairesNoires.get(val)[0]++;
                else pairesNoires.get(val)[1]++;
            }
        }

        // --- ÉTAPE 2 : CALCUL DES POINTS ---
        
        for (Carte c : jest) {
            if (c.getValeur() == Valeur.JOKER) continue; // Le Joker est traité à la fin

            int valeurCarte = c.getValeur().getValeurFaciale(); // 1, 2, 3, 4
            
            // Règle de l'As: Devient un 5 s'il est seul de sa couleur
            if (c.getValeur() == Valeur.AS) {
                if (compteursCouleur.get(c.getCouleur()) == 1) {
                    valeurCarte = 5;
                }
            }

            // Application des règles de couleur
            switch (c.getCouleur()) {
                case PIQUE:
                case TREFLE:
                    score += valeurCarte; // [cite: 167]
                    break;
                case CARREAU:
                    score -= valeurCarte; // [cite: 168]
                    break;
                case COEUR:
                    // Logique complexe Joker & Cœurs [cite: 170-173]
                    if (!aJoker) {
                        score += 0; // Cœurs valent 0 sans Joker
                    } else {
                        if (nbCoeurs == 4) {
                            score += valeurCarte; // Bonus spécial : tous les cœurs positifs
                        } else {
                            score -= valeurCarte; // Avec Joker, les cœurs sont négatifs
                        }
                    }
                    break;
            }
        }

        // --- ÉTAPE 3 : BONUS ET MALUS GLOBAUX ---

        // Bonus Joker [cite: 171]
        if (aJoker && nbCoeurs == 0) {
            score += 4;
        }

        // Bonus Paires Noires (Black Pairs) [cite: 177-178]
        // Pour chaque rang (2, 3, 4, As...), si on a le Pique ET le Trèfle, +2 points.
        for (int[] counts : pairesNoires.values()) {
            if (counts[0] > 0 && counts[1] > 0) {
                // On a au moins un Pique et un Trèfle de cette valeur
                // Note: Si on a plusieurs paires (ex: 2 jeux de cartes), on compte le min
                 int nbPaires = Math.min(counts[0], counts[1]);
                 score += (nbPaires * 2);
            }
        }

        // Enregistrement du score final
        joueur.setScore(score);
        scores.put(joueur.getNom(), score);
        
        System.out.println("Calcul score pour " + joueur.getNom() + " : " + score);
    }

    @Override
    public void visit(Carte carte) {
        // Méthode optionnelle dans ce contexte, car le score dépend du contexte global du Jest.
        // On peut l'utiliser pour afficher la valeur brute d'une carte.
        System.out.println("Carte visitée : " + carte);
    }
    
    public Map<String, Integer> getScores() {
        return scores;
    }
}