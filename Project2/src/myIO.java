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

    // writes the given ArrayList of lines to the output file
    public static void writeOutput(String outputFileName, ArrayList<String> stringArray) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        for (String s : stringArray) {
            writer.write(s + "\n");
        }
        writer.close();
    }

    // reads the input file, parses it, and performs necessary operations on other classes
    public static void parseFile(String inputFileName) throws FileNotFoundException {
        ArrayList<String> lines = myIO.readInput(inputFileName);
        Iterator<String> iterator = lines.iterator();

        // players data
        int numberOfPlayers = Integer.parseInt(iterator.next());
        for (int i = 0; i < numberOfPlayers; i++) {
            String[] words = iterator.next().split(" ");
            Player.players.add(new Player(Integer.parseInt(words[0]), Integer.parseInt(words[1])));
        }

        // events data
        int numberOfArrivals = Integer.parseInt(iterator.next());
        for (int i = 0; i < numberOfArrivals; i++) {
            String[] words = iterator.next().split(" ");
            String type = words[0];
            int playerID = Integer.parseInt(words[1]);
            double time = Double.parseDouble(words[2]);
            double duration = Double.parseDouble(words[3]);
            if (type.equals("t")) {
                DiscreteEventSimulator.addEvent(new TrainingEvent(playerID, time, duration));
            }
            else if (type.equals("m")) {
                DiscreteEventSimulator.addEvent(new MassageEvent(playerID, time, duration));
            }
        }

        // physiotherapists data
        String[] physiotherapists = iterator.next().split(" ");
        int numberOfPhysiotherapists = Integer.parseInt(physiotherapists[0]);
        PhysiotherapyEvent.numberOfPhysiotherapists = numberOfPhysiotherapists;
        for (int i = 1; i < numberOfPhysiotherapists + 1; i++) {
            PhysiotherapyEvent.physiotherapists.add(new Physiotherapist(Double.parseDouble(physiotherapists[i])));
        }

        // training coaches and masseurs data
        String[] trainingCoachesAndMasseurs = iterator.next().split(" ");
        TrainingEvent.numberOfTrainingCoaches = Integer.parseInt(trainingCoachesAndMasseurs[0]);
        MassageEvent.numberOfMasseurs = Integer.parseInt(trainingCoachesAndMasseurs[1]);
    }
}
