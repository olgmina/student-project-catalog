package com.example.editorcss.Model;

import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для применения стилей для элементов
 */
public class ViewCSS {
    /**
     * содержание css файла
     */
    private StringBuilder allCssFile;

    /**
     *Функция присваивает стиль элементу
     * @param cnt элемент
     * @param key название класса
     * @param styleCode StyleCode
     */
    public void setStyle(Parent cnt, String key, StyleCode styleCode){
        cnt.getStylesheets().clear();
        Propertites propertites = new Propertites();
        propertites.setPropertites(styleCode.getCodeString().get(key));
        StringBuilder cssClass = new StringBuilder();
        for(Map.Entry elem: propertites.getPropertites().entrySet()) {
            cssClass.append(elem.getKey() + ": "+elem.getValue()+"; ");
        }
        cnt.setStyle(cssClass.toString());
    }

    /**
     * Функция присваивает стиль всем элементам
     * @param controls HashMap
     * @param styleCode StyleCode
     */
    public void setAllStyle(HashMap<String, Parent> controls, StyleCode styleCode){
        for(Map.Entry<String, Parent> value: controls.entrySet()){
            setStyle(value.getValue(), value.getKey(),styleCode);
        }
    }

}
