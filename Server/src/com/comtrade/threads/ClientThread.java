package com.comtrade.threads;

import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.code.transferClass.TransferClassPlus;
import com.comtrade.controlerBL.*;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private List<ClientThread> listClients = new ArrayList<>();

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("hvata");
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                TransferClassPlus transferClassPlus = (TransferClassPlus) objectInputStream.readObject();
            TransferClass transferClass = (TransferClass) objectInputStream.readObject();
            clientHandle(transferClass);
            } catch (IOException e) {

                    try {
                        clientSocket.close();
                        ControlerThread.getInstance().removeFromList(this);
                        break;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public  void send(TransferClass transferClass) {
        // TODO Auto-generated method stub
        try {
            System.out.println("salje");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(transferClass);
//            objectOutputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private  void clientHandle(TransferClass transferClass) throws IOException {


        CommandBase commandBase = null;
        switch (transferClass.getConstantsFC()) {
            case USER:
                commandBase =new ControlerUser(this);
                break;
            case INGREDIENTS:
                commandBase =new ControlerIngredients();
                break;
            case RESTAURANT:
                commandBase =new ControlerRestaurant();
                break;
            case RESTAURANT_MENU:
                commandBase =new ControlerRestaurantMenu();
                break;
            case ORDER:
                commandBase =new ControlerOrders();
                break;
            case CHAT:
                commandBase =new ControlerChat(this);

                break;
            case BILL:
                commandBase =new ControlerBill();
                break;
            case IMAGES:
                commandBase =new ControlerImages();
                break;
            default:
//                commandBase = new ControlerChat(this);
                break;
        }
        commandBase.execute(transferClass);
        ControlerThread.getInstance().informOnlySendingClient(transferClass, this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientThread that = (ClientThread) o;
        return Objects.equals(clientSocket, that.clientSocket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientSocket);
    }



}
