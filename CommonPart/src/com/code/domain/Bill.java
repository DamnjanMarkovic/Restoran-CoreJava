package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Bill implements CommonDomain, Serializable {
    private int idBill;
    private int id_dinningTable;
    private Timestamp dateTime;
    private String username;
    private String paymentType;
    private double reduction;
    private double totalPrice;
    private int idRestaurant;
    private List<Order> orderList;

    public Bill(int idBill, int id_dinningTable, Timestamp dateTime, String username, String paymentType, double reduction, double totalPrice, int idRestaurant) {
        this.idBill = idBill;
        this.id_dinningTable = id_dinningTable;
        this.dateTime = dateTime;
        this.username = username;
        this.paymentType = paymentType;
        this.reduction = reduction;
        this.totalPrice = totalPrice;
        this.idRestaurant = idRestaurant;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Bill(int id_dinningTable, String username, String paymentType, double reduction, double totalPrice, int idRestaurant) {
        this.id_dinningTable = id_dinningTable;
        this.username = username;
        this.paymentType = paymentType;
        this.reduction = reduction;
        this.totalPrice = totalPrice;
        this.idRestaurant = idRestaurant;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public int getId_dinningTable() {
        return id_dinningTable;
    }

    public void setId_dinningTable(int id_dinningTable) {
        this.id_dinningTable = id_dinningTable;
    }

    public Bill(Timestamp dateTime, String username, String paymentType, double totalPrice, double reduction, int id_dinningTable) {
        this.dateTime = dateTime;
        this.username = username;
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
        this.reduction = reduction;
        this.id_dinningTable = id_dinningTable;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getUsername() {        return username;
    }

    public void setUsernamer(String username) {
        this.username = username;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
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
