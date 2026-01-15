package fr.utt.lo02.jest.view.swing;

import javax.swing.*;
import java.awt.*;
import fr.utt.lo02.jest.controller.swing.SwingController;

/**
 * ╔══════════════════════════════════════════════════════════════════════════╗
 * ║                         🎮 PROJET LO02 - JEST 🎮                         ║
 * ║                         Jeu de Cartes Stratégique                        ║
 * ╚══════════════════════════════════════════════════════════════════════════╝
 * 
 * Fenêtre principale de l'interface graphique Swing du jeu Jest.
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
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private JPanel contentPane;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private ResultPanel resultPanel;
	private SwingController controller;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public MainFrame() {
		controller = new SwingController(this);
		
		setTitle("Jest - Le Jeu de Cartes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setMinimumSize(new Dimension(800, 600));
		
		cardLayout = new CardLayout();
		contentPane = new JPanel(cardLayout);
		contentPane.setBackground(new Color(26, 71, 42));
		setContentPane(contentPane);
		
		// Créer les panels
		menuPanel = new MenuPanel(controller);
		gamePanel = new GamePanel(controller);
		resultPanel = new ResultPanel(controller);
		
		// Ajouter au CardLayout
		contentPane.add(menuPanel, "MENU");
		contentPane.add(gamePanel, "GAME");
		contentPane.add(resultPanel, "RESULT");
		
		// Afficher le menu
		showMenu();
	}

	public void showMenu() {
		cardLayout.show(contentPane, "MENU");
	}

	public void showGame() {
		cardLayout.show(contentPane, "GAME");
	}

	public void showResult() {
		resultPanel.afficherResultats();
		cardLayout.show(contentPane, "RESULT");
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public ResultPanel getResultPanel() {
		return resultPanel;
	}
}
