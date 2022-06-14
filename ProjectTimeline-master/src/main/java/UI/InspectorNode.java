package UI;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/** Класс по работе и обработке UI элементов для настройки нода */
public class InspectorNode {

    private NodeButtonControl nodeButtonControl;
    private VBox inspectorNodeLayer;
    private BorderPane borderPane;

    private TextField nameTextField;
    private TextArea descriptionTextArea;
    private ColorPicker colorPicker;

    private TextField radiusTextField;

    private boolean isActive;

    public InspectorNode (BorderPane borderPane, VBox inspectorNodeLayer, TextField nameTextField, TextArea descriptionTextArea, ColorPicker colorPicker, TextField radiusTextField) {
        this.inspectorNodeLayer = inspectorNodeLayer;
        this.borderPane = borderPane;
        this.nameTextField = nameTextField;
        this.descriptionTextArea = descriptionTextArea;
        this.colorPicker = colorPicker;
        this.radiusTextField = radiusTextField;
    }

    public NodeButtonControl getNodeButtonControl(){
        return nodeButtonControl;
    }

    /** Установка нода для работы */
    public void setNode (NodeButtonControl nodeButtonControl) {

        this.nodeButtonControl = nodeButtonControl;

        nameTextField.setText(nodeButtonControl.getNode().name);


        nameTextField.setOnKeyReleased(keyEvent -> {
            this.nodeButtonControl.setName(nameTextField.getText());
        });


        descriptionTextArea.setText(nodeButtonControl.getNode().description);

        descriptionTextArea.setOnKeyReleased(keyEvent -> {
            this.nodeButtonControl.getNode().description = descriptionTextArea.getText();
        });


        colorPicker.setValue(nodeButtonControl.getNode().uiNode.color);

        colorPicker.setOnAction(actionEvent -> {
            nodeButtonControl.setColorNode(colorPicker.getValue());
        });

        radiusTextField.setText((int) nodeButtonControl.getNode().uiNode.radius + "");

        radiusTextField.setOnAction(keyEvent -> {

            if (radiusTextField.getText().trim() == "") {
                return;
            }

            if (!radiusTextField.getText().matches("\\d*")) {
                radiusTextField.setText(radiusTextField.getText().replaceAll("[^\\d]", ""));
                return;
            }

            double radius;

            try {
                radius = Double.parseDouble(radiusTextField.getText());
            }
            catch (Exception e) {
                return;
            }

            if(radius < 10 && radius < 500)
                return;

            this.nodeButtonControl.setRadius(radius);
        });
    }

    /** Возвращает true если инспектор активен. */
    public boolean getIsActive () {
        return isActive;
    }

    /** Метод отображения инспектора. */
    public void show () {

        borderPane.setRight(inspectorNodeLayer);

        isActive = true;
    }

    /** Скрытие инспектора. */
    public void hide () {
        borderPane.setRight(null);

        isActive = false;
    }

}
