package org.planworks.roadmap.graphicElements;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.Pane;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Класс тестирования класса MarkTest
 */
@RunWith(JfxRunner.class)
public class MarkTest extends TestCase {

    /**
     * Проверка работы метода move
     */
    @Test
    public void testMove() {
        Mark mark =new Mark(10,20,1,new Pane(),"");
        mark.move(30,40);
        assertEquals(mark.getX(),-15.0);
        assertEquals(mark.getY(),-30.0);
    }
}