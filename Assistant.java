import java.util.*;

public class Assistant {
    static List<Book> booksToTake = new ArrayList<Book>();
    

    public synchronized static List<Book> takeBookFromBox(Box box) {
        List<Book> books = box.getBooks();
        if (!books.isEmpty()) {
            for (Book book : books) {
                booksToTake.add(book);
            }
            books.removeAll(books);
            return booksToTake;
        } else {
            return null;
        }
    }

    public int size() {
        int Size = booksToTake.size();

        System.out.print(Size);

        return Size;
    }
    

    @Override
    public String toString() {
        return ""+booksToTake;
    }
    public static void main(String [] args){
        Box books = new Box();
        books.main(args);
        // System.out.println(books.toString());
        takeBookFromBox(books);
        // System.out.println(books.toString());

        // System.out.println(booksToTake.toString());
        // System.out.println(takeBookFromBox(books));
    }

    public List<Book> getBook() {
        return booksToTake;
    }
}