package com.comtrade.view;

import com.code.constants.ConstantsImages;
import com.comtrade.threads.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerForm extends JFrame{
    private JPanel jPanel;
    private JButton btnStartServer;
    private JLabel lblSat;
    private JButton btnServerRunning;
    private JLabel lblServerImage;

    public static void main(String[] args) {
        ServerForm serverForm = new ServerForm();
        serverForm.setVisible(true);
    }

    public ServerForm(){

        setContentPane(jPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(1000,100,400,400);
        setDefaultLookAndFeelDecorated(true);

        btnServerRunning.setVisible(false);
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(ConstantsImages.SERVER.serverLogin()).getImage().getScaledInstance(400,250, java.awt.Image.SCALE_DEFAULT));
        lblServerImage.setIcon(imageIcon1);






        btnStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ServerThread serverThread = new ServerThread();
                serverThread.start();
                ClockThread clockThread = new ClockThread(lblSat);

                btnServerRunning.setVisible(true);
                btnStartServer.setVisible(false);

            }
        });
    }
}
