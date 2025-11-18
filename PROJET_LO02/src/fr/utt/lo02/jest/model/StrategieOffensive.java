package fr.utt.lo02.jest.model;

/**
 * Stratégie offensive : le joueur virtuel joue de manière agressive.
 * Privilégie les cartes de forte valeur pour maximiser les gains.
 * 
 * Comportement : Choisit toujours la carte ayant la plus grande valeur dans la main.
 * 
 * Pattern de conception : Strategy (implémentation concrète)
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class StrategieOffensive implements Strategie {
    
    @Override
    public void jouer(JoueurVirtuel joueur) {
        // Implémentation de la stratégie offensive
        // Le joueur choisit la carte de plus forte valeur dans sa main
        
        if (joueur.getMain().isEmpty()) {
            System.out.println(joueur.getNom() + " n'a plus de cartes à jouer");
            return;
        }
        
        // Trouve la carte avec la plus grande valeur
        Carte carteAJouer = joueur.getMain().getFirst();
        for (Carte carte : joueur.getMain()) {
            if (carte.getValeur().ordinal() > carteAJouer.getValeur().ordinal()) {
                carteAJouer = carte;
            }
        }
        
        // Retire la carte de la main et la définit comme dernière carte jouée
        joueur.getMain().remove(carteAJouer);
        joueur.setDerniereCarteJouee(carteAJouer);
        System.out.println(joueur.getNom() + " (Offensif) joue " + carteAJouer);
    }
}
