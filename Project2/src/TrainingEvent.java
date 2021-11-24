import java.util.PriorityQueue;

public class TrainingEvent extends Event implements Comparable<TrainingEvent> {

    // required for stats
    public static int numberOfTrainingCoaches;
    private static int numberOfBusyCoaches = 0;
    public static int numberOfCancelledAttempts = 0;
    public static double totalWaitingTimeInQueue = 0;
    public static double totalTrainingTime = 0;
    public static int maxQueueLength = 0;
    public static int number = 0; // total number of ended TrainingEvents, used when calculating average values

    // training queue
    private static final PriorityQueue<TrainingEvent> trainingQueue = new PriorityQueue<>();

    private final int playerID;
    private final Player player;
    private final double duration;
    private double time; // initially it is request time but changes when it becomes an ending event and represents end time after that
    private final double requestTime;
    private double startTime;
    private boolean isEnding; // whether this is an ending event or not, ...
    // ... here I am using the same event for ending it too instead of creating a separate end event class

    public TrainingEvent(int playerID, double time, double duration) {
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
        else if (this.player.isBusy()) {
            TrainingEvent.numberOfCancelledAttempts++;
        }
        else if (TrainingEvent.available()) {
            this.player.setBusy(true);
            this.startEvent();
        }
        else {
            this.player.setBusy(true);
            this.addToQueue();
        }
    }

    // first coming time (who comes first, goes first), second player id (low player id goes first)
    public int compareTo(TrainingEvent o) {
        return 2 * Double.compare(this.time, o.time) + Integer.compare(this.playerID, o.playerID);
    }

    public static boolean available() {
        return TrainingEvent.numberOfBusyCoaches < TrainingEvent.numberOfTrainingCoaches;
    }

    // starts the event
    public void startEvent() {
        this.startTime = DiscreteEventSimulator.systemTime;
        this.time = this.startTime + this.duration;
        this.isEnding = true;
        TrainingEvent.numberOfBusyCoaches++;
        DiscreteEventSimulator.addEvent(this);
    }

    // ends the event
    public void endEvent() {
        this.player.setBusy(false);
        TrainingEvent.totalTrainingTime += this.duration;
        TrainingEvent.totalWaitingTimeInQueue += this.startTime - this.requestTime;
        TrainingEvent.numberOfBusyCoaches--;
        TrainingEvent.checkQueue();
        TrainingEvent.number++;
        DiscreteEventSimulator.addEvent(new PhysiotherapyEvent(this.playerID, DiscreteEventSimulator.systemTime, this.duration));
    }

    // places the event into the queue
    public void addToQueue() {
        TrainingEvent.trainingQueue.add(this);
        int queueSize = TrainingEvent.trainingQueue.size();
        if (queueSize > TrainingEvent.maxQueueLength) {
            TrainingEvent.maxQueueLength = queueSize;
        }
    }

    // checks the queue when an event ends
    public static void checkQueue() {
        if (TrainingEvent.available() && !TrainingEvent.trainingQueue.isEmpty()) {
            TrainingEvent.trainingQueue.poll().startEvent();
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
        return TrainingEvent.totalWaitingTimeInQueue / TrainingEvent.number;
    }

    // required for stats
    public static double averageTrainingTime() {
        return TrainingEvent.totalTrainingTime / TrainingEvent.number;
    }
}
