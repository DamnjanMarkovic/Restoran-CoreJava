package com.comtrade.proxy;

import com.code.domain.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Proxy {
    void login(User user) throws IOException, ClassNotFoundException, InvocationTargetException, InterruptedException;
}
