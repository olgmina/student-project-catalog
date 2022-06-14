package view.binding.types;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import services.validate.TextFormatNumber;

public class BindingInteger extends BindingType {

    private SimpleIntegerProperty data;

    public BindingInteger(SimpleIntegerProperty data){
        this.data = data;
        onKeyUp = event -> { // validate data
            if(((TextField) event.getSource()).getText().equals("")){
                this.data.set(0);
            } else {
                this.data.set(Integer.parseInt(((TextField) event.getSource()).getText()));
            }
        };
        textFormat = new TextFormatNumber().getTextFormatterInteger();
    }

    @Override
    public void setBindingType(TextField n) {
        n.setOnKeyTyped(onKeyUp);
        n.setTextFormatter(textFormat);
    }

    @Override
    public void setDefaultValue() {
        data.set(0);
    }
}
