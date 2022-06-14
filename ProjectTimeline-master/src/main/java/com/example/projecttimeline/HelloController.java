package com.example.projecttimeline;

import File.FileManager;
import ProjectTimeLine.TimeLine.Node;
import ProjectTimeLine.TimeLine.ProjectTimeLine;
import ProjectTimeLine.TimeLine.UINode;
import Tool.CreateNodeButtonControlTool;
import Tool.PlaceNodeTool;
import Tool.ITool;
import UI.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/** Главный крнтроллер програмимы, отвечает за работу с view, содержит и инициализирует все переменные все  */
public class HelloController implements Initializable {

    /** Карта проекта.
     * Содержит все параметры карты.  */
    private ProjectTimeLine projectTimeLine;

    /** Интерфейс, содержит выбранный интсрумент.  */
    private ITool currentTool;

    //FileManager

    /** Класс по по рвботе с загрузкой и сохранением проекта.  */
    private FileManager fileManager;

    //Main

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private AnchorPane mainCanvasAnchorPane;

    @FXML
    private Label infoLabel;

    //TimeLineTreeView

    @FXML
    private TreeView<NodeButtonControl> treeView;

    private TimeLineTreeView timeLineTreeView;


    //InspectorPane

    private InspectorNode inspectorNode;

    @FXML
    private VBox inspectorNodeVBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker inspectorNodeColorPicker;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField radiusTextField;


    //CanvasSetting

    private InspectorCanvas inspectorCanvas;

    @FXML
    private TextField sizeXCanvas;
    @FXML
    private TextField sizeYCanvas;
    @FXML
    private ColorPicker colorPickerCanvas;


    /** Стартовый нод, расположенный всегда на экране.  */
    private NodeButtonControl startNodeButtonControl;


    /** Инициализация при запуске.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newProject();
    }

    /** Метод выбора инструмента создания нового нода.  */
    @FXML
    protected void onCreateNewNode() {

        Node node = new Node();
        node.name = "New Node";

        NodeButtonControlBuilder nodeButtonControlBuilder = new NodeButtonControlBuilder(node, currentTool, mainCanvasAnchorPane, inspectorNode, infoLabel);
        NodeButtonControl nodeButtonControl = nodeButtonControlBuilder.create();


        if(currentTool != null)
            currentTool.end();

        currentTool = new PlaceNodeTool(inspectorNode.getNodeButtonControl(), nodeButtonControl, mainCanvasAnchorPane, timeLineTreeView, infoLabel);
        currentTool.start();
    }

    /** Метод удаления выбранного нода.  */
    @FXML
    protected void onDeleteNewNode() {



        if(inspectorNode.getNodeButtonControl() == null)
            return;

        if(inspectorNode.getNodeButtonControl().getParent() == null){
            return;
        }

        NodeButtonControlDelete nodeButtonControlDelete = new NodeButtonControlDelete(mainCanvasAnchorPane, timeLineTreeView, infoLabel);
        nodeButtonControlDelete.delete(inspectorNode.getNodeButtonControl());

        inspectorNode.hide();
    }

    /** Скрытие инспектора нода.  */
    @FXML
    protected void closeInspectorNode () {
        inspectorNode.hide();
    }

    /** Сохронение проекта в файл.  */
    @FXML
    protected void Save () {

        if(fileManager == null)
            fileManager = new FileManager(infoLabel);

        fileManager.getFileSave().save(projectTimeLine);
    }

    /** Загрузка проекта из файла.  */
    @FXML
    protected void Load () {

        if(fileManager == null)
            fileManager = new FileManager(infoLabel);

        ProjectTimeLine projectTimeLine = fileManager.getFileLoad().load();

        if(projectTimeLine == null)
            return;

        inspectorNode.hide();

        this.projectTimeLine = projectTimeLine;

        mainCanvasAnchorPane.getChildren().clear();
        inspectorCanvas.setProjectTimeLine(projectTimeLine);

        CreateNodeButtonControlTool createNodeButtonControlTool = new CreateNodeButtonControlTool(projectTimeLine.startNode, currentTool, inspectorNode, mainCanvasAnchorPane, timeLineTreeView, infoLabel);
        startNodeButtonControl = createNodeButtonControlTool.create(null, projectTimeLine.startNode);

        timeLineTreeView.setStartNode(startNodeButtonControl);
        timeLineTreeView.treeViewUpdate();
    }

    /** Метод создания нового проекта.  */
    private void newProject () {

        projectTimeLine = new  ProjectTimeLine();

        projectTimeLine.uiCanvas.width = mainCanvasAnchorPane.getPrefWidth();
        projectTimeLine.uiCanvas.height = mainCanvasAnchorPane.getPrefHeight();
        projectTimeLine.uiCanvas.color = Color.rgb(255,255, 255);


        inspectorCanvas = new InspectorCanvas(mainCanvasAnchorPane, sizeXCanvas, sizeYCanvas, colorPickerCanvas, infoLabel);
        inspectorCanvas.setProjectTimeLine(projectTimeLine);


        inspectorNode = new InspectorNode(mainBorderPane, inspectorNodeVBox, nameTextField, descriptionTextArea, inspectorNodeColorPicker, radiusTextField);
        inspectorNode.hide();


        Node node =  new Node();
        node.name = "New Project";
        node.uiNode = new UINode();
        node.uiNode.posX = 100;
        node.uiNode.posY = 100;
        node.uiNode.radius = 150;

        projectTimeLine.startNode = node;

        timeLineTreeView = new TimeLineTreeView(treeView, inspectorNode);

        CreateNodeButtonControlTool createNodeButtonControlTool = new CreateNodeButtonControlTool(projectTimeLine.startNode, currentTool, inspectorNode, mainCanvasAnchorPane, timeLineTreeView, infoLabel);
        startNodeButtonControl = createNodeButtonControlTool.create(null, projectTimeLine.startNode);

        timeLineTreeView.setStartNode(startNodeButtonControl);

        timeLineTreeView.treeViewUpdate();
    }
}