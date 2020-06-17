package com.comtrade.view;

import com.code.constants.*;
import com.code.domain.ImageRestaurant;
import com.code.domain.User;
import com.code.transferClass.TransferClass;
import com.comtrade.communicationClient.CommunicationClient;
import com.comtrade.controlerFront.ControlerFront;
import com.comtrade.proxy.Proxy;
import com.comtrade.proxy.ProxyLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
                try {
                    if (ifuserNameExists(userName)) {
                        TransferClass transferClass = TransferClass.create(user, ConstantsFC.USER, ConstantsBLC.GET_LOGIN);
                        Proxy proxy = new ProxyLogin();
                        user = (User) ControlerFront.getFrontControler().execute(transferClass).getResponse();
                        if (user!=null){
                            CommunicationClient.getInstance().setUserID(user.getid_user());
                            proxy.login(user);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null, "You're almost there! \nPlease enter correct password!");
                        }
                    } else {
                        int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                        JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));
                    }
                } catch (InterruptedException | IOException | InvocationTargetException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean ifuserNameExists(String userName) throws InterruptedException, IOException, ClassNotFoundException {
        boolean exists = false;
        try{
            TransferClass transferClass = TransferClass.create(userName, ConstantsFC.USER, ConstantsBLC.CONFIRM_USERNAME);
            exists = (boolean) ControlerFront.getFrontControler().execute(transferClass).getResponse();
        } catch (Exception e) {
        }
        return exists;
    }


}
