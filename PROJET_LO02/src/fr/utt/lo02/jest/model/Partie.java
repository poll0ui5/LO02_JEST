package fr.utt.lo02.jest.model;
import java.util.*;
public class Partie {

	// attributs d'une Partie
	private ArrayList<Joueur> listJ;
	private JeuCartes jeu;
	

	// constructeur de Partie
	public Partie(){
		// instanciez la liste de joueurs : listJ
		/* ..... */
		this.listJ = new ArrayList<Joueur>();
		// instanciez le jeu de cartes : jeu
		/* ..... */
		this.jeu = new JeuCartes();
	}

	// ajout d'un joueur à la liste des joueurs
	public void ajouterUnJoueur(Joueur joueur){
		// ajoute un joueur à la liste des joueurs
		/* ..... */
		this.listJ.add(joueur);
	}

    // On mélange le jeu puis on on distribue les cartes aux joueurs 
	public void distribuerCartes(){
		jeu.melanger();
		while (jeu.estVide() == false){
			Iterator<Joueur> it =listJ.iterator();
			while(it.hasNext()){
				// on sort une carte du jeu
				/* ..... */
				Carte carteADistrib = jeu.distribuerUneCarte();
				// le joueur pointé par l'itérateur ramasse la carte
				/* ..... */
				it.next().ramasserCarte(carteADistrib);
			}
		}		
	}
	
	// Chaque joueur jette une et une seule carte sur la table.
	// Les cartes jouées se retrouvent dans la liste cartesJouees
	// On ne teste pas le cas où les joueurs jettent une carte de même valeur.
	public ArrayList<Carte> jouerVosCartes(){
		ArrayList<Carte> cartesJouees = new ArrayList<Carte> ();
		Iterator<Joueur> it = listJ.iterator();
		while(it.hasNext()) {
			// ajoute à la liste carteJouees la carte jouée par le joueur pointé par l'itérateur.
			/* ..... */
			cartesJouees.add(it.next().jouerCarte());
		}
		return cartesJouees;
	}
	
	// Parmi les cartes jouées, laquelle est gagnante?
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
	
	// Quel joueur a joué la carte gagnante?
	// Par souci de simplicité, on parcourt la liste des joueurs et on prend le premier joueur qui a posé la carte gagnante
	// même si plusieurs joueurs ont posé une carte de même valeur.
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
	
	// Le gagnant ramasse toutes les cartes jouees 
    public void recupererCartesJouees(Joueur gagnantj, ArrayList<Carte> cartesJouees) {
    	// le joueur gagnant ramasse toutes les cartes
		/* ..... */
    	Iterator<Carte> it1 = cartesJouees.iterator();
    	while (it1.hasNext()) {
    		gagnantj.ramasserCarte(it1.next());
    	}
    }
    	
	// la partie est terminée quand un vainqueur est trouvé
	public boolean partieTerminee() {
		boolean fin =false;
		Iterator<Joueur> it =listJ.iterator();
		while (it.hasNext() && fin == false) {
			Joueur jj = it.next();
			fin=jj.isWinner();
		}
		return fin;		
	}

	public String toString(){
		return listJ.toString();
	}
				
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
		
		// ces 2 lignes peuvent être omises: elles vont nous servir à dérouler la partie en mode pas à pas
		// vous pouvez les mettre en commentaire. si c'est le cas, mettre également  en commentaire  //saisieClavier=terminal.lireChaine();
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
		
 