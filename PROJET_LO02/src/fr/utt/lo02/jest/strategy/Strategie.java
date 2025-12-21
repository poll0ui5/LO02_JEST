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
     * @param bot Le joueur virtuel concerné.
     */
    void faireOffre(JoueurVirtuel bot);

    /**
     * Le bot doit choisir quel adversaire attaquer.
     * @param bot Le joueur virtuel qui joue.
     * @param joueurs La liste de tous les joueurs de la partie.
     * @return Le joueur cible choisi.
     */
    Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs);

    /**
     * Le bot doit choisir quelle carte prendre dans l'offre de la cible.
     * @param bot Le joueur virtuel qui joue.
     * @param cible Le joueur adverse choisi précédemment.
     * @return La carte choisie.
     */
    Carte choisirCarte(JoueurVirtuel bot, Joueur cible);
}