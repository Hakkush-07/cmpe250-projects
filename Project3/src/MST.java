import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

// Minimum Spanning Tree
public class MST {

    private static City start;
    // cities for this algorithm
    private final static HashSet<City> cities = new HashSet<>();
    // the ordered set containing the cities that we are not done yet
    private final static TreeSet<City> map = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(City o1, City o2) {
            if (o1.getValue() == o2.getValue()) {
                return o1.getID().compareTo(o2.getID());
            }
            else {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        }
    });
    // whether all cities can be visited or not
    private static boolean honeymoonSuccessful = true;

    // Prim Minimum Spanning Tree Algorithm
    private static void runAlgorithm() {
        // value of a city is the length of (or tax of) the edge just before the city in the minimum spanning tree
        // so one end of this edge is this city and the other end is another city that is closer to start point than this city

        // set the value of starting city as 0, others are Integer.MAX for now
        // set remove and add operations are needed because we need to keep the set sorted
        MST.map.remove(MST.start);
        MST.start.setValue(0);
        MST.map.add(MST.start);

        while (!MST.map.isEmpty()) {

            // take the city with minimum value
            City cityWithMinValue = MST.map.first();

            // if this city has the value of Integer.MAX_VALUE, that means we can not reach that city, so honeymoon fails
            if (cityWithMinValue.getValue() == Integer.MAX_VALUE) {
                MST.honeymoonSuccessful = false;
                break;
            }

            // update the values of its neighbors
            for (City neighbor : cityWithMinValue.getNeighbors().keySet()) {
                int d = cityWithMinValue.getNeighbors().get(neighbor);
                if (d < neighbor.getValue() && MST.map.contains(neighbor) && !cityWithMinValue.getID().equals(neighbor.getID())) {
                    MST.map.remove(neighbor);
                    neighbor.setValue(d);
                    MST.map.add(neighbor);
                }
            }

            // we are done with this city
            MST.map.remove(cityWithMinValue);
        }
    }

    private static int minTax() {
        // run the algorithm
        MST.runAlgorithm();

        // then sum of all city values gives the minimum spanning tree length
        // tax is two times this number
        int tax = 0;
        for (City city : MST.cities) {
            tax += 2 * city.getValue();
        }
        return tax;
    }

    public static String int1() {
        int m = MST.minTax();
        if (!SPT.isReachable()) return "-1";
        if (!SPT.isSuccessful()) return "-1";
        if (!MST.honeymoonSuccessful) return "-2";
        return Integer.toString(m);
    }

    public static void modifyNeighbors() {
        // after SPT which uses a directed graph, we need to convert the graph into an undirected graph for MST
        for (City city : MST.cities) {
            city.modifyNeighborsForMST();
        }
    }

    public static void addToMap(City city) {
        MST.map.add(city);
        MST.cities.add(city);
    }

    public static void setStart(String startCityID) {
        MST.start = City.getCityFromID(startCityID);
    }

    public static boolean containsCity(City city) {
        return MST.cities.contains(city);
    }
}
