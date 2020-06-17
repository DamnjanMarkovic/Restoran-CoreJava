package com.comtrade.threadClient;


import com.code.transferClass.TransferClass;
import com.comtrade.communicationClient.CommunicationClient;

import java.io.IOException;

public class ThreadWrite extends Thread {


    private  TransferClass transferClass;

    public ThreadWrite(TransferClass transferClass) {
        this.transferClass = transferClass;

        this.start();

    }

    @Override
    public void run() {

        try {
            CommunicationClient.getInstance().send(transferClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}