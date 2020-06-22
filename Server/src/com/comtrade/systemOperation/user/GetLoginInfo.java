package com.comtrade.systemOperation.user;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class GetLoginInfo extends GenericSystemOperation {
    public GetLoginInfo(ClientThread clientThread) {
        super();
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        String userFirstName = (String) transferClass.getMessage();
        Broker broker = new Broker();
        transferClass.setResponse(broker.getLoggingUserList(userFirstName));


    }
}
