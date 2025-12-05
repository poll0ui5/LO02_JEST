package fr.utt.lo02.jest.model;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.visitor.Visitor;

public abstract class Joueur {

    protected String nom;
    protected List<Carte> main; // Cartes "physiques" en main
    protected List<Carte> jest; // Cartes gagnées
    protected int score;

    // CHANGEMENT ICI : L'offre est un tableau de CarteOffre
    protected CarteOffre[] offre; 

    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.jest = new ArrayList<>();
        this.offre = new CarteOffre[2]; // Toujours 2 cartes dans l'offre au départ
        this.score = 0;
    }
    
    /**
     * Retourne la main actuelle du joueur (les cartes qu'il tient).
     * @return La liste des cartes en main.
     */
    public List<Carte> getMain() {
        return this.main;
    }

    // --- Gestion de l'offre ---

    /**
     * Crée l'offre en transformant 2 cartes de la main en CarteOffre.
     * @param indexCarteVisible index de la carte dans la main à mettre face visible
     * @param indexCarteCachee index de la carte dans la main à mettre face cachée
     */
    public void creerOffre(int indexCarteVisible, int indexCarteCachee) {
        // On récupère les objets Carte
        Carte c1 = main.get(indexCarteVisible);
        Carte c2 = main.get(indexCarteCachee);
        
        // On crée les CarteOffre (Wrapper)
        this.offre[0] = new CarteOffre(c1, true);  // Celle-ci sera visible
        this.offre[1] = new CarteOffre(c2, false); // Celle-ci sera cachée
        
        // On vide la main (car les cartes sont maintenant sur la table)
        this.main.clear();
    }
    
    /**
     * Renvoie la carte visible de l'offre pour déterminer qui joue en premier.
     * Si l'offre est vide ou ne contient que la cachée (cas fin de tour), gère null.
     */
    public CarteOffre getCarteVisibleDeLOffre() {
        for (CarteOffre co : offre) {
            if (co != null && co.getEstVisible()) {
                return co;
            }
        }
        return null; // Cas où la visible a déjà été prise
    }
    
    public CarteOffre[] getOffre() {
        return offre;
    }

    // --- Méthodes communes inchangées ---
    
    public void ramasserCarte(Carte c) {
        this.main.add(c);
    }
    
    public void ajouterAuJest(Carte c) {
        this.jest.add(c);
    }
    
    public String getNom() { 
    	return nom; 
    }
    
    public List<Carte> getJest() { 
    	return jest; 
    }
    public void setScore(int s) { 
    	this.score = s; 
    }
    public int getScore() { 
    	return score; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * Fin de partie : on récupère ce qui reste sur la table.
     */
    public void recupererDerniereCarteDeLOffre() {
        for (int i = 0; i < offre.length; i++) {
            if (offre[i] != null) {
                // Le polymorphisme permet de mettre une CarteOffre dans une List<Carte>
                this.jest.add(offre[i]); 
                offre[i] = null;
            }
        }
    }

    // Méthodes abstraites
    public abstract void faireOffre();
    public abstract Joueur choisirAdversaire(List<Joueur> joueurs);
    public abstract Carte prendreCarteDansOffre(Joueur cible);
}