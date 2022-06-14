package org.planworks.roadmap.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planworks.roadmap.graphicElements.Mark;
import org.planworks.roadmap.graphicElements.RoadmapObject;
import org.planworks.roadmap.selector.Momento;
import org.planworks.roadmap.selector.selectorRect;

import java.io.IOException;



@RunWith(JfxRunner.class)
public class ConstructorControllerTest extends TestCase {

    private ConstructorController cc;

   @Before
   public void init() {
       cc=new ConstructorController();
       cc.canvas=new Canvas();
       cc.workspace=new Pane();
       cc.workspace.minWidth(600);
       cc.workspace.minHeight(300);
       cc.selection=new CheckBox();
       cc.rect=new selectorRect();
       cc.background = new BackgroundImage(new Image("imgs/mark.png"),
               BackgroundRepeat.NO_REPEAT,
               BackgroundRepeat.NO_REPEAT,
               BackgroundPosition.DEFAULT,
               new BackgroundSize(1.0, 1.0, true, true, false, false));
       cc.roadmapObject = new RoadmapObject(cc.workspace,cc.canvas,cc.selection.selectedProperty(),false);
       cc.canvas.heightProperty().bind(cc.workspace.heightProperty());
       cc.canvas.widthProperty().bind(cc.workspace.widthProperty());
   }

    @Test
    public void testClearAll() {
            cc.clearAll();
            Assert.assertEquals(cc.roadmapObject.getMarks().getAll().size(), 0);
    }
    @Test
    public void testOnEnd()
    {
        cc.roadmapObject.getMarks().getMemoSelect().push(new Momento(new Mark(10,10,1, cc.workspace,"")));
        cc.selection.setSelected(true);
        cc.onEnd(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                300, 150, 300, 150, MouseButton.PRIMARY, 2,
                true, true, true, true, true, true, true, true, true, true, null));
        Assert.assertTrue(cc.roadmapObject.getMarks().getMemoSelect().isEmpty());
    }

    @Test
    public void testSetBackgroundImage() {
            Image img= new Image("imgs/background.png");
            cc.setBackgroundImage(img);
            Assert.assertEquals(cc.background.getImage(), img);
    }

    @Test
    public void testAddMark() {
            cc.addMark(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                    300, 150, 300, 150, MouseButton.PRIMARY, 2,
                    true, true, true, true, true, true, true, true, true, true, null));
           Assert.assertEquals(cc.roadmapObject.getMarks().getAll().size(),4);
    }
}