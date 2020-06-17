package com.comtrade.systemOperation.chat;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnStaffService extends GenericSystemOperation {
    public ReturnStaffService(ClientThread clientThread) {
        super();

    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        User user = (User) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.getRestaurantStaff(user.getid_user()));



    }
}
