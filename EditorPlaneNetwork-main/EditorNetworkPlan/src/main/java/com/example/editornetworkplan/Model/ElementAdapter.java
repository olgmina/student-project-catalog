package com.example.editornetworkplan.Model;

import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс-адаптер для Element
 */
public class ElementAdapter implements Serializable {
    List<Element> elementList;
    transient Pane workspace;
    /**
     * @param workspace рабочая панель
     */
    public ElementAdapter(Pane workspace)
    {
        elementList = new ArrayList<>();
        this.workspace=workspace;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    /** добавить элемент
     * @param x координата x
     * @param y координата y
     * @return элемент
     */
    public Element add(double x,double y,int type)
    {
        ElementFactory factory = new ElementFactory();
        Element temp = factory.getNewElement(x, y, elementList.size(),workspace,type);
        elementList.add(temp);
        return temp;
    }

    /**
     * addAll
     * @param elements List
     */
    public void addAll(List<Element> elements){
        elementList=elements;
    }
    /**
     * очистка списка
     */
    public void clear()
    {
        for(int i=0;i<elementList.size(); ) {
            System.out.println(i);
            this.delete(elementList.get(i));
        }
    }


    /** получить список элементов
     * @return список элементов
     */
    public List<Element> getAll()
    {
        return elementList;
    }

    public Element get(int id)
    {
        return elementList.get(id);
    }
    private Element get(Element element)
    {
        return elementList.get(element.getId());
    }

    /** удалить элемент
     * @param element элемент
     */
    public void delete(Element element)
    {
        workspace.getChildren().remove(element.getContainer());
        elementList.remove(element);
    }
}
