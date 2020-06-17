package com.comtrade.view;

//import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;

import javax.swing.*;


public class fileChooser {

    public static void main(String[] args) {

        JButton open = new JButton();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new java.io.File("Server/src/com/comtrade/images"));
        jFileChooser.setDialogTitle("PLEASE SELECT IMAGE");
        //JFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (jFileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){

        }
        if (jFileChooser.getSelectedFile()!=null) {

            String [] spliter = jFileChooser.getSelectedFile().getAbsolutePath().split("/");
            String requestedAdress=null;
            int lineBeginsIndex = 0;

            for (int i = 0; i <spliter.length ; i++) {
                if (spliter[i].equalsIgnoreCase("Server")){
                    lineBeginsIndex = i;
                }
            }
            int i = 0;
            for (i = lineBeginsIndex+i; i <spliter.length ; i++) {
                requestedAdress = requestedAdress + spliter[i] + "/";
            }

            assert requestedAdress != null;  requestedAdress = requestedAdress.substring(0, requestedAdress.length()-1);

            requestedAdress = requestedAdress.substring(4);

            System.out.println(requestedAdress);

        }

    }


}
