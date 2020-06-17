package com.comtrade.communicationClient;

import com.code.constants.Constants_IP_Port;
import com.code.transferClass.TransferClass;
import com.comtrade.threadClient.ThreadRead;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class CommunicationClient {
    private static CommunicationClient instance;
    private Socket socket;
    private boolean transfer = true;
    private TransferClass transferClass;
    private int userID;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private CommunicationClient() {
        try {
            socket = new Socket(Constants_IP_Port.IP_ADRESS.getIPAdress(), Constants_IP_Port.PORT.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CommunicationClient getInstance() {
        if (instance == null) {
            instance = new CommunicationClient();
        }
        return instance;
    }

    public  void send(TransferClass transferClass) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferClass);
        objectOutputStream.flush();
    }

    public  TransferClass read() throws IOException, ClassNotFoundException {
        TransferClass transferClass = new TransferClass();

        try {

            ExecutorService service =  Executors.newSingleThreadExecutor();
            ThreadRead threadRead = new ThreadRead(socket, userID);
            transferClass = service.submit(threadRead).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        return transferClass;
    }

}       
