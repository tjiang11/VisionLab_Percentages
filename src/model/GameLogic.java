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
    public static boolean checkAnswerCorrect(KeyEvent e, DotsPair dotsPair, boolean questionReversed, int blockMode) {
        boolean yesCorrect = false;
        switch (blockMode) {
        case DotsPairGenerator.MORE_THAN_FIFTY_BLOCK:
        case DotsPairGenerator.MORE_THAN_HALF_BLOCK:
            if (dotsPair.getDotSetOne().getTotalNumDots() > dotsPair.getDotSetTwo().getTotalNumDots()) {
                if (!questionReversed) {
                    yesCorrect = true;
                } else {
                    yesCorrect = false;
                }
            } else {
                if (!questionReversed) {
                    yesCorrect = false;
                } else {
                    yesCorrect = true;
                }
            }
            break;
        case DotsPairGenerator.MORE_THAN_SIXTY_BLOCK:
            if (!questionReversed 
                    && (double) dotsPair.getDotSetOne().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                            + dotsPair.getDotSetTwo().getTotalNumDots()) > .6) {
                yesCorrect = true;
            } else if (questionReversed
                    && (double) dotsPair.getDotSetTwo().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                        + dotsPair.getDotSetTwo().getTotalNumDots()) > .6){
                yesCorrect = true;
            } else {
                yesCorrect = false;
                System.out.println("false");
            }
            break;
        case DotsPairGenerator.MORE_THAN_SEVENTYFIVE_BLOCK:
            if (!questionReversed 
                    && (double) dotsPair.getDotSetOne().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                            + dotsPair.getDotSetTwo().getTotalNumDots()) > .75) {
                yesCorrect = true;
            } else if (questionReversed
                    && (double) dotsPair.getDotSetTwo().getTotalNumDots() / (dotsPair.getDotSetOne().getTotalNumDots() 
                        + dotsPair.getDotSetTwo().getTotalNumDots()) > .75){
                yesCorrect = true;
            } else {
                yesCorrect = false;
                System.out.println("false");
            }
            break;
        }
        
        boolean correct;
        if ((yesCorrect && e.getCode() == KeyCode.F)
                || !yesCorrect && e.getCode() == KeyCode.J) {
            correct = true;
        } else {  
            correct = false;     
        } 
        return correct;
    }
}
