package com.comtrade.systemOperation.user;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmUsersServis extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        String userName = (String) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.confirmUsername(userName));
    }
}
