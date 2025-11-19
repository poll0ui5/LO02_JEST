package fr.utt.lo02.jest.controller;

import java.util.*;

import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.view.Terminal;

/**
 * Contrôleur principal du jeu Jest.
 * Gère le déroulement d'une partie : distribution, tours de jeu, détermination du gagnant.
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class Partie {

	// attributs d'une Partie
	private ArrayList<Joueur> listJ;
	private JeuCartes jeu;
	

	/**
	 * Constructeur de Partie
	 * Initialise la liste des joueurs et le jeu de cartes
	 */
	public Partie(){
		this.listJ = new ArrayList<Joueur>();
		this.jeu = new JeuCartes();
	}

	/**
	 * Ajoute un joueur à la partie
	 * @param joueur Le joueur à ajouter
	 */
	public void ajouterUnJoueur(Joueur joueur){
		this.listJ.add(joueur);
	}

	/**
	 * Mélange le jeu et distribue les cartes aux joueurs
	 */
	public void distribuerCartes(){
		jeu.melanger();
		while (jeu.estVide() == false){
			Iterator<Joueur> it =listJ.iterator();
			while(it.hasNext()){
				Carte carteADistrib = jeu.distribuerUneCarte();
				it.next().ramasserCarte(carteADistrib);
			}
		}		
	}
	
	/**
	 * Chaque joueur joue une carte
	 * @return La liste des cartes jouées
	 */
	public ArrayList<Carte> jouerVosCartes(){
		ArrayList<Carte> cartesJouees = new ArrayList<Carte> ();
		Iterator<Joueur> it = listJ.iterator();
		while(it.hasNext()) {
			cartesJouees.add(it.next().jouerCarte());
		}
		return cartesJouees;
	}
	
	/**
	 * Détermine la carte gagnante parmi les cartes jouées
	 * @param cartes Les cartes jouées
	 * @return La carte ayant la plus grande valeur
	 */
	public Carte carteGagnante(List<Carte> cartes) {
		Carte meilleureCarte = cartes.get(0);
		System.out.println(meilleureCarte);
		Iterator<Carte> it = cartes.iterator();
		while(it.hasNext()) {
			Carte c = it.next();
			if (c.getValeur().ordinal() > meilleureCarte.getValeur().ordinal()) {
				meilleureCarte = c;
			}
		}		
		return meilleureCarte;
	}
	
	/**
	 * Détermine quel joueur a joué la carte gagnante
	 * @param carte La carte gagnante
	 * @return Le joueur ayant joué cette carte
	 */
	public Joueur joueurGagnant(Carte carte) {
		boolean leGagnantEst = false;
		Iterator<Joueur> it =listJ.iterator();
		Joueur gagnant = null;
		while (it.hasNext() && leGagnantEst == false) {
			gagnant=it.next();
			if ( carte.equals(gagnant.getDerniereCarteJouee()) ) 
					leGagnantEst = true;			
		}
		return gagnant;	
	}
	
	/**
	 * Le gagnant du tour ramasse toutes les cartes jouées
	 * @param gagnantj Le joueur gagnant
	 * @param cartesJouees Les cartes à ramasser
	 */
    public void recupererCartesJouees(Joueur gagnantj, ArrayList<Carte> cartesJouees) {
    	Iterator<Carte> it1 = cartesJouees.iterator();
    	while (it1.hasNext()) {
    		gagnantj.ramasserCarte(it1.next());
    	}
    }
    	
	/**
	 * Vérifie si la partie est terminée (un joueur a toutes les cartes)
	 * @return true si un joueur a gagné
	 */
	public boolean partieTerminee() {
		boolean fin =false;
		Iterator<Joueur> it =listJ.iterator();
		while (it.hasNext() && fin == false) {
			Joueur jj = it.next();
			fin=jj.isWinner();
		}
		return fin;		
	}

	@Override
	public String toString(){
		return listJ.toString();
	}
				
	/**
	 * Point d'entrée principal pour lancer une partie
	 * @param args Arguments de la ligne de commande
	 */
	public static void main(String[] args) {

		// création d'une partie de Bataille
		Partie bataille = new Partie();
		
		// création de deux joueurs 
		Joueur Karadoc = new Joueur("Karadoc");
		Joueur Perceval = new Joueur("Perceval");
		
		// on ajoute les 2 joueurs à la partie
		bataille.ajouterUnJoueur(Karadoc);
		bataille.ajouterUnJoueur(Perceval);
		
		// on affiche le jeu de cartes
		System.out.println(bataille.jeu);
		
		// on distribue les cartes à l'ensemble des joueurs
		bataille.distribuerCartes();

		// on affiche les 2 joueurs de cette partie
		System.out.println(bataille.listJ.get(0));
		System.out.println(bataille.listJ.get(1));
		
		// Mode pas à pas avec Terminal
		Terminal terminal = new Terminal();
		String saisieClavier = new String();

		while (bataille.partieTerminee()==false) {
			ArrayList<Carte> lesCartesJouees =  bataille.jouerVosCartes();
			Carte laCarteGagnante                 =  bataille.carteGagnante(lesCartesJouees);
			Joueur leGagnant                         =  bataille.joueurGagnant(laCarteGagnante);
			bataille.recupererCartesJouees(leGagnant, lesCartesJouees);		
			System.out.println(bataille);	
			saisieClavier=terminal.lireChaine();
		}		
	}
}
