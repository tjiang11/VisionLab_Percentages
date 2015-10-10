package main;

import view.GameGUI;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * @author Tony Jiang
 * 6-25-2015
 *
 * This is an assessment that tests the subject on comparatives.
 * 
 * There are four "blocks":
 * 
 * Block 0: Is Blue more than Yellow?
 * Block 1: Is Green more than 50% of the total?
 * Block 2: Is Purple more than 60% of the total?
 * Block 3: is Cyan more than 75% of the total?
 *
 * The subject is to go through one block at a time
 * in a random order.
 * 
 * The F and J keys are for Yes and No. Which key is true
 * is randomly decided at the beginning of the 
 * assessment.
 * 
 * This assessment was created by adapting the Dots game
 * in the DotsLettersNumbers assessments on the VisionLab SVN.
 * 
 * Model-View-Controller architecture
 * 
 * Here, models include the player and the dots.
 * The view consists of the GUI.
 * the controller controls the flow of the game, as well as setting input handlers.
 * 
 * The structure of the models from higher to lower:
 * (DotsGameController) > DotsPairGenerator > DotsPair > DotSet > Coordinate
 *                                          > Ratio
 *                      > Player
 *                      > DataWriter
 * Strucuture of view classes:
 * (DotsGameController) > GameGUI > SetUp
 */
public class PercentsMain extends Application {
    
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
