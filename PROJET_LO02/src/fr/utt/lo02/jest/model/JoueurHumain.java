package fr.utt.lo02.jest.model;

/**
 * Joueur humain qui joue via l'interface (Terminal ou Graphique).
 * Hérite de Joueur et utilise le comportement par défaut.
 * 
 * Les actions du joueur humain sont gérées par l'interface utilisateur.
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class JoueurHumain extends Joueur {
    
    /**
     * Constructeur d'un joueur humain
     * @param nom Le nom du joueur
     */
    public JoueurHumain(String nom) {
        super(nom);
    }
    
    /**
     * Le joueur humain joue via l'interface
     * Cette méthode sera appelée par l'interface utilisateur
     */
    @Override
    public Carte jouerCarte() {
        // Le comportement par défaut de Joueur suffit
        // L'interface gérera l'interaction avec le joueur humain
        return super.jouerCarte();
    }
}
