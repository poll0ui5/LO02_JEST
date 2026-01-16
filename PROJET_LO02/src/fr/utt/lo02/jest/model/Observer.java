package fr.utt.lo02.jest.model;

/**
 * Interface Observer du pattern Observer/Observable.
 * 
 * <p>
 * Les classes implémentant cette interface peuvent s'abonner à un Observable
 * pour être notifiées des changements d'état du modèle.
 * </p>
 * 
 * <p><b>Pattern Observer :</b></p>
 * <ul>
 *   <li><b>Observer</b> : Interface pour les objets qui observent (Views)</li>
 *   <li><b>Observable</b> : Classe pour les objets observés (Model)</li>
 *   <li><b>update()</b> : Méthode appelée lors d'un changement d'état</li>
 * </ul>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-16
 * 
 * @see Observable
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public interface Observer {
    
    /**
     * Méthode appelée lorsque l'objet observé change d'état.
     * 
     * @param observable L'objet Observable qui a changé
     * @param data Données optionnelles sur le changement
     */
    void update(Observable observable, Object data);
}
