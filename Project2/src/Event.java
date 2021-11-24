public abstract class Event {
    public abstract double getTime();
    public abstract void process();
    public abstract void startEvent();
    public abstract void endEvent();
    public abstract void addToQueue();
    public abstract int getEnding();
}
