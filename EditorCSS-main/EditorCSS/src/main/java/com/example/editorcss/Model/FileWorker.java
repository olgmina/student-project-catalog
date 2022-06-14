package com.example.editorcss.Model;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Класс для работы с файлом
 */
public class FileWorker {
    /**
     * б
     */
    byte data[] = new byte[4096];

    /**
     * Функция открытие файлов
     * @param path путь
     * @param styleCode код
     */
    public void openFile(String path, StyleCode styleCode) {
        ClassesAdapter classesAdapter = new ClassesAdapter();
        Propertites propertites = new Propertites();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            try {
                fileInputStream.read(data);//читаем файл получаем набор байтов
                ArrayList<String> classes = new ArrayList<>();//
                HashMap<String, HashMap<String, String>> all= new HashMap<>();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    stringBuilder.append((char) data[i]);
                }
                String chunkofdata = stringBuilder.toString();
                String chunksofdata[] = chunkofdata.split("}");
                StringBuilder classname = new StringBuilder();
                StringBuilder cssvalues = new StringBuilder();
                int counter = 0;
                for (int i = 0; i < chunksofdata.length; i++) {
                    HashMap<String, String> css = new HashMap<>();
                    char onechunk[] = chunksofdata[i].toCharArray();
                    for (int j = 0; j < onechunk.length; j++) {
                        if (onechunk[j] == '.') {
                            while (onechunk[j] != '{') {
                                classname.append(onechunk[j]);
                                j++;
                            }

                            classes.add(classname.toString());
                            classname.delete(0, classname.capacity());
                        }

                        if (onechunk[j] == '{') {
                            while (j < onechunk.length && onechunk[j] != '.') {
                                if (onechunk[j] != '{') {
                                    cssvalues.append(onechunk[j]);
                                }
                                j++;
                            }
                            String cssValues = cssvalues.toString().replaceAll("\r\n    ", "");
                            String attr[] = cssValues.split(";");
                            for (int k = 0; k < attr.length - 1; k++) {
                                String tmp[] = attr[k].split(":");
                                css.put(tmp[0], tmp[1]);
                            }
                            cssvalues.delete(0, cssvalues.capacity());
                            all.put(classes.get(counter), css);
                            counter++;
                        }
                    }

                }
                styleCode.setCode(all);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция сохранения файлов
     * @param allCssFile код
     */
    public void saveFile(StringBuilder allCssFile) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save style file as...");
        File file = fileChooser.showSaveDialog(null);
        try(FileWriter writer = new FileWriter(file.getAbsolutePath(), false))
        {
            writer.write(allCssFile.toString());
            writer.flush();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Функция сохранения файлов
     * @param allCssFile код
     */
    public void saveFile(String allCssFile) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save style file as...");
        File file = fileChooser.showSaveDialog(null);
        try(FileWriter writer = new FileWriter(file.getAbsolutePath(), false))
        {
            writer.write(allCssFile);
            writer.flush();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
