package com.comtrade.systemOperation.ingredients;



import com.code.domain.Ingredient;
import com.code.domain.Offer;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetIngredientService extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {

            int idUser = (Integer) transferClass.getRequest();
            Broker broker = new Broker();
            transferClass.setResponse(broker.getIngredients(idUser));

    }


}
