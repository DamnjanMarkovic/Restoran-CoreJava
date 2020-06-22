package com.comtrade.systemOperation.chat;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.threads.ControlerThread;
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

//    REMOVE_WRONG_ORDER:

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
       User user = (User) transferClass.getRequest();
        Broker broker = new Broker();
        String managerName = broker.getManagerNameBasedOnWaiterID(user.getid_user());
        transferClass.setResponse(managerName);
        List<ClientThread> loggedUsersList = ControlerThread.getInstance().getListClients();

        for (ClientThread ct:loggedUsersList             ) {
            if (ct.getName().equalsIgnoreCase(managerName)){
                ct.send(transferClass);
            }
            if (ct.getName().equalsIgnoreCase("Mr Burns")){
                transferClass.setResponse("Mr Burns");
                ct.send(transferClass);
            }

            if (ct.getName().equalsIgnoreCase(user.getuserFirstName())){
                ct.send(transferClass);
            }
        }

    }
}
