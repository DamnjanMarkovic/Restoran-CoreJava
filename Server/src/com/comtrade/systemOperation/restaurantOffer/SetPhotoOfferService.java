package com.comtrade.systemOperation.restaurantOffer;

import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;

public class SetPhotoOfferService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        String photoLocation = (String) transferClass.getRequest();
        int idOffer = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        broker.setPhotoLocation(photoLocation, idOffer);

    }
}
