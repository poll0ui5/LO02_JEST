package fr.utt.lo02.jest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Classe abstraite représentant un joueur (humain ou virtuel).
 * <p>
 * Un joueur possède :
 * <ul>
 * <li>Une main de cartes (2 cartes reçues à chaque tour)</li>
 * <li>Un Jest (collection de cartes accumulées durant la partie)</li>
 * <li>Une offre (1 carte visible + 1 carte cachée)</li>
 * <li>Un score calculé en fin de partie</li>
 * </ul>
 * </p>
 * 
 * @see JoueurHumain
 * @see JoueurVirtuel
 * @author LO02 Project
 */
public abstract class Joueur implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected List<Carte> main;
    protected List<Carte> jest;
    protected int score;
    protected CarteOffre[] offre;

    /**
     * Constructeur d'un joueur.
     * <p>
     * Initialise la main, le Jest et l'offre vides, ainsi que le score à 0.
     * </p>
     * 
     * @param nom Le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.jest = new ArrayList<>();
        this.offre = new CarteOffre[2];
        this.score = 0;
    }

    /**
     * @return La main du joueur (cartes en cours)
     */
    public List<Carte> getMain() { return this.main; }
    
    /**
     * @return L'offre du joueur (2 cartes : 1 visible, 1 cachée)
     */
    public CarteOffre[] getOffre() { return offre; }
    
    /**
     * @return Le nom du joueur
     */
    public String getNom() { return nom; }
    
    /**
     * @return Le Jest du joueur (collection de cartes accumulées)
     */
    public List<Carte> getJest() { return jest; }
    
    /**
     * Définit le score du joueur.
     * 
     * @param s Le nouveau score
     */
    public void setScore(int s) { this.score = s; }
    
    /**
     * @return Le score actuel du joueur
     */
    public int getScore() { return score; }

    /**
     * Crée l'offre du joueur à partir de sa main.
     * <p>
     * Place une carte visible et une carte cachée dans l'offre,
     * puis vide la main du joueur.
     * </p>
     * 
     * @param indexVisible Index de la carte à rendre visible
     * @param indexCachee Index de la carte à garder cachée
     */
    public void creerOffre(int indexVisible, int indexCachee) {
        this.offre[0] = new CarteOffre(main.get(indexVisible), true);
        if (indexCachee >= 0 && indexCachee < main.size()) {
            this.offre[1] = new CarteOffre(main.get(indexCachee), false);
        } else {
            this.offre[1] = null;
        }
        this.main.clear();
    }

    /**
     * Retourne la carte visible de l'offre du joueur.
     * <p>
     * Cette carte est utilisée pour déterminer l'ordre de jeu.
     * </p>
     * 
     * @return La carte visible de l'offre, ou null si aucune
     */
    public CarteOffre getCarteVisibleDeLOffre() {
        for (CarteOffre co : offre) {
            if (co != null && co.getEstVisible()) return co;
        }
        return null;
    }

    /**
     * Ajoute une carte à la main du joueur.
     * 
     * @param c La carte à ramasser
     */
    public void ramasserCarte(Carte c) { this.main.add(c); }
    
    /**
     * Ajoute une carte au Jest du joueur.
     * 
     * @param c La carte à ajouter au Jest
     */
    public void ajouterAuJest(Carte c) { this.jest.add(c); }
    
    /**
     * Accepte un visiteur (pattern Visitor pour le calcul du score).
     * 
     * @param v Le visiteur à accepter
     */
    public void accept(Visitor v) { v.visit(this); }

    /**
     * Récupère les cartes restantes de l'offre en fin de partie.
     * <p>
     * Les cartes non prises par les adversaires sont ajoutées au Jest.
     * </p>
     */
    public void recupererDerniereCarteDeLOffre() {
        for (int i = 0; i < offre.length; i++) {
            if (offre[i] != null) {
                this.jest.add(offre[i]);
                offre[i] = null;
            }
        }
    }

    /**
     * Fait l'offre du joueur (choisit quelle carte montrer et quelle carte cacher).
     * <p>
     * Méthode abstraite implémentée différemment par les joueurs humains et virtuels.
     * </p>
     */
    public abstract void faireOffre();
    
    /**
     * Choisit un adversaire dans la liste des joueurs.
     * <p>
     * Méthode abstraite implémentée différemment par les joueurs humains et virtuels.
     * </p>
     * 
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur ciblé
     */
    public abstract Joueur choisirAdversaire(List<Joueur> joueurs);
    
    /**
     * Prend une carte dans l'offre d'un adversaire.
     * <p>
     * Méthode abstraite implémentée différemment par les joueurs humains et virtuels.
     * </p>
     * 
     * @param cible Le joueur dont on prend une carte
     * @return La carte prise
     */
    public abstract Carte prendreCarteDansOffre(Joueur cible);
}