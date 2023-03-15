import java.util.*;
import java.util.concurrent.Semaphore;

public class Assistant implements Runnable {
    private final Object lock = new Object();
    private static Book book;
    List<String> priorityType = new ArrayList<String>();
    List<Book> booksInHands = new ArrayList<Book>();
    private static Semaphore assistant = new Semaphore(1);
    String name;

    static int carrySpace = 10;
    static boolean isBusy = false;

    public Assistant(String name, List<Book> booksInHands) {
        this.booksInHands = booksInHands;
        this.name = name;
        // this.booksToTake = booksToTake;
    }

    public static void TakeBreak() {
        try {
            Thread.sleep(Main.MilliSecondsNeeded(Main.TICK_TIME_SIZE, 15));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Book> takeBookFromBox() {
        List<Book> books = Box.getBook();
        List<Book> booksToTake = new ArrayList<Book>();

        if (!books.isEmpty()) {
            // System.out.println("Box contents are: " + books);
            while (booksToTake.size() < carrySpace) {
                for (Book book : books) {
                    booksToTake.add(book);
                }
            }
            books.removeAll(booksToTake);
            return booksToTake;
        } else {
            return null;
        }
    }

    public List<Book> takePriorityBookFromBox(List<String> priorityType) {
        List<Book> books = Box.getBook();
        List<Book> booksToTake = new ArrayList<Book>();

        int i = 0;
        if (!books.isEmpty()) {
            // System.out.println("Box contents are: " + books);

            while (booksToTake.size() < carrySpace) {
                for (Book book : books) {
                    while (i < priorityType.size()) {
                        if (book.toString() == priorityType.get(i)) {
                            booksToTake.add(book);
                        }
                        i++;
                    }

                    booksToTake.add(book);
                }
            }
            books.removeAll(booksToTake);
            return booksToTake;
        } else {
            return null;
        }
    }

    public synchronized boolean isBusy() {
        return isBusy;
    }

    // public int size() {
    // int Size = booksToTake.size();

    // // System.out.print(Size);

    // return Size;
    // }

    public boolean IsWaiting(Queue<String> Shelf) {
        boolean IsWaiting = false;
        if (Shelf.size() != 0) {
            IsWaiting = true;
        }

        return IsWaiting;
    }

    public int HowLongWillItTake(List<Book> BookInHand) {
        int BaseTimeToWalkToSection = 10000 / Main.TICK_TIME_SIZE;

        int HowMuchExtraForBook = (1000 / Main.TICK_TIME_SIZE) * BookInHand.size();

        int TotalSleepTime = BaseTimeToWalkToSection + HowMuchExtraForBook;

        TotalSleepTime = TotalSleepTime / 100;

        return TotalSleepTime;
    }

    // @Override
    // public String toString() {
    // return "" + booksToTake;
    // }

    @Override
    public void run() {

        while (true) {
            long threadId = Thread.currentThread().getId();
            try {

                Thread.sleep(1 * Main.TICK_TIME_SIZE);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                assistant.acquire();
                if (!Box.BookInBox.isEmpty()) {
                    if (IsWaiting(Shelve.CrimeWaitingLine)) {
                        priorityType.add("Crime");
                    }
                    if (IsWaiting(Shelve.HorrorWaitingLine)) {
                        priorityType.add("Horror");
                    }
                    if (IsWaiting(Shelve.RomanceWaitingLine)) {
                        priorityType.add("Romance");
                    }
                    if (IsWaiting(Shelve.FantasyWaitingLine)) {
                        priorityType.add("Fantasy");
                    }
                    if (IsWaiting(Shelve.FictionWaitingLine)) {
                        priorityType.add("Fiction");
                    }
                    if (IsWaiting(Shelve.SportWaitingLine)) {
                        priorityType.add("Sport");
                    }

                    System.out.println(priorityType);

                    try {
                        if (booksInHands.size() == 0 && priorityType.size() == 0) {
                            try {
                                Thread.sleep(10 * Main.TICK_TIME_SIZE);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            booksInHands = takeBookFromBox();
                        } else if (booksInHands.size() == 0 && priorityType.size() != 0) {
                            try {
                                Thread.sleep(10 * Main.TICK_TIME_SIZE);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            booksInHands = takePriorityBookFromBox(priorityType);
                        }
                        System.out.println("<" + threadId + ">" + "Book in hand: " + booksInHands);

                    } finally {

                    }

                    try {
                        Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                assistant.release();

            }

            if (booksInHands.size() != 0) {
                synchronized (lock) {
                    Iterator<Book> iterator = booksInHands.iterator();
                    if (booksInHands.toString().contains("Fiction")) {
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Fiction")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Fiction: " +
                                        Shelve.FictionShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction

                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (booksInHands.toString().contains("Sport")) {
                        iterator = booksInHands.iterator();
                        while (iterator.hasNext()) {
                            // System.out.println("In SPort");
                            book = iterator.next();
                            if (book.toString().equals("Sport")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Sport: " +
                                        Shelve.SportShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction
                                // stacked
                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    iterator = booksInHands.iterator();
                    if (booksInHands.toString().contains("Fantasy")) {
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Fantasy")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Fantasy: " +
                                        Shelve.FantasyShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction
                                // stacked
                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (booksInHands.toString().contains("Horror")) {
                        iterator = booksInHands.iterator();

                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Horror")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Horror: " +
                                        Shelve.HorrorShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction
                                // stacked
                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (booksInHands.toString().contains("Crime")) {
                        iterator = booksInHands.iterator();

                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Crime")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Crime: " +
                                        Shelve.CrimeShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction
                                // stacked
                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (booksInHands.toString().contains("Romance")) {
                        iterator = booksInHands.iterator();

                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Romance")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Romance: " +
                                        Shelve.RomanceShelf);
                                try {
                                    Thread.sleep(1 * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction
                                // stacked
                                try {
                                    Thread.sleep((booksInHands.size() + 10) * Main.TICK_TIME_SIZE);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }

        // System.out.println(booksInHands);
        // System.out.println("Check books");
    }

    public static void main(String[] args) {
        // Box books = new Box();
        // books.main(args);
        // System.out.println(books.toString());
        // takeBookFromBox(books);
        // System.out.println(books.toString());

        // System.out.println(booksToTake.toString());
        // System.out.println(takeBookFromBox(books));
    }

    // public List<Book> getBook() {
    // return booksToTake;
    // }
}