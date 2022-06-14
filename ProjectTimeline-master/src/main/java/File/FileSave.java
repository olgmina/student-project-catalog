package File;

import ProjectTimeLine.TimeLine.Node;
import ProjectTimeLine.TimeLine.ProjectTimeLine;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/** Класс по работе с сохранением проекта.
 * Реализует интерфейс IFileSave */
public class FileSave extends Application implements IFileSave {

    private Stage stage;
    private Label infoText;

    /** Конструктор класса, принимает Label для вывода информации о действиях в системе */
    public FileSave (Label infoText) {
        this.infoText = infoText;
    }

    /** Старт программы */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    /** Метод сохранения проекта. */
    public void save (ProjectTimeLine projectTimeLine) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selected directory for save");

        File selectedFile = directoryChooser.showDialog(stage);

        if(selectedFile == null)
            return;

        String path = selectedFile.getPath() + "\\" + projectTimeLine.startNode.name + ".tl";

        File file = new File(path);

        if(file.exists()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Внимание!");
            alert.setContentText("В этой директории уже имеется файл с таким же названием, он будет перезаписан.");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
                return;
            } else if (option.get() == ButtonType.CANCEL) {
                return;
            } else if (option.get() == ButtonType.CLOSE) {
                return;
            }
        }

        try(FileWriter writer = new FileWriter(path, false))
        {
            writer.write("name:" + projectTimeLine.startNode.name + ",");
            writer.append('\n');

            writer.write("UICanvas {");
            writer.append('\n');

            writer.write("width:" + projectTimeLine.uiCanvas.width + ",");
            writer.append('\n');

            writer.write("height:" + projectTimeLine.uiCanvas.height + ",");
            writer.append('\n');

            Color bgrColor = projectTimeLine.uiCanvas.color;
            writer.write("color:rgb(" + bgrColor.getRed() + ", " + bgrColor.getGreen() + ", " + bgrColor.getBlue() + "),");
            writer.append('\n');

            writer.write("startNode {");
            writer.append('\n');

            writer.write(getNodeToString(projectTimeLine.startNode));

            writer.write("}");
            writer.append('\n');



            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        infoText.setText("Save success");
    }

    private String getNodeToString (Node node) {

        String setting = new String(
                "[\n" +
                        "name:" + node.name + ",\n" +
                        "description:" + node.description +",\n" +
                        "UINode {\n" +
                        "posX:" + node.uiNode.posX + ",\n" +
                        "posY:" + node.uiNode.posY + ",\n" +
                        "radius:" + node.uiNode.radius + ",\n" +
                        "color:rgb(" + node.uiNode.color.getRed() + ", " + node.uiNode.color.getGreen() + ", " + node.uiNode.color.getBlue() + ")\n" +
                        "}\n"
        );

        if(node.getChildNodes().size() > 0)
            setting += "childNodes {\n";

        for (Node item: node.getChildNodes()) {
            setting += getNodeToString(item);
        }

        if(node.getChildNodes().size() > 0)
            setting += "}\n";

        setting += "]\n";
        return setting;
    }
}
