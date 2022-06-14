package org.planworks.roadmap.adapters;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.planworks.roadmap.graphicElements.MarkTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Комментарии к меткам
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class MarkTasks implements Serializable {
    List<MarkTask> tasks;
    private final int markId;
    transient VBox container;

    /** список комментариев
     * @param markId id метки
     * @param container контейнер
     */
    public MarkTasks(int markId, VBox container)
    {
        this.container = container;
        tasks = new ArrayList<>();
        this.markId=markId;
    }

    /** добавить комментарий
     * @param text текст
     * @return добавленное описание
     */
    public MarkTask add(String text)
    {
        MarkTask temp=new MarkTask(text, tasks.size());
        temp.getNode().setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                container.getParent().requestFocus();
            }
        });
        container.getChildren().add(0,temp.getNode());
        tasks.add(temp);
        return temp;
    }
    public MarkTask get(MarkTask task)
    {
        return tasks.get(task.getId());
    }
    private void update(MarkTask task,String newText)
    {
        tasks.get(task.getId()).setText(newText);
    }
    private void delete(MarkTask task)
    {
        tasks.remove(task.getId());
    }

    /** получить список комментариев
     * @return список комментариев
     */
    public List<MarkTask> getAllTasks()
    {
        return tasks;
    }
    private int getMarkId()
    {
        return markId;
    }
}
