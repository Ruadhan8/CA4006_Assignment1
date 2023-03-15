import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Tick class to manage the passing of time and statistics for the EOD in the store
public class Tick implements Runnable {
    private Box box;
    private Random random = new Random();
    public static boolean deliveryRecieved = false;
    public static int DeliveryCount = 0;
    public static int CustomersPrior = 0;
    public static int CustomersServedPrior = 0;

    public Tick(Box box) {
        this.box = box;
    }

    // code for the thread
    @Override
    public void run() {

        while (true) {
                try {
                    // sleep for one tick
                    Thread.sleep(1 * Main.TickTimeSize); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long threadId = Thread.currentThread().getId();
                // run NextDeliveryTime in delivery file that returns either True or False
                String isDelivery = Delivery.NextDeliveryTime();
                // if what is returned is True 
                if (isDelivery == "True") { 
                    // create a new thread for the box
                    Thread boxThread = new Thread(box); 

                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + "Recieved a delivery!");
                    // run the box thread and have it stop when it is finished
                    
                    boxThread.run();

                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + " Box: " + Box.box); 
                    // increment delivery counter
                    DeliveryCount++; 
                }
                
                Main.tickCount++;
                // if the tickCount modulo ticks per day (1000) == 0
                if (Main.tickCount % Main.TicksPerDay == 0) { 
                    // get the average wait time of the customers
                    int AverageWaitTime = Customer.WaitTimeAverage(Customer.CustomerWaitTimes); 
                     // get the amount of deliverys done that day
                    int DeliveryAmount = Tick.DeliveryCount;
                    // get the total customers today
                    int TotalCustomers = Customer.customerCount; 
                    // get the total amount of customers served in a day
                    int TotalCustomersServed = Customer.servedCustomerCount; 

                    int CustomersInDay = 0;
                    int CustomersServedInDay = 0;

                    // gets the customers from the first day
                    if (Main.tickCount < Main.TicksPerDay) { 
                        CustomersInDay = TotalCustomers; 
                        CustomersServedInDay = TotalCustomersServed;
                    }  

                    else {
                        // gets the customers from every other day after the first day
                        CustomersInDay = TotalCustomers - CustomersPrior;
                        CustomersServedInDay = TotalCustomersServed - CustomersServedPrior;
                    }

                    CustomersPrior = Customer.customerCount;
                    CustomersServedPrior = Customer.servedCustomerCount;

                    System.out.println("It is the end of the day here are the statistics for the day: ");
                    System.out.println("There were this many customers visting today: " + CustomersInDay);
                    System.out.println("There were this many customers served today: " + CustomersServedInDay);
                    System.out.println("There were this many deliverys: " + DeliveryAmount);
                    System.out.println("The average wait time of customers was: " + AverageWaitTime);
        
                    Customer.ClearWaitTime(Customer.CustomerWaitTimes); 
                    Tick.DeliveryCount = 0; 
                }
        
        }
    }

    public static void main(String[] args) {

    }
}