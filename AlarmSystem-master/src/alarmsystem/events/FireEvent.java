package alarmsystem.events;

import java.util.Timer;
import java.util.TimerTask;

public class FireEvent extends Event{
    private int position;
    private int burnTime = 10;
    private Timer timer;
    private final TimerTask burning = new TimerTask(){
        @Override
        public void run() {
            burnTime--;
        }
    };

    public FireEvent(long et, int position, int burnTime){
        super(et);
        this.position = position;
        this.burnTime = burnTime;
    }

    @Override
    public void stop(){
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void start(){
        timer = new Timer();
        timer.schedule(burning,0,1);
    }

    public int getPosition() {
        return position;
    }
}
