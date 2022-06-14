package view.binding;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ComboBindingControl<T> extends ComboBox implements BindingControl {

    private SimpleIntegerProperty current;

    public ComboBindingControl(ObservableList<T> items, SimpleIntegerProperty cur){
        super(items);
        current = cur;
        getSelectionModel().select(0);
        setOnAction(event -> current.set((Integer) this.getValue()));
    }

    @Override
    public void clearData() {
        getSelectionModel().select(0);
    }
}
