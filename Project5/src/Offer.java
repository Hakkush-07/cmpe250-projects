import java.util.ArrayList;
import java.util.Collections;

public class Offer implements Comparable<Offer> {

    private static final ArrayList<Offer> offers = new ArrayList<>();
    private static int numberOfOrders = 0;

    private final int startTime;
    private final int endTime;
    private final int profit;

    public Offer(int arrivalTime, String type, int profit, int timeA, int timeB) {
        this.startTime = arrivalTime;
        this.profit = profit;
        this.endTime = this.startTime + (type.equals("s") ? timeA : timeB);
        Offer.numberOfOrders++;
    }

    public void add() {
        Offer.offers.add(this);
    }

    public static int runAlgorithm() {
        Offer.sortOrders();
        int[] maxProfit = new int[Offer.numberOfOrders + 1];
        for (int i = 1; i <= Offer.numberOfOrders; i++) {
            int j = i - 1;
            while (j >= 1 && Offer.offers.get(j - 1).endTime > Offer.offers.get(i - 1).startTime) {
                j--;
            }
            maxProfit[i] = Math.max(maxProfit[i - 1], maxProfit[j] + Offer.offers.get(i - 1).profit);
        }
        return maxProfit[Offer.numberOfOrders];
    }

    public static void sortOrders() {
        Collections.sort(Offer.offers);
    }

    @Override
    public int compareTo(Offer o) {
        return 2 * Integer.compare(this.endTime, o.endTime) + Integer.compare(o.profit, this.profit);
    }
}
