import java.util.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

// Our Main class is used to run the overall project and all the classes
public class Main {
    public static int TicksPerDay = 1000;
    public static int TickTimeSize = 1000;

    public static int tickCount = 0;
    public static Box box = new Box();
    private static List<Books> BooksPossessed = new ArrayList<Books>();
    private static int booksCounter = 0;

    private final static List<Assistant> assistants = new ArrayList<>();

    // function to ensure that shelves all have one book when the shop first opens
    public static void StartShelves() {
        List<String> CategoriesUsed = new ArrayList<String>();

        while (CategoriesUsed.size() < 6) {
            Books book = Books.GenerateBook();

            if (!CategoriesUsed.contains(book.toString())) {

                String book_category = book.toString();

                if (book_category.equals("Horror")) {
                    CategoriesUsed.add("Horror");

                    Shelve.HorrorShelf.add(book);
                }

                if (book_category.equals("Poetry")) {
                    CategoriesUsed.add("Poetry");

                    Shelve.PoetryShelf.add(book);
                }

                if (book_category.equals("Fiction")) {
                    CategoriesUsed.add("Fiction");

                    Shelve.FictionShelf.add(book);
                }

                if (book_category.equals("Fantasy")) {
                    CategoriesUsed.add("Fantasy");

                    Shelve.FantasyShelf.add(book);
                }

                if (book_category.equals("Romance")) {
                    CategoriesUsed.add("Romance");

                    Shelve.RomanceShelf.add(book);
                }

                if (book_category.equals("History")) {
                    CategoriesUsed.add("History");

                    Shelve.HistoryShelf.add(book);
                }
            }
        }

        System.out.println("Each Shelf contains 1 book");

    }

    // our main function which contains the runner code for the project
    public static void main(String[] args) {
        // introducing popup GUI boxes
        JFrame jFrame = new JFrame();
        String getAssistants = JOptionPane.showInputDialog(jFrame, "Select a number of assistants to work in the bookstore.");
            
        JOptionPane.showMessageDialog(jFrame, "You Have Selected " + getAssistants + " Assistants.");

        String getTicks = JOptionPane.showInputDialog(jFrame, "Select a tick to second mapping. (1000 is one tick a second. A larger value makes the bookstore run slower and a smaller value increases the speed of the bookstore.)");
            
        JOptionPane.showMessageDialog(jFrame, "You Have Selected This Mapping: " + getTicks);

        int Ticks = Integer.parseInt(getTicks);
        int Assistants_Amount = Integer.parseInt(getAssistants);
    
        if (Assistants_Amount == 0) {
            System.out.println("You didnt enter a valid number by default there will only be one assistant.");
            Assistants_Amount = 1;
        }
        if (Ticks == 0) 
        {
            System.out.println("You Didnt enter a valid number, ticks will default to one every second");
        }
        else {
            TickTimeSize = Ticks;
        }
        
        StartShelves();

        // adding the threads to a list of threads
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Assistants_Amount; i++) {
            assistants.add(new Assistant("Assistant #" + i, BooksPossessed, booksCounter));
            threads.add(new Thread(new Assistant("Assistant #" + i, BooksPossessed, booksCounter)));
        }
        threads.add(new Thread(new Customer()));
        threads.add(new Thread(new Tick(box)));

        // starting all the threads in the list
        for (Thread thread : threads) {
            thread.start();
        }

    }
}