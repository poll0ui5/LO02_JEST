package fr.utt.lo02.jest.model;

import java.util.List;
import fr.utt.lo02.jest.strategy.Strategie;

/**
 * Représente un joueur virtuel (Bot).
 * <p>
 * Cette classe agit comme le "Contexte" dans le patron de conception Strategy.
 * Elle ne contient pas l'intelligence de jeu directement, mais délègue toutes
 * les décisions (offres, choix d'adversaire, prise de carte) à un objet
 * implémentant l'interface {@link Strategie}.
 * </p>
 * * @see fr.utt.lo02.jest.strategy.Strategie
 * @author Projet LO02
 * @version 2.0
 */
public class JoueurVirtuel extends Joueur {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * La stratégie utilisée par ce bot (Facile, Difficile, etc.).
     */
    private Strategie strategie;
    
    /**
     * Constructeur d'un joueur virtuel.
     * @param nom Le nom du bot (ex: "Bot 1").
     * @param strategie La stratégie initiale à appliquer.
     */
    public JoueurVirtuel(String nom, Strategie strategie) {
        super(nom);
        this.strategie = strategie;
    }
    
    /**
     * Délègue la création de l'offre à la stratégie.
     * <p>
     * La stratégie analysera la main du bot et appellera elle-même
     * la méthode {@code creerOffre} du bot.
     * </p>
     */
    @Override
    public void faireOffre() {
        // On passe 'this' (le bot lui-même) à la stratégie pour qu'elle puisse voir ses cartes
        this.strategie.faireOffre(this);
    }

    /**
     * Délègue le choix de l'adversaire à la stratégie.
     * @param joueurs La liste des joueurs disponibles.
     * @return Le joueur choisi par la stratégie.
     */
    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        return this.strategie.choisirAdversaire(this, joueurs);
    }

    /**
     * Délègue le choix de la carte à la stratégie.
     * @param cible Le joueur adverse choisi.
     * @return La carte sélectionnée par la stratégie.
     */
    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        Carte c = this.strategie.choisirCarte(this, cible);
        
        // Comme pour l'humain, on doit retirer la carte de l'offre adverse
        CarteOffre[] offreCible = cible.getOffre();
        if (offreCible[0] == c) {
            offreCible[0] = null;
        } else if (offreCible[1] == c) {
            offreCible[1] = null;
        }
        
        return c;
    }
    
    // --- Getters et Setters pour la stratégie ---

    /**
     * Permet de changer la stratégie du bot en cours de partie.
     * @param strategie La nouvelle stratégie à appliquer.
     */
    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }
    
    /**
     * Récupère la stratégie actuelle.
     * @return L'instance de la stratégie.
     */
    public Strategie getStrategie() {
        return this.strategie;
    }
}