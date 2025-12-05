package fr.utt.lo02.jest.model;

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