package Entities;
import java.util.ArrayList;
import Entities.Book;

/**
 * A section is the class that represents a bookshelf..
 */
public class Section {
    private String name;
    private ArrayList<Book> booksList = new ArrayList<Book>();

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getNbCurrentBook() {
        return this.booksList.size();
    }

    public int addBook(Book newBook) {
        if (booksList.size() + 1 <= 100) {
            this.booksList.add(newBook);
            return 0;
        }
        return 1;
    }
    

    public Book takeBook() {
        if (booksList.size() <= 0) {
            Book bookTarget = this.booksList.get(0);
            this.booksList.remove(0);
            return bookTarget;
        }
        return null;
    }
}