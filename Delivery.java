import java.util.*;
// import java.security.SecureRandom;

public class Delivery {
    static Object Books = new Books();
    static int size = 10;
    public static List<Books> DeliveryList = new ArrayList<Books>();
    static int DeliveryCount = 0;

    // creates delivery and fills DeliveryList with books
    public static List<Books> GenerateDelivery() {
        int i = 0;

        while (i < size) {
            Books book = new Books();

            book.setCategory();

            DeliveryList.add(book);

            i++;
        }
        return DeliveryList;
    }
    // finds how many books are in DeliveryList
    public int size() {
        int Size = DeliveryList.size();
        return Size;
    }

    // converts DeliveryList to string
    @Override
    public String toString() {
        return "" + DeliveryList;
    }

    // Calculates the probability of delivery as 1/100 
    public static String NextDeliveryTime() {
        List<String> ProbabilityOfDelivery = new ArrayList<String>();
        var i = 0;
        while (i < 100) {
            if (i != 99) {
                ProbabilityOfDelivery.add("False");
            } else {
                ProbabilityOfDelivery.add("True");
            }
            i++;
        }

        int UpperRange = ProbabilityOfDelivery.size();

        Random rand = new Random();
        int Index = rand.nextInt(UpperRange);

        String IsDelivery = ProbabilityOfDelivery.get(Index);

        return IsDelivery;
    }

    public static void main(String[] args) {
        String isDelivery = NextDeliveryTime();
        if (isDelivery == "True") {
            System.out.println(GenerateDelivery());
            DeliveryCount++;
        }

    }

}