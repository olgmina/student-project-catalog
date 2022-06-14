package com.example.editorcss.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс адаптер для классов в css файле
 */
public class ClassesAdapter {
    /**
     * конструктор класса
     */
    public ClassesAdapter(){
        classStyleList=new ArrayList<>();
    }

    /**
     * Функция возращает список классов
     * @return список классов
     */
    public List<ClassStyle> getClassStyleList() {
        return classStyleList;
    }

    /**
     * Печать содержимого списка классов в консоль
     */
    public void PringStyleList(){
        System.out.println("Метод PringStyleList() класса ClassesAdapter");
        for (ClassStyle item : classStyleList) {
            System.out.println(item.getName());
        }
        Iterator<ClassStyle> iter = classStyleList.iterator();
        while(iter.hasNext()){
            System.out.println("элемент-"+iter.next().getName());
        }
    }

    /**
     * Функция преобразования списка класов в список String
     * @return список названий классов
     */
    public List<String> getClassStyleListString() {
        List<String> list = new ArrayList<>();
        Iterator<ClassStyle> iter = classStyleList.iterator();
        while(iter.hasNext()){
            list.add(iter.next().getName());
        }
        return list;
    }

    /**
     * Список классов
     */
    private List<ClassStyle> classStyleList;

    /**
     *
     * @param classStyle ClassStyle
     * @return ы
     */
    public ClassStyle addAll(ClassStyle classStyle){
        classStyleList.add(classStyle);
        return classStyle;
    }

    /**
     * Функция добавления списка классов
     * @param classes список String
     */
    public void addAll(List<String> classes){
        System.out.println("Метод add() класса ClassesAdapter");
        ClassStyle classStyle=new ClassStyle();
        for(String className: classes){
            classStyle.setName(className);
            System.out.println("className="+className+" classStyle.getName="+classStyle.getName());
            classStyleList.add(classStyle);
        }
    }

    /**
     * Функция добавления класса
     * @param className название класса
     * @return ClassStyle
     */
    public ClassStyle add(String className){
        System.out.println("Метод add() класса ClassesAdapter");
        ClassStyle classStyle=new ClassStyle();
        classStyle.setName(className);
        System.out.println("className="+className+" classStyle.getName="+classStyle.getName());
        classStyleList.add(classStyle);
        return classStyle;
    }
}
