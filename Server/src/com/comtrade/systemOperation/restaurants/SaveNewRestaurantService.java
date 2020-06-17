package com.comtrade.systemOperation.restaurants;

import com.code.domain.Restaurant;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class SaveNewRestaurantService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        Restaurant restaurant = (Restaurant) transferClass.getRequest();
        int idUser = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        broker.save(restaurant);

        int idRestaurant = broker.returnID(restaurant);

        broker.connectRestaurantAndUser(idRestaurant, idUser);
        broker.createTables(idRestaurant);


    }
}
