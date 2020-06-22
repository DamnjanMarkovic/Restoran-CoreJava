package com.comtrade.communicationClient;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.Threads.Receiver;

import java.io.IOException;
import java.net.Socket;


public class CommunicationClient {
    private boolean transfer = true;
    private TransferClass transferClass;
    private final Socket socket;
    private final User user;

    public CommunicationClient(Socket socket, User user) {
        this.socket = socket;
        this.user = user;
    }


    public TransferClass getTransferClass() {
        return transferClass;
    }

    public void setTransferClass(TransferClass transferClass) {
        this.transferClass = transferClass;
    }

    public synchronized void send (TransferClass transferClass) throws IOException, InterruptedException {

        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
            }
        }
        transfer = false;
        this.transferClass = transferClass;
        notifyAll();
    }

    public synchronized TransferClass receive() throws Exception {

        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
            }
        }
        transfer = true;
        Receiver receiver = new Receiver(socket, user);
        notifyAll();
        return receiver.call();
    }
}
