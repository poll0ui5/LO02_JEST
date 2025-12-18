package fr.utt.lo02.jest.model;

import java.util.List;
import fr.utt.lo02.jest.strategy.Strategie;

/**
 * Joueur virtuel (Bot) utilisant le pattern Strategy pour déléguer ses décisions.
 * @author Projet LO02
 */
public class JoueurVirtuel extends Joueur {
    private static final long serialVersionUID = 1L;
    private Strategie strategie;

    public JoueurVirtuel(String nom, Strategie strategie) {
        super(nom);
        this.strategie = strategie;
    }

    @Override
    public void faireOffre() {
        this.strategie.faireOffre(this);
    }

    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        return this.strategie.choisirAdversaire(this, joueurs);
    }

    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        Carte c = this.strategie.choisirCarte(this, cible);
        CarteOffre[] offreCible = cible.getOffre();
        if (offreCible[0] == c) offreCible[0] = null;
        else if (offreCible[1] == c) offreCible[1] = null;
        return c;
    }

    public void setStrategie(Strategie strategie) { this.strategie = strategie; }
    public Strategie getStrategie() { return this.strategie; }
}