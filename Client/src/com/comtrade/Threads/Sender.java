package com.comtrade.Threads;

import com.code.transferClass.TransferClass;
import com.comtrade.communicationClient.CommunicationClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Sender implements Runnable{
    private final TransferClass transferClass;
    private final CommunicationClient communicationClient;
    private final Socket socket;

    public Sender(TransferClass transferClass, CommunicationClient communicationClient, Socket socket) {
        this.transferClass = transferClass;
        this.communicationClient = communicationClient;
        this.socket = socket;
    }



    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(transferClass);
            communicationClient.send(transferClass);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
