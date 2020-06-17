package com.code.constants;

public enum Constant_Offert_Type {


   ALCOHOLIC_DRINK, NON_ALCOHOLIC_DRINK, FOOD;

   public String returnAlcoholicdrink(){
      return "ALCOHOLIC_DRINK";
   }
   public String returnNonAlcoholicdrink(){
      return "NON_ALCOHOLIC_DRINK";
   }
   public String returnFood(){
      return "FOOD";
   }

   public double returnAlcoholicdrinkTax(){
      return 1.21;
   }
   public double returnNonAlcoholicdrinkTax(){
      return 1.2;
   }
   public double returnFoodTax(){
      return 1.18;
   }


}
