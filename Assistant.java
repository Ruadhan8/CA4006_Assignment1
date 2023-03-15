import java.util.*;

public class Assistant implements Runnable {
    static List<Book> booksToTake = new ArrayList<Book>();
    static List<Book> booksInHands = new ArrayList<Book>();
    private final Object lock = new Object();
    private static Book book;

    static int carrySpace = 10;
    static boolean Busy = false;

    public synchronized static List<Book> takeBookFromBox(){
        List<Book> books = Box.getBook();
        if (!books.isEmpty()) {
            while (booksToTake.size() < carrySpace) {
                for(Book book : books){
                    booksToTake.add(book);
                }
            }
            books.removeAll(booksToTake);
            return booksToTake;
        } else {
            return null;
        }
    }
    public synchronized boolean Busy() {
        return Busy;
    }
    public int size() {
        int Size = booksToTake.size();

        //System.out.print(Size);

        return Size;
    }
    


    @Override
    public void run() {
        while (Main.TICKS_PER_DAY > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // System.out.println(Tick.deliveryRecieved);
            if (Tick.deliveryRecieved == true) {
                if (booksInHands.size() == 0) {
                    booksInHands = takeBookFromBox();
                }
                if (booksInHands.size() != 0) {
                    synchronized (lock) {
                        Iterator<Book> iterator = booksInHands.iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Fiction")) {
                                Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Fiction: " + Shelve.FictionShelf);
                                if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are stacked
                                    try {
                                        Thread.sleep(100); // simulate 10 seconds to move to the next shelf
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are
                                    // stacked
                                    try {
                                        Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        iterator = booksInHands.iterator();
                        while (iterator.hasNext()) {
                                // System.out.println("In SPort");
                            book = iterator.next();
                            if (book.toString().equals("Sport")) {
                                 Shelve.AddBookToShelves(book);
                                iterator.remove(); // remove the book from booksInHands
                                System.out.println("<" + Main.tickCount + ">" + "Sport: " + Shelve.SportShelf);
                                 try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                                     e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are stacked
                                try {
                                    Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        iterator = booksInHands.iterator();

                        while (iterator.hasNext()) {
                        book = iterator.next();
                        if (book.toString().equals("Fantasy")) {
                            Shelve.AddBookToShelves(book);
                            iterator.remove(); // remove the book from booksInHands
                            System.out.println("<" + Main.tickCount + ">" + "Fantasy: " + Shelve.FantasyShelf);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                                 e.printStackTrace();
                            }
                        }
                        if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are
                                // stacked
                            try {
                                Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    iterator = booksInHands.iterator();

                    while (iterator.hasNext()) {
                        book = iterator.next();
                        if (book.toString().equals("Horror")) {
                            Shelve.AddBookToShelves(book);
                            iterator.remove(); // remove the book from booksInHands
                            System.out.println("<" + Main.tickCount + ">" + "Horror: " + Shelve.HorrorShelf);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }                      
                        if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are
                                // stacked
                            try {
                                Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    iterator = booksInHands.iterator();

                    while (iterator.hasNext()) {
                        book = iterator.next();
                        if (book.toString().equals("Crime")) {
                            Shelve.AddBookToShelves(book);
                            iterator.remove(); // remove the book from booksInHands
                            System.out.println("<" + Main.tickCount + ">" + "Crime: " + Shelve.CrimeShelf);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are
                                // stacked
                            try {
                                Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    iterator = booksInHands.iterator();

                    while (iterator.hasNext()) {
                        book = iterator.next();
                        if (book.toString().equals("Romance")) {
                            Shelve.AddBookToShelves(book);
                            iterator.remove(); // remove the book from booksInHands
                            System.out.println("<" + Main.tickCount + ">" + "Romance: " + Shelve.RomanceShelf);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        if (!iterator.hasNext()) { // stay at the Fiction shelf until all Fiction books are
                                // stacked
                            try {
                                Thread.sleep(1000); // simulate 10 seconds to move to the next shelf
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        // System.out.println(booksInHands);
        // System.out.println("Check books");
        Tick.deliveryRecieved = false;  
    }
}

    public static void main(String[] args) {
    // Box books = new Box();
    // books.main(args);
    // System.out.println(books.toString());
    // takeBooksFromBox(books);
        // System.out.println(books.toString());

    // System.out.println(booksToTake.toString());
    // System.out.println(takeBooksFromBox(books));
    }

    public List<Book> getBooks() {
        return booksToTake;
    }
}