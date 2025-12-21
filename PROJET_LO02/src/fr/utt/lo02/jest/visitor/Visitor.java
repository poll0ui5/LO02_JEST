package fr.utt.lo02.jest.visitor;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Joueur;
import fr.utt.lo02.jest.model.Trophee;

/**
 * Interface Visitor pour le pattern Visitor.
 * Permet de calculer les scores et d'effectuer des traitements sur les éléments du jeu
 * sans modifier les classes visitées.
 * 
 * Pattern de conception : Visitor
 * 
 * 
 * 
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
    
}
