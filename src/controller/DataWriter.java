package controller;

import java.io.BufferedWriter;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import model.ControlType;
import model.DotsPair;
import model.Player;


/**
 * Class for grabbing and exporting data to a CSV file.
 * 
 * Classes Related to:
 *  -DotsGameController.java
 *      -Grabs DotsPair and Player from the controller to record and export their data.
 * 
 * @author Tony Jiang
 * 6-25-2015
 *
 */
public class DataWriter {

    private static Logger logger = Logger.getLogger("mylog");
    
    public static final String DELIMITER = ",";
    public static final String SUBJECT_ID = "Subject ID";
    public static final String SUBJECT_AGE = "Subject Age";
    public static final String SUBJECT_GENDER = "Subject Gender";
    public static final String LEFT_CHOICE = "Left Choice";
    public static final String RIGHT_CHOICE = "Right Choice";
    public static final String WHICH_SIDE_CORRECT = "Side Correct";
    public static final String WHICH_SIDE_PICKED = "Side Picked";
    public static final String IS_CORRECT = "Correct";
    public static final String BLOCK = "Block";
    public static final String CONTROL_TYPE = "Control Type";
    public static final String NUMBER_DOTS_RATIO = "Ratio of number of dots";
    public static final String PERCENTAGE = "Percentage of dots";
    public static final String AREA_RATIO = "Ratio of areas of dot sets (greater to smaller)";
    public static final String COLOR_ONE = "Color One";
    public static final String COLOR_TWO = "Color Two";
    public static final String RESPONSE_TIME = "Response Time";
    public static final String DATE_TIME = "Date/Time";
    public static final String CONSECUTIVE_ROUND = "Consecutive Rounds";
    public static final String SECTION = "Section";
    public static final String FEEDBACK_GIVEN = "Feedback given";
    public static final String KEY_FOR_YES = "Key for \"Yes\" ";
    
    
    /** The subject to grab data from. */
    private Player player;
    /** DotsPair to grab data from. */
    private DotsPair dotsPair;
    /** DotsGameController */
    private DotsGameController dgc;
    private String colorOne;
    private String colorTwo;
    private int section;
    
    /**
     * Constructor for data writer that takes in a controller
     * and grabs the player and dots pair.
     * @param dgc Controller to grab data from
     */
    public DataWriter(DotsGameController dgc) {
        this.player = dgc.getThePlayer();
        this.dotsPair = dgc.getCurrentDotsPair();
        this.dgc = dgc;
    }
    
    /**
     * Regrab the current subject and dots pair from the controller.
     * @param dgc Controller to grab data from
     */
    public void grabData(DotsGameController dgc) {
        this.player = dgc.getThePlayer();
        this.dotsPair = dgc.getCurrentDotsPair();
        this.colorOne = dgc.getColorOne();
        this.colorTwo = dgc.getColorTwo();
        this.section = dgc.getDpg().getNumSections();
    }
    
    /**
     * Export data to CSV file. Appends to current CSV if data
     * for subject already exists.
     * Location of CSV file is in folder "results". "Results" will contain
     * subfolders each titled by Subject ID number containing the subject's
     * CSV data. 
     */
    public void writeToCSV() {
        
        PrintWriter writer = null;
        String subjectId = this.player.getSubjectID();
        try {
            /** Grab path to project */
            String path = new File(".").getAbsolutePath();
            path = path.substring(0, path.length() - 1);
            
            /** Create results folder if doesn't exist */
            File resultsDir = new File("results_dots");
            resultsDir.mkdir();
            
            /** Create subject folder if doesn't exist */
            File subjectDir = new File("results_dots\\" + subjectId);
            subjectDir.mkdir();    
            
            /** Create new csv file for subject if doesn't exist */
            File file = new File(path + "\\results_dots\\" + subjectId 
                    + "\\results_" + subjectId + ".csv");   
            logger.info(file.getPath());
            String text = "";
            /** Write data to new file or append to old file */
            if (file.createNewFile()) {
                text += this.generateColumnNames();
            }
            text += this.generateTrialText();
            writer = new PrintWriter(
                        new BufferedWriter(
                            new FileWriter(file, true)));
            writer.write(text);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
        } finally {
            writer.flush();
            writer.close();
        } 
    }
    
    /**
     * Generate the column names.
     * @return String column names.
     */
    private String generateColumnNames() {
        String text = SUBJECT_ID + DELIMITER
                + SUBJECT_AGE + DELIMITER
                + SUBJECT_GENDER + DELIMITER
                + LEFT_CHOICE + DELIMITER
                + RIGHT_CHOICE + DELIMITER
                + WHICH_SIDE_CORRECT + DELIMITER
                + WHICH_SIDE_PICKED + DELIMITER
                + IS_CORRECT + DELIMITER
                + BLOCK + DELIMITER
                + CONTROL_TYPE + DELIMITER 
                + NUMBER_DOTS_RATIO + DELIMITER
                + PERCENTAGE + DELIMITER
                + AREA_RATIO + DELIMITER    
                + COLOR_ONE + DELIMITER
                + COLOR_TWO + DELIMITER
                + RESPONSE_TIME + DELIMITER
                + DATE_TIME + DELIMITER
                + CONSECUTIVE_ROUND + DELIMITER
                + SECTION + DELIMITER
                + FEEDBACK_GIVEN + DELIMITER
                + KEY_FOR_YES + "\n";
        return text;
    }

    /**
     * Generate the CSV text data for the round (one pair).
     * @return String CSV text data
     */
    public String generateTrialText() {
        String subjectID = this.generateSubjectIdText();
        String subjectAge = this.generateSubjectAgeText();
        String subjectGender = this.generateSubjectGenderText();
        String leftChoice = this.generateLeftChoiceText();       
        String rightChoice = this.generateRightChoiceText();
        String whichSideCorrect = this.generateWhichSideCorrectText();
        String whichSidePicked = this.generateWhichSidePickedText(whichSideCorrect);
        String correct = this.generateCorrectText();
        String block = this.generateBlockText();
        String controlType = this.generateControlTypeText();
        String numDotsRatio = this.generateNumDotsRatioText();
        String percentage = this.generatePercentageText();
        String areaRatio = this.generateAreaRatioText();
        String colorOne = this.generateColorOneText();
        String colorTwo = this.generateColorTwoText();
        String responseTime = this.generateResponseTimeText();
        String dateTime = this.generateDateTimeText();
        String consecutiveRounds = this.generateConsecutiveRoundsText();
        String section = this.generateSectionText();
        String feedbackGiven = this.generateFeedbackGivenText();
        String keyForYes = this.generateKeyForYesText();
        
        String trialText = subjectID + DELIMITER
                + subjectAge + DELIMITER
                + subjectGender + DELIMITER
                + leftChoice + DELIMITER
                + rightChoice + DELIMITER
                + whichSideCorrect + DELIMITER
                + whichSidePicked + DELIMITER
                + correct + DELIMITER
                + block + DELIMITER
                + controlType + DELIMITER
                + numDotsRatio + DELIMITER
                + percentage + DELIMITER
                + areaRatio + DELIMITER
                + colorOne + DELIMITER
                + colorTwo + DELIMITER
                + responseTime + DELIMITER
                + dateTime + DELIMITER
                + consecutiveRounds + DELIMITER
                + section + DELIMITER
                + feedbackGiven + DELIMITER
                + keyForYes + "\n";
        
        return trialText;
    }
    
    private String generateSubjectIdText() {
        return this.player.getSubjectID();
    }
    
    private String generateSubjectAgeText() {
        return Integer.toString(
                this.player.getSubjectAge());
    }
    
    private String generateSubjectGenderText() {
        return this.player.getSubjectGender().toString();
    }
    
    private String generateLeftChoiceText() {
        return Integer.toString(
                this.dotsPair.getDotSetOne().getPositions().size());
    }
    
    private String generateRightChoiceText() {
        return Integer.toString(
                this.dotsPair.getDotSetTwo().getPositions().size());
    }
    
    private String generateWhichSideCorrectText() {
        if (this.dgc.isYesCorrect()) {
            return "YES";
        } else {
            return "NO";
        }
    }
    
    private String generateWhichSidePickedText(String whichSideCorrect) {
        if (this.player.isRight()) {
            return whichSideCorrect;
        } else {
            if (whichSideCorrect.equals("YES")) {
                return "NO";
            } else {
                return "YES";
            }
        }
    }
    
    private String generateCorrectText() {
        if (this.player.isRight()) {
            return "yes";
        } else {
            return "no";
        }
    }
    
    private String generateBlockText() {
        return Integer.toString(this.dgc.getLastBlock());
    }
    
    
    private String generateControlTypeText() {
        if (this.dotsPair.getControlType() == ControlType.EQUAL_AREAS) {
            return "Equal Areas";
        } else if (this.dotsPair.getControlType() == ControlType.INVERSE_AREAS) {
            return "Inverse Areas";
        } else if (this.dotsPair.getControlType() == ControlType.RADIUS_AVERAGE_EQUAL) {
            return "Equal Average Radii";
        } else if (this.dotsPair.getControlType() == ControlType.NONE) {
            return "None";
        } 
        return "-";
    }
    
    private String generateNumDotsRatioText() {
        double ratio = ((double) this.dotsPair.getDotSetOne().getTotalNumDots()) / this.dotsPair.getDotSetTwo().getTotalNumDots();
        if (ratio < 1) {
            ratio = 1 / ratio;
        }
        return Double.toString(ratio);
    }
    
    private String generatePercentageText() {
        double percentage = 
                ((double) this.dotsPair.getDotSetOne().getTotalNumDots() / 
                        (this.dotsPair.getDotSetOne().getTotalNumDots() 
                                + this.dotsPair.getDotSetTwo().getTotalNumDots()));
        return Double.toString(percentage);
    }
    
    private String generateAreaRatioText() {
        double ratio = ((double) this.dotsPair.getDotSetOne().getTotalArea() / this.dotsPair.getDotSetTwo().getTotalArea());
        if (ratio < 1) {
            ratio = 1 / ratio;
        }
        return Double.toString(ratio);
    }
    
    private String generateColorOneText() {
        return this.colorOne;
    }
    
    private String generateColorTwoText() {
        return this.colorTwo;
    }
    
    private String generateResponseTimeText() {
        return String.valueOf(this.player.getRT() / 1000000000.0);
    }
    
    private String generateDateTimeText() {
        return LocalDateTime.now().toString();
    }
    
    private String generateConsecutiveRoundsText() {
        return Integer.toString(
                this.player.getNumRounds());
    }
    
    private String generateSectionText() {
        return Integer.toString(this.section);
    }
    
    private String generateFeedbackGivenText() {
        if (this.section >= DotsGameController.SECTION_TO_START_FEEDBACK) {
            return "Yes";
        } else {
            return "No";
        }
    }
    
    private String generateKeyForYesText() {
        if (this.dgc.isFforTrue()) {
            return "F";
        } else {
            return "J";
        }
    }
}
