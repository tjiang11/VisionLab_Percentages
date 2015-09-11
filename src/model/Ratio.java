package model;

/**
 * Class to represent an integer ratio.
 * @author Tony Jiang
 * 9/11/15
 */
public class Ratio {
    
    private int numOne;
    private int numTwo;
    
    public Ratio() { 
        setNumOne(0);
        setNumTwo(0);
    }
    
    public Ratio(int numOne, int numTwo) {
        setNumOne(numOne);
        setNumTwo(numTwo);
    }

    public int getNumOne() {
        return numOne;
    }

    public void setNumOne(int numOne) {
        this.numOne = numOne;
    }

    public int getNumTwo() {
        return numTwo;
    }

    public void setNumTwo(int numTwo) {
        this.numTwo = numTwo;
    }
    
    @Override
    public String toString() {
        return "{" + this.numOne + "," + this.numTwo + "}";
    }
}
