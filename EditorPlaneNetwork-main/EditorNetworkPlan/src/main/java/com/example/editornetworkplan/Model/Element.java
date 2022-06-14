package com.example.editornetworkplan.Model;

import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Абстрактный класс Element
 */
public abstract class Element  implements Serializable {

    /**
     * Координата x
     */
    private double X;
    /**
     * Координата y
     */
    private double Y;
    /**
     * id
     */
    private final int id;

    /**
     * контейнер
     */
    transient private final VBox container;
    /**
     * рабочая панель
     */
    transient private Pane pane;
    public Element(double x, double y, int id, Pane workspace)
    {
        container = new VBox();
        this.X = x-25;
        this.Y = y-50;
        this.id = id;
        ImageView node = new ImageView();
        container.getChildren().add(node);
        draw(workspace, node);
    }
    private void draw(Pane pane,ImageView node)
    {
        this.pane=pane;
        markSetting(node);
        pane.getChildren().add(container);
    }
    protected void markSetting(ImageView node)
    {
    }

    /**
     * возвращает id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * getContainer
     * @return container
     */
    public VBox getContainer() {
        return container;
    }

    /**
     *
     * @return X
     */
    public double getX() {
        return X;
    }

    /**
     * Установка X
     * @param x X
     */
    public void setX(double x) {
        X = x;
    }

    /**
     * Перемещение элемента
     * @param x
     * @param y
     */
    public void move(double x,double y)
    {
        if(x>0 && x<pane.getWidth()) {
            final double deltaX = x - this.X;
            container.setTranslateX(container.getTranslateX() + deltaX);
            this.X = x;
        }
        if(y>0 && y<pane.getHeight()){
            final double deltaY = y - this.Y;
            container.setTranslateY(container.getTranslateY() + deltaY);
            this.Y = y;
        }
    }

    /**
     *
     * @return Y
     */
    public double getY() {
        return Y;
    }
    /** Получить объект
     * @return  объект
     */
    public VBox getNode()
    {
        return container;
    }
    /**
     * Установка Y
     * @param y Y
     */
    public void setY(double y) {
        Y = y;
    }
}
