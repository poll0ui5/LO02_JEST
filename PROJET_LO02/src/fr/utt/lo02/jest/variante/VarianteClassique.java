package fr.utt.lo02.jest.variante;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Variante classique du jeu Jest (règles de base).
 * <p>
 * Règles standard :
 * <ul>
 * <li>2 trophées pour 3 joueurs, 1 trophée pour 4 joueurs</li>
 * <li>2 cartes distribuées par tour</li>
 * <li>Offres simultanées</li>
 * </ul>
 * </p>
 * @author Projet LO02
 * @version 1.0
 */
public class VarianteClassique implements Variante {

    @Override
    public String getNom() {
        return "Classique";
    }

    @Override
    public String getDescription() {
        return "Règles de base du Jest. 2 trophées (3 joueurs) ou 1 trophée (4 joueurs).";
    }

    @Override
    public int getNombreTrophees(int nbJoueurs) {
        return (nbJoueurs == 4) ? 1 : 2;
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
        // Pas de règles spéciales en variante classique
    }
}
