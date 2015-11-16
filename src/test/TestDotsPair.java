package test;

import static org.junit.Assert.assertEquals;

import javafx.application.Application;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import view.GameGUI;
import model.ControlType;
import model.DotsPair;

public class TestDotsPair {
       
    private DotsPair testDotsPair;
    
    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            new GameGUI(primaryStage);
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Before
    public void initialize() {
        testDotsPair = new DotsPair(10, 15, ControlType.EQUAL_AREAS);
    }
    
    @Test
    public void testOne() {
        assertEquals(testDotsPair.getDotSetOne().getTotalArea(), testDotsPair.getDotSetTwo().getTotalArea(), .01);
        assertEquals(testDotsPair.getDotSetOne().getTotalNumDots(), testDotsPair.getDotSetTwo().getTotalNumDots() - 5, 0);
    }
}
