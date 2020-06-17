package com.code.domain;

import com.code.constants.ConstantsBLC;
import com.code.constants.ConstantsFC;
import com.code.transferClass.TransferClass;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient implements Serializable, CommonDomain {

    private int id_ingredient;
    private String ingredient_name;
    private double purchase_price;
    private String quantity_measure;
    private double quantity;
    private int idRestaurant;



    public Ingredient(int id_ingredient, String ingredient_name, String quantity_measure, int idRestaurant) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
        this.quantity_measure = quantity_measure;
        this.idRestaurant = idRestaurant;
    }




    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Ingredient(int id_ingredient, String ingredient_name, String quantity_measure, double quantity) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
        this.quantity_measure = quantity_measure;
        this.quantity = quantity;
    }

    public Ingredient(String ingredient_name, double purchase_price, String quantity_measure, double quantity) {
        this.ingredient_name = ingredient_name;
        this.purchase_price = purchase_price;
        this.quantity_measure = quantity_measure;
        this.quantity = quantity;
    }

    public Ingredient(int id_ingredient, String ingredient_name, double purchase_price, String quantity_measure) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
        this.purchase_price = purchase_price;
        this.quantity_measure = quantity_measure;
    }

    public Ingredient(int id_ingredient, String ingredient_name, double purchase_price, String quantity_measure, double quantity) {
        this.id_ingredient = id_ingredient;
        this.ingredient_name = ingredient_name;
        this.purchase_price = purchase_price;
        this.quantity_measure = quantity_measure;
        this.quantity = quantity;
    }

    public Ingredient(int id_ingredient, String ingredient_name, double purchase_price) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredient_name, that.ingredient_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient_name);
    }

    public Ingredient() {
    }

    public Ingredient(String ingredient_name, double quantity) {
        this.ingredient_name = ingredient_name;
        this.quantity = quantity;

    }

    public Ingredient(int ingredient_id, double quantity) {
        this.id_ingredient = ingredient_id;
        this.quantity = quantity;
    }

    public int getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(int id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }


    public String getQuantity_measure() {
        return quantity_measure;
    }

    public void setQuantity_measure(String quantity_measure) {
        this.quantity_measure = quantity_measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String returnTableName() {
        return " ingredients ";
    }

    @Override
    public String saveReturnColums() {
        return " (ingredient_name, purchase_price, quantity_measure) ";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return " (?,?,?) ";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {//ovde fali kolicina!!!
        try {
            preparedStatement.setString(1, ingredient_name);
            preparedStatement.setDouble(2, purchase_price);
            preparedStatement.setString(3, quantity_measure);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }


    @Override
    public CommonDomain readObjects(ResultSet resultSet) {      //ovo nije dobro nedostaje kolicina
        Ingredient ingredient = null;
        try {
            ingredient = new Ingredient  (resultSet.getInt("id_ingredient"), resultSet.getString("ingredient_name"),
                    resultSet.getDouble("purchase_price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    @Override
    public String returnIDQuestionMarks() {
        return " ? ";
    }

    @Override
    public String idColumnName() {
        return "id_ingredient";
    }

    @Override
    public String familiarColumns() {
        return " ingredient_name ";
    }

    @Override
    public PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, ingredient_name);
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
