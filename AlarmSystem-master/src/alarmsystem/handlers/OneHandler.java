package alarmsystem.handlers;

import static alarmsystem.handlers.ActionChain.ONE;

public class OneHandler extends Handler{

    public OneHandler(Handler processor) {
        super(processor);
    }

    @Override
    public void process(Integer request){
        if(request!=ONE) super.process(request);
        else {
            startSensor();
        }
    }

    private void startSensor() {
        System.out.println("Включение устройства");
    }
}
