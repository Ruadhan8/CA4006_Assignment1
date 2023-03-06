
import java.util.Random;
import java.util.*;


public class Book {
    String Section;

    public void Section(){
        System.out.println("Book Section is: " + Section);
    }

    public void setSection(){
        Section = getSection();
    }


    public static String getSection() {
        List<String> sectionNameList = new ArrayList<String>();
        sectionNameList.add("Horror");
        sectionNameList.add("Fiction");
        sectionNameList.add("Poetry");
        sectionNameList.add("History");
        sectionNameList.add("Sport");
        sectionNameList.add("Fantasy");

        Random rand = new Random();
        int upperRange = sectionNameList.size();
        int i = rand.nextInt(upperRange);

        String BookSection = sectionNameList.get(i);
        return BookSection;
    }



    public static void main(String[] args){
        Book book = new Book();
        book.setSection();
    }
}