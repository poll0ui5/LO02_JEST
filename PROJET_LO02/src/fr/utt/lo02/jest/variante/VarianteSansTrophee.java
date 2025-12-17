package fr.utt.lo02.jest.variante;

import fr.utt.lo02.jest.model.Joueur;
import java.util.List;

/**
 * Variante "Sans Trophée" du jeu Jest.
 * <p>
 * Règles modifiées :
 * <ul>
 * <li>Aucun trophée n'est distribué</li>
 * <li>Toutes les cartes sont jouées</li>
 * <li>Le score dépend uniquement des cartes collectées</li>
 * </ul>
 * </p>
 * @author Projet LO02
 * @version 1.0
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
