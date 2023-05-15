//customer ends checkout class that will reference customer and time from Event class
public class CustomerEndsCheckout extends Event {

    //added checkoutLane to clarify type of lane
    private CheckoutLane checkoutLane;

    public CustomerEndsCheckout(Customer customer, double time, CheckoutLane checkoutLane) {
        super(customer, time);
        this.checkoutLane = checkoutLane;
    }
    
    //getter for checkoutLane
    public CheckoutLane getCheckoutLane() {
        return checkoutLane;
    }
}
