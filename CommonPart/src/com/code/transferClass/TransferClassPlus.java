package com.code.transferClass;

public class TransferClassPlus {


    private TransferClass transferClass;



    public TransferClassPlus() {

    }

    public synchronized TransferClass getTransferClass() throws InterruptedException {

        wait();
        return transferClass;

    }

    public synchronized void setTransferClass(TransferClass transferClass) {
        this.transferClass = transferClass;
        notifyAll();
    }
}
