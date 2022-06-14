package alarmsystem.handlers;

abstract public class Handler {
    private final Handler processor;

    public Handler(Handler processor){
        this.processor = processor;
    }

    public void process(Integer request){
        if(processor!=null){
            processor.process(request);
        }
    }
}
