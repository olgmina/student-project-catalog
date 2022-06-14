package UI;

import ProjectTimeLine.TimeLine.Node;
import ProjectTimeLine.TimeLine.ProjectTimeLine;
import ProjectTimeLine.TimeLine.UINode;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/** Класс по работе и обработке UI элементов для настройки фона карты */
public class InspectorCanvas {

    private ProjectTimeLine projectTimeLine;
    private AnchorPane canvas;
    private TextField sizeX;
    private TextField sizeY;
    private ColorPicker colorPicker;
    private Label infoText;

    public InspectorCanvas (AnchorPane canvas, TextField sizeX, TextField sizeY, ColorPicker colorPicker, Label infoText) {
        this.canvas = canvas;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.colorPicker = colorPicker;

        this.infoText = infoText;
    }

    /** Привязывается проект для обработки */
    public void setProjectTimeLine (ProjectTimeLine projectTimeLine) {

        this.projectTimeLine = projectTimeLine;

        canvas.setMinSize(projectTimeLine.uiCanvas.width, projectTimeLine.uiCanvas.height);
        canvas.setPrefSize(projectTimeLine.uiCanvas.width, projectTimeLine.uiCanvas.height);
        canvas.setMaxSize(projectTimeLine.uiCanvas.width, projectTimeLine.uiCanvas.height);

        Init();
    }

    private void Init () {

        sizeX.setText((int) projectTimeLine.uiCanvas.width + "");
        sizeY.setText((int) projectTimeLine.uiCanvas.height + "");

        setColor(projectTimeLine.uiCanvas.color);

        colorPicker.setOnAction(actionEvent -> {
            setColor(colorPicker.getValue());
        });


        sizeX.setOnAction(keyEvent -> {

            if(!CheckNumber(sizeX))
                return;

            double x;

            try {
                x = Double.parseDouble(sizeX.getText());
            }
            catch (Exception e) {
                return;
            }

            UINode uiNode = CheckNodeX(projectTimeLine.startNode);
            double maxX = uiNode.posX + uiNode.radius;

            if( maxX > x) {
                x = maxX;
                sizeY.setText((int) x + "");
            }

            if(x < 10)
                return;

            projectTimeLine.uiCanvas.width = x;

            canvas.setMinWidth(x);
            canvas.setPrefWidth(x);
            canvas.setMaxWidth(x);

            sizeX.setText((int) projectTimeLine.uiCanvas.width + "");
        });

        sizeY.setOnAction(keyEvent -> {

            if(!CheckNumber(sizeY))
                return;

            double y;

            try {
                y = Double.parseDouble(sizeY.getText());
            }
            catch (Exception e) {
                return;
            }

            UINode uiNode = CheckNodeY(projectTimeLine.startNode);
            double maxY = uiNode.posY + uiNode.radius;

            if( maxY > y) {
                y = maxY;
                sizeY.setText((int) y + "");
            }

            if(y < 10)
                return;

            projectTimeLine.uiCanvas.height = y;

            canvas.setMinHeight(y);
            canvas.setPrefHeight(y);
            canvas.setMaxHeight(y);

        });
    }

    /** Установка цвета фона */
    public void setColor (Color color) {
        canvas.setStyle(
                "-fx-background-color: rgb(" + color.getRed() * 255 + "," + color.getGreen() * 255 + ", " + color.getBlue() * 255 + "); "
        );

        projectTimeLine.uiCanvas.color = colorPicker.getValue();
    }

    private UINode CheckNodeX (Node node) {
        UINode uiNode = node.uiNode;
        double size = node.uiNode.posX;

        for (Node item : node.getChildNodes()) {
            UINode t = CheckNodeX(item);

            if(t.posX > size)
                uiNode = t;
        }

        return uiNode;
    }

    private UINode CheckNodeY (Node node) {
        UINode uiNode = node.uiNode;
        double size = node.uiNode.posY;

        for (Node item : node.getChildNodes()) {
            UINode t = CheckNodeY(item);

            if(t.posY > size)
                uiNode = t;
        }

        return uiNode;
    }

    private boolean CheckNumber (TextField text) {
        if (text.getText().trim() == "") {
            return false;
        }

        if (!text.getText().matches("\\d*")) {
            text.setText(text.getText().replaceAll("[^\\d]", ""));
            return false;
        }

        return true;
    }
}
