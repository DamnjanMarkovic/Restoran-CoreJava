package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class Offer implements Serializable, CommonDomain {

    private int id_restaurant_menu;
    private String restaurant_menu_name;
    private double restaurant_menu_price;
    private String restaurant_menu_type;
    private Ingredient ingredient = new Ingredient();
    private List<Ingredient> menuIngredientList = new ArrayList<>();

    public Offer(int id_restaurant_menu, String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type, List<Ingredient> menuIngredientList) {
        this.id_restaurant_menu = id_restaurant_menu;
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
        this.menuIngredientList = menuIngredientList;
    }

    public Offer(int id_restaurant_menu, String restaurant_menu_name) {
        this.id_restaurant_menu = id_restaurant_menu;
        this.restaurant_menu_name = restaurant_menu_name;
    }

    public Offer(String restaurant_menu_name) {
        this.restaurant_menu_name = restaurant_menu_name;
    }

    public Offer(int id_restaurant_menu) {
        this.id_restaurant_menu = id_restaurant_menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id_restaurant_menu == offer.id_restaurant_menu &&
                Double.compare(offer.restaurant_menu_price, restaurant_menu_price) == 0 &&
                Objects.equals(restaurant_menu_name, offer.restaurant_menu_name) &&
                Objects.equals(restaurant_menu_type, offer.restaurant_menu_type) &&
                Objects.equals(ingredient, offer.ingredient) &&
                Objects.equals(menuIngredientList, offer.menuIngredientList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_restaurant_menu, restaurant_menu_name, restaurant_menu_price, restaurant_menu_type, ingredient, menuIngredientList);
    }

    public Offer(String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type, Ingredient ingredient) {
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
        this.ingredient = ingredient;
    }

    public Offer(int id_restaurant_menu, String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type) {
        this.id_restaurant_menu = id_restaurant_menu;
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
    }

    public Offer(String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type) {
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
    }

    public Offer(int id_restaurant_menu, String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type, Ingredient ingredient) {
        this.id_restaurant_menu = id_restaurant_menu;
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getRestaurant_menu_type() {
        return restaurant_menu_type;
    }

    public void setRestaurant_menu_type(String restaurant_menu_type) {
        this.restaurant_menu_type = restaurant_menu_type;
    }

    public Offer(String restaurant_menu_name, double restaurant_menu_price, String restaurant_menu_type, List<Ingredient> menuIngredientList) {
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
        this.restaurant_menu_type = restaurant_menu_type;
        this.menuIngredientList = menuIngredientList;
    }

    public List<Ingredient> getMenuIngredientList() {
        return menuIngredientList;
    }

    public void setMenuIngredientList(List<Ingredient> menuIngredientList) {
        this.menuIngredientList = menuIngredientList;
    }

    public Offer(int id_restaurant_menu, String restaurant_menu_name, double restaurant_menu_price) {
        this.id_restaurant_menu = id_restaurant_menu;
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;

    }

    public Offer() {

    }


    public Offer(String restaurant_menu_name, double restaurant_menu_price) {
        this.restaurant_menu_name = restaurant_menu_name;
        this.restaurant_menu_price = restaurant_menu_price;
    }


    public int getId_restaurant_menu() {
        return id_restaurant_menu;
    }

    public void setId_restaurant_menu(int id_restaurant_menu) {
        this.id_restaurant_menu = id_restaurant_menu;
    }

    public String getRestaurant_menu_name() {
        return restaurant_menu_name;
    }

    public void setRestaurant_menu_name(String restaurant_menu_name) {
        this.restaurant_menu_name = restaurant_menu_name;
    }

    public double getRestaurant_menu_price() {
        return restaurant_menu_price;
    }

    public void setRestaurant_menu_price(double restaurant_menu_price) {
        this.restaurant_menu_price = restaurant_menu_price;
    }

    @Override
    public String returnTableName() {
        return " restaurant_offer ";
    }

    @Override
    public String saveReturnColums() {
        return " (restaurant_offer_name, restaurant_offer_price, offer_type) ";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return " (?,?,?) ";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, restaurant_menu_name);
            preparedStatement.setDouble(2, restaurant_menu_price);
            preparedStatement.setString(3, restaurant_menu_type);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    public CommonDomain readObjects(ResultSet resultSet) {
        Offer offer = null;
        try {
            offer = new Offer(resultSet.getInt("id_restaurant_offer"),
                    resultSet.getString("restaurant_offer_name"),resultSet.getDouble("restaurant_offer_price"),
                    resultSet.getString("offer_type"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }

    @Override
    public String returnIDQuestionMarks() {
        return null;
    }

    @Override
    public String idColumnName() {
        return " id_restaurant_offer ";
    }

    @Override
    public String familiarColumns() {
        return " restaurant_offer_name ";
    }

    @Override
    public PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, restaurant_menu_name);

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
        return " ? ";
    }

    @Override
    public PreparedStatement returnDeletePreparedStatement(PreparedStatement preparedStatement) {
        return null;
    }
}
