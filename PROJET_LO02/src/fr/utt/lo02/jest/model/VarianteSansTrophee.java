package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
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

    /**
     * Retourne le nom de la variante.
     * 
     * @return "Sans Trophée"
     */
    @Override
    public String getNom() {
        return "Sans Trophée";
    }

    /**
     * Retourne la description des règles modifiées.
     * 
     * @return Description textuelle de la variante
     */
    @Override
    public String getDescription() {
        return "Aucun trophée. Toutes les 17 cartes sont jouées. Score basé uniquement sur les cartes.";
    }

    /**
     * Détermine le nombre de trophées pour cette variante.
     * 
     * @param nbJoueurs Le nombre de joueurs (non utilisé ici)
     * @return Toujours 0 (pas de trophées)
     */
    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return 0; // Pas de trophées
    }

    /**
     * Retourne le nombre de cartes distribuées par tour.
     * 
     * @return 2 cartes par tour
     */
    @Override
    public int getCartesParTour() {
        return 2;
    }

    /**
     * Indique si les offres sont faites simultanément.
     * 
     * @return true
     */
    @Override
    public boolean offresSimultanees() {
        return true;
    }

    /**
     * Applique les règles finales de la variante.
     * <p>
     * Cette variante n'a pas de règles spéciales.
     * </p>
     * 
     * @param joueurs Liste de tous les joueurs
     */
    @Override
    public void appliquerReglesFinales(List<Joueur> joueurs) {
        // Pas de règles spéciales
    }
}
