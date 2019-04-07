import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private final ShoppingCart cart = new ShoppingCart();
    @Test
    void testGetBalance() {
    }

    @Test
    void testAddItem() {

    }

    @Test
    void testRemoveItem() {
    }

    @Test
    void testGetItemCount() {
       assertEquals(0, cart.getItemCount());
    }

    @Test
    void testEmpty() {
        cart.empty();
        assertEquals(0, cart.getItemCount());
    }
}