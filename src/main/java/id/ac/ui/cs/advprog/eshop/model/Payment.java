package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(); 
    }

    private String determineStatus() {
        if ("VOUCHER".equals(method)) {
            return validateVoucherCode(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        } else if ("BANK_TRANSFER".equals(method)) {
            return validateBankTransfer(paymentData.get("bankName"), paymentData.get("referenceCode")) ? "SUCCESS" : "REJECTED";
        }
        return "REJECTED";
    }

    private boolean validateVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }

    private boolean validateBankTransfer(String bankName, String referenceCode) {
        return bankName != null && !bankName.isEmpty() &&
                referenceCode != null && !referenceCode.isEmpty();
    }

    public void setStatus(String status) {
        this.status = status;
    }
}