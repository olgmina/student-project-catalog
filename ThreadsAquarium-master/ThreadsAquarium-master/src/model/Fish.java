package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Fish extends Rectangle implements Runnable {

    private Thread t;
    private GraphicsContext gr;
    private boolean flagX = true;
    private boolean flagY = true;
    private Image bgImage;
    private int i = 0;
    private ArrayList<FishFood> fishFoods;


    public Fish(int x, int y, Color color, int width, int height, GraphicsContext gr, Image bgImage, boolean flagX, boolean flagY, int i, ArrayList<FishFood> fishFoods) {
        super(x, y, width, height);
        super.setFill(color);
        this.gr = gr;
        this.bgImage = bgImage;
        this.flagX = flagX;
        this.flagY = flagY;
        this.i = i;
        this.fishFoods = fishFoods;
        t = new Thread(this);
    }

    public Thread getT() {
        return t;
    }

    @Override
    public void run() {

        while (true) {


            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double canWidth, canHeigth;

            synchronized (gr) {
                canWidth = gr.getCanvas().getWidth();
                canHeigth = gr.getCanvas().getHeight();

                if (i == 25) {
                    i = 0;
                    flagY = !flagY;
                }
                gr.setFill(super.getFill());
                gr.fillOval(getX(), getY(), getWidth(), getHeight());
            }

            int foodX, foodY, foodWidth, foodHeight;

            synchronized (fishFoods) {
                for (int j = 0; j < fishFoods.size(); j++) {
                    foodX = fishFoods.get(j).getX();
                    foodY = fishFoods.get(j).getY();
                    synchronized (fishFoods) {
                        foodWidth = fishFoods.get(j).getWidth();
                        foodHeight = fishFoods.get(j).getHeight();
                    }
                    if (    this.getY() <= foodY + foodHeight &&
                            this.getY() + this.getWidth() >= foodY &&
                            this.getX() <= foodX + foodWidth &&
                            this.getX() + this.getHeight() >= foodX
                    ) {
                        if((this.getWidth() < canWidth) && (this.getHeight() < canHeigth)) {
                            this.setWidth(this.getWidth() * 1.1);
                            this.setHeight(this.getHeight() * 1.1);
                        }
                        synchronized (fishFoods) {
                            fishFoods.get(j).stop();
                            fishFoods.remove(j);
                        }
                    }
                }
            }

            if (flagX) {
                if (super.getX() <= canWidth - super.getWidth())
                    super.setX(super.getX() + 1);
                else
                    flagX = false;
            }
            if (!flagX) {
                if (super.getX() >= 0)
                    super.setX(super.getX() - 1);
                else
                    flagX = true;
            }
            if (flagY) {
                if (super.getY() <= canHeigth - super.getHeight())
                    super.setY(super.getY() + 1);
                else
                    flagY = false;
            }
            if (!flagY) {
                if (super.getY() >= 0)
                    super.setY(super.getY() - 1);
                else
                    flagY = true;
            }
            i++;
        }
    }


    public void startFish() {
        t.start();
    }

    public void stopFish() {
        t.stop();
    }

    public void addFood(ArrayList<FishFood> fishFoods) {
        this.fishFoods = fishFoods;
    }
}




