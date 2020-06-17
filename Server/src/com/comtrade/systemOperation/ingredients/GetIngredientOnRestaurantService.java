package com.comtrade.systemOperation.ingredients;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;

public class GetIngredientOnRestaurantService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        int idRestaurant = (Integer) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.getIngredientsOnRestaurant(idRestaurant));
    }
}
