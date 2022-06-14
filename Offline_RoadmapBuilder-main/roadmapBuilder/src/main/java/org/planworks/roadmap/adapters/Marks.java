package org.planworks.roadmap.adapters;

import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import org.planworks.roadmap.graphicElements.Mark;
import org.planworks.roadmap.selector.MemoSelect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Пользовательские метки
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class Marks implements Serializable {
    /**
     * Список меток
     */
    List<Mark> marks;
    transient Pane workspace;
    transient MemoSelect memoSelect;
    public transient Mark FocusedMark;

    /**
     * @param workspace рабочая панель
     */
    public Marks(Pane workspace)
    {
        memoSelect=new MemoSelect();
        marks = new ArrayList<>();
        this.workspace=workspace;
    }

    /** добавить метку
     * @param x координата x
     * @param y координата y
     * @param date дата
     * @return Метка
     */
    public Mark add(double x,double y,String date)
    {
        Mark temp = new Mark(x, y, marks.size(),workspace,date);
        temp.getNode().setOnMouseEntered(e -> focus(temp));
        temp.getNode().setOnMouseExited(e -> unFocus());
        temp.getNode().setOnMouseDragged(e -> temp.move(e.getSceneX()-130,e.getSceneY()-50));
        temp.getNode().setOnContextMenuRequested((e -> contextMenu(temp).show(temp.getNode(), e.getScreenX(), e.getScreenY())));
        marks.add(temp);
        return temp;
    }

    /** получить выделенные метки
     * @return выделенные метки
     */
    public MemoSelect getMemoSelect()
    {
        return memoSelect;
    }

    private void clearMemoSelect()
    {
        memoSelect= new MemoSelect();
    }

    /**
     * очистка списка
     */
    public void clear()
    {
        for(int i=0;i<marks.size(); ) {
            this.delete(marks.get(i));
        }
    }

    /** наведение на метку
     * @param mark метка
     */
    private void focus(Mark mark)
    {
        workspace.setCursor(Cursor.HAND);
        FocusedMark = mark;
    }

    /**
     * убирание курсора с метки
     */
    private void unFocus()
    {
        workspace.setCursor(Cursor.DEFAULT);
        FocusedMark = null;
    }

    /** вызов контекстного меню
     * @param mark метка
     * @return контекстное меню
     */
    private ContextMenu contextMenu(Mark mark)
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Добавить описание");
        item1.setOnAction(event -> mark.tasks.add(""));
        MenuItem item2 = new MenuItem("Удалить метку");
        item2.setOnAction(event -> delete(mark));
        contextMenu.getItems().addAll(item1, item2);
        return contextMenu;
    }

    /** получить список меток
     * @return список меток
     */
    public List<Mark> getAll()
    {
        return marks;
    }
    private void setMarks(List<Mark> marks)
    {
        this.marks=marks;
    }
    public Mark get(int id)
    {
        return marks.get(id);
    }
    private Mark get(Mark mark)
    {
        return marks.get(mark.getId());
    }

    /** удалить метку
     * @param mark метка
     */
    public void delete(Mark mark)
    {
        workspace.getChildren().remove(mark.getNode());
        marks.remove(mark);
    }
}
