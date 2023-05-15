public class Customer {
    private int customerID;
    private double arrivalTime;
    private int numItems;
    private double timePerItem;

    private double finishedCheckoutTime;
    private double endShoppingTime;
    private double endWaitingTime;

    public Customer(int customerID, double arrivalTime, int numItems, double timePerItem) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.numItems = numItems;
        this.timePerItem = timePerItem;
    }

    //getter for customer ID
    public int getCustomerID() {
        return customerID;
    }

    //getter for arrival time
    public double getArrivalTime() {
        return arrivalTime;
    }

    //getter for number of items
    public int getNumItems() {
        return numItems;
    }

    //getter for time per item
    public double getTimePerItem() {
        return timePerItem;
    }

    //getter for wait time of customer
    public double getWaitTime() {
        return endWaitingTime - endShoppingTime;
    }

    //getter for time for customer to finish shopping
    public double getTimeToFinishShopping() {
        return numItems * timePerItem;
    }

    
    //getter for time customer finished checking out
    public double getFinishedCheckoutTime() {
        return finishedCheckoutTime;
    }

    //getter for time it took customer to end shopping
    public double getEndShoppingTime() {
        return endShoppingTime;
    }

    //getter for time it took for customers to end waiting
    public double getEndWaitingTime() {
        return endWaitingTime;
    }

    //setter for finished checkout time 
    public void setFinishedCheckoutTime(double finishedCheckoutTime) {
        this.finishedCheckoutTime = finishedCheckoutTime;
    }

    //setter for end shopping time
    public void setEndShoppingTime(double endShoppingTime) {
        this.endShoppingTime = endShoppingTime;
    }

    //setter for end waiting time
    public void setEndWaitingTime(double endWaitingTime) {
        this.endWaitingTime = endWaitingTime;
    }

}


