package com.comtrade.systemOperation.images;

import com.code.domain.ImageRestaurant;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnImagesService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        ImageRestaurant imageRestaurant = (ImageRestaurant) transferClass.getRequest();
        Broker broker = new Broker();
        transferClass.setResponse(broker.get(imageRestaurant));


    }
}
