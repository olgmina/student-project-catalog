package view.binding.types;

import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TextField;
import services.validate.TextFormatNumber;

public class BindingFloat extends BindingType {

    private SimpleFloatProperty data;

    public BindingFloat(SimpleFloatProperty data){
        this.data = data;
        onKeyUp = event -> { // validate data
            if(((TextField) event.getSource()).getText().equals("")){
                this.data.set(0.f);
            } else {
                this.data.set(Float.parseFloat(((TextField) event.getSource()).getText()));
            }
        };
        textFormat = new TextFormatNumber().getTextFormatterFloat();
    }

    @Override
    public void setBindingType(TextField n) {
        n.setOnKeyTyped(onKeyUp);
        n.setTextFormatter(textFormat);
    }

    @Override
    public void setDefaultValue() {
        data.set(0.f);
    }
}
