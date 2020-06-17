package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.constants.ConstantsInfoWrongInput;
import com.code.domain.Restaurant;
import com.code.domain.User;
import com.code.domain.UserDTO;
import com.code.domain.UserRole;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class NewUserForm extends JDialog{
    private JPanel jPanel;
    private JTextField tfName;

    private JButton button1;
    private JButton btnSaveNewUser;
    private JLabel lblLogoRestaurant;
    private JPasswordField pf1;
    private JTextField tfUserName;
    private JPasswordField pf2;
    private JComboBox cbRestaurant;
    private JComboBox cbRole;
    private String chosenPhotoLocation = null;
    List<UserRole> listRoles = null;
    private String roleSelected = null;
    private String restaurantSelected = null;
    private List<Restaurant>restaurantList = null;
    private int idRole;
    private int idRestaurant;
    Random random = new Random();



    public NewUserForm(User user) throws InterruptedException, IOException, ClassNotFoundException {

        add(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(400, 100, 500, 500);
        setCBRole(user);
        restaurantList = ManagerForm.getRestaurantsList(user);
        setCBRestaurant();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosenPhotoLocation = OffersForm.selectImage();
                if (chosenPhotoLocation!=null){
                    ImageIcon image1 = new ImageIcon(new ImageIcon(chosenPhotoLocation).getImage().
                            getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));

                    lblLogoRestaurant.setIcon(image1);
                }
            }
        });
        btnSaveNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (tfName.getText()!=null && tfUserName.getText()!=null && pf1.getPassword()!=null &&
                        pf2.getPassword()!=null && chosenPhotoLocation!=null &&
                        cbRestaurant.getSelectedItem()!=null && cbRole.getSelectedItem()!=null) {

                    String password = String.copyValueOf(pf1.getPassword());
                    String password2 = String.copyValueOf(pf2.getPassword());
                    if (password.equals(password2)) {

                        String name = tfName.getText();
                        String userName = tfUserName.getText();
                        idRole = getRole();
                        idRestaurant = getIdRestaurant();
                        UserDTO userDTO = new UserDTO(name, userName, password, chosenPhotoLocation, idRestaurant, idRole);
                        TransferClass transferClass = TransferClass.create(userDTO, ConstantsFC.USER, ConstantsBLC.POST);
                        try {
                            ControlerFront.getFrontControler().execute(transferClass);
                            dispose();
                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                    JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));

                }
            }

        });
        cbRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roleSelected = cbRole.getSelectedItem().toString();
            }
        });
        cbRestaurant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                restaurantSelected = cbRestaurant.getSelectedItem().toString();
            }
        });
    }

    private int getIdRestaurant() {

        for (Restaurant rest:restaurantList             ) {
            if (rest.getName_restaurant().equalsIgnoreCase(restaurantSelected)){
                idRestaurant = rest.getId_restaurant();
            }
        }return idRestaurant;

    }

    private int getRole() {

        for (UserRole ur:listRoles             ) {
            if (ur.getLabel().equalsIgnoreCase(roleSelected)){
                idRole = ur.getId_role();
            }
        }return idRole;

    }

    private void setCBRestaurant() {

        for (Restaurant rest: restaurantList             ) {
            cbRestaurant.addItem(rest.getName_restaurant());
        }
    }

    private void setCBRole(User user) throws InterruptedException, IOException, ClassNotFoundException {
        TransferClass transferClass = TransferClass.create(user.getid_user(), ConstantsFC.USER, ConstantsBLC.RETURN_ROLES);

        listRoles = (List<UserRole>) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        for (UserRole us:listRoles             ) {
            cbRole.addItem(us.getLabel());
        }
    }


}
