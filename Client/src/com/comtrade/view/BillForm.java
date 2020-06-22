package com.comtrade.view;

import com.code.constants.*;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillForm extends JDialog {
    private JPanel jPanel;
    private JLabel lblRestaurantPhoto;
    private JTable table1;
    private JScrollPane jScrolPane;
    private JLabel lblWaiterName;
    private JLabel lblOffersTotalPrice;
    private JLabel lblTime;
    private JComboBox cbReduction;
    private JComboBox cbPaymentType;
    private JLabel lblReductionSavings;
    private JLabel lblPaymentTypeSavings;
    private JLabel lblFinaPrice;
    private JLabel lblHappyHour;
    private JLabel lblHappyHourSaving;
    private JButton btnFinalPrice;
    private JButton PRINTRECEIPTButton;
    private JLabel lblTotalSavings;
    private JLabel lblUserPhoto;
    private JButton printReceiptButton;
    private DefaultTableModel dtm = new DefaultTableModel();
//    private int happyHourStart;
//    private int happyHourEnd;
    private double totalPrice;
    private double finalPrice;
    double specialReduction = 0.0;
    double reductionForPaymentType = 0.0;
    double happyHourReduction = 0.0;
    private String finalIDOrderListInString;
    private List<Integer>finalIDOrdersList = new ArrayList<>();
    private List<Order>completeOrderList = new ArrayList<>();
    private double finalTax;


    public BillForm(List<Offer> listAllMenues, List<Order> finalOrderList, User user, Restaurant restaurant,
                    DinningTable dinningTable) {
//        happyHourStart = 18;
//        happyHourEnd = 20;
//        lblPaymentTypeSavings.setText("0");

        setContentPane(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(400,200,700,800);
        table1 = new JTable(dtm);
        jScrolPane.setViewportView(table1);
        setColumns();
        setCB();
        completeOrderList = returnCompleteOrderList(listAllMenues, finalOrderList);
        lblHappyHourSaving.setVisible(false);
        lblHappyHour.setVisible(false);
        isHappyHour();
        showHappyHourLabels();
        setOrdersIntoTable(completeOrderList);

        PRINTRECEIPTButton.setVisible(false);
        printReceiptButton.setVisible(false);

        lblRestaurantPhoto.setIcon(ImageRestaurant.getPhoto(restaurant.getImageLocation()));
        lblUserPhoto.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));

        btnFinalPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cbPaymentType.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.CASH.returnCash())){
                    reductionForPaymentType = totalPrice*(1-Constants_Reduction.CASH.reductionForCash());

                }else if (cbPaymentType.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.CREDIT_CARD.returnCreditCard())){
                    reductionForPaymentType = totalPrice*(1-Constants_Reduction.CREDIT_CARD.reductionForCreditCard());

                }else if (cbPaymentType.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.CHECK_PAYMENT.returnCheckPayent())){
                    reductionForPaymentType = totalPrice*(1-Constants_Reduction.CHECK_PAYMENT.reductionForCheck());
                }

                if (cbReduction.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.REGULAR_CUSTOMER.returnRegularCustomer())){
                    specialReduction = totalPrice*(1-Constants_Reduction.REGULAR_CUSTOMER.reductionForRegularCustomer());

                }else if (cbReduction.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.SPECIAL_DISCOUNT.returnSpecialDiscount())){
                    specialReduction = totalPrice*(1-Constants_Reduction.SPECIAL_DISCOUNT.reductionForSpecialDiscount());
                }
                else if (cbReduction.getSelectedItem().toString().equalsIgnoreCase(Constants_Reduction.NO_REDUCTION.returnNoReduction())){
                    specialReduction = totalPrice*(1-Constants_Reduction.SPECIAL_DISCOUNT.reductionNoReduction());

                }
                if(isHappyHour()){
                    happyHourReduction = totalPrice*.1;

                }

                lblReductionSavings.setText(convertToTwoDecimals(specialReduction));
                lblPaymentTypeSavings.setText(convertToTwoDecimals(reductionForPaymentType));
                lblHappyHourSaving.setText(convertToTwoDecimals(happyHourReduction));
                lblTotalSavings.setText(convertToTwoDecimals(specialReduction + reductionForPaymentType + happyHourReduction));
                lblFinaPrice.setText(convertToTwoDecimals(totalPrice - (specialReduction + reductionForPaymentType + happyHourReduction)));
                PRINTRECEIPTButton.setVisible(true);
                printReceiptButton.setVisible(true);

            }
        });
        PRINTRECEIPTButton.addActionListener(new ActionListener() {         //ovo je saveReceipt
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            finalIDOrderListInString = returnIDListToChar(completeOrderList);
            Bill bill = new Bill(dinningTable.getId_dinningTable(), user.getuserFirstName(), cbPaymentType.getSelectedItem().toString(),
                    Double.parseDouble(lblTotalSavings.getText()), Double.parseDouble(lblFinaPrice.getText()),
                    restaurant.getId_restaurant());
                TransferClass transferClass = TransferClass.create(bill, ConstantsFC.BILL, ConstantsBLC.POST_BILL);
                transferClass.setMessage(finalIDOrderListInString);
                try {
                    ControlerFront.getFrontControler().execute(transferClass);
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dispose();
            }

        });
        printReceiptButton.addActionListener(new ActionListener() {         //ovo je za pripremu PDF-a
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for(Order ord: completeOrderList){
                    finalTax+= (Math.round(getPriceWithTax(ord) * ord.getQuantity()*100.0/100.0) - (ord.getQuantity()* ord.getOffer().getRestaurant_menu_price()));
                }
                ReceiptPrint receiptPrint = new ReceiptPrint(user, restaurant, cbPaymentType.getSelectedItem().toString(),
                        Double.parseDouble(lblTotalSavings.getText()), Double.parseDouble(lblFinaPrice.getText()),
                        completeOrderList, finalTax, Double.parseDouble(lblOffersTotalPrice.getText()));
                receiptPrint.setVisible(true);
            }
        });
    }

    private String convertToTwoDecimals(double finalPrice) {

        return (((int)finalPrice) + ".00");

    }


    private List<Order> returnCompleteOrderList(List<Offer> listAllMenues, List<Order> finalOrderList) {
        Order order;
        for (Order ord:finalOrderList             ) {
            for (Offer ofr:listAllMenues                 ) {

                if (ord.getOffer().getId_restaurant_menu() == ofr.getId_restaurant_menu()){
                    order = new Order(ord.getId_Order(), ofr, ord.getQuantity(), ord.getDinningTable());
                    completeOrderList.add(order);
                }
            }
        }return completeOrderList;
    }

    private void setOrdersIntoTable(List<Order> completeOrderList) {
        totalPrice = 0;
        dtm.setRowCount(0);
        for (Order ord : completeOrderList) {
            Object[] line = {ord.getOffer().getRestaurant_menu_name(),
            ord.getQuantity(), ord.getOffer().getRestaurant_menu_price(),
                    convertToTwoDecimals(ord.getOffer().getRestaurant_menu_price() * ord.getQuantity())};

            dtm.addRow(line);
            totalPrice += (ord.getOffer().getRestaurant_menu_price() * ord.getQuantity());
        }
        lblOffersTotalPrice.setText(convertToTwoDecimals(totalPrice));
    }

    public static double getPriceWithTax(Order ord) {
        double price = 0;
        if (ord.getOffer().getRestaurant_menu_type().equalsIgnoreCase(Constant_Offert_Type.ALCOHOLIC_DRINK.returnAlcoholicdrink())){
            price = Constant_Offert_Type.ALCOHOLIC_DRINK.returnAlcoholicdrinkTax();
        }else if (ord.getOffer().getRestaurant_menu_type().equalsIgnoreCase(Constant_Offert_Type.NON_ALCOHOLIC_DRINK.returnNonAlcoholicdrink())){
            price = Constant_Offert_Type.NON_ALCOHOLIC_DRINK.returnNonAlcoholicdrinkTax();
        }else if (ord.getOffer().getRestaurant_menu_type().equalsIgnoreCase(Constant_Offert_Type.FOOD.returnFood())){
            price = Constant_Offert_Type.FOOD.returnFoodTax();
        }return price*ord.getOffer().getRestaurant_menu_price();
    }



    private void setIntoFinalIDList(List<Integer> listIdOrders) {
        for (Integer inte : listIdOrders) {
            finalIDOrdersList.add(inte);
        }
    }
    private boolean isHappyHour() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        lblTime.setText(simpleDateFormat.format(new Date()));

        LocalTime time = LocalTime.parse(simpleDateFormat.format(new Date()));

        if(isBetween(time, LocalTime.of(Constants_Happy_Hour.HAPPY_HOUR_START.happyHourStart(), 0),
                LocalTime.of(Constants_Happy_Hour.HAPPY_HOUR_FINISH.happyHourEnds(), 0))) return true;
        else return false;
    }
    private void showHappyHourLabels() {
        if (isHappyHour()){
            lblHappyHour.setVisible(true);
            lblHappyHourSaving.setVisible(true);
        }
    }



    public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);

    }

        private void setCB() {
        cbPaymentType.addItem(Constants_Reduction.CASH.returnCash());
        cbPaymentType.addItem(Constants_Reduction.CREDIT_CARD.returnCreditCard());
        cbPaymentType.addItem(Constants_Reduction.CHECK_PAYMENT.returnCheckPayent());
        cbReduction.addItem(Constants_Reduction.REGULAR_CUSTOMER.returnRegularCustomer());
        cbReduction.addItem(Constants_Reduction.SPECIAL_DISCOUNT.returnSpecialDiscount());
        cbReduction.addItem("NO_REDUCTION");


    }



    private void setColumns() {
        Object[] columns = {"Offer name", "Quantity", "Price/piece", "Total Price"};
        dtm.addColumn(columns[0]);
        dtm.addColumn(columns[1]);
        dtm.addColumn(columns[2]);
        dtm.addColumn(columns[3]);
    }
    private String returnIDListToChar(List<Order> finalIDOrdersList) {
        String requestedCharList="";
        List<Integer>listidOrders = new ArrayList<>();

        for (Order ord:finalIDOrdersList             ) {
            listidOrders.add(ord.getId_Order());
        }
        for (Integer inte: listidOrders             ) {
            requestedCharList = requestedCharList + inte + ";";
        }
        return finalIDOrdersList.size() + ";" + requestedCharList;
    }


}
