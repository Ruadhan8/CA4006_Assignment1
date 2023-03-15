import java.util.*;

public class Main {
    public static int TICKS_PER_DAY = 1000;
    public static int TICK_TIME_SIZE = 10;

    public static int tickCount = 0;
    public static Box box = new Box();
    private static List<Book> booksInHands = new ArrayList<Book>();
    private final static List<Assistant> assistants = new ArrayList<>();
    private static List<Book> booksToTake = new ArrayList<Book>();


    public static int MilliSecondsNeeded(int TICK_TIME_SIZE, int HowManyTicks) 
    {
        int MillisToTick = 1000 / TICK_TIME_SIZE;

        HowManyTicks = MillisToTick * HowManyTicks;

        return HowManyTicks;
    }

    public static void StartShelves() {
        List<String> CategoriesUsed = new ArrayList<String>();

        while (CategoriesUsed.size() < 6) {
            Book book = Book.GenerateBook();

            if (!CategoriesUsed.contains(book.toString())) {

                String book_category = book.toString();

                if (book_category.equals("Horror")) {
                    CategoriesUsed.add("Horror");

                    Shelve.HorrorShelf.add(book);

                    System.out.println(Shelve.HorrorShelf);
                }

                if (book_category.equals("Sport")) {
                    CategoriesUsed.add("Sport");

                    Shelve.SportShelf.add(book);

                    System.out.println(Shelve.SportShelf);
                }

                if (book_category.equals("Fiction")) {
                    CategoriesUsed.add("Fiction");

                    Shelve.FictionShelf.add(book);

                    System.out.println(Shelve.FictionShelf);
                }

                if (book_category.equals("Fantasy")) {
                    CategoriesUsed.add("Fantasy");

                    Shelve.FantasyShelf.add(book);

                    System.out.println(Shelve.FantasyShelf);
                }

                if (book_category.equals("Romance")) {
                    CategoriesUsed.add("Romance");

                    Shelve.RomanceShelf.add(book);

                    System.out.println(Shelve.RomanceShelf);
                }

                if (book_category.equals("Crime")) {
                    CategoriesUsed.add("Crime");

                    Shelve.CrimeShelf.add(book);

                    System.out.println(Shelve.CrimeShelf);
                }
            }
        }

        System.out.println("Each Shelf contains 1 book");

    }

    public static void main(String[] args) {
        StartShelves();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            assistants.add(new Assistant("Assistant-" + i, booksInHands));
            threads.add(new Thread(new Assistant("Assistant-" + i, booksInHands)));
        }
        threads.add(new Thread(new Customer()));
        threads.add(new Thread(new Tick(box)));

        for (Thread thread : threads) {
            // System.out.println(thread);
            thread.start();
        }
    }
}