package test.alarmsystem;

import alarmsystem.ui.MainController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("Before MainControllerTest.class");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("After MainControllerTest.class");
    }

    @Before
    public void initTest(){
        mainController = new MainController();
    }

    @After
    public void afterTest(){
        mainController = null;
    }

    @Test
    void handleFireEvent() {
        try{
            mainController.handleFireEvent(null);
        } catch (NullPointerException e){
            fail();
        }
    }
}