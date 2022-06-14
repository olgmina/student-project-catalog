package org.planworks.roadmap.graphicElements;

import javafx.scene.control.TextField;

import java.io.Serializable;

/**
 * Комментарий к метке
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class MarkTask implements Serializable {
    /**
     * Текст описания
     */
    private String text;
    /**
     * id описания
     */
    private final int id;
    /**
     * Поле ввода описания
     */
    transient private TextField node;

    /** Конструктор комментария
     * @param text текст комментария
     * @param id id
     */
    public MarkTask(String text, int id)
    {
        node=new TextField();
        this.id = id;
        this.text = text;
        labelSetting();
        node.setOnKeyTyped(e -> this.text=this.getNode().getText());
        node.setOnKeyReleased(e -> this.text=this.getNode().getText());
    }

    /**
     * Настройки внешнего вида комментария
     */
    private void labelSetting()
    {
        this.node.setText(text);
    }

    /** Получить текстовое поле
     * @return текстовое поле
     */
    public TextField getNode()
    {
        return node;
    }

    /** Получить текст комментария
     * @return текст комментария
     */
    public String getText()
    {
        return text;
    }

    /** Установить текст комментария
     * @param text текст комментария
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /** Получить id
     * @return id
     */
    public int getId()
    {
        return id;
    }
}
