import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Creating a Tick Class to manage the passing of Time and Statistics for the EOD in the Bookstore
public class Tick implements Runnable {
    private Box bookstore;
    private Random random = new Random();
    private static String deliveryTime = Delivery.NextDeliveryTime();
    public static boolean deliveryRecieved = false;
    public static int DeliveryCount = 0;
    public static int CustomersPrior = 0;
    public static int CustomersServedPrior = 0;

    public Tick(Box bookstore) {
        this.bookstore = bookstore;
    }

    // Runner Code for the Thread
    @Override
    public void run() {

        while (true) {
                try {
                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // System.out.println(Main.tickCount);
                long threadId = Thread.currentThread().getId();

                String isDelivery = Delivery.NextDeliveryTime();
                if (isDelivery == "True") {
                    Thread bookstoreThread = new Thread(bookstore);
                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + "Recieved a delivery!");
                    bookstoreThread.run();
                    deliveryTime = Delivery.NextDeliveryTime();
                    deliveryTime += Main.tickCount;
                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + " Box: " + Box.box);
                    deliveryRecieved = true;
                    DeliveryCount++;
                }
                
                Main.tickCount++;

                if (Main.tickCount % Main.TICKS_PER_DAY == 0) {
                    int AverageWaitTime = Customer.WaitTimeAverage(Customer.CustomerWaitTimes);
                    int DeliveryAmount = Tick.DeliveryCount;
                    int TotalCustomers = Customer.customerCount;
                    int TotalCustomersServed = Customer.customerServedCount;
                    int CustomersInDay = 0;
                    int CustomersServedInDay = 0;


                    if (Main.tickCount < Main.TICKS_PER_DAY) {
                        CustomersInDay = TotalCustomers;
                        CustomersServedInDay = TotalCustomersServed;
                    }  

                    else {
                        CustomersInDay = TotalCustomers - CustomersPrior;
                        CustomersServedInDay = TotalCustomersServed - CustomersServedPrior;
                    }

                    CustomersPrior = Customer.customerCount;
                    CustomersServedPrior = Customer.customerServedCount;

                    System.out.println("It is the End of the Day here are the statistics for the Day: ");
                    System.out.println("There was this many customers visting today: " + CustomersInDay);
                    System.out.println("There was this many customers served today: " + CustomersServedInDay);
                    System.out.println("There was this many deliverys: " + DeliveryAmount);
                    System.out.println("The Average Wait Time of Customers was: " + AverageWaitTime);
        
                    Customer.ClearWaitTime(Customer.CustomerWaitTimes);
                    Tick.DeliveryCount = 0;
                }
                // Main.TICKS_PER_DAY--;
                // System.out.println(Main.TICKS_PER_DAY);
                // System.out.println(Main.tickCount);
                // Randomly generate a customer every 10 ticks (on average)
                // if (random.nextInt(10) == 0) {
                // // Generate a random genre
                // String[] genres = {"fiction", "horror", "romance", "fantasy", "poetry",
                // "history"};
                // String genre = genres[random.nextInt(genres.length)];

                // // Attempt to buy a book from the genre
                // boolean success = bookstore.getBook(genre);

                // // If the purchase was successful, print a message
                // if (success) {
                // System.out.println("Customer bought a " + genre + " book.");
                // }
                // }
        
        }
    }

    public static void main(String[] args) {
        // Tick tickThread = new Tick(bookstore);
        // Thread ticks = new Thread(tickThread);
        // ticks.run();
    }
}