package alarmsystem.ui;

import alarmsystem.events.Event;
import alarmsystem.events.FireEvent;
import alarmsystem.handlers.ActionChain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

public class MainController {

    @FXML private GridPane systemGrid;
    @FXML private Label stateLabel;
    private final MainModel model = new MainModel();

    private final Timer createTimer = new Timer();
    private final TimerTask createEvents = new TimerTask() {
        @Override
        public void run() {
            if(Math.random()<0.3){ //С шансом 30%
                runEvent((int)(Math.random()*9+1)); //Запускается событие на случайном датчике
            }
        }
    };
    private final Timer handlerTimer = new Timer();
    private final TimerTask handleEvents = new TimerTask() {
        @Override
        public void run() {
            Event event;
            while (!model.getEvents().isEmpty()){
                event=model.getEvents().get(0);
                handleFireEvent((FireEvent)event);
                model.getEvents().remove(event);
            }
        }
    };
    private final Timer eventCounter = new Timer();
    private final TimerTask countEvents = new TimerTask() {
        @Override
        public void run() {
            String message;
            model.setFireCounter(0);
            for (boolean sensor : model.getSensors()) {
                if (sensor) {
                    model.setFireCounter(model.getFireCounter()+1);
                }
            }
        }
    };

    @FXML
    public void initialize(){
        createTimer.schedule(createEvents,0,1000); //Запуск создания событий
        handlerTimer.schedule(handleEvents,0,1); //Запуск обработки событий
        eventCounter.schedule(countEvents,0,1); //Запуск счётчика активированных датчиков
        Arrays.fill(model.getSensors(), false);
    }

    void runEvent(int position){
        if(position<1 || position>9) return;
        System.out.println("Creating FireEvent on "+position+" position");
        Event event = new FireEvent(System.currentTimeMillis(), position, 10);
        model.getEvents().add(event);
        event.start();
    }

    public void handleFireEvent(FireEvent event){
        Circle sensor = null;
        int position = event.getPosition();
        for(Node node: systemGrid.getChildren()){ // Поиск датчика по номеру
            if(node.getId().equals("sensor"+position)){
                sensor = (Circle) node;
            }
        }
        String message;
        switch (model.getFireCounter()){
            case 0 -> message = "Датчики не сообщают о наличии дыма";
            case 1 -> {
                message = "Один из датчиков сообщает о наличии дыма";
                new ActionChain().process(1);
            }
            case 2 -> {
                message = "Два датчика сообщает о наличии дыма. Вызов оператора...";
                new ActionChain().process(2);
            }
            default -> {
                message = "В здании пожар. Запуск системы тушения...";
                new ActionChain().process(3);
            }
        }
        Platform.runLater(() -> stateLabel.setText(message));

        model.getSensors()[position]=true;
        turnLight(sensor, true);
    }

    void turnLight(Circle sensor, boolean isFire){
        sensor.setFill(isFire?Color.RED: Color.WHITE);
    }
}
