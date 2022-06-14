package org.planworks.roadmap.selector;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.Pane;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.planworks.roadmap.graphicElements.Mark;

/**
 * Тестирование класса MemoSelect
 */
@RunWith(JfxRunner.class)
public class MemoSelectTest extends TestCase {

    MemoSelect memoSelect;
    Momento m;

    /**
     * Инициализация объектов
     */
    @Before
    public void init()
    {
        memoSelect=new MemoSelect();
        m=new Momento(new Mark(20,22,1,new Pane(),""));
        memoSelect.push(m);
    }

    /**
     * Проверка работы метода Push
     */
    @Test
    public void testPush() {
        Momento temp=new Momento(new Mark(20,22,2,new Pane(),""));
        memoSelect.push(temp);
        assertEquals(memoSelect.poll(),m);
        assertEquals(memoSelect.poll(),temp);
    }

    /**
     * Проверка работы метода Select
     */
    @Test
    public void testSelect() {
        Object[] arr=new Object[2];
        Momento temp=new Momento(new Mark(20,22,2,new Pane(),""));
        memoSelect.push(temp);
        arr[0]=m;
        arr[1]=temp;
        Assert.assertArrayEquals(memoSelect.select(),arr);
    }

    /**
     * Проверка работы метода Poll
     */
    @Test
    public void testPoll() {
        assertEquals(memoSelect.poll(),m);
        assertTrue(memoSelect.isEmpty());
    }

    /**
     * Проверка работы метода Remove
     */
    @Test
    public void testRemove() {
        Momento temp=new Momento(new Mark(20,22,2,new Pane(),""));
        memoSelect.push(temp);
        assertTrue(memoSelect.contains(m));
        memoSelect.remove(m.getState());
        assertFalse(memoSelect.contains(m));
    }

    /**
     * Проверка работы метода Peek
     */
    @Test
    public void testPeek() {
        Momento temp=new Momento(new Mark(20,22,2,new Pane(),""));
        memoSelect.push(temp);
        assertEquals(memoSelect.poll(),m);
        assertEquals(memoSelect.poll(),temp);
    }

    /**
     * Проверка работы метода IsEmpty
     */
    @Test
    public void testIsEmpty() {
        assertFalse(memoSelect.isEmpty());
    }

    /**
     * Проверка работы метода Contains
     */
    @Test
    public void testContains() {
        assertTrue(memoSelect.contains(m));
        Momento temp=new Momento(new Mark(20,22,2,new Pane(),""));
        assertFalse(memoSelect.contains(temp));
    }
}