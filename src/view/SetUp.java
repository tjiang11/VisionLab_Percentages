package view;
import controller.CurrentState;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    static final int DOTS_CANVAS_X = (int) (SCREEN_WIDTH * .15);
    static final int DOTS_CANVAS_Y = (int) (SCREEN_HEIGHT * .15);
    public static final int DOTS_CANVAS_WIDTH = (int) (SCREEN_WIDTH * .7);
    public static final int DOTS_CANVAS_HEIGHT = (int) (SCREEN_HEIGHT * .7);
   
  
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
        Label labelID = new Label(Strings.ENTER_SUBJECT_ID_EN);
        Label labelGender = new Label(Strings.PICK_YOUR_GENDER_EN);
        Label labelAge = new Label(Strings.ENTER_AGE_EN);
        
        view.setStart(new Button(Strings.START_EN));
        view.setEnterId(new TextField());
        view.setFeedback(new Label(Strings.SUB_ID_FEEDBACK_EN));
        view.setFeedbackGender(new Label(Strings.GENDER_FEEDBACK_EN));
        view.setFeedbackAge(new Label(Strings.AGE_FEEDBACK_EN));
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
        view.setPickFemale(new RadioButton(Strings.FEMALE_EN));
        view.setPickMale(new RadioButton(Strings.MALE_EN));
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
        Text instructionsText = new Text();
        instructionsText.setText(Strings.PRESS_NEXT_EN);
        instructionsText.setTextAlignment(TextAlignment.CENTER);
        instructionsText.setLayoutX(SCREEN_WIDTH * .1);
        instructionsText.setLayoutY(SCREEN_HEIGHT * .4);
        instructionsText.setFont(new Font("Century Gothic", 55));
        instructionsText.setWrappingWidth(SCREEN_WIDTH * .8);
        
        view.setLeftKeyGuide(new Label());
        view.setRightKeyGuide(new Label());
        view.setNext(new Button(Strings.NEXT_EN));
        view.getNext().setFont(new Font("Tahoma", 20));
        view.getNext().setPrefHeight(SCREEN_HEIGHT * .06);
        view.getNext().setPrefWidth(SCREEN_WIDTH * .1);
        view.getNext().setLayoutX(SCREEN_WIDTH / 2 - view.getNext().getPrefWidth() / 2);        
        view.getNext().setLayoutY(SCREEN_HEIGHT * .6);
        view.getLayout().getChildren().setAll(instructionsText, view.getNext(),
                view.getLeftKeyGuide(), view.getRightKeyGuide());
        configureKeyGuides(view);
    }
    
    private static void configureKeyGuides(GameGUI view) {
        view.getLeftKeyGuide().setFont(new Font("Tahoma", 50));
        view.getLeftKeyGuide().setAlignment(Pos.CENTER);
        view.getLeftKeyGuide().setPrefWidth(SCREEN_WIDTH * .4);
        view.getLeftKeyGuide().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getLeftKeyGuide().setLayoutX((SetUp.SCREEN_WIDTH / 3) - view.getLeftKeyGuide().getPrefWidth() / 2);
        view.getLeftKeyGuide().setLayoutY(SCREEN_HEIGHT * .7 - view.getLeftKeyGuide().getPrefHeight() / 2);
        
        view.getRightKeyGuide().setFont(new Font("Tahoma", 50));
        view.getRightKeyGuide().setAlignment(Pos.CENTER);
        view.getRightKeyGuide().setPrefWidth(SCREEN_WIDTH * .4);
        view.getRightKeyGuide().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getRightKeyGuide().setLayoutX((SetUp.SCREEN_WIDTH / 3 * 2) - view.getRightKeyGuide().getPrefWidth() / 2);
        view.getRightKeyGuide().setLayoutY(SCREEN_HEIGHT * .7 - view.getRightKeyGuide().getPrefHeight() / 2);
    }

    /**
     * Sets up the "cycle" (Practice or Block) complete screen where user has finished completing the practice or block and
     * is about to begin the next block.
     * @param view The graphical user interface.
     * @return scene the Scene containing the elements of this scene.
     */
    public static void setUpCycleCompleteScreen(GameGUI view, String colorOne, 
            String colorTwo, int blockMode, CurrentState state) {  
        String question = null;
        switch (blockMode) {
        case 0:
            question = "Is " + colorOne + " more than " + colorTwo + "?";
            break;
        case 1:
            question = "Is " + colorOne + " more than 50% of the total?";
            break;
        case 2:
            question = "Is " + colorOne + " more than 60% of the total?";
            break;
        case 3:
            question = "Is " + colorOne + " more than 75% of the total?";
            break;
        }
        String blockOrPracticeComplete = null;
        String firstOrNext = null;
        if (state == CurrentState.PRACTICE) {
            firstOrNext = " first ";
            blockOrPracticeComplete = Strings.PRACTICE_COMPLETE_MESSAGE_EN;
        } else {
            firstOrNext = " next ";
            blockOrPracticeComplete = "Block Complete.";
        }
        view.setPracticeComplete(new Text(blockOrPracticeComplete + "\n"
                + "In the" + firstOrNext + "block, the question you will be asked is: \n\n" +
                question));
        view.getPracticeComplete().setTextAlignment(TextAlignment.CENTER);
        view.getPracticeComplete().setFont(new Font("Tahoma", 50));
        view.getPracticeComplete().setWrappingWidth(SCREEN_WIDTH * .9);
        view.setStartAssessment(new Button(Strings.START_ASSESSMENT_EN));
        view.getPracticeComplete().setLayoutY(SetUp.SCREEN_HEIGHT * .3);
        view.getPracticeComplete().setLayoutX(SetUp.SCREEN_WIDTH / 2 - view.getPracticeComplete().getWrappingWidth() / 2);
        view.getStartAssessment().setPrefWidth(SCREEN_HEIGHT * .2);
        view.getStartAssessment().setLayoutY(SetUp.SCREEN_HEIGHT * .8);
        view.getStartAssessment().setLayoutX(SetUp.SCREEN_WIDTH / 2 - view.getStartAssessment().getPrefWidth() / 2);
        view.getLayout().getChildren().setAll(view.getPracticeComplete(), view.getStartAssessment(),
                view.getLeftKeyGuide(), view.getRightKeyGuide());
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
        
        view.setGetReady(new Label(Strings.GET_READY_EN));
        view.getGetReady().setFont(new Font("Tahoma", 50));
        
        view.setGetReadyBox(new VBox(10));
        view.getGetReadyBox().setAlignment(Pos.CENTER);
        view.getGetReadyBox().getChildren().addAll(view.getGetReady(), view.getGetReadyBar());
        
        view.setPractice(new Label(Strings.PRACTICE_EN));
        view.getPractice().setFont(new Font("Tahoma", 50));

        view.setQuestion(new Label());
        view.getQuestion().setFont(new Font("Tahoma", 50));

        view.setPressSpaceText(new Label());
        view.setMask(new ImageView(new Image("/res/images/mask2.png")));
        
        view.getLayout().getChildren().setAll(view.getGetReadyBox(),
                view.getDotsCanvas(), view.getPractice(), view.getQuestion(), view.getPressSpaceText(),
                view.getMask());
        
        view.getGetReadyBox().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getGetReadyBox().setPrefWidth(SCREEN_WIDTH * .4);    
        view.getGetReadyBox().setLayoutY((SetUp.SCREEN_HEIGHT / 2) - view.getGetReadyBox().getPrefHeight());
        view.getGetReadyBox().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getGetReadyBox().getPrefWidth() / 2));
        
        view.getPractice().setPrefHeight(SCREEN_HEIGHT * .1);
        view.getPractice().setPrefWidth(SCREEN_WIDTH * .4);
        view.getPractice().setAlignment(Pos.CENTER);        
        view.getPractice().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getPractice().getPrefWidth() / 2));
        view.getPractice().setLayoutY(SetUp.SCREEN_HEIGHT * .04);
        
        view.getQuestion().setPrefHeight(SCREEN_HEIGHT * .2);
        view.getQuestion().setPrefWidth(SCREEN_WIDTH * .8);
        view.getQuestion().setAlignment(Pos.CENTER);
        view.getQuestion().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getQuestion().getPrefWidth() / 2));
        view.getQuestion().setLayoutY(SetUp.SCREEN_HEIGHT * .35);
        
        view.getPressSpaceText().setPrefHeight(SCREEN_HEIGHT * .2);
        view.getPressSpaceText().setPrefWidth(SCREEN_WIDTH * .8);
        view.getPressSpaceText().setAlignment(Pos.CENTER);
        view.getPressSpaceText().setLayoutX((SetUp.SCREEN_WIDTH / 2) - (view.getQuestion().getPrefWidth() / 2));
        view.getPressSpaceText().setLayoutY(SetUp.SCREEN_HEIGHT * .35);
        view.getPressSpaceText().setFont(new Font("Tahoma", 30));
        
        view.getMask().setLayoutY(SCREEN_HEIGHT * .25);
        
        view.getMask().setFitWidth(DOTS_CANVAS_WIDTH);
        view.getMask().setFitHeight(DOTS_CANVAS_HEIGHT);
        view.getMask().setLayoutX(DOTS_CANVAS_X);
        view.getMask().setLayoutY(DOTS_CANVAS_Y);
        view.getMask().setVisible(false);
        view.getScene().setCursor(Cursor.NONE);
    }

    /**
     * Set up the positioning of the two options.
     * @param view The graphical user interface.
     */
    static void setUpOptions(GameGUI view) {
        view.setDotsCanvas(new Canvas(DOTS_CANVAS_WIDTH, DOTS_CANVAS_HEIGHT));

        view.getDotsCanvas().setLayoutX(DOTS_CANVAS_X);
        view.getDotsCanvas().setLayoutY(DOTS_CANVAS_Y);
    }
    
    /**
     * Set up the finish screen.
     * @param view The graphical user interface.
     * @param primaryStage The stage.
     * @return The finishing scene.
     */
    public static void setUpFinishScreen(GameGUI view, int points) {      
        Label score = new Label();
        score.setText(Strings.YOU_EARNED_EN 
                + points + Strings.POINTS_EN);
        view.setCongratulations(new Label(Strings.YOU_DID_IT_EN));
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
        quitBox.setStyle("-fx-background-color: rgba(238, 238, 255, 1);"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 3px;");
        quitBox.setPadding(new Insets(30, 30, 30, 30));
        quitBox.setAlignment(Pos.CENTER);
        Label quitLabel = new Label(Strings.QUIT_MESSAGE_EN);
        quitLabel.setFont(new Font("Tahoma", 20));
        Button yesButton = new Button(Strings.YES_EN);
        yesButton.setOnAction(e -> {
            System.exit(0);
        });
        Button noButton = new Button(Strings.NO_EN);
        noButton.setOnAction(e -> {
            view.getExitPopup().hide();
            view.getScene().setCursor(Cursor.NONE);
        });
        view.getExitPopup().setHideOnEscape(false);
        quitBox.getChildren().addAll(quitLabel, yesButton, noButton);
        quitBox.toFront();
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
