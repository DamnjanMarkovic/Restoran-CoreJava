package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class BillDTO implements CommonDomain, Serializable {

    private int id_dinningTable;
    private String dateStart;
    private String dateEnd;
    private String paymentType;
    private int idRestaurant;

    public BillDTO(int id_dinningTable, String dateStart, String dateEnd, String paymentType, int idRestaurant) {
        this.id_dinningTable = id_dinningTable;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.paymentType = paymentType;
        this.idRestaurant = idRestaurant;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public int getId_dinningTable() {
        return id_dinningTable;
    }

    public void setId_dinningTable(int id_dinningTable) {
        this.id_dinningTable = id_dinningTable;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }





    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String returnTableName() {
        return null;
    }

    @Override
    public String saveReturnColums() {
        return null;
    }

    @Override
    public String saveReturnQuestionMarks() {
        return null;
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        return null;
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
