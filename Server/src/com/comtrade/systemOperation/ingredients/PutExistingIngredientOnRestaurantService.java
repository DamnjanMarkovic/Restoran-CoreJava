package com.comtrade.systemOperation.ingredients;

import com.code.domain.AvailableIngredients;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;

public class PutExistingIngredientOnRestaurantService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {

        AvailableIngredients availableIngredients  = (AvailableIngredients) transferClass.getRequest();
        Broker broker = new Broker();
        broker.save(availableIngredients);


    }
}
