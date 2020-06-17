package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.user.*;
import com.comtrade.threads.ClientThread;

public class ControlerUser implements CommandBase{

    private final ClientThread clientThread;
    public ControlerUser(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case POST:
                genericSystemOperation = new UserSaveService();
                break;
            case GET_LOGIN:
                genericSystemOperation = new UserLoginServis(clientThread);
                break;
            case RETURN_USERS:
                genericSystemOperation = new ReturnUsersServis();
                break;
            case CONFIRM_USERNAME:
                genericSystemOperation = new ConfirmUsersServis();
                break;
            case RETURN_ROLES:
                genericSystemOperation = new ReturnRolesServis();
                break;

            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
