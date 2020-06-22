package com.comtrade.controlerFront;

import com.code.constants.Constants_IP_Port;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.Threads.Sender;
import com.comtrade.Threads.ThreadListener;
import com.comtrade.communicationClient.CommunicationClient;

import java.io.IOException;
import java.net.Socket;

public class ControlerFront {

    private static ControlerFront instance;
    private Socket socket;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private ControlerFront() {
        try {
            socket = new Socket(Constants_IP_Port.IP_ADRESS.getIPAdress(), Constants_IP_Port.PORT.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread listener = new Thread(new ThreadListener(socket, user));
        listener.start();

    }

    public static ControlerFront getFrontControler() {
        if (instance == null) {
            instance = new ControlerFront();
        }
        return instance;
    }

    public synchronized TransferClass execute(TransferClass transferClass) throws Exception {

        CommunicationClient communicationClient = new CommunicationClient(socket, user);
        Thread sender = new Thread(new Sender(transferClass, communicationClient, socket));
        sender.start();
        return communicationClient.receive();
    }



}

//    public TransferClass execute1(TransferClassPlus transferClassPlus) throws Exception {
//        System.out.println("stize u execute1");
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        System.out.println("kreiran output");
////        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//        System.out.println("kreiran input");
//
//        CommunicationClient.getInstance().send(transferClassPlus, objectOutputStream);
//        return CommunicationClient.getInstance().read1(socket);
//
//    }


//    public TransferClass execute(TransferClass transferClass) throws Exception {
//
//        CommunicationClient.getInstance().send(transferClass);
//        return CommunicationClient.getInstance().read();
//
//    }


//    public  TransferClass execute(TransferClass transferClass) throws Exception {
//
//        CommunicationClient.getInstance().send(transferClass);
//        return CommunicationClient.getInstance().read();
//
//    }








