package fr.utt.lo02.jest.strategy;

import fr.utt.lo02.jest.model.JoueurVirtuel;

/**
 * Interface Strategy pour définir le comportement de jeu des joueurs virtuels.
 * Permet de changer dynamiquement la stratégie d'un joueur virtuel sans modifier son code.
 * 
 * Pattern de conception : Strategy
 * 
 * @author Projet LO02
 * @version 1.0
 */
public interface Strategie {
    
    /**
     * Méthode pour faire jouer le joueur selon sa stratégie
     * @param joueur Le joueur virtuel qui utilise cette stratégie
     */
    void jouer(JoueurVirtuel joueur);
}
