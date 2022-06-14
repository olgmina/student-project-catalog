package model.device;

import javafx.beans.property.SimpleIntegerProperty;
import services.converter.Converter;
import services.converter.TextConverter;

import java.util.ArrayList;

public class TextDevice extends Device {

    private SimpleIntegerProperty data;

    @Override
    public Converter getConverter() {
        return new TextConverter(data);
    }

    @Override
    public void initDevice(String n, SimpleIntegerProperty c, SimpleIntegerProperty t, ArrayList<Integer> p) {
        setName(n);
        setCount(c);
        setTime(t);
        data = new SimpleIntegerProperty(p.get(0));
    }

    @Override
    public Integer getPower() {
        return data.get();
    }
}
