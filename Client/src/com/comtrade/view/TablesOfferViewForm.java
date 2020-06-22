package com.comtrade.view;

import com.code.domain.Ingredient;
import com.code.domain.Offer;
import com.code.domain.Restaurant;
import com.code.domain.User;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablesOfferViewForm extends JDialog {
    private JPanel jPanel;
    private JTable table1;
    private JScrollPane jScrollPane;
    private DefaultTableModel dtm = new DefaultTableModel();
    private int row;


    public TablesOfferViewForm (List<Offer>listOfferBasedOnBill, List<Offer> listAllMenues, User user, Restaurant restaurant){
        add(jPanel);
        setBounds(200,200,750,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table1 = new JTable(dtm);
        jScrollPane.setViewportView(table1);
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t MANAGER NAME: "+ user.getuserFirstName().toUpperCase());
        setColumnsOffers();
        setDataIntoTable(listOfferBasedOnBill, listAllMenues);

//
//        table1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                row = table1.getSelectedRow();
//                String offerName = (table1.getModel().getValueAt(row, 0).toString());
//                String line = "";
//                for (Offer allOffers:listAllMenues                     ) {
//                    if (offerName.equalsIgnoreCase(allOffers.getRestaurant_menu_name())){
//                        for (Ingredient ingre:allOffers.getMenuIngredientList() ) {
//                                line = "Ingredient: " + ingre.getIngredient_name() +
//                                        ", purchase price: " + ingre.getPurchase_price() + ", " +
//                                        "quantity: " + ingre.getQuantity() + " " + ingre.getQuantity_measure() + ";\n"+
//                                        line;
//                        }
//                    }
//                }
//                JOptionPane.showMessageDialog(null,line);
//            }
//        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                row = table1.getSelectedRow();
                String offerName = (table1.getModel().getValueAt(row, 0).toString());
                String line = "";
                for (Offer allOffers:listAllMenues                     ) {
                    if (offerName.equalsIgnoreCase(allOffers.getRestaurant_menu_name())){
                        for (Ingredient ingre:allOffers.getMenuIngredientList() ) {
                            line = "Ingredient: " + ingre.getIngredient_name() +
                                    ", purchase price: " + ingre.getPurchase_price() + ", " +
                                    "quantity: " + ingre.getQuantity() + " " + ingre.getQuantity_measure() + ";\n"+
                                    line;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null,line);
            }
        });
    }

    private void setDataIntoTable(List<Offer> listOfferBasedOnBill, List<Offer> listAllMenues) {
        for (Offer idOffer:listOfferBasedOnBill) {
            for (Offer allOffers:listAllMenues) {
                if (idOffer.getId_restaurant_menu()==allOffers.getId_restaurant_menu()){
                    setData(allOffers);
                }
            }
        }
    }

    private void setData(Offer allOffers) {

        Object [] line = {allOffers.getRestaurant_menu_name(), allOffers.getRestaurant_menu_price(), allOffers.getRestaurant_menu_type()};
        dtm.addRow(line);

    }


    private void setColumnsOffers() {
        dtm.setColumnCount(0);
        Object[]kolone = {"OFFER", "OFFER PRICE", "OFFER TYPE"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);

    }


}
