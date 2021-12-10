import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

// Shortest Path Tree
public class SPT {

    private static int threshold;
    private static City start;
    private static City end;
    // the ordered set containing the cities that we are not done yet
    private final static TreeSet<City> map = new TreeSet<>(new Comparator<>() {
        @Override
        public int compare(City o1, City o2) {
            if (o1.getDistance() == o2.getDistance()) {
                return o1.getID().compareTo(o2.getID());
            }
            else {
                return Integer.compare(o1.getDistance(), o2.getDistance());
            }
        }
    });
    // whether there exist a path from start to end or not
    private static boolean reachable = true;

    // Dijkstra Shortest Path Tree Algorithm
    private static void runAlgorithm() {
        // distance of a city is the length of the minimum path from start city to this city

        // set the distance of starting city as 0, others are Integer.MAX for now
        // set remove and add operations are needed because we need to keep the set sorted
        SPT.map.remove(SPT.start);
        SPT.start.setDistance(0);
        SPT.map.add(SPT.start);

        while (!SPT.map.isEmpty()) {

            // take the city with minimum distance
            City cityWithMinDistance = SPT.map.first();

            // if this city has the distance of Integer.MAX_VALUE, that means we can not reach that city and the cities left in the set,
            // so task fails if end city has not been reached yet (still in SPT.map)
            if (cityWithMinDistance.getDistance() == Integer.MAX_VALUE) {
                if (SPT.map.contains(SPT.end)) {
                    SPT.reachable = false;
                }
                // distances will not change after this point, so we can terminate the algorithm
                break;
            }

            // update the distances of its neighbors
            for (City neighbor : cityWithMinDistance.getNeighbors().keySet()) {
                int d = cityWithMinDistance.getDistance() + cityWithMinDistance.getNeighbors().get(neighbor);
                if (d < neighbor.getDistance() && SPT.map.contains(neighbor)) {
                    SPT.map.remove(neighbor);
                    neighbor.setDistance(d);
                    neighbor.setPrevious(cityWithMinDistance);
                    SPT.map.add(neighbor);
                }
            }

            // we are done with this city
            SPT.map.remove(cityWithMinDistance);
        }
    }

    public static boolean isSuccessful() {
        // whether the boy arrived in time or not
        return end.getDistance() <= SPT.threshold;
    }

    public static boolean isReachable() {
        // whether there exists a path between start and end
        return SPT.reachable;
    }

    public static String path() {
        // previous fields of all cities except the starting point show the previous city to reach it from start city

        // ids of cities on shortest path, in reverse
        ArrayList<String> list = new ArrayList<>();
        list.add(SPT.end.getID());
        City city = SPT.end;
        while (city.getPrevious() != null) {
            city = city.getPrevious();
            list.add(city.getID());
        }

        // concatenate this list to get list1
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            stringBuilder.append(list.get(i));
            if (i != 0) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static String list1() {
        // run the algorithm
        SPT.runAlgorithm();

        if (SPT.reachable) {
            return SPT.path();
        }
        else {
            return "-1";
        }
    }

    public static void addToMap(City city) {
        SPT.map.add(city);
    }

    public static void setStartEnd(String startCityID, String endCityID) {
        SPT.start = City.getCityFromID(startCityID);
        SPT.end = City.getCityFromID(endCityID);
    }

    public static void setThreshold(int threshold) {
        SPT.threshold = threshold;
    }
}
