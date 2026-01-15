package fr.utt.lo02.jest.model;

/**
 * Énumération des couleurs de cartes (♠ Pique, ♥ Cœur, ♦ Carreau, ♣ Trèfle).
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
public enum Couleur {
	COEUR,    // ordinal 0 - le plus faible
	CARREAU,  // ordinal 1
	TREFLE,   // ordinal 2
	PIQUE     // ordinal 3 - le plus fort
}

