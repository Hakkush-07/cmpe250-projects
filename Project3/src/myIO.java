import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class myIO {
    // reads the input file and returns each line in an ArrayList
    private static ArrayList<String> readInput(String inputFileName) throws FileNotFoundException {
        File inputFile = new File(inputFileName);
        ArrayList<String> inputLines = new ArrayList<>();
        Scanner reader = new Scanner(inputFile);
        while (reader.hasNextLine()) {
            inputLines.add(reader.nextLine());
        }
        return inputLines;
    }

    // writes the output
    public static void writeOutput(String outputFileName, String path, String tax) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        writer.write(path + "\n" + tax);
        writer.close();
    }

    // reads the input file, parses it, and performs necessary operations on other classes
    public static void parseFile(String inputFileName) throws FileNotFoundException {
        ArrayList<String> lines = myIO.readInput(inputFileName);
        Iterator<String> iterator = lines.iterator();

        // threshold set by the father
        SPT.setThreshold(Integer.parseInt(iterator.next()));

        int numberOfCities = Integer.parseInt(iterator.next());
        String[] cityIDs = iterator.next().split(" ");
        String boyCityID = cityIDs[0];
        String girlCityID = cityIDs[1];

        // cities
        for (int i = 0; i < numberOfCities; i++) {
            City city = new City(iterator.next().split(" "));
            // add the city to correct algorithm
            city.addCity();
        }

        // set start point and end point for SPT
        SPT.setStartEnd(boyCityID, girlCityID);

        // girl city should also be added to the list of cities in the MST algorithm
        MST.addToMap(City.getCityFromID(girlCityID));
        // set start point for MST
        MST.setStart(girlCityID);
    }
}
