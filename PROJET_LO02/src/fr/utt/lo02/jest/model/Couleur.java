package fr.utt.lo02.jest.model;

/**
 * Énumération des couleurs du jeu Jest.
 * <p>
 * L'ordre est important pour le tie-break : Pique > Trèfle > Carreau > Cœur.
 * L'ordinal le plus élevé = la couleur la plus forte.
 * </p>
 */
public enum Couleur {
	COEUR,    // ordinal 0 - le plus faible
	CARREAU,  // ordinal 1
	TREFLE,   // ordinal 2
	PIQUE     // ordinal 3 - le plus fort
}

