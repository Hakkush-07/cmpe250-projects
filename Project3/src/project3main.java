import java.io.IOException;

public class project3main {
    public static void main(String[] args) throws IOException {
        myIO.parseFile(args[0]);
        City.formAllNeighbors();
        String list1 = SPT.list1();
        MST.modifyNeighbors();
        String int1 = MST.int1();
        myIO.writeOutput(args[1], list1, int1);
    }
}
