package com.comtrade.threads;

import com.code.constants.Constants_IP_Port;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerThread extends Thread {

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        ServerSocket serverSocket = null;
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(Constants_IP_Port.PORT.getPort());
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
