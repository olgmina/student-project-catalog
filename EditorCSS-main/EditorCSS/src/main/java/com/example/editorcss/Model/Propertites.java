package com.example.editorcss.Model;

import java.util.HashMap;

/**
 * Класс адаптер для свойств
 */
public class Propertites {
    /**
     * Функция возвращает свойства
     * @return свойства
     */
    public HashMap<String, String> getPropertites() {
        return propertites;
    }

    /**
     * Функция задает свойства
     * @param propertites свойства
     */
    public void setPropertites(HashMap<String, String> propertites) {
        this.propertites = propertites;
    }

    /**
     * свойства
     */
    private HashMap<String,String> propertites;

}
