package com.comtrade.communicationClient;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.code.transferClass.TransferClassPlus;
import com.comtrade.Threads.ThreadRead;

import java.io.*;
import java.net.Socket;

public class CommunicationClient {
    private static final CommunicationClient instance = new CommunicationClient();
    private Socket socket;
    private boolean transfer = true;
    private TransferClass transferClass;
    private User user;
    private ObjectInputStream objectInputStream;
    private Thread comClientThread;
    private TransferClassPlus transferClassPlus;

    public TransferClassPlus getTransferClassPlus() {
        return transferClassPlus;
    }

    public void setTransferClassPlus(TransferClassPlus transferClassPlus) {
        this.transferClassPlus = transferClassPlus;
    }

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


//
//    public synchronized void send(TransferClassPlus transferClassPlus, ObjectOutputStream objectOutputStream) throws IOException {
//        System.out.println("stize u send");
//
//        threadWriteFinal = new ThreadWriteFinal(objectOutputStream, transferClassPlus);
//
//    }
//    public synchronized TransferClass read1(Socket socket) throws Exception {
//
//        threadReadFinal = new ThreadReadFinal(socket, transferClassPlus);
//        return getInstance().getTransferClassPlus().getTransferClass();
//    }








//    public synchronized void send (TransferClass transferClass) throws IOException, InterruptedException {
//        while (!transfer) {
//            System.out.println("ulazi u send");
//            try {
//                System.out.println("ide u send cekanje");
//                wait();
//            } catch (InterruptedException e)  {
//                Thread.currentThread().interrupt();
//            }
//        }
//        System.out.println("send menja transfer bulijan u false");
//        transfer = false;
//
//        this.transferClass = transferClass;
//        notifyAll();
//    }
//
//    public synchronized TransferClass receive() {
//        while (transfer) {
//            System.out.println("receive 1");
//            try {
//                System.out.println("receive wait");
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//
//                System.out.println("receive menja transfer bulijan u true");
//                transfer = true;
//                System.out.println("receive notify all");
//                notifyAll();
//                System.out.println(transferClass.getRequest());
//                System.out.println(transferClass.getResponse());
//                return transferClass;
//            } catch (Exception e) {
//                System.out.println("krc");
//            }
//        }
//            return transferClass;
//
//    }


    public void send(TransferClass transferClass) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferClass);
        objectOutputStream.flush();
    }

    public  TransferClass read() throws Exception {

        ThreadRead threadRead = new ThreadRead(user, socket);
        Thread thread = new Thread(threadRead);
        thread.run();
        thread.join();
        return getInstance().getTransferClass();
    }


//    public synchronized TransferClass read() throws Exception {
//
//        threadReadFinal = new ThreadReadFinal(objectInputStream, transferClassPlus);
//        return getInstance().getTransferClassPlus().getTransferClass();
//    }




}