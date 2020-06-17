package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.threads.ClientThread;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ControlerThread {

    private static ControlerThread instance;
    private List<ClientThread> listClients = new ArrayList<>();
    private Socket socket;

    private ControlerThread(){

    }

    public static ControlerThread getInstance() {
        if (instance ==null){
            synchronized (ControlerThread.class) {
                if(instance == null) {
                    instance = new ControlerThread();
                }
            }
        }
        return instance;
    }

    public synchronized List<ClientThread> getListClients() {
        return listClients;
    }

    public synchronized void addClientThread(ClientThread clientThread){
        listClients.add(clientThread);
    }

    public void setThreadName(ClientThread clientThread, String name) throws IOException {
        for (ClientThread clientThread1 : listClients             ) {
            if (clientThread1.getClientSocket().equals(clientThread.getClientSocket())) {
                clientThread.setName(name);
            }
        }
    }

    public void informAllClients(TransferClass transferClass, ClientThread clientThread) throws IOException {
        for (ClientThread client: listClients) {
            if (!clientThread.equals(client)){
                clientThread.send(transferClass);

            }
        }
    }


    public  void informOnlySendingClient(TransferClass transferClass, ClientThread clientThread) throws IOException {
        for (ClientThread client: listClients) {
            if (client.equals(clientThread)){
                client.send(transferClass);

            }
        }
    }

    public void removeFromList(ClientThread clientThread){
        int numberOfLoggedInUsers = 0;

        String nameUserExit = "";
        for (ClientThread nit:listClients             ) {
            if (nit.equals(clientThread)) {
                nameUserExit = clientThread.getName();
                listClients.remove(clientThread);
                break;
            }
        }
        StringBuilder namesRest = new StringBuilder();
        for (ClientThread nit:listClients             ) {
                namesRest.append(nit.getName()).append(", ");
                numberOfLoggedInUsers ++;
        }

        if (numberOfLoggedInUsers>0) {
            String finalNames = namesRest.substring(0, namesRest.length() - 2);
            JOptionPane.showMessageDialog(null, nameUserExit + " logged out.\n" + finalNames + " still logged in.");
        } else {
            JOptionPane.showMessageDialog(null, nameUserExit + " logged out.\nNo more users logged in.");
        }
    }




}
