package com.comtrade.systemOperation.order;

import com.code.domain.Order;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RemoveOrdersService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        List<Order> listOrders = (List<Order>) transferClass.getRequest();
        Broker broker = new Broker();
        broker.removeOrders(listOrders);



    }
}
