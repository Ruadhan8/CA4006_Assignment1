import java.util.*;
import java.util.Random;

public class Book {
    String Category;

    public void Category() {
        System.out.println("Book Category is : " + Category);
    }

    public void setCategory() {
        Category = GiveBookACategorie();
    }

    @Override
    public String toString() {
        return "" + Category;
    }

    public static String GiveBookACategorie() {
        List<String> ListOfCategories = new ArrayList<String>();
        ListOfCategories.add("Fiction");
        ListOfCategories.add("Crime");
        ListOfCategories.add("Fantasy");
        ListOfCategories.add("Romance");
        ListOfCategories.add("Horror");
        ListOfCategories.add("Sport");
        int UpperRange = ListOfCategories.size();

        Random rand = new Random();
        int Index = rand.nextInt(UpperRange);

        String BookCategory = ListOfCategories.get(Index);

        return BookCategory;
    }

    public static Book GenerateBook() {
        Book book = new Book();
        book.setCategory();

        return book;
    }
    

    public static void main(String[] args) {
        Book book = new Book();
        book.setCategory();
    }
}