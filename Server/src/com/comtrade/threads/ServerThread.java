package com.comtrade.threads;

import com.code.constants.Constants_IP_Port;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
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
                    List<ClientThread> listClients = ControlerThread.getInstance().getListClients();

                        if(!listClients.contains(clientThread)){
                            ControlerThread.getInstance().addClientThread(clientThread);
                            executorService.execute(clientThread);
                        } else {
                            System.out.println("em");
                        }




            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }


    }

}


/*
                   Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket);
                try {

                    if(!ControlerThread.getInstance().getListClients().contains(clientThread)) {
                        ControlerThread.getInstance().addClientThread(clientThread);
                        executorService.execute(clientThread);
                    }
                } catch (Exception e){
                    System.out.println("vec ste logovani");
                }


            }
 */