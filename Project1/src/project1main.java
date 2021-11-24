import java.io.IOException;

public class project1main {
    public static void main(String[] args) throws IOException {
        String inputFileName = args[0];
        String outputFileName = args[1];
        DormOffice dormOffice = new DormOffice();
        myIO.parseFile(dormOffice, inputFileName);
        dormOffice.applicationsClosed();
        for (int semester = 1; semester <= 8; semester++) {
            dormOffice.simulateSemester();
        }
        myIO.writeOutput(outputFileName, dormOffice.studentsThatCouldNotStay());
    }
}
