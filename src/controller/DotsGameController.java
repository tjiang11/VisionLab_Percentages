package controller;

import java.net.URL;
import java.util.logging.Logger;

import config.Config;
import model.DotSet;
import model.DotsPair;
import model.DotsPairGenerator;
import model.GameLogic;
import model.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameGUI;

/**
 * 
 * The center of the program; interface between the
 * models and the view. 
 * 
 * Classes Related to:
 *  -GameGUI.java (view)
 *      -Updates elements of the GUI as the game progresses and responds.
 *  -DotsPairGenerator.java (model)
 *      -Calls on DotsPairGenerator to generate new DotsPairs.
 *  -DotsPair.java (model)
 *      -LetterGameController keeps track of the most recent DotsPair created in variable currentDotsPair.
 *  -Player.java (model)
 *      -Updates Player information as the game progresses and responds.
 *  -GameLogic.java (model)
 *      -Calls on GameLogic to evaluate the correctness of a response from the subject.
 *  -DataWriter.java
 *      -Passes information (Player and DotsPair) to DataWriter to be exported.
 *      
 * @author Tony Jiang
 * 6-25-2015
 * 
 */
public class DotsGameController implements GameController {
    
    private static Logger logger = Logger.getLogger("mylog");
    
    final static Color CANVAS_COLOR = Color.BEIGE;
    final static Color[] DOT_COLORS = {Color.GREEN, Color.BLUE, Color.RED, Color.GOLDENROD, Color.DARKMAGENTA, Color.DEEPSKYBLUE};
    
    /** Punish for wrong answers */
    static final boolean PUNISH = true;
    
    /** Time in milliseconds for the player to get ready after pressing start */
    final static int GET_READY_TIME = 2000;
    
    /** Integer representing each each background. */
    private static int backgroundNumber = 0;
    
    /** Time between rounds in milliseconds. */
    static int TIME_BETWEEN_ROUNDS;
    
    /** Time in milliseconds that the DotSets flash */
    static int FLASH_TIME;
    
    /** DataWriter to export data to CSV. */
    private DataWriter dataWriter;
    
    /** DotsPairGenerator to generate an DotsPair */
    private DotsPairGenerator dpg;
    /** The graphical user interface. */
    private GameGUI theView;
    /** The current scene. */
    private Scene theScene;
    /** Left Canvas Graphics Context */
    private GraphicsContext gcLeft;
    /** Right Canvas Graphics Context */
    private GraphicsContext gcRight;
    
    /** Index of the current dots color in DOTS_COLORS */
    private int currentColor;
    
    /** The subject. */
    private Player thePlayer;
    /** The current DotsPair being evaluated by the subject. */
    private DotsPair currentDotsPair;
    
    /** Used to measure response time. */
    private static long responseTimeMetric;
    
    /** Current state of the overall game. */
    public static CurrentState state;
    
    /** Describes the current state of gameplay */
    private static GameState gameState;
    
    /** How many stars the player has earned. 
     * The player earns a star for every time the
     * progress bar is filled. */
    private static int numStars = 0;
    
    /** Number of stars earned before changing to next background. */
    private static final int STARS_PER_BACKGROUND = 3;
    
    private enum GameState {
        /** Player has responded and next round is loading. */
        WAITING_BETWEEN_ROUNDS,
        
        /** Player has not responded and the dots sets are still visible. */
        WAITING_FOR_RESPONSE_VISIBLE,
        
        /** Player has not responded and the dot sets have 
         * already been hidden after the flash time has passed. */
        WAITING_FOR_RESPONSE_BLANK,

        NONE
    }
    
    private Object lock = new Object();
    
    /** Whether or not the user has provided feedback. */
    private static boolean feedback_given;
    
    /** Alternate reference to "this" to be used in inner methods */
    private DotsGameController gameController;
    
    /** 
     * Constructor for the controller. There is only meant
     * to be one instance of the controller. Attaches listener
     * for when user provides response during trials. On a response,
     * prepare the next round and record the data.
     * @param view The graphical user interface.
     */
    public DotsGameController(GameGUI view) {
        
        loadConfig();
        
        this.gameController = this;
        this.dpg = new DotsPairGenerator();
        this.currentDotsPair = null;
        this.currentColor = 0;
        this.theView = view;
        this.theScene = view.getScene();
        this.thePlayer = new Player();
        this.dataWriter = new DataWriter(this);
    }
    
    /** 
     * Load configuration settings. 
     */
    private void loadConfig() {
        new Config();
        FLASH_TIME = Config.getPropertyInt("flash.time");
        TIME_BETWEEN_ROUNDS = Config.getPropertyInt("time.between.rounds");
    }
    
    /**
     * Sets event listener for when subject clicks the start button or presses Enter.
     * Pass in the subject's ID number entered.
     */
    public void setLoginHandlers() {
        
        this.theScene = theView.getScene();
        
        this.theView.getStart().setOnAction(e -> 
            {
                onClickStartButton();
            });
        this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {                
                    onClickStartButton();
                }
            }
        });
        this.theScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    theView.showExitPopup();
                    keyEvent.consume();
                }
            }
        });
    }
    
    /**
     * Action to be executed upon clicking of Start on Login screen.
     * 
     * Records user inputted data and sets instructions screen.
     */
    private void onClickStartButton() {
        theView.getFeedback().setVisible(false);
        theView.getFeedbackAge().setVisible(false);
        theView.getFeedbackGender().setVisible(false);
        try {
            gameController.thePlayer.setSubjectID(Integer.parseInt(theView.getEnterId().getText()));
        } catch (NumberFormatException ex) {
            theView.getEnterId().requestFocus();
            theView.getEnterId().setText("");
            theView.getFeedback().setVisible(true);
            return;
        }    
        if (theView.getPickMale().isSelected()) {
            gameController.thePlayer.setSubjectGender(Player.Gender.MALE);
        } else if (theView.getPickFemale().isSelected()) {
            gameController.thePlayer.setSubjectGender(Player.Gender.FEMALE);
        } else {
            theView.getFeedbackGender().setVisible(true);
            return;
        }
        try {
            gameController.thePlayer.setSubjectAge(Integer.parseInt(theView.getEnterAge().getText()));
        } catch (NumberFormatException ex) {
            theView.getEnterAge().requestFocus();
            theView.getEnterAge().setText("");
            theView.getFeedbackAge().setVisible(true);
            return;
        }
        theView.setInstructionsScreen(); 
    }
    
    /** 
     * Set event listener on the Next button. 
     */
    public void setInstructionsHandlers() {
        this.theScene = theView.getScene();
        this.theView.getNext().setOnAction(e -> {
            onClickNextInstructions();
        });
        this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    onClickNextInstructions();
                }
            }
        });
    }
    
    /**
     *  Actions to be executed on clicking the Next button 
     */
    private void onClickNextInstructions() {
        theView.setGameScreen(); 
        state = CurrentState.PRACTICE;
    }
    
    /**
     * Set handler upon clicking the "Start Assessment" button, preparing for actual assessment.
     * Sets the game screen and the state to GAMEPLAY from PRACTICE. Removes the "Practice" Label.
     * Resets the player's data.
     */
    public void setPracticeCompleteHandlers() {
        this.theView.getStartAssessment().setOnAction( e-> {
            theView.setGameScreen();
            theView.getPractice().setVisible(false);
            state = CurrentState.GAMEPLAY;
            gameState = GameState.WAITING_FOR_RESPONSE_VISIBLE;
            this.resetPlayer();
        });
    }
    
    /** 
     * Reset the player data, but retain intrinsic subject data 
     */
    private void resetPlayer() {
        SimpleIntegerProperty subjectID = new SimpleIntegerProperty(thePlayer.getSubjectID());
        Player.Gender subjectGender = thePlayer.getSubjectGender();
        SimpleIntegerProperty subjectAge = new SimpleIntegerProperty(thePlayer.getSubjectAge());
        thePlayer = new Player(subjectID, subjectGender, subjectAge);
    }
    
    /** 
     * Sets event listener for when subject presses 'F' or 'J' key
     * during a round. 
     */
    public void setGameHandlers() {
        this.theScene = theView.getScene();
        this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.F 
                        || event.getCode() == KeyCode.J) 
                        && !feedback_given) {
                    gameController.handlePressForJ(event);
                }
            }
        });
    }  
    
    /**
     * Actions to be executed on the pressing of the F or J key.
     * Update the models/data, prepare the next round, and export data to CSV.
     * @param event
     */
    private void handlePressForJ(KeyEvent event) {
        this.responseAndUpdate(event);
        this.prepareNextRound(); 
        this.exportDataToCSV();
    }
    
    /** 
     * Export data to CSV file.
     */
    private void exportDataToCSV() {
        if (state == CurrentState.GAMEPLAY) {
            dataWriter.writeToCSV();    
        }
    }
    
    /**
     * Update models and view appropriately according to correctness
     * of subject's response.  
     * @param e The key event to check which key the user pressed.
     * @param view the graphical user interface
     * @return True if the player is correct. False otherwise.
     */
    public void responseAndUpdate (
            KeyEvent e) {
        feedback_given = true;
        if (gameState == GameState.WAITING_FOR_RESPONSE_BLANK) {
            gameState = GameState.WAITING_BETWEEN_ROUNDS;
        }
        DotsPair dp = this.currentDotsPair;
        boolean correct = GameLogic.checkAnswerCorrect(e, dp);
        this.updatePlayer(correct);   
        this.dataWriter.grabData(this);
    }
    
    /** Update the player appropriately.
     * 
     * @param currentPlayer The current player.
     * @param correct True if subject's reponse is correct. False otherwise.
     */
    private void updatePlayer(boolean correct) {
        Player currentPlayer = this.thePlayer;
        this.recordResponseTime();
        if (correct) {
            currentPlayer.addPoint();
            currentPlayer.setRight(true);
        } else {
            currentPlayer.setRight(false);
        }
        currentPlayer.incrementNumRounds();
    }
    
//Need to re-add feedback sound
    
    /** Play sound */
    private void playSound(String soundFile, double rate) {
        URL sound = getClass().getResource("/res/sounds/" + soundFile);
        Media media = new Media(sound.toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setRate(rate);
        MediaView mediaView = new MediaView(player);
        theView.getLayout().getChildren().add(mediaView);
    }
    
    /** If user inputs correct answer play positive feedback sound,
     * if not then play negative feedback sound.
     * @param feedbackSoundFileUrl the File Url of the Sound to be played.
     * @param correct whether the subject answered correctly or not.
     */
    private void feedbackSound(boolean correct) {
        URL feedbackSoundFileUrl;
        if (correct) {
            feedbackSoundFileUrl = 
                    getClass().getResource("/res/sounds/Ping.aiff");
        } else {
            feedbackSoundFileUrl = 
                    getClass().getResource("/res/sounds/Basso.aiff");
        }
        new AudioClip(feedbackSoundFileUrl.toString()).play();
    }
    
    /**
     * Prepare the first round by making a load bar to 
     * let the subject prepare for the first question.
     * 
     * Also sets up the canvases on which the dots will be painted.
     */
    public void prepareFirstRound() {
        feedback_given = true;
        Task<Void> sleeper = new Task<Void>() {   
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < GET_READY_TIME; i++) {
                    this.updateProgress(i, GET_READY_TIME); 
                    Thread.sleep(1);
                }
                return null;
            }
        };
        theView.getGetReadyBar().progressProperty().bind(sleeper.progressProperty());
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                gameState = GameState.WAITING_FOR_RESPONSE_VISIBLE;
                feedback_given = false;
                
                gcLeft = theView.getLeftOption().getGraphicsContext2D();
                gcRight = theView.getRightOption().getGraphicsContext2D();
                
                clearRound();    
                setOptions();

                responseTimeMetric = System.nanoTime();
                theView.getGetReadyBox().setVisible(false);
            }
        });
        Thread sleeperThread = new Thread(sleeper);
        sleeperThread.start();
    }
    
    /**
     * Prepares the next round by recording reponse time,
     * waiting, and creating the next round.
     */
    public void prepareNextRound() {
        this.waitBeforeNextRoundAndUpdate(TIME_BETWEEN_ROUNDS);  
        this.checkIfDone();
    }
    
    /** 
     * Check if subject has completed practice or assessment.
     */
    private void checkIfDone() {
        if (thePlayer.getNumRounds() >= NUM_ROUNDS) {
            this.finishGame();
        }
        if (state == CurrentState.PRACTICE && thePlayer.getNumRounds() >= NUM_PRACTICE_ROUNDS) {
            this.finishPractice();
        }
    } 
    
    /**
     * If subject has completed the total number of rounds specified,
     * then change the scene to the finish screen.
     */
    private void finishGame() {
        theView.setFinishScreen(thePlayer.getNumCorrect(), backgroundNumber);
        theView.getScene().setOnKeyPressed(null);
        this.playSound("Applause.mp3", 1.4);
        this.playSound("Correct1.wav", 1.4);
    }
  
    /**
     * If subject has completed the total number of rounds specified,
     * then change the scene to the practice complete screen.
     */
    private void finishPractice() {
        theView.setPracticeCompleteScreen();
        theView.getScene().setOnKeyPressed(null);
        numStars = 0;
        backgroundNumber = 0;
    }
    
    /**
     * Clears the options.
     */
    public void clearRound() {
        gcLeft.setFill(CANVAS_COLOR);
        gcLeft.fillRect(0, 0, theView.getLeftOption().getWidth(),theView.getLeftOption().getHeight());
        
        gcRight.setFill(CANVAS_COLOR);
        gcRight.fillRect(0, 0, theView.getRightOption().getWidth(),theView.getRightOption().getHeight());
        
        theView.getLeftOption().setOpacity(0.85);
        theView.getRightOption().setOpacity(0.85);
    }

    /**
     * Wait for a certain time and then set the next round.
     */
    public void waitBeforeNextRoundAndUpdate(int waitTime) {
        
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int i = 0;
                while (i < waitTime) {
                    synchronized (lock) {
                        if (gameState == GameState.WAITING_BETWEEN_ROUNDS) {
                            this.updateProgress(i, waitTime); 
                            Thread.sleep(1);
                            i++;
                        }
                    }
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                setOptions();
                responseTimeMetric = System.nanoTime();
                gameState = GameState.WAITING_FOR_RESPONSE_VISIBLE;
            }
        });
        Thread sleeperThread = new Thread(sleeper);
        sleeperThread.start();
    }

    /**
     * Set and show the next round's choices.
     */
    public void setOptions() {
        this.prepareNextPair();
        this.paintDots();
        this.hideDots();
        feedback_given = false;
    }
    
    /**
     * Prepare the next pair.
     */
    private void prepareNextPair() {
        dpg.getNewDifficultyPair();
        this.currentDotsPair = dpg.getDotsPair();
    }
    
    /**
     * Show the choices.
     */
    private void paintDots() {
        theView.getLeftOption().setOpacity(1.0);
        theView.getRightOption().setOpacity(1.0);
        
        DotSet dotSetOne = this.currentDotsPair.getDotSetOne();
        DotSet dotSetTwo = this.currentDotsPair.getDotSetTwo();
        this.paintDotSet(dotSetOne, gcLeft);
        this.paintDotSet(dotSetTwo, gcRight);
    }
    
    /**
     * Hide the dot sets after some time (FLASH_TIME) has passed.
     */
    private void hideDots() { 
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected java.lang.Void call() throws Exception {
                Thread.sleep(FLASH_TIME);
                return null;
            }    
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                gameController.clearRound();    
                if (feedback_given) {
                    DotsGameController.gameState = GameState.WAITING_BETWEEN_ROUNDS;
                } else {
                    DotsGameController.gameState = GameState.WAITING_FOR_RESPONSE_BLANK;
                }
            }
        });
        new Thread(sleeper).start();
    }

    /**
     * Paint the dots for a given dotset.
     * @param 
     */
    private void paintDotSet(DotSet dotSet, GraphicsContext graphicsContext) {
        graphicsContext.setFill(DOT_COLORS[currentColor]);
        for (int i = 0; i < dotSet.getTotalNumDots(); i++) {
            
            int x = dotSet.getPositions().get(i).x;
            int y = dotSet.getPositions().get(i).y;

            graphicsContext.fillOval(x, y, 
                    dotSet.getDiameters().get(i), 
                    dotSet.getDiameters().get(i));
        }
    }
   

    /** 
     * Record the response time of the subject. 
     */
    public void recordResponseTime() {
        long responseTime = System.nanoTime() - responseTimeMetric;
        thePlayer.setResponseTime(responseTime);
        logger.info("Response time: " + responseTime / 1000000000.0);
    }

    public Player getThePlayer() {
        return thePlayer;
    }

    public void setThePlayer(Player thePlayer) {
        this.thePlayer = thePlayer;
    }

    public DotsPair getCurrentDotsPair() {
        return currentDotsPair;
    }

    public void setCurrentDotsPair(DotsPair currentDotsPair) {
        this.currentDotsPair = currentDotsPair;
    }

    public DotsPairGenerator getApg() {
        return dpg;
    }

    public void setApg(DotsPairGenerator dpg) {
        this.dpg = dpg;
    }
    
    public GameGUI getTheView() {
        return theView;
    }
    
    public void setTheScene(Scene scene) {
        this.theScene = scene;
    }
}