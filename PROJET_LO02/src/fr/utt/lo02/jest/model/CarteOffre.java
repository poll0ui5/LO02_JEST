package fr.utt.lo02.jest.model;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Interface graphique du projet Jest.
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


/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                            PROJET LO02 - JEST                            ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Carte avec état de visibilité (visible/cachée) pour les offres.
 * 
 * <p>
 * Ce projet implémente le jeu de cartes Jest avec une architecture MVC stricte,
 * permettant deux modes de jeu : interface graphique (Swing) et terminal.
 * </p>
 * 
 * @author Moss'Ab Mirande-Ney
 * @author Paul-Louis Ledoux
 * @version 2.0
 * @since 2026-01-15
 * 
 * @see <a href="https://github.com/poll0ui5/LO02_JEST">GitHub Repository</a>
 */
public class CarteOffre extends Carte {
	
	private static final long serialVersionUID = 1L;
	
	private boolean estVisible;
	
    // Constructeur qui prend les valeurs brutes
	public CarteOffre(Valeur valeur, Couleur couleur, boolean estVisible) {
		super(valeur, couleur);
		this.estVisible = estVisible;
	}

    // Constructeur utilitaire : Crée une offre à partir d'une carte existante
    public CarteOffre(Carte c, boolean estVisible) {
        super(c.getValeur(), c.getCouleur());
        this.estVisible = estVisible;
    }
	
	public boolean getEstVisible() {
		return estVisible;
	}

    public void setEstVisible(boolean estVisible) {
        this.estVisible = estVisible;
    }

    @Override
    public String toString() {
        if (estVisible) {
            return super.toString(); // Affiche "As de Pique"
        } else {
            return "[Carte Cachée]"; // Masque l'info
        }
    }
}