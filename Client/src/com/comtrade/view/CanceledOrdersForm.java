package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.domain.OrderDeletionDTO;
import com.code.domain.Restaurant;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CanceledOrdersForm extends JDialog{
    private JPanel jPanel;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton CONFIRMORDERDELETIONButton;
    private JLabel lblUserPhoto;
    private JScrollPane jScrollPane1;
    private JTable table2;
    private JScrollPane jscrolPane2;
    private JComboBox comboBox2;
    private JButton button1;
    private JLabel lblRestaurantPhoto;
    private DefaultTableModel dtm1 = new DefaultTableModel();
    private List<OrderDeletionDTO> deletedOrdersList = null;
    private List<OrderDeletionDTO> newOrdersForDeletion = null;
    private ImageIcon userPhoto;
    private ImageIcon restaurantPhoto;


    public CanceledOrdersForm(User user, Restaurant restaurant, ImageIcon userPhoto, ImageIcon restaurantPhoto) throws InterruptedException, IOException, ClassNotFoundException {
        setContentPane(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setBounds(400,400,900,500);
        lblUserPhoto.setIcon(userPhoto);
        setTitle("RESTAURANT " +  restaurant.getName_restaurant().toUpperCase() + "\t WAITER NAME: "+ user.getuserFirstName().toUpperCase());
        table1 = new JTable(dtm1);
        jScrollPane1.setViewportView(table1);
        setTableColumns();
        setCBDeletion();
        lblUserPhoto.setIcon(userPhoto);
        lblRestaurantPhoto.setIcon(restaurantPhoto);
        deletedOrdersList = returnDeletedOrders(restaurant.getId_restaurant());
        newOrdersForDeletion = returnNewOrdersForDeletion(restaurant.getId_restaurant());
        setDataIntoTable(deletedOrdersList, newOrdersForDeletion, user);
        CONFIRMORDERDELETIONButton.setVisible(false);
        button1.setVisible(false);








        CONFIRMORDERDELETIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                TransferClass transferClass = TransferClass.create(newOrdersForDeletion, ConstantsFC.ORDER, ConstantsBLC.DELETE_ORDERS);
                transferClass.setMessage(String.valueOf(user.getid_user()));
                try {
                    ControlerFront.getFrontControler().execute(transferClass);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dtm1.setRowCount(0);
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dtm1.setRowCount(0);

                try {
                    deletedOrdersList = returnDeletedOrders(restaurant.getId_restaurant());
                    newOrdersForDeletion = returnNewOrdersForDeletion(restaurant.getId_restaurant());
                    setDataIntoTable(deletedOrdersList, newOrdersForDeletion, user);
                } catch (InterruptedException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setDataIntoTable(List<OrderDeletionDTO> deletionDTOList, List<OrderDeletionDTO> newOrdersForDeletion, User user) {

        if (comboBox1.getSelectedItem().toString().equalsIgnoreCase("NEW ORDERS FOR DELETION")){
            if (newOrdersForDeletion!=null && !newOrdersForDeletion.isEmpty()){
                CONFIRMORDERDELETIONButton.setVisible(true);
                for (OrderDeletionDTO ord:newOrdersForDeletion                 ) {

                    Object[] columns = {ord.getId_order(), ord.getOfferName(), ord.getOfferQuantity(), ord.getWaiterName(), ord.getDateTime(), user.getuserFirstName()};
                    dtm1.addRow(columns);

                }
            }


        }else if (comboBox1.getSelectedItem().toString().equalsIgnoreCase("DELETED ORDERS")){
            CONFIRMORDERDELETIONButton.setVisible(false);
            if (deletionDTOList!=null && !deletionDTOList.isEmpty()){
                for (OrderDeletionDTO ord:deletionDTOList                 ) {
                    Object[] columns = {ord.getId_order(), ord.getOfferName(), ord.getOfferQuantity(), ord.getWaiterName(), ord.getDateTime(), ord.getManagerName()};
                    dtm1.addRow(columns);


                }
            }
        }



    }

    private List<OrderDeletionDTO> returnNewOrdersForDeletion(int id_restaurant) throws InterruptedException, IOException, ClassNotFoundException {
        List<OrderDeletionDTO> newOrderForDeletion = null;
        TransferClass transferClass = TransferClass.create(id_restaurant, ConstantsFC.ORDER, ConstantsBLC.RETURN_ORDERS_FOR_DELETION);
        newOrderForDeletion = (List<OrderDeletionDTO>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        return newOrderForDeletion;
    }

    private List<OrderDeletionDTO> returnDeletedOrders(int id_restaurant) throws InterruptedException, IOException, ClassNotFoundException {
        List<OrderDeletionDTO> deletedOrdersList = null;
        TransferClass transferClass = TransferClass.create(id_restaurant, ConstantsFC.ORDER, ConstantsBLC.RETURN_DELETED_ORDERS);
        deletedOrdersList = (List<OrderDeletionDTO>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        return deletedOrdersList;
    }


    private void setCBDeletion() {
        Object[] item1 = {"NEW ORDERS FOR DELETION", "DELETED ORDERS"};
        comboBox1.addItem(item1[0]);
        comboBox1.addItem(item1[1]);

    }

    private void setTableColumns() {
        Object[] columns1 = {"idOrder", "offer name", "quantity", "waiter name", "time", "approved by"};
        dtm1.addColumn(columns1[0]);
        dtm1.addColumn(columns1[1]);
        dtm1.addColumn(columns1[2]);
        dtm1.addColumn(columns1[3]);
        dtm1.addColumn(columns1[4]);
        dtm1.addColumn(columns1[5]);



    }


}
