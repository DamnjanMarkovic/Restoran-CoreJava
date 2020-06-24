package com.comtrade.systemOperation.order;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.io.IOException;
import java.sql.SQLException;

public class TransferOrders extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        int idOrder = (int) transferClass.getRequest();
        int idTableNew = Integer.parseInt(transferClass.getSpecialMessage());
        Broker broker = new Broker();
        broker.transferOrder(idOrder, idTableNew);



    }
}
