package controller;

/**
 * An interface for the game controller that handles
 * the flow of the game from one round to the next.
 * 
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public interface GameController {
    /** Number of questions per block */
    public static final int NUM_QUESTIONS_PER_BLOCK = 20;
    
    /** Number of rounds. One round is one pair of options. */
    public static final int NUM_ROUNDS = NUM_QUESTIONS_PER_BLOCK * 4;
    
    /** Number of practice rounds. */
    public static final int NUM_PRACTICE_ROUNDS = 4;
    /** Number of rounds for each difficulty setting. */
    
    public static final int ROUNDS_PER_DIFFICULTY = NUM_ROUNDS / 3;
    
    /**
     * Prepares the next round be recording reponse time,
     * clearing the previous round, waiting, and creating the next round.
     */
    void prepareNextRound();
    
    /**
     * Clears the options.
     */
    void clearRound();
        
    /**
     * Set the next round's choices.
     */
    void setOptions();
    
    /** 
     * Record the response time of the subject. 
     */
    void recordResponseTime();
}
