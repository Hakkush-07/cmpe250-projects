public class House implements Comparable<House> {

    private final int id;
    private int duration;
    private final double rating;

    public House(int id, int duration, double rating) {
        this.id = id;
        this.duration = duration;
        this.rating = rating;
    }

    @Override
    public int compareTo(House o) {
        return this.id - o.id;
    }

    /**
     * places the student to this house
     * @param student the student
     */
    public void place(Student student) {
        this.duration = student.getDuration();
    }

    /**
     * checks whether this house is available
     * @return true if this house is available else false
     */
    public boolean available() {
        return this.duration == 0;
    }

    /**
     * one semester passes for this house
     */
    public void passSemester() {
        if (this.duration > 0) {
            this.duration--;
        }
    }

    public double getRating() {
        return this.rating;
    }
}
