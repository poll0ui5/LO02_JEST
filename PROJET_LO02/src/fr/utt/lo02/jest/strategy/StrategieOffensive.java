package fr.utt.lo02.jest.strategy;

import java.util.List;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.CarteOffre;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

/**
 * Stratégie offensive pour un joueur virtuel.
 * <p>
 * Philosophie : "Domination et Prise de risque".
 * <ul>
 * <li><b>Offre :</b> Montre sa carte la plus forte pour tenter de prendre la main (jouer en premier).</li>
 * <li><b>Cible :</b> Attaque le joueur possédant la carte visible la plus forte (pour la voler).</li>
 * <li><b>Prise :</b> Tente de récupérer les grosses cartes. Si l'offre visible est faible, prend la carte cachée à l'aveugle.</li>
 * </ul>
 * </p>
 * @author Projet LO02
 * @version 2.0
 */
public class StrategieOffensive implements Strategie {

    /**
     * Comportement offensif : Montre la carte la plus forte.
     * Le but est d'avoir la plus forte visible pour être le premier à choisir au tour suivant.
     */
    @Override
    public void faireOffre(JoueurVirtuel bot) {
        if (bot.getMain().size() < 2) {
            System.out.println(bot.getNom() + " n'a pas assez de cartes pour faire une offre.");
            return;
        }
        
        Carte c1 = bot.getMain().get(0);
        Carte c2 = bot.getMain().get(1);

        if (c1.estSuperieureA(c2)) {
            bot.creerOffre(0, 1);
        } else {
            bot.creerOffre(1, 0);
        }
        
        System.out.println(bot.getNom() + " (Offensif) a fait son offre (montre sa force).");
    }

    /**
     * Comportement offensif : Cible le joueur avec la meilleure carte visible.
     * Le bot veut voler les meilleures cartes (As, Figures, Piques).
     */
    @Override
    public Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs) {
        Joueur cible = null;
        Carte meilleurCarteVisible = null;

        for (Joueur j : joueurs) {
            // Vérifie que ce n'est pas nous-même et qu'il a des cartes
            boolean aDesCartes = (j.getOffre()[0] != null || j.getOffre()[1] != null);
            
            if (j != bot && aDesCartes) {
                Carte visible = j.getCarteVisibleDeLOffre();
                
                // Si c'est le premier adversaire examiné
                if (cible == null) {
                    cible = j;
                    meilleurCarteVisible = visible;
                } else {
                    // On cherche la carte visible la PLUS FORTE
                    if (visible != null && meilleurCarteVisible != null) {
                        if (visible.estSuperieureA(meilleurCarteVisible)) {
                            cible = j;
                            meilleurCarteVisible = visible;
                        }
                    }
                }
            }
        }

        // Si aucun adversaire valide n'est trouvé (cas fin de partie), retourne le bot lui-même
        if (cible == null) return bot;
        
        return cible;
    }

    /**
     * Comportement offensif :
     * - Si la carte visible est forte (3, 4, As, Joker), on la prend.
     * - Si la carte visible est faible (2), on tente le diable et on prend la cachée.
     */
    @Override
    public Carte choisirCarte(JoueurVirtuel bot, Joueur cible) {
        CarteOffre[] offre = cible.getOffre();
        CarteOffre visible = null;
        CarteOffre cachee = null;
        
        // Identification des cartes (gestion des nulls si déjà prises)
        if (offre[0] != null) {
            if (offre[0].getEstVisible()) visible = offre[0];
            else cachee = offre[0];
        }
        if (offre[1] != null) {
            if (offre[1].getEstVisible()) visible = offre[1];
            else cachee = offre[1];
        }

        // LOGIQUE DÉCISIONNELLE :
        
        // 1. Analyse de la carte visible
        if (visible != null) {
            // Si la carte visible est intéressante (Valeur faciale élevée >= 3 ou Joker/As)
            // On considère ici que >= 3 est "fort". (As=1 mais c'est une carte spéciale, simplifions avec la valeur faciale brute pour l'instant)
            if (visible.getValeur().getValeurFaciale() >= 3 || visible.getValeur().getValeurFaciale() <= 1) { 
                // Note : <= 1 capture l'As (1) et le Joker (0), qui sont très convoités.
                return visible;
            }
            
            // Si la carte visible est "bof" (un 2 par exemple), et qu'il y a une cachée...
            if (cachee != null) {
                // ... on prend le risque de prendre la cachée !
                return cachee;
            } else {
                return visible; // Pas le choix
            }
        }
        
        // 2. Si seule la cachée est dispo
        if (cachee != null) {
            return cachee;
        }

        return null; // Ne devrait pas arriver
    }
}