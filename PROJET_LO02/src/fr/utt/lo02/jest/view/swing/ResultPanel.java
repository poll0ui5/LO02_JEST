package fr.utt.lo02.jest.view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import fr.utt.lo02.jest.model.Joueur;

/**
 * Panel des résultats (Swing/WindowBuilder compatible).
 */
public class ResultPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private SwingController controller;
	private JPanel panelScores;

	public ResultPanel(SwingController controller) {
		this.controller = controller;
		setBackground(new Color(26, 71, 42));
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(50, 50, 50, 50));
		
		// Titre
		JLabel lblTitre = new JLabel("Résultats", SwingConstants.CENTER);
		lblTitre.setFont(new Font("Arial", Font.BOLD, 48));
		lblTitre.setForeground(new Color(255, 215, 0));
		add(lblTitre, BorderLayout.NORTH);
		
		// Panel central pour les scores
		panelScores = new JPanel();
		panelScores.setOpaque(false);
		panelScores.setLayout(new BoxLayout(panelScores, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(panelScores);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		
		// Bouton rejouer
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		panelButtons.setOpaque(false);
		
		JButton btnRejouer = new JButton("Nouvelle Partie");
		btnRejouer.setFont(new Font("Arial", Font.BOLD, 18));
		btnRejouer.setBackground(new Color(255, 215, 0));
		btnRejouer.setForeground(new Color(26, 71, 42));
		btnRejouer.setFocusPainted(false);
		btnRejouer.addActionListener(e -> controller.retourMenu());
		panelButtons.add(btnRejouer);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setFont(new Font("Arial", Font.BOLD, 18));
		btnQuitter.addActionListener(e -> System.exit(0));
		panelButtons.add(btnQuitter);
		
		add(panelButtons, BorderLayout.SOUTH);
	}
	
	public void afficherResultats() {
		panelScores.removeAll();
		panelScores.add(Box.createVerticalStrut(30));
		
		ArrayList<Joueur> joueurs = controller.getJoueurs();
		
		// Trier par score décroissant
		ArrayList<Joueur> joueursTriés = new ArrayList<>(joueurs);
		joueursTriés.sort(Comparator.comparingInt(Joueur::getScore).reversed());
		
		boolean first = true;
		for (Joueur joueur : joueursTriés) {
			JPanel panelJoueur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
			panelJoueur.setOpaque(false);
			panelJoueur.setMaximumSize(new Dimension(500, 60));
			
			// Médaille pour le gagnant
			String prefix = first ? "🏆 " : "   ";
			first = false;
			
			JLabel lblNom = new JLabel(prefix + joueur.getNom());
			lblNom.setFont(new Font("Arial", Font.BOLD, 24));
			lblNom.setForeground(Color.WHITE);
			lblNom.setPreferredSize(new Dimension(250, 40));
			panelJoueur.add(lblNom);
			
			JLabel lblScore = new JLabel(joueur.getScore() + " pts");
			lblScore.setFont(new Font("Arial", Font.BOLD, 24));
			lblScore.setForeground(new Color(255, 215, 0));
			panelJoueur.add(lblScore);
			
			JLabel lblCartes = new JLabel("(" + joueur.getJest().size() + " cartes)");
			lblCartes.setFont(new Font("Arial", Font.PLAIN, 16));
			lblCartes.setForeground(new Color(170, 170, 170));
			panelJoueur.add(lblCartes);
			
			panelScores.add(panelJoueur);
			panelScores.add(Box.createVerticalStrut(10));
		}
		
		panelScores.revalidate();
		panelScores.repaint();
	}
}
