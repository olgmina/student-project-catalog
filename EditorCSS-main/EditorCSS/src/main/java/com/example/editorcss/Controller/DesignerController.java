package com.example.editorcss.Controller;

import com.example.editorcss.Model.FileWorker;
import com.example.editorcss.Model.StyleCode;
import com.example.editorcss.Model.ViewCSS;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Класс контроллер для окна дизайнер
 */
public class DesignerController implements Initializable {
    @FXML
    private GridPane gridCss;
    @FXML
    private Label labelCss;
    @FXML
    private CheckBox checkBoxCss;
    @FXML
    private Button buttonCss;
    @FXML
    private Slider sliderCss;
    @FXML
    private TextField textFieldCss;
    @FXML
    private TextArea codetext;
    @FXML
    private Label labelfilename;
    @FXML
    private ChoiceBox<String> listfont;
    @FXML
    private ChoiceBox<String> box;
    @FXML
    private ChoiceBox<String> fontsize;
    /**
     * объект ViewCSS {@link ViewCSS}
     */
    private ViewCSS viewCSS;
    /**
     * объект FileWorker {@link FileWorker}
     */
    private FileWorker worker;
    /**
     * объект StyleCode {@link StyleCode}
     */
    private StyleCode styleCode;
    /**
     * Название файла
     */
    private String filename;
    private StringBuilder allCssFile;
    private String currentCssClass;
    private HashMap<String, Parent> controls;

    /** Инициализация
     * @param location ссылка
     * @param resources ресурсы
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        viewCSS = new ViewCSS();
        currentCssClass = null;
        listfont.setItems(FXCollections.observableArrayList(Font.getFamilies()));
        listfont.getSelectionModel().select(0);
        ArrayList<String> size = new ArrayList<>();
        for(int i = 0;i<50;i++){
            size.add((i+2) + "px");
        }
        fontsize.setItems(FXCollections.observableArrayList(size));
        fontsize.getSelectionModel().select(0);
        controls = new HashMap<String, Parent>(){{
            put(".root", gridCss);
            put(".label", labelCss);
            put(".check-box", checkBoxCss);
            put(".button", buttonCss);
            put(".slider", sliderCss);
            put(".text-field", textFieldCss);
        }};


    }

    /**
     * открытие файла
     */
    public void OpenFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css");
        fileChooser.setTitle("Open style file");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if(file == null){
            return;
        }
        styleCode = new StyleCode();
        worker=new FileWorker();
//        model = new com.example.editorcss.Model.FileCss();
        System.out.println(file.getAbsoluteFile());
        String filepath = file.getAbsolutePath();
        filename = file.getName();
        gridCss.getStylesheets().add("file:///" + filepath.replace('\\', '/'));
        initPreview(filepath);
    }

    /**
     * Отображение кода
     * @param filepath путь к файлу
     */
    private void initPreview(String filepath){
        labelfilename.setText(filename);
        worker.openFile(filepath,styleCode);
        styleCode.getClassesAdapter().PringStyleList();
        box.setItems(FXCollections.observableArrayList(styleCode.getClassesAdapter().getClassStyleListString()));
//        box.setValue(styleCode.getClassesAdapter().getClassStyleListString().get(0));
//        currentCssClass = styleCode.getClassesAdapter().getClassStyleListString().get(0);
        box.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentCssClass = box.getSelectionModel().getSelectedItem();
            }
        });
        codetext.setText(styleCode.createCssAllText().toString());
        viewCSS.setAllStyle(controls, styleCode);
    }

    /**
     * Изменения в полях ввода
     * @param actionEvent событие
     */
    public void actionCssProperty(Event actionEvent) {
        if(currentCssClass != null) {
            String key = (((Parent)actionEvent.getSource()).getUserData()).toString();
            String value = "";
            if(actionEvent.getSource() instanceof ColorPicker){
                StringBuilder v = new StringBuilder(((ColorPicker)actionEvent.getSource()).getValue().toString());
                v.delete(0, 2);
                v.insert(0,'#');
                value = v.toString();
            }else if(actionEvent.getSource() instanceof TextField){
                value = ((TextField)actionEvent.getSource()).getText();
                if(value.equals("")){
                    value = "0";
                }
            }else if(actionEvent.getSource() instanceof ChoiceBox){
                value = ((ChoiceBox)actionEvent.getSource()).getValue().toString();
            }
            if(value.equals("")){
                return;
            }

//            if(!tryPadding(key, value)) {
                HashMap<String, String> currentClassProperties = styleCode.getCodeString().get(currentCssClass);
                currentClassProperties.put(key, value);
//            }
            System.out.println(currentClassProperties);
            codetext.setText(styleCode.createCssAllText().toString());
            viewCSS.setStyle(controls.get(currentCssClass), currentCssClass, styleCode);
        }
    }
    public void clear(){

    }
    /**
     * Сохранение файла
     * @param actionEvent событие
     */
    public void saveStyle(ActionEvent actionEvent) {
        worker.saveFile(styleCode.createCssAllText());
    }
}