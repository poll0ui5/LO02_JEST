package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Interface définissant les règles d'une variante de jeu.
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
public interface Variante {
    
    /**
     * Retourne le nom de la variante.
     */
    String getNom();
    
    /**
     * Retourne la description des règles modifiées.
     */
    String getDescription();
    
    /**
     * Modifie le nombre de trophées selon la variante.
     */
    int getNombreTrophees(int nbJoueurs);
    
    /**
     * Modifie le nombre de cartes distribuées par tour.
     */
    int getCartesParTour();
    
    /**
     * Indique si les offres sont simultanées ou séquentielles.
     */
    boolean offresSimultanees();
    
    /**
     * Applique des règles spéciales en fin de partie (bonus/malus).
     */
    void appliquerReglesFinales(List<Joueur> joueurs);
}
