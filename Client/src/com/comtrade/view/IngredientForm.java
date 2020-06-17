package com.comtrade.view;


import com.code.constants.*;
import com.code.domain.AvailableIngredients;
import com.code.domain.Ingredient;
import com.code.domain.Restaurant;
import com.code.domain.User;
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


public class IngredientForm extends JDialog{
    private JPanel jPanel;
    private JButton btnUpdateItem;
    private JTable table1;
    private JScrollPane jScrollPane;
    private JTextField tfName;
    private JTextField tfQuantity;
    private JPanel panelOwner;
    private JLabel lblRestaurant;
    private JButton DELETEITEMButton;
    private JButton btnAddNewItems;
    private JLabel lblIngredient;

    private DefaultTableModel dtm = new DefaultTableModel();
    private int id_ingredient;
    private List<Ingredient>listIngredient = null;
    private List<Restaurant>restaurantsList = null;
    private Restaurant restaurant;
    List<Ingredient>fullIngredientList = new ArrayList<>();
    private Ingredient ingredient = new Ingredient();



    public IngredientForm(User user, Restaurant restaurant){
        Random random = new Random();
        add(jPanel);
        setBounds(200,200,750,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table1 = new JTable(dtm);
        jScrollPane.setViewportView(table1);
        lblIngredient.setIcon(setImageIngredient());
        setColumns();
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t MANAGER NAME: "+ user.getuserFirstName().toUpperCase());
        listIngredient = returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
        setIngredientsIntoTable(listIngredient);




        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int red = table1.getSelectedRow();
                id_ingredient = Integer.parseInt(table1.getModel().getValueAt(red, 0).toString());
                tfName.setText(table1.getModel().getValueAt(red, 1).toString());
            }
        });



        DELETEITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AvailableIngredients availableIngredients =
                        new AvailableIngredients(restaurant.getId_restaurant(), id_ingredient, 0);

            TransferClass transferClass = TransferClass.create(availableIngredients, ConstantsFC.INGREDIENTS, ConstantsBLC.DELETE);
            transferClass.setMessage(String.valueOf(restaurant.getId_restaurant()));
                try {
                    ControlerFront.getFrontControler().execute(transferClass);
                    listIngredient = returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
                    setIngredientsIntoTable(listIngredient);
                    deleteFields();

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        btnUpdateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (tfName.getText()!=null) {
                    if (checkIngredientMeasureType(tfName.getText(), listIngredient)) {
                        if (tfQuantity.getText() != null) {
                            try {
                                int ingredientQuantity = Integer.parseInt(tfQuantity.getText());
                                if (ingredientQuantity > 0) {
                                    addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant);
                                } else {
                                    double quantityForChecking = Double.parseDouble(tfQuantity.getText());
                                    if (checkIfReductionWillGoUnderZero(tfName.getText(), quantityForChecking, listIngredient)) {
                                        addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Quantity can not go under 0");
                                    }
                                }
                                tfQuantity.setText("");
                            } catch (Exception e) {
                                int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                                JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                                tfQuantity.setText("");
                            }
                        }
                    } else {
                        if (tfQuantity.getText() != null) {
                            try {
                                double ingredientQuantity = Double.parseDouble(tfQuantity.getText());
                                if (ingredientQuantity > 0) {
                                    addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant);
                                } else {
                                    double quantityForChecking = Double.parseDouble(tfQuantity.getText());
                                    if (checkIfReductionWillGoUnderZero(tfName.getText(), quantityForChecking, listIngredient)) {
                                        addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Quantity can not go under 0");
                                    }
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
            }
        });
        btnAddNewItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                IngredientAddForm ingredientAddForm = new IngredientAddForm(user, restaurant);
                ingredientAddForm.setVisible(true);
            }
        });
    }

    public static boolean checkIfReductionWillGoUnderZero(String nameIngredient, double quantity, List<Ingredient> list) {
        boolean isTypePiece = false;
        for (Ingredient selectedIngredient: list             ) {
            if (selectedIngredient.getIngredient_name().equalsIgnoreCase(nameIngredient)){
                if (selectedIngredient.getQuantity() + quantity >= 0){
                    isTypePiece = true;
                }
            }
        }
        return isTypePiece;
    }

    public static boolean checkIngredientMeasureType(String nameIngredient, List<Ingredient> list) {
        boolean isTypePiece = false;
        for (Ingredient selectedIngredient: list             ) {
            if (selectedIngredient.getIngredient_name().equalsIgnoreCase(nameIngredient)){
                if (selectedIngredient.getQuantity_measure().equalsIgnoreCase("piece")){
                    isTypePiece = true;
                    break;
                }
            }
        }
        return isTypePiece;
    }

    private Icon setImageIngredient() {
        ImageIcon ingredientImage = new ImageIcon(new ImageIcon((ConstantsImages.LOGIN_MANAGER.imageManagerIngredient())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        return ingredientImage;

    }

    private void addItemsIntoBase(int id_ingredient, double quantity, Restaurant restaurant) {
        Ingredient ingredient = new Ingredient(id_ingredient, quantity);
        TransferClass transferClass = TransferClass.create(ingredient, ConstantsFC.INGREDIENTS, ConstantsBLC.PUT_INGREDIENTS);
        transferClass.setMessage(String.valueOf(restaurant.getId_restaurant()));
        try {
            ControlerFront.getFrontControler().execute(transferClass);
            deleteFields();
            listIngredient = returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
            setIngredientsIntoTable(listIngredient);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void setIngredientsIntoTable(List<Ingredient> listIngredient) {
        dtm.setRowCount(0);
        for (Ingredient ingr: listIngredient) {
            Object [] line = {ingr.getId_ingredient(), ingr.getIngredient_name(),
                    ingr.getPurchase_price(), ingr.getQuantity_measure(),ingr.getQuantity()};
            dtm.addRow(line);
        }
    }

    public static List<Ingredient> returnListIngredientBasedOnRestaurant(int id_restaurant) {
        List<Ingredient> listIngredient = null ;
        TransferClass transferClass = TransferClass.create(id_restaurant, ConstantsFC.INGREDIENTS, ConstantsBLC.GET_INGREDIENTS_ON_RESTAURANT);
        try {
            listIngredient = (List<Ingredient>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return listIngredient;
    }

    public static List<Ingredient> returnListIngredientsBasdOnUser(int idUser) {
        List<Ingredient>listIngredient = null;
        TransferClass transferClass = TransferClass.create(idUser, ConstantsFC.INGREDIENTS, ConstantsBLC.GET_INGREDIENTS_BASED_ON_USER);
        try {
        listIngredient = (List<Ingredient>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return listIngredient;
    }


    private void deleteFields() {
        tfName.setText("");
        tfQuantity.setText("");

    }
    private void setColumns() {
        Object[]kolone = {"id", "ingredient","price", "measure","quantity"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);
        dtm.addColumn(kolone[3]);
        dtm.addColumn(kolone[4]);
    }
}
