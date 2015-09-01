package view;
import util.Strings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Popup;
import javafx.stage.Screen;

/**
 * Functions to set up the elements of various screens of the GUI.
 * 
 * Classes Related To:
 *  -GameGUI.java
 *      -This class is a support class for GameGUI.java
 *  -LetterGameController.java
 *      -Used to read in and display information contained in the models, 
 *      which can be modified/accessed through LetterGameController.java.
 *      
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public final class SetUp {
        
    /** Width and height of the computer's screen */
    static final Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    static final double SCREEN_WIDTH = primaryScreenBounds.getWidth();
    static final double SCREEN_HEIGHT = primaryScreenBounds.getHeight();
    
    /**
     * Game Screen. */
    static final int NUM_STARS = 100;
    /** Positions of the choices the subject can pick. */
    static final int LEFT_OPTION_X = (int) (SCREEN_WIDTH * .15);
    static final int LEFT_OPTION_Y = (int) (SCREEN_HEIGHT * .15);
    public static final int OPTION_WIDTH = (int) (SCREEN_WIDTH * .3);
    public static final int OPTION_HEIGHT = (int) (SCREEN_HEIGHT * .7);
    static final int RIGHT_OPTION_X = (int) (SCREEN_WIDTH - LEFT_OPTION_X - OPTION_WIDTH);
    static final int RIGHT_OPTION_Y = LEFT_OPTION_Y;
    static final int PROGRESS_BAR_X = (int) (SCREEN_WIDTH * .02);
    static final int PROGRESS_BAR_Y = (int) (SCREEN_HEIGHT * .05);
    static final int FIRST_STAR_X = (int) (SCREEN_WIDTH * .93);
    static final int STAR_Y = -25;
    static final int STAR_SHIFT = 35;
    static final double STAR_SCALE = .28;
    /** Font size of the letter options. */
    static final int LETTER_SIZE = 100;
  
    /** Disable constructing of an object. */
    private SetUp() {
        
    }
    
    /**
     * Set up the login screen.
     * @param view The graphical user interface.
     * @param primaryStage The stage.
     * @return The login scene.
     */
    public static void setUpLoginScreen(GameGUI view) {
        Label labelID = new Label(Strings.ENTER_SUBJECT_ID_SP);
        Label labelGender = new Label(Strings.PICK_YOUR_GENDER_SP);
        Label labelAge = new Label(Strings.ENTER_AGE_SP);
        
        view.setStart(new Button(Strings.START_SP));
        view.setEnterId(new TextField());
        view.setFeedback(new Label(Strings.SUB_ID_FEEDBACK_SP));
        view.setFeedbackGender(new Label(Strings.GENDER_FEEDBACK_SP));
        view.setFeedbackAge(new Label(Strings.AGE_FEEDBACK_SP));
        view.getFeedback().setTextFill(Color.RED);
        view.getFeedbackGender().setTextFill(Color.RED);
        view.getFeedbackAge().setTextFill(Color.RED);
        view.getFeedback().setVisible(false);
        view.getFeedbackGender().setVisible(false);
        view.getFeedbackAge().setVisible(false);
        view.getEnterId().setAlignment(Pos.CENTER);

        view.setLoginBox(new VBox(5));
        Insets loginBoxInsets = new Insets(30, 30, 30, 30);
        view.getLoginBox().setPadding(loginBoxInsets);
        view.getLoginBox().setStyle("-fx-background-color: rgba(238, 238, 255, 0.5);"
                + "-fx-border-style: solid;");

        view.getLoginBox().setAlignment(Pos.CENTER);
        view.setPickGender(new ToggleGroup());
        view.setPickFemale(new RadioButton(Strings.FEMALE_SP));
        view.setPickMale(new RadioButton(Strings.MALE_SP));
        HBox pickGenderBox = new HBox(25);
        pickGenderBox.getChildren().addAll(view.getPickFemale(), view.getPickMale());
        view.getPickFemale().setToggleGroup(view.getPickGender());
        view.getPickMale().setToggleGroup(view.getPickGender());
        view.setEnterAge(new TextField());
        view.getEnterAge().setAlignment(Pos.CENTER);
        view.getLoginBox().getChildren().addAll(labelID, view.getEnterId(), view.getFeedback(), 
                labelGender, pickGenderBox, view.getFeedbackGender(), 
                labelAge, view.getEnterAge(), view.getFeedbackAge(), 
                view.getStart());
        view.getLayout().getChildren().setAll(view.getLoginBox());
        view.getEnterId().requestFocus();
        view.getPrimaryStage().show(); 
        view.getLoginBox().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getLoginBox().getWidth() / 2));
        view.getLoginBox().setLayoutY(SetUp.SCREEN_HEIGHT * .2);
    }
    
    /**
     * Sets up the elements of the instructions screen.
     * @param gameGUI GameGUI
     * @param primaryStage stage
     * @return Scene the instructions scene
     */
    public static void setUpInstructionsScreen(GameGUI view) {
        Rectangle r = new Rectangle();
        r.setLayoutX(SCREEN_WIDTH * .09);
        r.setLayoutY(SCREEN_HEIGHT * .07);
        r.setWidth(SCREEN_WIDTH * .82);
        r.setHeight(SCREEN_HEIGHT * .84);
        r.setFill(Color.WHITESMOKE);
        r.setOpacity(0.25);
        
        Text instructionsText = new Text();
        instructionsText.setText(Strings.PRESS_NEXT_SP);
        instructionsText.setTextAlignment(TextAlignment.CENTER);
        instructionsText.setLayoutX(SCREEN_WIDTH * .1);
        instructionsText.setLayoutY(SCREEN_HEIGHT * .4);
        instructionsText.setFont(new Font("Century Gothic", 55));
        instructionsText.setWrappingWidth(SCREEN_WIDTH * .8);
        
        view.setNext(new Button(Strings.NEXT_SP));
        view.getNext().setFont(new Font("Tahoma", 20));
        view.getNext().setPrefHeight(SCREEN_HEIGHT * .06);
        view.getNext().setPrefWidth(SCREEN_WIDTH * .1);
        view.getNext().setLayoutX(SCREEN_WIDTH / 2 - view.getNext().getPrefWidth() / 2);        
        view.getNext().setLayoutY(SCREEN_HEIGHT * .6);
        view.getLayout().getChildren().setAll(r, instructionsText, view.getNext());
    }
    
    /**
     * Sets up the practice complete screen where user has finished completing the practice trials and
     * is about to begin assessment.
     * @param view The graphical user interface.
     * @return scene the Scene containing the elements of this scene.
     */
    public static void setUpPracticeCompleteScreen(GameGUI view) {       
        view.setPracticeComplete(new Text(Strings.PRACTICE_COMPLETE_MESSAGE_SP));
        view.getPracticeComplete().setTextAlignment(TextAlignment.CENTER);
        view.getPracticeComplete().setFont(new Font("Tahoma", 50));
        view.getPracticeComplete().setWrappingWidth(600.0);
        view.setStartAssessment(new Button(Strings.START_ASSESSMENT_SP));
        view.getPracticeComplete().setLayoutY(SetUp.SCREEN_HEIGHT * .4);
        view.getPracticeComplete().setLayoutX(SetUp.SCREEN_WIDTH / 2 - view.getPracticeComplete().getWrappingWidth() / 2);
        view.getStartAssessment().setPrefWidth(SCREEN_HEIGHT * .2);
        view.getStartAssessment().setLayoutY(SetUp.SCREEN_HEIGHT * .6);
        view.getStartAssessment().setLayoutX(SetUp.SCREEN_WIDTH / 2 - view.getStartAssessment().getPrefWidth() / 2);
        view.getLayout().getChildren().setAll(view.getPracticeComplete(), view.getStartAssessment());
        view.getScene().setCursor(Cursor.DEFAULT);
        view.getPracticeComplete().requestFocus();
    }
    
    /**
     * Set up the game screen where subject will undergo trials.
     * @param view The graphical user interface.
     * @param primaryStage The stage.
     * @param subjectID The subject's ID.
     * @return The game scene.
     */
    public static void setUpGameScreen(GameGUI view) {
        
        setUpOptions(view);
        
        view.setGetReadyBar(new ProgressBar(0.0));
        view.getGetReadyBar().setPrefWidth(300.0);
        view.getGetReadyBar().setStyle("-fx-accent: green;");
        
        view.setGetReady(new Label(Strings.GET_READY_SP));
        view.getGetReady().setFont(new Font("Tahoma", 50));
        
        view.setGetReadyBox(new VBox(10));
        view.getGetReadyBox().setAlignment(Pos.CENTER);
        view.getGetReadyBox().getChildren().addAll(view.getGetReady(), view.getGetReadyBar());
        
        view.setPractice(new Label(Strings.PRACTICE_SP));
        view.getPractice().setFont(new Font("Tahoma", 50));

        view.getLayout().getChildren().setAll(view.getGetReadyBox(),
                view.getLeftOption(), view.getRightOption(), view.getPractice());
        
        view.getGetReadyBox().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getGetReadyBox().setPrefWidth(SCREEN_WIDTH * .4);    
        view.getGetReadyBox().setLayoutY((SetUp.SCREEN_HEIGHT / 2) - view.getGetReadyBox().getPrefHeight());
        view.getGetReadyBox().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getGetReadyBox().getPrefWidth() / 2));
        
        view.getPractice().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getPractice().setPrefWidth(SCREEN_WIDTH * .4);
        view.getPractice().setAlignment(Pos.CENTER);        
        view.getPractice().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getPractice().getPrefWidth() / 2));
        view.getPractice().setLayoutY(SetUp.SCREEN_HEIGHT * .04);
        
        view.getScene().setCursor(Cursor.NONE);
    }

    /**
     * Set up the positioning of the two options.
     * @param view The graphical user interface.
     */
    static void setUpOptions(GameGUI view) {
        view.setLeftOption(new Canvas(OPTION_WIDTH, OPTION_HEIGHT));
        view.setRightOption(new Canvas(OPTION_WIDTH, OPTION_HEIGHT));

        view.getLeftOption().setLayoutX(LEFT_OPTION_X);
        view.getLeftOption().setLayoutY(LEFT_OPTION_Y);
        view.getRightOption().setLayoutX(RIGHT_OPTION_X);
        view.getRightOption().setLayoutY(RIGHT_OPTION_Y);
    }

    /**
     * Set up the finish screen.
     * @param view The graphical user interface.
     * @param primaryStage The stage.
     * @return The finishing scene.
     */
    public static void setUpFinishScreen(GameGUI view, int points, int level) {      
        Label score = new Label();
        score.setText(Strings.YOU_EARNED_SP 
                + points + Strings.POINTS_SP);
        view.setCongratulations(new Label(Strings.YOU_DID_IT_SP));
        view.getCongratulations().setFont(Font.font("Verdana", 20));
        score.setFont(Font.font("Tahoma", 16));
        view.setFinishMessage(new VBox(6));
        view.getFinishMessage().getChildren().addAll(view.getCongratulations(), score);
        view.getFinishMessage().setAlignment(Pos.CENTER); 
        view.getLayout().getChildren().setAll(view.getFinishMessage());    
        view.getFinishMessage().setPrefHeight(SCREEN_HEIGHT * .3);
        view.getFinishMessage().setPrefWidth(SCREEN_WIDTH * .3);
        view.getFinishMessage().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getFinishMessage().getPrefWidth() / 2));
        view.getFinishMessage().setLayoutY((SetUp.SCREEN_HEIGHT / 2) - (view.getFinishMessage().getPrefHeight() / 2));
    }
    
    /**
     * Create the exit pop up asking if user wants to quit.
     * @param view
     */
    public static void setExitPopup(GameGUI view) {
        view.setExitPopup(new Popup());
        view.getExitPopup().centerOnScreen();
        VBox quitBox = new VBox(8);
        quitBox.setStyle("-fx-background-color: rgba(238, 238, 255, 0.5);"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 3px;");
        quitBox.setPadding(new Insets(30, 30, 30, 30));
        quitBox.setAlignment(Pos.CENTER);
        Label quitLabel = new Label(Strings.QUIT_MESSAGE_SP);
        quitLabel.setFont(new Font("Tahoma", 20));
        Button yesButton = new Button(Strings.YES_SP);
        yesButton.setOnAction(e -> {
            System.exit(0);
        });
        Button noButton = new Button(Strings.NO_SP);
        noButton.setOnAction(e -> {
            view.getExitPopup().hide();
            view.getScene().setCursor(Cursor.NONE);
        });
        view.getExitPopup().setHideOnEscape(false);
        quitBox.getChildren().addAll(quitLabel, yesButton, noButton);
        view.getExitPopup().getContent().addAll(quitBox);
        quitLabel.requestFocus();
    }
    
    /**
     * Show the popup asking if user wants to quit.
     * @param view
     */
    public static void showExitPopup(GameGUI view) {
        view.getScene().setCursor(Cursor.DEFAULT);
        view.getExitPopup().show(view.getPrimaryStage());  
        view.getExitPopup().getContent().get(0).requestFocus();
    }
}
