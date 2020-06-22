package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.emptyTest.EmptyTestService;

public class ControlerEmpty implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case EMPTY_TEST:
                genericSystemOperation = new EmptyTestService();
                break;

            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
