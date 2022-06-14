package model.device;

import javafx.beans.property.SimpleIntegerProperty;
import services.converter.ComboConverter;
import services.converter.Converter;

import java.util.ArrayList;

public class ComboDevice extends Device {

    private Integer [] data;
    private SimpleIntegerProperty current;

    @Override
    public Converter getConverter() {
        return new ComboConverter(data, current);
    }

    @Override
    public void initDevice(String n, SimpleIntegerProperty c, SimpleIntegerProperty t, ArrayList<Integer> p) {
        setName(n);
        setCount(c);
        setTime(t);
        data = p.toArray(new Integer[p.size()]);
        current = new SimpleIntegerProperty(p.get(0));
    }

    @Override
    public Integer getPower() {
        return current.get();
    }

}
