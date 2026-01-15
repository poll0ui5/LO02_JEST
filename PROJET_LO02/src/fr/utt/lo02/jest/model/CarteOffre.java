package fr.utt.lo02.jest.model;

/**
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
	
	/** État de visibilité de la carte : true si visible, false si cachée */
	private boolean estVisible;
	
/**
     * Crée une carte offre avec ses valeurs brutes.
     * 
     * @param valeur La valeur de la carte (As, 2, 3, 4, Joker)
     * @param couleur La couleur de la carte (Pique, Trèfle, Carreau, Cœur)
     * @param estVisible true si la carte doit être visible, false si cachée
     */
	public CarteOffre(Valeur valeur, Couleur couleur, boolean estVisible) {
		super(valeur, couleur);
		this.estVisible = estVisible;
	}

    /**
     * Crée une carte offre à partir d'une carte existante.
     * <p>
     * Copie les propriétés (valeur et couleur) d'une carte existante
     * en ajoutant l'état de visibilité.
     * </p>
     * 
     * @param c La carte source à convertir
     * @param estVisible true si la carte doit être visible, false si cachée
     */
    public CarteOffre(Carte c, boolean estVisible) {
        super(c.getValeur(), c.getCouleur());
        this.estVisible = estVisible;
    }
	
	/**
	 * Vérifie si la carte offre est visible.
	 * 
	 * @return true si visible, false si cachée
	 */
	public boolean getEstVisible() {
		return estVisible;
	}

    /**
     * Définit la visibilité de la carte offre.
     * 
     * @param estVisible true pour la rendre visible, false pour la cacher
     */
    public void setEstVisible(boolean estVisible) {
        this.estVisible = estVisible;
    }

    /**
     * Retourne une représentation textuelle de la carte.
     * <p>
     * Si la carte est visible, affiche sa valeur et sa couleur.
     * Si elle est cachée, affiche un placeholder générique.
     * </p>
     * 
     * @return String représentant la carte
     */
    @Override
    public String toString() {
        if (estVisible) {
            return super.toString(); // Affiche "As de Pique"
        } else {
            return "[Carte Cachée]"; // Masque l'info
        }
    }
}