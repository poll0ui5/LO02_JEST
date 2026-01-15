package fr.utt.lo02.jest.model;

import java.io.*;

/**
 * Gère la sérialisation et désérialisation des parties au format .jest.
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
public class GestionnaireSauvegarde {
    
    /** Extension des fichiers de sauvegarde */
    private static final String EXTENSION = ".jest";
    
    /** Répertoire contenant les sauvegardes */
    private static final String DOSSIER_SAUVEGARDES = "sauvegardes/";
    
    /**
     * Sauvegarde un objet sérialisable dans un fichier.
     * <p>
     * Crée le répertoire de sauvegarde s'il n'existe pas,
     * puis sérialise l'objet dans un fichier.
     * </p>
     * 
     * @param objet L'objet à sauvegarder
     * @param nomFichier Le nom du fichier (sans extension)
     * @return true si la sauvegarde s'est bien déroulée, false en cas d'erreur
     */
    public static boolean sauvegarder(Serializable objet, String nomFichier) {
        // Créer le dossier s'il n'existe pas
        File dossier = new File(DOSSIER_SAUVEGARDES);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
        
        String cheminComplet = DOSSIER_SAUVEGARDES + nomFichier + EXTENSION;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(cheminComplet))) {
            oos.writeObject(objet);
            System.out.println("Partie sauvegardée dans : " + cheminComplet);
            return true;
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Charge un objet depuis un fichier de sauvegarde.
     * <p>
     * Désérialise l'objet stocké dans le fichier spécifié.
     * </p>
     * 
     * @param nomFichier Le nom du fichier à charger (sans extension)
     * @return L'objet désérialisé, ou null en cas d'erreur
     */
    public static Object charger(String nomFichier) {
        String cheminComplet = DOSSIER_SAUVEGARDES + nomFichier + EXTENSION;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(cheminComplet))) {
            Object objet = ois.readObject();
            System.out.println("Partie chargée depuis : " + cheminComplet);
            return objet;
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + cheminComplet);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Liste les sauvegardes disponibles.
     * <p>
     * Parcourt le répertoire de sauvegarde et retourne les noms
     * de tous les fichiers de sauvegarde (.jest).
     * </p>
     * 
     * @return Tableau contenant les noms des sauvegardes disponibles
     */
    public static String[] listerSauvegardes() {
        File dossier = new File(DOSSIER_SAUVEGARDES);
        if (!dossier.exists()) {
            return new String[0];
        }
        
        File[] fichiers = dossier.listFiles((dir, name) -> name.endsWith(EXTENSION));
        if (fichiers == null) {
            return new String[0];
        }
        
        String[] noms = new String[fichiers.length];
        for (int i = 0; i < fichiers.length; i++) {
            String nom = fichiers[i].getName();
            noms[i] = nom.substring(0, nom.length() - EXTENSION.length());
        }
        return noms;
    }
    
    /**
     * Vérifie si une sauvegarde existe.
     */
    public static boolean sauvegardeExiste(String nomFichier) {
        File fichier = new File(DOSSIER_SAUVEGARDES + nomFichier + EXTENSION);
        return fichier.exists();
    }
}
