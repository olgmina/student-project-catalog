package sample;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.Model;
import view.Dialog;
import view.View;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Accordion mainAccordion;
    @FXML
    private HBox hBoxPrice;
    @FXML
    private TextField payment;
    @FXML
    private TextField consumption;

    private Model model;
    private View view;

    @FXML
    private void initialize() {
        initSystem();
    }

    private void initSystem(){
        model = new Model(new File("configs/list_groups.txt"), new File("configs/other.txt"));
        view = new View(mainAccordion, hBoxPrice, model);
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "ok":
                if (view.getPrice().getText().trim().isEmpty()) {
                    Dialog.showInfoDialog("Ошибка", "Заполните все поля!");
                }else{
                    model.calculate();
                    payment.setText(model.getModelCalculate().getPayment().toString());
                    consumption.setText(model.getModelCalculate().getConsumption().toString());
                }
                break;
            case "clear":
                view.clearData(mainAccordion);
                break;
        }
    }

    public void handle(ActionEvent event) throws IOException {
        model.writeFile();
    }

}
