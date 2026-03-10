package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        List<id.ac.ui.cs.advprog.eshop.model.Product> products = new ArrayList<>();
        id.ac.ui.cs.advprog.eshop.model.Product product = new id.ac.ui.cs.advprog.eshop.model.Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira");
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/createOrder"));
    }

    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderHistory"));
    }

    @Test
    void testHistoryOrderPost() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findAllByAuthor("Safira")).thenReturn(orders);

        mockMvc.perform(post("/order/history").param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderList"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById("1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/payOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        mockMvc.perform(post("/order/pay/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment request accepted. Payment ID will be generated."));
    }
}