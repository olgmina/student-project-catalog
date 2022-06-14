package alarmsystem.events;

public abstract class Event {
    private long eventTime;

    public Event(long et){
        eventTime = et;
    }

    public abstract void stop();
    public abstract void start();
}
