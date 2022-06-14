package test.alarmsystem;

import alarmsystem.ui.MainModel;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainModelTest {

    private MainModel mainModel;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("Before MainModelTest.class");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("After MainModelTest.class");
    }

    @Before
    public void initTest(){
        mainModel = new MainModel();
    }

    @Test
    void getSensors() throws Exception{
         assertEquals (10, mainModel.getSensors().length);
    }

    @Test
    void getEvents() {
        assertNotNull(mainModel.getEvents());
    }
}