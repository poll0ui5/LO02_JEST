package fr.utt.lo02.jest.view;

import java.io.*;

/**
 * Classe Terminal pour gérer les entrées/sorties en mode console.
 * Permet de lire des chaînes de caractères et des entiers depuis le terminal.
 * 
 * @author Projet LO02
 * @version 1.0
 */
public class Terminal {
	
	private InputStream entree;
	private PrintStream sortie;
	
	/**
	 * Constructeur du Terminal
	 * Initialise les flux d'entrée/sortie standard
	 */
	public Terminal(){
		entree = System.in;
		sortie = System.out;		
	}
	
	/**
	 * Lit un entier depuis l'entrée standard
	 * @return L'entier lu
	 */
	public int lireEntier(){
		DataInputStream dos = new DataInputStream(entree);
		int value=0;
		try {
			value =  dos.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return value;		
	}
	
	/**
	 * Lit une chaîne de caractères depuis l'entrée standard
	 * @return La chaîne lue
	 */
	public String lireChaine(){
		String laChaine = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(entree));
		try {
			laChaine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return laChaine;
	}
	
	/**
	 * Affiche une chaîne de caractères sur la sortie standard
	 * @param laChaine La chaîne à afficher
	 */
	public void afficheLaChaine(String laChaine){
		sortie.println(laChaine);
	}
	
	/**
	 * Mode echo : répète ce qui est saisi jusqu'à "exit"
	 */
	public void echo(){
		String saisieClavier = new String();
		while(saisieClavier.compareTo("exit") != 0){
			saisieClavier=lireChaine();
			afficheLaChaine(saisieClavier);			
		}		
	}
}
