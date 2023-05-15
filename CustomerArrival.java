//customer arrival class will reference customer and tiem from Event class
public class CustomerArrival extends Event {
    public CustomerArrival(Customer customer, double time) {
        super(customer, time);
    }
}
