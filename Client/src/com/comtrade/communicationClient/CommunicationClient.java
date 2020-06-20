package com.comtrade.communicationClient;

import com.code.constants.ConstantsFC;
import com.code.constants.Constants_IP_Port;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.Threads.ThreadRead;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

public class CommunicationClient {
    private static final CommunicationClient instance = new CommunicationClient();
    private Socket socket;
    private boolean transfer = true;
    private TransferClass transferClass;
    private User user;
    private ObjectInputStream objectInputStream;
    private Thread comClientThread;

    public void setUser(User user) {
        this.user = user;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private CommunicationClient() {
        comClientThread = Thread.currentThread();
    }

    public TransferClass getTransferClass() {
        return transferClass;
    }

    public void setTransferClass(TransferClass transferClass) {
        this.transferClass = transferClass;
    }

    public static CommunicationClient getInstance() {
        return instance;
    }

    public void send(TransferClass transferClass) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferClass);
        objectOutputStream.flush();
    }
    public TransferClass read() throws Exception {


        ThreadRead threadRead = new ThreadRead(socket, user);
        threadRead.start();
        threadRead.join();
        return getInstance().getTransferClass();
    }


//    public synchronized void send (TransferClass transferClass) throws IOException  {
//        while (!transfer) {
//            System.out.println("ulazi u send");
//            try {
//                System.out.println("ide u send cekanje");
//                wait();
//
//            } catch (InterruptedException e)  {
//                Thread.currentThread().interrupt();
//            }
//        }
//        transfer = false;
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        objectOutputStream.writeObject(transferClass);
//        objectOutputStream.flush();
//        notifyAll();
//    }
//
//    public synchronized TransferClass read() {
//        while (transfer) {
//            System.out.println("ulazi u read");
//            try {
//                System.out.println("ide u read cekanje");
//                wait();
//                ThreadRead threadRead = new ThreadRead(socket, user);
//                threadRead.run();
//                return getInstance().getTransferClass();
//            } catch (InterruptedException e)  {
//                Thread.currentThread().interrupt();
//
//            }
//        }
//        transfer = true;
//
//        notifyAll();
//        return transferClass;
//    }




}