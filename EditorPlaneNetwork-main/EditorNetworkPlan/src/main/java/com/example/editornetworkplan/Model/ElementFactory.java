package com.example.editornetworkplan.Model;

import javafx.scene.layout.Pane;

/**
 * Класс-фабрика для подклассов Elementov
 */
public class ElementFactory {
    public Element getNewElement(double x, double y, int id, Pane workspace, int element){
        switch (element){
            case 0:
                return new Router(x,y,id,workspace);
            case 1:
                return new Switch(x,y,id,workspace);
            case 2:
                return new PC(x,y,id,workspace);
            default:
                return null;
        }
    }
}
