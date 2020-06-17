package com.comtrade.systemOperation.restaurantOffer;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnMenuBasedOnBillIdService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        int idBill = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        transferClass.setResponse(broker.getOffersBasedOnBillId(idBill));



    }
}
