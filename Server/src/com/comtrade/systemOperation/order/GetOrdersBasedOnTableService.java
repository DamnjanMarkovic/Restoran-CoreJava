package com.comtrade.systemOperation.order;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class GetOrdersBasedOnTableService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        int idTable = (int) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.getOrdersBasedOnTable(idTable));


    }
}
