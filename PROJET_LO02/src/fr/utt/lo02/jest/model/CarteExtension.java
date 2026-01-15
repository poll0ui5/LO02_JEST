package fr.utt.lo02.jest.model;

import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.Valeur;
import fr.utt.lo02.jest.model.Couleur;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Carte spéciale avec effets bonus (As Doré, Cœur Maudit).
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
public class CarteExtension extends Carte {
    
    private String nomSpecial;
    private String effetDescription;
    
    /**
     * Constructeur d'une carte d'extension.
     */
    public CarteExtension(Valeur valeur, Couleur couleur, String nomSpecial, String effetDescription) {
        super(valeur, couleur);
        this.nomSpecial = nomSpecial;
        this.effetDescription = effetDescription;
    }
    
    public String getNomSpecial() {
        return nomSpecial;
    }
    
    public String getEffetDescription() {
        return effetDescription;
    }
    
    /**
     * Vérifie si cette carte est une carte d'extension.
     */
    public boolean estCarteExtension() {
        return true;
    }
    
    @Override
    public String toString() {
        return nomSpecial + " (" + super.toString() + ")";
    }
}
