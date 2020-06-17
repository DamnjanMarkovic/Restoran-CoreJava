package com.code.constants;

public enum Constants_Reduction {

    CASH, CREDIT_CARD, CHECK_PAYMENT, REGULAR_CUSTOMER, SPECIAL_DISCOUNT, NO_REDUCTION;



    public String returnCash(){
        return "CASH";
    }
    public String returnCreditCard(){
        return "CREDIT_CARD";
    }
    public String returnCheckPayent(){
        return "CHECK_PAYMENT";
    }
    public String returnRegularCustomer(){
        return "REGULAR_CUSTOMER";
    }
    public String returnNoReduction(){
        return "NO_REDUCTION";
    }




    public double reductionNoReduction(){
        return 1;
    }
    public String returnSpecialDiscount(){
        return "SPECIAL_DISCOUNT";
    }
    public double reductionForCash(){
        return 0.9;
    }
    public double reductionForCreditCard(){
        return 0.85;
    }
    public double reductionForCheck(){
        return 1;
    }
    public double reductionForRegularCustomer(){
        return 0.9;
    }
    public double reductionForSpecialDiscount(){
        return 0.9;
    }



}
