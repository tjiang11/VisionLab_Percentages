package model;

/**
 * Enum ControlType describing how two dot sets (DotSet) in a dot set pair (DotsPair) are related.
 * 
 * @author Tony Jiang 
 * 6-25-2015
 *
 */
public enum ControlType {
    
    /** Both dot sets should have equal areas. 
     * 
     * If X and Y are two dot sets with initally generated areas so that TotalArea(X) < TotalArea(Y)
     * 
     * and we want TotalArea(X) = TotalArea(Y).
     * 
     * then we scale the dot set with greater total area down.
     * 
     * TotalArea(X) = TotalArea(Y) * (TotalArea(X) / TotalArea(Y))
     * 
     * Equivalent to saying the "Smaller" is correct
     * as the set with smaller dots will have a greater number of dots. */
    EQUAL_AREAS,
    
    /** The dot set with more dots has larger dots.
     * 
     * Let X and Y be two dots sets with initally generated areas so that TotalArea(X) < TotalArea(Y)
     * 
     * and we want TotalArea(X) << TotalArea(Y).
     * 
     * then we scale the dot set with lesser total area down.
     * 
     * TotalArea(X) * (TotalArea(X) / TotalArea(Y)) << TotalArea(Y).
     *  
     * Equivalent to saying the "Bigger" is correct
     * as the set with bigger dots will have a greater nubmer of dots. */
    INVERSE_AREAS,
    
    /** Both dot sets have same average radii. */
    RADIUS_AVERAGE_EQUAL,
    
    /** 
     * Every dot in both dot sets is determined by randomly
     * choosing between a min and max diameter. 
     */
    NONE
}
