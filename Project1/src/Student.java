public class Student implements Comparable<Student> {

    private final int id;
    private final String name;
    private int duration;
    private final double rating;

    public Student(int id, String name, int duration, double rating) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.rating = rating;
    }

    @Override
    public int compareTo(Student o) {
        return this.id - o.id;
    }

    /**
     * checks whether this student can stay at the house
     * condition for this is that house rating should be greater than or equal to student threshold rating
     * @param house the house
     * @return true if this student can stay at the house
     */
    public boolean likes(House house) {
        return house.getRating() >= this.rating;
    }

    /**
     * checks whether this student is graduated.
     * graduated students are excluded when finding available houses for students
     * @return true if this student is graduated, else false
     */
    public boolean graduated() {
        return this.duration == 0;
    }

    /**
     * this student completes one semester at the university
     */
    public void studySemester() {
        if (this.duration > 0) {
            this.duration--;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getDuration() {
        return this.duration;
    }
}
