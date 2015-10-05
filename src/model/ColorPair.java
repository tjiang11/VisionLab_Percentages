package model;

import javafx.scene.paint.Color;

/**
 * Class to represent a pair of colors.
 * 
 * @author Tony Jiang
 * 10/5/15
 *
 */

public class ColorPair {
    Color colorOne;
    Color colorTwo;
    String colorOneName;
    String colorTwoName;
    
    public ColorPair(Color colorOne, Color colorTwo, String colorOneName, String colorTwoName) {
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;
        this.colorOneName = colorOneName;
        this.colorTwoName = colorTwoName;
    }
    
    public Color getColorOne() {
        return this.colorOne;
    }
    
    public Color getColorTwo() {
        return this.colorTwo;
    }
    
    public String getColorOneName() {
        return this.colorOneName;
    }
    public String getColorTwoName() {
        return this.colorTwoName;
    }
}

