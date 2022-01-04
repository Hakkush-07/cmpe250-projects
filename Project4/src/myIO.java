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
    public static void writeOutput(String outputFileName, int gifts) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        writer.write(String.valueOf(gifts));
        writer.close();
    }

    // reads the input file, parses it, and performs necessary operations on other classes
    public static void parseFile(String inputFileName) throws FileNotFoundException {
        ArrayList<String> lines = myIO.readInput(inputFileName);
        Iterator<String> iterator = lines.iterator();

        // green trains
        int numberOfGreenTrains = Integer.parseInt(iterator.next());
        String s1 = iterator.next();
        if (numberOfGreenTrains > 0) {
            String[] capacityOfGreenTrains = s1.split(" ");
            for (int i = 0; i < numberOfGreenTrains; i++) {
                Vehicle vehicle = new Vehicle('g', 't', Integer.parseInt(capacityOfGreenTrains[i]));
                vehicle.add();
            }
        }

        // red trains
        int numberOfRedTrains = Integer.parseInt(iterator.next());
        String s2 = iterator.next();
        if (numberOfRedTrains > 0) {
            String[] capacityOfRedTrains = s2.split(" ");
            for (int i = 0; i < numberOfRedTrains; i++) {
                Vehicle vehicle = new Vehicle('r', 't', Integer.parseInt(capacityOfRedTrains[i]));
                vehicle.add();
            }
        }

        // green reindeer
        int numberOfGreenReindeer = Integer.parseInt(iterator.next());
        String s3 = iterator.next();
        if (numberOfGreenReindeer > 0) {
            String[] capacityOfGreenReindeer = s3.split(" ");
            for (int i = 0; i < numberOfGreenReindeer; i++) {
                Vehicle vehicle = new Vehicle('g', 'r', Integer.parseInt(capacityOfGreenReindeer[i]));
                vehicle.add();
            }
        }

        // red reindeer
        int numberOfRedReindeer = Integer.parseInt(iterator.next());
        String s4 = iterator.next();
        if (numberOfRedReindeer > 0) {
            String[] capacityOfRedReindeer = s4.split(" ");
            for (int i = 0; i < numberOfRedReindeer; i++) {
                Vehicle vehicle = new Vehicle('r', 'r', Integer.parseInt(capacityOfRedReindeer[i]));
                vehicle.add();
            }
        }

        // bags
        int numberOfBags = Integer.parseInt(iterator.next());
        if (numberOfBags > 0) {
            String[] typesAndNumbers = iterator.next().split(" ");
            for (int i = 0; i < numberOfBags; i++) {
                Bag bag = new Bag(typesAndNumbers[2 * i], Integer.parseInt(typesAndNumbers[2 * i + 1]));
                bag.add();
            }
        }
    }
}

