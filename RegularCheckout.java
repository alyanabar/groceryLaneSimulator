public class RegularCheckout extends CheckoutLane{
    //calculate time to checkout for regular checkouts
    public double getCheckoutTime(Customer c) {
        return 0.05 * c.getNumItems() + 2.0;
    }
    
}
