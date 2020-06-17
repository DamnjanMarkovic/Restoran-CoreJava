package com.comtrade.systemOperation;


import com.code.transferClass.TransferClass;
import com.comtrade.sqlConnection.SQLConnection;

import java.io.IOException;
import java.sql.SQLException;

public abstract class GenericSystemOperation {

    public void executeSystemOperation(TransferClass transferClass){

        try {
            startTransaction();
            executeSpecificOperation(transferClass);
            confirmTransaction();
        }catch (Exception e){
            cancelTransaction();
            transferClass.setMessage(e.getMessage());

        }finally {
            closeConnection();
        }

    }

    public abstract void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException;

    public void confirmTransaction() {
        SQLConnection.getInstance().confirmTransaction();
    }

    public void cancelTransaction() {
        SQLConnection.getInstance().cancelTransaction();
    }

    public void startTransaction(){
        SQLConnection.getInstance().startTransaction();
    }

    public void closeConnection(){
        SQLConnection.getInstance().closeConnection();
    }


}
