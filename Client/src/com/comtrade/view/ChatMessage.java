package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class ChatMessage extends JDialog {
    private JPanel jPanel;
    private JTextField tfMessage;
    private JComboBox cbLoggedUsers;
    private JButton btnSend;
    private User user;
    List<User> listUsers;
    private List<String> listLoggedUsers;

    public ChatMessage(User user, List<User> listUsers, List<String> listLoggedUsers) {
        add(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(700, 300, 450, 150);
        this.user = user;
        this.listUsers = listUsers;
        this.listLoggedUsers = listLoggedUsers;
        setNamesInCB();
        Random random = new Random();


        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String receiver;
                if (tfMessage.getText()!=null && cbLoggedUsers.getSelectedItem()!=null){
                    String messageFromOwner = tfMessage.getText();

                    if (cbLoggedUsers.getSelectedItem().toString().equalsIgnoreCase("ALL LOGGED-IN USERS")){
                        receiver = "allLoggedUsers";
                    } else {
                        receiver = cbLoggedUsers.getSelectedItem().toString();
                    }
                    try {
                        TransferClass transferClass = TransferClass.create(user, ConstantsFC.CHAT, ConstantsBLC.OWNER_SENDING_MESSAGE);
                        transferClass.setMessage(messageFromOwner);
                        transferClass.setSpecialMessage(receiver);
                        ControlerFront.getFrontControler().execute(transferClass);
                    } catch (Exception ef) {
                        ef.printStackTrace();
                    }

                } else{
                    JOptionPane.showMessageDialog(null, "Please enter message.");
                }
                dispose();
            }
        });
    }

    private void setNamesInCB() {


        if (listLoggedUsers!=null){

            cbLoggedUsers.removeAllItems();
            for (String userName: listLoggedUsers                 ) {
                cbLoggedUsers.addItem(userName);
            }
            cbLoggedUsers.addItem("ALL LOGGED-IN USERS");
        }






    }
}
