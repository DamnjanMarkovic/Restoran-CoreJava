package com.comtrade.systemOperation.chat;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.transferClass.TransferClass;
import com.comtrade.controlerBL.ControlerThread;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.threads.ClientThread;

import java.io.IOException;
import java.sql.SQLException;

public class ReturnEmpty extends GenericSystemOperation {
    private ClientThread clientThread;

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }


    public ReturnEmpty(ClientThread clientThread) {
        super();
    }

    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {
        setClientThread(clientThread);
        transferClass = TransferClass.create("CHAT", ConstantsFC.CHAT, ConstantsBLC.CHAT_LEAVING_MESSAGE);
        transferClass.setMessage("chat se salje");
        System.out.println("u chat sistemskoj operaciji ");
        ControlerThread.getInstance().informAllClients(transferClass, clientThread);

    }
}
