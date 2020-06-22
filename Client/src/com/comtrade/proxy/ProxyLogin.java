package com.comtrade.proxy;

import com.code.domain.User;
import com.comtrade.view.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class ProxyLogin implements Proxy {




    @Override
    public void login(User user) throws Exception {

        Set<String> setRoleLabels = user.getSetUserRole().stream().map(rola -> rola.getLabel()).collect(Collectors.toSet());

        if (setRoleLabels.contains("owner")) {
            OwnerForm ownerForm = new OwnerForm(user);
            ownerForm.setVisible(true);

        } else if (setRoleLabels.contains("waiter_restaurant") ){
            WaiterEntryForm waiterEntryForm = new WaiterEntryForm(user);
            waiterEntryForm.setVisible(true);

        }else if (setRoleLabels.contains("manager_restaurant")){
            ManagerForm managerForm = new ManagerForm(user);
            managerForm.setVisible(true);
        }


    }
}
