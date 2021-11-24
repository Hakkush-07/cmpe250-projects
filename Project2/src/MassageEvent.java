import java.util.PriorityQueue;

public class MassageEvent extends Event implements Comparable<MassageEvent> {

    // required for stats
    public static int numberOfMasseurs;
    private static int numberOfBusyMasseurs = 0;
    public static int numberOfCancelledAttempts = 0;
    public static int numberOfInvalidAttempts = 0;
    public static double totalWaitingTimeInQueue = 0;
    public static double totalMassageTime = 0;
    public static int maxQueueLength = 0;
    public static int number = 0; // total number of ended TrainingEvents, used when calculating average values

    // massage queue
    private static final PriorityQueue<MassageEvent> massageQueue = new PriorityQueue<>();

    private final int playerID;
    private final Player player;
    private final double duration;
    private double time; // initially it is request time but changes when it becomes an ending event and represents end time after that
    private final double requestTime;
    private double startTime;
    private boolean isEnding; // whether this is an ending event or not, ...
    // ... here I am using the same event for ending it too instead of creating a separate end event class

    public MassageEvent(int playerID, double time, double duration) {
        this.playerID = playerID;
        this.player = Player.players.get(this.playerID);
        this.time = time;
        this.requestTime = time;
        this.duration = duration;
        this.isEnding = false;
    }

    public void process() {
        if (this.isEnding) {
            this.endEvent();
        }
        else if (!this.player.canMassage()) {
            MassageEvent.numberOfInvalidAttempts++;
        }
        else if (this.player.isBusy()) {
            MassageEvent.numberOfCancelledAttempts++;
        }
        else if (MassageEvent.available()) {
            this.player.setBusy(true);
            this.player.massage();
            this.startEvent();
        }
        else {
            this.player.setBusy(true);
            this.player.massage();
            this.addToQueue();
        }
    }

    // first player skill, second time, third player id
    public int compareTo(MassageEvent o) {
        return 4 * Integer.compare(o.player.getSkill(), this.player.getSkill()) + 2 * Double.compare(this.time, o.time) + Integer.compare(this.playerID, o.playerID);
    }

    public static boolean available() {
        return MassageEvent.numberOfBusyMasseurs < MassageEvent.numberOfMasseurs;
    }

    // starts the event
    public void startEvent() {
        this.startTime = DiscreteEventSimulator.systemTime;
        this.time = this.startTime + this.duration;
        this.isEnding = true;
        MassageEvent.numberOfBusyMasseurs++;
        DiscreteEventSimulator.addEvent(this);
    }

    // ends the event
    public void endEvent() {
        this.player.setBusy(false);
        this.player.addMassageQueueTime(this.startTime - this.requestTime);
        MassageEvent.totalMassageTime += this.duration;
        MassageEvent.totalWaitingTimeInQueue += this.startTime - this.requestTime;
        MassageEvent.numberOfBusyMasseurs--;
        MassageEvent.checkQueue();
        MassageEvent.number++;
    }

    // places the event into the queue
    public void addToQueue() {
        MassageEvent.massageQueue.add(this);
        int queueSize = MassageEvent.massageQueue.size();
        if (queueSize > MassageEvent.maxQueueLength) {
            MassageEvent.maxQueueLength = queueSize;
        }
    }

    // checks the queue when an event ends
    public static void checkQueue() {
        if (MassageEvent.available() && !MassageEvent.massageQueue.isEmpty()) {
            MassageEvent.massageQueue.poll().startEvent();
        }
    }

    public double getTime() {
        return this.time;
    }

    public int getEnding() {
        return this.isEnding ? 0 : 1;
    }

    // required for stats
    public static double averageQueueTime() {
        return MassageEvent.totalWaitingTimeInQueue / MassageEvent.number;
    }

    // required for stats
    public static double averageMassageTime() {
        return MassageEvent.totalMassageTime / MassageEvent.number;
    }
}
