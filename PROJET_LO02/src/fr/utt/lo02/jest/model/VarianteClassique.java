package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Mode de jeu standard avec trophées attribués au meilleur joueur.
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
public class VarianteClassique implements Variante {

    /**
     * Retourne le nom de la variante.
     * 
     * @return "Classique"
     */
    @Override
    public String getNom() {
        return "Classique";
    }

    /**
     * Retourne la description des règles de la variante.
     * 
     * @return Description textuelle des règles
     */
    @Override
    public String getDescription() {
        return "Règles de base du Jest. 2 trophées (3 joueurs) ou 1 trophée (4 joueurs).";
    }

    /**
     * Détermine le nombre de trophées selon le nombre de joueurs.
     * 
     * @param nbJoueurs Le nombre de joueurs dans la partie
     * @return 1 si 4 joueurs, 2 sinon
     */
    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return (nbJoueurs == 4) ? 1 : 2;
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
     * @return true pour les offres simultanées
     */
    @Override
    public boolean offresSimultanees() {
        return true;
    }

    /**
     * Applique les règles finales spécifiques à la variante.
     * <p>
     * La variante classique n'a pas de règles spéciales en fin de partie.
     * </p>
     * 
     * @param joueurs Liste de tous les joueurs
     */
    @Override
    public void appliquerReglesFinales(List<Joueur> joueurs) {
        // Pas de règles spéciales en variante classique
    }
}
