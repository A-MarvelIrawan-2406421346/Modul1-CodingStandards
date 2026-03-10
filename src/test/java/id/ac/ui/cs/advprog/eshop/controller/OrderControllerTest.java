package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("1", new ArrayList<>(), 123456L, "Safira");
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/create"));
    }

    @Test
    void testHistoryOrderPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/history"));
    }

    @Test
    void testHistoryOrderPost() throws Exception {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderService.findAllByAuthor("Safira")).thenReturn(orders);

        mockMvc.perform(post("/order/history").param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/list"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById("1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/pay"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        mockMvc.perform(post("/order/pay/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment request accepted. Payment ID will be generated."));
    }
}