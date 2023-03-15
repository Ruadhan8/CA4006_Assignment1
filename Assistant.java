import java.util.*;
import java.util.concurrent.Semaphore;

public class Assistant implements Runnable {
    // Introducing an assistant class
    private final Object lock = new Object();
    private static Books book;
    private int assistantTicks = 0;
    List<String> priorityType = new ArrayList<String>();
    List<Books> BooksPossessed = new ArrayList<Books>();
    private static Semaphore assistant = new Semaphore(1);
    private static Semaphore assistantBreaks = new Semaphore(1);
    static Random rand = new Random();
    private static int randomNum= rand.nextInt(101) + 200;
    static int carrySpace = 10;
    String name;
    int booksCounter = 0;
    

    // New instance of BooksPossessed and name for assistants
    public Assistant(String name, List<Books> BooksPossessed, int booksCounter) {
        this.BooksPossessed = BooksPossessed;
        this.name = name;
        this.booksCounter = booksCounter;
    }
    // Take books from the box for each assistant with new BooksToTake variable each time
    public List<Books> takeBooksFromBox() {
        List<Books> books = Box.takeBooks();
        List<Books> booksToTake = new ArrayList<Books>();

        if (!books.isEmpty()) {
            while (booksToTake.size() < carrySpace) {
                for (Books book : books) {
                    booksToTake.add(book);
                }
            }
            books.removeAll(booksToTake);
            return booksToTake;
        } else {
            return null;
        }
    }
    // Take books from the box for each assistant with new BooksToTake variable each time
    // with a list of book types being passed through
    public List<Books> takePriorityBooksFromBox(List<String> priorityType) {
        List<Books> books = Box.takeBooks();
        List<Books> booksToTake = new ArrayList<Books>();

        int i = 0;
        if (!books.isEmpty()) {
            while (booksToTake.size() < carrySpace) {
                for (Books book : books) {
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
    
    // Checks to see if a customer is waiting at a certain shelf
    public boolean waiting(Queue<String> Shelf) {
        boolean waiting = false;
        if (Shelf.size() != 0) {
            waiting = true;
        }

        return waiting;
    }

    @Override
    public void run() {
        // continues to run while thread is active
        while (true) {
            long threadId = Thread.currentThread().getId();
            try {

                Thread.sleep(1 * Main.TickTimeSize); // sleep for 1 tick
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assistantTicks++;

            try {
                assistant.acquire(); 
                // semaphore is used to ensure two assistants aren't going to box at the same time
                if (!Box.BooksInBox.isEmpty()) {
                    if (waiting(Shelve.HistoryWaitingLine)) {
                        priorityType.add("History");
                    }
                    if (waiting(Shelve.HorrorWaitingLine)) {
                        priorityType.add("Horror");
                    }
                    if (waiting(Shelve.RomanceWaitingLine)) {
                        priorityType.add("Romance");
                    }
                    if (waiting(Shelve.FantasyWaitingLine)) {
                        priorityType.add("Fantasy");
                    }
                    if (waiting(Shelve.FictionWaitingLine)) {
                        priorityType.add("Fiction");
                    }
                    if (waiting(Shelve.PoetryWaitingLine)) {
                        priorityType.add("Poetry");
                    }
                    try {
                        // when assitant has no books in hand and there is no one waiting in a queue, take books from box
                        if (BooksPossessed.size() == 0 && priorityType.size() == 0) { 
                            try {
                                Thread.sleep(10 * Main.TickTimeSize);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            BooksPossessed = takeBooksFromBox();
                        } // when assitant has no books in hand and there are people waiting in queues, take books from box
                        else if (BooksPossessed.size() == 0 && priorityType.size() != 0) {
                            try {
                                Thread.sleep(10 * Main.TickTimeSize);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            BooksPossessed = takePriorityBooksFromBox(priorityType);
                        }
                        System.out.println("<"+Main.tickCount+">"+"<"+threadId+">"+name+" collected 10 books from the box, the books are: " + BooksPossessed);
                    } finally {

                    }
                    
                    try {
                        Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize); // sleep for 10 ticks
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // release the semaphore so another assistant can access this
                assistant.release(); 

            }
            // as long as books in hands does not equal 0, so assistant possesses books
            if (BooksPossessed.size() != 0) {
                synchronized (lock) { 
                    // create an iterator which iterates through the books possessed
                    Iterator<Books> iterator = BooksPossessed.iterator();
                    // if the BooksPossessed has a fiction book
                    if (BooksPossessed.toString().contains("Fiction")) {
                        // while there is still a book in the iterator list 
                        while (iterator.hasNext()) { 
                            book = iterator.next();
                            //if the book is a fiction book
                            if (book.toString().equals("Fiction")) {
                                // stack the fiction book onto the shelf 
                                Shelve.AddBooksToShelves(book); 
                                // remove the book from BooksPossessed
                                iterator.remove(); 
                                // increment a book counter to count how many fiction books were stacked
                                booksCounter++; 
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                             // stay at the Fiction shelf until all of  the Fiction books are stacked
                            if (!iterator.hasNext()) {
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking FICTION section with " + booksCounter + " books");
                                // reset counter to 0 it can be reused
                                booksCounter = 0; 
                                try {
                                    // sleep for one tick for every book still possessed + 10 ticks
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize); 
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    // operates the same as above
                    if (BooksPossessed.toString().contains("Poetry")) {
                        iterator = BooksPossessed.iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Poetry")) {
                                Shelve.AddBooksToShelves(book);
                                iterator.remove(); 
                                booksCounter++;
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) { 
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking Poetry section with " + booksCounter + " books");
                                booksCounter = 0;
                                try {
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    // operates the same as fiction shelf
                    if (BooksPossessed.toString().contains("Fantasy")) {
                        iterator = BooksPossessed.iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Fantasy")) {
                                Shelve.AddBooksToShelves(book);
                                iterator.remove();
                                booksCounter++;
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) {
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking FANTASY section with " + booksCounter + " books");
                                booksCounter = 0;
                                try {
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    // operates the same as fiction shelf
                    if (BooksPossessed.toString().contains("Horror")) {
                        iterator = BooksPossessed.iterator();

                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Horror")) {
                                Shelve.AddBooksToShelves(book);
                                iterator.remove();
                                booksCounter++;
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) {
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking HORROR section with " + booksCounter + " books");
                                booksCounter = 0;
                                try {
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    // operates the same as above 
                    if (BooksPossessed.toString().contains("History")) {
                        iterator = BooksPossessed.iterator();

                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("History")) {
                                Shelve.AddBooksToShelves(book);
                                iterator.remove();
                                booksCounter++;
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) {
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking History section with " + booksCounter + " books");
                                booksCounter = 0;
                                try {
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    // operates the same as above 
                    if (BooksPossessed.toString().contains("Romance")) {
                        iterator = BooksPossessed.iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            if (book.toString().equals("Romance")) {
                                Shelve.AddBooksToShelves(book);
                                iterator.remove();
                                booksCounter++;
                                try {
                                    Thread.sleep(1 * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!iterator.hasNext()) {
                                System.out.println("<" + Main.tickCount + ">" + "<" + threadId + ">" + name + " began stocking Romance section with " + booksCounter + " books");
                                booksCounter = 0;
                                try {
                                    Thread.sleep((BooksPossessed.size() + 10) * Main.TickTimeSize);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            // if assistantsTicks modulo the random number between 200-300
            if(assistantTicks % randomNum== 0)
            {
                try {
                    // semaphore acquire so only one assitant can take a break at a time
                    assistantBreaks.acquire(); 
                    System.out.println("<"+Main.tickCount+">"+"<"+threadId+">"+ name + " is on their break.");
                    // sleep for 150 ticks
                    Thread.sleep(150 * Main.TickTimeSize);
                    // generate another random number between 200-300 and add the current assistant ticks to it
                } catch (InterruptedException e) {
                    randomNum= rand.nextInt(101) + 200 + assistantTicks; 
                    e.printStackTrace();
                }
                finally{
                    // release the sempahore so another assistant can take a break
                    assistantBreaks.release(); 
                    System.out.println("<"+Main.tickCount+">"+"<"+threadId+">"+ name + " is back from their break.");

                }
            }
        }
    }

    public static void main(String[] args) {
    }
}