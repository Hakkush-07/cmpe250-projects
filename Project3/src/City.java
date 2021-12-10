import java.util.HashMap;
import java.util.HashSet;

public class City {

    // the set of all cities
    public static final HashSet<City> cities = new HashSet<>();
    // matches ids with cities so that a city can be found from its id
    private static final HashMap<String, City> catalog = new HashMap<>();

    // the line in the input file containing info about neighbors of this city, split
    private final String[] rawNeighbors;
    // matches neighbor cities with their distances from this city
    private final HashMap<City, Integer> neighbors;
    private final String id;

    private int distance;  // for SPT, the shortest distance from start point to this city
    private City previous; // for SPT, so that the shortest path can be printed
    private int value;     // for MST

    public City(String[] rawNeighbors) {
        this.rawNeighbors = rawNeighbors;
        this.neighbors = new HashMap<>();
        this.id = this.rawNeighbors[0];
        this.distance = Integer.MAX_VALUE; // initially distance is infinity
        this.previous = null; // initially previous city of the shortest path is not exist
        this.value = Integer.MAX_VALUE; // initially value is infinity
        City.catalog.put(id, this);
    }

    public void addCity() {
        // add this city to SPT or MST according to which side of the country it is in
        City.cities.add(this);
        char type = this.id.charAt(0);
        if (type == 'c') {
            SPT.addToMap(this);
        }
        else if (type == 'd') {
            MST.addToMap(this);
        }
    }

    private void formNeighbors() {
        // create neighbors using their ids
        for (int i = 0; i < this.rawNeighbors.length / 2; i++) {
            this.neighbors.put(City.catalog.get(this.rawNeighbors[2 * i + 1]), Integer.parseInt(this.rawNeighbors[2 * i + 2]));
        }
    }

    public static void formAllNeighbors() {
        for (City city : City.cities) {
            city.formNeighbors();
        }
    }

    public void modifyNeighborsForMST() {
        // convert this city to an undirected graph city
        HashSet<City> toBeRemoved = new HashSet<>();
        for (City city : this.neighbors.keySet()) {
            if (MST.containsCity(city)) {
                // if there exist roads from this city to neighbor and from neighbor to this city,
                // update both of them to smaller one
                int a1 = this.neighbors.get(city);
                int a2 = city.neighbors.getOrDefault(this, Integer.MAX_VALUE);
                int smaller = Math.min(a1, a2);
                city.neighbors.put(this, smaller);
                this.neighbors.put(city, smaller);
            }
            else {
                // safely remove the neighbor if it is in the other side of the country because we are done with that part
                toBeRemoved.add(city);
            }
        }
        this.neighbors.keySet().removeAll(toBeRemoved);
    }

    public static City getCityFromID(String id) {
        return City.catalog.get(id);
    }

    public HashMap<City, Integer> getNeighbors() {
        return this.neighbors;
    }

    public String getID() {
        return this.id;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public City getPrevious() {
        return this.previous;
    }

    public void setPrevious(City previous) {
        this.previous = previous;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
