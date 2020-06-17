package com.comtrade.view;



import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.constants.ConstantsInfoWrongInput;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderForm extends JDialog{
    private JPanel jPanel;
    private JComboBox cbNonAlcoholicDrink;
    private JTextField tfAlcofholicDrink;
    private JComboBox cbFood;
    private JLabel lblUserPhoto;
    private JLabel lblRestaurant;
    private JComboBox cbAlcoholicDrink;
    private JScrollPane jScrolPane;
    private JTable table1;
    private JButton ADDButton;
    private JButton DELETEOFFERButton;
    private JButton BILLButton;
    private JTextField tfFood;
    private JTextField tfNonAlcoholic;
    private JButton INSERTORDERButton;
    private JButton btnListAdd;
    private JComboBox cbList;
    private JButton CHECKBILLButton;
    private List<Offer> listAvailableMenues = new ArrayList<>();
    private DefaultTableModel dtm = new DefaultTableModel();
    private DefaultTableModel dtm2 = new DefaultTableModel();
    private List<Integer>finalIDOrdersList = new ArrayList<>();
    private int row = -1;
    private DinningTable dinningTable;
    private List<Order> finalOrderList = new ArrayList<>();
    private String stringIdListToChar;
    private String finalIDOrderListInString;
    private List<Offer> listAllMenues =new ArrayList<>();
    private List<Ingredient> listIngredient = new ArrayList<>();
    private List<Ingredient> listIngredientChangeable = new ArrayList<>();
    private List<ImageRestaurant> imageRestaurants = new ArrayList<>();
    private ImageRestaurant imageRestaurant;
    private ImageIcon restaurantPhoto;
    private ImageIcon userPhoto;
    private List<Order>currentOrderList = new ArrayList<>();

//    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
//    private static final String CHAR_UPPER = "skdfjbsjclsjnd12312312";
//    private static final String NUMBER = "0123456789";

    public OrderForm(List<Offer> listAllMenues, User user, Restaurant restaurant, DinningTable dinningTable){

        Random random = new Random();
        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
        setContentPane(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(200,200,1400,600);
        table1 = new JTable(dtm);
        jScrolPane.setViewportView(table1);
        setColumns();
        lblUserPhoto.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));
        imageRestaurants = AvailableMenuForm.returnImages();
        listAvailableMenues = AvailableMenuForm.returnListAvailableMenues(restaurant.getId_restaurant(), listAllMenues);
        setCB(listAvailableMenues);
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t WAITER NAME: "+ user.getuserFirstName().toUpperCase());
        lblRestaurant.setIcon(ImageRestaurant.getPhoto(restaurant.getImageLocation()));
        cbFood.setRenderer(new ImagesTextRenderer());
        cbAlcoholicDrink.setRenderer(new ImagesTextRenderer());
        cbNonAlcoholicDrink.setRenderer(new ImagesTextRenderer());
        listIngredient = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
        listIngredientChangeable = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());


        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Order>listOrder = new ArrayList<>();

                if (!tfFood.getText().equals("")){
                    String offerName = cbFood.getModel().getSelectedItem().toString();
                    try{
                        int quantity = Integer.parseInt(tfFood.getText());
                        if (quantity > 0) {
                            for (Offer ofr:listAllMenues ) {
                                if (ofr.getRestaurant_menu_name().equalsIgnoreCase(offerName)){
                                    Order order = new Order(ofr, quantity);
                                    listOrder.add(order);
                                }
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Quantity has to be more then 0");

                        }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }


                }
                if (!tfAlcofholicDrink.getText().equals("")){
                    String offerName = cbAlcoholicDrink.getModel().getSelectedItem().toString();
                    try{
                    int quantity = Integer.parseInt(tfAlcofholicDrink.getText());
                    if (quantity > 0) {
                        for (Offer ofr:listAllMenues ) {
                            if (ofr.getRestaurant_menu_name().equalsIgnoreCase(offerName)){
                                Order order = new Order(ofr, quantity);
                                listOrder.add(order);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantity has to be more then 0");
                    }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }
                }
                if (!tfNonAlcoholic.getText().equals("")){
                    String offerName = cbNonAlcoholicDrink.getModel().getSelectedItem().toString();
                    try{
                    int quantity = Integer.parseInt(tfNonAlcoholic.getText());
                    if (quantity > 0) {
                        for (Offer ofr:listAllMenues ) {
                            if (ofr.getRestaurant_menu_name().equalsIgnoreCase(offerName)){
                                Order order = new Order(ofr, quantity);
                                listOrder.add(order);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantity has to be more then 0");
                    }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }
                }
                if (checkifAvailable(restaurant, listOrder)){
                    for (Order order: listOrder) {
                        Object[] line = {order.getOffer().getRestaurant_menu_name(), order.getQuantity()};
                        dtm.addRow(line);
                    }
                }
                listOrder.clear();
                clearFields();
                setCB(listAvailableMenues);
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                row = table1.getSelectedRow();
            }
        });
        DELETEOFFERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (row>=0) {
                    dtm.removeRow(row);
                }
            }
        });
        INSERTORDERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Order order;
                    List<Order>listOrders = new ArrayList<>();
                    Offer offer = new Offer();

                    int numberOfLines = table1.getRowCount();
                    for (int i = 0; i<numberOfLines; i++) {
                        offer = returnOffer(table1.getModel().getValueAt(i, 0).toString());
                        int quantity = Integer.parseInt(table1.getModel().getValueAt(i, 1).toString());
                        order = new Order(offer, quantity, dinningTable);
                        listOrders.add(order);
                    }
                    TransferClass transferClass = TransferClass.create(listOrders, ConstantsFC.ORDER, ConstantsBLC.POST_ORDER_AND_RETURN_IDS);
                    transferClass.setMessage(String.valueOf(restaurant.getId_restaurant()));
                    try {
                        List<Integer> listIdOrders = (List<Integer>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
                        dtm.setRowCount(0);
//                        listIngredientChangeable = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }

                transferClass = TransferClass.create(listAvailableMenues, ConstantsFC.ORDER, ConstantsBLC.RETURN_TAKEN_ORDERS);
                finalIDOrderListInString = returnIDListToChar(finalIDOrdersList);
                transferClass.setMessage(finalIDOrderListInString);
                try {
                    currentOrderList = (List<Order>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        CHECKBILLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                CheckBillForm checkBillForm = new CheckBillForm(listAllMenues, user, restaurant, dinningTable);
                checkBillForm.setVisible(true);
                dispose();
            }
        });
    }



    private boolean checkifAvailable(Restaurant restaurant, List<Order> listOrder) {
        for (Order ord1: listOrder             ) {
            for (Ingredient ingr: ord1.getOffer().getMenuIngredientList()){
                for (Ingredient ingred: listIngredientChangeable                                 ) {
                    if (ingr.getIngredient_name().equalsIgnoreCase(ingred.getIngredient_name())){
                        if(ingred.getQuantity()-((ord1.getQuantity())*ingr.getQuantity())>=0){
                            ingred.setQuantity(ingred.getQuantity()-((ord1.getQuantity())*ingr.getQuantity()));
                        }else {
                            JOptionPane.showMessageDialog(null, "NOT ENOUGHT INGREDIENTS " +
                                    " " + ord1.getOffer().getRestaurant_menu_name() + ", TRY SOMETHING ELSE.");
                            return false;
                        }
                    }
                }
            }
        }return true;
    }


    private String returnIDListToChar(List<Integer> finalIDOrdersList) {
        String requestedCharList="";
        for (Integer inte: finalIDOrdersList             ) {
            requestedCharList = requestedCharList + inte + ";";
        }
        return finalIDOrdersList.size() + ";" + requestedCharList;
    }



    private Offer returnOffer(String toString) {
        for (Offer offe: listAvailableMenues             ) {
            if (offe.getRestaurant_menu_name().equalsIgnoreCase(toString))
                return offe;
        }
        return null;
    }

    private void clearFields() {
        tfFood.setText("");
        tfAlcofholicDrink.setText("");
        tfNonAlcoholic.setText("");
    }

    private void setCB(List<Offer> listAvailableMenues) {
        cbNonAlcoholicDrink.removeAllItems();
        cbAlcoholicDrink.removeAllItems();
        cbFood.removeAllItems();


        for (Offer rstm: listAvailableMenues) {
            if (rstm!=null && rstm.getRestaurant_menu_type().equals("ALCOHOLIC_DRINK")) {
                for (ImageRestaurant imageRestaurant1 : imageRestaurants) {
                    if (rstm.getId_restaurant_menu()== imageRestaurant1.getId_offer()){
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageRestaurant1.getImageLocation()).getImage().
                                getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));
                        cbAlcoholicDrink.addItem(new ImagesNText(new ImageIcon(imageIcon.getImage()), rstm.getRestaurant_menu_name()));
                    }
                }

            } else if (rstm!=null && rstm.getRestaurant_menu_type().equals("NON_ALCOHOLIC_DRINK")) {
                for (ImageRestaurant imageRestaurant1 : imageRestaurants) {
                    if (rstm.getId_restaurant_menu()== imageRestaurant1.getId_offer()){
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageRestaurant1.getImageLocation()).getImage().
                                getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));
                        cbNonAlcoholicDrink.addItem(new ImagesNText(new ImageIcon(imageIcon.getImage()), rstm.getRestaurant_menu_name()));
                    }
                }
            } else if (rstm!=null && rstm.getRestaurant_menu_type().equals("FOOD")) {
                for (ImageRestaurant imageRestaurant1 : imageRestaurants) {
                    if (rstm.getId_restaurant_menu()== imageRestaurant1.getId_offer()){
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageRestaurant1.getImageLocation()).getImage().
                                getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));
                        cbFood.addItem(new ImagesNText(new ImageIcon(imageIcon.getImage()), rstm.getRestaurant_menu_name()));
                    }
                }

            }
        }
    }


    private void setColumns() {
            Object[] kolone3 = {"offer name", "quantity"};
            dtm.addColumn(kolone3[0]);
            dtm.addColumn(kolone3[1]);
    }


}
