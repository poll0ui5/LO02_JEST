package fr.utt.lo02.jest.view;

import java.util.List;
import java.util.Scanner;
import fr.utt.lo02.jest.model.Carte;
import fr.utt.lo02.jest.model.CarteOffre;
import fr.utt.lo02.jest.model.Joueur;

/**
 * Vue en ligne de commande (Console).
 * <p>
 * Cette classe gère toutes les interactions avec l'utilisateur :
 * <ul>
 * <li>Affichage des messages, des cartes et des scores.</li>
 * <li>Lecture des saisies clavier (nombre de joueurs, choix de cartes, etc.).</li>
 * </ul>
 * Elle ne contient aucune logique de jeu, seulement de l'affichage et de la saisie.
 * </p>
 * * 
 * 
 */
public class Terminal {

    private Scanner scanner;

    /**
     * Constructeur. Initialise le scanner pour lire l'entrée standard (clavier).
     */
    public Terminal() {
        this.scanner = new Scanner(System.in);
    }

    // --- MÉTHODES D'AFFICHAGE (SORTIES) ---

    /**
     * Affiche un message simple à l'utilisateur.
     */
    public void afficherMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * Affiche la liste des trophées en jeu au début de la partie.
     */
    public void afficherTrophees(List<Carte> trophees) {
        System.out.println("------------------------------------------------");
        System.out.println("TROPHÉES EN JEU :");
        for (Carte c : trophees) {
            // Les trophées sont toujours visibles
            System.out.println(" - " + c.toString());
        }
        System.out.println("------------------------------------------------");
    }

    /**
     * Affiche l'offre actuelle d'un joueur.
     * <p>
     * Utilise le toString() de CarteOffre qui masque automatiquement
     * la carte si elle est cachée.
     * </p>
     */
    public void afficherOffre(Joueur j) {
        System.out.println("Offre de " + j.getNom() + " :");
        CarteOffre[] offre = j.getOffre();
        
        if (offre[0] != null) System.out.println("  1. " + offre[0].toString());
        if (offre[1] != null) System.out.println("  2. " + offre[1].toString());
    }

    /**
     * Affiche le score final d'un joueur.
     */
    public void afficherScore(Joueur j) {
        System.out.println(">> Joueur " + j.getNom() + " a terminé avec " + j.getScore() + " points.");
        // On pourrait afficher le détail du Jest ici si on voulait
        System.out.println("   Détail du Jest : " + j.getJest());
    }

    // --- MÉTHODES DE SAISIE (ENTRÉES) ---

    /**
     * Demande le nombre de joueurs pour la partie.
     * Bloque tant que l'utilisateur n'entre pas 3 ou 4.
     */
    public int demanderNombreJoueurs() {
        int nb = 0;
        while (nb != 3 && nb != 4) {
            System.out.print("Combien de joueurs ? (3 ou 4) : ");
            try {
                String input = scanner.nextLine();
                nb = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un chiffre (3 ou 4).");
            }
            
            if (nb != 3 && nb != 4) {
                System.out.println("Le jeu se joue uniquement à 3 ou 4 joueurs.");
            }
        }
        return nb;
    }

    /**
     * Demande le nom d'un joueur.
     */
    public String demanderNomJoueur(int numero) {
        System.out.print("Entrez le nom du Joueur " + numero + " : ");
        return scanner.nextLine();
    }
    
    /**
     * Méthode utilitaire générique pour demander un choix numérique.
     * Utilisée par JoueurHumain pour choisir une carte ou un adversaire.
     * * @param message La question à poser.
     */
    public int lireEntier(String message, int min, int max) {
        int choix = min - 1;
        boolean valide = false;
        
        while (!valide) {
            System.out.print(message + " (" + min + "-" + max + ") : ");
            try {
                String input = scanner.nextLine();
                choix = Integer.parseInt(input);
                
                if (choix >= min && choix <= max) {
                    valide = true;
                } else {
                    System.out.println("Choix hors limites. Essayez encore.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }
        return choix;
    }
}