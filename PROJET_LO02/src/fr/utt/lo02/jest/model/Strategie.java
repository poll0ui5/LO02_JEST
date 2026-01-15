package fr.utt.lo02.jest.model;

import java.io.Serializable;
import java.util.List;
import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Interface définissant le comportement d'une stratégie d'IA pour les joueurs virtuels.
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
public interface Strategie extends Serializable {

    /**
     * Décide comment le bot constitue son offre.
     * <p>
     * Doit appeler bot.creerOffre(...) pour valider le choix de la carte
     * visible et de la carte cachée.
     * </p>
     * 
     * @param bot Le joueur virtuel qui doit faire son offre
     */
    void faireOffre(JoueurVirtuel bot);

    /**
     * Décide quel adversaire le bot doit cibler.
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur ciblé
     */
    Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs);

    /**
     * Décide quelle carte le bot doit prendre dans l'offre de l'adversaire.
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param cible Le joueur dont on prend une carte
     * @return La carte choisie
     */
    Carte choisirCarte(JoueurVirtuel bot, Joueur cible);
}