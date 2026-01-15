package fr.utt.lo02.jest.model;
import java.util.*;

/**
 * Gère la pioche de cartes et la distribution aux joueurs.
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
public class JeuCartes {
	
	/** Le tas contenant toutes les cartes non distribuées */
	private LinkedList<Carte> tasCartes;
	
	/** Nombre total de cartes dans le jeu (16 cartes + 1 Joker) */
	public static final int NBR_CARTES = 17;
	
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
	
	/**
	 * Distribue une carte du sommet du tas.
	 * <p>
	 * Retire et retourne la première carte du tas. Si le tas est vide,
	 * une exception NoSuchElementException sera levée.
	 * </p>
	 * 
	 * @return La carte distribuée
	 * @throws NoSuchElementException si le tas est vide
	 */
	public Carte distribuerUneCarte(){ 
		Carte c = this.tasCartes.getFirst();
		this.tasCartes.remove(c);
		return c;
	}
	
	/**
	 * Mélange toutes les cartes du tas.
	 * <p>
	 * Utilise l'algorithme Fisher-Yates via Collections.shuffle().
	 * À appeler au début de chaque partie.
	 * </p>
	 */
	public void melanger(){
		Collections.shuffle(tasCartes);
	}
	
	/**
	 * Vérifie si le tas de cartes est vide.
	 * 
	 * @return true si aucune carte n'est disponible, false sinon
	 */
	public boolean estVide() {
		return this.tasCartes.isEmpty();
	}
	
	/**
	 * Retourne une représentation textuelle du tas.
	 * 
	 * @return String représentant toutes les cartes du tas
	 */
	@Override
	public String toString(){
		return tasCartes.toString();
	}
	
	/**
	 * Récupère la liste complète des cartes du tas.
	 * 
	 * @return LinkedList contenant toutes les cartes restantes
	 */
	public LinkedList<Carte> getTasCartes(){
		return tasCartes;
	}
	
	/**
	 * Ajoute une carte au tas (utilisé pour les extensions).
	 * <p>
	 * Permet d'enrichir le jeu avec des cartes supplémentaires.
	 * </p>
	 * 
	 * @param carte La carte à ajouter au tas
	 */
	public void ajouterCarte(Carte carte) {
		this.tasCartes.add(carte);
	}
}
