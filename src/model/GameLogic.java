package model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Utility class for the game logic.
 * 
 * @author Tony Jiang
 * 6-25-2015
 *
 */
public final class GameLogic {
    
    /**
     * Checks whether subject's answer is correct or incorrect.
     * @param e The key event to check which key the user pressed.
     * @param dotsPair The current AlphaPair being evaluated.
     * @return correct True if correct, false otherwise.
     */
    public static boolean checkAnswerCorrect(KeyEvent e, DotsPair dotsPair, boolean questionReversed) {
        boolean correct;
        if ((dotsPair.isLeftCorrect() && e.getCode() == KeyCode.F)
                || !dotsPair.isLeftCorrect() && e.getCode() == KeyCode.J) {
            correct = true;
        } else {  
            correct = false;     
        } 
        if (questionReversed) {
            if (correct) {
                correct = false;
            } else {
                correct = true;
            }
        }
        return correct;
    }
}
