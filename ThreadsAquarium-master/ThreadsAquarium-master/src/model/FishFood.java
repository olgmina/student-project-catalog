package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FishFood extends Thread{

    private  Thread t;
    private GraphicsContext gr;
    private int x;
    private int y;
    private int width = 10;
    private int height = 10;

    public FishFood(GraphicsContext gr, int x, int y) {
        this.gr = gr;
        this.x = x;
        this.y = y;
        this.start();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (gr) {
                gr.setFill(Color.BLACK);
                gr.fillOval(x, y, width, height);
            }
            if(this.getY() <= gr.getCanvas().getHeight() - width)
            y++;
            else
                this.stop();
        }
    }
}
