//customer ends shopping and picks lane class will reference customer and time from Event class
public class CustomerEndsShoppingPicksLane extends Event {
    public CustomerEndsShoppingPicksLane(Customer customer, double time) {
        super(customer, time);
    }
    
}
