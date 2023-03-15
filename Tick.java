import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tick implements Runnable {
    private Box bookstore;
    private Random random = new Random();
    private static String deliveryTime = Delivery.NextDeliveryTime();
    public static boolean deliveryRecieved = false;

    public Tick(Box bookstore) {
        this.bookstore = bookstore;
    }

    @Override
    public void run() {

        while (Main.TICKS_PER_DAY > 0) {
            try {
                // Sleep for 1 tick
                // System.out.println(deliveryTime);
                Thread.sleep(100);
                // System.out.println("DeliveryTime: " + deliveryTime);
                // Main.TICKS_PER_DAY--;
                long threadId = Thread.currentThread().getId();

                String isDelivery = Delivery.NextDeliveryTime();
                if (isDelivery == "True") {
                    Thread bookstoreThread = new Thread(bookstore);
                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + "Recieved a delivery!");
                    bookstoreThread.start();
                    bookstoreThread.join();
                    deliveryTime = Delivery.NextDeliveryTime();
                    deliveryTime += Main.tickCount;
                    System.out.println("<" + Main.tickCount + "> <" + threadId + ">" + " Box: " + Box.box);
                    deliveryRecieved = true;
                }
                Main.tickCount++;
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

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Tick tickThread = new Tick(bookstore);
        // Thread ticks = new Thread(tickThread);
        // ticks.run();
    }
}