package com.comtrade.systemOperation.user;

import com.code.domain.User;
import com.code.domain.UserDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import com.comtrade.systemOperation.GenericSystemOperation;

import java.sql.SQLException;

public class UserSaveService extends GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        UserDTO userDTO = (UserDTO) transferClass.getRequest();
        Broker broker = new Broker();
        broker.saveUser(userDTO);


    }
}
