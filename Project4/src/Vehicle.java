import java.util.ArrayList;

public class Vehicle extends Node {

    public static final ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static int numberOfVehicles = 0;

    private final char region;
    private final char type;
    private final int capacity;

    public Vehicle(char region, char type, int capacity) {
        super(1);
        this.region = region;
        this.type = type;
        this.capacity = capacity;
        Vehicle.numberOfVehicles++;
    }

    public void add() {
        Vehicle.vehicles.add(this);
        Node.nodes.add(this);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public char getRegion() {
        return this.region;
    }

    public char getType() {
        return this.type;
    }
}
