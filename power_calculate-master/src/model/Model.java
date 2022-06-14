package model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.device.Device;
import model.factory_method.Creator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Model {

    private ArrayList<String> groups;
    private ArrayList<ArrayList<Device>> elements;
    private SimpleFloatProperty price;
    private ModelCalculate modelCalculate;

    public Model() {
        groups = new ArrayList<>();
        elements = new ArrayList<>();
        price = new SimpleFloatProperty(0.f);
        modelCalculate = new ModelCalculate();
    }

    public Model(File fileGroups, File other){
        this();
        loadConfigFIle(fileGroups, other);
    }

    public void loadConfigFIle(File fileGroups, File other){
        try {
            Scanner s = new Scanner(new FileInputStream(other), "Cp1251");
            Float p = s.nextFloat();
            price.set(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Creator creator = new Creator();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileGroups), "Cp1251"));
            String strLine;
            while ((strLine = br.readLine()) != null){ // грузим блоки
                String [] lines = strLine.split("\\s*\\$\\s*");
                groups.add(lines[0]);
                Scanner s = new Scanner(new FileInputStream(new File("configs/" + lines[1])), "Cp1251");
                ArrayList<Device> currentDevices = new ArrayList<>();
                while (true) { // грузим элементы конкретного блока
                    String name = s.nextLine();
                    Integer type = s.nextInt();
                    Integer count = s.nextInt();
                    Integer time = s.nextInt();
                    ArrayList<Integer> powers = new ArrayList<>();
                    while(s.hasNextInt()){
                        powers.add(s.nextInt());
                    }
                    Device device = creator.getDevice(type);
                    device.initDevice(name, new SimpleIntegerProperty(count), new SimpleIntegerProperty(time), powers);
                    currentDevices.add(device);
                    if(s.hasNextLine()) {
                        s.nextLine();
                    }else{
                        break;
                    }
                }
                elements.add(currentDevices);
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }
    }

    public void calculate(){
        modelCalculate.getAllCalculates().clear();
        Float result = 0.f;
        for(ArrayList<Device> devices: elements){
            for(Device d: devices){
                Integer f = d.getCount().get() * d.getTime().get() * d.getPower();
                modelCalculate.getAllCalculates().add(f);
                result += f;
            }
        }
        modelCalculate.setConsumption(result);
        modelCalculate.setPayment(price.get() * result);
    }

    public void writeFile() throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("result.txt"));
        bf.write("ДАННЫЕ ЭНЕРГОПОТРЕБЛЕНИЯ(сперва все приборы, последние 3 цифры - цена за кВт, энергопотребление и сумма к оплате:");
        bf.newLine();
        for(Integer f: modelCalculate.getAllCalculates()){
            bf.write(f + " ");
        }
        /*bf.write(price.get() + " ");
        bf.write(modelCalculate.getConsumption() + " ");
        bf.write(modelCalculate.getPayment() + " ");*/
        bf.newLine();
        bf.write("Цена за кВТ= "+price.get());
        bf.newLine();
        bf.write("Общее потребление= "+modelCalculate.getConsumption());
        bf.newLine();
        bf.write("Итого к оплате= "+modelCalculate.getPayment());

        bf.close();
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public ArrayList<ArrayList<Device>> getElements() {
        return elements;
    }

    public SimpleFloatProperty getPrice() {
        return price;
    }

    public ModelCalculate getModelCalculate() {
        return modelCalculate;
    }
}
