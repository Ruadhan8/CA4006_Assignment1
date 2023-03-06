import java.util.*;

public class Section {
    static int capacity = 20;
    static List<Book> FictionShelf = new ArrayList<Book>();
    static List<Book> HorrorShelf = new ArrayList<Book>();
    static List<Book> PoetryShelf = new ArrayList<Book>();
    static List<Book> FantasyShelf = new ArrayList<Book>();
    static List<Book> HistoryShelf = new ArrayList<Book>();
    static List<Book> SportShelf = new ArrayList<Book>();


    public static void AddBookToSections(Assistant assistant) {
        for (Book book : assistant.getBook()) {
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
            } else if (book.toString().equals("Poetry")) {
                if (PoetryShelf.size() < capacity) {
                    PoetryShelf.add(book);
                }
            } else if (book.toString().equals("Fantasy")) {
                if (FantasyShelf.size() < capacity) {
                    FantasyShelf.add(book);
                }
            } else if (book.toString().equals("Sport")) {
                if (SportShelf.size() < capacity) {
                    SportShelf.add(book);
                }
            } else if (book.toString().equals("History")) {
                if (HistoryShelf.size() < capacity) {
                    HistoryShelf.add(book);
                }
            }
        }
        // System.out.println(FictionShelf);
        // System.out.println(HorrorShelf);
        // System.out.println(SportShelf);
        // System.out.println(FantasyShelf);
        // System.out.println(HistoryShelf);
        // System.out.println(PoetryShelf);
    }
    
    public static void main(String [] args){
        Assistant assistant = new Assistant();
        assistant.main(args);
        System.out.println(assistant);
        AddBookToSections(assistant);
        // System.out.println(FictionShelf);
    }
}