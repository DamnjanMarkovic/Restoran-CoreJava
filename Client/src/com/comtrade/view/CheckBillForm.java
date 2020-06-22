package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
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

public class CheckBillForm extends JDialog{
    private JPanel jPanel;
    private JLabel lblUserPhoto;
    private JTable table1;
    private JScrollPane scrolPane;
    private JButton btnBill;
    private JTextArea textArea1;
    private JTextField tfMessage;
    private JButton btnSendForRemoval;
    private JComboBox cbEmployees;
    private JButton REMOVEButton;
    private JLabel lblRestaurant;
    private JLabel lblMessageRequestText;
    private JLabel lbluser;
    private JButton CONFIRMButton;
    private JButton btnSendToOtherBill;
    private JComboBox cbTables;
    private JButton btnSendToOTherTable;
    private JLabel lblSelectTable;
    private DefaultTableModel dtm = new DefaultTableModel();
    private List<OrderDeletionDTO>listOrderForDeletion = new ArrayList<>();

    private Order order;
    private int row;
    private int idOrderForDeletion;
    private List<User>listRestaurantStaff;
    private TransferClass transferClass;
    private String firstMessage, nameReceiver, messageForSending;
    private Offer offer;
    private List<Order>finalOrderList = new ArrayList<>();
    private List<Order>fullOrderList = new ArrayList<>();



    public CheckBillForm(List<Offer> listAllMenues, User user, Restaurant restaurant, DinningTable dinningTable) throws Exception {
        setContentPane(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        REMOVEButton.setVisible(false);
        setBounds(600,400,600,400);
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t WAITER NAME: "+ user.getuserFirstName().toUpperCase());
        table1 = new JTable(dtm);
        scrolPane.setViewportView(table1);
        setColumns();
        fullOrderList = returnFullOrderList(dinningTable.getId_dinningTable());
        setDataIntoTable(fullOrderList);

        lblUserPhoto.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));
        lblRestaurant.setIcon(ImageRestaurant.getPhoto(restaurant.getImageLocation()));
        lblMessageRequestText.setVisible(false);
        tfMessage.setVisible(false);
        //setcbTables();
        //btnSendToOtherBill.setVisible(false);
        //lblSelectTable.setVisible(false);
        //cbTables.setVisible(false);
        //btnSendToOTherTable.setVisible(false);





        btnBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                finalOrderList = returnFinalOrderList(fullOrderList, listOrderForDeletion);

                BillForm billForm = new BillForm(listAllMenues, finalOrderList, user, restaurant, dinningTable);
                billForm.setVisible(true);
                dispose();
            }
        });


        REMOVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                OrderDeletionDTO orderDeletionDTO = new OrderDeletionDTO(idOrderForDeletion, restaurant.getId_restaurant(), user.getid_user());
                listOrderForDeletion.add(orderDeletionDTO);
                dtm.removeRow(row);
                transferClass = TransferClass.create(orderDeletionDTO, ConstantsFC.ORDER, ConstantsBLC.INSERT_ORDERS_FOR_DELETION);
                try {
                    ControlerFront.getFrontControler().execute(transferClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String sendingMessage = "Druze ajde skini ovu porudzbinu, molim te.";
                if (tfMessage!=null) {
                    sendingMessage = tfMessage.getText();
                }
                transferClass = TransferClass.create(user, ConstantsFC.CHAT, ConstantsBLC.REMOVE_WRONG_ORDER);
                transferClass.setMessage(sendingMessage);
                transferClass.setSpecialMessage(String.valueOf(idOrderForDeletion));
                try {
                    ControlerFront.getFrontControler().execute(transferClass);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                lblMessageRequestText.setVisible(false);
                tfMessage.setVisible(false);
                REMOVEButton.setVisible(false);

            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                row = table1.getSelectedRow();
                idOrderForDeletion = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());
                REMOVEButton.setVisible(true);
                lblMessageRequestText.setVisible(true);
                tfMessage.setVisible(true);
            }
        });
    }



    private List<Order> returnFinalOrderList(List<Order> fullOrderList, List<OrderDeletionDTO> listOrderForDeletion) {
        List<Order> finalList = new ArrayList<>();
        for (Order ord1:fullOrderList                     ) {
            if (listOrderForDeletion.isEmpty()){
                finalList.add(ord1);
            }else {
                for (OrderDeletionDTO ord2:listOrderForDeletion                         ) {
                    if (ord1.getId_Order()!=ord2.getId_order()){
                        finalList.add(ord1);
                    }
                }
            }
        }return finalList;
    }

    private List<Order> returnFullOrderList(int id_dinningTable) throws Exception {
        transferClass = TransferClass.create(id_dinningTable, ConstantsFC.ORDER, ConstantsBLC.GET_ORDERS_BASED_ON_TABLE);
            List<Order>listOrderFull = new ArrayList<>();
        try {
            listOrderFull = (List<Order>) ControlerFront.getFrontControler().execute(transferClass).getResponse();

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }return listOrderFull;
    }
/*
    private void setcbTables() {
        for (int i =1; i <10 ; i++) {
            cbTables.addItem("TABLE NUMBER: " + i);
        }
    }*/

    private void setColumns() {
            Object[] columns = {"idOrder", "Offer name", "Quantity", "Table number"};
            dtm.addColumn(columns[0]);
            dtm.addColumn(columns[1]);
            dtm.addColumn(columns[2]);
    }

    private void setDataIntoTable(List<Order> fullOrderList) {
        dtm.setRowCount(0);
        for (Order ord : fullOrderList) {
            Object[] line = {ord.getId_Order(), ord.getOffer().getRestaurant_menu_name(), ord.getQuantity(), ord.getDinningTable().getTable_number()};
            dtm.addRow(line);
        }
    }



}
