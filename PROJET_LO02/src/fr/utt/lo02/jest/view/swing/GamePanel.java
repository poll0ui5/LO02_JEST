package fr.utt.lo02.jest.view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import fr.utt.lo02.jest.model.*;
import fr.utt.lo02.jest.controller.swing.SwingController;

/**
 * Panel principal du jeu avec affichage des cartes et des actions.
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
public class GamePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private SwingController controller;
	private GameModel gameModel;
	private JLabel lblMessage;
	private JLabel lblManche;
	private JLabel lblPioche;
	private JPanel panelTrophees;
	private JPanel panelJoueurs;
	private JButton btnSauvegarder;
	private Map<String, ImageIcon> cardImages;
	private ImageIcon backCardImage;

	public GamePanel(SwingController controller) {
		this.controller = controller;
		setBackground(new Color(26, 71, 42));
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		// Charger les images
		loadCardImages();
		
		// Header
		JPanel panelHeader = new JPanel(new BorderLayout());
		panelHeader.setBackground(new Color(13, 40, 24));
		panelHeader.setBorder(new EmptyBorder(10, 20, 10, 20));
		
		JPanel panelLeft = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		panelLeft.setOpaque(false);
		
		lblManche = new JLabel("Manche 1");
		lblManche.setFont(new Font("Arial", Font.BOLD, 24));
		lblManche.setForeground(new Color(255, 215, 0));
		panelLeft.add(lblManche);
		
		lblPioche = new JLabel("Pioche: 17 cartes");
		lblPioche.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPioche.setForeground(new Color(200, 200, 200));
		panelLeft.add(Box.createVerticalStrut(5));
		panelLeft.add(lblPioche);
		
		panelHeader.add(panelLeft, BorderLayout.WEST);
		
		panelTrophees = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelTrophees.setOpaque(false);
		panelTrophees.setPreferredSize(new Dimension(800, 110));
		JLabel lblTrophees = new JLabel("Trophées: ");
		lblTrophees.setForeground(Color.WHITE);
		lblTrophees.setFont(new Font("Arial", Font.PLAIN, 16));
		panelTrophees.add(lblTrophees);
		panelHeader.add(panelTrophees, BorderLayout.CENTER);
		
		JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		panelBoutons.setOpaque(false);
		
		btnSauvegarder = new JButton("Sauvegarder");
		btnSauvegarder.addActionListener(e -> controller.sauvegarderPartie());
		panelBoutons.add(btnSauvegarder);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(e -> controller.retourMenu());
		panelBoutons.add(btnMenu);
		
		panelHeader.add(panelBoutons, BorderLayout.EAST);
		
		add(panelHeader, BorderLayout.NORTH);
		
		// Zone centrale pour les joueurs
		panelJoueurs = new JPanel(new GridLayout(2, 2, 20, 20));
		panelJoueurs.setOpaque(false);
		panelJoueurs.setBorder(new EmptyBorder(20, 20, 20, 20));
		add(panelJoueurs, BorderLayout.CENTER);
		
		// Footer avec message
		JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelFooter.setBackground(new Color(13, 40, 24));
		panelFooter.setBorder(new EmptyBorder(10, 20, 10, 20));
		
		lblMessage = new JLabel("Bienvenue dans Jest!");
		lblMessage.setFont(new Font("Arial", Font.PLAIN, 18));
		lblMessage.setForeground(Color.WHITE);
		panelFooter.add(lblMessage);
		
		add(panelFooter, BorderLayout.SOUTH);
	}
	
	public void updateDisplay() {
		// Mettre à jour le numéro de manche
		lblManche.setText("Manche " + controller.getNumeroManche());
		
		// Mettre à jour la pioche
		int nbCartesPioche = controller.getPioche().getTasCartes().size();
		lblPioche.setText("🃏 Pioche: " + nbCartesPioche + " cartes");
		if (nbCartesPioche < 6) {
			lblPioche.setForeground(new Color(255, 100, 100)); // Rouge si peu de cartes
		} else {
			lblPioche.setForeground(new Color(200, 200, 200));
		}
		
		// Mettre à jour les trophées
		panelTrophees.removeAll();
		JLabel lblTrophees = new JLabel("Trophées: ");
		lblTrophees.setForeground(Color.WHITE);
		lblTrophees.setFont(new Font("Arial", Font.PLAIN, 16));
		panelTrophees.add(lblTrophees);
		
		for (Carte trophee : controller.getTrophees()) {
			JLabel lblCarte = createCarteLabel(trophee, true);
			panelTrophees.add(lblCarte);
		}
		
		// Mettre à jour les zones joueurs
		panelJoueurs.removeAll();
		ArrayList<Joueur> joueurs = controller.getJoueurs();
		
		for (Joueur joueur : joueurs) {
			JPanel panelJoueur = createPanelJoueur(joueur);
			panelJoueurs.add(panelJoueur);
		}
		
		panelJoueurs.revalidate();
		panelJoueurs.repaint();
		panelTrophees.revalidate();
		panelTrophees.repaint();
	}
	
	private JPanel createPanelJoueur(Joueur joueur) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(255, 255, 255, 30));
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
		
		boolean isActuel = joueur == controller.getJoueurActuel();
		
		// Nom du joueur
		String nomText = isActuel ? "► " + joueur.getNom() + " ◄" : joueur.getNom();
		JLabel lblNom = new JLabel(nomText);
		lblNom.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNom.setFont(new Font("Arial", Font.BOLD, 18));
		lblNom.setForeground(isActuel ? new Color(255, 215, 0) : Color.WHITE);
		panel.add(lblNom);
		panel.add(Box.createVerticalStrut(10));
		
		// Main du joueur (si humain et a des cartes)
		if (joueur instanceof JoueurHumain && joueur.getMain().size() > 0) {
			JLabel lblMain = new JLabel("Votre main:");
			lblMain.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblMain.setForeground(Color.WHITE);
			panel.add(lblMain);
			
			JPanel panelMain = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
			panelMain.setOpaque(false);
			
			for (int i = 0; i < joueur.getMain().size(); i++) {
				Carte carte = joueur.getMain().get(i);
				JButton btnCarte = createCarteButton(carte, true);
				final int index = i;
				btnCarte.addActionListener(e -> controller.joueurFaitOffre(joueur, index));
				panelMain.add(btnCarte);
			}
			panel.add(panelMain);
			panel.add(Box.createVerticalStrut(10));
		}
		
		// Offre du joueur
		CarteOffre[] offre = joueur.getOffre();
		if (offre[0] != null || offre[1] != null) {
			JLabel lblOffre = new JLabel("Offre:");
			lblOffre.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblOffre.setForeground(Color.WHITE);
			panel.add(lblOffre);
			
			JPanel panelOffre = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
			panelOffre.setOpaque(false);
			
			Joueur actuel = controller.getJoueurActuel();
			boolean peutPrendre = actuel instanceof JoueurHumain && actuel != joueur && controller.isPhaseChoix();
			
			for (int i = 0; i < 2; i++) {
				if (offre[i] != null) {
					boolean visible = offre[i].getEstVisible();
					
					if (peutPrendre) {
						JButton btnCarte = createCarteButton(offre[i], visible);
						btnCarte.setBorder(new LineBorder(new Color(255, 215, 0), 2));
						final int cardIndex = i;
						final Joueur cible = joueur;
						btnCarte.addActionListener(e -> controller.joueurPrendCarte(cible, cardIndex));
						panelOffre.add(btnCarte);
					} else {
						JLabel lblCarte = createCarteLabel(offre[i], visible);
						panelOffre.add(lblCarte);
					}
				}
			}
			panel.add(panelOffre);
		}
		
		// Jest (cartes gagnées)
		if (!joueur.getJest().isEmpty()) {
			panel.add(Box.createVerticalStrut(5));
			
			if (joueur instanceof JoueurHumain) {
				// Pour le joueur humain : afficher toutes les cartes
				JLabel lblJestTitle = new JLabel("Votre Jest ("+joueur.getJest().size()+" cartes):");
				lblJestTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblJestTitle.setForeground(new Color(255, 215, 0));
				lblJestTitle.setFont(new Font("Arial", Font.BOLD, 12));
				panel.add(lblJestTitle);
				
				// Afficher les cartes du Jest
				JPanel panelJest = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 3));
				panelJest.setOpaque(false);
				
				for (Carte carte : joueur.getJest()) {
					JLabel lblCarte = createMiniCarteLabel(carte);
					panelJest.add(lblCarte);
				}
				panel.add(panelJest);
			} else {
				// Pour les bots : juste le nombre
				JLabel lblJest = new JLabel("Jest: " + joueur.getJest().size() + " cartes");
				lblJest.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblJest.setForeground(new Color(170, 170, 170));
				lblJest.setFont(new Font("Arial", Font.PLAIN, 12));
				panel.add(lblJest);
			}
		}
		
		return panel;
	}
	
	private JLabel createCarteLabel(Carte carte, boolean visible) {
		ImageIcon icon = null;
		
		if (!visible && backCardImage != null) {
			icon = backCardImage;
		} else if (visible) {
			String key = getImageKey(carte);
			icon = cardImages.get(key);
		}
		
		if (icon != null) {
			JLabel label = new JLabel(icon);
			label.setBorder(new LineBorder(Color.WHITE, 1));
			return label;
		} else {
			// Fallback texte si image non trouvée
			String text = visible ? getCarteText(carte) : "[???]";
			JLabel label = new JLabel(text);
			label.setFont(new Font("Monospaced", Font.BOLD, 14));
			label.setForeground(visible ? getCarteColor(carte) : Color.GRAY);
			label.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.WHITE, 1),
				new EmptyBorder(5, 10, 5, 10)
			));
			label.setOpaque(true);
			label.setBackground(new Color(255, 255, 255, 200));
			return label;
		}
	}
	
	private JButton createCarteButton(Carte carte, boolean visible) {
		ImageIcon icon = null;
		
		if (!visible && backCardImage != null) {
			icon = backCardImage;
		} else if (visible) {
			String key = getImageKey(carte);
			icon = cardImages.get(key);
		}
		
		JButton btn;
		if (icon != null) {
			btn = new JButton(icon);
			btn.setBorder(new LineBorder(Color.WHITE, 2));
		} else {
			// Fallback texte si image non trouvée
			String text = visible ? getCarteText(carte) : "[???]";
			btn = new JButton(text);
			btn.setFont(new Font("Monospaced", Font.BOLD, 14));
			btn.setForeground(visible ? getCarteColor(carte) : Color.GRAY);
			btn.setBackground(new Color(255, 255, 255));
		}
		
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setContentAreaFilled(false);
		return btn;
	}
	
	private JLabel createMiniCarteLabel(Carte carte) {
		// Version mini pour le Jest avec images
		ImageIcon icon = null;
		String key = getImageKey(carte);
		icon = cardImages.get(key);
		
		if (icon != null) {
			// Redimensionner l'image pour le Jest (plus petite)
			Image img = icon.getImage();
			Image scaledImg = img.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImg);
			
			JLabel label = new JLabel(scaledIcon);
			label.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
			return label;
		} else {
			// Fallback texte si image non trouvée
			String text = getCarteText(carte);
			JLabel label = new JLabel(text);
			label.setFont(new Font("Monospaced", Font.PLAIN, 10));
			label.setForeground(getCarteColor(carte));
			label.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.LIGHT_GRAY, 1),
				new EmptyBorder(2, 4, 2, 4)
			));
			label.setOpaque(true);
			label.setBackground(new Color(255, 255, 255, 220));
			return label;
		}
	}
	
	private String getCarteText(Carte carte) {
		if (carte.estJoker()) return "JOKER";
		
		String valeur = "";
		switch (carte.getValeur()) {
			case AS: valeur = "A"; break;
			case DEUX: valeur = "2"; break;
			case TROIS: valeur = "3"; break;
			case QUATRE: valeur = "4"; break;
			default: valeur = "?";
		}
		
		String couleur = "";
		switch (carte.getCouleur()) {
			case PIQUE: couleur = "♠"; break;
			case COEUR: couleur = "♥"; break;
			case CARREAU: couleur = "♦"; break;
			case TREFLE: couleur = "♣"; break;
			default: couleur = "?";
		}
		
		return valeur + couleur;
	}
	
	private Color getCarteColor(Carte carte) {
		if (carte.estJoker()) return Color.BLACK;
		if (carte.getCouleur() == Couleur.COEUR || carte.getCouleur() == Couleur.CARREAU) {
			return Color.RED;
		}
		return Color.BLACK;
	}
	
	private void loadCardImages() {
		cardImages = new HashMap<>();
		String basePath = "/resources/images/";
		
		// Charger les images des cartes
		String[] couleurs = {"SPADES", "CLUBS", "DIAMONDS", "HEARTS"};
		String[] valeurs = {"ACE", "TWO", "THREE", "FOUR"};
		
		for (String couleur : couleurs) {
			for (String valeur : valeurs) {
				String key = couleur + "_" + valeur;
				try {
					java.net.URL imgURL = getClass().getResource(basePath + key + ".png");
					if (imgURL != null) {
						ImageIcon icon = new ImageIcon(imgURL);
						Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
						cardImages.put(key, new ImageIcon(img));
					}
				} catch (Exception e) {
					System.out.println("Image non trouvée: " + key);
				}
			}
		}
		
		// Joker
		try {
			java.net.URL jokerURL = getClass().getResource(basePath + "JOKER.png");
			if (jokerURL != null) {
				ImageIcon icon = new ImageIcon(jokerURL);
				Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
				cardImages.put("JOKER", new ImageIcon(img));
			}
		} catch (Exception e) {
			System.out.println("Image Joker non trouvée");
		}
		
		// Cartes spéciales (Extension)
		try {
			java.net.URL goldenURL = getClass().getResource(basePath + "SPADES_ACE_GOLDEN.png");
			if (goldenURL != null) {
				ImageIcon icon = new ImageIcon(goldenURL);
				Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
				cardImages.put("SPADES_ACE_GOLDEN", new ImageIcon(img));
			}
		} catch (Exception e) {
			System.out.println("Image As Doré non trouvée");
		}
		
		try {
			java.net.URL cursedURL = getClass().getResource(basePath + "HEARTS_FOUR_CURSED.png");
			if (cursedURL != null) {
				ImageIcon icon = new ImageIcon(cursedURL);
				Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
				cardImages.put("HEARTS_FOUR_CURSED", new ImageIcon(img));
			}
		} catch (Exception e) {
			System.out.println("Image Cœur Maudit non trouvée");
		}
		
		// Dos de carte
		try {
			java.net.URL backURL = getClass().getResource(basePath + "BACK_CARD.png");
			if (backURL != null) {
				ImageIcon icon = new ImageIcon(backURL);
				Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
				backCardImage = new ImageIcon(img);
			}
		} catch (Exception e) {
			System.out.println("Image dos de carte non trouvée");
		}
	}
	
	private String getImageKey(Carte carte) {
		if (carte.estJoker()) return "JOKER";
		
		// Vérifier si c'est une carte d'extension
		if (carte instanceof CarteExtension) {
			CarteExtension carteExt = (CarteExtension) carte;
			if ("As Doré".equals(carteExt.getNomSpecial())) {
				return "SPADES_ACE_GOLDEN";
			} else if ("Cœur Maudit".equals(carteExt.getNomSpecial())) {
				return "HEARTS_FOUR_CURSED";
			}
		}
		
		String couleur = "";
		switch (carte.getCouleur()) {
			case PIQUE: couleur = "SPADES"; break;
			case COEUR: couleur = "HEARTS"; break;
			case CARREAU: couleur = "DIAMONDS"; break;
			case TREFLE: couleur = "CLUBS"; break;
		}
		
		String valeur = "";
		switch (carte.getValeur()) {
			case AS: valeur = "ACE"; break;
			case DEUX: valeur = "TWO"; break;
			case TROIS: valeur = "THREE"; break;
			case QUATRE: valeur = "FOUR"; break;
		}
		
		return couleur + "_" + valeur;
	}
	
	public void showMessage(String msg) {
		lblMessage.setText(msg);
	}
	
	/**
	 * Définit le modèle de jeu et s'abonne aux notifications.
	 * 
	 * @param model Le modèle de jeu observable
	 */
	public void setGameModel(GameModel model) {
		if (this.gameModel != null) {
			this.gameModel.removeObserver(this);
		}
		this.gameModel = model;
		if (model != null) {
			model.addObserver(this);
		}
	}
	
	/**
	 * Méthode appelée lorsque le modèle change d'état.
	 * Met à jour l'affichage en fonction de l'événement reçu.
	 * 
	 * @param observable Le modèle qui a changé
	 * @param data L'événement (type de changement)
	 */
	@Override
	public void update(Observable observable, Object data) {
		if (data == null) return;
		
		String event = data.toString();
		switch (event) {
			case "JOUEURS_UPDATED":
			case "OFFRES_UPDATED":
			case "MANCHE_UPDATED":
			case "TROPHEES_UPDATED":
				// Rafraîchir l'affichage complet
				updateDisplay();
				break;
			case "MESSAGE":
				if (gameModel != null) {
					showMessage(gameModel.getMessageActuel());
				}
				break;
			case "PARTIE_TERMINEE":
				// Géré par le controller
				break;
		}
		repaint();
	}
}
