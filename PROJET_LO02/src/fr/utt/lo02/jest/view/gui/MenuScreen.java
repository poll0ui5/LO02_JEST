package fr.utt.lo02.jest.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Écran du menu principal.
 * @author Projet LO02
 */
public class MenuScreen extends BorderPane {
    
    private GameController controller;
    private ComboBox<String> varianteCombo;
    private ComboBox<Integer> nbJoueursCombo;
    private ComboBox<Integer> nbHumainsCombo;
    private TextField[] nomFields;
    private VBox nomsContainer;
    
    public MenuScreen(GameController controller) {
        this.controller = controller;
        this.setStyle("-fx-background-color: #1a472a;");
        
        // Logo/Titre
        VBox header = createHeader();
        this.setTop(header);
        
        // Formulaire de configuration
        VBox form = createForm();
        this.setCenter(form);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(40, 0, 20, 0));
        
        // Essayer de charger le logo
        try {
            Image logo = new Image(getClass().getResourceAsStream("/resources/images/JEST.png"));
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight(150);
            logoView.setPreserveRatio(true);
            header.getChildren().add(logoView);
        } catch (Exception e) {
            Label title = new Label("JEST");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 72));
            title.setStyle("-fx-text-fill: gold;");
            header.getChildren().add(title);
        }
        
        Label subtitle = new Label("Le Jeu de Cartes");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        subtitle.setStyle("-fx-text-fill: white;");
        header.getChildren().add(subtitle);
        
        return header;
    }
    
    private VBox createForm() {
        VBox form = new VBox(20);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));
        form.setMaxWidth(500);
        
        // Style commun pour les labels
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 16px;";
        
        // Variante
        HBox varianteBox = new HBox(15);
        varianteBox.setAlignment(Pos.CENTER);
        Label varianteLabel = new Label("Variante :");
        varianteLabel.setStyle(labelStyle);
        varianteCombo = new ComboBox<>();
        varianteCombo.getItems().addAll("Classique", "Sans Trophée", "Double Mise");
        varianteCombo.setValue("Classique");
        varianteCombo.setStyle("-fx-font-size: 14px;");
        varianteBox.getChildren().addAll(varianteLabel, varianteCombo);
        
        // Nombre de joueurs
        HBox nbJoueursBox = new HBox(15);
        nbJoueursBox.setAlignment(Pos.CENTER);
        Label nbJoueursLabel = new Label("Nombre de joueurs :");
        nbJoueursLabel.setStyle(labelStyle);
        nbJoueursCombo = new ComboBox<>();
        nbJoueursCombo.getItems().addAll(3, 4);
        nbJoueursCombo.setValue(3);
        nbJoueursCombo.setOnAction(e -> updateNbHumains());
        nbJoueursBox.getChildren().addAll(nbJoueursLabel, nbJoueursCombo);
        
        // Nombre d'humains
        HBox nbHumainsBox = new HBox(15);
        nbHumainsBox.setAlignment(Pos.CENTER);
        Label nbHumainsLabel = new Label("Joueurs humains :");
        nbHumainsLabel.setStyle(labelStyle);
        nbHumainsCombo = new ComboBox<>();
        nbHumainsCombo.getItems().addAll(1, 2, 3);
        nbHumainsCombo.setValue(1);
        nbHumainsCombo.setOnAction(e -> updateNomsFields());
        nbHumainsBox.getChildren().addAll(nbHumainsLabel, nbHumainsCombo);
        
        // Container pour les noms
        nomsContainer = new VBox(10);
        nomsContainer.setAlignment(Pos.CENTER);
        updateNomsFields();
        
        // Bouton Jouer
        Button playButton = new Button("JOUER");
        playButton.setStyle("-fx-background-color: gold; -fx-text-fill: #1a472a; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 15 50;");
        playButton.setOnAction(e -> startGame());
        
        // Ajouter un effet hover
        playButton.setOnMouseEntered(e -> playButton.setStyle("-fx-background-color: #ffd700; -fx-text-fill: #1a472a; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 15 50; -fx-cursor: hand;"));
        playButton.setOnMouseExited(e -> playButton.setStyle("-fx-background-color: gold; -fx-text-fill: #1a472a; -fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 15 50;"));
        
        form.getChildren().addAll(varianteBox, nbJoueursBox, nbHumainsBox, nomsContainer, playButton);
        
        // Centrer le formulaire
        VBox centerWrapper = new VBox(form);
        centerWrapper.setAlignment(Pos.CENTER);
        
        return centerWrapper;
    }
    
    private void updateNbHumains() {
        int nbJoueurs = nbJoueursCombo.getValue();
        nbHumainsCombo.getItems().clear();
        for (int i = 1; i <= nbJoueurs; i++) {
            nbHumainsCombo.getItems().add(i);
        }
        nbHumainsCombo.setValue(1);
        updateNomsFields();
    }
    
    private void updateNomsFields() {
        nomsContainer.getChildren().clear();
        int nbHumains = nbHumainsCombo.getValue();
        nomFields = new TextField[nbHumains];
        
        String labelStyle = "-fx-text-fill: white; -fx-font-size: 14px;";
        
        for (int i = 0; i < nbHumains; i++) {
            HBox nomBox = new HBox(10);
            nomBox.setAlignment(Pos.CENTER);
            Label nomLabel = new Label("Nom joueur " + (i+1) + " :");
            nomLabel.setStyle(labelStyle);
            nomFields[i] = new TextField("Joueur " + (i+1));
            nomFields[i].setStyle("-fx-font-size: 14px;");
            nomFields[i].setPrefWidth(200);
            nomBox.getChildren().addAll(nomLabel, nomFields[i]);
            nomsContainer.getChildren().add(nomBox);
        }
    }
    
    private void startGame() {
        // Récupérer les valeurs
        controller.setVariante(varianteCombo.getValue());
        controller.setNbJoueurs(nbJoueursCombo.getValue());
        controller.setNbHumains(nbHumainsCombo.getValue());
        
        // Récupérer les noms
        String[] noms = new String[nomFields.length];
        for (int i = 0; i < nomFields.length; i++) {
            noms[i] = nomFields[i].getText().trim();
            if (noms[i].isEmpty()) noms[i] = "Joueur " + (i+1);
        }
        controller.setNomsJoueurs(noms);
        
        // Démarrer la partie
        controller.demarrerPartie();
    }
}
