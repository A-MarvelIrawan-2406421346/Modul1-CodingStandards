package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String STATUS_FAILED = "FAILED";

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        paymentData.put("orderId", order.getId());
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.save(payment);

        updateOrderStatus(payment.getPaymentData().get("orderId"), status);

        return payment;
    }

    private void updateOrderStatus(String orderId, String paymentStatus) {
        if (orderId != null) {
            Order order = orderRepository.findById(orderId);
            if (order != null) {
                if (STATUS_SUCCESS.equals(paymentStatus)) {
                    order.setStatus(STATUS_SUCCESS);
                } else if (STATUS_REJECTED.equals(paymentStatus)) {
                    order.setStatus(STATUS_FAILED);
                }
                orderRepository.save(order);
            }
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}