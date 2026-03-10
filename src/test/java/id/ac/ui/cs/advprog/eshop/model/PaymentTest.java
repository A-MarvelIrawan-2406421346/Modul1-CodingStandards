package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private static final String KEY_VOUCHER_CODE = "voucherCode";
    private static final String METHOD_VOUCHER = "VOUCHER";
    private static final String STATUS_REJECTED = "REJECTED";

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        // 16 chars, starts with ESHOP, exactly 8 numerics
        paymentData.put(KEY_VOUCHER_CODE, "ESHOP1234ABC5678");
        Payment payment = new Payment("1", METHOD_VOUCHER, paymentData);

        assertEquals("1", payment.getId());
        assertEquals(METHOD_VOUCHER, payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentVoucherRejectedNot16Chars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, "ESHOP123");
        Payment payment = new Payment("2", METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNotStartWithEshop() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, "DISCO1234ABC5678");
        Payment payment = new Payment("3", METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherRejectedNot8Numerics() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_VOUCHER_CODE, "ESHOP12ABCDEFGHX");
        Payment payment = new Payment("4", METHOD_VOUCHER, paymentData);

        assertEquals(STATUS_REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF12345");
        Payment payment = new Payment("5", "BANK_TRANSFER", paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedBankNameEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF12345");
        Payment payment = new Payment("6", "BANK_TRANSFER", paymentData);

        assertEquals(STATUS_REJECTED, payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferRejectedReferenceCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("7", "BANK_TRANSFER", paymentData);

        assertEquals(STATUS_REJECTED, payment.getStatus());
    }
}