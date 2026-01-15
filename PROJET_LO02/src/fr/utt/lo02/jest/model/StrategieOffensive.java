package fr.utt.lo02.jest.model;

import java.util.List;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.CarteOffre;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Stratégie agressive privilégiant les cartes de haute valeur et les Jest adverses.
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
public class StrategieOffensive implements Strategie {

    /**
     * Crée l'offre en montrant la meilleure carte.
     * <p>
     * Le but est d'avoir la carte visible la plus forte pour être
     * le premier à jouer et choisir son adversaire.
     * </p>
     * 
     * @param bot Le joueur virtuel qui doit faire son offre
     */
    @Override
    public void faireOffre(JoueurVirtuel bot) {
        if (bot.getMain().size() < 2) {
            System.out.println(bot.getNom() + " n'a pas assez de cartes pour faire une offre.");
            return;
        }
        
        Carte c1 = bot.getMain().get(0);
        Carte c2 = bot.getMain().get(1);

        if (c1.estSuperieureA(c2)) {
            bot.creerOffre(0, 1);
        } else {
            bot.creerOffre(1, 0);
        }
        
        System.out.println(bot.getNom() + " (Offensif) a fait son offre (montre sa force).");
    }

    /**
     * Choisit l'adversaire avec la meilleure carte visible.
     * <p>
     * Stratégie agressive : cible les joueurs forts pour leur voler
     * leurs meilleures cartes (valeurs élevées, Piques).
     * </p>
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur avec la meilleure carte visible
     */
    @Override
    public Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs) {
        Joueur cible = null;
        Carte meilleurCarteVisible = null;

        for (Joueur j : joueurs) {
            // Vérifie que ce n'est pas nous-même et qu'il a des cartes
            boolean aDesCartes = (j.getOffre()[0] != null || j.getOffre()[1] != null);
            
            if (j != bot && aDesCartes) {
                Carte visible = j.getCarteVisibleDeLOffre();
                
                // Si c'est le premier adversaire examiné
                if (cible == null) {
                    cible = j;
                    meilleurCarteVisible = visible;
                } else {
                    // On cherche la carte visible la PLUS FORTE
                    if (visible != null && meilleurCarteVisible != null) {
                        if (visible.estSuperieureA(meilleurCarteVisible)) {
                            cible = j;
                            meilleurCarteVisible = visible;
                        }
                    }
                }
            }
        }

        // Si aucun adversaire valide n'est trouvé (cas fin de partie), retourne le bot lui-même
        if (cible == null) return bot;
        
        return cible;
    }

    /**
     * Choisit quelle carte prendre dans l'offre adverse.
     * <p>
     * Logique décisionnelle :
     * <ul>
     * <li>Si la carte visible est forte (valeur ≥3 ou As/Joker) → la prendre</li>
     * <li>Si la carte visible est faible (2) → prendre la cachée (risque)</li>
     * <li>Si seule la cachée est disponible → la prendre</li>
     * </ul>
     * </p>
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param cible Le joueur dont on prend une carte
     * @return La carte choisie
     */
    @Override
    public Carte choisirCarte(JoueurVirtuel bot, Joueur cible) {
        CarteOffre[] offre = cible.getOffre();
        CarteOffre visible = null;
        CarteOffre cachee = null;
        
        // Identification des cartes (gestion des nulls si déjà prises)
        if (offre[0] != null) {
            if (offre[0].getEstVisible()) visible = offre[0];
            else cachee = offre[0];
        }
        if (offre[1] != null) {
            if (offre[1].getEstVisible()) visible = offre[1];
            else cachee = offre[1];
        }

        // LOGIQUE DÉCISIONNELLE :
        
        // 1. Analyse de la carte visible
        if (visible != null) {
            // Si la carte visible est intéressante (Valeur faciale élevée >= 3 ou Joker/As)
            // On considère ici que >= 3 est "fort". (As=1 mais c'est une carte spéciale, simplifions avec la valeur faciale brute pour l'instant)
            if (visible.getValeur().getValeurFaciale() >= 3 || visible.getValeur().getValeurFaciale() <= 1) { 
                // Note : <= 1 capture l'As (1) et le Joker (0), qui sont très convoités.
                return visible;
            }
            
            // Si la carte visible est "bof" (un 2 par exemple), et qu'il y a une cachée...
            if (cachee != null) {
                // ... on prend le risque de prendre la cachée !
                return cachee;
            } else {
                return visible; // Pas le choix
            }
        }
        
        // 2. Si seule la cachée est dispo
        if (cachee != null) {
            return cachee;
        }

        return null; // Ne devrait pas arriver
    }
}