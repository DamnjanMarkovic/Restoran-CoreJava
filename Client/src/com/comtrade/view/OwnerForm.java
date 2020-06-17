package com.comtrade.view;


import com.code.constants.ConstantsImages;
import com.code.domain.*;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


public class OwnerForm extends JFrame{
    private JPanel jPanel;
    private JLabel lblUser;
    private JButton btnTables;
    private JLabel lblAllRestaurants;
    private JButton RESTAURANTSButton;
    private JButton MANAGERSWAITERSButton;
    private User user;
    private List<User>listRestaurantStaff;
    private ImageIcon userPhoto;




    public OwnerForm(User user) {

        setContentPane(jPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400, 100, 500, 500);
        setDefaultLookAndFeelDecorated(true);
        setImages();
        lblUser.setIcon(getUserPhoto(user));



        btnTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TablesBillViewForm tablesBillViewForm = null;
                try {
                    tablesBillViewForm = new TablesBillViewForm(user);
                    tablesBillViewForm.setVisible(true);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
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
                }


            }
        });
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



}
