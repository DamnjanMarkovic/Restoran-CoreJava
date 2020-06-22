package com.comtrade.systemOperation.chat;

import com.code.transferClass.TransferClass;
import com.comtrade.threads.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class PostMessageService extends GenericSystemOperation {

    private ClientThread clientThread;

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }


    public PostMessageService(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        setClientThread(clientThread);
        if (transferClass.getMessage().equalsIgnoreCase("QUIT")){

        }else {
            ControlerThread.getInstance().informAllClients(transferClass);
        }


    }






}
