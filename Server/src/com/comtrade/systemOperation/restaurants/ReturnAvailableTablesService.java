package com.comtrade.systemOperation.restaurants;

import com.code.domain.DinningTable;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnAvailableTablesService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {


        Broker broker = new Broker();
        transferClass.setResponse(broker.returnAvailableTables());


    }
}
