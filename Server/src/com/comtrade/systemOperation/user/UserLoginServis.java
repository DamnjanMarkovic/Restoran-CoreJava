package com.comtrade.systemOperation.user;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.controlerBL.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;

public class UserLoginServis extends GenericSystemOperation {
        private final ClientThread clientThread;

    public UserLoginServis(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws IOException {
        User user = (User) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.returnRoleUser(user));
        ControlerThread.getInstance().setThreadName(clientThread, user.getuserFirstName());
    }





}
