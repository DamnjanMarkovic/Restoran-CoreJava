package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Order implements CommonDomain, Serializable {

    private int id_Order;
    private Offer offer;
    private int quantity;
    private DinningTable dinningTable;
    private int idBill;




    public int getIdBill() {
        return idBill;
    }

    public Order(int id_Order) {
        this.id_Order = id_Order;
    }

    public Order(int id_Order, Offer offer, int quantity, DinningTable dinningTable) {
        this.id_Order = id_Order;
        this.offer = offer;
        this.quantity = quantity;
        this.dinningTable = dinningTable;
    }



    public Order(Offer offer, int quantity, DinningTable dinningTable) {
        this.offer = offer;
        this.quantity = quantity;
        this.dinningTable = dinningTable;
    }

    public Order(int id_Order, Offer offer, int quantity) {
        this.id_Order = id_Order;
        this.offer = offer;
        this.quantity = quantity;
    }

    public DinningTable getDinningTable() {
        return dinningTable;
    }

    public void setDinningTable(DinningTable dinningTable) {
        this.dinningTable = dinningTable;
    }

    public Order(Offer offer, int quantity) {
        this.offer = offer;
        this.quantity = quantity;

    }

    public int getId_Order() {
        return id_Order;
    }

    public void setId_Order(int id_Order) {
        this.id_Order = id_Order;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
