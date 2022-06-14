package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Aquarium implements Runnable{

    private Image image;
    private Timer timer;
    private GraphicsContext gr;
    private ArrayList<Fish> fishes = new ArrayList<>();

    public Aquarium(Image image, GraphicsContext gr, int delay) {
        this.image = image;
        this.gr = gr;
        timer = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        timer.start();
    }

    public int getSize(){
        return fishes.size();
    }

    public void addFish(Fish fish){
        fishes.add(fish);
    }

    @Override
    public void run() {
        synchronized (gr) {
            gr.clearRect(0,0, gr.getCanvas().getWidth(), gr.getCanvas().getHeight());
            gr.drawImage(image, 0, 0);
        }
    }

    public void startAquarium() {
        synchronized (gr) {
            for (Fish fish : fishes) {
                fish.startFish();
            }
        }
    }

    public void stopAquarium(){
        synchronized (gr) {
            for (Fish fish : fishes) {
                fish.stopFish();
            }
        }
    }
}
