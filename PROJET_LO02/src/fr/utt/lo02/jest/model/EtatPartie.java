package fr.utt.lo02.jest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Représente l'état complet d'une partie pour la sauvegarde/chargement.
 * 
 * <p>
 * Ce projet implémente le jeu de cartes Jest avec une architecture MVC stricte,
 * permettant deux modes de jeu : interface graphique (Swing) et terminal.
 * </p>
 * 
 * <p><b>Architecture MVC :</b></p>
 * <ul>
 *   <li><b>Model</b> : Logique métier (cartes, joueurs, stratégies, variantes)</li>
 *   <li><b>View</b> : Interfaces utilisateur (Terminal, Swing)</li>
 *   <li><b>Controller</b> : Coordination du flux de jeu</li>
 * </ul>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-15
 * 
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
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
