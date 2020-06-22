package com.comtrade.view;

import com.code.constants.Constant_Offert_Type;
import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.domain.*;
import com.code.domain.ImageRestaurant;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AvailableMenuForm extends JDialog {
    private DefaultTableModel dtm3 = new DefaultTableModel();
    private JPanel jPanel;
    private JTable table3;
    private JScrollPane scrolpane3;
    private JPanel panelOwner;
    private JComboBox cbEstablishment;
    private JComboBox cbType;
    private List<Offer> listAllMenues = new ArrayList<>();
    private List<Offer> listAvailableMenues = new ArrayList<>();
    private List<Ingredient> listAvailableIngredient = new ArrayList<>();
    private List<Restaurant> restaurantsList;
    private Restaurant restaurant;
    private List<ImageRestaurant> imageRestaurants = new ArrayList<>();
    private ImageRestaurant imageRestaurant;




    public AvailableMenuForm(User user, Restaurant restaurant) throws Exception {
        add(jPanel);
        setBounds(400, 150, 800, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table3 = new JTable( dtm3 ) {
            public Class getColumnClass(int column){
                return getValueAt(0, column).getClass();
            }
        };

        table3.setRowHeight(124);
        table3.setGridColor(Color.BLACK);
        scrolpane3.setViewportView(table3);
        setColumns();
        listAllMenues = returnAllListRestaurantMenues();

        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + " MANAGER NAME: "+ user.getuserFirstName().toUpperCase());
        listAvailableMenues = returnListAvailableMenues(restaurant.getId_restaurant(), listAllMenues);
        panelOwner.setVisible(false);
        imageRestaurants = AvailableMenuForm.returnImages();
        settTableCellsAlignment(table3);
        setCBType();

        cbType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    listAvailableMenues = returnListAvailableMenues(restaurant.getId_restaurant(), listAllMenues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dtm3.setRowCount(0);

                for (Offer rstm: listAvailableMenues) {
                    if (cbType.getSelectedItem().toString().equals("ALCOHOLIC_DRINK") &&
                            rstm.getRestaurant_menu_type().equals("ALCOHOLIC_DRINK")) {
                        for (ImageRestaurant ima: imageRestaurants) {
                            if (ima.getId_offer()==rstm.getId_restaurant_menu()){
                                Object[] line = {ImageRestaurant.getPhotoInTable(ima.getImageLocation()), rstm.getRestaurant_menu_name(), rstm.getRestaurant_menu_price()};
                                dtm3.addRow(line);
                            }
                        }

                    } else if (cbType.getSelectedItem().toString().equals("NON_ALCOHOLIC_DRINK") &&
                            rstm.getRestaurant_menu_type().equals("NON_ALCOHOLIC_DRINK")) {
                        for (ImageRestaurant ima: imageRestaurants) {
                            if (ima.getId_offer() == rstm.getId_restaurant_menu()) {
                                Object[] line = {ImageRestaurant.getPhotoInTable(ima.getImageLocation()), rstm.getRestaurant_menu_name(), rstm.getRestaurant_menu_price()};
                                dtm3.addRow(line);
                            }
                        }

                    } else if ((cbType.getSelectedItem().toString().equals("FOOD")) &&
                            rstm.getRestaurant_menu_type().equals("FOOD")) {
                        for (ImageRestaurant ima: imageRestaurants) {
                            if (ima.getId_offer() == rstm.getId_restaurant_menu()) {
                                Object[] line = {ImageRestaurant.getPhotoInTable(ima.getImageLocation()), rstm.getRestaurant_menu_name(), rstm.getRestaurant_menu_price()};
                                dtm3.addRow(line);
                            }
                        }

                    } else if ((cbType.getSelectedItem().toString().equals("ALL_ITEMS"))) {
                        for (ImageRestaurant ima: imageRestaurants) {
                            if (ima.getId_offer() == rstm.getId_restaurant_menu()) {
                                Object[] line = {ImageRestaurant.getPhotoInTable(ima.getImageLocation()), rstm.getRestaurant_menu_name(), rstm.getRestaurant_menu_price()};
                                dtm3.addRow(line);
                            }
                        }
                    }
                }
            }
        });
        cbType.setSelectedIndex(0);

    }

    private void settTableCellsAlignment(JTable table) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().getDefaultRenderer();
        TableModel tableModel = table.getModel();
        for (int columnIndex = 1; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JTableHeader header = table3.getTableHeader();
        header.setDefaultRenderer(headerRenderer);

    }

    public static List<ImageRestaurant> returnImages() {
        List<ImageRestaurant> imageRestaurants = new ArrayList<>();
        ImageRestaurant imageRestaurant = new ImageRestaurant();
        TransferClass transferClass = TransferClass.create(imageRestaurant, ConstantsFC.IMAGES, ConstantsBLC.GET);
        try {
            imageRestaurants = (List<ImageRestaurant>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageRestaurants;
    }


    private void setCBType() {
        Object [] line = {Constant_Offert_Type.ALCOHOLIC_DRINK, Constant_Offert_Type.NON_ALCOHOLIC_DRINK, Constant_Offert_Type.FOOD,
        "ALL_ITEMS"};
        cbType.addItem(line[0]);
        cbType.addItem(line[1]);
        cbType.addItem(line[2]);
        cbType.addItem(line[3]);
    }


    public static List<Offer> returnAllListRestaurantMenues() throws Exception {
        List<Offer> listOffer2 = new ArrayList<>();
        Offer offer = new Offer();
        TransferClass transferClass = TransferClass.create(offer, ConstantsFC.RESTAURANT_MENU, ConstantsBLC.GET_ALL_MENU_OFFERS);
        try {
            listOffer2 = (List<Offer>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return listOffer2;
    }



    public static List<Offer> returnListAvailableMenues(int idRestaurant, List<Offer> listAllMenues) throws Exception {
        List<Offer> listAvailableMenues = new ArrayList<>();
        TransferClass transferClass = TransferClass.create(listAllMenues, ConstantsFC.RESTAURANT_MENU, ConstantsBLC.GET_AVAILABLE_MENU_OFFERS);
        transferClass.setMessage(String.valueOf(idRestaurant));
        try {
            listAvailableMenues = (List<Offer>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return listAvailableMenues;
    }



    private void setColumns() {
        Object[] kolone3 = {"offer imageRestaurant", "offer name", "offer price"};
        dtm3.addColumn(kolone3[0]);
        dtm3.addColumn(kolone3[1]);
        dtm3.addColumn(kolone3[2]);
    }

}