import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class ProductTest {

    private Product testProduct;
    private String title;
    private double price;

    private final double MAX_PRICE = 10000.0;

    // aneesh saripalli
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

    // aneesh saripalli
    @Test
    void getTitle() {
        assertEquals(title, testProduct.getTitle());
    }

    // aneesh saripalli
    @Test
    void getPrice() {
        assertEquals(price, testProduct.getPrice());
    }

    // aneesh saripalli
    @Test
    void equals1() {
        final Product copy = new Product(testProduct.getTitle(), testProduct.getPrice());
        assertEquals(testProduct, copy);
    }
}