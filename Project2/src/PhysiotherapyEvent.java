import java.util.ArrayList;
import java.util.PriorityQueue;

public class PhysiotherapyEvent extends Event implements Comparable<PhysiotherapyEvent> {

    // required for stats
    public static int numberOfPhysiotherapists;
    private static int numberOfBusyPhysiotherapists;
    public static ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
    public static int numberOfCancelledAttempts = 0;
    public static double totalWaitingTimeInQueue = 0;
    public static double totalPhysiotherapyTime = 0;
    public static int maxQueueLength = 0;
    public static int number = 0; // total number of ended TrainingEvents, used when calculating average values

    // physiotherapy queue
    private static final PriorityQueue<PhysiotherapyEvent> physiotherapyQueue = new PriorityQueue<>();

    private final int playerID;
    private final Player player;
    private double time; // initially it is request time but changes when it becomes an ending event and represents end time after that
    private final double requestTime;
    private double startTime;

    private final double trainingDuration;
    private double duration;

    private boolean isEnding; // whether this is an ending event or not, ...
    // ... here I am using the same event for ending it too instead of creating a separate end event class

    private Physiotherapist physiotherapist;

    public PhysiotherapyEvent(int playerID, double time, double trainingDuration) {
        this.playerID = playerID;
        this.player = Player.players.get(this.playerID);
        this.time = time;
        this.requestTime = time;
        this.isEnding = false;
        this.trainingDuration = trainingDuration;
    }

    public void process() {
        if (this.isEnding) {
            this.endEvent();
        }
        else if (this.player.isBusy()) {
            PhysiotherapyEvent.numberOfCancelledAttempts++;
        }
        else if (PhysiotherapyEvent.available()) {
            this.player.setBusy(true);
            this.startEvent();
        }
        else {
            this.player.setBusy(true);
            this.addToQueue();
        }
    }

    public int compareTo(PhysiotherapyEvent o) {
        return 4 * Double.compare(o.trainingDuration, this.trainingDuration) + 2 * Double.compare(this.time, o.time) + Integer.compare(this.playerID, o.playerID);
    }

    public static boolean available() {
        return PhysiotherapyEvent.numberOfBusyPhysiotherapists < PhysiotherapyEvent.numberOfPhysiotherapists;
    }

    // starts the event
    public void startEvent() {
        this.startTime = DiscreteEventSimulator.systemTime;
        this.physiotherapist = PhysiotherapyEvent.firstAvailablePhysiotherapist();
        this.physiotherapist.setAvailable(false);
        this.duration = this.physiotherapist.getServiceTime();
        this.time = this.startTime + this.duration;
        this.isEnding = true;
        PhysiotherapyEvent.numberOfBusyPhysiotherapists++;
        DiscreteEventSimulator.addEvent(this);
    }

    // ends the event
    public void endEvent() {
        this.player.setBusy(false);
        this.physiotherapist.setAvailable(true);
        double queueTime = this.startTime - this.requestTime;
        this.player.addPhysiotherapyQueueTime(queueTime);
        PhysiotherapyEvent.totalPhysiotherapyTime += this.duration;
        PhysiotherapyEvent.totalWaitingTimeInQueue += queueTime;
        PhysiotherapyEvent.numberOfBusyPhysiotherapists--;
        PhysiotherapyEvent.checkQueue();
        PhysiotherapyEvent.number++;
    }

    // places the event into the queue
    public void addToQueue() {
        PhysiotherapyEvent.physiotherapyQueue.add(this);
        int queueSize = PhysiotherapyEvent.physiotherapyQueue.size();
        if (queueSize > PhysiotherapyEvent.maxQueueLength) {
            PhysiotherapyEvent.maxQueueLength = queueSize;
        }
    }

    // checks the queue when an event ends
    public static void checkQueue() {
        if (PhysiotherapyEvent.available() && !PhysiotherapyEvent.physiotherapyQueue.isEmpty()) {
            PhysiotherapyEvent.physiotherapyQueue.poll().startEvent();
        }
    }

    // finds the first available physiotherapist for the next event
    public static Physiotherapist firstAvailablePhysiotherapist() {
        for (Physiotherapist physiotherapist : PhysiotherapyEvent.physiotherapists) {
            if (physiotherapist.isAvailable()) {
                return physiotherapist;
            }
        }
        // this function is supposed to be called when physiotherapy is available, so this line should never be accessed
        return new Physiotherapist(0);
    }

    public double getTime() {
        return this.time;
    }

    public int getEnding() {
        return this.isEnding ? 0 : 1;
    }

    // required for stats
    public static double averageQueueTime() {
        return PhysiotherapyEvent.totalWaitingTimeInQueue / PhysiotherapyEvent.number;
    }

    // required for stats
    public static double averagePhysiotherapyTime() {
        return PhysiotherapyEvent.totalPhysiotherapyTime / PhysiotherapyEvent.number;
    }
}
