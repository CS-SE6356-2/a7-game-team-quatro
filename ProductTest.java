import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class ProductTest {

    private static Product testProduct;
    private static String title;
    private static double price;

    private static final double MAX_PRICE = 10000.0;

    @Before
    public void initialize(){
        Random rand = new Random();
        rand.setSeed(System.nanoTime());

        for(int i = 0; i < rand.nextInt() % 100; ++i)
        {
            char randChar = (char)(rand.nextInt() % 26 + 'a');
            title += randChar;
        }

        price = rand.nextDouble() * MAX_PRICE;

        testProduct = new Product(title, price);
    }

    @Test
    void getTitle() {
        assertEquals(title, testProduct.getTitle());
    }

    @Test
    void getPrice() {
        assertEquals(price, testProduct.getPrice());
    }

    @Test
    void equals1() {
        final Product copy = new Product(testProduct.getTitle(), testProduct.getPrice());
        assertEquals(testProduct, copy);
    }
}