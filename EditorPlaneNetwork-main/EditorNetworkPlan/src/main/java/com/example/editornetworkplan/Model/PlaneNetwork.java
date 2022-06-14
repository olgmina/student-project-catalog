package com.example.editornetworkplan.Model;

import javafx.beans.property.BooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Класс для Схемы сети
 */
public class PlaneNetwork implements Serializable {
    /**
     * Соединение
     */
    Cable cable;
    ElementAdapter elementAdapter;

    /**
     * Конструктор
     * @param pane панеть
     * @param canvas холст
     */
    public PlaneNetwork(Pane pane, Canvas canvas){
        cable=new Cable(canvas);
        elementAdapter = new ElementAdapter(pane);
    }

    public Cable getCable() {
        return cable;
    }

    public ElementAdapter getElementAdapter() {
        return elementAdapter;
    }
    public void clear(){
        elementAdapter.clear();
        cable.reset();
    }

    public void setElementAdapter(ElementAdapter adapter) {
        elementAdapter=adapter;
    }
}
