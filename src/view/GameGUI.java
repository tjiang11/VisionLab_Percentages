package view;

import controller.CurrentState;
import controller.DotsGameController;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * The graphical user interface of the game.
 * 
 * Classes Related to:
 *  -SetUp.java (utility/support)
 *      -SetUp is a utility class that contains functions that support the setup of GameGUI.
 *  -DotsGameController.java
 *      -The controller is created in this class. As the game progresses, the controller 
 *      adopts to the scenes of the GameGUI and sets their event handlers appropriately.
 *  
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public class GameGUI {
    
    /** Whether or not to slowly drain the progress bar as time pressure. */
    static final boolean PROGRESS_DRAIN = false;
    
    /** Controller for setting event handlers */
    private DotsGameController DGC;

    /** The JavaFX stage for the game. */
    private Stage primaryStage;
    /** The current scene of the game. */
    private Scene scene;
    /** The pane of the game */
    private AnchorPane layout;
    
    /** Login Screen - start button. */
    private Button start;
    /** Login Screen - feedback to tell if user needs to
     * reinput their ID. */
    private Label feedback;
    /** Login Screen - Text field for user to enter their 
     * Subject ID. */
    private TextField enterId;
    /** Login Screen - Toggle group for gender */
    private ToggleGroup pickGender;
    /** Login Screen - Radio Button "Male" */
    private RadioButton pickMale;
    /** Login Screen - Radio Button "Female" */
    private RadioButton pickFemale; 
    /** Login Screen - Text field for user to enter age. */ 
    private TextField enterAge;
    /** Login Screen - feedback to tell if user needs to correctly input their gender. */
    private Label feedbackGender;
    /** Login Screen - feedback to tell if user needs to correctly input their age. */
    private Label feedbackAge;
    /** Login Box to contain start button, feedback label, and enterId TextField. */
    private VBox loginBox;
    
    /** Instructions Screen - Next button */
    private Button next;
    
    /** Exit pop up asking if user wants to quit. */
    private Popup exitPopup;
    
    /** Practice Complete screen */
    /** Label informing user has complete the practice trials. */
    private Text practiceComplete;
    /** Button to begin actual assessment. */
    private Button startAssessment;
    
    /** Game Screen - The canvas. */
    private Canvas dotsCanvas;
    /** Game Screen - The Question. */
    private Label question;
    
    /** Game Screen - Get Ready */
    private Label getReady;
    /** Game Screen - Get Ready Bar */
    private ProgressBar getReadyBar;
    private VBox getReadyBox;
    /** Game Screen - Practice label */
    private Label practice;
    /** Game Screen - Press Space to continue */
    private Label pressSpaceText;
    /** Game Screen - Mask to be shown immediately after each dot set is displayed. */
    private ImageView mask;
    /** Left Key Guide */
    private Label leftKeyGuide;
    /** Right Key Guide */
    private Label rightKeyGuide;
    
    /** End Screen - message informing the user has finished. */
    private Label congratulations;
    
    private VBox finishMessage;

    /** 
     * Constructor for the user interface. Sets the stage
     * and login screen.
     * @param stage The user interface stage.
     * @throws IOException 
     */
    public GameGUI(Stage stage) {
        DGC = new DotsGameController(this);
        this.setPrimaryStage(stage);
        this.layout = new AnchorPane();
        this.scene = new Scene(this.layout, SetUp.SCREEN_WIDTH, SetUp.SCREEN_HEIGHT, Color.BLUE);
        layout.setStyle("-fx-background-color:#707070;");
        this.primaryStage.setScene(this.scene);
        this.primaryStage.setTitle("Letter Game");  
        this.setLoginScreen();
        this.primaryStage.setResizable(false);
        this.primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        this.primaryStage.setFullScreen(true);
        this.primaryStage.sizeToScene();
        SetUp.setExitPopup(this);
    }
    
    /**
     * Sets the login screen where user will input their informmation.
     * @param stage The user interface stage.
     * @throws IOException
     */
    private void setLoginScreen() {
        SetUp.setUpLoginScreen(this);
        DGC.setLoginHandlers(); 
    }

    /**
     * Sets the screen where instructions are shown.
     */
    public void setInstructionsScreen() {
        SetUp.setUpInstructionsScreen(this);
        this.DGC.setInstructionsHandlers();
    }
    
    /**
     * Sets the screen where user has finished practice trials and is about to begin assessment.
     */
    public void setPracticeCompleteScreen(int blockMode, String colorOne, String colorTwo) {
        SetUp.setUpCycleCompleteScreen(this, colorOne, colorTwo, blockMode, CurrentState.PRACTICE);
        this.DGC.setCycleCompleteHandlers(CurrentState.PRACTICE);
    }

    public void setBlockCompleteScreen(int blockMode, String colorOne, String colorTwo) {
        SetUp.setUpCycleCompleteScreen(this, colorOne, colorTwo, blockMode, CurrentState.GAMEPLAY);
        this.DGC.setCycleCompleteHandlers(CurrentState.GAMEPLAY);
    }
    
    /**
     * Sets the game screen where subject will be presented with two letters.
     * @param stage The user interface stage.
     * @param subjectID The subject's ID number.
     */
    public void setGameScreen() {
        SetUp.setUpGameScreen(this);          
        this.DGC.prepareFirstRound();
        this.DGC.setGameHandlers();
    }
    
    /** 
     * Sets the ending screen informing the subject of their completion.
     * @param stage The user interface stage.
     */
    public void setFinishScreen(int points) {
        SetUp.setUpFinishScreen(this, points);
    }
    
    /**
     * Change the background in real time.
     */
    public void changeBackground(int level) { 
        this.scene.setRoot(this.layout);
    }
    
    /**
     * Show the exit pop up asking if user wants to quit.
     */
    public void showExitPopup() {
        SetUp.showExitPopup(this);     
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    public void setScene(Scene s) {
        this.scene = s;
    }
    
    public Canvas getDotsCanvas() {
        return this.dotsCanvas;
    }
    
    public void setDotsCanvas(Canvas c) {
        this.dotsCanvas = c;
    }
    

    public Button getStart() {
        return start;
    }

    public void setStart(Button start) {
        this.start = start;
    }
    
    public void setEnterId(TextField t) {
        this.enterId = t;
    }
    
    public TextField getEnterId() {
        return this.enterId;
    }

    public Label getFeedback() {
        return this.feedback;
    }

    public void setFeedback(Label feedback) {
        this.feedback = feedback;
    }

    public Label getCongratulations() {
        return this.congratulations;
    }

    public void setCongratulations(Label congratulations) {
        this.congratulations = congratulations;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Label getGetReady() {
        return getReady;
    }

    public void setGetReady(Label getReady) {
        this.getReady = getReady;
    }

    public ProgressBar getGetReadyBar() {
        return getReadyBar;
    }

    public void setGetReadyBar(ProgressBar getReadyBar) {
        this.getReadyBar = getReadyBar;
    }

    public AnchorPane getLayout() {
        return layout;
    }

    public void setLayout(AnchorPane layout) {
        this.layout = layout;
    }

    public VBox getLoginBox() {
        return loginBox;
    }

    public void setLoginBox(VBox loginBox) {
        this.loginBox = loginBox;
    }

    public VBox getGetReadyBox() {
        return getReadyBox;
    }

    public void setGetReadyBox(VBox getReadyBox) {
        this.getReadyBox = getReadyBox;
    }
    
    
    public VBox getFinishMessage() {
        return finishMessage;
    }

    public void setFinishMessage(VBox finishMessage) {
        this.finishMessage = finishMessage;
    }

    public Button getNext() {
        return next;
    }

    public void setNext(Button next) {
        this.next = next;
    }

    public Text getPracticeComplete() {
        return practiceComplete;
    }

    public void setPracticeComplete(Text practiceComplete) {
        this.practiceComplete = practiceComplete;
    }

    public Button getStartAssessment() {
        return startAssessment;
    }

    public void setStartAssessment(Button startAssessment) {
        this.startAssessment = startAssessment;
    }

    public Label getPractice() {
        return practice;
    }

    public void setPractice(Label practice) {
        this.practice = practice;
    }

    public ToggleGroup getPickGender() {
        return pickGender;
    }

    public void setPickGender(ToggleGroup pickGender) {
        this.pickGender = pickGender;
    }

    public RadioButton getPickMale() {
        return pickMale;
    }

    public void setPickMale(RadioButton pickMale) {
        this.pickMale = pickMale;
    }

    public RadioButton getPickFemale() {
        return pickFemale;
    }

    public void setPickFemale(RadioButton pickFemale) {
        this.pickFemale = pickFemale;
    }

    public TextField getEnterAge() {
        return enterAge;
    }

    public void setEnterAge(TextField enterAge) {
        this.enterAge = enterAge;
    }

    public Label getFeedbackGender() {
        return feedbackGender;
    }

    public void setFeedbackGender(Label feedbackGender) {
        this.feedbackGender = feedbackGender;
    }

    public Label getFeedbackAge() {
        return feedbackAge;
    }

    public void setFeedbackAge(Label feedbackAge) {
        this.feedbackAge = feedbackAge;
    }

    public Popup getExitPopup() {
        return exitPopup;
    }

    public void setExitPopup(Popup exitPopup) {
        this.exitPopup = exitPopup;
    }

    public Label getQuestion() {
        return question;
    }

    public void setQuestion(Label question) {
        this.question = question;
    }

    public Label getPressSpaceText() {
        return pressSpaceText;
    }

    public void setPressSpaceText(Label pressSpaceText) {
        this.pressSpaceText = pressSpaceText;
    }

    public ImageView getMask() {
        return mask;
    }

    public void setMask(ImageView mask) {
        this.mask = mask;
    }

    public Label getLeftKeyGuide() {
        return leftKeyGuide;
    }

    public void setLeftKeyGuide(Label leftKeyGuide) {
        this.leftKeyGuide = leftKeyGuide;
    }

    public Label getRightKeyGuide() {
        return rightKeyGuide;
    }

    public void setRightKeyGuide(Label rightKeyGuide) {
        this.rightKeyGuide = rightKeyGuide;
    }
}
