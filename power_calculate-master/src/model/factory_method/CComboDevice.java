package model.factory_method;

import model.device.ComboDevice;
import model.device.Device;

public class CComboDevice extends CDevice {
    @Override
    public Device create() {
        return new ComboDevice();
    }
}
