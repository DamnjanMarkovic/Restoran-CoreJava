package com.comtrade.systemOperation.user;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.threads.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class UserLoginServis extends GenericSystemOperation {
        private final ClientThread clientThread;

    public UserLoginServis(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws IOException, SQLException {
        User user = (User) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.returnRoleUser(user));

        int loggedInID = broker.startLoggingUserReturnID(user.getuserFirstName());
        ControlerThread.getInstance().setThreadName(clientThread, user.getuserFirstName(), loggedInID);


    }





}
