package org.planworks.roadmap.graphicElements;

import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Дорога
 * @version 1.0.0
 * @author Максим Подтынников
 */
public class Road implements Serializable {

    /**
     * Холст
     */
    final transient private Canvas canvas;
    /**
     * Графический контекст
     */
    final transient private GraphicsContext gc;
    /**
     * Размер кисти
     */
    transient double size=10;
    /**
     * Цвет кисти
     */
    transient private Color color=Color.BLACK;

    /** Конструктор дороги
     * @param canvas поле рисования
     * @param selection режим выбора
     */
    public Road(Canvas canvas, BooleanProperty selection) {
        gc = canvas.getGraphicsContext2D();
        this.canvas = canvas;
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                e -> draw(e.getSceneX()-105,e.getSceneY()-25,selection.getValue()));
    }
    private Canvas getCanvas()
    {
        return canvas;
    }

    /** Установить цвет дороги
     * @param clr цвет дороги
     */
    public void setColor(Color clr)
    {
        this.color = clr;
    }

    /** Установить размер дороги
     * @param size размер
     */
    public void setSize(double size)
    {
        this.size = size;
    }
    private Color getColor()
    {
        return color;
    }
    private double getSize()
    {
        return size;
    }

    /** Нарисовать дорогу
     * @param x координата x
     * @param y координата y
     * @param select режим выбора
     */
    public void draw(double x, double y,boolean select)
    {
        if(!select) {
            gc.setFill(color);
            gc.fillOval(x - size / 2, y - size / 2, size, size);
        }
    }

    /**
     * Очистить дорогу
     */
    public void reset() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
