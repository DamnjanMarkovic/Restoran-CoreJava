package com.comtrade.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import java.util.Date;
import java.util.List;

import com.code.constants.Constant_Offert_Type;
import com.code.constants.ConstantsImages;
import com.code.constants.ConstantsInfoWrongInput;
import com.code.domain.Order;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.*;


public class ReceiptPrint extends JDialog{
    private JPanel jPanel;
    private JTextField tfCustomerName;
    private JTextField tfCompanyPIB;
    private JLabel lblName;
    private JButton btnPRINT;
    private JTextField tfCompanyName;
    private String customerName;
    private String companyName;
    private String companyPIB;
    private java.util.List<Order> completeOrderList = new ArrayList<>();
    private static String pdfFileName = "Customer receipt.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
    Random random = new Random();

    public ReceiptPrint(String waiterName, String restaurantName, String paymentType,double reduction, double finalPrice,
                        List<Order> completeOrderList, double finalTax, double totalPrice){
        setContentPane(jPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100,200,400,400);
        createFieldsForPrinting();




        btnPRINT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!tfCompanyName.getText().isEmpty() && !tfCompanyPIB.getText().isEmpty() && !tfCustomerName.getText().isEmpty() &&
                        tfCompanyName.getText() != null && tfCompanyPIB.getText() != null && tfCustomerName.getText() != null) {

                    customerName = tfCustomerName.getText();
                    companyName = tfCompanyName.getText();
                    companyPIB = tfCompanyPIB.getText();
                    printFileToPDF(waiterName, restaurantName, paymentType, reduction, finalPrice, customerName, companyName, companyPIB, completeOrderList, finalTax, totalPrice);
                    dispose();
                } else {
                    int res = random.nextInt(ConstantsImages.WRONG_INPUT.infoWrongInput().size());
                    JOptionPane.showMessageDialog(null, ConstantsImages.WRONG_INPUT.infoWrongInput().get(res));

                }
            }
        });

    }

    private void createFieldsForPrinting() {


    }


    private void printFileToPDF(String waiterName, String restaurantName, String paymentType, double reduction, double finalPrice,
                                String customerName, String companyName, String companyPIB, List<Order>completeOrderList, double finalTax, double totalPrice) {
        Document document = new Document();
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
            document.open();
            addMetaData(document);
            addStartPage(document, waiterName, restaurantName, customerName, companyName, companyPIB);
            createTable(document, completeOrderList);
            addEndPage(document, paymentType, reduction, finalPrice, finalTax, totalPrice);

            document.close();

        } catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void addEndPage(Document document, String paymentType, double reduction, double finalPrice, double finalTax, double totalPrice) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 4);

        preface.add(new Paragraph("Way of payment: " +  paymentType+ ".",  smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Full price without discount: " + totalPrice + ".",  smallBold));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Discount: " + reduction + "." ,  smallBold));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Tax: " + finalTax + ".",  smallBold));
        addEmptyLine(preface, 1);


        preface.add(new Paragraph("Final price: " + finalPrice + "." ,  smallBold));
        addEmptyLine(preface, 1);


        document.add(preface);


    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


    private void addStartPage(Document document, String waiterName, String restaurantName, String customerName, String companyName, String companyPIB) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("RECEIPT FOR THE CUSTOMER ", catFont));

        addEmptyLine(preface, 3);
        preface.add(new Paragraph("Time: " +  new Date(),  smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Served by: " + waiterName + ".",  smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Restaurant: " + restaurantName + ".",  smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Customer name: " + customerName + "." ,  smallBold));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Company name: " + companyName + "." ,  smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Customer PIB: " + companyPIB + ".",  smallBold));
        addEmptyLine(preface, 4);
        document.add(preface);


    }


    private static void createTable(Document document, List<Order>completeOrderList)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);

        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);


        PdfPCell c1 = new PdfPCell(new Phrase("Restaurant offer name"));

        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price per piece"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Total price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(c1);

        table.setHeaderRows(1);

        for (Order order:completeOrderList             ) {

            c1 = new PdfPCell(new Phrase(order.getOffer().getRestaurant_menu_name()));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
           

            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(order.getQuantity())));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(order.getOffer().getRestaurant_menu_price())));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(String.valueOf(Math.round(BillForm.getPriceWithTax(order) * order.getQuantity())*100.0/100.0)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(c1);



        }

        document.add(table);

    }



    private void addMetaData(Document document) {
        document.addTitle("RECEIPT FOR CUSTOMER");
        document.addAuthor("Damnjan");
        document.addCreator("Damnjan");

    }

}
