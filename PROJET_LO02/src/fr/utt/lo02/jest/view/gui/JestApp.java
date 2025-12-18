package fr.utt.lo02.jest.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application principale JavaFX pour le jeu Jest.
 * @author Projet LO02
 */
public class JestApp extends Application {
    
    private Stage primaryStage;
    private GameController gameController;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameController = new GameController(this);
        
        primaryStage.setTitle("Jest - Le Jeu de Cartes");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        
        showMenuScreen();
        primaryStage.show();
    }
    
    public void showMenuScreen() {
        MenuScreen menuScreen = new MenuScreen(gameController);
        Scene scene = new Scene(menuScreen, 1200, 800);
        primaryStage.setScene(scene);
    }
    
    public void showGameScreen() {
        GameScreen gameScreen = new GameScreen(gameController);
        Scene scene = new Scene(gameScreen, 1200, 800);
        primaryStage.setScene(scene);
    }
    
    public void showResultScreen() {
        ResultScreen resultScreen = new ResultScreen(gameController);
        Scene scene = new Scene(resultScreen, 1200, 800);
        primaryStage.setScene(scene);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
