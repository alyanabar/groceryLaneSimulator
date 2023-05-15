import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //arraylist for customers
        ArrayList<Customer> customers = new ArrayList<>();

        //priority queue of events
        PriorityQueue<Event> events = new PriorityQueue<>();

        //priority queue for regular checkouts
        PriorityQueue<RegularCheckout> regularLanes = new PriorityQueue<>();

        //change number of regular lanes here 
        int numRegular = 9;

        //adds new regular lane objects to regularLanes priority queue depending on the number 
        //entered in numRegular
        for(int i = 0; i < numRegular; i++) {
            regularLanes.add(new RegularCheckout());
        }

        //priority queue for express checkouts
        PriorityQueue<ExpressCheckout> expressLanes = new PriorityQueue<>();

        //can change number of express lanes here
        int numExpress = 3;

        //adds new express lane object to expressLanes priority queue depending on the number
        //entered in numExpress
        for (int i = 0; i < numExpress; i++) {
            expressLanes.add(new ExpressCheckout());
        }

        // read in the data file and if file does not exist, display error and exit
        File myFile = new File("arrival.txt");
        if (!myFile.exists()) {
            System.out.println("Error: could not find " + myFile.getName());
            System.exit(1);
        }

        //try catch block to make sure if file is working. if not working, print file not found.
        //if working, proceed with code
        try {
            //scan file 
            Scanner in = new Scanner(myFile); 

            //while file has another token, create each number in line equal to a variable to then
            //create into a customer object and add to customers list
            int count = 0;
            while (in.hasNext()) {
                //assign/set variables arrivalTime, numItems, and timePerItem to next read-in type
                double arrivalTime = in.nextDouble();
                int numItems = in.nextInt();
                double timePerItem = in.nextDouble();

                //creat new customer object with variables and add count as the customerID
                Customer customer = new Customer(count, arrivalTime, numItems, timePerItem);
                
                //add customer object to customers arrayList
                customers.add(customer);

                //add new customer arrival object with the customer and arrival time to the events 
                //priority queue
                events.add(new CustomerArrival(customer, arrivalTime));
                count++;
            } 
            //close scanner
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }

        //while events priority queue is not empty
        while (!events.isEmpty()) {
            //this will take out first event in events priority queue with poll
            Event topOfEventsPQ = events.poll();

            //if topOfEventPQ is an instance of customer arrival, then print out time and that the 
            //customer has arrived and customerID and more
            if (topOfEventsPQ instanceof CustomerArrival) {
                //attempt to format decimal 
                DecimalFormat df = new DecimalFormat("#.##");

                //print statement of arrival (print out reference below)
                //1.00: Arrival Customer 2
                System.out.println(df.format(topOfEventsPQ.getTime()) + ": Arrival Customer " + topOfEventsPQ.getCustomer().getCustomerID());

                //declare variable time after shopping and add time to time to finish shopping
                double timeAfterShopping = topOfEventsPQ.getTime() + topOfEventsPQ.getCustomer().getTimeToFinishShopping();

                //add new customerEndsShoppingPicksLane object with customer and the time calculated after shopping 
                events.add(new CustomerEndsShoppingPicksLane(topOfEventsPQ.getCustomer(), timeAfterShopping));

            //else if the topOfEventsPQ is an instance of customer ends shopping and then picks lane, then
            //will print out time and that the customer finished shopping and customerID and more 
            } else if (topOfEventsPQ instanceof CustomerEndsShoppingPicksLane) {
                
                //set end shopping time
                topOfEventsPQ.getCustomer().setEndShoppingTime(topOfEventsPQ.getTime());

                //attempt to format decimals
                DecimalFormat df1 = new DecimalFormat("#.##");

                //print statement of finished shopping time (print out reference below)
                //8.00: Finished Shopping Customer 2
                System.out.println(df1.format(topOfEventsPQ.getCustomer().getEndShoppingTime()) + ": Finished Shopping Customer " + topOfEventsPQ.getCustomer().getCustomerID());

                //if number of tems is greater than 12, then it will go to regular chekcout lane. Else, it would
                //check both regular and express  lane to see which lane is smaller and will act accordingly
                if (topOfEventsPQ.getCustomer().getNumItems() > 12) {
                    //More that 12, chose Lane 0 (0)
                    System.out.println("More than 12, chose Lane ");

                    //regular lane of RegularCheckout will take out first customer of queue
                    RegularCheckout regularLane = regularLanes.poll();
                    
                    //add first customer to the regular lane 
                    regularLane.add(topOfEventsPQ.getCustomer());

                    //if the size of regular lane is 1, then.. 
                    if (regularLane.size() == 1) {
                        //get calculation for regular lane
                        double timeFinishedCheckout = topOfEventsPQ.getTime() + topOfEventsPQ.getCustomer().getNumItems() * 0.05 + 2.0;

                        //add a new event object for customer ends checkout with calculation
                        events.add(new CustomerEndsCheckout(topOfEventsPQ.getCustomer(), timeFinishedCheckout, regularLane));
                        
                        //set end of waiting time
                        topOfEventsPQ.getCustomer().setEndWaitingTime(topOfEventsPQ.getTime());
                    }

                    //add regularLane back to regular lanes pq
                    regularLanes.add(regularLane);

                } else {
                    //Less than 12, chose Lane 4 (0)
                    System.out.println("Less than 12, chose Lane ");

                    //look at both express lane and regular lane to check for next step 
                    ExpressCheckout express = expressLanes.peek();
                    RegularCheckout regular = regularLanes.peek();

                    //if express lane size is less than or equal to regular lane size, then..
                    if (express.size() <= regular.size()) {

                        //express lane of ExpressCheckout will take out first customer of queue
                        ExpressCheckout expressLane =expressLanes.poll();

                        //add first customer to the express lane 
                        expressLane.add(topOfEventsPQ.getCustomer());

                        //if the size of express lane is 1, then.. 
                        if (expressLane.size() == 1) {
                            //get calculation for express lane 
                            double timeFinishedCheckout = topOfEventsPQ.getTime() + topOfEventsPQ.getCustomer().getNumItems() * 0.1 + 1.0;

                            ///add a new event object for customer ends checkout with calculation
                            events.add(new CustomerEndsCheckout(topOfEventsPQ.getCustomer(), timeFinishedCheckout, expressLane));

                            //set end of waiting time
                            topOfEventsPQ.getCustomer().setEndWaitingTime(topOfEventsPQ.getTime());
                        }

                        //add expressLane back to express lanes pq
                        expressLanes.add(expressLane);
                    } else {
                        //else, go through regular lane process (similar to above)
                        RegularCheckout regularLane = regularLanes.poll();
                        regularLane.add(topOfEventsPQ.getCustomer());
                        
                        if (regularLane.size() == 1) {
                            double timeFinishedCheckout = topOfEventsPQ.getTime() + topOfEventsPQ.getCustomer().getNumItems() * 0.05 + 2.0;
                            events.add(new CustomerEndsCheckout(topOfEventsPQ.getCustomer(), timeFinishedCheckout, regularLane));
                            topOfEventsPQ.getCustomer().setEndWaitingTime(topOfEventsPQ.getTime());
                        }

                        //add regularLane back to regular lanes pq
                        regularLanes.add(regularLane);
                    }
                }

            //else if the topOfEventsPQ is an instance of customer ends checkout, then will print out time 
            //and that the customer ended checkout and customerID and more
            } else if (topOfEventsPQ instanceof CustomerEndsCheckout) {

                //set time at when customer finishes checkinf out
                topOfEventsPQ.getCustomer().setFinishedCheckoutTime(topOfEventsPQ.getTime());

                //attempt to format the decimal places
                DecimalFormat df2 = new DecimalFormat("#.##");

                //print out statement of finished check out (example format below)
                //10.20: Finished Checkout Customer 1 on Lane 4 (0) (0.00 minute wait, 0 people in line -- finished shopping at 8.00, got to the front of the line at 8.00)
                System.out.println(df2.format(topOfEventsPQ.getCustomer().getFinishedCheckoutTime()) + ": Finished Checkout Customer " + topOfEventsPQ.getCustomer().getCustomerID() + " on Lane " + 
                "(" + topOfEventsPQ.getCustomer().getWaitTime() + " minute wait, " + " people in line -- finished shopping at " + topOfEventsPQ.getCustomer().getEndShoppingTime() + ", got to the front of the line at " + topOfEventsPQ.getCustomer().getEndWaitingTime() + ")");

                //finished checkout time
                CustomerEndsCheckout endingCheckout = (CustomerEndsCheckout) topOfEventsPQ;
                
                CheckoutLane checkoutLane = endingCheckout.getCheckoutLane();

                // checkoutLane is an instance of regular checkout, then..
                if (checkoutLane instanceof RegularCheckout) {
                    //remove checkoutLane from regularLanes
                    regularLanes.remove(checkoutLane);

                    //take out first item of checkoutLane
                    checkoutLane.poll();

                    //if the checkoutlane size is greater than or equal to 1, then..
                    if (checkoutLane.size() >= 1) {
                        //calculate time finished checkout with regular checkout calculations
                        double timeFinishedCheckout = endingCheckout.getTime() + checkoutLane.peek().getNumItems() * 0.05 + 2.0;
                        
                        //add new customer ends checkout object with new calculations
                        events.add(new CustomerEndsCheckout(checkoutLane.peek(), timeFinishedCheckout, checkoutLane));
                        checkoutLane.peek().setEndWaitingTime(topOfEventsPQ.getTime());
                    }

                    //add checkout lane back to regular checkout lanes
                    regularLanes.add((RegularCheckout) checkoutLane);
                } else {
                    //remove checkoutLane from expressLanes
                    expressLanes.remove(checkoutLane);
                    
                    //take out first item of checkout lane
                    checkoutLane.poll();

                    //if the checkout lane size is greater than or equal to 1, then..
                    if (checkoutLane.size() >= 1) {
                        //calculate time finished checkout with express checkout calculations
                        double timeFinishedCheckout = endingCheckout.getTime() + checkoutLane.peek().getNumItems() * 0.1 + 1.0;
                        
                        //add new customer ends checkout object with new calculations
                        events.add(new CustomerEndsCheckout(checkoutLane.peek(), timeFinishedCheckout, checkoutLane));
                        checkoutLane.peek().setEndWaitingTime(topOfEventsPQ.getTime());
                    }
                    //add express lane back to express checkout lanes
                    expressLanes.add((ExpressCheckout) checkoutLane);
                }
            }
        }

        //calculate average. made loop for arraylist of customers that will loop that amount of times of number of customers
        double sum = 0; 
        for (int i=0; i < customers.size(); i++) {
            //calculate sum of wait time
            sum += customers.get(i).getWaitTime();
        }

        //divie sum of wait time by num of customers for average
        double avg = sum/customers.size();

        //attempt to format decimal average
        DecimalFormat df3 = new DecimalFormat("#.##");

        //print avg wait time
        System.out.println("Average wait time: " + df3.format(avg));
    }
}