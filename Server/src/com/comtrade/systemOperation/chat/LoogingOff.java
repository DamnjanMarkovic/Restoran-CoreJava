package com.comtrade.systemOperation.chat;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoogingOff extends GenericSystemOperation {
    public LoogingOff(ClientThread clientThread) {
        super();
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        int loggedInID = Integer.parseInt(transferClass.getSpecialMessage());


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Broker broker = new Broker();
        broker.logOffUserWithID(timestamp, loggedInID);
    }
}
