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


public class ManagerForm extends JFrame{
    private List<Restaurant> restaurantsList;
    private JPanel jPanel;
    private JButton btnAvailableOffers;
    private JLabel lblUser;
    private JButton btnIngredients;
    private JLabel lblRestaurant;
    private JButton btnTables;
    private JButton btnSpecialOffers;
    private JButton btnCancel;
    private JButton button1;
    private JButton transferOrder;
    private User user;
    private Restaurant restaurant;
    private ImageIcon userPhoto;
    private ImageIcon restaurantPhoto;



    public ManagerForm(User user) throws Exception {

        setContentPane(jPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 100, 900, 900);
        setDefaultLookAndFeelDecorated(true);
        restaurantsList = getRestaurantsList(user);
        this.user = user;
        restaurant = restaurantsList.stream().findFirst().get();
        setImagesButtons();
        setTitle("RESTAURANT " + restaurant.getName_restaurant().toUpperCase() + "\t MANAGER NAME: " + user.getuserFirstName().toUpperCase());
        lblUser.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));
        lblRestaurant.setIcon(ImageRestaurant.getPhoto(restaurant.getImageLocation()));



        btnIngredients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                IngredientForm ingredientForm = null;
                try {
                    ingredientForm = new IngredientForm(user, restaurant);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ingredientForm.setVisible(true);
            }
        });
        btnAvailableOffers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AvailableMenuForm menuForm = null;
                try {
                    menuForm = new AvailableMenuForm(user, restaurant);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                menuForm.setVisible(true);
            }
        });


        btnSpecialOffers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                OffersForm offersForm = null;
                try {
                    offersForm = new OffersForm(user, restaurant);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                offersForm.setVisible(true);
            }
        });


        btnTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TablesBillViewForm tablesBillViewForm = null;
                try {
                    tablesBillViewForm = new TablesBillViewForm(user);
                    tablesBillViewForm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                CanceledOrdersForm canceledOrdersForm = null;
                try {
                    canceledOrdersForm = new CanceledOrdersForm(user, restaurant, userPhoto, restaurantPhoto);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                canceledOrdersForm.setVisible(true);
            }
        });
//        button1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                CanceledOrdersForm canceledOrdersForm = null;
//                try {
//                    canceledOrdersForm = new CanceledOrdersForm(user, restaurant, userPhoto, restaurantPhoto);
//                } catch (InterruptedException | IOException | ClassNotFoundException ef) {
//                    ef.printStackTrace();
//                } catch (Exception ef) {
//                    ef.printStackTrace();
//                }
//                canceledOrdersForm.setVisible(true);
//            }
//        });

        transferOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                TransferOrderForm transferOrderForm = new TransferOrderForm(user, restaurant);
                transferOrderForm.setVisible(true);




            }
        });
    }


    private void setImagesButtons() {

        ImageIcon offers = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginbtnAvailableOffers())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        btnAvailableOffers.setIcon(offers);
        ImageIcon tables = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginbtnTables())).getImage().
                getScaledInstance(300, 150, java.awt.Image.SCALE_SMOOTH));
        btnTables.setIcon(tables);
        ImageIcon specialOffers = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginbtnSpecialOffers())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        btnSpecialOffers.setIcon(specialOffers);
        ImageIcon ingredients = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginbtnIngredients())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        btnIngredients.setIcon(ingredients);
        ImageIcon returnedOrders = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginbtnCancel())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        btnCancel.setIcon(returnedOrders);
        ImageIcon transfer = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerLoginTransfer())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        transferOrder.setIcon(transfer);
    }



    public static List<Restaurant> getRestaurantsList(User user) throws Exception {
        List<Restaurant>restaurantsList = new ArrayList<>();
        restaurantsList = (List<Restaurant>) ControlerFront.getFrontControler().execute
                (TransferClass.create(user, ConstantsFC.RESTAURANT, ConstantsBLC.GET_RESTAURANTS)).getResponse();
        return restaurantsList;
    }


}
