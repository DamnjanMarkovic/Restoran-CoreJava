package com.code.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class OrderDeletionDTO implements Serializable {

    private int id_order;
    private int id_restaurant;
    private int id_waiter;
    private Timestamp dateTime;
    private int id_manager;
    private String offerName;
    private int offerQuantity;
    private String waiterName;
    private String managerName;

    public OrderDeletionDTO(int id_order,  String offerName, int offerQuantity, String waiterName, Timestamp dateTime) {
        this.id_order = id_order;
        this.offerName = offerName;
        this.offerQuantity = offerQuantity;
        this.waiterName = waiterName;
        this.dateTime = dateTime;
    }
    public OrderDeletionDTO(int id_order,  String offerName, int offerQuantity, String waiterName, Timestamp dateTime, String managerName) {
        this.id_order = id_order;
        this.offerName = offerName;
        this.offerQuantity = offerQuantity;
        this.waiterName = waiterName;
        this.dateTime = dateTime;
        this.managerName = managerName;
    }





    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public int getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(int offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDeletionDTO that = (OrderDeletionDTO) o;
        return id_order == that.id_order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_order);
    }

    public OrderDeletionDTO(int id_order, int id_restaurant, int id_waiter) {
        this.id_order = id_order;
        this.id_restaurant = id_restaurant;
        this.id_waiter = id_waiter;
    }

    public OrderDeletionDTO(int id_order, int id_restaurant, int id_waiter, Timestamp dateTime) {
        this.id_order = id_order;
        this.id_restaurant = id_restaurant;
        this.id_waiter = id_waiter;
        this.dateTime = dateTime;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getId_waiter() {
        return id_waiter;
    }

    public void setId_waiter(int id_waiter) {
        this.id_waiter = id_waiter;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
    }
}
