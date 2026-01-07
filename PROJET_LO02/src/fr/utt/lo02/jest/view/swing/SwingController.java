package fr.utt.lo02.jest.view.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.strategy.*;
import fr.utt.lo02.jest.variante.*;
import fr.utt.lo02.jest.extension.*;
import fr.utt.lo02.jest.sauvegarde.*;
import fr.utt.lo02.jest.visitor.VisitorScore;
import javax.swing.JOptionPane;

/**
 * Contrôleur pour l'interface Swing (Swing/WindowBuilder compatible).
 */
public class SwingController {

	private MainFrame mainFrame;
	private ArrayList<Joueur> joueurs;
	private JeuCartes pioche;
	private ArrayList<Carte> trophees;
	private Variante variante;
	private int numeroManche;
	private boolean partieTerminee;
	private Joueur joueurActuel;
	private boolean phaseOffre;
	private boolean phaseChoix;
	private ArrayList<Joueur> joueursAyantJoue;
	private boolean partiePausee;

	public SwingController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.joueurs = new ArrayList<>();
		this.trophees = new ArrayList<>();
		this.joueursAyantJoue = new ArrayList<>();
	}

	public void configurerPartie(String varianteNom, String extensionNom, int nbJoueurs, int nbHumains, String[] noms) {
		joueurs.clear();
		trophees.clear();
		joueursAyantJoue.clear();
		numeroManche = 1;
		partieTerminee = false;
		phaseOffre = false;
		phaseChoix = false;

		// Variante
		switch (varianteNom) {
			case "Sans Trophée":
				variante = new VarianteSansTrophee();
				break;
			case "Double Mise":
				variante = new VarianteDoubleMise();
				break;
			default:
				variante = new VarianteClassique();
		}

		// Créer les joueurs humains
		for (int i = 0; i < nbHumains; i++) {
			joueurs.add(new JoueurHumain(noms[i]));
		}

		// Créer les bots
		for (int i = 0; i < nbJoueurs - nbHumains; i++) {
			Strategie strat = i % 2 == 0 ? new StrategieOffensive() : new StrategieDefensive();
			joueurs.add(new JoueurVirtuel("Bot " + (i + 1), strat));
		}

		// Créer et mélanger la pioche
		pioche = new JeuCartes();
		
		// Ajouter les cartes de l'extension si activée
		if ("Cartes Spéciales".equals(extensionNom)) {
			Extension ext = new ExtensionCartesSpeciales();
			ext.setActive(true);
			for (Carte c : ext.getCartesExtension()) {
				pioche.ajouterCarte(c);
			}
			System.out.println("Extension Cartes Spéciales activée (+" + ext.getCartesExtension().size() + " cartes)");
		}
		
		pioche.melanger();

		// Distribuer les trophées
		int nbTrophees = variante.getNombreTrophees(nbJoueurs);
		for (int i = 0; i < nbTrophees && !pioche.estVide(); i++) {
			Carte t = pioche.distribuerUneCarte();
			t.show();
			trophees.add(t);
		}
	}

	public void demarrerPartie() {
		System.out.println("\n========================================");
		System.out.println("NOUVELLE PARTIE - Variante: " + variante.getNom());
		System.out.println("Joueurs: " + joueurs.size() + " | Trophées: " + trophees.size());
		System.out.println("Pioche: " + pioche.getTasCartes().size() + " cartes");
		System.out.println("========================================");
		mainFrame.showGame();
		demarrerManche();
	}

	public void demarrerManche() {
		// Vérifier qu'il y a assez de cartes
		int cartesNecessaires = joueurs.size() * 2;
		System.out.println("\n=== MANCHE " + numeroManche + " ===");
		System.out.println("Pioche: " + pioche.getTasCartes().size() + " cartes (besoin de " + cartesNecessaires + ")");
		
		if (pioche.getTasCartes().size() < cartesNecessaires) {
			System.out.println("Pas assez de cartes -> FIN DE PARTIE");
			terminerPartie();
			return;
		}

		joueursAyantJoue.clear();
		phaseOffre = true;
		phaseChoix = false;

		// Distribuer 2 cartes à chaque joueur
		System.out.println("Distribution de 2 cartes à chaque joueur...");
		for (Joueur j : joueurs) {
			if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
			if (!pioche.estVide()) j.ramasserCarte(pioche.distribuerUneCarte());
		}
		System.out.println("Pioche après distribution: " + pioche.getTasCartes().size() + " cartes");

		// Trouver le premier joueur humain qui doit faire son offre
		joueurActuel = trouverProchainJoueurOffre();
		
		mainFrame.getGamePanel().updateDisplay();
		
		if (joueurActuel != null) {
			mainFrame.getGamePanel().showMessage("Tour de " + joueurActuel.getNom() + " - Choisissez une carte à cacher");
		} else {
			// Tous les joueurs sont des bots, faire les offres automatiquement
			faireToutesLesOffresBot();
		}
	}

	private Joueur trouverProchainJoueurOffre() {
		for (Joueur j : joueurs) {
			if (j instanceof JoueurHumain && j.getMain().size() > 0) {
				return j;
			}
		}
		return null;
	}

	public void joueurFaitOffre(Joueur joueur, int indexCarteCachee) {
		if (!phaseOffre || joueur != joueurActuel) return;

		// Faire l'offre : la carte choisie est cachée
		Carte carteCachee = joueur.getMain().get(indexCarteCachee);
		Carte carteVisible = joueur.getMain().get(1 - indexCarteCachee);
		
		joueur.getMain().clear();
		joueur.getOffre()[0] = new CarteOffre(carteVisible, true);
		joueur.getOffre()[1] = new CarteOffre(carteCachee, false);

		// Chercher le prochain humain
		joueurActuel = trouverProchainJoueurOffre();
		
		if (joueurActuel != null) {
			mainFrame.getGamePanel().updateDisplay();
			mainFrame.getGamePanel().showMessage("Tour de " + joueurActuel.getNom() + " - Choisissez une carte à cacher");
		} else {
			// Faire les offres des bots
			faireToutesLesOffresBot();
		}
	}

	private void faireToutesLesOffresBot() {
		for (Joueur j : joueurs) {
			if (j instanceof JoueurVirtuel && j.getMain().size() > 0) {
				j.faireOffre();
			}
		}
		
		// Passer à la phase de choix
		phaseOffre = false;
		phaseChoix = true;
		joueurActuel = trouverMeilleureOffre(joueurs);
		
		mainFrame.getGamePanel().updateDisplay();
		
		if (joueurActuel instanceof JoueurHumain) {
			mainFrame.getGamePanel().showMessage("Tour de " + joueurActuel.getNom() + " - Prenez une carte d'un adversaire");
		} else {
			// Le bot joue
			jouerTourBot();
		}
	}

	public void joueurPrendCarte(Joueur cible, int indexCarte) {
		if (!phaseChoix || joueurActuel == null) return;
		if (!(joueurActuel instanceof JoueurHumain)) return;

		// Prendre la carte
		CarteOffre carteOffre = cible.getOffre()[indexCarte];
		if (carteOffre == null) return;

		Carte carte = carteOffre;
		joueurActuel.ajouterAuJest(carte);
		cible.getOffre()[indexCarte] = null;

		mainFrame.getGamePanel().showMessage(joueurActuel.getNom() + " prend " + carte + " chez " + cible.getNom());

		joueursAyantJoue.add(joueurActuel);
		passerAuJoueurSuivant(cible);
	}

	private void jouerTourBot() {
		if (partiePausee) return; // Ne pas jouer si la partie est en pause
		if (joueurActuel == null || !(joueurActuel instanceof JoueurVirtuel)) return;
		
		final Joueur botActuel = joueurActuel;

		// Étape 1 : Le bot réfléchit (2 secondes)
		mainFrame.getGamePanel().showMessage(botActuel.getNom() + " réfléchit...");
		
		Timer timer1 = new Timer(1500, e1 -> {
			if (partiePausee) return; // Vérifier la pause
			// Étape 2 : Le bot choisit sa cible
			final Joueur cible = botActuel.choisirAdversaire(joueurs);
			mainFrame.getGamePanel().showMessage(botActuel.getNom() + " cible " + cible.getNom() + "...");
			
			// Log
			System.out.println(botActuel.getNom() + " cible " + cible.getNom());
			System.out.println("  Offre de " + cible.getNom() + " AVANT: [" + 
				(cible.getOffre()[0] != null ? cible.getOffre()[0] : "null") + ", " +
				(cible.getOffre()[1] != null ? cible.getOffre()[1] : "null") + "]");
			
			Timer timer2 = new Timer(1500, e2 -> {
				if (partiePausee) return; // Vérifier la pause
				// Étape 3 : Le bot prend une carte
				Carte carte = botActuel.prendreCarteDansOffre(cible);
				botActuel.ajouterAuJest(carte);
				
				// Log
				System.out.println("  " + botActuel.getNom() + " prend: " + carte);
				System.out.println("  Offre de " + cible.getNom() + " APRES: [" + 
					(cible.getOffre()[0] != null ? cible.getOffre()[0] : "null") + ", " +
					(cible.getOffre()[1] != null ? cible.getOffre()[1] : "null") + "]");

				mainFrame.getGamePanel().showMessage(botActuel.getNom() + " prend " + carte + " chez " + cible.getNom());
				mainFrame.getGamePanel().updateDisplay();

				Timer timer3 = new Timer(1500, e3 -> {
					if (partiePausee) return; // Vérifier la pause
					// Étape 4 : Passer au joueur suivant
					joueursAyantJoue.add(botActuel);
					passerAuJoueurSuivant(cible);
				});
				timer3.setRepeats(false);
				timer3.start();
			});
			timer2.setRepeats(false);
			timer2.start();
		});
		timer1.setRepeats(false);
		timer1.start();
	}

	private void passerAuJoueurSuivant(Joueur derniereCible) {
		if (joueursAyantJoue.size() >= joueurs.size()) {
			System.out.println("\n--- Fin de manche " + numeroManche + " ---");
			
			// Fin de la manche : récupérer les cartes restantes dans les offres
			System.out.println("Récupération des cartes restantes dans les offres...");
			for (Joueur j : joueurs) {
				int avantRecup = j.getJest().size();
				j.recupererDerniereCarteDeLOffre();
				int apresRecup = j.getJest().size();
				System.out.println("  " + j.getNom() + ": Jest " + avantRecup + " -> " + apresRecup + " cartes");
			}
			
			mainFrame.getGamePanel().updateDisplay();
			numeroManche++;
			
			// Vérifier s'il y a assez de cartes pour la prochaine manche
			int cartesNecessaires = joueurs.size() * 2;
			System.out.println("Pioche: " + pioche.getTasCartes().size() + " cartes (besoin de " + cartesNecessaires + " pour manche suivante)");
			
			if (pioche.getTasCartes().size() < cartesNecessaires) {
				System.out.println("Pas assez de cartes -> FIN DE PARTIE (dans 2 secondes...)");
				mainFrame.getGamePanel().showMessage("Fin de partie ! Calcul des scores en cours...");
				// Pause de 2 secondes pour voir le Jest final avant la fin
				Timer timerFin = new Timer(2000, ev -> terminerPartie());
				timerFin.setRepeats(false);
				timerFin.start();
			} else {
				System.out.println("Lancement de la manche suivante...");
				// Délai avant la prochaine manche
				Timer timer = new Timer(1000, e -> demarrerManche());
				timer.setRepeats(false);
				timer.start();
			}
			return;
		}

		// Déterminer le prochain joueur
		if (!joueursAyantJoue.contains(derniereCible)) {
			joueurActuel = derniereCible;
		} else {
			ArrayList<Joueur> restants = new ArrayList<>();
			for (Joueur j : joueurs) {
				if (!joueursAyantJoue.contains(j)) {
					restants.add(j);
				}
			}
			if (!restants.isEmpty()) {
				joueurActuel = trouverMeilleureOffre(restants);
			} else {
				joueurActuel = null;
			}
		}

		mainFrame.getGamePanel().updateDisplay();

		if (joueurActuel != null) {
			if (joueurActuel instanceof JoueurHumain) {
				mainFrame.getGamePanel().showMessage("Tour de " + joueurActuel.getNom() + " - Prenez une carte d'un adversaire");
			} else {
				jouerTourBot();
			}
		}
	}

	private Joueur trouverMeilleureOffre(List<Joueur> candidats) {
		Joueur meilleur = candidats.get(0);
		for (Joueur j : candidats) {
			Carte c1 = j.getCarteVisibleDeLOffre();
			Carte c2 = meilleur.getCarteVisibleDeLOffre();
			if (c1 != null && (c2 == null || c1.estSuperieureA(c2))) {
				meilleur = j;
			}
		}
		return meilleur;
	}

	private void terminerPartie() {
		partieTerminee = true;
		phaseChoix = false;

		// Récupérer les dernières cartes
		for (Joueur j : joueurs) {
			j.recupererDerniereCarteDeLOffre();
		}

		// Attribuer les trophées
		attribuerTrophees();

		// Calculer les scores
		VisitorScore calc = new VisitorScore();
		for (Joueur j : joueurs) {
			j.accept(calc);
		}
		variante.appliquerReglesFinales(joueurs);

		// Afficher les résultats
		mainFrame.showResult();
	}

	private void attribuerTrophees() {
		for (Carte trophee : trophees) {
			Joueur gagnant = null;
			int max = -1;
			for (Joueur j : joueurs) {
				int count = 0;
				if (trophee.estJoker()) {
					for (Carte c : j.getJest()) {
						if (c.getCouleur() == Couleur.COEUR) count++;
					}
				} else {
					for (Carte c : j.getJest()) {
						if (c.getValeur() == trophee.getValeur()) count++;
					}
				}
				if (count > max) {
					max = count;
					gagnant = j;
				}
			}
			if (gagnant != null) {
				gagnant.ajouterAuJest(trophee);
			}
		}
	}

	public void retourMenu() {
		mainFrame.showMenu();
	}

	// Getters
	public ArrayList<Joueur> getJoueurs() { return joueurs; }
	public ArrayList<Carte> getTrophees() { return trophees; }
	public JeuCartes getPioche() { return pioche; }
	public int getNumeroManche() { return numeroManche; }
	public Joueur getJoueurActuel() { return joueurActuel; }
	public boolean isPhaseOffre() { return phaseOffre; }
	public boolean isPhaseChoix() { return phaseChoix; }
	public boolean isPartieTerminee() { return partieTerminee; }
	
	// Sauvegarde et chargement
	public void sauvegarderPartie() {
		// Mettre en pause immédiatement
		partiePausee = true;
		
		String nom = JOptionPane.showInputDialog(mainFrame, "Nom de la sauvegarde :", "Sauvegarde", JOptionPane.PLAIN_MESSAGE);
		if (nom != null && !nom.trim().isEmpty()) {
			EtatPartie etat = new EtatPartie();
			etat.setJoueurs(joueurs);
			etat.setPioche(pioche.getTasCartes());
			etat.setTrophees(trophees);
			etat.setNumeroManche(numeroManche);
			etat.setNomVariante(variante.getNom());
			etat.setPartieTerminee(partieTerminee);
			
			if (GestionnaireSauvegarde.sauvegarder(etat, nom.trim())) {
				// Demander si le joueur veut continuer ou quitter
				int choix = JOptionPane.showOptionDialog(
					mainFrame,
					"Partie sauvegardée avec succès !\n\nVoulez-vous continuer la partie ?",
					"Sauvegarde",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[]{"Continuer", "Quitter"},
					"Continuer"
				);
				
				if (choix == 1) { // Quitter
					retourMenu();
				} else { // Continuer
					partiePausee = false;
					// Relancer le tour du bot si c'était son tour
					if (joueurActuel instanceof JoueurVirtuel) {
						jouerTourBot();
					}
				}
			} else {
				partiePausee = false;
				JOptionPane.showMessageDialog(mainFrame, "Erreur lors de la sauvegarde.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// Annulation de la sauvegarde
			partiePausee = false;
		}
	}
	
	public void chargerPartie(String nomFichier) {
		Object obj = GestionnaireSauvegarde.charger(nomFichier);
		if (obj instanceof EtatPartie) {
			EtatPartie etat = (EtatPartie) obj;
			
			joueurs = etat.getJoueurs();
			pioche = new JeuCartes();
			pioche.getTasCartes().clear();
			pioche.getTasCartes().addAll(etat.getPioche());
			trophees = etat.getTrophees();
			numeroManche = etat.getNumeroManche();
			partieTerminee = etat.isPartieTerminee();
			
			// Restaurer la variante
			switch (etat.getNomVariante()) {
				case "Sans Trophée":
					variante = new VarianteSansTrophee();
					break;
				case "Double Mise":
					variante = new VarianteDoubleMise();
					break;
				default:
					variante = new VarianteClassique();
			}
			
			joueursAyantJoue.clear();
			phaseOffre = false;
			phaseChoix = true;
			
			System.out.println("Partie chargée : " + nomFichier);
			mainFrame.showGame();
			mainFrame.getGamePanel().updateDisplay();
			mainFrame.getGamePanel().showMessage("Partie chargée - Manche " + numeroManche);
		}
	}
	
	public String[] listerSauvegardes() {
		return GestionnaireSauvegarde.listerSauvegardes();
	}
}
