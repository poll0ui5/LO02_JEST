package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Interface pour les extensions de jeu ajoutant de nouvelles mécaniques.
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
public interface Extension {
    
    /**
     * Retourne le nom de l'extension.
     */
    String getNom();
    
    /**
     * Retourne la description de l'extension.
     */
    String getDescription();
    
    /**
     * Retourne les cartes ajoutées par cette extension.
     */
    List<Carte> getCartesExtension();
    
    /**
     * Indique si l'extension est activée.
     */
    boolean estActive();
    
    /**
     * Active ou désactive l'extension.
     */
    void setActive(boolean active);
}
