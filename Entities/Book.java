package Entities;
import java.util.ArrayList;
import java.util.Random;

public class Book {
    String Section;

    public void Section(){
        System.out.println("Book Section is: " + Section);
    }

    public void setSection(){
        Section = getSection();
    }


    public static String getSection() {
        Random rand = new Random();
        ArrayList<String> sectionNameList = new ArrayList<String>();
        sectionNameList.add("Horror");
        sectionNameList.add("Fiction");
        sectionNameList.add("Poetry");
        sectionNameList.add("History");
        sectionNameList.add("Sci-Fi");
        sectionNameList.add("Fantasy");

        int upperRange = sectionNameList.size();
        int i = rand.nextInt(upperRange);

        String BookSection = sectionNameList.get(i);
        return BookSection;
    }



    public static void Main(){
        Book book = new Book();
        book.setSection();
    }
}