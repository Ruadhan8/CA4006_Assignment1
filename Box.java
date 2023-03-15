import java.util.*;
public class Box implements Runnable{
    static int space = 50;
    public static List<Book> BookInBox = new ArrayList<Book>();
    static Box Box_1;
    static Box box = CreateNewBox();
    static Delivery delivery = new Delivery();


    // Need Function to print contents of Box
    public static List<Book> getBook() {
        List<Book> books = new ArrayList<>();
    
        synchronized (BookInBox) {
            // Wait until the box has books
            while (BookInBox.isEmpty()) {
                try {
                    BookInBox.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    
            // Take up to 10 books from the box
            Iterator<Book> iterator = BookInBox.iterator();
            int count = 0;
            while (iterator.hasNext() && count < 10) {
                Book book = iterator.next();
                iterator.remove();
                books.add(book);
                count++;
            }
        }
    
        return books;
    }

    public List<Book> FillBox(List<Book> DeliveryList) {
        int i = 0;
            if (DeliveryList.size() != 0) 
            {
                while (i < DeliveryList.size()) 
                {
                    if(BookInBox.size() == space){
                        return BookInBox;
                    }
                    Book x = DeliveryList.get(i);
                    
                    BookInBox.add(x);
                    
                    i++;
                }
            }

        return BookInBox;
    }

    public int size() {
        int Size = BookInBox.size();

        // System.out.print(Size);

        return Size;
    }

    public static Box CreateNewBox() {
        Box box = new Box();
        // System.out.println(box.getClass());
        return box;
    }

    @Override
    public String toString() {
        return ""+BookInBox;
    }

    public static void main(String[] args){
        Box box = CreateNewBox();
        Delivery Delivery = new Delivery();
        List<Book> delivery_1 = Delivery.GenerateDelivery();
        box.FillBox(delivery_1);

    }
    @Override
    public void run() {
            List<Book> deliveredContents1 = delivery.GenerateDelivery();
            box.FillBox(new ArrayList<>(deliveredContents1));
            deliveredContents1.clear();
        }
                    
    }