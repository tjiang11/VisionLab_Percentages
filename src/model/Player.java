package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Object to represent the Subject.
 * 
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public class Player {

    /** The subject's assigned ID number. */
    private String subjectID;
    
    /** Enum for gender */
    public enum Gender {
        MALE,
        FEMALE,
    }
    
    /** The subject's gender */
    private Gender subjectGender;
    
    /** The subject's age. */
    private IntegerProperty subjectAge;
    
    /** The number of correct answers the subject has input. */
    private IntegerProperty numCorrect;
    
    /** The total number of questions the subject has answered. */
    private IntegerProperty numRounds;
    
    /** The response time of the subject for the most recent question. */
    private double responseTime;
    
    /** Whether the subject was correct for the most recent question. */
    private boolean isRight;
    
    /** 
     * Constructor.
     */
    public Player() {
        this.subjectID = "";
        this.subjectAge = new SimpleIntegerProperty(0);
        this.numCorrect = new SimpleIntegerProperty(0);
        this.numRounds = new SimpleIntegerProperty(0);
        this.setResponseTime(0);
    }

    public Player(String subjectID, Gender subjectGender, IntegerProperty subjectAge) {
        this();
        this.subjectID = subjectID;
        this.subjectGender = subjectGender;
        this.subjectAge = subjectAge;
    }
    
    public double getRT() {
        return this.responseTime;
    }

    public int getNumCorrect() {
        return this.numCorrect.get();
    }

    public void setNumCorrect(int numCorrect) {
        this.numCorrect.set(numCorrect);
    }
    
    public void addPoint() {
        this.numCorrect.set(this.numCorrect.get() + 1);
    }
    
    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public int getNumRounds() {
        return this.numRounds.get();
    }

    public void setNumRounds(int numRounds) {
        this.numRounds.set(numRounds);
    }
    
    public void incrementNumRounds() {
        this.numRounds.set(this.numRounds.get() + 1);
    }

    public String getSubjectID() {
        return this.subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public boolean isRight() {
        return this.isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public Gender getSubjectGender() {
        return subjectGender;
    }

    public void setSubjectGender(Gender subjectGender) {
        this.subjectGender = subjectGender;
    }

    public int getSubjectAge() {
        return this.subjectAge.get();
    }

    public void setSubjectAge(int subjectAge) {
        this.subjectAge.set(subjectAge);
    }
}
