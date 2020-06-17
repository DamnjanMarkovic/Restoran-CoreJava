package com.comtrade.systemOperation.bill;

import com.code.domain.Bill;
import com.code.domain.Offer;
import com.code.domain.Order;
import com.code.domain.OrderDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostBillService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {


        Bill bill = (Bill) transferClass.getRequest();
        Broker broker = new Broker();
        int idBill = broker.postBillAndReturnID(bill);
        broker.insertDataIntoBillOrders(idBill, prepareLlistIDOrders(transferClass.getMessage()));
        broker.insertIdBillIntoOrderOffer(idBill, prepareLlistIDOrders(transferClass.getMessage()));
    }


    private List<Integer> prepareLlistIDOrders(String message) {
        String[] tokens = message.split(";");
        int number = Integer.parseInt(tokens[0])+1;
        List<Integer>listIDOrders = new ArrayList<>();
        for (int i = 1; i <number; i++) {
            listIDOrders.add(Integer.parseInt(tokens[i]));
        }return listIDOrders;
    }
}

