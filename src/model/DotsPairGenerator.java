package model;

import java.util.ArrayList;
import java.util.Random;

import controller.DotsGameController;

/**
 * Generates DotsPairs with random numbers of dots.
 * 
 * Classes Related To:
 *  -DotsPair.java
 * 
 * @author Tony Jiang
 * 6-25-2015
 *
 */
public class DotsPairGenerator {
    
    /** Maximum number of total dots to be shown in one trial. */
    static final int MAX_DOTS = 60;
    /** Minimum number of total dots to be shown in one trial. */
    static final int MIN_DOTS = 20;
    
    /** Max number of times the same side may be the correct choice. */
    static final int MAX_TIMES_SAME_ANSWER = 3;
    
    /** Max number of times the same relative size (or control type) may be the correct choice. */
    static final int MAX_TIMES_SAME_SIZE_CORRECT = 3;
    
    /** Map from each block to an integer representation. */
    public static final int MORE_THAN_HALF_BLOCK = 0;
    public static final int MORE_THAN_FIFTY_BLOCK = 1;
    public static final int MORE_THAN_SIXTY_BLOCK = 2;
    public static final int MORE_THAN_SEVENTYFIVE_BLOCK = 3;    
    
    /** Define the lowest distance (in number of letters) each difficulty can have. */
    public static final int EASY_MODE_MIN = 14;
    public static final int MEDIUM_MODE_MIN = 8;
    public static final int HARD_MODE_MIN = 2;
    
    /** The highest distance each difficulty can have is their minimum plus NUM_CHOICES_IN_MODE. */
    public static final int NUM_CHOICES_IN_MODE = 4;
    
    /**
     * Number of triplets of modes per set. See fillDifficultySet().
     */
    static final int NUM_MODE_TRIPLETS = 2;
    
    /** Random number generator. */
    Random randomGenerator = new Random();

    /** The most recent DotsPair produced by DotsPairGenerator. */
    private DotsPair dotsPair; 
    
    /** The current block setting */
    private int blockMode;
    
    /** List containing the blocks. */
    private ArrayList<Integer> blockSet;
    
    /** List containing Ratios for the current block. */
    private ArrayList<Ratio> ratiosBucket;
    
    /** A measure of how many times the same side has been correct. */
    private int sameChoiceCorrect;
    
    /** A measure of how many times the same size has been correct, meaning
     how many times has the cluster with bigger/smaller individual dots been correct.
     Depends on area control type --> 
         Equal Areas <--> Smaller Correct  
         Inverse Areas <--> Bigger Correct */
    private int sameSizeCorrect;
    
    /** True if the last correct choice was left. False otherwise. */
    private boolean lastWasLeft;
    
    /** True if the last correct choice was the cluster with bigger individual dots. */
    private boolean lastWasBig;
    
    /**
     * Constructor. 
     */
    public DotsPairGenerator() {
        this.setSameChoice(0);
        this.setLastWasLeft(false);
        this.setLastWasBig(false);
        this.blockSet = new ArrayList<Integer>();
        this.ratiosBucket = new ArrayList<Ratio>();
        this.fillBlockSet();
    }
    
    /**
     * Fill the block set in a random manner.
     */
    private void fillBlockSet() {
        ArrayList<Integer> tempSet = new ArrayList<Integer>();
        tempSet.add(MORE_THAN_HALF_BLOCK);
        tempSet.add(MORE_THAN_FIFTY_BLOCK);
        tempSet.add(MORE_THAN_SIXTY_BLOCK);
        tempSet.add(MORE_THAN_SEVENTYFIVE_BLOCK);
        int size = tempSet.size();
        for (int i = 0; i < size; i++) {
            this.blockSet.add((Integer) tempSet.remove(randomGenerator.nextInt(tempSet.size())));
            System.out.println(this.blockSet.toString());
        }
        this.blockMode = this.blockSet.get(0);
    }
    
    /** 
     * Get a new pair based on current mode. 
     */
    public void getNewModePair() {
        Ratio ratio = this.decideRatio();
        this.getNewPair(ratio);
    }
    
    /** Clear the ratios */
    public void clearRatios() {
        this.ratiosBucket.clear();
    }
    
    private Ratio decideRatio() {
        if (this.ratiosBucket.isEmpty()) {
            fillRatiosBucket();
        }
        Ratio ratio = this.ratiosBucket.remove(randomGenerator.nextInt(this.ratiosBucket.size()));
        return ratio;
    }
    
    private void fillRatiosBucket() {
        switch (this.blockMode) {
        case MORE_THAN_FIFTY_BLOCK:
        case MORE_THAN_HALF_BLOCK:
                this.ratiosBucket.add(new Ratio(9,16));
                this.ratiosBucket.add(new Ratio(2,3));
                this.ratiosBucket.add(new Ratio(11,14));
                this.ratiosBucket.add(new Ratio(12,13));
                this.ratiosBucket.add(new Ratio(13,12));
                this.ratiosBucket.add(new Ratio(14,11));
                this.ratiosBucket.add(new Ratio(3,2));
                this.ratiosBucket.add(new Ratio(16,9));
            break;
        case MORE_THAN_SIXTY_BLOCK:
                this.ratiosBucket.add(new Ratio(11,14));
                this.ratiosBucket.add(new Ratio(12,13));
                this.ratiosBucket.add(new Ratio(13,12));
                this.ratiosBucket.add(new Ratio(4,3));
                this.ratiosBucket.add(new Ratio(5,3));
                this.ratiosBucket.add(new Ratio(13,7));
                this.ratiosBucket.add(new Ratio(18,7));
                this.ratiosBucket.add(new Ratio(19,6));
            break;
        case MORE_THAN_SEVENTYFIVE_BLOCK:
                this.ratiosBucket.add(new Ratio(7,6));
                this.ratiosBucket.add(new Ratio(3,2));
                this.ratiosBucket.add(new Ratio(2,1));
                this.ratiosBucket.add(new Ratio(18,7));
                this.ratiosBucket.add(new Ratio(18,5));
                this.ratiosBucket.add(new Ratio(21,4));
                this.ratiosBucket.add(new Ratio(9,1));
                this.ratiosBucket.add(new Ratio(24,1));
            break;
        }
        System.out.println(this.ratiosBucket.toString());
    }
    
    private void getNewPair(Ratio ratio) {
        int ratioNumOne = ratio.getNumOne();
        int ratioNumTwo = ratio.getNumTwo();
        int numDotsOne = ratio.getNumOne();
        int numDotsTwo = ratio.getNumTwo();
        while (numDotsOne + numDotsTwo < MIN_DOTS) {
            numDotsOne += ratioNumOne;
            numDotsTwo += ratioNumTwo;
        }
        int max = (MAX_DOTS - (numDotsOne + numDotsTwo)) / (ratioNumOne + ratioNumTwo);
        int randMax = randomGenerator.nextInt(max);
        for (int i = 0; i < randMax; i++) {
            numDotsOne += ratioNumOne;
            numDotsTwo += ratioNumTwo;
        }
        this.checkAndSet(numDotsOne, numDotsTwo);
        System.out.println(numDotsOne + " " + numDotsTwo);
        System.out.println((double) numDotsOne / (numDotsOne + numDotsTwo));
        System.out.println((double) numDotsTwo / (numDotsOne + numDotsTwo));
    }
    
    /**
     * Perform checks to see if/how the pair should be set and set.
     * @param dotSetOne number of dots in dot set one.
     * @param dotSetTwo number of dots in dot set two.
     */
    private void checkAndSet(int dotSetOne, int dotSetTwo) {  
        ControlType controlTypeCandidate = generateRandomAreaControlType();
        this.performChecks(dotSetOne, dotSetTwo, controlTypeCandidate);
        
        if (this.getSameSizeCorrect() >= MAX_TIMES_SAME_SIZE_CORRECT) {
            if (controlTypeCandidate == ControlType.EQUAL_AREAS) {
                controlTypeCandidate = ControlType.INVERSE_AREAS;
            } else if (controlTypeCandidate == ControlType.INVERSE_AREAS) {
                controlTypeCandidate = ControlType.EQUAL_AREAS;
            }       
            this.setSameSizeCorrect(0);
            this.toggleLastWasBig();
        }
        
//        if (this.getSameChoice() >= MAX_TIMES_SAME_ANSWER) {
//            this.setReversePair(dotSetOne, dotSetTwo, controlTypeCandidate);
//        } else {
            this.setDotsPair(new DotsPair(dotSetOne, dotSetTwo, controlTypeCandidate));
       // }
    }
    
    /**
     * Perform checks.
     * @return true if this pair should NOT be set.
     */
    private void performChecks(int dotSetOne, int dotSetTwo, ControlType controlTypeCandidate) {
        this.checkSameChoice(dotSetOne, dotSetTwo);
        this.checkSameSize(controlTypeCandidate);
    }

    /**
     * Generates a random control type. Either EQUAL_AREAS or INVERSE_AREAS.
     * @return random control type.
     */
    private ControlType generateRandomAreaControlType() {
        if (randomGenerator.nextBoolean()) {
            return ControlType.EQUAL_AREAS;
        } else {
            return ControlType.INVERSE_AREAS;
        }
    }
    
    /**
     * Occurs under the condition that the same side has been correct
     * for MAX_TIMES_SAME_ANSWER times in a row.
     * 
     * Set the DotsPair with the positions of the right and left letters
     * flipped as to what it would have otherwise been.
     * 
     * Toggles the lastWasLeft property because we are toggling the side
     * of which each component of the pair is being shown, so the opposite
     * side will be correct after setting the alpha pair in reverse order.
     * 
     * @param dotSetOne 
     * @param dotSetTwo
     */
    public void setReversePair(int dotSetOne, int dotSetTwo, ControlType controlType) {
        this.setDotsPair(new DotsPair(dotSetTwo, dotSetOne, controlType));
        this.toggleLastWasLeft();
        this.setSameChoice(0);
    }

    /**
     * Check if the same side is correct as the last round.
     * @param dotSetOne Position of first letter of current round.
     * @param dotSetTwo Position of second letter of current round.
     */
    public void checkSameChoice(int dotSetOne, int dotSetTwo) {
        if (dotSetOne > dotSetTwo) {
            if (this.lastWasLeft) {
                this.incrementSameChoice();
            } else {
                this.setSameChoice(0);
            }
            this.lastWasLeft = true;
        } else {
            if (!this.lastWasLeft) {
                this.incrementSameChoice();
            } else {
                this.setSameChoice(0);
            }
            this.lastWasLeft = false;
        }   
    }
    
    /**
     * Check if the same relative size (or control type) is correct
     * as the last round. Inverse areas <--> Big correct. Equal areas <--> Small correct.
     * @param controlType the ControlType to be evaluated.
     */
    private void checkSameSize(ControlType controlType) {
        if (controlType == ControlType.INVERSE_AREAS) {
            if (this.lastWasBig) {
                this.incrementSameSizeCorrect();
            } else {
                this.setSameSizeCorrect(0);
            }
            this.lastWasBig = true;
        } else if (controlType == ControlType.EQUAL_AREAS) {
            if (!this.lastWasBig) {
                this.incrementSameSizeCorrect();
            } else {
                this.setSameSizeCorrect(0);
            }
            this.lastWasBig = false;
        }
    }

    /**
     * Toggles which of the last choices was correct.
     */
    private void toggleLastWasLeft() {
        if (this.lastWasLeft) {
            this.lastWasLeft = false;
        } else {
            this.lastWasLeft = true;
        }
    }
    
    /**
     * Toggles which of the last relative sizes was correct.
     */
    private void toggleLastWasBig() {
        if (this.lastWasBig) {
            this.lastWasBig = false;
        } else {
            this.lastWasBig = true;
        }   
    }
    
    /** 
     * Swap values of x and y. To be used as an expression.
     * 
     * @param x
     * @param y This parameter should be an assignment.
     */
    private int swap(int x, int y) {
        return x;
    }
    
    public void changeBlock() {
        this.blockSet.remove(0);
        if (!this.blockSet.isEmpty()) {
            this.blockMode = this.blockSet.get(0);
        }
        this.ratiosBucket.clear();
    }

    public DotsPair getDotsPair() {
        return this.dotsPair;
    }

    public void setDotsPair(DotsPair dotsPair) {
        this.dotsPair = dotsPair;
    }

    public int getSameChoice() {
        return this.sameChoiceCorrect;
    }

    public void setSameChoice(int sameChoiceCorrect) {
        this.sameChoiceCorrect = sameChoiceCorrect;
    }

    public void incrementSameChoice() {
        this.sameChoiceCorrect++;
    }
    
    public boolean isLastWasLeft() {
        return this.lastWasLeft;
    }

    public void setLastWasLeft(boolean lastWasLeft) {
        this.lastWasLeft = lastWasLeft;
    }

    public boolean isLastWasBig() {
        return lastWasBig;
    }

    public void setLastWasBig(boolean lastWasBig) {
        this.lastWasBig = lastWasBig;
    }

    public int getSameSizeCorrect() {
        return sameSizeCorrect;
    }

    public void setSameSizeCorrect(int sameSizeCorrect) {
        this.sameSizeCorrect = sameSizeCorrect;
    }
    
    public void incrementSameSizeCorrect() {
        this.sameSizeCorrect++;
    }

    public int getBlockMode() {
        return blockMode;
    }

    public void setBlockMode(int blockMode) {
        this.blockMode = blockMode;
    }
}
