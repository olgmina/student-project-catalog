package services.converter;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import view.binding.BindingControl;
import view.binding.ComboBindingControl;

public class ComboConverter extends Converter {

    private Integer [] data;
    private SimpleIntegerProperty current;

    public ComboConverter(Integer[] data, SimpleIntegerProperty cur) {
        if(data != null && data.length != 0) {
            this.data = data;
            current = cur;
        }
    }

    @Override
    public BindingControl createNode() {
        return new ComboBindingControl<>(FXCollections.observableArrayList(data), current);
    }

}
