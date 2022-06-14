package alarmsystem.handlers;

public class ActionChain {

    Handler chain;
    public static int ONE = 1, TWO = 2, THREE = 3;

    public ActionChain(){
        buildChain();
    }

    private void buildChain(){
        chain = new OneHandler(new TwoHandler(new ThreeHandler(null)));
    }

    public void process(int handler){
        if(handler>0 && handler<4) chain.process(handler);
    }
}
