package model;

import java.util.Random;

import config.Config;

/** 
 * Object to represent two sets of dots.
 * 
 * Classes Related To;
 *  -DotSet.java
 *      -DotsPair contains a pair of DotSets.
 * 
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public class DotsPair {
    
    /** The first letter. */
    private DotSet dotSetOne;
    
    /** The second letter. */
    private DotSet dotSetTwo;
    
    /** The difference in number of dots between the sets. */
    private int difference;
    
    /** Whether the left answer is correct or not. */
    private boolean leftCorrect;
    
    /** The control type of this pair */
    private ControlType controlType;

    private static boolean TOTAL_AREA_CONTROL_ON;
    private static boolean EQUAL_AREAS_ONLY;
    private static boolean INVERSE_AREAS_ONLY;
    private static boolean AVERAGE_RADIUS_CONTROL;
    
    private Random randomGenerator = new Random();
    
    private boolean swapped;
    
    /** 
     * Constructor for DotsPair.
     * @param numDotsOne The number of dots in the first set.
     * @param numDotsTwo The number of dots in the second set.
     * @param controlType The control type of this dots pair.
     *      (Whether the dot sets should have equal areas, inverse areas, or equal average radii.)
     */
    public DotsPair(int numDotsOne, int numDotsTwo, ControlType controlType) {
        loadConfig();

        this.dotSetOne = new DotSet(numDotsOne);
        this.dotSetTwo = new DotSet(numDotsTwo, this.dotSetOne);
        
        this.controlType = controlType;
        
        this.defineControlType();
        
        if (TOTAL_AREA_CONTROL_ON) {
            this.scaleAreas();
        }
        
        this.difference = numDotsOne - numDotsTwo;
        if (this.difference > 0) {
            this.setLeftCorrect(true);
        } else if (this.difference < 0) {
            this.setLeftCorrect(false);
        }
    }
    
    private void loadConfig() {
        new Config();
        
        TOTAL_AREA_CONTROL_ON = Config.getPropertyBoolean("total.area.control.on");
        EQUAL_AREAS_ONLY = Config.getPropertyBoolean("equal.areas.only");
        INVERSE_AREAS_ONLY = Config.getPropertyBoolean("inverse.areas.only");
        AVERAGE_RADIUS_CONTROL = Config.getPropertyBoolean("average.radius.control");
    }
    
    
    public void determineWhichSideCorrect(int blockMode) {
        int numDotsOne = this.dotSetOne.getTotalNumDots();
        int numDotsTwo = this.dotSetTwo.getTotalNumDots();
        switch (blockMode) {
        case DotsPairGenerator.MORE_THAN_FIFTY_BLOCK:
        case DotsPairGenerator.MORE_THAN_HALF_BLOCK:
            if ((double) numDotsOne / (numDotsOne + numDotsTwo) > .5) {
                setLeftCorrect(true);
            } else {
                setLeftCorrect(false);
            }
            break;
        case DotsPairGenerator.MORE_THAN_SIXTY_BLOCK: 
            if ((double) numDotsOne / (numDotsOne + numDotsTwo) > .6) {
                setLeftCorrect(true);
            } else {
                setLeftCorrect(false);
            }
        case DotsPairGenerator.MORE_THAN_SEVENTYFIVE_BLOCK:
            if ((double) numDotsOne / (numDotsOne + numDotsTwo) > .75) {
                setLeftCorrect(true);
            } else {
                setLeftCorrect(false);
            }
        }
    }
    
    /**
     * Scale the total areas of the dots based on configuration.
     */
    private void scaleAreas() {
        if (this.controlType == ControlType.EQUAL_AREAS) {
            this.matchAreas(dotSetOne, dotSetTwo);
            return;
        }
        if (this.controlType == ControlType.INVERSE_AREAS) {
            this.inverseMatchAreas(dotSetOne, dotSetTwo);
            return;
        }
        this.randomScaleAreas();
    }
    
    /** Choose a random scaling if none was specified. */
    private void randomScaleAreas() {
        if (randomGenerator.nextBoolean()) {
            this.matchAreas(dotSetOne, dotSetTwo);
            this.controlType = ControlType.EQUAL_AREAS;
        } else {
            this.inverseMatchAreas(dotSetOne, dotSetTwo);
            this.controlType = ControlType.INVERSE_AREAS;
        }
    }
    
    /** Used only if specified in configuration. */
    private void defineControlType() {
        if (AVERAGE_RADIUS_CONTROL) {
            this.controlType = ControlType.RADIUS_AVERAGE_EQUAL;
        } else if (TOTAL_AREA_CONTROL_ON) {
            if (EQUAL_AREAS_ONLY) {
                this.controlType = ControlType.EQUAL_AREAS;
            } else if (INVERSE_AREAS_ONLY) {
                this.controlType = ControlType.INVERSE_AREAS;
            } 
        } else {
            this.controlType = ControlType.NONE;
        }
    }
    
    /**
     * Make two dot sets have equal areas by scaling the dot set with greater area down.
     * @param dotSetOne
     * @param dotSetTwo
     */
    private void matchAreas(DotSet dotSetOne, DotSet dotSetTwo) {
        double totalAreaOne = this.dotSetOne.getTotalArea();
        double totalAreaTwo = this.dotSetTwo.getTotalArea();
        
        if (totalAreaOne > totalAreaTwo) {
            dotSetOne.matchArea(totalAreaTwo);        
        } else {
            dotSetTwo.matchArea(totalAreaOne);
        }
    }

    /**
     * Further scale down the dot set with lesser area.
     * @param dotSetOne
     * @param dotSetTwo
     */
    private void inverseMatchAreas(DotSet dotSetOne, DotSet dotSetTwo) {
        double totalAreaOne = dotSetOne.getTotalArea();
        double totalAreaTwo = dotSetTwo.getTotalArea();
        
        if (totalAreaOne > totalAreaTwo) {
            dotSetTwo.inverseMatchArea(totalAreaOne);
        } else {
            dotSetOne.inverseMatchArea(totalAreaTwo);
        }
    }
    
    public DotSet getDotSetOne() {
        return this.dotSetOne;
    }

    public void setDotSetOne(DotSet dotSetOne) {
        this.dotSetOne = dotSetOne;
    }

    public DotSet getDotSetTwo() {
        return this.dotSetTwo;
    }

    public void setDotSetTwo(DotSet dotSetTwo) {
        this.dotSetTwo = dotSetTwo;
    }


    public int getDifference() {
        return this.difference;
    }


    public void setDifference(int difference) {
        this.difference = difference;
    }

    public boolean isLeftCorrect() {
        return this.leftCorrect;
    }

    public void setLeftCorrect(boolean leftCorrect) {
        this.leftCorrect = leftCorrect;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public boolean isSwapped() {
        return swapped;
    }

    public void setSwapped(boolean swapped) {
        this.swapped = swapped;
    }
}
