package com.comtrade.systemOperation.bill;

import com.code.domain.Bill;
import com.code.domain.BillDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnBillsService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        BillDTO billDTO = (BillDTO) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.getBills(billDTO));


    }
}
