package org.planworks.roadmap.adapters;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.VBox;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planworks.roadmap.graphicElements.MarkTask;

/**
 * Проверка работы класса MarkTasks
 */
@RunWith(JfxRunner.class)
public class MarkTasksTest extends TestCase {

    MarkTasks markTasks;

    /**
     * Инициализация объектов
     */
    @Before
    public void init()
    {
        markTasks=new MarkTasks(1,new VBox());
        markTasks.add("строка");
        markTasks.add("парампампам");
    }

    /**
     * Проверка метода Add
     */
    @Test
    public void testAdd() {
        MarkTask task=markTasks.add("еще строка");
        Assert.assertEquals(markTasks.get(task).getText(),"еще строка");
    }

    /**
     * Проверка метода GetAllTasks
     */
    @Test
    public void testGetAllTasks() {
        Assert.assertEquals(markTasks.getAllTasks().size(),2);
    }
}