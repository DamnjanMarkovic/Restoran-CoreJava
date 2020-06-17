package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Message implements CommonDomain, Serializable {

    private int id_message;
    private String message;
    private int idSender;

    public Message(String message, int idSender) {
        this.message = message;
        this.idSender = idSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
/*
    public synchronized String getMessage() throws InterruptedException {
            wait();
        return message;

    }

    public synchronized void setMessage(String message) {
        this.message = message;
        notifyAll();

    }*/

    public Message(String message) {
        this.message = message;
    }

    public Message() {

    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }



    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    @Override
    public String returnTableName() {
        return " messages ";
    }

    @Override
    public String saveReturnColums() {
        return " (message, id_user) ";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return " (?,?) ";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, getMessage());
            preparedStatement.setInt(2, getIdSender());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    public Object readObjects(ResultSet resultSet) {
        return null;
    }

    @Override
    public String returnIDQuestionMarks() {
        return null;
    }

    @Override
    public String idColumnName() {
        return null;
    }

    @Override
    public String familiarColumns() {
        return null;
    }

    @Override
    public PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement) {
        return null;
    }

    @Override
    public String returnColumnforDelete() {
        return null;
    }

    @Override
    public String return1QuestionMark() {
        return null;
    }

    @Override
    public PreparedStatement returnDeletePreparedStatement(PreparedStatement preparedStatement) {
        return null;
    }


}
