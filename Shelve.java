import java.util.*;
import java.util.Queue;

public class Shelve {
    static int capacity = 20;
    static List<Book> FictionShelf = new ArrayList<Book>();
    static List<Book> HorrorShelf = new ArrayList<Book>();
    static List<Book> SportShelf = new ArrayList<Book>();
    static List<Book> FantasyShelf = new ArrayList<Book>();
    static List<Book> RomanceShelf = new ArrayList<Book>();
    static List<Book> CrimeShelf = new ArrayList<Book>();
    static Queue<String> FictionWaitingLine = new LinkedList<>();
    static Queue<String> HorrorWaitingLine = new LinkedList<>();
    static Queue<String> SportWaitingLine = new LinkedList<>();
    static Queue<String> FantasyWaitingLine = new LinkedList<>();
    static Queue<String> RomanceWaitingLine = new LinkedList<>();
    static Queue<String> CrimeWaitingLine = new LinkedList<>();

    public static void AddBookToShelves(Book book) {
        // for (Book book : assistant.getBook()) {
        // System.out.println(book.toString().equals("Fiction"));
        // System.out.println(book);
        if (book.toString().equals("Fiction")) {
            if (FictionShelf.size() < capacity) {
                FictionShelf.add(book);
            }
        } else if (book.toString().equals("Horror")) {
            if (HorrorShelf.size() < capacity) {
                HorrorShelf.add(book);
            }
        } else if (book.toString().equals("Sport")) {
            if (SportShelf.size() < capacity) {
                SportShelf.add(book);
            }
        } else if (book.toString().equals("Fantasy")) {
            if (FantasyShelf.size() < capacity) {
                FantasyShelf.add(book);
            }
        } else if (book.toString().equals("Romance")) {
            if (RomanceShelf.size() < capacity) {
                RomanceShelf.add(book);
            }
        } else if (book.toString().equals("Crime")) {
            if (CrimeShelf.size() < capacity) {
                CrimeShelf.add(book);
            }
        }

        // return FictionShelf, SportShelf, HorrorShelf, FantasyShelf, RomanceShelf,
        // CrimeShelf;
        // System.out.println(FictionShelf);
        // System.out.println(HorrorShelf);
        // System.out.println(SportShelf);
        // System.out.println(FantasyShelf);
        // System.out.println(RomanceShelf);
        // System.out.println(CrimeShelf);
        // return RomanceShelf;
    }

    public static Queue<String> CustomerWaitingLine(Queue<String> CustomerWaitingLine, String Customer) {

        CustomerWaitingLine.add(Customer);

        return CustomerWaitingLine;
    }

    @Override
    public String toString() {
        return "";
    }

    public static void main(String[] args) {
        // Assistant assistant = new Assistant();
        // assistant.main(args);
        // System.out.println(assistant);
        // AddBookToShelves(assistant);
        // System.out.println(FictionShelf);
    }
}