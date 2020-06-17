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
    private User user;
    private Restaurant restaurant;
    private ImageIcon userPhoto;
    private ImageIcon restaurantPhoto;



    public ManagerForm(User user) throws IOException, ClassNotFoundException, InterruptedException {

        setContentPane(jPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 100, 800, 900);
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
                IngredientForm ingredientForm = new IngredientForm(user, restaurant);
                ingredientForm.setVisible(true);
            }
        });
        btnAvailableOffers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AvailableMenuForm menuForm = new AvailableMenuForm(user, restaurant);
                menuForm.setVisible(true);
            }
        });


        btnSpecialOffers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                OffersForm offersForm = new OffersForm(user, restaurant);
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
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
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
                }
                canceledOrdersForm.setVisible(true);
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
    }



    public static List<Restaurant> getRestaurantsList(User user) throws IOException, ClassNotFoundException, InterruptedException {
        List<Restaurant>restaurantsList = new ArrayList<>();
        restaurantsList = (List<Restaurant>) ControlerFront.getFrontControler().execute
                (TransferClass.create(user, ConstantsFC.RESTAURANT, ConstantsBLC.GET_RESTAURANTS)).getResponse();
        return restaurantsList;
    }


}
