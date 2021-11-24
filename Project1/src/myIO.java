import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class myIO {

    /**
     * reads the input file
     * @param inputFileName name of the input file
     * @return lines in the file
     * @throws FileNotFoundException when the file is not found
     */
    public static ArrayList<String> readInput(String inputFileName) throws FileNotFoundException {
        File inputFile = new File(inputFileName);
        ArrayList<String> inputLines = new ArrayList<>();
        Scanner reader = new Scanner(inputFile);
        while (reader.hasNextLine()) {
            inputLines.add(reader.nextLine());
        }
        return inputLines;
    }

    /**
     * writes to the output file
     * @param outputFileName output file name
     * @param stringArray strings that need to be written
     * @throws IOException when it can not write to the file
     */
    public static void writeOutput(String outputFileName, ArrayList<String> stringArray) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        for (String s : stringArray) {
            writer.write(s + "\n");
        }
        writer.close();
    }

    /**
     * parses the input file and creates houses and students
     * @param dormOffice dorm office that handles students and houses
     * @param inputFileName input file name
     * @throws FileNotFoundException when the file is not found
     */
    public static void parseFile(DormOffice dormOffice, String inputFileName) throws FileNotFoundException {
        for (String line : myIO.readInput(inputFileName)) {
            String[] words = line.split(" ");
            if (words[0].equals("h")) {
                dormOffice.add(new House(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Double.parseDouble(words[3])));
            }
            else if (words[0].equals("s")) {
                dormOffice.add(new Student(Integer.parseInt(words[1]), words[2], Integer.parseInt(words[3]), Double.parseDouble(words[4])));
            }
            else {
                System.out.println("Wrong Line");
            }
        }
    }
}
