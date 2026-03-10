package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private Payment payment;

    private static final String PAYMENT_ID = "pay-1";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(PAYMENT_ID, "VOUCHER", paymentData);
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetailForm"));
    }

    @Test
    void testPaymentDetailByIdPage() throws Exception {
        when(paymentService.getPayment(PAYMENT_ID)).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/" + PAYMENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminPaymentListPage() throws Exception {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentAdminList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testAdminPaymentDetailPage() throws Exception {
        when(paymentService.getPayment(PAYMENT_ID)).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/" + PAYMENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("payment/paymentAdminDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminSetPaymentStatusPost() throws Exception {
        when(paymentService.getPayment(PAYMENT_ID)).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/" + PAYMENT_ID).param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/list"));
    }
}