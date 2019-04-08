import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private final ShoppingCart cart = new ShoppingCart();

    private final Product first = new Product("stapler", 1.0);
    private final Product second = new Product("banana", .49);

    // aneesh saripalli
    @Test
    void testGetBalance() {
        assertEquals(0.0, cart.getBalance());

        cart.addItem(first);
        assertEquals(1.0, cart.getBalance());
    
        cart.addItem(second);
        assertEquals(1.49, cart.getBalance());
    }

    // aneesh saripalli
    @Test
    void testAddItem() {
        assertEquals(0, cart.getItemCount());

        cart.addItem(first);
        assertEquals(1, cart.getItemCount());

        cart.addItem(second);
        assertEquals(2, cart.getItemCount());

        cart.empty();
    }

    // italo zevallos
    @Test
    void testRemoveItem() {
        cart.addItem(first);
        int numItems = cart.getItemCount();
        try{
            cart.removeItem(first);
        }
        catch(Exception e){
            fail();
        }
        assertEquals(numItems-1, cart.getItemCount());
    }

    // shannen barrameda
    @Test
    void testGetItemCount() {
       assertEquals(0, cart.getItemCount());
    }

    // shannen barrameda
    @Test
    void testEmpty() {
        cart.empty();
        assertEquals(0, cart.getItemCount());
    }
}
