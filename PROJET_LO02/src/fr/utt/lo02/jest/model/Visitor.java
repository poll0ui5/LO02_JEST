package fr.utt.lo02.jest.model;

/**
 * Interface Visitor pour le pattern Visitor.
 * Permet de calculer les scores et d'effectuer des traitements sur les éléments du jeu
 * sans modifier les classes visitées.
 * 
 * Pattern de conception : Visitor
 * 
 * @author Projet LO02
 * @version 1.0
 */
public interface Visitor {
    
    /**
     * Visite un joueur pour calculer son score ou effectuer un traitement
     * @param joueur Le joueur à visiter
     */
    void visit(Joueur joueur);
    
    /**
     * Visite une carte pour effectuer un traitement
     * @param carte La carte à visiter
     */
    void visit(Carte carte);
    
    /**
     * Visite un trophée pour effectuer un traitement
     * @param trophee Le trophée à visiter
     */
    void visit(Trophee trophee);
}
