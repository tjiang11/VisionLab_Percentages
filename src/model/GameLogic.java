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
     * Check which answer choice, "Yes" or "No", is correct.
     * @param dotsPair The current DotsPair being evaluated.
     * @param blockMode The current block mode to evaluate by.
     * @return true if "Yes" is correct. false if "No" is correct.
     */
    public static boolean checkWhichSideCorrect(DotsPair dotsPair, int blockMode) {
        boolean yesCorrect = false;
        switch (blockMode) {
        case DotsPairGenerator.MORE_THAN_FIFTY_BLOCK:
        case DotsPairGenerator.MORE_THAN_HALF_BLOCK:
            if (dotsPair.getDotSetOne().getTotalNumDots() > dotsPair.getDotSetTwo().getTotalNumDots()) {
                yesCorrect = true;
            } else {
                yesCorrect = false;
            }
            break;
        case DotsPairGenerator.MORE_THAN_SIXTY_BLOCK:
            if ((double) dotsPair.getDotSetOne().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                            + dotsPair.getDotSetTwo().getTotalNumDots()) > .6) {
                yesCorrect = true;
            } else {
                yesCorrect = false;
            }
            break;
        case DotsPairGenerator.MORE_THAN_SEVENTYFIVE_BLOCK:
            if ((double) dotsPair.getDotSetOne().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                            + dotsPair.getDotSetTwo().getTotalNumDots()) > .75) {
                yesCorrect = true;
            } else {
                yesCorrect = false;
            }
            break;
        }
        return yesCorrect;
    }
    /**
     * Checks whether subject's answer is correct or incorrect.
     * @param e The key event to check which key the user pressed.
     * @param dotsPair The current DotsPair being evaluated.
     * @return correct True if correct, false otherwise.
     */
    public static boolean checkAnswerCorrect(KeyEvent e, boolean yesCorrect, boolean FforTrue) {
        boolean correct;
        if ((yesCorrect && e.getCode() == KeyCode.F)
                || !yesCorrect && e.getCode() == KeyCode.J) {
            correct = true;
        } else {  
            correct = false;     
        } 
        if (!FforTrue) {
            return !correct;
        }
        return correct;
    }
}
