package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Mode sans attribution de trophées en fin de partie.
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
public class VarianteSansTrophee implements Variante {

    @Override
    public String getNom() {
        return "Sans Trophée";
    }

    @Override
    public String getDescription() {
        return "Aucun trophée. Toutes les 17 cartes sont jouées. Score basé uniquement sur les cartes.";
    }

    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return 0; // Pas de trophées
    }

    @Override
    public int getCartesParTour() {
        return 2;
    }

    @Override
    public boolean offresSimultanees() {
        return true;
    }

    @Override
    public void appliquerReglesFinales(List<Joueur> joueurs) {
        // Pas de règles spéciales
    }
}
