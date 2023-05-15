import java.util.LinkedList;

abstract class CheckoutLane extends LinkedList<Customer> implements Comparable<CheckoutLane> {
    
    //abstract variable for getCheckoutTime
    public abstract double getCheckoutTime(Customer c);

    //compares the size of this lane to other lane and will return depending
    //on if size is greater than, exqual to, or less than
    public int compareTo(CheckoutLane other) {
        if (this.size() < other.size()) {
            return -1;
        } else if (this.size() == other.size()) {
            return 0;
        } else {
            return 1;
        }
    }
}
