package com.comtrade.systemOperation.order;

import com.code.domain.OrderDeletionDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DeleteOrdersService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        List<OrderDeletionDTO>orderDeletionDTOList = (List<OrderDeletionDTO>) transferClass.getRequest();
        int idManager = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        broker.deleteOrders(orderDeletionDTOList, idManager);



    }
}
