import java.io.IOException;

public class project4main {
    public static void main(String[] args) throws IOException {
        myIO.parseFile(args[0]);
        myIO.writeOutput(args[1], Node.runAlgorithm());
    }
}
