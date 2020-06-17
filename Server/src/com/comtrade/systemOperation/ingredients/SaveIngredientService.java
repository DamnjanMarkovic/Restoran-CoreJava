package com.comtrade.systemOperation.ingredients;

import com.code.domain.Ingredient;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;

public class SaveIngredientService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        Ingredient ingredient = (Ingredient) transferClass.getRequest();
        int id_restaurant = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        broker.save(ingredient);


        int id_Ingredient = broker.returnID(ingredient);
        ingredient.setId_ingredient(id_Ingredient);
        broker.saveIngredients(ingredient, id_restaurant);



    }
}
