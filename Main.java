import java.security.SecureRandom;
import java.util.*;

public class Main {
    private static final int DELIVERY_TICKS = 100;
    private static final int CUSTOMER_TICKS = 10;
    public static int TICKS_PER_DAY = 1000;
    public static int tickCount = 0;
    public static Box box = new Box();
    private final static List<Assistant> assistants = new ArrayList<>();

    // public Main() {
    //     // int tickCount = 0;
    //     // TICKS_PER_DAY = 1000;
    // }

    // @Override
    // public void run() {
    //     long threadId = Thread.currentThread().getId();
    //     while (TICKS_PER_DAY > 0) {
    //         try {
    //             Thread.sleep(1000);
    //         } catch (InterruptedException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //         // tickCount++;
    //         System.out.println("TPD: " + TICKS_PER_DAY);
    //         Assistant assistant = new Assistant();
    //         Tick tick = new Tick(box);
    //         Thread ticks = new Thread(tick);
            
    //         Thread assistantThread = new Thread(assistant);
    //         ticks.start();
    //         TICKS_PER_DAY--;
    //         // assistantThread.start();

    //     }
    // }
    // public synchronized boolean isAssistantAvailable() {
    //     for (Assistant assistant : assistants) {
    //         if (!assistant.isBusy()) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            // assistants.add(new Assistant());
            threads.add(new Thread(new Assistant()));
        }
        for (int i = 0; i < 10; i++) {
            // threads.add(new Thread(new Customer(bookstore)));
        }
        threads.add(new Thread(new Tick(box)));

        
        for (Thread thread : threads) {
            // System.out.println(thread);
            thread.start();
        }
    }
}