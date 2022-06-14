package model.factory_method;

import model.device.Device;
import model.device.TextDevice;

public class CTextDevice extends CDevice {
    @Override
    public Device create() {
        return new TextDevice();
    }
}
