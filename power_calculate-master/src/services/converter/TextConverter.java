package services.converter;

import javafx.beans.property.SimpleIntegerProperty;
import view.binding.BindingControl;
import view.binding.TextBindingControl;
import view.binding.types.BindingInteger;

public class TextConverter extends Converter {

    private SimpleIntegerProperty data;

    public TextConverter(SimpleIntegerProperty data) {
        this.data = data;
    }

    @Override
    public BindingControl createNode() {
        return new TextBindingControl(data.getValue().toString(), new BindingInteger(data));
    }
}
