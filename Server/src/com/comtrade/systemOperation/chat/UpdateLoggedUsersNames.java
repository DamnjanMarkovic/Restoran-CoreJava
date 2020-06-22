package com.comtrade.systemOperation.chat;

import com.code.transferClass.TransferClass;
import com.comtrade.threads.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateLoggedUsersNames extends GenericSystemOperation {
    ClientThread clientThread;
    public UpdateLoggedUsersNames(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

//    UPDATE_LOGGED_USERS_NAMES

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        List<String> listNames = new ArrayList<>();
        List<ClientThread>listClients = ControlerThread.getInstance().getListClients();
        for (ClientThread ct:listClients             ) {
            if (!ct.getName().equals(clientThread.getName())){
                listNames.add(ct.getName());
            }
        }
        transferClass.setResponse(listNames);
    }
}
