package org.planworks.roadmap.adapters;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planworks.roadmap.graphicElements.Mark;

/**
 * Класс для тестирования класса Marks
 */
@RunWith(JfxRunner.class)
public class MarksTest {

    Marks marks;

    /**
     * Инициализация объектов
     */
    @Before
    public void init()
    {
        marks=new Marks(new Pane());
        marks.add(20,20,"");
        marks.add(12,20,"");
    }

    /**
     * Проверка метода Add
     */
    @Test
    public void testAdd() {
        Mark temp=marks.add(12,20,"");
        Assert.assertEquals(marks.get(marks.getAll().size()-1), temp);
    }

    /**
     * Проверка метода Clear
     */
    @Test
    public void testClear() {
        marks.clear();
        Assert.assertTrue(marks.getAll().isEmpty());
    }

    /**
     * Проверка метода Delete
     */
    @Test
    public void testDelete() {
        Mark temp=marks.add(12,20,"");
        int size=marks.getAll().size();
        marks.delete(temp);
        Assert.assertEquals(size-1,marks.getAll().size());
    }
}