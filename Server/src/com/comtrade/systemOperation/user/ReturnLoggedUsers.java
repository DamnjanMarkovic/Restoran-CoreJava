package com.comtrade.systemOperation.user;

import com.code.constants.ConstantsImages;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerBL.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnLoggedUsers extends GenericSystemOperation {
    private ClientThread clientThread;
    public ReturnLoggedUsers(ClientThread clientThread) {
        super();
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        List<String> listNames = new ArrayList<>();
        List<ClientThread>listClients = ControlerThread.getInstance().getListClients();
        for (ClientThread ct:listClients             ) {
            if (ct.getName()!=clientThread.getName()){
                listNames.add(ct.getName());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str: listNames             ) {
            sb.append(str).append(",");
        }
        String result = sb.substring(0, sb.length() - 1) + ".";

        transferClass.setResponse(result);

    }
}
