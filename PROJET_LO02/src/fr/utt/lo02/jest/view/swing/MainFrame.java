package fr.utt.lo02.jest.view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale du jeu Jest (Swing/WindowBuilder compatible).
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
