package fr.utt.lo02.jest.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import fr.utt.lo02.jest.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Écran principal du jeu.
 * @author Projet LO02
 */
public class GameScreen extends BorderPane {
    
    private GameController controller;
    private Label messageLabel;
    private HBox tropheeBox;
    private VBox[] playerAreas;
    private Map<String, Image> cardImages;
    private Image backCard;
    
    public GameScreen(GameController controller) {
        this.controller = controller;
        controller.setGameScreen(this);
        this.setStyle("-fx-background-color: #1a472a;");
        
        loadCardImages();
        createUI();
        
        // Démarrer la première manche
        controller.demarrerManche();
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
                    cardImages.put(key, new Image(getClass().getResourceAsStream(basePath + key + ".png")));
                } catch (Exception e) {
                    System.out.println("Image non trouvée: " + key);
                }
            }
        }
        
        // Joker et dos de carte
        try {
            cardImages.put("JOKER", new Image(getClass().getResourceAsStream(basePath + "JOKER.png")));
            backCard = new Image(getClass().getResourceAsStream(basePath + "BACK_CARD.png"));
        } catch (Exception e) {
            System.out.println("Image Joker/Back non trouvée");
        }
    }
    
    private void createUI() {
        // Header avec infos
        VBox header = createHeader();
        this.setTop(header);
        
        // Zone centrale avec les joueurs
        GridPane center = createPlayersArea();
        this.setCenter(center);
        
        // Footer avec message
        HBox footer = createFooter();
        this.setBottom(footer);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #0d2818;");
        
        // Titre manche
        Label mancheLabel = new Label("Manche " + controller.getNumeroManche());
        mancheLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        mancheLabel.setStyle("-fx-text-fill: gold;");
        
        // Trophées
        tropheeBox = new HBox(10);
        tropheeBox.setAlignment(Pos.CENTER);
        Label tropheeTitle = new Label("Trophées: ");
        tropheeTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        tropheeBox.getChildren().add(tropheeTitle);
        
        for (Carte trophee : controller.getTrophees()) {
            ImageView iv = createCardView(trophee, true);
            iv.setFitHeight(80);
            iv.setPreserveRatio(true);
            tropheeBox.getChildren().add(iv);
        }
        
        header.getChildren().addAll(mancheLabel, tropheeBox);
        return header;
    }
    
    private GridPane createPlayersArea() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(20));
        
        ArrayList<Joueur> joueurs = controller.getJoueurs();
        playerAreas = new VBox[joueurs.size()];
        
        // Disposition selon le nombre de joueurs
        int[][] positions = joueurs.size() == 3 
            ? new int[][]{{1, 0}, {0, 1}, {2, 1}}  // Triangle
            : new int[][]{{1, 0}, {0, 1}, {2, 1}, {1, 2}};  // Carré
        
        for (int i = 0; i < joueurs.size(); i++) {
            playerAreas[i] = createPlayerArea(joueurs.get(i));
            grid.add(playerAreas[i], positions[i][0], positions[i][1]);
        }
        
        return grid;
    }
    
    private VBox createPlayerArea(Joueur joueur) {
        VBox area = new VBox(10);
        area.setAlignment(Pos.CENTER);
        area.setPadding(new Insets(15));
        area.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10;");
        area.setMinWidth(250);
        
        // Nom du joueur
        Label nomLabel = new Label(joueur.getNom());
        nomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        boolean isCurrentPlayer = joueur == controller.getJoueurActuel();
        nomLabel.setStyle("-fx-text-fill: " + (isCurrentPlayer ? "gold" : "white") + ";");
        
        // Indicateur si c'est son tour
        if (isCurrentPlayer) {
            nomLabel.setText("► " + joueur.getNom() + " ◄");
        }
        
        // Cartes en main (si c'est un humain et qu'il doit faire son offre)
        HBox mainBox = new HBox(10);
        mainBox.setAlignment(Pos.CENTER);
        
        if (joueur instanceof JoueurHumain && joueur.getMain().size() > 0) {
            Label mainLabel = new Label("Votre main:");
            mainLabel.setStyle("-fx-text-fill: white;");
            area.getChildren().add(mainLabel);
            
            for (int i = 0; i < joueur.getMain().size(); i++) {
                Carte carte = joueur.getMain().get(i);
                ImageView iv = createCardView(carte, true);
                iv.setFitHeight(120);
                iv.setPreserveRatio(true);
                
                final int index = i;
                Button btn = new Button();
                btn.setGraphic(iv);
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                btn.setOnAction(e -> controller.joueurFaitOffre(joueur, index));
                
                mainBox.getChildren().add(btn);
            }
        }
        
        // Offre du joueur
        HBox offreBox = new HBox(15);
        offreBox.setAlignment(Pos.CENTER);
        
        CarteOffre[] offre = joueur.getOffre();
        if (offre[0] != null || offre[1] != null) {
            Label offreLabel = new Label("Offre:");
            offreLabel.setStyle("-fx-text-fill: white;");
            
            for (int i = 0; i < 2; i++) {
                if (offre[i] != null) {
                    boolean visible = offre[i].getEstVisible();
                    ImageView iv = createCardView(offre[i], visible);
                    iv.setFitHeight(100);
                    iv.setPreserveRatio(true);
                    
                    // Si c'est le tour d'un humain de prendre une carte
                    Joueur actuel = controller.getJoueurActuel();
                    if (actuel instanceof JoueurHumain && actuel != joueur) {
                        final int cardIndex = i;
                        final Joueur cible = joueur;
                        Button btn = new Button();
                        btn.setGraphic(iv);
                        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-color: gold; -fx-border-width: 2;");
                        btn.setOnAction(e -> controller.joueurPrendCarte(cible, cardIndex));
                        offreBox.getChildren().add(btn);
                    } else {
                        offreBox.getChildren().add(iv);
                    }
                }
            }
            
            VBox offreContainer = new VBox(5, offreLabel, offreBox);
            offreContainer.setAlignment(Pos.CENTER);
            area.getChildren().add(offreContainer);
        }
        
        // Jest (cartes gagnées)
        if (!joueur.getJest().isEmpty()) {
            Label jestLabel = new Label("Jest: " + joueur.getJest().size() + " cartes");
            jestLabel.setStyle("-fx-text-fill: #aaa; -fx-font-size: 12px;");
            area.getChildren().add(jestLabel);
        }
        
        if (!mainBox.getChildren().isEmpty()) {
            area.getChildren().add(mainBox);
        }
        
        area.getChildren().add(0, nomLabel);
        
        return area;
    }
    
    private ImageView createCardView(Carte carte, boolean visible) {
        Image img = null;
        
        if (!visible) {
            img = backCard;
        } else if (carte.estJoker()) {
            img = cardImages.get("JOKER");
        } else {
            String couleur = carte.getCouleur().name();
            // Convertir les noms français en anglais
            switch (couleur) {
                case "PIQUE": couleur = "SPADES"; break;
                case "COEUR": couleur = "HEARTS"; break;
                case "CARREAU": couleur = "DIAMONDS"; break;
                case "TREFLE": couleur = "CLUBS"; break;
            }
            
            String valeur = carte.getValeur().name();
            switch (valeur) {
                case "AS": valeur = "ACE"; break;
                case "DEUX": valeur = "TWO"; break;
                case "TROIS": valeur = "THREE"; break;
                case "QUATRE": valeur = "FOUR"; break;
            }
            
            img = cardImages.get(couleur + "_" + valeur);
        }
        
        ImageView iv = new ImageView(img);
        iv.setPreserveRatio(true);
        return iv;
    }
    
    private HBox createFooter() {
        HBox footer = new HBox(20);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(15));
        footer.setStyle("-fx-background-color: #0d2818;");
        
        messageLabel = new Label("Bienvenue dans Jest!");
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        messageLabel.setStyle("-fx-text-fill: white;");
        
        // Bouton Menu
        Button menuBtn = new Button("Menu");
        menuBtn.setStyle("-fx-background-color: #666; -fx-text-fill: white;");
        menuBtn.setOnAction(e -> controller.retourMenu());
        
        footer.getChildren().addAll(messageLabel, menuBtn);
        return footer;
    }
    
    public void updateDisplay() {
        // Recréer l'interface
        createUI();
    }
    
    public void showMessage(String msg) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
        }
    }
}
