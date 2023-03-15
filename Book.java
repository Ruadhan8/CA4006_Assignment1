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
        sectionNameList.add("Fiction");
        sectionNameList.add("Crime");
        sectionNameList.add("Fantasy");
        sectionNameList.add("Romance");
        sectionNameList.add("Horror");
        sectionNameList.add("Sport");

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