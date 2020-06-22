package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.chat.*;
import com.comtrade.systemOperation.user.ReturnLoggedUsers;
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
            case CHAT_LEAVING_MESSAGE:
                genericSystemOperation = new ChatLeavingMessage(clientThread);
                break;
            case LOGGING_OFF:
                genericSystemOperation = new LoogingOff(clientThread);
                break;
            case UPDATE_LOGGED_USERS_NAMES:
                genericSystemOperation = new UpdateLoggedUsersNames(clientThread);
                break;
            case OWNER_SENDING_MESSAGE:
                genericSystemOperation = new OwnerSendingMessage(clientThread);
                break;
            default:
                genericSystemOperation = new ReturnEmpty(clientThread);
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
