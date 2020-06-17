package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.domain.ImageRestaurant;
import com.code.domain.Restaurant;
import com.code.domain.User;
import com.code.domain.UserDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerFront.ControlerFront;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class UsersForm extends JDialog{
    private JPanel jPanel;
    private JLabel lblUser;
    private DefaultTableModel dtm = new DefaultTableModel();
    private JTable table1;
    private JScrollPane jScrolPane;
    private JLabel lblAllUsers;
    private JButton btnNewUser;

    private List<Restaurant>restaurantsList = null;
    private ImageIcon userPhoto = null;
    private List <UserDTO> userDTOList = null;


    public UsersForm(User user) throws InterruptedException, IOException, ClassNotFoundException {

        add(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(400, 100, 800, 800);
        table1 = new JTable( dtm ) {
            public Class getColumnClass(int column){
                return getValueAt(0, column).getClass();
            }
        };
        table1.setRowHeight(124);
        table1.setGridColor(Color.BLACK);
        jScrolPane.setViewportView(table1);
        setColumns();
        lblUser.setIcon(ImageRestaurant.getPhoto(user.getImageLocation()));
        restaurantsList = ManagerForm.getRestaurantsList(user);
        setImages();
        settTableCellsAlignment(table1);
        setUsersInTable(user);


        btnNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                NewUserForm newUserForm = null;
                try {
                    newUserForm = new NewUserForm(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                newUserForm.setVisible(true);
                    dispose();
            }
        });
    }

    private void setUsersInTable(User user) throws InterruptedException, IOException, ClassNotFoundException {

        TransferClass transferClass = TransferClass.create(user.getid_user(), ConstantsFC.USER, ConstantsBLC.RETURN_USERS);

        userDTOList = (List<UserDTO>) ControlerFront.getFrontControler().execute(transferClass).getResponse();

        for(UserDTO userDTO: userDTOList){

            Object [] line = {ImageRestaurant.getPhotoInTable(userDTO.getPhotoLocation()), userDTO.getUserName(), userDTO.getNameRole(), userDTO.getNameRestaurant()};
            dtm.addRow(line);
        }

    }

    private void setImages() {

        ImageIcon allRestaurants = new ImageIcon(new ImageIcon((ConstantsImages.OWNER.ownerUsers())).getImage().
                getScaledInstance(500, 150, Image.SCALE_SMOOTH));
        lblAllUsers.setIcon(allRestaurants);

    }
    private void setColumns() {
        Object[]kolone = {"photo", "name", "role", "restaurant"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);
        dtm.addColumn(kolone[3]);

    }
    private ImageIcon getUserPhoto(User user) {
        userPhoto = new ImageIcon(new ImageIcon((user.getImageLocation())).getImage().
                getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        return userPhoto;
    }

    private void settTableCellsAlignment(JTable table1) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table1.getTableHeader().getDefaultRenderer();
        TableModel tableModel = table1.getModel();
        for (int columnIndex = 1; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table1.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JTableHeader header = table1.getTableHeader();
        header.setDefaultRenderer(headerRenderer);

    }


}
