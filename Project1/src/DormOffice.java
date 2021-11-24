import java.util.ArrayList;
import java.util.Collections;

/**
 * DormOffice manages all operations on student house placements
 * it uses two ArrayList for storing students and houses
 */
public class DormOffice {

    /**
     * list of all houses offered by the university
     */
    private final ArrayList<House> houses;

    /**
     * list of all students studying at the university
     */
    private final ArrayList<Student> students;

    public DormOffice() {
        this.houses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    /**
     * adds the house to the houses list, used when parsing the input file
     * @param house the house
     */
    public void add(House house) {
        this.houses.add(house);
    }

    /**
     * adds the student to the students list, used when parsing the input file
     * @param student the student
     */
    public void add(Student student) {
        this.students.add(student);
    }

    /**
     * sorts the houses list and the students list according to their ids,
     * used when the reading of the input file is completed
     */
    public void applicationsClosed() {
        Collections.sort(this.houses);
        Collections.sort(this.students);
    }

    /**
     * simulates one semester of placing students to houses
     */
    public void simulateSemester() {
        // list of student that are placed to a house
        ArrayList<Student> placed = new ArrayList<>();

        // for each student who has not placed to a house yet,
        // search for available houses for that student
        for (Student student : this.students) {
            for (House house : this.houses) {
                if (house.available() && !student.graduated() && student.likes(house)) {
                    house.place(student);
                    placed.add(student);
                    break;
                }
            }
        }

        // remove students that are already placed to a house
        this.students.removeAll(placed);

        // all students complete one semester
        for (Student student : this.students) {
            student.studySemester();
        }

        // all houses complete one semester
        for (House house : this.houses) {
            house.passSemester();
        }
    }

    /**
     * the list of names of students that could not stay at any house during their education period
     * this method should be called after semesters are simulated
     * and students that are placed to a house are removed
     * @return the list of names to be written to output file
     */
    public ArrayList<String> studentsThatCouldNotStay() {
        ArrayList<String> names = new ArrayList<>();
        for (Student student : this.students) {
            names.add(student.getName());
        }
        return names;
    }
}
