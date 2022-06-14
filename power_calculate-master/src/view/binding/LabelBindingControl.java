package view.binding;

import javafx.scene.control.Label;

public class LabelBindingControl extends Label implements BindingControl {

    public LabelBindingControl(String data){
        super(String.valueOf(data));
    }

    @Override
    public void clearData() {
        // not used
        return;
    }
}
