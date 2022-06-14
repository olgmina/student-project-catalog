package alarmsystem.handlers;

import static alarmsystem.handlers.ActionChain.TWO;

public class TwoHandler extends Handler{

    private boolean called;

    public TwoHandler(Handler processor) {
        super(processor);
        called = false;
    }

    @Override
    public void process(Integer request) {
        if(request!=TWO) super.process(request);
        else {
            callOperator();
        }
    }

    private void callOperator(){
        if(!called){
            System.out.println("Вызов оператора");
            called = true;
        }
    }
}
