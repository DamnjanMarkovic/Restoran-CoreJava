package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.bill.PostBillService;
import com.comtrade.systemOperation.images.ReturnImagesService;

public class ControlerImages implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case GET:
                genericSystemOperation = new ReturnImagesService();
                break;



            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }

}
