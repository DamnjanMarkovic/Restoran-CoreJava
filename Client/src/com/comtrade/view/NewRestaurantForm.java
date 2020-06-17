package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.constants.ConstantsInfoWrongInput;
import com.code.domain.ImageRestaurant;
import com.code.domain.Restaurant;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class NewRestaurantForm extends JDialog{
    private JPanel jPanel;
    private JTextField tfName;
    private JTextField tfStreet;
    private JTextField tfNumber;
    private JTextField tfCity;
    private JButton button1;
    private JButton SAVENEWRESTAURANTButton;
    private JLabel lblLogoRestaurant;
    private String chosenPhotoLocation = null;



    public NewRestaurantForm(User user){
        Random random = new Random();
        add(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(400, 100, 500, 500);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosenPhotoLocation =OffersForm.selectImage();
                if (chosenPhotoLocation!=null){

                    ImageIcon image1 = new ImageIcon(new ImageIcon(chosenPhotoLocation).getImage().
                            getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));


                    lblLogoRestaurant.setIcon(image1);
                }
            }
        });
        SAVENEWRESTAURANTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (tfName.getText()!=null && tfCity.getText()!=null && tfNumber.getText()!=null &&
                tfStreet.getText()!=null && chosenPhotoLocation!=null) {

                    String name = tfName.getText();
                    String city = tfCity.getText();
                    String street = tfStreet.getText();
                    try {
                        int number = Integer.parseInt(tfNumber.getText());
                        if (number>0) {
                            Restaurant restaurant = new Restaurant(name, street, number, city, chosenPhotoLocation);
                            TransferClass transferClass = TransferClass.create(restaurant, ConstantsFC.RESTAURANT, ConstantsBLC.PUT);
                            transferClass.setMessage(String.valueOf(user.getid_user()));
                            ControlerFront.getFrontControler().execute(transferClass);
                            clearFields();
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "Home number must be more then 0");

                        }
                    } catch (Exception e) {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));

                    }


                } else {
                    int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                    JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));

                }

            }
        });
    }

    private void clearFields() {
        tfCity.setText("");
        tfName.setText("");
        tfNumber.setText("");
        tfStreet.setText("");
    }


}
