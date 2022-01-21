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
    public static void writeOutput(String outputFileName, int maxProfit) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        writer.write(String.valueOf(maxProfit));
        writer.close();
    }

    // reads the input file, parses it, and performs necessary operations on other classes
    public static void parseFile(String inputFileName) throws FileNotFoundException {
        ArrayList<String> lines = myIO.readInput(inputFileName);
        Iterator<String> iterator = lines.iterator();

        String[] types = iterator.next().split(" ");
        String[] timesA = iterator.next().split(" ");
        String[] timesB = iterator.next().split(" ");
        String[] profits = iterator.next().split(" ");
        String[] arrivalTimes = iterator.next().split(" ");

        for (int i = 0; i < types.length; i++) {
            Offer order = new Offer(Integer.parseInt(arrivalTimes[i]), types[i], Integer.parseInt(profits[i]), Integer.parseInt(timesA[i]), Integer.parseInt(timesB[i]));
            order.add();
        }
    }
}

