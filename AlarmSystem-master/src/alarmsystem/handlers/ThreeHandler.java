package alarmsystem.handlers;

import static alarmsystem.handlers.ActionChain.THREE;

public class ThreeHandler extends Handler{

    public ThreeHandler(Handler processor) {
        super(processor);
    }

    @Override
    public void process(Integer request){
        if(request!=THREE) super.process(request);
        else {
            callFireDepartment();
        }
    }

    private void callFireDepartment(){
        System.out.println("Вызов пожарной службы");
    }
}
