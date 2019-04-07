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
       assertEquals(cart.getItemCount(), 0);
    }

    @Test
    void testEmpty() {
        cart.empty();
        assertEquals(cart.getItemCount(), 0);
    }
}