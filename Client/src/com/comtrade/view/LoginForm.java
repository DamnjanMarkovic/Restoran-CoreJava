package com.comtrade.view;

import com.code.constants.*;
import com.code.domain.ImageRestaurant;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;
import com.comtrade.proxy.Proxy;
import com.comtrade.proxy.ProxyLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LoginForm extends JFrame{
    private JPanel jPanel;
    private JTextField tfusername;
    private JPasswordField pfPassword;
    private JButton LOGINButton;
    private JLabel lblImage;
    private JLabel lblUsername;
    private JLabel lblPass;



    public static void main(String[] args) {

        LoginForm loginForm = new LoginForm();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
            }
        }
        loginForm.setVisible(true);
    }


    public LoginForm(){
        Random random = new Random();
        setContentPane(jPanel);
        setTitle("LOGIN FORM");
        setBounds(600,300,400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);


        lblUsername.setIcon(ImageRestaurant.getPhotoInTable(ConstantsImages.LOGIN.imageLoginUser()));
        lblPass.setIcon(ImageRestaurant.getPhotoInTable(ConstantsImages.LOGIN.imageLoginKey()));



        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userName = tfusername.getText();
                String password = String.copyValueOf(pfPassword.getPassword());
                User user = new User();
                user.setUsername(userName);
                user.setPassword(password);
                TransferClass transferClass;

                try {

                    if (ifuserNameExists(userName)) {

                        transferClass = TransferClass.create(user, ConstantsFC.USER, ConstantsBLC.GET_LOGIN);
                        Proxy proxy = new ProxyLogin();

                        user = (User) ControlerFront.getFrontControler().execute(transferClass).getResponse();

                        if(user.getuserFirstName().equalsIgnoreCase("AlreadyLoggedINUser")) {
                        JOptionPane.showMessageDialog(null, "User already Logged IN!");

                        } else {
                            if(user.getuserFirstName()!=null){
                            System.out.println("Korisnik " + user.getuserFirstName() + " se ulogovao");
                            proxy.login(user);
                            ControlerFront.getFrontControler().setUser(user);
                            dispose();
                        } else {
                            System.out.println("ne");
                            JOptionPane.showMessageDialog(null, "You're almost there! \nPlease enter correct password!");
                        }
                        }
                    } else {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean ifuserNameExists(String userName) throws Exception {
        boolean exists = false;

            TransferClass transferClass = TransferClass.create(userName, ConstantsFC.USER, ConstantsBLC.CONFIRM_USERNAME);
            transferClass.setSpecialMessage("poruka");
            exists = (boolean) ControlerFront.getFrontControler().execute(transferClass).getResponse();


        return exists;
    }


}
