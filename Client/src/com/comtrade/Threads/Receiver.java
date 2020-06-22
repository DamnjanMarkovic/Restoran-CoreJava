package com.comtrade.Threads;

import com.code.constants.ConstantsBLC;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.communicationClient.CommunicationClient;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;


public class Receiver implements Callable<TransferClass> {
    private volatile TransferClass transferClass;
    private CommunicationClient communicationClient;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private User user;

    public Receiver(Socket socket, User user) {
        this.socket = socket;
        this.user = user;
    }

    @Override
    public TransferClass call() throws Exception {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while ((transferClass = (TransferClass) objectInputStream.readObject()) !=null) {
                try {

                    while (transferClass.getConstantsBLC().equals(ConstantsBLC.OWNER_SENDING_MESSAGE)) {
                        if (user.getuserFirstName().equalsIgnoreCase("Mr Burns")){
                            break;
                        }
                        else {

                          JOptionPane.showMessageDialog(null, "For " + transferClass.getSpecialMessage() +
                                  ":\nOwner is sending a message:\n " +transferClass.getMessage());
                        }
                        objectInputStream = new ObjectInputStream(socket.getInputStream());
                        transferClass = (TransferClass) objectInputStream.readObject();
                        break;
                    }

                    while (transferClass.getConstantsBLC().equals(ConstantsBLC.UPDATE_LOGGED_USERS_NAMES)) {
                        if (!transferClass.getSpecialMessage().equalsIgnoreCase("Mr Burns")){
                            JOptionPane.showMessageDialog(null, transferClass.getMessage());
                        }
                            objectInputStream = new ObjectInputStream(socket.getInputStream());
                            transferClass = (TransferClass) objectInputStream.readObject();
                            break;
                    }
                    if (transferClass.getConstantsBLC().equals(ConstantsBLC.REMOVE_WRONG_ORDER)) {
                        User user1 = (User) transferClass.getRequest();
                        if (user1.getuserFirstName().equalsIgnoreCase(user.getuserFirstName())) {
                            objectInputStream = new ObjectInputStream(socket.getInputStream());
                            transferClass = (TransferClass) objectInputStream.readObject();
                            break;
                        }
                    }
                    while (transferClass.getConstantsBLC().equals(ConstantsBLC.REMOVE_WRONG_ORDER)) {
                        User user1 = (User) transferClass.getRequest();
                        if (!user1.getuserFirstName().equalsIgnoreCase(user.getuserFirstName())) {
                            JOptionPane.showMessageDialog(null, "Poruka za: " + transferClass.getResponse()
                                    + ": \n" + transferClass.getMessage() + "\nPorudzbina pod brojem: " + transferClass.getSpecialMessage() +
                                    "\nPoruku salje: " + user1.getuserFirstName() + ".");

                            objectInputStream = new ObjectInputStream(socket.getInputStream());
                            transferClass = (TransferClass) objectInputStream.readObject();
                        }

                        else {
                            break;
                        }
                    }
                    while (transferClass.getConstantsBLC().equals(ConstantsBLC.LOGGING_OFF)) {
//                        User user1 = (User) transferClass.getRequest();
                        break;
                    }


                    while (transferClass.getConstantsBLC().equals(ConstantsBLC.CHAT_LEAVING_MESSAGE)) {
//                        JOptionPane.showMessageDialog(null, "Poruka za: " + transferClass.getRequest()
//                                + ": \nKorisnik: " + transferClass.getMessage() + " se izlogovao.");

                        objectInputStream = new ObjectInputStream(socket.getInputStream());
                        transferClass = (TransferClass) objectInputStream.readObject();

                    }
                    return transferClass;
                } catch (IOException | ClassNotFoundException ignored) {
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } return transferClass;
    }


}
