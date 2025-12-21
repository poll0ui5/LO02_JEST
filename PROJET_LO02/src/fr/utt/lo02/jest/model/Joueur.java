package fr.utt.lo02.jest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Classe abstraite représentant un joueur (humain ou virtuel).
 * 
 */
public abstract class Joueur implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected List<Carte> main;
    protected List<Carte> jest;
    protected int score;
    protected CarteOffre[] offre;

    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.jest = new ArrayList<>();
        this.offre = new CarteOffre[2];
        this.score = 0;
    }

    public List<Carte> getMain() { return this.main; }
    public CarteOffre[] getOffre() { return offre; }
    public String getNom() { return nom; }
    public List<Carte> getJest() { return jest; }
    public void setScore(int s) { this.score = s; }
    public int getScore() { return score; }

    /** Crée l'offre : une carte visible, une cachée. */
    public void creerOffre(int indexVisible, int indexCachee) {
        this.offre[0] = new CarteOffre(main.get(indexVisible), true);
        if (indexCachee >= 0 && indexCachee < main.size()) {
            this.offre[1] = new CarteOffre(main.get(indexCachee), false);
        } else {
            this.offre[1] = null;
        }
        this.main.clear();
    }

    /** Retourne la carte visible de l'offre. */
    public CarteOffre getCarteVisibleDeLOffre() {
        for (CarteOffre co : offre) {
            if (co != null && co.getEstVisible()) return co;
        }
        return null;
    }

    public void ramasserCarte(Carte c) { this.main.add(c); }
    public void ajouterAuJest(Carte c) { this.jest.add(c); }
    public void accept(Visitor v) { v.visit(this); }

    /** Récupère les cartes restantes de l'offre en fin de partie. */
    public void recupererDerniereCarteDeLOffre() {
        for (int i = 0; i < offre.length; i++) {
            if (offre[i] != null) {
                this.jest.add(offre[i]);
                offre[i] = null;
            }
        }
    }

    public abstract void faireOffre();
    public abstract Joueur choisirAdversaire(List<Joueur> joueurs);
    public abstract Carte prendreCarteDansOffre(Joueur cible);
}