package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveAndFindById() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("1", "VOUCHER", paymentData);

        paymentRepository.save(payment);

        Payment savedPayment = paymentRepository.findById("1");
        assertNotNull(savedPayment);
        assertEquals(payment.getId(), savedPayment.getId());
        assertEquals(payment.getMethod(), savedPayment.getMethod());
    }

    @Test
    void testFindByIdNotFound() {
        Payment savedPayment = paymentRepository.findById("999");
        assertNull(savedPayment);
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> data1 = new HashMap<>();
        data1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("1", "VOUCHER", data1);

        Map<String, String> data2 = new HashMap<>();
        data2.put("bankName", "BCA");
        data2.put("referenceCode", "REF123");
        Payment payment2 = new Payment("2", "BANK_TRANSFER", data2);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertEquals(2, allPayments.size());
    }
}