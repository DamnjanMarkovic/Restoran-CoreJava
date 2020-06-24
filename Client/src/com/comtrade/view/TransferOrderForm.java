package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.domain.*;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class TransferOrderForm extends JDialog{
    private JPanel jPanel;
    private JTextField tfWrongTableNumber;
    private JTextField tfCorrectTableNumber;
    private JButton button1;
    private JTextField tfIdOrder;
    private User user;
    private Integer idTableNumberCorrect;
    private Restaurant restaurant;
    private Integer idOrder;



    public TransferOrderForm(User user, Restaurant restaurant) {
        Random random = new Random();
        setContentPane(jPanel);
        setBounds(300, 300, 300, 200);
        setDefaultLookAndFeelDecorated(true);
        this.user = user;
        this.restaurant = restaurant;
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (tfCorrectTableNumber.getText()!=null && tfIdOrder!=null){
                    try {
                        idTableNumberCorrect = returnTableID(Integer.valueOf(tfCorrectTableNumber.getText()));
                        idOrder = Integer.valueOf(tfIdOrder.getText());
                        TransferClass transferClass = TransferClass.create(idOrder, ConstantsFC.ORDER, ConstantsBLC.TRANSFER_ORDERS);
                        transferClass.setSpecialMessage(String.valueOf(idTableNumberCorrect));
                        try {
                            ControlerFront.getFrontControler().execute(transferClass);

                        } catch (Exception ef) {
                            ef.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "DONE");
                        dispose();
                    } catch (Exception ef) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }
                }


            }
        });
    }




    public Integer returnTableID(Integer tableNumber){
        DinningTable dinningTable = new DinningTable(tableNumber, restaurant.getId_restaurant());
        DinningTable dinningTable1 = null;
        TransferClass transferClass = TransferClass.create(dinningTable, ConstantsFC.RESTAURANT, ConstantsBLC.RETURN_DINNING_TABLE);
        try {
            dinningTable1 = (DinningTable) ControlerFront.getFrontControler().execute(transferClass).getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dinningTable1.getId_dinningTable();
    }

}
