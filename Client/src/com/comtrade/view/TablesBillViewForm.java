package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.Constants_PaymentType;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TablesBillViewForm extends JDialog{
    private JPanel jPanel;
    private JComboBox cbTables;
    private JTable table1;
    private JScrollPane jScrollPane;
    private JPanel jPanelStartDate;
    private JPanel jPanelEndDate;
    private JComboBox cbPaymentType;
    private JButton btnBills;
    private JPanel panelOwner;
    private JComboBox cbEstablishment;
    private DefaultTableModel dtm = new DefaultTableModel();
    private Restaurant restaurant;
    private Calendar cld = Calendar.getInstance();
    private JDateChooser startDate =new JDateChooser(cld.getTime());
    private JDateChooser endDate = new JDateChooser(cld.getTime());
    private int idTable;
    private String paymentTypeChosen;
    private String startDateString;
    private String endDateString;
    private List<Bill> listBills = new ArrayList<>();
    private List<Offer>listAllMenues = null;
    private List<Restaurant>restaurantsList = null;
    private int row;




    public TablesBillViewForm(User user) throws InterruptedException, IOException, ClassNotFoundException {

        add(jPanel);
        setBounds(200,200,900,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table1 = new JTable(dtm);
        jScrollPane.setViewportView(table1);
        restaurantsList = ManagerForm.getRestaurantsList(user);
        //restaurant = restaurantsList.stream().findFirst().get();

        listAllMenues = AvailableMenuForm.returnAllListRestaurantMenues();

        setDates();
        Set<String> setRoleLabels = user.getSetUserRole().stream().map(rola -> rola.getLabel()).collect(Collectors.toSet());
        panelOwner.setVisible(false);
        setCBPaymentType();






        if (setRoleLabels.contains("owner")) {
            panelOwner.setVisible(true);
            restaurantsList.stream().forEach(r -> cbEstablishment.addItem(r.getName_restaurant()));
            setTitle("NAME: "+ user.getuserFirstName().toUpperCase());
            restaurant = selectRestaurant(restaurantsList);
        } else {
            restaurant = restaurantsList.stream().findFirst().get();
            setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t  NAME: "+ user.getuserFirstName().toUpperCase());
        }
        setCBTabless(restaurant);

        cbEstablishment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //restaurant = selectRestaurant(restaurantsList, cbEstablishment.getSelectedItem().toString());
                restaurant = selectRestaurant(restaurantsList);
                setCBTabless(restaurant);

            }
        });







        cbPaymentType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cbPaymentType.getSelectedItem().toString().equals(Constants_PaymentType.CASH.returnCash())){
                    paymentTypeChosen = Constants_PaymentType.CASH.returnCash();
                }else if (cbPaymentType.getSelectedItem().toString().equals(Constants_PaymentType.CHECK_PAYMENT.returnCheckPayent())){
                    paymentTypeChosen = Constants_PaymentType.CHECK_PAYMENT.returnCheckPayent();
                }else if (cbPaymentType.getSelectedItem().toString().equals(Constants_PaymentType.CREDIT_CARD.returnCreditCard())){
                    paymentTypeChosen = Constants_PaymentType.CREDIT_CARD.returnCreditCard();
                }else{
                    paymentTypeChosen = Constants_PaymentType.ALL_PAYMENT_TYPE.returnAllPaymentType();
                }
            }
        });
        cbTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (cbTables.getSelectedItem()!=null) {
                    if (cbTables.getSelectedItem().toString().contains("ALL TABLES")) {
                        idTable = -1;
                    } else {
                        idTable = Integer.parseInt(cbTables.getSelectedItem().toString().substring(cbTables.getSelectedItem().toString().length() - 1));
                    }
                }
            }
        });
        btnBills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setColumnsBill();
                pickUpDates();

                BillDTO billDTO = new BillDTO(idTable, startDateString, endDateString, paymentTypeChosen, restaurant.getId_restaurant());
                TransferClass transferClass = TransferClass.create(billDTO, ConstantsFC.BILL, ConstantsBLC.RETURN_BILLS);
                try {
                    listBills = (List<Bill>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dtm.setRowCount(0);
                if (listBills!=null && !listBills.isEmpty()){
                    for (Bill bill: listBills) {
                        Object [] line = {bill.getIdBill(), bill.getUsername(),  bill.getId_dinningTable(),
                                bill.getPaymentType(), bill.getDateTime(), bill.getReduction(),
                                bill.getTotalPrice()};
                        dtm.addRow(line);
                    }
                }
            }
        });


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                List<Offer>listOfferBasedOnBill = new ArrayList<>();
                row = table1.getSelectedRow();
                int idBill = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());

                TransferClass transferClass = TransferClass.create(null, ConstantsFC.RESTAURANT_MENU, ConstantsBLC.RETURN_MENU_BASED_ON_BILL_ID);
                transferClass.setMessage(String.valueOf(idBill));
                try {
                    listOfferBasedOnBill = (List<Offer>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
                } catch (IOException | ClassNotFoundException | InterruptedException ef) {
                    ef.printStackTrace();
                }
                TablesOfferViewForm tablesOfferViewForm = new TablesOfferViewForm(listOfferBasedOnBill, listAllMenues, user, restaurant);
                tablesOfferViewForm.setVisible(true);
            }
        });
    }

    private Restaurant selectRestaurant(List<Restaurant> restaurantsList) {


        for (Restaurant rest:restaurantsList             ) {
            if (cbEstablishment.getSelectedItem().toString().equalsIgnoreCase(rest.getName_restaurant())){
                restaurant = rest;
            }
        }

        return restaurant;
    }





    private void pickUpDates() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDateString =  simpleDateFormat.format(startDate.getDate());
        endDateString =  simpleDateFormat.format(endDate.getDate());
    }

    private void setDates() {
        startDate.setDateFormatString("yyyy-MM-dd");
        endDate.setDateFormatString("yyyy-MM-dd");
        jPanelStartDate.add(startDate);
        jPanelEndDate.add(endDate);
    }



    private void setCBTabless(Restaurant restaurant) {

        if (restaurant!=null){

            cbTables.removeAllItems();
            for (int i =1; i <10 ; i++) {
                cbTables.addItem("RESTAURANT: " + restaurant.getName_restaurant() + "\t TABLE NUMBER: " + i);
            }
            cbTables.addItem("RESTAURANT: " + restaurant.getName_restaurant() + "\t ALL TABLES");
        }

    }
    private void setCBPaymentType() {

            cbPaymentType.addItem(Constants_PaymentType.CASH);
            cbPaymentType.addItem(Constants_PaymentType.CHECK_PAYMENT);
            cbPaymentType.addItem(Constants_PaymentType.CREDIT_CARD);
            cbPaymentType.addItem("ALL");

    }

    private void setColumnsBill() {
        dtm.setColumnCount(0);
        Object[]kolone = {"ID BILL", "WAITER NAME", "TABLE", "PAYMENT TYPE", "TIME","REDUCTION", "TOTAL AMOUNT"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);
        dtm.addColumn(kolone[3]);
        dtm.addColumn(kolone[4]);
        dtm.addColumn(kolone[5]);
        dtm.addColumn(kolone[6]);
    }


}
