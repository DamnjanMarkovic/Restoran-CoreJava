package com.comtrade.view;


import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OwnerForm extends JFrame{
    private JPanel jPanel;
    private JLabel lblUser;
    private JButton btnTables;
    private JLabel lblAllRestaurants;
    private JButton RESTAURANTSButton;
    private JButton MANAGERSWAITERSButton;
    private JButton btnLoggedUsers;
    private JLabel lblLoggedUsers;
    private JButton SENDMESSAGEButton;
    private User user;
    private List<User>listRestaurantStaff;
    private ImageIcon userPhoto;
    private List<String>loggedUsersNames = new ArrayList<>();




    public OwnerForm(User user) {

        setContentPane(jPanel);
        this.user = user;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400, 100, 500, 500);
        setDefaultLookAndFeelDecorated(true);
        setImages();
        lblUser.setIcon(getUserPhoto(user));
        loggedUsersNames = getLoggedUsers();
        setLoggedUsersNamesInLabel(loggedUsersNames);




        btnTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TablesBillViewForm tablesBillViewForm = null;
                try {
                    tablesBillViewForm = new TablesBillViewForm(user);
                    tablesBillViewForm.setVisible(true);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        RESTAURANTSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RestaurantsForm restaurantsForm = null;
                try {
                    restaurantsForm = new RestaurantsForm(user);
                    restaurantsForm.setVisible(true);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        MANAGERSWAITERSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                try {
                    UsersForm usersForm = null;
                    usersForm = new UsersForm(user);
                    usersForm.setVisible(true);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        btnLoggedUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedUsersNames = getLoggedUsers();
                setLoggedUsersNamesInLabel(loggedUsersNames);

            }
        });
        SENDMESSAGEButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                loggedUsersNames = getLoggedUsers();

                ChatMessage chatMessage = new ChatMessage(user, listRestaurantStaff, loggedUsersNames);
                chatMessage.setVisible(true);
            }
        });
    }

    private void setLoggedUsersNamesInLabel(List<String> loggedUsersNames) {
        if (loggedUsersNames!=null) {
            String namesString = prepareListUsersNames(loggedUsersNames);
            lblLoggedUsers.setText(String.valueOf(namesString));
        }
    }


    private List<String> getLoggedUsers() {

        TransferClass transferClass = TransferClass.create(user.getuserFirstName(), ConstantsFC.USER, ConstantsBLC.GET_LOGGED_USERS);
        try {
            loggedUsersNames = (List<String>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        } return loggedUsersNames;
    }

    private ImageIcon getUserPhoto(User user) {
        userPhoto = new ImageIcon(new ImageIcon((user.getImageLocation())).getImage().
                getScaledInstance(150, 250, java.awt.Image.SCALE_SMOOTH));
        return userPhoto;
    }


    private void setImages() {

        ImageIcon allRestaurants = new ImageIcon(new ImageIcon((ConstantsImages.OWNER.ownerLoginAllRestaurants())).getImage().
                getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH));
        lblAllRestaurants.setIcon(allRestaurants);

    }

    public String prepareListUsersNames(List<String> listNames){
        StringBuilder sb = new StringBuilder();
        if(listNames!=null) {
            for (String str : listNames) {
                sb.append(str).append(",");
            }
        }
        if (sb!=null && sb.length()>0){
            return sb.substring(0, sb.length() - 1) + ".";
        } else return "NO LOGGED USERS";


    }



}
