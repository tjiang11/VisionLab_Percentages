package model;

import java.util.ArrayList;
import java.util.Random;

import com.sun.media.jfxmedia.logging.Logger;

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
    
    /** Number of characters to choose from. */
    static final int MAX_DOTS = 26;
    
    /** Max number of times the same side may be the correct choice. */
    static final int MAX_TIMES_SAME_ANSWER = 3;
    
    /** Max number of times the same relative size (or control type) may be the correct choice. */
    static final int MAX_TIMES_SAME_SIZE_CORRECT = 3;
    
    /** Map from each difficulty mode to an integer representation. */
    static final int EASY_MODE = 0;
    static final int MEDIUM_MODE = 1;
    static final int HARD_MODE = 2;
    
    /** Map from each block to an integer representation. */
    public static final int MORE_THAN_HALF_BLOCK = 0;
    public static final int MORE_THAN_FIFTY_BLOCK = 1;
    public static final int MORE_THAN_SIXTY_BLOCK = 2;
    public static final int MORE_THAN_SEVENTYFIVE_BLOCK = 3;    
    
    /** Number of difficulty modes. */
    static final int NUM_MODES = 3;
    
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
    
    /** The difficulty setting: EASY, MEDIUM, HARD */
    private int difficultyMode;
    
    /** The current block setting */
    private int blockMode;
    
    /** The list containing the difficulties. */
    private ArrayList<Integer> difficultySet;
    
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
        this.difficultySet = new ArrayList<Integer>();
        this.blockSet = new ArrayList<Integer>();
        this.ratiosBucket = new ArrayList<Ratio>();
        this.fillDifficultySet();
        this.fillBlockSet();
    }
    
    /**
     * Gets a new DotsPair with random letters while
     * checking to make sure that the same choice will
     * not be picked more than three times in a row
     * as being correct.
     */
    public void getNewPair() {
        this.setDifficulty();
        int dotSetOne, dotSetTwo;
        dotSetOne = this.randomGenerator.nextInt(MAX_DOTS) + 1;
        do {
            dotSetTwo = this.randomGenerator.nextInt(MAX_DOTS) + 1; 
        } while (dotSetOne == dotSetTwo);        
           
        this.checkAndSet(dotSetOne, dotSetTwo);
    }
    
    /**
     * This is how the difficulty is pseudo-randomly decided:
     * 
     * There will be a list (difficultySet) containing triplets of modes, 
     * where each triplet would contain one of each difficulty mode.
     * NUM_MODE_SETS is the number of triplets that the difficultySet contains.
     * 
     * When resetting the difficulty <setDifficulty()>, one mode will be randomly selected
     * from the difficultySet and removed. This repeats until difficultySet
     * is empty where it will then refill.
     * 
     */
    private void fillDifficultySet() {
        for (int i = 0; i < NUM_MODE_TRIPLETS; i++) {
            this.difficultySet.add(EASY_MODE);
            this.difficultySet.add(MEDIUM_MODE);
            this.difficultySet.add(HARD_MODE);
        }
    }
    
    public void setDifficulty() {
        this.difficultyMode = 
                this.difficultySet.remove(
                        randomGenerator.nextInt(this.difficultySet.size()));
        if (this.difficultySet.isEmpty()) {
            this.fillDifficultySet();
        }
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
    
    /**
     * Get a new pair based on the current difficulty.
     */
    public void getNewDifficultyPair() {
        this.setDifficulty();
        int difference = this.decideDifference();
        this.getNewPair(difference);
    }
    
    /**
     * Decide the distance between the two choices, based on current difficulty.
     * @return int distance between the choices.
     */
    private int decideDifference() {
        switch (this.difficultyMode) {
            case EASY_MODE:
                return this.randomGenerator.nextInt(NUM_CHOICES_IN_MODE) + EASY_MODE_MIN;
            case MEDIUM_MODE:
                return this.randomGenerator.nextInt(NUM_CHOICES_IN_MODE) + MEDIUM_MODE_MIN;
            case HARD_MODE:
                return this.randomGenerator.nextInt(NUM_CHOICES_IN_MODE) + HARD_MODE_MIN;
        }
        System.err.println("Error on decideDifference");
        return 0;
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
            for (int i = 0; i < DotsGameController.NUM_QUESTIONS_PER_BLOCK / 4; i++) {
                this.ratiosBucket.add(new Ratio(2,1));
                this.ratiosBucket.add(new Ratio(3,1));
                this.ratiosBucket.add(new Ratio(1,2));
                this.ratiosBucket.add(new Ratio(1,3));
            }
            break;
        case MORE_THAN_SIXTY_BLOCK:
            for (int i = 0; i < DotsGameController.NUM_QUESTIONS_PER_BLOCK / 4; i++) {
                this.ratiosBucket.add(new Ratio(2,1));
                this.ratiosBucket.add(new Ratio(3,1));
                this.ratiosBucket.add(new Ratio(5,4));
                this.ratiosBucket.add(new Ratio(2,2));
            }
            break;
        case MORE_THAN_SEVENTYFIVE_BLOCK:
            for (int i = 0; i < DotsGameController.NUM_QUESTIONS_PER_BLOCK / 4; i++) {
                this.ratiosBucket.add(new Ratio(7,5));
                this.ratiosBucket.add(new Ratio(8,5));
                this.ratiosBucket.add(new Ratio(5,1));
                this.ratiosBucket.add(new Ratio(6,1));
            }
            break;
        }
        System.out.println(this.ratiosBucket.toString());
    }
    
    private void getNewPair(Ratio ratio) {
        int scaleUp = randomGenerator.nextInt(5);
        int minDots = 15;
        int maxDots = 45;
        int ratioNumOne = ratio.getNumOne();
        int ratioNumTwo = ratio.getNumTwo();
        int numDotsOne = ratio.getNumOne();
        int numDotsTwo = ratio.getNumTwo();
        numDotsOne *= scaleUp;
        numDotsTwo *= scaleUp;
        while (numDotsOne + numDotsTwo < minDots) {
            numDotsOne += ratioNumOne;
            numDotsTwo += ratioNumTwo;
        }
        while (numDotsOne + numDotsTwo > maxDots) {
            numDotsOne -= ratioNumOne;
            numDotsTwo -= ratioNumTwo;
        }
        boolean swap = false;
        if (randomGenerator.nextBoolean()) {
            numDotsOne = swap(numDotsTwo, numDotsTwo = numDotsOne);
            swap = true;
        }
        this.checkAndSet(numDotsOne, numDotsTwo);
        this.dotsPair.determineWhichSideCorrect(this.blockMode);
        this.dotsPair.setSwapped(swap);
        System.out.println(numDotsOne + " " + numDotsTwo);
        System.out.println((double) numDotsOne / (numDotsOne + numDotsTwo));
        System.out.println((double) numDotsTwo / (numDotsOne + numDotsTwo));
    }
    
    /**
     * Gets a new DotsPair with letters a certain distance apart.
     * @param difference distance between the letters.
     */
    public void getNewPair(int difference) {
        int dotSetOne, dotSetTwo;
        dotSetOne = this.randomGenerator.nextInt(MAX_DOTS - difference) + 1;
        dotSetTwo = dotSetOne + difference;
        
        if (randomGenerator.nextBoolean()) {
            dotSetOne = swap(dotSetTwo, dotSetTwo = dotSetOne);
        }
        this.checkAndSet(dotSetOne, dotSetTwo);
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
        
        if (this.getSameChoice() >= MAX_TIMES_SAME_ANSWER) {
            this.setReversePair(dotSetOne, dotSetTwo, controlTypeCandidate);
        } else {
            this.setDotsPair(new DotsPair(dotSetOne, dotSetTwo, controlTypeCandidate));
        }
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
        this.blockMode = this.blockSet.get(0);
        this.ratiosBucket.clear();
    }
    
    public void setRandomDifficulty() {
        this.difficultyMode = this.randomGenerator.nextInt(NUM_MODES);
    }
    
    public void increaseDifficulty() {
        this.difficultyMode++;
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

    public int getDifficultyMode() {
        return difficultyMode;
    }

    public void setDifficultyMode(int difficultyMode) {
        this.difficultyMode = difficultyMode;
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
