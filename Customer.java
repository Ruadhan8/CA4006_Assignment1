import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;

// Customer class to handle the functionality of a customer on a thread
public class Customer implements Runnable {
    // introducing variables to be accessed by other classes
    private final String[] genres = { "fiction", "horror", "Romance", "fantasy", "Poetry", "History" };
    private final Random random;
    static int customerCount;
    static int servedCustomerCount;
    private String customer;
    static List<Integer> CustomerWaitTimes = new ArrayList<>();

    // function to generate randomness for determining a book type the customer wants to buy
    public Customer() {
        this.random = new Random();
    }

    // Introducing a function which takes two strings the arrival time and departure time
    // of a customer and changing them to ints to calculate the wait time
    public static int WaitTime(String ArrivalTime, String DepartureTime) {
        Integer Arrival = Integer.parseInt(ArrivalTime);

        Integer Departure = Integer.parseInt(DepartureTime);

        int WaitTime = Departure - Arrival;

        return WaitTime;
    }

    // function which takes an integer and adds the integer to a list
    // of Integers which it returns, the list it is added to is a global class variable
    public static List<Integer> WaitTimeList(int Time) {
        CustomerWaitTimes.add(Time);

        return CustomerWaitTimes;
    }

    // functionality to clear the global List, which is passed to the function
    // this is needed to ensure the list only contains the customers that left
    // during that day
    public static List<Integer> ClearWaitTime(List<Integer> WaitTime) {
        WaitTime.clear();

        return WaitTime;
    }

    // function to calculate the average wait time of customers, by
    // summing the values in the list and dividing it by the size of the list
    public static int WaitTimeAverage(List<Integer> WaitTimes) {
        int i = 0;
        int SumOfWaitTime = 0;

        while (i < WaitTimes.size()) {
            SumOfWaitTime += WaitTimes.get(i);

            i++;
        }

        // introducing an error boundary to prevent average being attemped if the list is empty
        if (WaitTimes.size() != 0) {

            int AverageWaitTime = SumOfWaitTime / WaitTimes.size();

            return AverageWaitTime;
        }

        else {
            return 0;
        }
    }

    // functionality to determine whether a customer comes after a tick
    public static String CustomerProb() {
        List<String> ProbabilityOfCustomer = new ArrayList<String>();
        var i = 0;
        while (i < 10) {
            if (i != 9) {
                ProbabilityOfCustomer.add("False");
            } else {
                ProbabilityOfCustomer.add("True");
            }
            i++;
        }

        int UpperRange = ProbabilityOfCustomer.size();

        Random rand = new Random();
        int Index = rand.nextInt(UpperRange);

        String IsDelivery = ProbabilityOfCustomer.get(Index);

        return IsDelivery;
    }

    // introducing Functionality so a Customer can take a book from a shelf and
    // buy it
    public static void takeBook(String genre) {
        if (genre == "fiction") {
            Shelve.FictionShelf.remove(0);
        }
        if (genre == "horror") {
            Shelve.HorrorShelf.remove(0);

        }
        if (genre == "Romance") {
            Shelve.RomanceShelf.remove(0);
        }
        if (genre == "fantasy") {
            Shelve.FantasyShelf.remove(0);

        }
        if (genre == "History") {
            Shelve.HistoryShelf.remove(0);

        }
        if (genre == "Poetry") {
            Shelve.PoetryShelf.remove(0);

        }

        servedCustomerCount++;
    }

    // main runner code that runs when the Thread is started
    @Override
    public void run() {
        while (true) {
            // try to sleep the thread for 10 ticks
            try {
                Thread.sleep(1 * Main.TickTimeSize);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String isCustomer = Customer.CustomerProb();
            if (isCustomer == "True") {
                // setting the thread Id to a variable
                long threadId = Thread.currentThread().getId();
                // Customer count to ensure customers are uniquly identifiable.
                customerCount++;
                customer = "Customer-" + customerCount;
                // adding the customer name along with arrival time to a string
                String CustomerAndStartTime = customer + ":" + Main.tickCount;
                // getting the next book the customer wants
                String genre = genres[random.nextInt(genres.length)];
                
                // introducing a switch based off the customers book genre
                switch (genre) {
                    // if the genre is fiction
                    case "fiction":
                        // first of three possible events when a customer comes this one that the shelve isn't empty but the waiting line is
                        if (!Shelve.FictionShelf.isEmpty() && Shelve.FictionWaitingLine.isEmpty()) {
                            // take the Book from the shelf
                            takeBook(genre);
                            // add the finishing time to a string along with the start time and customer name
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            // split this string into separate parts
                            String[] parts = CustomerAndEndTime.split(":");
                            // setting a string to contain arrival time and departure time
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            // sending the strings to a function to calculate the wait time
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            // adding the customer wait time to a list to be used to calculate average wait time for the day
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer +" bought a book from " + genre + " section." + "The Customer spent " + WaitTime + " shopping");
                        } // this is the second event a customer arrives and the shelf isn't empty but the line isn't empty either 
                        else if (!Shelve.FictionShelf.isEmpty() && !Shelve.FictionWaitingLine.isEmpty()) {
                            // adding the current customer to the waiting queue
                            Shelve.CustomerWaitingLine(Shelve.FictionWaitingLine, CustomerAndStartTime);
                            // get the customer who is first in the line
                            String CustomerInQueue = Shelve.FictionWaitingLine.remove();
                            // split this string into parts
                            String[] parts = CustomerInQueue.split(":");
                            // setting a string to contain arrival time and departure time
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            // take the Book
                            takeBook(genre);
                            // get departure times as integers
                            String DepartureTime = Integer.toString(Main.tickCount);
                            // calculate the wait time of the customer
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            // add this wait time to a list
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from "+ genre + " section." + " The Customer spent " + WaitTime + " shopping.");
                        } // this is the third case where the shelf is empty
                        else if (Shelve.FictionShelf.isEmpty()) {
                            // the customer is added to the appropriate waiting line
                            Shelve.CustomerWaitingLine(Shelve.FictionWaitingLine, CustomerAndStartTime);
                            System.out
                                    .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;
                    // these are the same as above just for different genres
                    case "fantasy":
                        if (!Shelve.FantasyShelf.isEmpty() && Shelve.FantasyWaitingLine.isEmpty()) {
                            takeBook(genre);
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            String[] parts = CustomerAndEndTime.split(":");
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping.");

                        } else if (!Shelve.FantasyShelf.isEmpty() && !Shelve.FantasyWaitingLine.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.FantasyWaitingLine, CustomerAndStartTime);
                            String CustomerInQueue = Shelve.FantasyWaitingLine.remove();
                            String[] parts = CustomerInQueue.split(":");
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            takeBook(genre);
                            String DepartureTime = Integer.toString(Main.tickCount);
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping");
                        } else if (Shelve.FantasyShelf.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.FantasyWaitingLine, CustomerAndStartTime);
                            System.out
                            .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;

                    case "History":
                        if (!Shelve.HistoryShelf.isEmpty() && Shelve.HistoryWaitingLine.isEmpty()) {
                            takeBook(genre);
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            String[] parts = CustomerAndEndTime.split(":");
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " bought a book from " + genre + " section." + "The Customer spent " + WaitTime + " shopping");
                        } else if (!Shelve.HistoryShelf.isEmpty() && !Shelve.HistoryWaitingLine.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.HistoryWaitingLine, CustomerAndStartTime);
                            String CustomerInQueue = Shelve.HistoryWaitingLine.remove();
                            String[] parts = CustomerInQueue.split(":");
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            takeBook(genre);
                            String DepartureTime = Integer.toString(Main.tickCount);
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping.");
                        } else if (Shelve.HistoryShelf.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.HistoryWaitingLine, CustomerAndStartTime);
                            System.out
                            .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;

                    case "Romance":
                        if (!Shelve.RomanceShelf.isEmpty() && Shelve.RomanceWaitingLine.isEmpty()) {
                            takeBook(genre);
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            String[] parts = CustomerAndEndTime.split(":");
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " bought a book from " + genre + " section." + "The Customer spent " + WaitTime + " shopping");
                        } else if (!Shelve.RomanceShelf.isEmpty() && !Shelve.RomanceWaitingLine.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.RomanceWaitingLine, CustomerAndStartTime);
                            String CustomerInQueue = Shelve.RomanceWaitingLine.remove();
                            String[] parts = CustomerInQueue.split(":");
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            takeBook(genre);
                            String DepartureTime = Integer.toString(Main.tickCount);
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping.");
                        } else if (Shelve.RomanceShelf.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.RomanceWaitingLine, CustomerAndStartTime);
                            System.out
                            .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;

                    case "horror":
                        if (!Shelve.HorrorShelf.isEmpty() && Shelve.HorrorWaitingLine.isEmpty()) {
                            takeBook(genre);
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            String[] parts = CustomerAndEndTime.split(":");
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " bought a book from " + genre + " section." + "The Customer spent " + WaitTime + " shopping");
                        } else if (!Shelve.HorrorShelf.isEmpty() && !Shelve.HorrorWaitingLine.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.HorrorWaitingLine, CustomerAndStartTime);
                            String CustomerInQueue = Shelve.HorrorWaitingLine.remove();
                            String[] parts = CustomerInQueue.split(":");
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            takeBook(genre);
                            String DepartureTime = Integer.toString(Main.tickCount);
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping.");
                        } else if (Shelve.HorrorShelf.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.HorrorWaitingLine, CustomerAndStartTime);
                            System.out
                            .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;

                    case "Poetry":
                        if (!Shelve.PoetryShelf.isEmpty() && Shelve.PoetryWaitingLine.isEmpty()) {
                            takeBook(genre);
                            String CustomerAndEndTime = CustomerAndStartTime + ":" + Main.tickCount;
                            String[] parts = CustomerAndEndTime.split(":");
                            String ArrivalTime = parts[1];
                            String DepartureTime = parts[2];
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " bought a book from " + genre + " section." + "The Customer spent " + WaitTime + " shopping");
                        } else if (!Shelve.PoetryShelf.isEmpty() && Shelve.PoetryWaitingLine.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.PoetryWaitingLine, CustomerAndStartTime);
                            String CustomerInQueue = Shelve.PoetryWaitingLine.remove();
                            String[] parts = CustomerInQueue.split(":");
                            String CustomerToServe = parts[0];
                            String ArrivalTime = parts[1];
                            takeBook(genre);
                            String DepartureTime = Integer.toString(Main.tickCount);
                            int WaitTime = WaitTime(ArrivalTime, DepartureTime);
                            WaitTimeList(WaitTime);
                            System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + CustomerToServe + " bought a book from " + genre + " section." + " The Customer spent " + WaitTime + " shopping.");
                        } else if (Shelve.PoetryShelf.isEmpty()) {
                            Shelve.CustomerWaitingLine(Shelve.PoetryWaitingLine, CustomerAndStartTime);
                            System.out
                            .println("<" + Main.tickCount + ">" + "<" + threadId + ">" + customer + " joined the waiting line for " + genre);
                        }

                        break;
                }
            }
        }
    }
}