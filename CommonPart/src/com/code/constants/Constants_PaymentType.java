package com.code.constants;

public enum Constants_PaymentType {

    CASH, CREDIT_CARD, CHECK_PAYMENT, ALL_PAYMENT_TYPE;

    public String returnCash(){
        return "CASH";
    }
    public String returnCreditCard(){
        return "CREDIT_CARD";
    }
    public String returnCheckPayent(){
        return "CHECK_PAYMENT";
    }
    public String returnAllPaymentType(){
        return "ALL_PAYMENT_TYPE";
    }

}
