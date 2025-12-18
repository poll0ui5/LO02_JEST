package fr.utt.lo02.jest.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import fr.utt.lo02.jest.model.*;
import java.util.ArrayList;

/**
 * Écran des résultats de la partie.
 * @author Projet LO02
 */
public class ResultScreen extends BorderPane {
    
    private GameController controller;
    
    public ResultScreen(GameController controller) {
        this.controller = controller;
        this.setStyle("-fx-background-color: #1a472a;");
        
        createUI();
    }
    
    private void createUI() {
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));
        
        // Titre
        Label title = new Label("FIN DE PARTIE");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setStyle("-fx-text-fill: gold;");
        
        // Gagnant
        Joueur gagnant = controller.getGagnant();
        Label gagnantLabel = new Label("🏆 " + gagnant.getNom() + " gagne avec " + gagnant.getScore() + " points! 🏆");
        gagnantLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        gagnantLabel.setStyle("-fx-text-fill: #ffd700;");
        
        // Tableau des scores
        VBox scoresBox = createScoresTable();
        
        // Boutons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        Button rejouerBtn = new Button("Rejouer");
        rejouerBtn.setStyle("-fx-background-color: gold; -fx-text-fill: #1a472a; -fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10 30;");
        rejouerBtn.setOnAction(e -> controller.demarrerPartie());
        
        Button menuBtn = new Button("Menu Principal");
        menuBtn.setStyle("-fx-background-color: #666; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 30;");
        menuBtn.setOnAction(e -> controller.retourMenu());
        
        buttons.getChildren().addAll(rejouerBtn, menuBtn);
        
        content.getChildren().addAll(title, gagnantLabel, scoresBox, buttons);
        this.setCenter(content);
    }
    
    private VBox createScoresTable() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10;");
        box.setMaxWidth(600);
        
        Label tableTitle = new Label("Classement Final");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        tableTitle.setStyle("-fx-text-fill: white;");
        box.getChildren().add(tableTitle);
        
        // Trier les joueurs par score décroissant
        ArrayList<Joueur> joueurs = new ArrayList<>(controller.getJoueurs());
        joueurs.sort((a, b) -> b.getScore() - a.getScore());
        
        int rank = 1;
        for (Joueur j : joueurs) {
            HBox row = new HBox(20);
            row.setAlignment(Pos.CENTER);
            row.setPadding(new Insets(10));
            
            String medal = "";
            if (rank == 1) medal = "🥇 ";
            else if (rank == 2) medal = "🥈 ";
            else if (rank == 3) medal = "🥉 ";
            
            Label rankLabel = new Label(medal + rank + ".");
            rankLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-min-width: 60;");
            
            Label nameLabel = new Label(j.getNom());
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 150;");
            
            Label scoreLabel = new Label(j.getScore() + " pts");
            scoreLabel.setStyle("-fx-text-fill: gold; -fx-font-size: 18px; -fx-min-width: 80;");
            
            Label jestLabel = new Label("(" + j.getJest().size() + " cartes)");
            jestLabel.setStyle("-fx-text-fill: #aaa; -fx-font-size: 14px;");
            
            row.getChildren().addAll(rankLabel, nameLabel, scoreLabel, jestLabel);
            box.getChildren().add(row);
            rank++;
        }
        
        return box;
    }
}
