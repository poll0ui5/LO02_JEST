package fr.utt.lo02.jest.model;
import java.util.*;

/**
 * Représente le paquet de cartes du jeu Jest.
 * <p>
 * Le jeu contient 17 cartes :
 * <ul>
 * <li>16 cartes de couleur : 4 couleurs × 4 valeurs (As, 2, 3, 4)</li>
 * <li>1 Joker (sans couleur)</li>
 * </ul>
 * </p>
 */
public class JeuCartes {
	
	private LinkedList<Carte> tasCartes;
	public static final int NBR_CARTES = 17; // 16 cartes + 1 Joker
	
	/**
	 * Constructeur : crée le paquet de 17 cartes du Jest.
	 */
	public JeuCartes(){
		this.tasCartes = new LinkedList<Carte>();
		
		// 1. Création des 16 cartes de couleur (As, 2, 3, 4 pour chaque couleur)
		Valeur[] valeursNormales = {Valeur.AS, Valeur.DEUX, Valeur.TROIS, Valeur.QUATRE};
		Couleur[] couleurs = Couleur.values();
		
		for (Valeur v : valeursNormales) {
			for (Couleur c : couleurs) {
				this.tasCartes.add(new Carte(v, c));
			}
		}
		
		// 2. Ajout du Joker (couleur null car le Joker n'a pas de couleur)
		this.tasCartes.add(new Carte(Valeur.JOKER, null));
	}
	
	// retire la premiére carte du tas de cartes (la carte du dessus)
	public Carte distribuerUneCarte(){ 
		Carte c;
		// on retire la carte du dessus du tas de cartes
		 /* ... */ 
		c = this.tasCartes.getFirst();
		this.tasCartes.remove(c);
	return c;
		
	}
	
	// Mélange de toutes les cartes. Très simple ....Appel de shuffle de la classe Collections (à différencier de l'interface Collection)
	public void melanger(){
		Collections.shuffle(tasCartes);
	}
	
	
	// le tas de cartes est-il vide?
	public boolean estVide() {
		// le tas cartes est vide ?
		/* .... */
		boolean vide;
		vide = this.tasCartes.isEmpty();
		return vide;
	}
	
	public String toString(){
		return tasCartes.toString();
	}
	
	public LinkedList<Carte> getTasCartes(){
		return tasCartes;
	}
}
