import java.util.ArrayList;

public class Player {

    // list of all players
    public static ArrayList<Player> players = new ArrayList<>();

    private final int id;
    private final int skill;

    private double totalTimeInPhysiotherapyQueue;
    private double totalTimeInMassageQueue;

    private int numberOfMassages;
    private boolean isBusy;

    public Player(int id, int skill) {
        this.id = id;
        this.skill = skill;
        this.numberOfMassages = 0;
        this.isBusy = false;
    }

    public void massage() {
        this.numberOfMassages++;
    }

    public boolean canMassage() {
        return this.numberOfMassages < 3;
    }

    public int getSkill() {
        return this.skill;
    }

    public void setBusy(boolean busy) {
        this.isBusy = busy;
    }

    public boolean isBusy() {
        return this.isBusy;
    }

    public void addPhysiotherapyQueueTime(double queueTime) {
        this.totalTimeInPhysiotherapyQueue += queueTime;
    }

    public void addMassageQueueTime(double queueTime) {
        this.totalTimeInMassageQueue += queueTime;
    }

    // required for stats
    public static String getMaxPhysiotherapyQueueTime() {
        if (Player.players.isEmpty()) {
            return "0 0";
        }
        else {
            Player maxPlayer = null;
            for (Player player : Player.players) {
                if (maxPlayer == null) {
                    maxPlayer = player;
                }
                else if (player.totalTimeInPhysiotherapyQueue > maxPlayer.totalTimeInPhysiotherapyQueue) {
                    maxPlayer = player;
                }
            }
            if (maxPlayer == null) {
                return "0 0";
            }
            else {
                return "" + maxPlayer.id + " " + DiscreteEventSimulator.convert(maxPlayer.totalTimeInPhysiotherapyQueue);
            }
        }
    }

    // required for stats
    public static String getMaxMassageQueueTime() {
        if (Player.players.isEmpty()) {
            return "-1 -1";
        }
        else {
            Player maxPlayer = null;
            for (Player player : Player.players) {
                if (maxPlayer == null) {
                    if (player.numberOfMassages == 3) {
                        maxPlayer = player;
                    }
                }
                else if (player.numberOfMassages == 3 && player.totalTimeInMassageQueue < maxPlayer.totalTimeInMassageQueue) {
                    maxPlayer = player;
                }

            }
            if (maxPlayer == null) {
                return "-1 -1";
            }
            else {
                return "" + maxPlayer.id + " " + DiscreteEventSimulator.convert(maxPlayer.totalTimeInMassageQueue);
            }
        }
    }
}
