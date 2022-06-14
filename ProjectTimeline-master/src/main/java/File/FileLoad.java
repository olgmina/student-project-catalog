package File;

import ProjectTimeLine.TimeLine.Node;
import ProjectTimeLine.TimeLine.ProjectTimeLine;
import ProjectTimeLine.TimeLine.UICanvas;
import ProjectTimeLine.TimeLine.UINode;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/** Класс по работе с загрузкой проекта.
 * Реализует интерфейс IFileLoad */
public class FileLoad extends Application implements IFileLoad {

    private Stage stage;
    private Label infoText;

    /** Конструктор класса, принимает Label для вывода информации о действиях в системе */
    public FileLoad (Label infoText) {
        this.infoText = infoText;
    }

    /** Старт программы */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /** Метод загрузки проекта.
     * Возвращает загруженный проект */
    @Override
    public ProjectTimeLine load () {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TimeLine files", "*.tl"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile == null)
            return null;


        ProjectTimeLine projectTimeLine = new ProjectTimeLine();

        String stringLoad = "";

        try(FileReader reader = new FileReader(selectedFile.getPath()))
        {
            Scanner scanner = new Scanner(reader);

            while(scanner.hasNextLine()){
                stringLoad += scanner.nextLine() + "\n";
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
            infoText.setText("File load error");

            return null;
        }

        try{
            projectTimeLine.uiCanvas = loadUICanvas(stringLoad);
            Node startNode = loadStartNode(stringLoad);

            projectTimeLine.startNode = startNode;

            infoText.setText("File load success");

            return projectTimeLine;

        }catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            infoText.setText("File load error");

            return null;
        }
    }

    private UICanvas loadUICanvas (String stringLoad) {
        UICanvas uiCanvas = new UICanvas();

        int startIndexUICanvasSetting = stringLoad.indexOf("UICanvas");
        startIndexUICanvasSetting = stringLoad.indexOf("{", startIndexUICanvasSetting) + 1;
        int endIndexUICanvasSetting = stringLoad.indexOf("}", startIndexUICanvasSetting);

        String stringLoadUICanvas = stringLoad.substring(startIndexUICanvasSetting, endIndexUICanvasSetting + 1);

        uiCanvas.width = getValueFromStringDouble(stringLoadUICanvas, "width");
        uiCanvas.height = getValueFromStringDouble(stringLoadUICanvas, "height");
        uiCanvas.color = getValueFromStringColor(stringLoadUICanvas, "color");

        return uiCanvas;
    }

    private Node loadStartNode (String stringLoad) {

        int startIndexOf = stringLoad.indexOf("startNode");
        startIndexOf = stringLoad.indexOf("{", startIndexOf) + 1;
        int endIndexOf = stringLoad.lastIndexOf("}");

        String stringNode = stringLoad.substring(startIndexOf, endIndexOf);

        return loadNode(stringNode);
    }

    private Node loadNode (String nodeString) {
        Node node = new Node();
        node.name = getValueFromStringString(nodeString, "name");
        node.description = getValueFromStringString(nodeString, "description");
        node.uiNode = getValueFromStringUINode(nodeString);

        int startIndexOf = nodeString.indexOf("childNodes");

        if(startIndexOf > -1) {

            startIndexOf = nodeString.indexOf("[", startIndexOf);

            while (startIndexOf != -1) {

                int endIndexOf = getPosCloseId(nodeString, "[","]", startIndexOf);
                String newstring = nodeString.substring(startIndexOf + 1, endIndexOf);
                node.addChildNode(loadNode(newstring));

                startIndexOf = nodeString.indexOf("[", endIndexOf);
            }
        }

        return node;
    }


    private int getPosCloseId (String text, String open, String close, int startId) {

        int startOpenBrace = text.indexOf(open, startId);

        int openBrace = text.indexOf(open, startOpenBrace + 1);
        int closeBrace = text.indexOf(close, startOpenBrace);

        while (openBrace < closeBrace && closeBrace != -1 && openBrace != -1) {
            openBrace = text.indexOf(open, openBrace + 1);
            closeBrace = text.indexOf(close, closeBrace + 1);
        }

        return closeBrace;
    }

    private UINode getValueFromStringUINode (String stringLoad) {
        UINode uiNode = new UINode();

        int startIndexUICanvasSetting = stringLoad.indexOf("UINode");
        startIndexUICanvasSetting = stringLoad.indexOf("{", startIndexUICanvasSetting) + 1;
        int endIndexUICanvasSetting = stringLoad.indexOf("}", startIndexUICanvasSetting);

        String stringLoadUICanvas = stringLoad.substring(startIndexUICanvasSetting, endIndexUICanvasSetting);

        uiNode.posX = getValueFromStringDouble(stringLoadUICanvas, "posX");
        uiNode.posY = getValueFromStringDouble(stringLoadUICanvas, "posY");
        uiNode.radius = getValueFromStringDouble(stringLoadUICanvas, "radius");
        uiNode.color = getValueFromStringColor(stringLoadUICanvas, "color");

        return uiNode;
    }

    private String getValueFromStringString (String text, String stringKey) {

        int start = text.indexOf(stringKey);
        start = text.indexOf(":",start) + 1;

        int end = text.indexOf(",", start);

        if(start == end)
            return "";

        return text.substring(start, end);
    }

    private Double getValueFromStringDouble (String text, String stringKey) {

        int start = text.indexOf(stringKey);
        start = text.indexOf(":",start) + 1;

        int end = text.indexOf(",", start);

        if(start == end)
            return 0.0;

        return Double.parseDouble(text.substring(start, end));
    }

    private Color getValueFromStringColor (String text, String stringKey) {
        int start = text.indexOf(stringKey);
        start = text.indexOf("(", start) + 1;
        int end = text.indexOf(",", start);

        double r = Double.parseDouble(text.substring(start, end));
        start = end + 1;
        end = text.indexOf(",", start);
        double b = Double.parseDouble(text.substring(start, end));
        start = end + 1;
        end = text.indexOf(")", start);
        double g = Double.parseDouble(text.substring(start, end));

        Color color = new Color(r,b,g, 1);

        return color;
    }

}
