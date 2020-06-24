package com.comtrade.threads;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.transferClass.TransferClass;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControlerThread {

    private static ControlerThread instance;
    private List<ClientThread> listClients = new ArrayList<>();
    private Socket socket;
    private List<String> loggedUsersNames = new ArrayList<>();
    private String nameChangingStatus;

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

    public List<String> getLoggedUsersNames() {
        return loggedUsersNames;
    }

    public synchronized void addClientThread(ClientThread clientThread){
        listClients.add(clientThread);

    }


    public void setThreadName(ClientThread clientThread, String name, Integer userIDLOgged) throws IOException {
        for (ClientThread clientThread1 : listClients             ) {
            if (clientThread1.getClientSocket().equals(clientThread.getClientSocket())) {
                clientThread.setName(name);
                clientThread.setIdLoggedUser(userIDLOgged);
                loggedUsersNames.add(name);
                nameChangingStatus = name;
            }
        }
        updateLOggedUsersNames("User "+  name  + " logged-in.");
    }

    private void updateLOggedUsersNames(String message) {

        for (ClientThread ct: listClients             ) {
            if (ct.getName().equalsIgnoreCase("Mr Burns")){
                TransferClass transferClass = TransferClass.create(loggedUsersNames, ConstantsFC.CHAT, ConstantsBLC.UPDATE_LOGGED_USERS_NAMES);
                transferClass.setMessage(message);
                transferClass.setSpecialMessage(nameChangingStatus);
                ct.send(transferClass);
            }
        }
    }

    public void informAllClients(TransferClass transferClass) throws IOException {
        for (ClientThread client: listClients) {
            client.send(transferClass);
        }
    }


    public  void informOnlySendingClient(TransferClass transferClass, ClientThread clientThread) throws IOException {
        for (ClientThread client: listClients) {
            if (client.equals(clientThread)){
                client.send(transferClass);
            }
        }
    }

    public void removeFromList(ClientThread clientThread) throws IOException, SQLException {
        int numberOfLoggedInUsers = 0;
        String nameUserExit = "";

        for (ClientThread nit:listClients             ) {
            if (nit.equals(clientThread)) {
                nameUserExit = clientThread.getName();
                listClients.remove(clientThread);
                loggedUsersNames.remove(nameUserExit);
                nameChangingStatus = nameUserExit;
                updateLOggedUsersNames("User "+ nameUserExit + " logged-out.");
                break;
            }
        }

//        StringBuilder namesRest = new StringBuilder();
//        for (ClientThread nit:listClients             ) {
//                namesRest.append(nit.getName()).append(", ");
//                numberOfLoggedInUsers ++;
//        }
//        for (ClientThread ct:listClients             ) {
//            TransferClass transferClass = TransferClass.create(ct.getName(), ConstantsFC.CHAT, ConstantsBLC.CHAT_LEAVING_MESSAGE);
//            transferClass.setMessage(nameUserExit);
//            transferClass.setSpecialMessage(String.valueOf(ct.getIdLoggedUser()));
//            System.out.println("Korisniku "+ ct.getName() + " poslata poruka da se korisnik "+ nameUserExit + " izlogovao");
//            ct.send(transferClass);
//        }


//        if (numberOfLoggedInUsers>0) {
//            String finalNames = namesRest.substring(0, namesRest.length() - 2);
//            JOptionPane.showMessageDialog(null, nameUserExit + " logged out.\n" + finalNames + " still logged in.");
//        } else {
//            JOptionPane.showMessageDialog(null, nameUserExit + " logged out.\nNo more users logged in.");
//        }


    }



}
