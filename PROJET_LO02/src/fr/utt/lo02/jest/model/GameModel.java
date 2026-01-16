package fr.utt.lo02.jest.model;

import java.util.ArrayList;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Modèle de jeu observable contenant l'état complet de la partie.
 * 
 * <p>
 * Cette classe hérite d'Observable et encapsule toutes les données du jeu.
 * Lorsque l'état change, elle notifie automatiquement toutes les vues enregistrées.
 * </p>
 * 
 * <p><b>État du jeu :</b></p>
 * <ul>
 *   <li>Liste des joueurs</li>
 *   <li>Pioche de cartes</li>
 *   <li>Trophées</li>
 *   <li>Variante et extension actives</li>
 *   <li>Numéro de manche</li>
 *   <li>État de la partie (en cours, terminée)</li>
 * </ul>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-16
 * 
 * @see Observable
 * @see Observer
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public class GameModel extends Observable {
    
    private ArrayList<Joueur> joueurs;
    private JeuCartes pioche;
    private ArrayList<Carte> trophees;
    private Variante variante;
    private Extension extension;
    private int numeroManche;
    private boolean partieTerminee;
    private String messageActuel;
    
    /**
     * Constructeur initialisant le modèle de jeu.
     */
    public GameModel() {
        super();
        this.joueurs = new ArrayList<>();
        this.pioche = new JeuCartes();
        this.trophees = new ArrayList<>();
        this.numeroManche = 1;
        this.partieTerminee = false;
        this.messageActuel = "";
    }
    
    /**
     * Définit la liste des joueurs et notifie les observers.
     */
    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
        notifyObservers("JOUEURS_UPDATED");
    }
    
    /**
     * Définit la pioche et notifie les observers.
     */
    public void setPioche(JeuCartes pioche) {
        this.pioche = pioche;
        notifyObservers("PIOCHE_UPDATED");
    }
    
    /**
     * Définit les trophées et notifie les observers.
     */
    public void setTrophees(ArrayList<Carte> trophees) {
        this.trophees = trophees;
        notifyObservers("TROPHEES_UPDATED");
    }
    
    /**
     * Définit la variante et notifie les observers.
     */
    public void setVariante(Variante variante) {
        this.variante = variante;
        notifyObservers("VARIANTE_UPDATED");
    }
    
    /**
     * Définit l'extension et notifie les observers.
     */
    public void setExtension(Extension extension) {
        this.extension = extension;
        notifyObservers("EXTENSION_UPDATED");
    }
    
    /**
     * Définit le numéro de manche et notifie les observers.
     */
    public void setNumeroManche(int numeroManche) {
        this.numeroManche = numeroManche;
        notifyObservers("MANCHE_UPDATED");
    }
    
    /**
     * Définit l'état de fin de partie et notifie les observers.
     */
    public void setPartieTerminee(boolean partieTerminee) {
        this.partieTerminee = partieTerminee;
        notifyObservers("PARTIE_TERMINEE");
    }
    
    /**
     * Définit un message et notifie les observers.
     */
    public void setMessage(String message) {
        this.messageActuel = message;
        notifyObservers("MESSAGE");
    }
    
    /**
     * Notifie que les offres ont changé.
     */
    public void notifyOffresChanged() {
        notifyObservers("OFFRES_UPDATED");
    }
    
    /**
     * Notifie qu'un tour est terminé.
     */
    public void notifyTourTermine() {
        notifyObservers("TOUR_TERMINE");
    }
    
    // Getters
    
    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }
    
    public JeuCartes getPioche() {
        return pioche;
    }
    
    public ArrayList<Carte> getTrophees() {
        return trophees;
    }
    
    public Variante getVariante() {
        return variante;
    }
    
    public Extension getExtension() {
        return extension;
    }
    
    public int getNumeroManche() {
        return numeroManche;
    }
    
    public boolean isPartieTerminee() {
        return partieTerminee;
    }
    
    public String getMessageActuel() {
        return messageActuel;
    }
}
