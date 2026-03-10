package id.ac.ui.cs.advprog.eshop.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private List<Product> products;

    private static final String DEFAULT_ORDER_ID = "13652556-012a-4c07-b546-54eb1396d79b";
    private static final String DEFAULT_AUTHOR = "Safira Sudrajat";

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f23db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);
    }

    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(DEFAULT_ORDER_ID,
                    this.products, 1708560000L, DEFAULT_AUTHOR);
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order(DEFAULT_ORDER_ID,
                this.products, 1708560000L, DEFAULT_AUTHOR);

        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals(DEFAULT_ORDER_ID, order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals(DEFAULT_AUTHOR, order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order(DEFAULT_ORDER_ID,
                this.products, 1708560000L, DEFAULT_AUTHOR, "SUCCESS");
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(DEFAULT_ORDER_ID,
                    this.products, 1708560000L, DEFAULT_AUTHOR, "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order(DEFAULT_ORDER_ID,
                this.products, 1708560000L, DEFAULT_AUTHOR);
        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order(DEFAULT_ORDER_ID,
                this.products, 1708560000L, DEFAULT_AUTHOR);
        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }
}