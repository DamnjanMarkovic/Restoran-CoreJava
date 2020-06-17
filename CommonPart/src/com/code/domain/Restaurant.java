package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Restaurant implements CommonDomain, Serializable {

    private int id_restaurant;
    private String name_restaurant;
    private String street;
    private int number;
    private String city;
    private String imageLocation;

    public Restaurant(){

    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Restaurant(int id_restaurant, String name_restaurant, String street, int number, String city, String imageLocation) {
        this.id_restaurant = id_restaurant;
        this.name_restaurant = name_restaurant;
        this.street = street;
        this.number = number;
        this.city = city;
        this.imageLocation = imageLocation;
    }

    public Restaurant(String name_restaurant, String street, int number, String city, String imageLocation) {
        this.name_restaurant = name_restaurant;
        this.street = street;
        this.number = number;
        this.city = city;
        this.imageLocation = imageLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id_restaurant == that.id_restaurant &&
                Objects.equals(name_restaurant, that.name_restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_restaurant, name_restaurant);
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public String getName_restaurant() {
        return name_restaurant;
    }

    public void setName_restaurant(String name_restaurant) {
        this.name_restaurant = name_restaurant;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String returnTableName() {
        return " restaurants ";
    }

    @Override
    public String saveReturnColums() {
        return " (name_restaurant, street, number, city, imageRestaurant) ";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return " (?,?,?,?,?) ";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, name_restaurant);
            preparedStatement.setString(2, street);
            preparedStatement.setInt(3, number);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, imageLocation);
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
        return " ? ";
    }

    @Override
    public String idColumnName() {
        return "id_restaurant";
    }

    @Override
    public String familiarColumns() {
        return " name_restaurant ";
    }

    @Override
    public PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, name_restaurant);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
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
