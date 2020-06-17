package com.comtrade.controlerFront;

import com.code.transferClass.TransferClass;
import com.comtrade.communicationClient.CommunicationClient;

import java.io.IOException;

public class ControlerFront {

    private static ControlerFront instance;

    private ControlerFront(){
    }

    public static ControlerFront getFrontControler() {
        if (instance==null){
            instance = new ControlerFront();
        }
        return instance;
    }

    public TransferClass execute(TransferClass transferClass) throws IOException, ClassNotFoundException, InterruptedException {
        CommunicationClient.getInstance().send(transferClass);
        return CommunicationClient.getInstance().read();

    }

}
