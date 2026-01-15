package fr.utt.lo02.jest.model;

import java.util.List;
import fr.utt.lo02.jest.model.Strategie;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Joueur contrôlé par une IA avec stratégie configurable (offensive/défensive).
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
public class JoueurVirtuel extends Joueur {
    private static final long serialVersionUID = 1L;
    private Strategie strategie;

    /**
     * Constructeur d'un joueur virtuel.
     * 
     * @param nom Le nom du bot
     * @param strategie La stratégie de jeu du bot
     */
    public JoueurVirtuel(String nom, Strategie strategie) {
        super(nom);
        this.strategie = strategie;
    }

    /**
     * Crée l'offre du bot en déléguant à sa stratégie.
     */
    @Override
    public void faireOffre() {
        try {
            Thread.sleep(2000); // Pause de 2 secondes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.strategie.faireOffre(this);
    }

    /**
     * Choisit un adversaire en déléguant à sa stratégie.
     * 
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur ciblé
     */
    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        try {
            Thread.sleep(2000); // Pause de 2 secondes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this.strategie.choisirAdversaire(this, joueurs);
    }

    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        try {
            Thread.sleep(2000); // Pause de 2 secondes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Carte c = this.strategie.choisirCarte(this, cible);
        if (c == null) {
            // Fallback : prendre la première carte disponible
            CarteOffre[] offreCible = cible.getOffre();
            if (offreCible[0] != null) {
                c = offreCible[0];
                offreCible[0] = null;
            } else if (offreCible[1] != null) {
                c = offreCible[1];
                offreCible[1] = null;
            }
            return c;
        }
        CarteOffre[] offreCible = cible.getOffre();
        if (offreCible[0] == c)
            offreCible[0] = null;
        else if (offreCible[1] == c)
            offreCible[1] = null;
        return c;
    }

    /**
     * Change la stratégie du bot.
     * 
     * @param strategie La nouvelle stratégie
     */
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    /**
     * @return La stratégie actuelle du bot
     */
    public Strategie getStrategie() {
        return this.strategie;
    }
}