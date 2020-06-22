package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.domain.DTOUserLogging;
import com.code.domain.ImageRestaurant;
import com.code.domain.User;
import com.code.domain.UserDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class UserLogging extends JDialog  {
    private DefaultTableModel dtm = new DefaultTableModel();
    private JPanel jPanel;
    private JTable table1;
    private JScrollPane scrolPane;
    private User user;
    private List<DTOUserLogging> listUserLoggin;

    public UserLogging (User user, List<DTOUserLogging>listUserLoggin){
        add(jPanel);
        setBounds(200,200,900,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        table1 = new JTable(dtm);
        scrolPane.setViewportView(table1);
        this.user = user;
        this.listUserLoggin = listUserLoggin;
        setColumnHeaders();
        setLoggingInTable(user, listUserLoggin);

    }

    private void setColumnHeaders() {
        Object[]kolone = {"Waiter Name", "Log-IN time", "Log-OFF time"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);
    }

    private void setLoggingInTable(User user, List<DTOUserLogging> listUserLoggin) {


        for(DTOUserLogging userDTO: listUserLoggin){

            Object [] line = {userDTO.getUserFirstName(), userDTO.getLoginTime(), userDTO.getLogoutTime()};
            dtm.addRow(line);
        }


    }
}
