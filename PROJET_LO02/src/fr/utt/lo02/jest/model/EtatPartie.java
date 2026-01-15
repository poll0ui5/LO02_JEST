package fr.utt.lo02.jest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;

/**
 * Classe encapsulant l'état complet d'une partie pour la sauvegarde.
 * <p>
 * Contient toutes les informations nécessaires pour reprendre une partie :
 * joueurs, pioche, trophées, numéro de manche, variante choisie.
 * </p>
 * 
 * 
 */
public class EtatPartie implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Joueur> joueurs;
    private LinkedList<Carte> pioche;
    private ArrayList<Carte> trophees;
    private int numeroManche;
    private String nomVariante;
    private String nomExtension;
    private boolean partieTerminee;
    private String nomJoueurActuel;
    private boolean phaseOffre;
    private boolean phaseChoix;
    
    /**
     * Constructeur de l'état de partie.
     */
    public EtatPartie() {
        this.joueurs = new ArrayList<>();
        this.pioche = new LinkedList<>();
        this.trophees = new ArrayList<>();
        this.numeroManche = 1;
        this.nomVariante = "Classique";
        this.nomExtension = null;
        this.partieTerminee = false;
        this.nomJoueurActuel = null;
        this.phaseOffre = false;
        this.phaseChoix = false;
    }
    
    // Getters et Setters
    
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }
    
    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
    
    public LinkedList<Carte> getPioche() {
        return pioche;
    }
    
    public void setPioche(LinkedList<Carte> pioche) {
        this.pioche = pioche;
    }
    
    public ArrayList<Carte> getTrophees() {
        return trophees;
    }
    
    public void setTrophees(ArrayList<Carte> trophees) {
        this.trophees = trophees;
    }
    
    public int getNumeroManche() {
        return numeroManche;
    }
    
    public void setNumeroManche(int numeroManche) {
        this.numeroManche = numeroManche;
    }
    
    public String getNomVariante() {
        return nomVariante;
    }
    
    public void setNomVariante(String nomVariante) {
        this.nomVariante = nomVariante;
    }
    
    public String getNomExtension() {
        return nomExtension;
    }
    
    public void setNomExtension(String nomExtension) {
        this.nomExtension = nomExtension;
    }
    
    public boolean isPartieTerminee() {
        return partieTerminee;
    }
    
    public void setPartieTerminee(boolean partieTerminee) {
        this.partieTerminee = partieTerminee;
    }
    
    public String getNomJoueurActuel() {
        return nomJoueurActuel;
    }
    
    public void setNomJoueurActuel(String nomJoueurActuel) {
        this.nomJoueurActuel = nomJoueurActuel;
    }
    
    public boolean isPhaseOffre() {
        return phaseOffre;
    }
    
    public void setPhaseOffre(boolean phaseOffre) {
        this.phaseOffre = phaseOffre;
    }
    
    public boolean isPhaseChoix() {
        return phaseChoix;
    }
    
    public void setPhaseChoix(boolean phaseChoix) {
        this.phaseChoix = phaseChoix;
    }
}
