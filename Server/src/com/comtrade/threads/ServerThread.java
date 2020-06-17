package com.comtrade.threads;

import com.code.constants.Constants_IP_Port;
import com.comtrade.controlerBL.ControlerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(Constants_IP_Port.PORT.getPort());
            while (true) {

                    Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket);
                    ControlerThread.getInstance().addClientThread(clientThread);

                executorService.execute(clientThread);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
