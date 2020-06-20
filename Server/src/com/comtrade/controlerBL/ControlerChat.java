package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.chat.RemoveWronOrders;
import com.comtrade.systemOperation.user.ReturnLoggedUsers;
import com.comtrade.systemOperation.chat.PostMessageService;
import com.comtrade.systemOperation.chat.ReturnEmpty;
import com.comtrade.systemOperation.chat.ReturnStaffService;
import com.comtrade.threads.ClientThread;

public class ControlerChat implements CommandBase {
    private ClientThread clientThread;

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public ControlerChat(ClientThread clientThread) {
        this.clientThread = clientThread;
    }




    @Override
    public void execute(TransferClass transferClass) {
        setClientThread(clientThread);
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case POST_MESSAGE:
                genericSystemOperation = new PostMessageService(clientThread);
                break;
            case CHAT_RETURN_RESTAURANT_STAFF:
                genericSystemOperation = new ReturnStaffService(clientThread);
                break;
            case CHAT_TESTING:
                genericSystemOperation = new ReturnLoggedUsers(clientThread);
                break;
            case REMOVE_WRONG_ORDER:
                genericSystemOperation = new RemoveWronOrders(clientThread);
                break;

            default:
                genericSystemOperation = new ReturnEmpty(clientThread);
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
