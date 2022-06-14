package model.device;

import javafx.beans.property.SimpleIntegerProperty;
import services.converter.Converter;

import java.util.ArrayList;

public abstract class Device {

    private String name;
    private SimpleIntegerProperty count;
    private SimpleIntegerProperty time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleIntegerProperty getCount() {
        return count;
    }

    public void setCount(SimpleIntegerProperty count) {
        this.count = count;
    }

    public SimpleIntegerProperty getTime() {
        return time;
    }

    public void setTime(SimpleIntegerProperty time) {
        this.time = time;
    }

    public abstract Converter getConverter();
    public abstract void initDevice(String n, SimpleIntegerProperty c, SimpleIntegerProperty t, ArrayList<Integer> p);
    public abstract Integer getPower();

}
