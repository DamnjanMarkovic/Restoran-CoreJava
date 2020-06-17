package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.restaurants.*;

public class ControlerRestaurant implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case GET_RESTAURANT:
                genericSystemOperation = new GetRestaurantService();
                break;
            case GET_RESTAURANTS:
                genericSystemOperation = new GetRestaurantsService();
                break;
            case RETURN_DINNING_TABLE:
                genericSystemOperation = new ReturnTableService();
                break;
            case RETURN_AVAILABLE_TABLES:
                genericSystemOperation = new ReturnAvailableTablesService();
                break;
            case PUT:
                genericSystemOperation = new SaveNewRestaurantService();
                break;


            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
