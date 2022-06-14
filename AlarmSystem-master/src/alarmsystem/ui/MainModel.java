package alarmsystem.ui;

import alarmsystem.events.Event;

import java.util.ArrayList;
import java.util.List;

public class MainModel {

    private final boolean[] sensors = new boolean[10];
    private final List<Event> events = new ArrayList<>();
    private int fireCounter = 0;

    public boolean[] getSensors() {
        return sensors;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getFireCounter() {
        return fireCounter;
    }

    public void setFireCounter(int fireCounter) {
        this.fireCounter = fireCounter;
    }
}
