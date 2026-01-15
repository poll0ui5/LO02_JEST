package fr.utt.lo02.jest.model;

/**
 * Énumération des valeurs de cartes (As à Roi).
 * 
 * <p>
 * Ce projet implémente le jeu de cartes Jest avec une architecture MVC stricte,
 * permettant deux modes de jeu : interface graphique (Swing) et terminal.
 * </p>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-15
 * 
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public enum Valeur {
    JOKER(0),
    AS(1),
    DEUX(2),
    TROIS(3),
    QUATRE(4);

    private final int valeurFaciale;

    Valeur(int valeurFaciale) {
        this.valeurFaciale = valeurFaciale;
    }

    public int getValeurFaciale() {
        return valeurFaciale;
    }
}