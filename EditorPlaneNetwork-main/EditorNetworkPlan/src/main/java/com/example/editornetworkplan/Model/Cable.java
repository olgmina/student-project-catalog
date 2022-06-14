package com.example.editornetworkplan.Model;

import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Класс Соединения
 */
public class Cable implements Serializable {
    /**
     * Холст
     */
    final transient private Canvas canvas;
    /**
     * Графический контекст
     */
    final transient private GraphicsContext gc;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    /**
     * Размер кисти
     */
    transient double size=5;
    /**
     * Цвет кисти
     */
    transient private Color color=Color.BLACK;

    /** Конструктор соединения
     * @param canvas поле рисования
     *
     */
    public Cable(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        this.canvas = canvas;
//        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
//                e -> draw(e.getX(),e.getY()));
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });
        canvas.setOnMouseReleased(e -> {
            gc.strokeLine(startX, startY, e.getX(), e.getY());
        });
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



    /** Нарисовать соединение
     * @param x координата x
     * @param y координата y
     */
    public void draw(double x, double y)
    {
        gc.setFill(color);
        gc.fillOval(x - size / 2, y - size / 2, size, size);

    }

    /**
     * Удалить соединение
     */
    public void reset() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
