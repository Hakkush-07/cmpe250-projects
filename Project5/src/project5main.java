import java.io.IOException;

public class project5main {
    public static void main(String[] args) throws IOException {
        myIO.parseFile(args[0]);
        myIO.writeOutput(args[1], Offer.runAlgorithm());
    }
}
