package com.comtrade.systemOperation.chat;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;
import com.comtrade.threads.ControlerThread;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OwnerSendingMessage extends GenericSystemOperation {
    private ClientThread clientThread;
    public OwnerSendingMessage(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

            String receiver = transferClass.getSpecialMessage();
            List<ClientThread>fullLoggedUsersList = ControlerThread.getInstance().getListClients();

            if (receiver.equalsIgnoreCase("allLoggedUsers")){
                for (ClientThread ct:fullLoggedUsersList                     ) {
                    if (!ct.getName().equalsIgnoreCase("Mr Burns")){
                        transferClass.setSpecialMessage(ct.getName());
                        ct.send(transferClass);
                    }
                }
            }
            else {
                for (ClientThread ct:fullLoggedUsersList                     ) {
                    if (ct.getName().equalsIgnoreCase(receiver)){
                        ct.send(transferClass);
                    }
                }

            }



    }
}
