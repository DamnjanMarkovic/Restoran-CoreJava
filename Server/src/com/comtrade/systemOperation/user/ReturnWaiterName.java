package com.comtrade.systemOperation.user;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnWaiterName extends GenericSystemOperation {
    public ReturnWaiterName(ClientThread clientThread) {
        super();
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        int idSender = (Integer) transferClass.getRequest();
        Broker broker = new Broker();
        String managerName = broker.getManagerNameBasedOnWaiterID(idSender);
        transferClass.setResponse(managerName);

    }
}
