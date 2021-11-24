import java.util.ArrayList;
import java.util.PriorityQueue;

public class DiscreteEventSimulator {

    // main event queue, sorted wrt the EventComparator
    public static final PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
    // the system time, changes frequently as events are being processed
    public static double systemTime;

    // converts the given double number to a string with 3 digits after decimal point
    // required to conform to the output format
    public static String convert(double number) {
        return String.format("%.3f", number).replace(',', '.');
    }

    // returns the ArrayList of output lines
    public static ArrayList<String> statistics() {
        ArrayList<String> stats = new ArrayList<>();
        stats.add(Integer.toString(TrainingEvent.maxQueueLength));
        stats.add(Integer.toString(PhysiotherapyEvent.maxQueueLength));
        stats.add(Integer.toString(MassageEvent.maxQueueLength));
        stats.add(DiscreteEventSimulator.convert(TrainingEvent.averageQueueTime()));
        stats.add(DiscreteEventSimulator.convert(PhysiotherapyEvent.averageQueueTime()));
        stats.add(DiscreteEventSimulator.convert(MassageEvent.averageQueueTime()));
        stats.add(DiscreteEventSimulator.convert(TrainingEvent.averageTrainingTime()));
        stats.add(DiscreteEventSimulator.convert(PhysiotherapyEvent.averagePhysiotherapyTime()));
        stats.add(DiscreteEventSimulator.convert(MassageEvent.averageMassageTime()));
        stats.add(DiscreteEventSimulator.convert(TrainingEvent.averageQueueTime() + PhysiotherapyEvent.averageQueueTime() + TrainingEvent.averageTrainingTime() + PhysiotherapyEvent.averagePhysiotherapyTime()));
        stats.add(Player.getMaxPhysiotherapyQueueTime());
        stats.add(Player.getMaxMassageQueueTime());
        stats.add(Integer.toString(MassageEvent.numberOfInvalidAttempts));
        stats.add(Integer.toString(TrainingEvent.numberOfCancelledAttempts + MassageEvent.numberOfCancelledAttempts + PhysiotherapyEvent.numberOfCancelledAttempts));
        stats.add(DiscreteEventSimulator.convert(DiscreteEventSimulator.systemTime));
        return stats;
    }

    // simulates the events
    public static void simulate() {
        while (!DiscreteEventSimulator.events.isEmpty()) {
            // take the event
            Event event = DiscreteEventSimulator.events.poll();
            // update the system time
            DiscreteEventSimulator.systemTime = event.getTime();
            // process it
            event.process();
        }
    }

    // adds the event to the main event queue
    public static void addEvent(Event event) {
        DiscreteEventSimulator.events.add(event);
    }
}
