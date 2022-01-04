import java.util.*;

public class Node {

    public static final LinkedList<Node> nodes = new LinkedList<>();
    private static final Node start = new Node();
    private static final Node end = new Node();
    private static int numberOfNodes;
    private static final HashSet<Node> relabeledVehicles = new HashSet<>();

    private int height;
    private int flow;
    private final HashMap<Node, Integer> neighbors = new HashMap<>();
    private final ArrayList<Node> neighborList = new ArrayList<>();
    private int pointer;

    public Node() {
        this.height = 0;
        this.flow = 0;
        this.pointer = 0;
        Node.numberOfNodes++;
    }

    public Node(int height) {
        this.height = height;
        this.flow = 0;
        this.pointer = 0;
        Node.numberOfNodes++;
    }

    // creates the graph
    private static void createEdges() {
        // edges between bags and the start
        for (Bag bag : Bag.bags) {
            Node.start.neighbors.put(bag, bag.getGifts());
            bag.getNeighbors().put(Node.start, 0);
            bag.getNeighborList().add(Node.start);
        }

        // edges between vehicles and the end
        for (Vehicle vehicle : Vehicle.vehicles) {
            Node.end.neighbors.put(vehicle, 0);
            vehicle.getNeighbors().put(Node.end, vehicle.getCapacity());
            vehicle.getNeighborList().add(Node.end);
        }

        // edges between bags and vehicles
        for (Vehicle vehicle : Vehicle.vehicles) {
            for (Bag bag : Bag.bags) {
                if ((!bag.hasProperty("b") || vehicle.getRegion() == 'g') && (!bag.hasProperty("c") || vehicle.getRegion() == 'r') && (!bag.hasProperty("d") || vehicle.getType() == 't') && (!bag.hasProperty("e") || vehicle.getType() == 'r')) {
                    bag.getNeighbors().put(vehicle, bag.hasProperty("a") ? 1 : bag.getGifts());
                    bag.getNeighborList().add(vehicle);
                    vehicle.getNeighbors().put(bag, 0);
                    vehicle.getNeighborList().add(bag);
                }
            }
        }
    }

    public static int runAlgorithm() {
        // relabel to front max flow algorithm

        Node.createEdges();

        // pre flow
        Node.start.height = Node.numberOfNodes;
        for (Map.Entry<Node, Integer> entry : Node.start.neighbors.entrySet()) {
            Node.start.push(entry.getKey(), entry.getValue());
        }

        // pre check
        if (Node.nodes.isEmpty()) {
            return 0;
        }

        // iterating through the list of nodes
        Iterator<Node> iterator = Node.nodes.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.flow == 0) {
                continue;
            }
            if (Node.relabeledVehicles.size() == Vehicle.numberOfVehicles) {
                break;
            }
            int oldHeight = node.height;
            node.discharge();
            if (node.height > oldHeight) {
                iterator.remove();
                Node.nodes.add(0, node);
                iterator = Node.nodes.iterator();
                iterator.next();
            }
        }
        return Bag.totalGifts - Node.end.flow;
    }

    public void discharge() {
        while (this.flow != 0) {
            if (this.pointer >= this.neighborList.size()) {
                this.relabel();
                if (this instanceof Vehicle) {
                    Node.relabeledVehicles.add(this);
                }
                this.pointer = 0;
            }
            else {
                Node neighbor = this.neighborList.get(this.pointer);
                if (this.neighbors.get(neighbor) != 0 && this.height > neighbor.height) {
                    this.push(neighbor, Math.min(this.flow, this.neighbors.get(neighbor)));
                }
                else {
                    this.pointer++;
                }
            }
        }
    }

    public void push(Node node, int flow) {
        this.neighbors.put(node, this.neighbors.get(node) - flow);
        node.neighbors.put(this, node.neighbors.get(this) + flow);
        this.flow -= flow;
        node.flow += flow;
    }

    private void relabel() {
        int minHeight = Integer.MAX_VALUE;
        for (Node neighbor : this.neighborList) {
            if (this.neighbors.get(neighbor) != 0 && neighbor.height < minHeight) {
                minHeight = neighbor.height;
            }
        }
        this.height = minHeight + 1;
    }

    public HashMap<Node, Integer> getNeighbors() {
        return this.neighbors;
    }

    public ArrayList<Node> getNeighborList() {
        return this.neighborList;
    }
}
