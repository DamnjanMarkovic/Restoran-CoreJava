package com.comtrade.systemOperation.order;

import com.code.domain.Order;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.sql.SQLException;
import java.util.List;

public class PostOrderAndReturnIDsService extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        List<Order> listOrders = (List<Order>) transferClass.getRequest();
        Broker broker = new Broker();

        transferClass.setResponse(broker.postOffersAndReturnIDs(listOrders));
        int idRestaurant = Integer.parseInt(transferClass.getMessage());
        broker.reduceNumberOfUsedIngredients(listOrders, idRestaurant);



    }
}
