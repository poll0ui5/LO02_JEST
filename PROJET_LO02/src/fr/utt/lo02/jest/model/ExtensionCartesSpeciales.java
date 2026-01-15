package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.model.Couleur;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension ajoutant des cartes avec effets spéciaux (As Doré +2, Cœur Maudit -3).
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
public class ExtensionCartesSpeciales implements Extension {
    
    /** État d'activation de l'extension */
    private boolean active;
    
    /** Liste des cartes spéciales ajoutées par cette extension */
    private List<Carte> cartes;
    
    /**
     * Constructeur de l'extension.
     * <p>
     * Initialise les cartes spéciales (As Doré, Cœur Maudit).
     * </p>
     */
    public ExtensionCartesSpeciales() {
        this.active = false;
        this.cartes = new ArrayList<>();
        initialiserCartes();
    }
    
    /**
     * Initialise les cartes spéciales de l'extension.
     * <p>
     * Crée deux cartes bonus avec effets spéciaux.
     * </p>
     */
    private void initialiserCartes() {
        // Note: On réutilise QUATRE comme valeur max existante
        // Dans une vraie extension, on ajouterait CINQ à l'enum Valeur
        // Pour simplifier, on crée des cartes "bonus" avec les valeurs existantes
        // mais avec un effet spécial géré dans le VisitorScore
        
        // Carte bonus : As de Pique supplémentaire (très convoité)
        cartes.add(new CarteExtension(Valeur.AS, Couleur.PIQUE, "As Doré", 
            "Vaut 6 points au lieu de 5 s'il est seul de sa couleur"));
        
        // Carte bonus : Quatre de Cœur supplémentaire
        cartes.add(new CarteExtension(Valeur.QUATRE, Couleur.COEUR, "Cœur Maudit",
            "Vaut -4 si vous avez le Joker, +4 sinon"));
    }

    /**
     * Retourne le nom de l'extension.
     * 
     * @return "Cartes Spéciales"
     */
    @Override
    public String getNom() {
        return "Cartes Spéciales";
    }

    /**
     * Retourne la description de l'extension.
     * 
     * @return Description textuelle des cartes ajoutées
     */
    @Override
    public String getDescription() {
        return "Ajoute 2 cartes bonus avec des effets spéciaux : As Doré et Cœur Maudit.";
    }

    /**
     * Retourne les cartes ajoutées par l'extension.
     * 
     * @return Liste des CarteExtension
     */
    @Override
    public List<Carte> getCartesExtension() {
        return cartes;
    }

    /**
     * Vérifie si l'extension est active.
     * 
     * @return true si l'extension est appliquée, false sinon
     */
    @Override
    public boolean estActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
