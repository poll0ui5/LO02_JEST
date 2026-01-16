package fr.utt.lo02.jest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Classe Observable du pattern Observer/Observable.
 * 
 * <p>
 * Les objets héritant de cette classe peuvent être observés par des Observers.
 * Lorsque l'état de l'Observable change, tous les Observers sont notifiés.
 * </p>
 * 
 * <p><b>Utilisation typique :</b></p>
 * <ul>
 *   <li>Le Model hérite d'Observable</li>
 *   <li>Les Views implémentent Observer</li>
 *   <li>Le Controller enregistre les Views auprès du Model</li>
 *   <li>Le Model notifie les Views lors des changements</li>
 * </ul>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-16
 * 
 * @see Observer
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public class Observable {
    
    private List<Observer> observers;
    
    /**
     * Constructeur initialisant la liste des observers.
     */
    public Observable() {
        this.observers = new ArrayList<>();
    }
    
    /**
     * Ajoute un observer à la liste des observateurs.
     * 
     * @param observer L'observer à ajouter
     */
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    /**
     * Retire un observer de la liste des observateurs.
     * 
     * @param observer L'observer à retirer
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifie tous les observers d'un changement d'état.
     * 
     * @param data Données optionnelles sur le changement
     */
    public void notifyObservers(Object data) {
        for (Observer observer : observers) {
            observer.update(this, data);
        }
    }
    
    /**
     * Notifie tous les observers sans données supplémentaires.
     */
    public void notifyObservers() {
        notifyObservers(null);
    }
    
    /**
     * Retourne le nombre d'observers enregistrés.
     * 
     * @return Le nombre d'observers
     */
    public int countObservers() {
        return observers.size();
    }
}
