package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.code.constants.Constant_Offert_Type.*;

public class OffersForm extends JDialog{
    private JPanel jPanel;
    private JTable table1;
    private JTextField tfOfferName;
    private JTextField tfOfferPrice;
    private JComboBox cbIngredient;
    private JTextField tfQuantity;
    private JButton btnAddIngredient;
    private JButton btnRemoveIngredient;
    private JButton SAVENEWOFFERButton;
    private JScrollPane scrolPane1;
    private JComboBox cbChooseOffer;
    private JTable table2;
    private JScrollPane scrolPane2;
    private JLabel lblOfferPrice;
    private JComboBox cbType;
    private JComboBox cbIngredientMeasureType;
    private JButton DELETEButton;
    private JButton btnSetPhoto;
    private JButton CHOOSEPHOTOButton;
    private JLabel lblOfferPhoto;
    private DefaultTableModel dtm1 = new DefaultTableModel();
    private List<Ingredient> listAvailableIngredient = new ArrayList<>();
    private int row = -1;
    private String chosenPhotoLocation = null;
    private DefaultTableModel dtm2 = new DefaultTableModel();
    private int idOffer;
    private List<ImageRestaurant>imagesList = new ArrayList<>();

    private Restaurant restaurant;
    private List<Offer>listAllMenues = null;

    public OffersForm(User user, Restaurant restaurant) throws Exception {
        Random random = new Random();
        add(jPanel);
        setBounds(200,200,1000,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("SPECIAL OFFERS");
        table1 = new JTable(dtm1);
        scrolPane1.setViewportView(table1);
        table2 = new JTable(dtm2);
        scrolPane2.setViewportView(table2);
        listAllMenues = AvailableMenuForm.returnAllListRestaurantMenues();
        setColumnsTable1();
        setColumnsTable2();
        listAvailableIngredient = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
        setIngridientsCBIngredients(listAvailableIngredient);
        setcbChooseOffer();
        setCBType();
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t MANAGER NAME: "+ user.getuserFirstName().toUpperCase());
        imagesList = AvailableMenuForm.returnImages();
        btnSetPhoto.setVisible(false);





        btnAddIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cbIngredientMeasureType.getSelectedItem()!=null && tfOfferPrice.getText()!=null && cbType.getSelectedItem()!=null &&
                cbIngredient.getSelectedItem()!=null && cbIngredientMeasureType.getSelectedItem()!=null && tfQuantity.getText()!=null
                ) {
                    if (cbIngredientMeasureType.getSelectedItem().toString().equalsIgnoreCase("piece")) {
                        String ingredientname = cbIngredient.getSelectedItem().toString();
                        if(tfQuantity.getText()!=null){
                            try {
                                int ingredientQuantity = Integer.parseInt(tfQuantity.getText());
                                if (ingredientQuantity > 0) {
                                    Object[] red = {ingredientname, ingredientQuantity};
                                    dtm1.addRow(red);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Quantity has to be larger then 0");
                                }
                                tfQuantity.setText("");
                            } catch (Exception e) {
                                int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                                JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                                tfQuantity.setText("");
                            }
                        }
                    } else {
                        String ingredientname = cbIngredient.getSelectedItem().toString();
                        if(tfQuantity.getText()!=null){
                            try {
                                double ingredientQuantity = Double.parseDouble(tfQuantity.getText());
                                if (ingredientQuantity > 0) {
                                    Object[] red = {ingredientname, ingredientQuantity};
                                    dtm1.addRow(red);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Quantity has to be larger then 0");
                                }
                                tfQuantity.setText("");
                            } catch (Exception e) {
                                int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                                JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                                tfQuantity.setText("");
                            }
                        }
                    }
                }
                else {
                    int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                    JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));

                }
            }
        });
        btnRemoveIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (row >= 0) {
                    System.out.println(row);
                    dtm1.removeRow(row);
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                row = table1.getSelectedRow();
            }
        });
        SAVENEWOFFERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String restaurant_menu_name = tfOfferName.getText();
                if(tfOfferPrice.getText()!=null){
                    try {
                        double restaurant_menu_price = Double.parseDouble(tfOfferPrice.getText());
                        if (restaurant_menu_price > 0) {
                            String offerType = cbType.getSelectedItem().toString();
                            List<Ingredient> listIngredient = new ArrayList<>();
                            int numberOfLines = table1.getRowCount();
                            Ingredient ingredient;
                            for (int i = 0; i<numberOfLines; i++) {
                                int id = returnIdIngredient(table1.getModel().getValueAt(i, 0).toString(), listAvailableIngredient);
                                ingredient = new Ingredient(id, Double.parseDouble(table1.getModel().getValueAt(i, 1).toString()));
                                listIngredient.add(ingredient);
                            }
                            Offer offer = new Offer(restaurant_menu_name, restaurant_menu_price, offerType, listIngredient);
                            TransferClass transferClass = TransferClass.create(offer, ConstantsFC.RESTAURANT_MENU, ConstantsBLC.POST_NEW_MENU_OFFERS);
                            try {
                                ControlerFront.getFrontControler().execute(transferClass);
                            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            clearFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Offer price has to be larger then 0");
                            tfOfferPrice.setText("");
                        }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                        tfOfferPrice.setText("");
                    }
                }
                try {
                    listAllMenues = AvailableMenuForm.returnAllListRestaurantMenues();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setcbChooseOffer();


            }
        });

        cbChooseOffer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                dtm2.setRowCount(0);

                for (Offer rstm : listAllMenues) {
                    if (cbChooseOffer!=null){
                    if (cbChooseOffer.getSelectedItem().toString().equals(rstm.getRestaurant_menu_name())) {
                        lblOfferPrice.setText(String.valueOf(rstm.getRestaurant_menu_price()));
                        for (Ingredient ingr: rstm.getMenuIngredientList()                             ) {
                            Object[] red = {ingr.getIngredient_name(), ingr.getQuantity()};
                            dtm2.addRow(red);
                        }
                    }
                }}
                setOfferPhotoInLabel(listAllMenues, imagesList);
            }
        });
        cbIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            cbIngredientMeasureType.removeAllItems();
            for (Ingredient ingrid: listAvailableIngredient             ) {

                    if (cbIngredient.getSelectedItem().toString().equalsIgnoreCase(ingrid.getIngredient_name())){
                        cbIngredientMeasureType.addItem(ingrid.getQuantity_measure());
                    }
                }
            }
        });

        btnSetPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                idOffer = returnIDOffer();

                TransferClass transferClass = TransferClass.create(chosenPhotoLocation, ConstantsFC.RESTAURANT_MENU, ConstantsBLC.SET_PHOTO_LOCATION);
                transferClass.setMessage(String.valueOf(idOffer));
                try {
                    ControlerFront.getFrontControler().execute(transferClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagesList = AvailableMenuForm.returnImages();
                try {
                    listAllMenues = AvailableMenuForm.returnAllListRestaurantMenues();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (chosenPhotoLocation!=null){
                    ImageIcon image1 = new ImageIcon(new ImageIcon(chosenPhotoLocation).getImage().
                            getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));

                    lblOfferPhoto.setIcon(image1);
                }

            }
        });
        CHOOSEPHOTOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosenPhotoLocation =selectImage();
                btnSetPhoto.setVisible(true);
            }
        });


    }

    public static String selectImage() {
        String location = null;
        JButton open = new JButton();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new java.io.File(ConstantsImages.IMAGE_FOLDER_LOCATION.locationImageFolder()));
        jFileChooser.setDialogTitle("PLEASE SELECT IMAGE");
        if (jFileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){

        }
        if (jFileChooser.getSelectedFile()!=null) {
            String [] spliter = jFileChooser.getSelectedFile().getAbsolutePath().split("/");
            String requestedAdress=null;
            int lineBeginsIndex = 0;

            for (int i = 0; i <spliter.length ; i++) {
                if (spliter[i].equalsIgnoreCase("Server")){
                    lineBeginsIndex = i;
                }
            }
            int i = 0;
            for (i = lineBeginsIndex+i; i <spliter.length ; i++) {
                requestedAdress = requestedAdress + spliter[i] + "/";
            }
            assert requestedAdress != null;  requestedAdress = requestedAdress.substring(0, requestedAdress.length()-1);
             location  = requestedAdress.substring(4);

        }return location;

    }

    private void setOfferPhotoInLabel(List<Offer> listAllMenues, List<ImageRestaurant> imagesList) {
        for (Offer off:listAllMenues             ) {
            for (ImageRestaurant img:imagesList                 ) {
                if (cbChooseOffer.getSelectedItem().toString().equalsIgnoreCase(off.getRestaurant_menu_name())){

                    if (img.getImageLocation()!=null && off.getId_restaurant_menu()==img.getId_offer()){
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(img.getImageLocation()).getImage().
                            getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));
                    lblOfferPhoto.setIcon(imageIcon);
                    }
                }
            }
        }
    }

    private int returnIDOffer() {
        for(Offer offer: listAllMenues){

            if (cbChooseOffer.getSelectedItem().toString().equalsIgnoreCase(offer.getRestaurant_menu_name())){
                idOffer =  offer.getId_restaurant_menu();
                break;
            }
        }return idOffer;
    }


    private void clearFields() {

        tfOfferName.setText("");
        tfOfferPrice.setText("");
        tfQuantity.setText("");
        dtm1.setRowCount(0);
    }

    private void setCBType() {
        Object[] line= {ALCOHOLIC_DRINK, NON_ALCOHOLIC_DRINK, FOOD};
        cbType.addItem(line[0]);
        cbType.addItem(line[1]);
        cbType.addItem(line[2]);

    }


    private void setColumnsTable2() {
        Object[]kolone2 = {"ingredient name" , "quantity"};
        dtm2.addColumn(kolone2[0]);
        dtm2.addColumn(kolone2[1]);

    }



    private void setcbChooseOffer() {
        cbChooseOffer.removeAllItems();
        for (Offer rsm : listAllMenues) {
                cbChooseOffer.addItem(rsm.getRestaurant_menu_name());
        }
    }





    private void setColumnsTable1() {
        Object[]kolone1 = {"ingredient name" , "quantity"};
        dtm1.addColumn(kolone1[0]);
        dtm1.addColumn(kolone1[1]);
    }


    private int returnIdIngredient(String ingredientName, List<Ingredient> ingredientList) {
        int idIngredient = 0;
        for (Ingredient ingr : ingredientList             ) {
            if (ingr.getIngredient_name().equals(ingredientName)){
                idIngredient = ingr.getId_ingredient();
            }
        }return idIngredient;
    }

    private void setIngridientsCBIngredients(List<Ingredient> listAllIngredient) {
        for (Ingredient ingrid: listAllIngredient             ) {
            cbIngredient.addItem(ingrid.getIngredient_name());
        }
    }


}
