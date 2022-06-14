package com.example.editorcss.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * класс кода Css файла
 */
public class StyleCode {
    /**
     * конструктор
     */
    public StyleCode(){
        classesAdapter = new ClassesAdapter();
        propertites =new Propertites();
        code=new HashMap<>();
    }

    /**
     * функция возращает {@link ClassesAdapter}
     * @return classesAdapter
     */
    public ClassesAdapter getClassesAdapter() {
        return classesAdapter;
    }
    /**
     * функция возращает {@link Propertites}
     * @return propertites
     */
    public Propertites getPropertites() {
        return propertites;
    }

    /**
     * Адаптер классов
     */
    ClassesAdapter classesAdapter;
    /**
     * Адаптер свойтсв
     */
    Propertites propertites;
    /**
     * Весь код
     */
    HashMap<ClassStyle, Propertites> code;
    /**
     * Функция полного кода в HashMap
     * @return codeString
     */
    public HashMap<String, HashMap<String, String>> getCodeString() {
        return codeString;
    }

    /**
     * Функция задания полного кода в HashMap
     * @param codeString HashMap
     */
    public void setCodeString(HashMap<String, HashMap<String, String>> codeString) {
        this.codeString = codeString;
    }

    /**
     * полный код в HashMap
     */
    HashMap<String, HashMap<String, String>> codeString;
    public void setCode(ClassStyle classStyle, Propertites propertites){
        code.put(classStyle,propertites);
    }

    /**
     * Функция задает полный код в HashMap
     * @param parsedData полный код
     */
    public void setCode(HashMap<String, HashMap<String, String>> parsedData){
        ClassStyle Class = new ClassStyle();
        for (String key : parsedData.keySet()) {
            System.out.println(key);
            Class.setName(key);
            propertites.setPropertites(parsedData.get(key));
            code.put(Class,propertites);
            classesAdapter.add(key);
        }
        codeString=parsedData;
    }

    /**
     * Функция полконого кода в {@link StringBuilder}
     * @return полконый код в {@link StringBuilder}
     */
    public StringBuilder createCssAllText(){
        StringBuilder  allCssFile = new StringBuilder();
        for(Map.Entry<String, HashMap<String, String>> kv: codeString.entrySet()){
            allCssFile.append(kv.getKey() + "{");
            for(Map.Entry v: kv.getValue().entrySet()){
                allCssFile.append("\r\n     " + v.getKey() + ": " + v.getValue() + ";");
            }
            allCssFile.append("\n}\n\n");
        }
        return allCssFile;
    }
}
