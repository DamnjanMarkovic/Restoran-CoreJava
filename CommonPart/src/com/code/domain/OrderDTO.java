package com.code.domain;

import java.io.Serializable;

public class OrderDTO implements Serializable {

    private int id_order;
    private int id_offer;
    private int quantity;

    public OrderDTO(int id_order, int id_offer, int quantity) {
        this.id_order = id_order;
        this.id_offer = id_offer;
        this.quantity = quantity;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_offer() {
        return id_offer;
    }

    public void setId_offer(int id_offer) {
        this.id_offer = id_offer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
