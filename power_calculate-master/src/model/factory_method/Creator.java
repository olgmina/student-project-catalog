package model.factory_method;

import model.device.Device;

import java.util.HashMap;

public class Creator {

    private HashMap<Integer, CDevice> factorDevices = new HashMap<Integer, CDevice>(){{
      put(0, new CTextDevice());
      put(1, new CComboDevice());
    }};

    public Device getDevice(int i){
        try{
            return factorDevices.get(i).create();
        }catch (Exception ex){
            System.out.println(ex);
        }
        return null;
    }

}
