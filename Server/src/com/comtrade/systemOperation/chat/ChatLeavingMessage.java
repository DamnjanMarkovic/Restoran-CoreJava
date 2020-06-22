package com.comtrade.systemOperation.chat;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ChatLeavingMessage extends GenericSystemOperation {
    public ChatLeavingMessage(ClientThread clientThread) {
        super();
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

//        String nameUserExit = transferClass.getMessage();
//        int idLoggedUser = Integer.parseInt(transferClass.getSpecialMessage());
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp);
//            Broker broker = new Broker();
////            broker.logOffUser(nameUserExit, timestamp);
//        broker.logOffUserWithID(nameUserExit, timestamp, idLoggedUser);


    }
}
