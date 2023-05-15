public class Event implements Comparable<Event> {
    private Customer customer;
    private double time;

    public Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    //getter for customer
    public Customer getCustomer() {
        return customer;
    }

    //getter for time
    public double getTime() {
        return time;
    }

    //Event implements Comparable and will sort by time
    public int compareTo(Event other) {
        if (this.getTime() < other.getTime()) {
            return -1;
        } else if (this.getTime() == other.getTime()) {
            return 0;
        } else {
            return 1;
        }
    }
}
