import java.util.*;

public class Shelve {
    static int capacity = 20;
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

    public static void AddBooksToShelves(Books book) {

        if (book.toString().equals("Fiction")) {
            if (FictionShelf.size() < capacity) {
                FictionShelf.add(book);
            }
        } else if (book.toString().equals("Horror")) {
            if (HorrorShelf.size() < capacity) {
                HorrorShelf.add(book);
            }
        } else if (book.toString().equals("Poetry")) {
            if (PoetryShelf.size() < capacity) {
                PoetryShelf.add(book);
            }
        } else if (book.toString().equals("Fantasy")) {
            if (FantasyShelf.size() < capacity) {
                FantasyShelf.add(book);
            }
        } else if (book.toString().equals("Romance")) {
            if (RomanceShelf.size() < capacity) {
                RomanceShelf.add(book);
            }
        } else if (book.toString().equals("History")) {
            if (HistoryShelf.size() < capacity) {
                HistoryShelf.add(book);
            }
        }
    }

    public static Queue<String> CustomerWaitingLine(Queue<String> CustomerWaitingLine, String Customer) {

        CustomerWaitingLine.add(Customer);

        return CustomerWaitingLine;
    }

    public static boolean LineEmpty(Queue<String> CustomerWaitingLine) {
        if (CustomerWaitingLine.size() == 0) {
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