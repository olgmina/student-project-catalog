package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import model.DataBaseInterface;
import model.DbHandler;
import model.ReferencePoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public FlowPane paneForButtons;                 // pane for buttons

    public RadioButton floor_1 = new RadioButton("-1"); //
    public RadioButton floor1 = new RadioButton("1");   //
    public RadioButton floor2 = new RadioButton("2");   //
    public RadioButton floor3 = new RadioButton("3");   //
    public RadioButton floor4 = new RadioButton("4");   //
    public RadioButton floor5 = new RadioButton("5");   //
    public ToggleGroup group = new ToggleGroup();
    public TableView<ReferencePoint> tableData;
    public TextField fromText;
    public TextField toText;
    public TableColumn<ReferencePoint, String> nameColumn;

    public boolean fromActive = true;
    public ReferencePoint fromPoint = null;
    public ReferencePoint toPoint = null;

    Image building1floor1 = new Image("resources/building1floor1.png");     //building 1 floor 1

    private static BufferedImage building1floor_1_edit;  // buffered images
    private static BufferedImage building1floor1_edit;   // buffered images
    private static BufferedImage building1floor2_edit;   // buffered images
    private static BufferedImage building1floor3_edit;   // buffered images
    private static BufferedImage building1floor4_edit;   // buffered images
    private static BufferedImage building1floor5_edit;   // buffered images

    public ImageView img_view;                                              // view for images
    private static final int MIN_PIXELS = 10;                               // min pixels for scrolling
    private static final int blueRGB = 255;                                 // blue
    private static final int greenRGB = blueRGB * 256;                      // green
    private static final int redRGB = greenRGB * 256;                       // red
    private static final int yellowRGB = redRGB + greenRGB;                 // yellow
    private final DataBaseInterface dataBase = DbHandler.getInstance();     // database handler

    // initializing
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        fromText.setOnKeyReleased(e -> {
            fromActive = true;
            tableData.getItems().clear();
            ArrayList<ReferencePoint> find = dataBase.getPointsByPartOfName(fromText.getText());
            for (ReferencePoint referencePoint : find)
                tableData.getItems().add(referencePoint);
        });
        toText.setOnKeyReleased(e -> {
            fromActive = false;
            tableData.getItems().clear();
            ArrayList<ReferencePoint> finded = dataBase.getPointsByPartOfName(toText.getText());
            for (ReferencePoint referencePoint : finded) {
                tableData.getItems().add(referencePoint);
            }
        });
        tableData.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2)
                if (!tableData.getSelectionModel().isEmpty()) {
                    int id = tableData.getSelectionModel().getSelectedItem().getId();
                    int id_building = tableData.getSelectionModel().getSelectedItem().getId_building();
                    int id_floor = tableData.getSelectionModel().getSelectedItem().getId_floor();
                    int x = tableData.getSelectionModel().getSelectedItem().getX();
                    int y = tableData.getSelectionModel().getSelectedItem().getY();
                    String name = tableData.getSelectionModel().getSelectedItem().getName();
                    if (fromActive) {
                        fromPoint = new ReferencePoint(id, id_building, id_floor, name, x, y);
                        fromText.setEditable(false);
                        fromText.setDisable(true);
                        fromText.setText(fromPoint.toString());
                    }
                    else {
                        toPoint = new ReferencePoint(id, id_building, id_floor, name, x, y);
                        toText.setEditable(false);
                        toText.setDisable(true);
                        toText.setText(toPoint.toString());
                    }
                    tableData.getItems().clear();
                }
        });

        clear();
        floor_1.setToggleGroup(group);
        floor1.setToggleGroup(group);
        floor2.setToggleGroup(group);
        floor3.setToggleGroup(group);
        floor4.setToggleGroup(group);
        floor5.setToggleGroup(group);
        floor_1.setSelected(true);
        paneForButtons.getChildren().addAll(floor_1, floor1, floor2, floor3, floor4, floor5);
        for (Node child : paneForButtons.getChildren()) {
            ((RadioButton)child).setPadding(new Insets(0,20,0,0));
        }
        clearBuildings(1, 2, 3, 4, 5, 6, 7, 8); // initializing images

        // view drag`n`zoom
        double width = building1floor1.getWidth();
        double height = building1floor1.getHeight();
        img_view.setImage(building1floor1);
        img_view.setPreserveRatio(true);
        img_view.setFitWidth(width);
        img_view.setFitHeight(height);
        reset(img_view, img_view.getFitWidth(), img_view.getFitHeight());

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
        img_view.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(img_view, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });
        img_view.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(img_view, new Point2D(e.getX(), e.getY()));
            shift(img_view, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(img_view, new Point2D(e.getX(), e.getY())));
        });
        img_view.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = img_view.getViewport();

            double scale = clamp(Math.pow(1.01, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );

            Point2D mouse = imageViewToImage(img_view, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale, 0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale, 0, height - newHeight);

            img_view.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });
        img_view.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(img_view, width, height);
            }
        });

        floor_1.setOnAction(e -> newImage(img_view, building1floor_1_edit));
        floor1.setOnAction(e -> newImage(img_view, building1floor1_edit));
        floor2.setOnAction(e -> newImage(img_view, building1floor2_edit));
        floor3.setOnAction(e -> newImage(img_view, building1floor3_edit));
        floor4.setOnAction(e -> newImage(img_view, building1floor4_edit));
        floor5.setOnAction(e -> newImage(img_view, building1floor5_edit));
    }

    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        else if (value > max)
            return max;
        return value;
    }

    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    public void howToGo() {
        if (fromPoint != null && toPoint != null) {
            //if "from" and "to" not empty
            if (fromPoint.getId_building() == toPoint.getId_building()) {
                // same building
                if (fromPoint.getId_floor() == toPoint.getId_floor()) {
                    // same floor
                    radioFromTo(dataBase.getFloorFromId(fromPoint.getId_floor()), dataBase.getFloorFromId(toPoint.getId_floor()));
                    clearBuildings(dataBase.getBuildingFromId(fromPoint.getId_building()));
                    BufferedImage image = getBufferedImage(dataBase.getBuildingFromId(fromPoint.getId_building()), dataBase.getFloorFromId(fromPoint.getId_floor()));
                    goTo(image, fromPoint, toPoint);
                    drawPixel(image, fromPoint.getX(), fromPoint.getY(), redRGB);
                    drawPixel(image, toPoint.getX(), toPoint.getY(), greenRGB);
                    setSelected(dataBase.getBuildingFromId(fromPoint.getId_building()), dataBase.getFloorFromId(fromPoint.getId_floor()));
                } else {
                    //other floors
                    radioFromTo(dataBase.getFloorFromId(fromPoint.getId_floor()), dataBase.getFloorFromId(toPoint.getId_floor()));
                    int building = dataBase.getBuildingFromId(fromPoint.getId_building()); // get building number
                    int floorFrom = dataBase.getFloorFromId(fromPoint.getId_floor()); // get "from" floor number
                    int floorTo = dataBase.getFloorFromId(toPoint.getId_floor()); // get "to" floor number
                    clearBuildings(dataBase.getBuildingFromId(fromPoint.getId_building()));
                    BufferedImage floorFromImage = getBufferedImage(building, floorFrom); // getting image
                    BufferedImage floorToImage = getBufferedImage(building, floorTo); // getting image
                    clearFloor(building, floorFrom); // clear "from" floor
                    clearFloor(building, floorTo); // clear "to" floor
                    ArrayList<ReferencePoint> fromFloorStairs = dataBase.getStairs(building, floorFrom);
                    ArrayList<ReferencePoint> toFloorStairs = dataBase.getStairs(building, floorTo);

                    ArrayList<Double> distances = new ArrayList<>();
                    for (ReferencePoint stairs : fromFloorStairs)
                        distances.add(Math.sqrt(Math.pow(stairs.getX() - fromPoint.getX(), 2) + Math.pow(stairs.getY() - fromPoint.getY(), 2)));
                    int min_index = 0;
                    for (int i = 0; i < distances.size(); i++)
                        if (distances.get(min_index) > distances.get(i))
                            min_index = i;
                    ReferencePoint stairsFrom = fromFloorStairs.get(min_index);
                    ReferencePoint stairsTo = null;
                    for (ReferencePoint stairs : toFloorStairs) {
                        if (stairs.getX() == stairsFrom.getX() && stairs.getY() == stairsFrom.getY())
                            stairsTo = stairs;
                    }
                    goTo(floorFromImage, fromPoint, stairsFrom);
                    assert stairsTo != null;
                    goTo(floorToImage, stairsTo, toPoint);
                    drawPixel(floorFromImage, fromPoint.getX(), fromPoint.getY(), redRGB);
                    drawPixel(floorToImage, toPoint.getX(), toPoint.getY(), greenRGB);
                    setSelected(dataBase.getBuildingFromId(fromPoint.getId_building()), dataBase.getFloorFromId(fromPoint.getId_floor()));
                }
            }
            /*else {
                //other buildings
            }*/
        }
    }

    private void goTo(BufferedImage image, ReferencePoint fromPoint, ReferencePoint toPoint) {
        int building = dataBase.getBuildingFromId(fromPoint.getId_building()); // get building number
        int floor = dataBase.getFloorFromId(fromPoint.getId_floor()); // get floor number
        clearFloor(building, floor); // clear floor
        ArrayList<ReferencePoint> floorPoints = dataBase.getFloorPoints(building, floor); // getting only needed points
        ArrayList<ReferencePoint> usefulPoints = deleteDeadEnds(floorPoints, fromPoint, toPoint); // array without dead ends
        for (ReferencePoint usefulPoint : usefulPoints) {
            ArrayList<ReferencePoint> connectedPoints = dataBase.getConnectedPoints(usefulPoint); // getting points that connected to current
            for (ReferencePoint connectedPoint : connectedPoints)
                for (ReferencePoint point : usefulPoints)
                    if (connectedPoint.getId() == point.getId())
                        // array without dead ends contains point that connected to current
                        // draw line between two points
                        drawLine(image, usefulPoint.getX(), usefulPoint.getY(), connectedPoint.getX(), connectedPoint.getY(), yellowRGB);
        }
        newImage(img_view, image);
    }

    private void newImage(ImageView imageView, BufferedImage image) {
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }

    // get image from building and floor
    private BufferedImage getBufferedImage (int building, int floor) {
        switch (building) {
            case (1):
                if (floor == -1) {
                    return building1floor_1_edit;
                }
                else if (floor == 1) {
                    return building1floor1_edit;
                }
                else if (floor == 2) {
                    return building1floor2_edit;
                }
                else if (floor == 3) {
                    return building1floor3_edit;
                }
                else if (floor == 4) {
                    return building1floor4_edit;
                }
                else if (floor == 5) {
                    return building1floor5_edit;
                }
                break;
            case (2): /*code for 2 building*/
                break;
            case (3): /*code for 3 building*/
                break;
            case (4): /*code for 4 building*/
                break;
            case (5): /*code for 5 building*/
                break;
            case (6): /*code for 6 building*/
                break;
            case (7): /*code for 7 building*/
                break;
            case (8): /*code for 8 building*/
                break;
        }
        return null;
    }

    private void radioFromTo(int fromFloor, int toFloor) {
        floor_1.setDisable(true);
        floor1.setDisable(true);
        floor2.setDisable(true);
        floor3.setDisable(true);
        floor4.setDisable(true);
        floor5.setDisable(true);
        if (fromFloor == -1)
            floor_1.setDisable(false);
        else if (fromFloor == 1)
            floor1.setDisable(false);
        else if (fromFloor == 2)
            floor2.setDisable(false);
        else if (fromFloor == 3)
            floor3.setDisable(false);
        else if (fromFloor == 4)
            floor4.setDisable(false);
        else if (fromFloor == 5)
            floor5.setDisable(false);
        if (toFloor == -1)
            floor_1.setDisable(false);
        else if (toFloor == 1)
            floor1.setDisable(false);
        else if (toFloor == 2)
            floor2.setDisable(false);
        else if (toFloor == 3)
            floor3.setDisable(false);
        else if (toFloor == 4)
            floor4.setDisable(false);
        else if (toFloor == 5)
            floor5.setDisable(false);
    }

    private void setSelected (int building, int floor) {
        switch (building) {
            case (1):
                if (floor == -1) {
                    floor_1.setSelected(true);
                    newImage(img_view, building1floor_1_edit);
                }
                else if (floor == 1) {
                    floor1.setSelected(true);
                    newImage(img_view, building1floor1_edit);
                }
                else if (floor == 2) {
                    floor2.setSelected(true);
                    newImage(img_view, building1floor2_edit);
                }
                else if (floor == 3) {
                    floor3.setSelected(true);
                    newImage(img_view, building1floor3_edit);
                }
                else if (floor == 4) {
                    floor4.setSelected(true);
                    newImage(img_view, building1floor4_edit);
                }
                else if (floor == 5) {
                    floor5.setSelected(true);
                    newImage(img_view, building1floor5_edit);
                }
                break;
            case (2): /*code for 2 building*/
                break;
            case (3): /*code for 3 building*/
                break;
            case (4): /*code for 4 building*/
                break;
            case (5): /*code for 5 building*/
                break;
            case (6): /*code for 6 building*/
                break;
            case (7): /*code for 7 building*/
                break;
            case (8): /*code for 8 building*/
                break;
        }
    }

    // delete dead ends from points array
    private ArrayList<ReferencePoint> deleteDeadEnds(ArrayList<ReferencePoint> referencePoints, ReferencePoint from, ReferencePoint to) {
        ArrayList<ReferencePoint> newList = new ArrayList<>(referencePoints);
        ArrayList<ArrayList<ReferencePoint>> connect = new ArrayList<>();
        for (ReferencePoint referencePoint : newList)
            connect.add(new ArrayList<>(dataBase.getConnectedPoints(referencePoint)));
        do {
            int deadEndsCount = 0;
            for (int i = 0; i < newList.size(); i++) {
                if (connect.get(i).size() <= 1 && newList.get(i).getId() != from.getId() && newList.get(i).getId() != to.getId()) {
                    deadEndsCount++;
                    int deletedPoint = newList.get(i).getId();
                    newList.remove(i);
                    connect.remove(i);
                    for (ArrayList<ReferencePoint> points : connect)
                        for (ReferencePoint point : points)
                            if (point.getId() == deletedPoint) {
                                points.remove(point);
                                break;
                            }
                    break;
                }
            }
            if (deadEndsCount == 0)
                break;
        } while (true);
        return newList;
    }

    // clear buildings map
    private void clearBuildings(int ...buildings) {
        for (int building : buildings) {
            switch (building) {
                case (1):
                    try {
                        building1floor_1_edit = ImageIO.read(new File("src/resources/building1floor-1.png"));
                        building1floor1_edit = ImageIO.read(new File("src/resources/building1floor1.png"));     //
                        building1floor2_edit = ImageIO.read(new File("src/resources/building1floor2.png"));     //
                        building1floor3_edit = ImageIO.read(new File("src/resources/building1floor3.png"));     //
                        building1floor4_edit = ImageIO.read(new File("src/resources/building1floor4.png"));     //
                        building1floor5_edit = ImageIO.read(new File("src/resources/building1floor5.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Image newImage = SwingFXUtils.toFXImage(building1floor_1_edit, null);
                    img_view.setImage(newImage);
                    break;
                case (2): /*code for 2 building*/
                    break;
                case (3): /*code for 3 building*/
                    break;
                case (4): /*code for 4 building*/
                    break;
                case (5): /*code for 5 building*/
                    break;
                case (6): /*code for 6 building*/
                    break;
                case (7): /*code for 7 building*/
                    break;
                case (8): /*code for 8 building*/
                    break;
            }
        }
    }

    // clear one floor
    private void clearFloor(int building, int floor) {
        switch (building) {
            case (1):
                if (floor == -1) {
                    // buffered images
                    BufferedImage img_1;
                    img_1 = building1floor_1_edit;
                    newImage(img_view, img_1);
                }
                else if (floor == 1) {
                    //
                    BufferedImage img1;
                    img1 = building1floor1_edit;
                    newImage(img_view, img1);
                }
                else if (floor == 2) {
                    //
                    BufferedImage img2;
                    img2 = building1floor2_edit;
                    newImage(img_view, img2);
                }
                else if (floor == 3) {
                    //
                    BufferedImage img3;
                    img3 = building1floor3_edit;
                    newImage(img_view, img3);
                }
                else if (floor == 4) {
                    //
                    BufferedImage img4;
                    img4 = building1floor4_edit;
                    newImage(img_view, img4);
                }
                else if (floor == 5) {
                    //
                    BufferedImage img5;
                    img5 = building1floor5_edit;
                    newImage(img_view, img5);
                }
                break;
            case (2): /*code for 2 building*/
                break;
            case (3): /*code for 3 building*/
                break;
            case (4): /*code for 4 building*/
                break;
            case (5): /*code for 5 building*/
                break;
            case (6): /*code for 6 building*/
                break;
            case (7): /*code for 7 building*/
                break;
            case (8): /*code for 8 building*/
                break;
        }
    }

    // draw line
    public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2, int colorRGB) {
        if (x2 >= x1 && y1 == y2)
            for (int i = x1; i <= x2; i++)
                drawPixel(image, i, y1, colorRGB);
        else if (x2 < x1 && y1 == y2)
            for (int i = x2; i < x1; i++)
                drawPixel(image, i, y1, colorRGB);
        else if (y2 >= y1 && x1 == x2)
            for (int i = y1; i <= y2; i++)
                drawPixel(image, x1, i, colorRGB);
        else if (y2 < y1 && x1 == x2)
            for (int i = y2; i < y1; i++)
                drawPixel(image, x1, i, colorRGB);
        /*else {
            //diagonal lines
        }*/
    }

    // draw point
    public void drawPixel(BufferedImage image, int x, int y, int colorRGB) {
        for (int i = x - 2; i < x + 2; i++) {
            for (int j = y - 2; j < y + 2; j++) {
                image.setRGB(i, j, colorRGB);
            }
        }
        Image newImage = SwingFXUtils.toFXImage(image, null);
        img_view.setImage(newImage);
    }

    // clear view
    public void clear() {
        clearBuildings(1,2,3,4,5,6,7,8);
        floor_1.setSelected(true);
        newImage(img_view, building1floor_1_edit);
        fromPoint = null;
        fromText.setEditable(true);
        fromText.setDisable(false);
        fromText.setText("");
        toPoint = null;
        toText.setEditable(true);
        toText.setDisable(false);
        toText.setText("");
        tableData.getItems().clear();
        floor_1.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(false);
        floor4.setDisable(false);
        floor5.setDisable(false);
    }
}
