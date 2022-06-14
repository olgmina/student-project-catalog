package com.example.editorcss.Controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(JfxRunner.class)
public class DesignerControllerTest {
    private DesignerController designerController;

    @Before
    public void init() {
        designerController=new DesignerController();

    }
}