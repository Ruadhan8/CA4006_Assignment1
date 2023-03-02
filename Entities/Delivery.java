package Entities;
import java.util.*;


public class Delivery {
    static Object Book = new Book();
    static int size = 10;

    public static List GenerateDelivery(){
        int i = 0;

        List<Book> DeliveryList = new ArrayList<Book>();
        while (i < size) {
            Book book  = new Book();

            book.setSection();

            DeliveryList.add(book);

            i++;
        }
        System.out.print(DeliveryList);

        for (int x = 0; x < DeliveryList.size(); x++) {
            Book test = DeliveryList.get(x);
            test.Section();
        }
        return DeliveryList;
    }

    public static void main(String[] args){
        GenerateDelivery();
    }
}