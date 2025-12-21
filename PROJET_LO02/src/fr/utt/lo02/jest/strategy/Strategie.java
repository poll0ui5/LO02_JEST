package fr.utt.lo02.jest.strategy;

import java.io.Serializable;
import java.util.List;
import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

// Définit comment un bot joue (offensif ou défensif)
public interface Strategie extends Serializable {

    /**
     * Le bot doit décider comment constituer son offre (quelle carte visible/cachée).
     * Doit appeler bot.creerOffre(...) pour valider le choix.
     */
    void faireOffre(JoueurVirtuel bot);

    /**
     * Le bot doit choisir quel adversaire attaquer.
     */
    Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs);

    /**
     * Le bot doit choisir quelle carte prendre dans l'offre de la cible.
     */
    Carte choisirCarte(JoueurVirtuel bot, Joueur cible);
}