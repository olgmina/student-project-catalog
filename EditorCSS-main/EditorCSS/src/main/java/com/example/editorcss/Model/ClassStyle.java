package com.example.editorcss.Model;

/**
 * Класс для классов в css файле
 */
public class ClassStyle {
    /**
     * Возвращает имя класса
     * @return имя класса
     */
    public String getName() {
        return Name;
    }

    /**
     * Задает имя класса
     * @param name имя класса
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * имя класса
     */
    private String Name;
}
