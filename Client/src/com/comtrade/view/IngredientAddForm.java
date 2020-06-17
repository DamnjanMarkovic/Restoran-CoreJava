package com.comtrade.view;


import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.constants.ConstantsInfoWrongInput;
import com.code.domain.Ingredient;
import com.code.domain.Restaurant;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class IngredientAddForm extends JDialog{
    private JPanel jPanel;
    private JButton btnAddItem;
    private JTable table1;
    private JScrollPane jScrollPane;
    private JTextField tfName;
    private JTextField tfPriceNew;
    private JTextField tfQuantity;
    private JButton btnAddNewItem;
    private JComboBox cbAllItems;
    private JComboBox cbQuantityMeasre;
    private JLabel lblAddExistingIngre;
    private JTextField tfNameNew;
    private JTextField tfQuantityNew;
    private JLabel lblMeasure;
    private JTextField tfMeasure;
    private DefaultTableModel dtm = new DefaultTableModel();
    private int id_ingredient;
    private List<String> listMissingIngredientNames = new ArrayList<>();
    private List<Ingredient>listIngredient = new ArrayList<>();
    private List<Restaurant>restaurantsList;
    List<Ingredient>fullIngredientList = new ArrayList<>();
    private Ingredient ingredient = new Ingredient();
    private List<Ingredient> listMissingIngredient;



    public IngredientAddForm(User user, Restaurant restaurant){
        Random random = new Random();
        add(jPanel);
        setBounds(200,200,600,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table1 = new JTable(dtm);
        jScrollPane.setViewportView(table1);
        setColumns();
        setComboBoxOptions();

        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t MANAGER NAME: "+ user.getuserFirstName().toUpperCase());
        listIngredient = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
        listMissingIngredient = returnMissingIngredientsList(listIngredient, restaurant.getId_restaurant());
        setIngredientsIntoTable(listIngredient);
        insertIngredientsInCB();


        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cbAllItems.getSelectedItem()!=null) {
                    if (IngredientForm.checkIngredientMeasureType(cbAllItems.getSelectedItem().toString(), listMissingIngredient)) {
                        if (tfQuantity.getText() != null) {
                            try {
                                int ingredientQuantity = Integer.parseInt(tfQuantity.getText());
                                if (ingredientQuantity > 0) {
                                    addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant.getId_restaurant());
                                }
                                else {
                                    double quantityForChecking = Double.parseDouble(tfQuantity.getText());
                                    if (IngredientForm.checkIfReductionWillGoUnderZero(tfName.getText(), quantityForChecking, listMissingIngredient)) {
                                        addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant.getId_restaurant());
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
                                    addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant.getId_restaurant());
                                } else {
                                    double quantityForChecking = Double.parseDouble(tfQuantity.getText());
                                    if (IngredientForm.checkIfReductionWillGoUnderZero(tfName.getText(), quantityForChecking, listMissingIngredient)) {
                                        addItemsIntoBase(id_ingredient, Double.parseDouble(tfQuantity.getText()), restaurant.getId_restaurant());
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

                listMissingIngredient = returnMissingIngredientsList(listIngredient, restaurant.getId_restaurant());
                insertIngredientsInCB();

            }
        });

        btnAddNewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int fakeIDIngredient = -1;
                double quantity = 0;

                if ((tfNameNew.getText()!=null) && (tfPriceNew.getText() != null) && (cbQuantityMeasre.getSelectedItem()!=null) &&
                        (tfQuantityNew.getText() != null)) {
                    String ingredient_name = tfNameNew.getText();
                    try {
                        double purchase_price = Double.parseDouble(tfPriceNew.getText());
                        if (purchase_price > 0) {
                            String quantity_measure = cbQuantityMeasre.getSelectedItem().toString();
                                try {
                                    if (cbQuantityMeasre.getSelectedItem().toString().equalsIgnoreCase("piece")){
                                        try{
                                            int quantityINT = Integer.parseInt(tfQuantityNew.getText());
                                            quantity = Double.parseDouble(String.valueOf(quantityINT));
                                            if (quantity > 0) {

                                                ingredient = new Ingredient(fakeIDIngredient, ingredient_name, purchase_price,
                                                        quantity_measure, quantity);
                                                TransferClass transferClass = TransferClass.create(ingredient, ConstantsFC.INGREDIENTS, ConstantsBLC.POST_INGREDIENTS);
                                                transferClass.setMessage(String.valueOf(restaurant.getId_restaurant()));
                                                try {
                                                    ControlerFront.getFrontControler().execute(transferClass);
                                                    deleteFields();
                                                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Quantity has to be more then 0");
                                                deleteFields();
                                            }
                                        }catch (Exception e) {
                                            int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                                            JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                                            deleteFields();
                                        }
                                    } else {
                                         quantity = Double.parseDouble(tfQuantityNew.getText());
                                        if (quantity > 0) {

                                            ingredient = new Ingredient(fakeIDIngredient, ingredient_name, purchase_price,
                                                    quantity_measure, quantity);
                                            TransferClass transferClass = TransferClass.create(ingredient, ConstantsFC.INGREDIENTS, ConstantsBLC.POST_INGREDIENTS);
                                            transferClass.setMessage(String.valueOf(restaurant.getId_restaurant()));
                                            try {
                                                ControlerFront.getFrontControler().execute(transferClass);
                                                deleteFields();
                                            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Quantity has to be more then 0");
                                            deleteFields();
                                        }
                                    }
                                   

                                } catch (Exception e) {
                                    int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                                    JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                                    deleteFields();
                                }
                            
                        } else {
                                JOptionPane.showMessageDialog(null, "Price has to be more then 0");
                            deleteFields();
                        }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                        deleteFields();
                    }
                }
                deleteFields();
                listIngredient = IngredientForm.returnListIngredientBasedOnRestaurant(restaurant.getId_restaurant());
                setIngredientsIntoTable(listIngredient);
            }
        });

        cbAllItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (cbAllItems.getSelectedItem()!=null){

                    for (Ingredient inger: listMissingIngredient                         ) {
                        if (inger.getIngredient_name().equalsIgnoreCase(cbAllItems.getSelectedItem().toString())){

                            lblMeasure.setText(inger.getQuantity_measure());
                        }
                    }

                id_ingredient = listMissingIngredient.stream().filter(r->(r.getIngredient_name().equals(cbAllItems.getSelectedItem().toString())))
                        .findFirst().get().getId_ingredient();

            }
            }
        });
    }

    private List<Ingredient> returnMissingIngredientsList(List<Ingredient> listIngredient, int id_restaurant) {
        TransferClass transferClass = TransferClass.create(listIngredient, ConstantsFC.INGREDIENTS, ConstantsBLC.GET_INGREDIENTS_MISSING);
        transferClass.setMessage(String.valueOf(id_restaurant));
        List<Ingredient>listMissingIngredient = null;
        try {
            listMissingIngredient = (List<Ingredient>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        return listMissingIngredient;
    }

    private void addItemsIntoBase(int id_ingredient, double quantity, int idRestaurant) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId_ingredient(id_ingredient);
        ingredient.setQuantity(quantity);
        TransferClass transferClass = TransferClass.create(ingredient, ConstantsFC.INGREDIENTS, ConstantsBLC.PUT_INGREDIENTS);
        transferClass.setMessage(String.valueOf(idRestaurant));
        try {
            ControlerFront.getFrontControler().execute(transferClass);
            deleteFields();
            listIngredient = IngredientForm.returnListIngredientBasedOnRestaurant(idRestaurant);
            setIngredientsIntoTable(listIngredient);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void insertIngredientsInCB() {
        cbAllItems.removeAllItems();
        Set<String>ingredientsNames = new HashSet<>();

        for (Ingredient name:listMissingIngredient             ) {
            cbAllItems.addItem(name.getIngredient_name());
        }
//        ingredientsNames.stream().forEach(r -> cbAllItems.addItem(r));
    }

    private void setIngredientsIntoTable(List<Ingredient> listIngredient) {
        dtm.setRowCount(0);
        for (Ingredient ingr: listIngredient) {
            Object [] line = {ingr.getId_ingredient(), ingr.getIngredient_name(),
                    ingr.getPurchase_price(), ingr.getQuantity_measure(),ingr.getQuantity()};
            dtm.addRow(line);
        }
    }


    private void setComboBoxOptions() {
        String [] combo1 = {"L","KG","piece"};
        for (String com1: combo1) {
            cbQuantityMeasre.addItem(com1);
        }
    }

    private void deleteFields() {
        tfNameNew.setText("");
        tfQuantity.setText("");
        tfPriceNew.setText("");

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
