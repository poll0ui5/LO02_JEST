package fr.utt.lo02.jest.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.CarteOffre;
import fr.utt.lo02.jest.model.Couleur;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.JoueurVirtuel;

// Stratégie défensive : cache sa meilleure carte et joue prudemment
public class StrategieDefensive implements Strategie {

    /**
     * Comportement défensif : Cache la carte la plus forte.
     * Le bot espère que personne ne prendra sa carte cachée, pour pouvoir la récupérer à la fin.
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
            bot.creerOffre(1, 0);
        } else {
            bot.creerOffre(0, 1);
        }
        
        System.out.println(bot.getNom() + " (Défensif) a fait son offre (a caché sa carte forte).");
    }

    /**
     * Comportement défensif : Choisit l'adversaire avec la carte visible la plus faible.
     * Cela minimise le risque de donner la main à un joueur fort au tour suivant.
     */
    @Override
    public Joueur choisirAdversaire(JoueurVirtuel bot, List<Joueur> joueurs) {
        Joueur cible = null;
        Carte meilleurCarteFaible = null;

        for (Joueur j : joueurs) {
            // On ne s'attaque pas soi-même et on vérifie qu'il reste des cartes
            boolean aDesCartes = (j.getOffre()[0] != null || j.getOffre()[1] != null);
            
            if (j != bot && aDesCartes) {
                // On regarde la carte visible de cet adversaire
                Carte visible = j.getCarteVisibleDeLOffre();
                
                // Si c'est le premier qu'on examine, on le prend par défaut
                if (cible == null) {
                    cible = j;
                    meilleurCarteFaible = visible;
                } else {
                    // Sinon, on compare : on cherche la PLUS PETITE carte visible
                    // Si 'visible' est plus faible que 'meilleurCarteFaible', on change de cible
                    if (visible != null && meilleurCarteFaible != null) {
                        if (meilleurCarteFaible.estSuperieureA(visible)) {
                            cible = j;
                            meilleurCarteFaible = visible;
                        }
                    }
                }
            }
        }

        // Sécurité : si on n'a trouvé personne (cas rare), on retourne le bot lui-même
        if (cible == null) return bot;
        
        return cible;
    }

    /**
     * Comportement défensif : Prend la carte visible si elle est sûre.
     * Évite la carte cachée (peur du Joker ou d'un mauvais coup).
     */
    @Override
    public Carte choisirCarte(JoueurVirtuel bot, Joueur cible) {
        CarteOffre[] offre = cible.getOffre();
        CarteOffre visible = null;
        CarteOffre cachee = null;
        
        // Identifier quelle carte est visible et laquelle est cachée (si elles existent encore)
        if (offre[0] != null) {
            if (offre[0].getEstVisible()) visible = offre[0];
            else cachee = offre[0];
        }
        if (offre[1] != null) {
            if (offre[1].getEstVisible()) visible = offre[1];
            else cachee = offre[1];
        }

        // LOGIQUE DÉCISIONNELLE :
        
        // 1. Si la carte visible est disponible
        if (visible != null) {
            // Un joueur défensif n'aime pas les Carreaux (points négatifs)
            // Note: Le Joker a une couleur null
            if (visible.getCouleur() != null && visible.getCouleur() == Couleur.CARREAU) {
                // Si la visible est un Carreau, on prend le risque de prendre la cachée (si elle existe)
                if (cachee != null) return cachee;
                else return visible; // Pas le choix
            } 
            // Si ce n'est pas un carreau, on prend la visible (sécurité)
            else {
                return visible;
            }
        }
        
        // 2. Si seule la carte cachée est dispo, on la prend
        if (cachee != null) {
            return cachee;
        }

        // 3. Fallback (ne devrait pas arriver si la logique est bonne)
        return null; 
    }
}