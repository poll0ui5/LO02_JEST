package fr.utt.lo02.jest.model;

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