package view.binding;

import javafx.scene.control.TextField;
import view.binding.types.BindingType;


public class TextBindingControl extends TextField implements BindingControl {

    private BindingType bindingType;

    public TextBindingControl(String name, BindingType bindingType){
        super(String.valueOf(name));
        this.bindingType = bindingType;
        this.bindingType.setBindingType(this);
    }

    @Override
    public void clearData() {
        setText("");
        bindingType.setDefaultValue();
    }

}
