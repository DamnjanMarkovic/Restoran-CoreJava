package com.code.misc;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;



    public class TableIcon extends JFrame
    {
        public TableIcon()
        {
            ImageIcon aboutIcon = new ImageIcon("/home/damnjan/Documents/Link to Java/CodeComtrade/Predavanja/ZavrsniRad/ZavrsniRad_46/CommonPart/src/com/code/imagesCommonPart/Margarite.jpg");
            ImageIcon addIcon = new ImageIcon("/home/damnjan/Documents/Link to Java/CodeComtrade/Predavanja/ZavrsniRad/ZavrsniRad_46/CommonPart/src/com/code/imagesCommonPart/Martini.jpg");
            ImageIcon copyIcon = new ImageIcon("/home/damnjan/Documents/Link to Java/CodeComtrade/Predavanja/ZavrsniRad/ZavrsniRad_46/CommonPart/src/com/code/imagesCommonPart/Mojito.jpeg");

            String[] columnNames = {"Picture", "Description"};
            Object[][] data =
                    {
                            {aboutIcon, "About"},
                            {addIcon, "Add"},
                            {copyIcon, "Copy"},
                    };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable( model )
            {
                //  Returning the Class of each column will allow different
                //  renderers to be used based on Class
                public Class getColumnClass(int column)
                {
                    return getValueAt(0, column).getClass();
                }
            };
            table.setPreferredScrollableViewportSize(table.getPreferredSize());

            JScrollPane scrollPane = new JScrollPane( table );
            getContentPane().add( scrollPane );
        }

        public static void main(String[] args)
        {
            TableIcon frame = new TableIcon();
            frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
            frame.pack();
            frame.setVisible(true);
        }

    }



