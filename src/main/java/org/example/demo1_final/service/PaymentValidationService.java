package org.example.demo1_final.service;

import org.example.demo1_final.config.BankConfig;
import org.example.demo1_final.model.PaymentRequest;
import org.example.demo1_final.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class PaymentValidationService {
    @Autowired
    private BankConfig bankConfig;
    @Autowired
    private RedisService redisService;

    private static final String PRIVATE_KEY = "ghffffffffff";

//    public PaymentResponse processRequest(PaymentRequest request) {
//        String responseId = generateResponseId();
//        String responseTime = generateResponseTime();
//        String responseChecksum = generateResponseChecksum("00", "Success", responseId, responseTime);
//
//        redisService.setData(request.getBankCode(), request.getTokenKey(), request.toString());
//
//        return new PaymentResponse("00", "Success", responseId, responseTime, responseChecksum);
//    }

    public PaymentResponse processRequest(PaymentRequest request) {
        if (isInvalid(request.getTokenKey()) ||
                isInvalid(request.getApiID()) ||
                isInvalid(request.getMobile()) ||
                isInvalid(request.getBankCode()) ||
                isInvalid(request.getAccountNo()) ||
                isInvalid(request.getPayDate()) ||
                isInvalid(request.getAdditionalData())||
                request.getDebitAmount() <= 0  ||
                isInvalid(request.getRespCode()) ||
                isInvalid(request.getRespDesc()) ||
                isInvalid(request.getTraceTransfer()) ||
                isInvalid(request.getMessageType()) ||
                isInvalid(request.getCheckSum()) ||
                isInvalid(request.getOrderCode()) ||
                isInvalid(request.getUserName()) ||
                isInvalid(request.getRealAmount()) ||
                isInvalid(request.getPromotionCode())) {

            String responseId = generateResponseId();
            String responseTime = generateResponseTime();
            String responseChecksum = generateResponseChecksum("01", "Invalid Input", responseId, responseTime);

            return new PaymentResponse("01", "Invalid Input", responseId, responseTime, responseChecksum);
        }

        String responseId = generateResponseId();
        String responseTime = generateResponseTime();
        String responseChecksum = generateResponseChecksum("00", "Success", responseId, responseTime);

        redisService.setData(request.getBankCode(), request.getTokenKey(), request.toString());

        return new PaymentResponse("00", "Success", responseId, responseTime, responseChecksum);
    }

    private boolean isInvalid(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isInvalid(PaymentRequest request) {
        return !StringUtils.hasText(request.getTokenKey()) ||
                !StringUtils.hasText(request.getApiID()) ||
                !StringUtils.hasText(request.getMobile()) ||
                !StringUtils.hasText(request.getBankCode()) ||
                !StringUtils.hasText(request.getAccountNo()) ||
                !StringUtils.hasText(request.getPayDate()) ||
                request.getDebitAmount() == null ||
                !StringUtils.hasText(request.getRespCode()) ||
                !StringUtils.hasText(request.getTraceTransfer()) ||
                !StringUtils.hasText(request.getMessageType()) ||
                !StringUtils.hasText(request.getCheckSum()) ||
                !StringUtils.hasText(request.getOrderCode()) ||
                !StringUtils.hasText(request.getUserName()) ||
                !StringUtils.hasText(request.getRealAmount()) ||
                !StringUtils.hasText(request.getPromotionCode());
    }


    private boolean isValidBankCode(String bankCode) {
        return bankConfig.getBanks().stream().anyMatch(bank -> bank.getBankCode().equals(bankCode));
    }

    private boolean isValidChecksum(PaymentRequest request) {
        String dataToHash = request.getMobile() + request.getBankCode() +
                request.getAccountNo() + request.getPayDate() +
                request.getDebitAmount() + request.getRespCode() +
                request.getTraceTransfer() + request.getMessageType() +
                PRIVATE_KEY;

        String calculatedChecksum = calculateSHA256(dataToHash);
        return calculatedChecksum.equals(request.getCheckSum());
    }

    private String generateResponseId() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String generateResponseTime() {
        return new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    }

    private String generateResponseChecksum(String code, String message, String responseId, String responseTime) {
        String dataToHash = code + message + responseId + responseTime + PRIVATE_KEY;
        return calculateSHA256(dataToHash);
    }

    private String calculateSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

