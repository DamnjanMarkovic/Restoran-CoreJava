package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailableIngredients implements CommonDomain, Serializable {

    private int id_ingredientsAvailable;
    private int id_restaurant;
    private int id_ingredients;
    private double quantity;

    public AvailableIngredients(int id_restaurant, int id_ingredients, double quantity) {
        this.id_restaurant = id_restaurant;
        this.id_ingredients = id_ingredients;
        this.quantity = quantity;
    }

    public int getId_ingredientsAvailable() {
        return id_ingredientsAvailable;
    }

    public void setId_ingredientsAvailable(int id_ingredientsAvailable) {
        this.id_ingredientsAvailable = id_ingredientsAvailable;
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public int getId_ingredients() {
        return id_ingredients;
    }

    public void setId_ingredients(int id_ingredients) {
        this.id_ingredients = id_ingredients;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String returnTableName() {
        return " available_ingredients ";
    }

    @Override
    public String saveReturnColums() {
        return "(id_restaurant, id_ingredients, quantity)";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return "(?,?,?)";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {

        try {
            preparedStatement.setInt(1, id_restaurant);
            preparedStatement.setInt(2, id_ingredients);
            preparedStatement.setDouble(3, quantity);
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
        return " id_restaurant = ? and id_ingredients ";
    }

    @Override
    public String return1QuestionMark() {
        return " ? ";
    }

    @Override
    public PreparedStatement returnDeletePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setInt(1, id_restaurant);
            preparedStatement.setInt(2, id_ingredients);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }





}
