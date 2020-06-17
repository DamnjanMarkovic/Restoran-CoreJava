package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.ingredients.*;


public class ControlerIngredients implements com.comtrade.controlerBL.CommandBase {


    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case GET_INGREDIENTS_BASED_ON_USER:
                genericSystemOperation = new GetIngredientService();
                break;
            case POST_INGREDIENTS:
                genericSystemOperation = new SaveIngredientService();
                break;
            case PUT_INGREDIENTS:
                genericSystemOperation = new UpdateIngredientService();
                break;
            case GET_INGREDIENTS_ON_RESTAURANT:
                genericSystemOperation = new GetIngredientOnRestaurantService();
                break;
            case PUT_EXISTING_INGREDIENTS:
                genericSystemOperation = new PutExistingIngredientOnRestaurantService();
                break;
            case DELETE:
                genericSystemOperation = new DeleteIngredientOnRestaurantService();
                break;
            case GET_INGREDIENTS_MISSING:
                genericSystemOperation = new GetIngredientsMissingService();
                break;
            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
