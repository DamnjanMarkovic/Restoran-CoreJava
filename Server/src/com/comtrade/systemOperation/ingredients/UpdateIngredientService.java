package com.comtrade.systemOperation.ingredients;

import com.code.domain.Ingredient;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;

public class UpdateIngredientService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        Ingredient ingredient = (Ingredient) transferClass.getRequest();
        int id_restaurant = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        broker.updateIngredients(ingredient, id_restaurant);

     }
}
