package com.comtrade.systemOperation.restaurantOffer;

import com.code.domain.AvailableIngredients;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteRestaurantMenuService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        int idMenuOffer = (int) transferClass.getRequest();
        Broker broker = new Broker();
        broker.deleteMenu(idMenuOffer);



    }
}
