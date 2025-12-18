package fr.utt.lo02.jest.model;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.view.Terminal;

/**
 * Joueur humain interagissant via la console.
 * @author Projet LO02
 */
public class JoueurHumain extends Joueur {
    private static final long serialVersionUID = 1L;

    public JoueurHumain(String nom) {
        super(nom);
    }

    @Override
    public void faireOffre() {
        Terminal t = new Terminal();
        t.afficherMessage("\n>> C'est à vous, " + this.nom + ".");
        t.afficherMessage("Vos cartes en main : " + this.main);
        int choix = t.lireEntier("Quelle carte placer FACE VISIBLE ? (1 ou 2)", 1, 2);
        if (choix == 1) this.creerOffre(0, 1);
        else this.creerOffre(1, 0);
    }

    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        Terminal t = new Terminal();
        ArrayList<Joueur> adversaires = new ArrayList<>();
        t.afficherMessage("\n>> " + this.nom + ", choisissez un adversaire :");

        int index = 1;
        for (Joueur j : joueurs) {
            if (j != this && (j.getOffre()[0] != null || j.getOffre()[1] != null)) {
                adversaires.add(j);
                t.afficherMessage("  " + index + ". " + j.getNom());
                index++;
            }
        }

        if (adversaires.isEmpty()) {
            t.afficherMessage("Personne d'autre n'a de cartes. Vous prenez chez vous.");
            return this;
        }

        int choix = t.lireEntier("Votre choix", 1, adversaires.size());
        return adversaires.get(choix - 1);
    }

    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        Terminal t = new Terminal();
        CarteOffre[] offreCible = cible.getOffre();

        t.afficherMessage("Offre de " + cible.getNom() + " :");
        if (offreCible[0] != null) t.afficherMessage("  1. " + offreCible[0]);
        if (offreCible[1] != null) t.afficherMessage("  2. " + offreCible[1]);

        Carte carteChoisie = null;
        int indexChoisi = -1;

        while (carteChoisie == null) {
            int choix = t.lireEntier("Quelle carte prendre ? (1 ou 2)", 1, 2);
            indexChoisi = choix - 1;
            if (offreCible[indexChoisi] != null) carteChoisie = offreCible[indexChoisi];
            else t.afficherMessage("Carte déjà prise ! Choisissez l'autre.");
        }

        cible.getOffre()[indexChoisi] = null;
        return carteChoisie;
    }
}