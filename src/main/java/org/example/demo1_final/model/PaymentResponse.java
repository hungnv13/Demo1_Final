package org.example.demo1_final.model;

public class PaymentResponse {
    private String code;
    private String message;
    private String responseId;
    private String responseTime;
    private String checkSum;

    public PaymentResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public PaymentResponse(String code, String message, String responseId, String responseTime, String checkSum) {
        this.code = code;
        this.message = message;
        this.responseId = responseId;
        this.responseTime = responseTime;
        this.checkSum = checkSum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}

