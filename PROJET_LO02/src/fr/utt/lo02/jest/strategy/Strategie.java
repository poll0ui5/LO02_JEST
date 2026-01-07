package fr.utt.lo02.jest.strategy;

import java.io.Serializable;
import java.util.List;
import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

/**
 * Interface définissant une stratégie de jeu pour les joueurs virtuels (pattern Strategy).
 * <p>
 * Permet de définir différents comportements pour les bots :
 * <ul>
 * <li>Comment créer leur offre (quelle carte montrer/cacher)</li>
 * <li>Quel adversaire cibler</li>
 * <li>Quelle carte prendre dans l'offre adverse</li>
 * </ul>
 * </p>
 * 
 * @see StrategieOffensive
 * @see StrategieDefensive
 * @see JoueurVirtuel
 * @author LO02 Project
 */
public interface Strategie extends Serializable {

    /**
     * Décide comment le bot constitue son offre.
     * <p>
     * Doit appeler bot.creerOffre(...) pour valider le choix de la carte
     * visible et de la carte cachée.
     * </p>
     * 
     * @param bot Le joueur virtuel qui doit faire son offre
     */
    void faireOffre(JoueurVirtuel bot);

    /**
     * Décide quel adversaire le bot doit cibler.
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur ciblé
     */
    Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs);

    /**
     * Décide quelle carte le bot doit prendre dans l'offre de l'adversaire.
     * 
     * @param bot Le joueur virtuel qui doit choisir
     * @param cible Le joueur dont on prend une carte
     * @return La carte choisie
     */
    Carte choisirCarte(JoueurVirtuel bot, Joueur cible);
}