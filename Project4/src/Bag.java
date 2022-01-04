import java.util.ArrayList;

public class Bag extends Node {

    public static final ArrayList<Bag> bags = new ArrayList<>();
    public static int totalGifts = 0;

    private final String properties;
    private final int gifts;

    public Bag(String properties, int gifts) {
        super(2);
        this.properties = properties;
        this.gifts = gifts;
        Bag.totalGifts += gifts;
    }

    public void add() {
        Bag.bags.add(this);
        Node.nodes.addFirst(this);
    }

    public boolean hasProperty(String property) {
        return this.properties.contains(property);
    }

    public int getGifts() {
        return this.gifts;
    }
}
