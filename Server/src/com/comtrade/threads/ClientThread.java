package com.comtrade.threads;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerBL.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private int idLoggedUser;
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

    public int getIdLoggedUser() {
        return idLoggedUser;
    }

    public void setIdLoggedUser(int idLoggedUser) {
        this.idLoggedUser = idLoggedUser;
    }

    @Override
    public void run() {

        while(true) {
            try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            TransferClass transferClass = (TransferClass) objectInputStream.readObject();
            clientHandle(transferClass);
            } catch (IOException e) {

                    try {
                        clientSocket.close();
                        TransferClass transferClass = TransferClass.create(null, ConstantsFC.CHAT, ConstantsBLC.LOGGING_OFF);
                        transferClass.setMessage(null);
                        transferClass.setSpecialMessage(String.valueOf(this.idLoggedUser));
                        clientHandle(transferClass);
                        ControlerThread.getInstance().removeFromList(this);
                        clientSocket.close();
                        break;

                    } catch (IOException | SQLException ioException) {
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

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(transferClass);
            objectOutputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void clientHandle(TransferClass transferClass) throws IOException {


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
            case EMPTY:
                commandBase =new ControlerEmpty();
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
