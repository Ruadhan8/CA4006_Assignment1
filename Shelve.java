import java.util.*;

public class Shelve {
    static int MaxBooksOnShelf = 20;
    static List<Books> FictionShelf = new ArrayList<Books>();
    static List<Books> HorrorShelf = new ArrayList<Books>();
    static List<Books> PoetryShelf = new ArrayList<Books>();
    static List<Books> FantasyShelf = new ArrayList<Books>();
    static List<Books> RomanceShelf = new ArrayList<Books>();
    static List<Books> HistoryShelf = new ArrayList<Books>();
    static Queue<String> FictionWaitingLine = new LinkedList<>();
    static Queue<String> HorrorWaitingLine = new LinkedList<>();
    static Queue<String> PoetryWaitingLine = new LinkedList<>();
    static Queue<String> FantasyWaitingLine = new LinkedList<>();
    static Queue<String> RomanceWaitingLine = new LinkedList<>();
    static Queue<String> HistoryWaitingLine = new LinkedList<>();


    // adds books to shelves
    public static void AddBooksToShelves(Books book) {

        if (book.toString().equals("Fiction")) {
            if (FictionShelf.size() < MaxBooksOnShelf) {
                FictionShelf.add(book);
            }
        } else if (book.toString().equals("Horror")) {
            if (HorrorShelf.size() < MaxBooksOnShelf) {
                HorrorShelf.add(book);
            }
        } else if (book.toString().equals("Sport")) {
            if (PoetryShelf.size() < MaxBooksOnShelf) {
                PoetryShelf.add(book);
            }
        } else if (book.toString().equals("Fantasy")) {
            if (FantasyShelf.size() < MaxBooksOnShelf) {
                FantasyShelf.add(book);
            }
        } else if (book.toString().equals("Romance")) {
            if (RomanceShelf.size() < MaxBooksOnShelf) {
                RomanceShelf.add(book);
            }
        } else if (book.toString().equals("Crime")) {
            if (HistoryShelf.size() < MaxBooksOnShelf) {
                HistoryShelf.add(book);
            }
        }
    }

    public static Queue<String> CustomerWaitingQueue(Queue<String> CustomerWaitingQueue, String Customer) {

        CustomerWaitingQueue.add(Customer);
        return CustomerWaitingQueue;
    }

    public static boolean LineEmpty(Queue<String> CustomerWaitingQueue) {
        if (CustomerWaitingQueue.size() == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "";
    }

    public static void main(String[] args) {

    }
}