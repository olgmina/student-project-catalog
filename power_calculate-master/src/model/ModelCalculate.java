package model;

import java.util.ArrayList;

public class ModelCalculate {

    private ArrayList<Integer> allCalculates;
    private Float payment;
    private Float consumption;

    public ModelCalculate(){
        allCalculates = new ArrayList<>();
        payment = new Float(0);
        consumption = new Float(0);
    }

    public ArrayList<Integer> getAllCalculates() {
        return allCalculates;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Float getConsumption() {
        return consumption;
    }

    public void setConsumption(Float consumption) {
        this.consumption = consumption;
    }
}
