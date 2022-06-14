package view.binding.types;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

public abstract class BindingType {

    protected EventHandler<? super KeyEvent> onKeyUp;
    protected TextFormatter<String> textFormat;

    public abstract void setBindingType(TextField n);
    public abstract void setDefaultValue();

}
