package constructors;

import javafx.event.ActionEvent;
import model.*;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Random random = new Random();
    public Canvas canvas;
    public GraphicsContext gr;
    public AnchorPane anchorPane;
    public ArrayList<Fish> fishes = new ArrayList<>();
    public ArrayList<FishFood> fishFoods = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gr = canvas.getGraphicsContext2D();
        Image img = new Image(getClass().getResourceAsStream("../resources/aquarium.jpg"));
        int x, y, width, heigth, i;
        Color color;
        boolean flagX, flagY;
        int canWidth = (int)gr.getCanvas().getWidth();
        int canHeight = (int)gr.getCanvas().getHeight();

        gr.drawImage(img, 0, 0);

        Aquarium aquarium = new Aquarium(img, gr, 100);

        for (int k = 0; k < 10; k++){
            width = random.nextInt(canWidth / 12);
            heigth = random.nextInt(canHeight / 12);
            x = random.nextInt(canWidth - width);
            y = random.nextInt(canHeight - heigth);
            i = random.nextInt(25);
            color = Color.color(random.nextFloat(), random.nextFloat(), random.nextFloat());
            flagX = random.nextBoolean();
            flagY = random.nextBoolean();
            fishes.add(new Fish(x, y, color, width, heigth, gr, img, flagX, flagY, i, fishFoods));
            aquarium.addFish(new Fish(x, y, color, width, heigth, gr, img, flagX, flagY, i, fishFoods));
        }
        aquarium.startAquarium();
    }

    public void addFood(ActionEvent actionEvent) {
        int x, y, canWidth, canHeight;
        synchronized (gr) {
            canWidth = (int) gr.getCanvas().getWidth();
            canHeight = (int) gr.getCanvas().getHeight();

            for (int i = 0; i < 25; i++) {
                x = random.nextInt(canWidth - 10);
                y = random.nextInt(canHeight - 10) - canHeight;
                fishFoods.add(new FishFood(gr, x, y));
            }
        }
    }
}
