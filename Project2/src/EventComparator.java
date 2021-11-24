import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    // first event time (the smaller the event time, the higher the priority),
    // second whether the event is an ending event or not (ending events have the priority)
    public int compare(Event e1, Event e2) {
        return 2 * Double.compare(e1.getTime(), e2.getTime()) + Integer.compare(e1.getEnding(), e2.getEnding());
    }
}
