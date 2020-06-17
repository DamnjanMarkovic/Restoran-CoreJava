package com.comtrade.view;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsImages;
import com.code.domain.ImageRestaurant;
import com.code.domain.Ingredient;
import com.code.domain.Restaurant;
import com.code.domain.User;
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
import java.util.ArrayList;
import java.util.List;

public class RestaurantsForm extends JDialog{
    private JPanel jPanel;
    private JLabel lblUser;
    private DefaultTableModel dtm = new DefaultTableModel();
    private JTable table1;
    private JScrollPane jScrolPane;
    private JLabel lblAllrestaurant;
    private JButton NEWRESTAURANTButton;

    private List<Restaurant>restaurantsList = null;
    private ImageIcon userPhoto = null;


    public RestaurantsForm(User user) throws InterruptedException, IOException, ClassNotFoundException {

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
        setRestaurantsInTable(restaurantsList);


        NEWRESTAURANTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                    NewRestaurantForm newRestaurantForm = new NewRestaurantForm(user);
                    newRestaurantForm.setVisible(true);
                    dispose();
            }
        });
    }

    private void setRestaurantsInTable(List<Restaurant>restaurantsList) {
        dtm.setRowCount(0);
            for(Restaurant rest: restaurantsList){
                Object [] line = {ImageRestaurant.getPhotoInTable(rest.getImageLocation()), rest.getName_restaurant(), rest.getStreet(), rest.getNumber(), rest.getCity()};
                dtm.addRow(line);

            }
    }

    private void setImages() {

        ImageIcon allRestaurants = new ImageIcon(new ImageIcon((ConstantsImages.OWNER.ownerRestaurants())).getImage().
                getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH));
        lblAllrestaurant.setIcon(allRestaurants);

    }
    private void setColumns() {
        Object[]kolone = {"logo", "restaurant name","street", "number","city"};
        dtm.addColumn(kolone[0]);
        dtm.addColumn(kolone[1]);
        dtm.addColumn(kolone[2]);
        dtm.addColumn(kolone[3]);
        dtm.addColumn(kolone[4]);
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
