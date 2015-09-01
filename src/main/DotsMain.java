package main;

import view.GameGUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * @author Tony Jiang
 * 6-25-2015
 *
 * Main class for an assessment that measures the Weber Fraction.
 * Given two letters, the subject is to pick the letter that
 * comes later in the alphabet.
 *
 */
public class DotsMain extends Application {
    
    /**
    * Main class.
    * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new GameGUI(primaryStage);
    }
}
