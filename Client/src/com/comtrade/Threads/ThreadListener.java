package com.comtrade.Threads;


import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;


import javax.swing.*;
import java.net.Socket;


public class ThreadListener implements Runnable {

    private volatile TransferClass transferClass;
    private final User user;
    private final Socket socket;


    public ThreadListener(Socket socket, User user) {
        this.user = user;
        this.socket = socket;
    }

    @Override
    public void run() {
        TransferClass transferClass = TransferClass.create(null, ConstantsFC.EMPTY, ConstantsBLC.EMPTY_TEST);
        while (true) {
            try {
                Thread.sleep(3000);
                ControlerFront.getFrontControler().execute(transferClass);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}
