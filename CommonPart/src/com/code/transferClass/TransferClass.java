package com.code.transferClass;

import com.code.constants.ConstantsFC;
import com.code.constants.ConstantsBLC;

import java.io.Serializable;

public class  TransferClass implements Serializable {

    private Object request;
    private String message;
    private ConstantsFC constantsFC;
    private ConstantsBLC constantsBLC;
    private Object response;
    private String specialMessage;


    public synchronized static TransferClass create(Object request, ConstantsFC constantsFC, ConstantsBLC constantsBLC) {
        TransferClass transferClass = new TransferClass();
        transferClass.setRequest(request);
        transferClass.setConstantsBLC(constantsBLC);
        transferClass.setConstantsFC(constantsFC);
        return transferClass;
    }


    public String getSpecialMessage() {
        return specialMessage;
    }

    public void setSpecialMessage(String specialMessage) {
        this.specialMessage = specialMessage;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConstantsFC getConstantsFC() {
        return constantsFC;
    }

    public void setConstantsFC(ConstantsFC constantsFC) {
        this.constantsFC = constantsFC;
    }

    public ConstantsBLC getConstantsBLC() {
        return constantsBLC;
    }

    public void setConstantsBLC(ConstantsBLC constantsBLC) {
        this.constantsBLC = constantsBLC;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
