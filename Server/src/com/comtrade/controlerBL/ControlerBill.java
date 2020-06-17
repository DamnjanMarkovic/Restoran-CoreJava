package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.bill.PostBillService;
import com.comtrade.systemOperation.bill.ReturnBillsService;
import com.comtrade.systemOperation.restaurants.GetRestaurantService;


public class ControlerBill implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case POST_BILL:
                genericSystemOperation = new PostBillService();
                break;
            case RETURN_BILLS:
                genericSystemOperation = new ReturnBillsService();
                break;



            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
