import java.io.IOException;

public class project2main {
    public static void main(String[] args) throws IOException {
        String inputFileName = args[0];
        String outputFileName = args[1];
        myIO.parseFile(inputFileName);
        DiscreteEventSimulator.simulate();
        myIO.writeOutput(outputFileName, DiscreteEventSimulator.statistics());
    }
}