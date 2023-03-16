import java.util.*;

public class Box implements Runnable{
    // Introducing a box class
    static int MaxBooks = 50;
    public static List<Books> BooksInBox = new ArrayList<Books>();
    static Box Box_1;
    static Box box = CreateNewBox();
    static Delivery delivery = new Delivery();


    public static List<Books> takeBooks() {
        List<Books> books = new ArrayList<>();
    
        synchronized (BooksInBox) {
            // Checks to see if box is empty. If so, it waits until there is books in the box
            while (BooksInBox.isEmpty()) {
                try {
                    BooksInBox.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            // Take maximum 10 books from the box
            Iterator<Books> iterator = BooksInBox.iterator();
            int count = 0;
            while (iterator.hasNext() && count < 10) {
                Books book = iterator.next();
                iterator.remove();
                books.add(book);
                count++;
            }
        }
    
        return books;
    }
    // Fills the box with books from delivery
    public List<Books> FillBox(List<Books> DeliveryList) {
        int i = 0;
            if (DeliveryList.size() != 0) 
            {
                while (i < DeliveryList.size()) 
                {
                    if(BooksInBox.size() == MaxBooks){
                        return BooksInBox;
                    }
                    Books x = DeliveryList.get(i);
                    
                    BooksInBox.add(x);
                    
                    i++;
                }
            }

        return BooksInBox;
    }
    // finds number of books in the box 
    public int size() {
        int Size = BooksInBox.size();
        return Size;
    }
    // creates new box
    public static Box CreateNewBox() {
        Box box = new Box();
        return box;
    }
    // converts BooksInBox to string
    @Override
    public String toString() {
        return ""+BooksInBox;
    }
    // creates a new box, generates delivery and fills the box with the contents of delivery
    public static void main(String[] args){
        Box box = CreateNewBox();
        Delivery Delivery = new Delivery();
        List<Books> delivery_1 = Delivery.GenerateDelivery();
        box.FillBox(delivery_1);

    }
    @Override
    public void run() {
            List<Books> DeliveredBooks = Delivery.GenerateDelivery();
            box.FillBox(new ArrayList<>(DeliveredBooks));
            DeliveredBooks.clear();
        }
                    
    }
