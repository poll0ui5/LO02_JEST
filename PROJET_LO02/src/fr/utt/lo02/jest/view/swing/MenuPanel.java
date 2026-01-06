package fr.utt.lo02.jest.view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel du menu principal (Swing/WindowBuilder compatible).
 */
public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private SwingController controller;
	private JComboBox<String> comboVariante;
	private JComboBox<Integer> comboNbJoueurs;
	private JComboBox<Integer> comboNbHumains;
	private JTextField[] txtNoms;
	private JPanel panelNoms;

	public MenuPanel(SwingController controller) {
		this.controller = controller;
		setBackground(new Color(26, 71, 42));
		setLayout(new BorderLayout(0, 0));
		
		// Panel central avec le formulaire
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
		
		// Logo JEST
		try {
			ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/images/JEST.png"));
			Image logoImg = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
			lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
			centerPanel.add(lblLogo);
		} catch (Exception e) {
			JLabel lblTitre = new JLabel("JEST");
			lblTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblTitre.setFont(new Font("Arial", Font.BOLD, 72));
			lblTitre.setForeground(new Color(255, 215, 0));
			centerPanel.add(lblTitre);
		}
		
		JLabel lblSousTitre = new JLabel("Jest LO02");
		lblSousTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSousTitre.setFont(new Font("Arial", Font.PLAIN, 24));
		lblSousTitre.setForeground(Color.WHITE);
		centerPanel.add(lblSousTitre);
		
		centerPanel.add(Box.createVerticalStrut(40));
		
		// Variante
		JPanel panelVariante = createFormRow("Variante :");
		comboVariante = new JComboBox<>(new String[]{"Classique", "Sans Trophée", "Double Mise"});
		panelVariante.add(comboVariante);
		centerPanel.add(panelVariante);
		centerPanel.add(Box.createVerticalStrut(15));
		
		// Nombre de joueurs
		JPanel panelNbJoueurs = createFormRow("Nombre de joueurs :");
		comboNbJoueurs = new JComboBox<>(new Integer[]{3, 4});
		comboNbJoueurs.addActionListener(e -> updateNbHumains());
		panelNbJoueurs.add(comboNbJoueurs);
		centerPanel.add(panelNbJoueurs);
		centerPanel.add(Box.createVerticalStrut(15));
		
		// Nombre d'humains
		JPanel panelNbHumains = createFormRow("Joueurs humains :");
		comboNbHumains = new JComboBox<>(new Integer[]{1, 2, 3});
		comboNbHumains.addActionListener(e -> updateNomsFields());
		panelNbHumains.add(comboNbHumains);
		centerPanel.add(panelNbHumains);
		centerPanel.add(Box.createVerticalStrut(15));
		
		// Panel pour les noms
		panelNoms = new JPanel();
		panelNoms.setOpaque(false);
		panelNoms.setLayout(new BoxLayout(panelNoms, BoxLayout.Y_AXIS));
		centerPanel.add(panelNoms);
		
		updateNomsFields();
		
		centerPanel.add(Box.createVerticalStrut(30));
		
		// Bouton Jouer
		JButton btnJouer = new JButton("JOUER");
		btnJouer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnJouer.setFont(new Font("Arial", Font.BOLD, 24));
		btnJouer.setBackground(new Color(255, 215, 0));
		btnJouer.setForeground(new Color(26, 71, 42));
		btnJouer.setFocusPainted(false);
		btnJouer.setPreferredSize(new Dimension(200, 60));
		btnJouer.setMaximumSize(new Dimension(200, 60));
		btnJouer.addActionListener(e -> demarrerPartie());
		centerPanel.add(btnJouer);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private JPanel createFormRow(String labelText) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(400, 40));
		
		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Arial", Font.PLAIN, 16));
		label.setForeground(Color.WHITE);
		panel.add(label);
		
		return panel;
	}
	
	private void updateNbHumains() {
		int nbJoueurs = (Integer) comboNbJoueurs.getSelectedItem();
		comboNbHumains.removeAllItems();
		for (int i = 1; i <= nbJoueurs; i++) {
			comboNbHumains.addItem(i);
		}
		updateNomsFields();
	}
	
	private void updateNomsFields() {
		panelNoms.removeAll();
		int nbHumains = comboNbHumains.getSelectedItem() != null ? (Integer) comboNbHumains.getSelectedItem() : 1;
		txtNoms = new JTextField[nbHumains];
		
		for (int i = 0; i < nbHumains; i++) {
			JPanel row = createFormRow("Nom joueur " + (i + 1) + " :");
			txtNoms[i] = new JTextField("Joueur " + (i + 1), 15);
			row.add(txtNoms[i]);
			panelNoms.add(row);
			panelNoms.add(Box.createVerticalStrut(10));
		}
		
		panelNoms.revalidate();
		panelNoms.repaint();
	}
	
	private void demarrerPartie() {
		String variante = (String) comboVariante.getSelectedItem();
		int nbJoueurs = (Integer) comboNbJoueurs.getSelectedItem();
		int nbHumains = (Integer) comboNbHumains.getSelectedItem();
		
		String[] noms = new String[nbHumains];
		for (int i = 0; i < nbHumains; i++) {
			noms[i] = txtNoms[i].getText().trim();
			if (noms[i].isEmpty()) noms[i] = "Joueur " + (i + 1);
		}
		
		controller.configurerPartie(variante, nbJoueurs, nbHumains, noms);
		controller.demarrerPartie();
	}
}
