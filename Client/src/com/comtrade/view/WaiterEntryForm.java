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

public class WaiterEntryForm extends JFrame{
    private JPanel jPanel;
    private JLabel lblUser;
    private JLabel lblRestaurant;
    private JPanel panelWaiter;
    private JButton TABLE1Button;
    private JButton TABLE4Button;
    private JButton TABLE7Button;
    private JButton TABLE2Button;
    private JButton TABLE5Button;
    private JButton TABLE8Button;
    private JButton TABLE3Button;
    private JButton TABLE6Button;
    private JButton TABLE9Button;
    private JButton btnRefresh;
    private User user;
    private ImageIcon restaurantPhoto;
    private Restaurant restaurant;
    private List<Offer> listAllMenues = new ArrayList<>();
    private DinningTable dinningTable;
    private List<JButton>buttonList = new ArrayList<>();
    private ImageIcon userPhoto = null;
    private List<DinningTable>listAvailableTables = new ArrayList<>();

    public WaiterEntryForm(User user) throws IOException, ClassNotFoundException, InterruptedException {

        setContentPane(jPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400,200,1000,700);
        setDefaultLookAndFeelDecorated(true);
        List<Restaurant>restaurantsList = ManagerForm.getRestaurantsList(user);
        listAllMenues = AvailableMenuForm.returnAllListRestaurantMenues();
        this.user = user;
        restaurant = restaurantsList.stream().findFirst().get();
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t WAITER NAME: "+ user.getuserFirstName().toUpperCase());
        lblRestaurant.setIcon(ImageRestaurant.getPhoto(restaurant.getImageLocation()));
        lblUser.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));
        buttonList = addButtonsToList();
        setTablePhotos();


        TABLE1Button.addActionListener(new ButtonListener());
        TABLE2Button.addActionListener(new ButtonListener());
        TABLE3Button.addActionListener(new ButtonListener());
        TABLE4Button.addActionListener(new ButtonListener());
        TABLE5Button.addActionListener(new ButtonListener());
        TABLE6Button.addActionListener(new ButtonListener());
        TABLE7Button.addActionListener(new ButtonListener());
        TABLE8Button.addActionListener(new ButtonListener());
        TABLE9Button.addActionListener(new ButtonListener());

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            setTablePhotos();

            }
        });
    }


    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            int table_number = 0;
            JButton buttonClicked = (JButton) actionEvent.getSource();
            int tableNumberArrived = Integer.parseInt(buttonClicked.getName());
            switch (tableNumberArrived) {
                case 1:
                    table_number = 1;
                    break;
                case 2:
                    table_number = 2;
                    break;
                case 3:
                    table_number = 3;
                    break;
                case 4:
                    table_number = 4;
                    break;
                case 5:
                    table_number = 5;
                    break;
                case 6:
                    table_number = 6;
                    break;
                case 7:
                    table_number = 7;
                    break;
                case 8:
                    table_number = 8;
                    break;
                case 9:
                    table_number = 9;
                    break;
            }
            dinningTable  = returnDinningTable(table_number, restaurant.getId_restaurant());
            OrderForm orderForm = new OrderForm(listAllMenues, user, restaurant, dinningTable);
            orderForm.setVisible(true);
            setTablePhotos();

        }
    }



    private List<JButton> addButtonsToList() {
        buttonList.add(TABLE1Button);
        buttonList.add(TABLE2Button);
        buttonList.add(TABLE3Button);
        buttonList.add(TABLE4Button);
        buttonList.add(TABLE5Button);
        buttonList.add(TABLE6Button);
        buttonList.add(TABLE7Button);
        buttonList.add(TABLE8Button);
        buttonList.add(TABLE9Button);
        return buttonList;
    }

    private void setTablePhotos() {

        TransferClass transferClass = TransferClass.create(null, ConstantsFC.RESTAURANT, ConstantsBLC.RETURN_AVAILABLE_TABLES);
        try {
            listAvailableTables = (List<DinningTable>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (JButton button:buttonList             ) {
            ImageIcon buttonPhoto = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_WAITER.imageWaiterTableAvailable())).getImage().
                    getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
            button.setIcon(buttonPhoto);
        }

        if (listAvailableTables.size()>0){
            for (DinningTable table: listAvailableTables) {
                if (table.getId_restaurant() == restaurant.getId_restaurant()) {

                    for (int i = 0; i <buttonList.size() ; i++) {
                        if (table.getTable_number() == (i+1)){
                            ImageIcon buttonPhoto = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_WAITER.imageWaiterTableOccupied())).getImage().
                                    getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH));
                            buttonList.get(i).setIcon(buttonPhoto);
                        }
                    }
                }
            }
        }
    }


    public static DinningTable returnDinningTable(int table_number, int id_restaurant) {
        DinningTable dinningTable = new DinningTable(table_number, id_restaurant);
        TransferClass transferClass = TransferClass.create(dinningTable, ConstantsFC.RESTAURANT, ConstantsBLC.RETURN_DINNING_TABLE);
        try {
            dinningTable = (DinningTable) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return dinningTable;
    }



}
