public class Physiotherapist {
    private final double serviceTime;
    private boolean available;

    public Physiotherapist(double serviceTime) {
        this.serviceTime = serviceTime;
        this.available = true;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }
}
