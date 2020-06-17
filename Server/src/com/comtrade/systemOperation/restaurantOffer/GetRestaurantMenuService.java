package com.comtrade.systemOperation.restaurantOffer;

import com.code.domain.Offer;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.sql.SQLException;

public class GetRestaurantMenuService extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        Offer offer = (Offer) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.get(offer));
    }
}
