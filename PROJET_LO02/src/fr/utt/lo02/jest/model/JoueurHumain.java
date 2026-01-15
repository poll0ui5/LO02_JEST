package fr.utt.lo02.jest.model;

import java.util.ArrayList;
import java.util.List;
import fr.utt.lo02.jest.view.terminal.TerminalView;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Joueur contrôlé par un humain via saisie clavier ou interface graphique.
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
public class JoueurHumain extends Joueur {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur d'un joueur humain.
     * 
     * @param nom Le nom du joueur
     */
    public JoueurHumain(String nom) {
        super(nom);
    }

    /**
     * Crée l'offre du joueur humain de manière interactive.
     * <p>
     * Affiche les cartes en main et demande au joueur de choisir
     * quelle carte placer face visible.
     * </p>
     */
    @Override
    public void faireOffre() {
        if (this.main.size() < 2) {
            System.out.println(this.nom + " n'a pas assez de cartes pour faire une offre.");
            return;
        }
        TerminalView t = new TerminalView();
        t.afficherMessage("\n>> C'est à vous, " + this.nom + ".");
        if (!this.jest.isEmpty()) {
            t.afficherMessage("Votre Jest actuel : " + this.jest);
        }
        t.afficherMessage("Vos cartes en main : " + this.main);
        if (this.main.size() == 1) {
            t.afficherMessage("Vous n'avez qu'une carte, elle sera visible.");
            this.creerOffre(0, -1);
        } else {
            int choix = t.lireEntier("Quelle carte placer FACE VISIBLE ? (1 ou 2)", 1, 2);
            if (choix == 1) this.creerOffre(0, 1);
            else this.creerOffre(1, 0);
        }
    }

    /**
     * Permet au joueur humain de choisir un adversaire.
     * <p>
     * Affiche la liste des adversaires disponibles et demande
     * au joueur de faire son choix.
     * </p>
     * 
     * @param joueurs Liste de tous les joueurs
     * @return Le joueur ciblé
     */
    @Override
    public Joueur choisirAdversaire(List<Joueur> joueurs) {
        TerminalView t = new TerminalView();
        ArrayList<Joueur> adversaires = new ArrayList<>();
        t.afficherMessage("\n>> " + this.nom + ", choisissez un adversaire :");
        if (!this.jest.isEmpty()) {
            t.afficherMessage("Votre Jest actuel : " + this.jest);
        }

        int index = 1;
        for (Joueur j : joueurs) {
            if (j != this && (j.getOffre()[0] != null || j.getOffre()[1] != null)) {
                adversaires.add(j);
                t.afficherMessage("  " + index + ". " + j.getNom());
                index++;
            }
        }

        if (adversaires.isEmpty()) {
            t.afficherMessage("Personne d'autre n'a de cartes. Vous prenez chez vous.");
            return this;
        }

        int choix = t.lireEntier("Votre choix", 1, adversaires.size());
        return adversaires.get(choix - 1);
    }

    /**
     * Permet au joueur humain de prendre une carte dans l'offre d'un adversaire.
     * <p>
     * Affiche l'offre de l'adversaire (carte visible et carte cachée)
     * et demande au joueur de choisir laquelle prendre.
     * </p>
     * 
     * @param cible Le joueur dont on prend une carte
     * @return La carte prise
     */
    @Override
    public Carte prendreCarteDansOffre(Joueur cible) {
        TerminalView t = new TerminalView();
        CarteOffre[] offreCible = cible.getOffre();

        t.afficherMessage("Offre de " + cible.getNom() + " :");
        if (offreCible[0] != null) t.afficherMessage("  1. " + offreCible[0]);
        if (offreCible[1] != null) t.afficherMessage("  2. " + offreCible[1]);

        Carte carteChoisie = null;
        int indexChoisi = -1;

        while (carteChoisie == null) {
            int choix = t.lireEntier("Quelle carte prendre ? (1 ou 2)", 1, 2);
            indexChoisi = choix - 1;
            if (offreCible[indexChoisi] != null) carteChoisie = offreCible[indexChoisi];
            else t.afficherMessage("Carte déjà prise ! Choisissez l'autre.");
        }

        cible.getOffre()[indexChoisi] = null;
        return carteChoisie;
    }
}