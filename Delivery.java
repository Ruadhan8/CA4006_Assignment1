import java.util.*;
import java.security.SecureRandom;

public class Delivery {
    static Object Book = new Book();
    static int size = 10;
    public static List<Book> DeliveryList = new ArrayList<Book>();
    
    public static List<Book> GenerateDelivery(){
        int i = 0;

        while (i < size) {
            Book book  = new Book();

            book.setSection();

            DeliveryList.add(book);

            i++;
        }
        System.out.print(DeliveryList);
        return DeliveryList;

    }

    public int size() {
        int Size = DeliveryList.size();
        System.out.print(Size);
        return Size;
    }

    public static int NextDeliveryTime() {
        SecureRandom rand = new SecureRandom();

        int TicksTillDelivery = rand.nextInt(100);

        if(TicksTillDelivery == 0){
            TicksTillDelivery = 100;

        }
        return TicksTillDelivery;

    }

    public static void main(String[] args){
        System.out.print(GenerateDelivery());
    }
}