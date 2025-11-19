package fr.utt.lo02.jest.model;

import java.util.*;
import fr.utt.lo02.jest.visitor.Visitor;

/**
 * Classe représentant un joueur dans le jeu Jest.
 * Un joueur possède un nom, une main de cartes et peut jouer des cartes.
 * Cette classe supporte le pattern Visitor via la méthode accept().
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class Joueur {
	
	// attributs d'un joueur
	private String nom;
	private LinkedList<Carte> main;
	private Carte derniereCarteJouee;
	
	// constructeur
	public Joueur(String nom){
		// initialise le nom du joueur
	    /* ..... */
		this.nom = nom;
		// instancie la main du joueur
		/* .... */ 	
		this.main = new LinkedList<Carte>();
	}
	
	// le joueur ramasse la carte et l'ajoute en dessous des cartes déjà existantes dans la main
	public void ramasserCarte(Carte carte){
		/* .... */ 
		this.main.addLast(carte);
	}

	// le joueur retire la premiere carte de sa main.Cette carte est donc la dernière carte jouée.  
	public Carte jouerCarte(){
		derniereCarteJouee = this.main.getFirst() /* ..... */ ;
		this.main.remove(derniereCarteJouee);
		System.out.println(this.nom + " joue " + this.derniereCarteJouee);
		return derniereCarteJouee;
	}
	// getter de derniereCarteJouee;
	public Carte getDerniereCarteJouee() {
		return derniereCarteJouee;
	}
	
	// setter de derniereCarteJouee (pour les stratégies)
	public void setDerniereCarteJouee(Carte carte) {
		this.derniereCarteJouee = carte;
	}
	
	// le joueur gagne s'il a toutes les cartes dans sa main.
	// JeuCartes.nbrCartes représente toutes les cartes
	public boolean isWinner(){
		boolean jaigagne = false;
		if (main.size()==32)
			jaigagne=true;
		return jaigagne;
	}
	
	public LinkedList<Carte> getMain(){
		return main;
	}
	
	public String getNom(){
		return nom;
	}
	
	// Description d'un joueur avec sa main
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\n ******************************************* \n");		
		sb.append(nom + " a  " +  main.size() + " cartes dans sa main \n");
		sb.append(main);
		sb.append("\n ******************************************* \n");
		return sb.toString();
	}
	
	/**
	 * Méthode accept du pattern Visitor
	 * Permet à un visiteur de traiter ce joueur
	 * @param visitor Le visiteur qui va traiter ce joueur
	 */
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

