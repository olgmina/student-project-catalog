package view;

import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import model.Model;
import model.device.Device;
import view.binding.BindingControl;
import view.binding.LabelBindingControl;
import view.binding.TextBindingControl;
import view.binding.types.BindingFloat;
import view.binding.types.BindingInteger;

public class View {

    private TextBindingControl price;

    public View(Accordion mainAccordion, HBox hBoxPrice, Model model){
        generateGUI(mainAccordion, model);
        createPriceTextField(hBoxPrice, model.getPrice());
    }

    public void generateGUI(Accordion mainAccordion, Model model){
        for(int i = 0; i < model.getGroups().size(); i++){
            GridPane gridPane = new GridPane();
            TitledPane titledPane = new TitledPane(model.getGroups().get(i), gridPane);
            mainAccordion.getPanes().add(titledPane);
            for(int j = 0;j<4;j++){
                ColumnConstraints cl = new ColumnConstraints();
                cl.setPercentWidth(25);
                gridPane.getColumnConstraints().add(cl);
            }
            for(int j = 0; j < model.getElements().get(i).size(); j++){
                RowConstraints rw = new RowConstraints();
                gridPane.getRowConstraints().add(rw);
                Device d = model.getElements().get(i).get(j);
                BindingControl [] controls = new BindingControl[]{
                        new LabelBindingControl(d.getName()),
                        new TextBindingControl(d.getCount().getValue().toString(), new BindingInteger(d.getCount())),
                        d.getConverter().createNode(),
                        new TextBindingControl(d.getTime().getValue().toString(), new BindingInteger(d.getTime()))
                };
                for(int k = 0;k<controls.length;k++){
                    GridPane.setMargin((Node)controls[k], new Insets(5, 10, 5, 10));
                    gridPane.add((Node)controls[k], k, j);
                }
            }
        }
    }

    public void createPriceTextField(HBox hBoxPrice, SimpleFloatProperty o){
        hBoxPrice.getChildren().add(price = new TextBindingControl(o.getValue().toString(), new BindingFloat(o)));
        HBox.setMargin(price, new Insets(25, 0, 0,0));
    }

    public void clearData(Accordion mainAccordion){
        for(TitledPane p: mainAccordion.getPanes()){
            for(Node n: ((GridPane)p.getContent()).getChildren()){
                ((BindingControl)n).clearData();
            }
        }
    }

    public TextBindingControl getPrice() {
        return price;
    }

}
