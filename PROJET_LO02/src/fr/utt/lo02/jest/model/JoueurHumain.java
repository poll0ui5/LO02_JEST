package fr.utt.lo02.jest.model;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.view.Terminal;

/**
 * Représente un joueur physique interagissant via la console.
 * Implémente les décisions stratégiques en demandant à l'utilisateur.
 * * @author Projet LO02
 * @version 2.0
 */
public class JoueurHumain extends Joueur {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur.
     * @param nom Le nom du joueur.
     */
    public JoueurHumain(String nom) {
        super(nom);
    }

    /**
     * Demande au joueur quelle carte mettre face visible.
     * L'autre carte sera automatiquement mise face cachée.
     * Règle : [cite: 119-121]
     */
    @Override
    public void faireOffre() {
        Terminal t = new Terminal();
        t.afficherMessage("\n>> C'est à vous, " + this.nom + ".");
        t.afficherMessage("Vos cartes en main : " + this.main);
        
        // On demande à l'utilisateur quelle carte montrer (1 ou 2)
        int choix = t.lireEntier("Quelle carte voulez-vous placer FACE VISIBLE ? (1 ou 2)", 1, 2);
        
        // Si le joueur choisit la carte 1 (index 0), elle est visible, l'autre cachée.
        // Si le joueur choisit la carte 2 (index 1), elle est visible, l'autre cachée.
        if (choix == 1) {
            this.creerOffre(0, 1);
        } else {
            this.creerOffre(1, 0);
        }
    }

    /**
     * Demande au joueur de choisir un adversaire cible pour voler une carte.
     * Règle : On ne peut pas se choisir soi-même (sauf cas particulier géré par le contrôleur)[cite: 127].
     */
    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        Terminal t = new Terminal();
        ArrayList<Joueur> adversairesPossibles = new ArrayList<>();
        
        t.afficherMessage("\n>> " + this.nom + ", choisissez un adversaire :");

        // On filtre la liste pour ne pas s'afficher soi-même
        // Et on ne garde que ceux qui ont encore des cartes dans leur offre
        int index = 1;
        for (Joueur j : joueurs) {
            if (j != this && (j.getOffre()[0] != null || j.getOffre()[1] != null)) {
                adversairesPossibles.add(j);
                t.afficherMessage("  " + index + ". " + j.getNom());
                index++;
            }
        }
        
        // Cas particulier : si je suis le dernier et que personne d'autre n'a de cartes
        if (adversairesPossibles.isEmpty()) {
            t.afficherMessage("Personne d'autre n'a de cartes. Vous devez prendre chez vous[cite: 136].");
            return this;
        }

        int choix = t.lireEntier("Votre choix", 1, adversairesPossibles.size());
        
        // On retourne le joueur correspondant à l'index choisi (attention au décalage -1)
        return adversairesPossibles.get(choix - 1);
    }

    /**
     * Demande au joueur quelle carte prendre dans l'offre de la cible.
     * Règle : On ne peut pas prendre une carte vide (null)[cite: 127].
     */
    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        Terminal t = new Terminal();
        CarteOffre[] offreCible = cible.getOffre();
        
        t.afficherMessage("Offre de " + cible.getNom() + " :");
        // Affiche l'offre (la carte cachée apparaitra masquée grâce à CarteOffre.toString)
        if (offreCible[0] != null) t.afficherMessage("  1. " + offreCible[0]);
        if (offreCible[1] != null) t.afficherMessage("  2. " + offreCible[1]);
        
        Carte carteChoisie = null;
        int indexChoisi = -1;

        // Boucle jusqu'à ce que le joueur choisisse une carte valide (non null)
        while (carteChoisie == null) {
            int choix = t.lireEntier("Quelle carte prendre ? (1 ou 2)", 1, 2);
            indexChoisi = choix - 1;
            
            if (offreCible[indexChoisi] != null) {
                carteChoisie = offreCible[indexChoisi];
            } else {
                t.afficherMessage("Il n'y a plus de carte ici ! Choisissez l'autre.");
            }
        }

        // Action importante : On retire la carte de l'offre de l'adversaire !
        cible.getOffre()[indexChoisi] = null;
        
        // Si c'était la carte cachée, on la révèle pour le joueur qui la prend (optionnel mais logique)
        // Mais techniquement dans le Jest elle reste face cachée pour le score final[cite: 128].
        // Cependant, le joueur qui la prend la regarde.
        // carteChoisie.setEstVisible(true); // Décommenter si on veut la voir tout de suite dans la console
        
        return carteChoisie;
    }
}