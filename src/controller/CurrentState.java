package controller;

/**
 * Enum describing the current point in time of the game.
 * 
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public enum CurrentState {
    /** Introduction screen where user inputs Subject ID. */
    INTRODUCTION,
    
    //////////////////////////////////////////////////////////////
    /** Practice rounds. */
    PRACTICE,
    
    /** The actual assessment where data is being recorded. */
    GAMEPLAY,
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    /** After all trials have been completed. */
    FINISHED,
}
