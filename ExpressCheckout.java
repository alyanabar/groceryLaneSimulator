public class ExpressCheckout extends CheckoutLane {
    //calculate time to checkout for express checkouts
    public double getCheckoutTime(Customer c) {
        return 0.1 * c.getNumItems() + 1.0;
    }
}
