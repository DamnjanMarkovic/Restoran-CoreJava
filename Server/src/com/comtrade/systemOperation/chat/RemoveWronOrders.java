package com.comtrade.systemOperation.chat;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.controlerBL.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RemoveWronOrders extends GenericSystemOperation {
    private ClientThread clientThread;
    public RemoveWronOrders(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
       User user = (User) transferClass.getRequest();
       String messageForManager = transferClass.getMessage();
        Broker broker = new Broker();
        String managerName = broker.getManagerNameBasedOnWaiterID(user.getid_user());
        transferClass.setResponse("uspelo!");
        List<ClientThread> loggedUsersList = ControlerThread.getInstance().getListClients();

        for (ClientThread ct:loggedUsersList             ) {
            if (ct.getName().equalsIgnoreCase(managerName)){
                ct.send(transferClass);
            }
        }
        for (ClientThread ct:loggedUsersList             ) {
            if (ct.getName().equalsIgnoreCase(user.getuserFirstName())){
                ct.send(transferClass);
            }
        }

    }
}
