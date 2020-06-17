package com.code.domain;

import java.io.Serializable;
import java.util.Objects;

public class DinningTable implements Serializable {
    private int id_dinningTable;
    private int table_number;
    private int id_restaurant;
    private int capacity;
    private int status;

    public DinningTable(int id_dinningTable, int table_number, int id_restaurant) {
        this.id_dinningTable = id_dinningTable;
        this.table_number = table_number;
        this.id_restaurant = id_restaurant;
    }

    public DinningTable(int table_number) {
        this.table_number = table_number;
    }

    public DinningTable(int table_number, int id_restaurant, int capacity, int status) {
        this.table_number = table_number;
        this.id_restaurant = id_restaurant;
        this.capacity = capacity;
        this.status = status;
    }

    public DinningTable(int id_dinningTable, int table_number, int id_restaurant, int capacity, int status) {
        this.id_dinningTable = id_dinningTable;
        this.table_number = table_number;
        this.id_restaurant = id_restaurant;
        this.capacity = capacity;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DinningTable that = (DinningTable) o;
        return id_dinningTable == that.id_dinningTable &&
                table_number == that.table_number &&
                id_restaurant == that.id_restaurant &&
                capacity == that.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_dinningTable, table_number, id_restaurant, capacity);
    }

    public DinningTable(int table_number, int id_restaurant) {
        this.table_number = table_number;
        this.id_restaurant = id_restaurant;
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_dinningTable() {
        return id_dinningTable;
    }

    public void setId_dinningTable(int id_dinningTable) {
        this.id_dinningTable = id_dinningTable;
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
