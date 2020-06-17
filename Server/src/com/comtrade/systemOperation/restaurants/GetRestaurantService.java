package com.comtrade.systemOperation.restaurants;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;

public class GetRestaurantService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        User user = (User) transferClass.getRequest();
        Broker broker = new Broker();
        //transferClass.setResponse(broker.getRestaurant(user));
    }
}
