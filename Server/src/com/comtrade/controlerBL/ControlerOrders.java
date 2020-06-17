package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.order.*;

public class ControlerOrders implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case POST_ORDER_AND_RETURN_IDS:
                genericSystemOperation = new PostOrderAndReturnIDsService();
                break;
            case RETURN_TAKEN_ORDERS:
                genericSystemOperation = new ReturnTakenOrdersService();
                break;
            case REMOVE_ORDERS:
                genericSystemOperation = new RemoveOrdersService();
                break;
            case GET_ORDERS_BASED_ON_TABLE:
                genericSystemOperation = new GetOrdersBasedOnTableService();
                break;
            case INSERT_ORDERS_FOR_DELETION:
                genericSystemOperation = new InsertOrdersForDeletionService();
                break;
            case RETURN_ORDERS_FOR_DELETION:
                genericSystemOperation = new ReturnOrdersForDeletionService();
                break;
            case DELETE_ORDERS:
                genericSystemOperation = new DeleteOrdersService();
                break;
            case RETURN_DELETED_ORDERS:
                genericSystemOperation = new ReturnDeletedOrdersService();
                break;

            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
