package fr.utt.lo02.jest.model;

public class CarteOffre extends Carte{
	
	private boolean estVisible;
	
	public CarteOffre(Valeur valeur, Couleur couleur, boolean estVisible) {
		super(valeur, couleur);
		this.estVisible = estVisible;
	}
	
}
