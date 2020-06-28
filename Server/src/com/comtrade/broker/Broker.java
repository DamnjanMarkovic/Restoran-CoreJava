package com.comtrade.broker;


import com.code.constants.Constants_PaymentType;
import com.code.domain.*;
import com.comtrade.sqlConnection.SQLConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Broker {
    public void save(CommonDomain commonDomain) throws SQLException {

        String sql = "insert into " + commonDomain.returnTableName()
                + commonDomain.saveReturnColums() + " values " + commonDomain.saveReturnQuestionMarks();
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement = commonDomain.setInsertValues(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved, table: " + commonDomain.returnTableName() + " " + e.getMessage());
        }
    }

    public User returnRoleUser(User user) {
        String sql = "SELECT u.id_user, u.username, u.password, u.userFirstName as firstName, u.imageLink, r.role_label AS label FROM user AS u " +
                "INNER JOIN user_roles AS ur ON ur.id_user = u.id_user " +
                "INNER JOIN role AS r ON r.id_role = ur.id_role WHERE u.username = ? AND u.password = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<UserRole> role = new HashSet<>();
            while (resultSet.next()) {
                UserRole userRole = new UserRole();
                userRole.setLabel(resultSet.getString("label"));
                role.add(userRole);

            }
            if (resultSet.first()) {
                int id_user = resultSet.getInt("id_user");
                String userFirstName = resultSet.getString("firstName");
                String imageLocation = resultSet.getString("imageLink");
                user.setid_user(id_user);
                user.setuserFirstName(userFirstName);
                user.setImageLocation(imageLocation);
                user.setSetUserRole(role);
                return user;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public List<Ingredient> getIngredients(int idUser) {
        List<Ingredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        String sql = "SELECT ing.id_ingredient as id, ing.ingredient_name as name, ing.purchase_price as price, " +
                "ing.quantity_measure as measure,sum(aving.quantity) as quantity FROM ingredients AS ing INNER JOIN available_ingredients " +
                "AS aving ON aving.id_ingredients = ing.id_ingredient INNER JOIN restaurants as rest on " +
                "rest.id_restaurant = aving.id_restaurant INNER JOIN user_restaurant as ur on ur.id_restaurant = rest.id_restaurant " +
                "INNER JOIN user as us on us.id_user = ur.id_user where us.id_user in ( ? ) group by ing.id_ingredient";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredient = new Ingredient(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getDouble("price"), resultSet.getString("measure"), resultSet.getDouble("quantity"));
                listIngredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listIngredients;
    }


    public List<CommonDomain> get(CommonDomain commonDomain) {

        List<CommonDomain> listCommonDomain = new ArrayList<>();
        String sql = "select * from " + commonDomain.returnTableName();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                commonDomain = (CommonDomain) commonDomain.readObjects(resultSet);
                listCommonDomain.add(commonDomain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCommonDomain;


    }

    public void updateIngredients(Ingredient ingredient, int id_restaurant) {


        String sql = "INSERT INTO available_ingredients(id_restaurant, id_ingredients, quantity) VALUES (?,?,?) ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id_restaurant);
            preparedStatement.setInt(2, ingredient.getId_ingredient());
            preparedStatement.setDouble(3, ingredient.getQuantity());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    public Restaurant getRestaurant(User user) {
        Restaurant restaurant = new Restaurant();
        String sql = "select * from restaurants INNER JOIN user_restaurant on " +
                "restaurants.id_restaurant = user_restaurant.id_restaurant WHERE user_restaurant.id_user = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, user.getid_user());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                int id_restaurant = resultSet.getInt("id_restaurant");
                String name_restaurant = resultSet.getString("name_restaurant");
                String street = resultSet.getString("street");
                int number = resultSet.getInt("number");
                String city = resultSet.getString("city");
                restaurant = new Restaurant(id_restaurant, name_restaurant, street, number, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }*/

    public int returnID(CommonDomain commonDomain) {
        int id_Ingredient = 0;
        String sql = "select " + commonDomain.idColumnName() + " from" + commonDomain.returnTableName() +
                " where " + commonDomain.familiarColumns() + " = " + commonDomain.returnIDQuestionMarks();
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement = commonDomain.returnIDPreparedStatement(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first())
                id_Ingredient = resultSet.getInt(commonDomain.idColumnName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_Ingredient;

    }

    public void saveIngredients(Ingredient ingredient, int id_restaurant) throws SQLException {

        String sql = "insert into available_ingredients (id_restaurant, id_ingredients, quantity) values (?,?,?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id_restaurant);
            preparedStatement.setInt(2, ingredient.getId_ingredient());
            preparedStatement.setDouble(3, ingredient.getQuantity());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());

        }


    }

    public List<Restaurant> getRestaurants(User user) {
        Restaurant restaurant = new Restaurant();
        List<Restaurant> listRestaurants = new ArrayList<>();
        String sql = "select * from restaurants INNER JOIN user_restaurant on " +
                "restaurants.id_restaurant = user_restaurant.id_restaurant WHERE user_restaurant.id_user = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, user.getid_user());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_restaurant = resultSet.getInt("id_restaurant");
                String name_restaurant = resultSet.getString("name_restaurant");
                String street = resultSet.getString("street");
                int number = resultSet.getInt("number");
                String city = resultSet.getString("city");
                String imageLocation = resultSet.getString("imageRestaurant");
                restaurant = new Restaurant(id_restaurant, name_restaurant, street, number, city, imageLocation);
                listRestaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRestaurants;
    }

    public List<Ingredient> getIngredientsOnRestaurant(int idRestaurant) {
        List<Ingredient> listIngredients = new ArrayList<>();
        Ingredient ingredient = null;
        String sql = "SELECT ing.id_ingredient as id, ing.ingredient_name as name, ing.purchase_price as price, ing.quantity_measure as measure, sum(aving.quantity) as quantity FROM ingredients AS ing INNER JOIN " +
                "available_ingredients AS aving ON aving.id_ingredients = ing.id_ingredient INNER JOIN restaurants as rest " +
                "on rest.id_restaurant = aving.id_restaurant where rest.id_restaurant = ? group by ing.id_ingredient";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idRestaurant);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredient = new Ingredient(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getDouble("price"), resultSet.getString("measure"), resultSet.getDouble("quantity"));
                listIngredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listIngredients;
    }

    public void delete(CommonDomain commonDomain) {

        String sql = "delete from " + commonDomain.returnTableName() + " where " + commonDomain.returnColumnforDelete() +
                " = " + commonDomain.return1QuestionMark();
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement = commonDomain.returnDeletePreparedStatement(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "You are not authorized to perform this operation.");
        }
    }



    public List<Offer> getAllMenuOffers() {
        List<Offer> listOffers = new ArrayList<>();
        Offer offer = new Offer();
        Ingredient ingredient = new Ingredient();
        //List<Ingredient>ingredientList = new ArrayList<>();

        String sql = "select restaurant_offer.id_restaurant_offer as offerID, restaurant_offer.restaurant_offer_name as offer_name,restaurant_offer.restaurant_offer_price " +
                "as price, restaurant_offer.offer_type as type, ingredients.id_ingredient as id_ingredient, ingredients.ingredient_name as ingredient_name, " +
                "ingredients.purchase_price as ingredientPrice," +
                "ingredients.quantity_measure as measure, " +
                "restaurant_offer_ingredients.quantity as quantity from restaurant_offer_ingredients " +
                "INNER JOIN restaurant_offer ON restaurant_offer.id_restaurant_offer = " +
                "restaurant_offer_ingredients.id_restaurant_offer INNER JOIN ingredients on ingredients.id_ingredient = " +
                "restaurant_offer_ingredients.id_ingredient ORDER by restaurant_offer.restaurant_offer_name ";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int offerID = resultSet.getInt("offerID");
                String offerName = resultSet.getString("offer_name");
                double price = resultSet.getDouble("price");
                String offertype = resultSet.getString("type");
                int idIngredient = resultSet.getInt("id_ingredient");
                String ingredientName = resultSet.getString("ingredient_name");
                double ingredientPrice = resultSet.getDouble("ingredientPrice");
                String measure = resultSet.getString("measure");
                double quantity = resultSet.getDouble("quantity");

                ingredient = new Ingredient(idIngredient, ingredientName, ingredientPrice, measure, quantity);
                offer = new Offer(offerID, offerName, price, offertype, ingredient);
                listOffers.add(offer);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOffers;

    }

    public void saveOfferIngredients(int idRestaurantOffer, List<Ingredient> listIngredient) throws SQLException {

        for (int i = 0; i < listIngredient.size(); i++) {
            String sql = "INSERT INTO restaurant_offer_ingredients (id_restaurant_offer, id_ingredient, quantity) " +
                    "VALUES (?,?,?)";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, idRestaurantOffer);
                preparedStatement.setInt(2, listIngredient.get(i).getId_ingredient());
                preparedStatement.setDouble(3, listIngredient.get(i).getQuantity());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }
    }

    public int saveAndReturnID(Offer offer) throws SQLException {
        int idOffer = 0;


        String sql = "INSERT INTO restaurant_offer (restaurant_offer_name, restaurant_offer_price, offer_type) VALUES (?,?,?);";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, offer.getRestaurant_menu_name());
            preparedStatement.setDouble(2, offer.getRestaurant_menu_price());
            preparedStatement.setString(3, offer.getRestaurant_menu_type());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql1 = "select id_restaurant_offer from restaurant_offer where restaurant_offer_name = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql1);
            preparedStatement.setString(1, offer.getRestaurant_menu_name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first())
                idOffer = resultSet.getInt("id_restaurant_offer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql2 = "INSERT INTO images(id_restaurant_offer) VALUES (?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql2);
            preparedStatement.setInt(1, idOffer);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idOffer;
    }


    public List<Integer> postOffersAndReturnIDs(List<Order> listOrders) throws SQLException {
        List<Integer> listOfferIDs = new ArrayList<>();
        int offerID = 0;
        for (int i = 0; i < listOrders.size(); i++) {
            String sql = "INSERT INTO order_offers (id_offer, quantity, id_table) VALUES (?,?,?)";
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement =
                        SQLConnection.getInstance().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, listOrders.get(i).getOffer().getId_restaurant_menu());
                preparedStatement.setInt(2, listOrders.get(i).getQuantity());
                preparedStatement.setInt(3, listOrders.get(i).getDinningTable().getId_dinningTable());
                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected != 0) {
                    resultSet = preparedStatement.getGeneratedKeys();
                    while (resultSet.next()) {
                        offerID = resultSet.getInt(1);
                        listOfferIDs.add(offerID);
                    }
                }
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }
        return listOfferIDs;
    }

    public List<User> getRestaurantStaff(int userID) {
        List<User> listRestaurantStaff = new ArrayList<>();

        String sql = "SELECT us.userFirstName AS NAME, us.id_user AS finalID FROM user AS us WHERE us.id_user IN" +
                "( select ur1.id_user AS userID FROM user_restaurant AS ur1 WHERE ur1.id_restaurant IN" +
                "( SELECT id_restaurant AS idRest FROM user_restaurant AS ur WHERE ur.id_user = ? ) ) ";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("NAME");
                int userIDAsking = resultSet.getInt("finalID");
                User user = new User(userIDAsking, userName);
                listRestaurantStaff.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRestaurantStaff;
    }

    public DinningTable returnTable(DinningTable dinningTable) {
        DinningTable dinningTable1 = null;
        String sql = "SELECT * from dinning_table WHERE table_number=? and id_restaurant=?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, dinningTable.getTable_number());
            preparedStatement.setInt(2, dinningTable.getId_restaurant());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                dinningTable1 = new DinningTable(resultSet.getInt("id_table"), resultSet.getInt("table_number"),
                        resultSet.getInt("id_restaurant"), resultSet.getInt("capacity"),
                        resultSet.getInt("table_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dinningTable1;
    }

    public List<OrderDTO> getFinalOrderList(List<Integer> listIDOrders) {
        List<OrderDTO> listOrderDTO = new ArrayList<>();
        OrderDTO orderDTO;
        for (int i = 0; i < listIDOrders.size(); i++) {
            String sql = "select id_order, id_offer, quantity from order_offers where id_order = ?";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, listIDOrders.get(i));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    orderDTO = new OrderDTO(resultSet.getInt("id_order"),
                            resultSet.getInt("id_offer"), resultSet.getInt("quantity"));
                    listOrderDTO.add(orderDTO);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOrderDTO;
    }

    public int postBillAndReturnID(Bill bill) throws SQLException {

        int billID = 0;

        String sql = "INSERT INTO bill (id_dinning_table, username, payment_type, reduction, total_amount, id_restaurant) VALUES (?,?,?,?,?,?)";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement =
                    SQLConnection.getInstance().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bill.getId_dinningTable());
            preparedStatement.setString(2, bill.getUsername());
            preparedStatement.setString(3, bill.getPaymentType());
            preparedStatement.setDouble(4, bill.getReduction());
            preparedStatement.setDouble(5, bill.getTotalPrice());
            preparedStatement.setInt(6, bill.getIdRestaurant());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected != 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    billID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
        }

        return billID;
    }

    public void insertDataIntoBillOrders(int idBill, List<Integer> prepareLlistIDOrders) throws SQLException {
        for (int i = 0; i < prepareLlistIDOrders.size(); i++) {

            String sql = "INSERT INTO bill_orders (id_bill, id_order) VALUES (?,?)";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, idBill);
                preparedStatement.setInt(2, prepareLlistIDOrders.get(i));
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }
    }

    public void reduceNumberOfUsedIngredients(List<Order> listOrders, int idRestaurant) throws SQLException {
        for (int i = 0; i < listOrders.size(); i++) {

            String sql = "UPDATE available_ingredients INNER JOIN ingredients on available_ingredients.id_ingredients = " +
                    "ingredients.id_ingredient INNER JOIN restaurant_offer_ingredients ON " +
                    "restaurant_offer_ingredients.id_ingredient = ingredients.id_ingredient \n" +
                    "SET available_ingredients.quantity = available_ingredients.quantity - ? * " +
                    "restaurant_offer_ingredients.quantity \n" +
                    "WHERE restaurant_offer_ingredients.id_restaurant_offer= ? AND id_restaurant = ?";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setDouble(1, listOrders.get(i).getQuantity());
                preparedStatement.setInt(2, listOrders.get(i).getOffer().getId_restaurant_menu());
                preparedStatement.setInt(3, idRestaurant);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }

    }

    public List<Bill> getBills(BillDTO billDTO) {
        Bill bill;
        List<Bill> listBIll = new ArrayList<>();
        String sql = null;

        if (billDTO.getPaymentType().equalsIgnoreCase(Constants_PaymentType.ALL_PAYMENT_TYPE.returnAllPaymentType())) {
            if (billDTO.getId_dinningTable() == -1) {
                sql = "SELECT * FROM bill where id_restaurant = ? and (bill_time BETWEEN ? AND ?)";
                try {
                    PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                    preparedStatement.setInt(1, billDTO.getIdRestaurant());
                    preparedStatement.setString(2, billDTO.getDateStart());
                    preparedStatement.setString(3, billDTO.getDateEnd());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        bill = new Bill(resultSet.getInt("id_bill"), resultSet.getInt("id_dinning_table"),
                                resultSet.getTimestamp("bill_time"), resultSet.getString("username"),
                                resultSet.getString("payment_type"), resultSet.getDouble("reduction"), resultSet.getDouble("total_amount"),
                                resultSet.getInt("id_restaurant"));
                        listBIll.add(bill);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sql = "SELECT * FROM bill where id_dinning_table = ? and id_restaurant = ? and (bill_time BETWEEN ? AND ?)";
                try {
                    PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                    preparedStatement.setInt(1, billDTO.getId_dinningTable());
                    preparedStatement.setInt(2, billDTO.getIdRestaurant());
                    preparedStatement.setString(3, billDTO.getDateStart());
                    preparedStatement.setString(4, billDTO.getDateEnd());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        bill = new Bill(resultSet.getInt("id_bill"), resultSet.getInt("id_dinning_table"),
                                resultSet.getTimestamp("bill_time"), resultSet.getString("username"),
                                resultSet.getString("payment_type"), resultSet.getDouble("reduction"), resultSet.getDouble("total_amount"),
                                resultSet.getInt("id_restaurant"));
                        listBIll.add(bill);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (billDTO.getId_dinningTable() == -1) {
                sql = "SELECT * FROM bill where id_restaurant = ? and payment_type = ? and (bill_time BETWEEN ? AND ?)";
                try {
                    PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                    preparedStatement.setInt(1, billDTO.getIdRestaurant());
                    preparedStatement.setString(2, billDTO.getPaymentType());
                    preparedStatement.setString(3, billDTO.getDateStart());
                    preparedStatement.setString(4, billDTO.getDateEnd());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        bill = new Bill(resultSet.getInt("id_bill"), resultSet.getInt("id_dinning_table"),
                                resultSet.getTimestamp("bill_time"), resultSet.getString("username"),
                                resultSet.getString("payment_type"), resultSet.getDouble("reduction"), resultSet.getDouble("total_amount"),
                                resultSet.getInt("id_restaurant"));

                        listBIll.add(bill);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                sql = "SELECT * FROM bill where id_dinning_table = ? and payment_type = ? and id_restaurant = ? and (bill_time BETWEEN ? AND ?)";
                try {
                    PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                    preparedStatement.setInt(1, billDTO.getId_dinningTable());
                    preparedStatement.setString(2, billDTO.getPaymentType());
                    preparedStatement.setInt(3, billDTO.getIdRestaurant());
                    preparedStatement.setString(4, billDTO.getDateStart());
                    preparedStatement.setString(5, billDTO.getDateEnd());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        bill = new Bill(resultSet.getInt("id_bill"), resultSet.getInt("id_dinning_table"),
                                resultSet.getTimestamp("bill_time"), resultSet.getString("username"),
                                resultSet.getString("payment_type"), resultSet.getDouble("reduction"), resultSet.getDouble("total_amount"),
                                resultSet.getInt("id_restaurant"));
                        listBIll.add(bill);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return listBIll;
    }

    public List<Offer> getOffersBasedOnBillId(int idBill) {
        List<Offer>listOffers = new ArrayList<>();
        Offer offer;
        String sql = "SELECT id_offer from order_offers INNER JOIN bill_orders on order_offers.id_order = bill_orders.id_order WHERE bill_orders.id_bill = ?";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idBill);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                offer = new Offer(resultSet.getInt("id_offer"));
                listOffers.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOffers;
    }

    public List<Order> getOrdersBasedOnTable(int idTable) {

        List<Order>listOrders = new ArrayList<>();
        Offer offer;
        Order order;
        String sql = "SELECT id_order, id_offer, quantity, table_number, restaurant_offer_name FROM order_offers INNER JOIN " +
                "dinning_table ON order_offers.id_table = dinning_table.id_table INNER JOIN restaurant_offer on order_offers.id_offer = " +
                "restaurant_offer.id_restaurant_offer WHERE order_offers.id_table = ? AND id_bill IS NULL";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idTable);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                offer = new Offer(resultSet.getInt("id_offer"), resultSet.getString("restaurant_offer_name"));
                DinningTable dinningTable = new DinningTable(resultSet.getInt("table_number"));
                order = new Order(resultSet.getInt("id_order"), offer, resultSet.getInt("quantity"), dinningTable);
                listOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOrders;
    }

    public void insertIdBillIntoOrderOffer(int idBill, List<Integer> prepareLlistIDOrders) throws SQLException {
        for (int i = 0; i < prepareLlistIDOrders.size(); i++) {

        String sql = "UPDATE order_offers SET id_bill=? WHERE id_order = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idBill);
            preparedStatement.setInt(2, prepareLlistIDOrders.get(i));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }

    }

    public void removeOrders(List<Order> listOrders) throws SQLException {
        for (int i = 0; i < listOrders.size(); i++) {

            String sql = "DELETE FROM order_offers WHERE order_offers.id_order = ? ";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, listOrders.get(i).getId_Order());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }
    }

    public List<DinningTable> returnAvailableTables() {
        List<DinningTable>listAvailableTables = new ArrayList<>();
        List<Integer>listDinningTablesID = new ArrayList<>();
        DinningTable dinningTable;
        String sql = "SELECT DISTINCT id_table from order_offers where id_bill is null";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idRequested = resultSet.getInt("id_table");
                listDinningTablesID.add(idRequested);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listDinningTablesID.size(); i++) {
         sql = "SELECT id_table,table_number, id_restaurant FROM dinning_table WHere id_table = ?";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, listDinningTablesID.get(i));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {


                dinningTable = new DinningTable(resultSet.getInt("id_table"), resultSet.getInt("table_number"), resultSet.getInt("id_restaurant"));
                listAvailableTables.add(dinningTable);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        return listAvailableTables;
    }

    public void deleteMenu(int idMenuOffer) {

            String sql = "DELETE * FROM restaurant_offer_ingredients WHERE id_restaurant_offer = ? ";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, idMenuOffer);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        sql = "DELETE * FROM restaurant_offer WHERE id_restaurant_offer = ? ";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idMenuOffer);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertOrdersForDeletion(OrderDeletionDTO orderDeletionDTO) throws SQLException {


            String sql = "INSERT INTO deleted_orders (id_order, id_restaurant, id_user_waiter) VALUES (?,?,?)";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, orderDeletionDTO.getId_order());
                preparedStatement.setInt(2, orderDeletionDTO.getId_restaurant());
                preparedStatement.setInt(3, orderDeletionDTO.getId_waiter());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }

    }

    public List<OrderDeletionDTO> returnOrdersForDeletion(int restaurantID) {

        List<OrderDeletionDTO>listPlus = new ArrayList<>();
        OrderDeletionDTO orderDeletionDTO = null;
        String sql = "SELECT deleted_orders.id_order as id_order, restaurant_offer.restaurant_offer_name as offer_name, order_offers.quantity as quantity, " +
                "user.userFirstName as waiterName, deleted_orders.time as time FROM deleted_orders INNER JOIN order_offers on deleted_orders.id_order = order_offers.id_order " +
                "INNER JOIN restaurant_offer on order_offers.id_offer = restaurant_offer.id_restaurant_offer " +
                "INNER JOIN user on deleted_orders.id_user_waiter = user.id_user WHERE deleted_orders.id_restaurant=? and deleted_orders.id_user_manager is null" ;
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, restaurantID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                orderDeletionDTO = new OrderDeletionDTO (resultSet.getInt("id_order"), resultSet.getString("offer_name"),
                resultSet.getInt("quantity"), resultSet.getString("waiterName"), resultSet.getTimestamp("time"));

                assert listPlus != null;
                if(!listPlus.contains(orderDeletionDTO)) {
                    listPlus.add(orderDeletionDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listPlus;

    }

    public void deleteOrders(List<OrderDeletionDTO> orderDeletionDTOList, int idManager) {

        for (int i = 0; i <orderDeletionDTOList.size() ; i++) {
            String sql = "UPDATE deleted_orders set id_user_manager = ? WHERE id_order = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);

                preparedStatement.setInt(1, idManager);
                preparedStatement.setInt(2, orderDeletionDTOList.get(i).getId_order());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i <orderDeletionDTOList.size() ; i++) {
            String sql = "UPDATE order_offers set id_bill = -1, id_user_deleted = ? WHERE id_order = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);

                preparedStatement.setInt(1, idManager);
                preparedStatement.setInt(2, orderDeletionDTOList.get(i).getId_order());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





    }

    public Object returnDeletedOrders(int restaurantID) {
        List<OrderDeletionDTO>listPlus = new ArrayList<>();
        OrderDeletionDTO orderDeletionDTO = null;
        String sql = "SELECT deleted_orders.id_order as id_order, restaurant_offer.restaurant_offer_name as offer_name, order_offers.quantity as quantity, " +
                "u1.userFirstName as waiterName, deleted_orders.time as time, u2.userFirstName as managerName FROM deleted_orders INNER JOIN " +
                "order_offers on deleted_orders.id_order = order_offers.id_order INNER JOIN restaurant_offer on order_offers.id_offer = " +
                "restaurant_offer.id_restaurant_offer INNER JOIN user as u1 on deleted_orders.id_user_waiter = u1.id_user INNER JOIN user as u2 " +
                "on deleted_orders.id_user_manager = u2.id_user WHERE deleted_orders.id_restaurant=?" ;
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, restaurantID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                orderDeletionDTO = new OrderDeletionDTO (resultSet.getInt("id_order"), resultSet.getString("offer_name"),
                        resultSet.getInt("quantity"), resultSet.getString("waiterName"), resultSet.getTimestamp("time"), resultSet.getString("managerName"));

                assert listPlus != null;
                if (!listPlus.contains(orderDeletionDTO)) {
                    listPlus.add(orderDeletionDTO);
                }



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return listPlus;
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient>listRequested = new ArrayList<>();

        Ingredient ingredient = null;
        String sql = "SELECT ing.id_ingredient as id, ing.ingredient_name as name, ing.quantity_measure as measure, " +
                "aving.id_restaurant as idRestaurant FROM ingredients AS ing INNER JOIN available_ingredients AS " +
                "aving ON aving.id_ingredients = ing.id_ingredient INNER JOIN restaurants as rest on rest.id_restaurant = aving.id_restaurant";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredient = new Ingredient(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("measure"), resultSet.getInt("idRestaurant"));
                listRequested.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRequested;
    }

    public void setPhotoLocation(String photoLocation, int idOffer) throws SQLException {

        String sql = "UPDATE images SET imageLocation=? WHERE id_restaurant_offer = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, photoLocation);
            preparedStatement.setInt(2, idOffer);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
        }
    }

    public void connectRestaurantAndUser(int idRestaurant, int idUser) throws SQLException {

        String sql = "INSERT INTO user_restaurant(id_restaurant, id_user) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idRestaurant);
            preparedStatement.setInt(2, idUser);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
        }



    }
/*
    public void saveRestaurant(CommonDomain commonDomain, int idUser, Restaurant restaurant) throws SQLException {
        String sql = "insert into " + commonDomain.returnTableName()
                + commonDomain.saveReturnColums() + " values " + commonDomain.saveReturnQuestionMarks();
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement = commonDomain.setInsertValues(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved, table: " + commonDomain.returnTableName() + " " + e.getMessage());
        }


    }*/


    public List<UserDTO> returnUsers(int idOwner) {

        List<UserDTO>listRequested = new ArrayList<>();

        UserDTO userDTO = null;
        String sql = "SELECT userFirstName, imageLink, role.id_role, role.role_label, restaurants.id_restaurant, restaurants.name_restaurant  " +
                "FROM user INNER JOIN user_restaurant on user.id_user = user_restaurant.id_user INNER JOIN restaurants on restaurants.id_restaurant = " +
                "user_restaurant.id_restaurant INNER JOIN user_roles on user_roles.id_user = user.id_user INNER JOIN role on role.id_role = " +
                "user_roles.id_role where user.id_user not in (?)";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idOwner);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {



                userDTO = new UserDTO(resultSet.getString("userFirstName"), resultSet.getString("imageLink"),
                        resultSet.getInt("id_role"), resultSet.getString("role_label"), resultSet.getInt("id_restaurant"),
                        resultSet.getString("name_restaurant"));
                listRequested.add(userDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRequested;




    }

    public List<UserRole> getRolesBasedOnUser(int idOwner) {
        List<UserRole>listRequested = new ArrayList<>();
        UserRole userRole = null;
        String sql = "SELECT * FROM role WHERE role.id_role not in (?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idOwner);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                userRole = new UserRole(resultSet.getInt("id_role"), resultSet.getString("role_label"));
                listRequested.add(userRole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRequested;

    }

    public void saveUser(UserDTO userDTO) {
        int idUser = 0;
        ResultSet resultSet = null;
        String sql = "INSERT INTO user (username, password, userFirstName,imageLink) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userDTO.getUserLoginName());
            preparedStatement.setString(2, userDTO.getUserPassword());
            preparedStatement.setString(3, userDTO.getUserName());
            preparedStatement.setString(4, userDTO.getPhotoLocation());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected != 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.first()) {
                    idUser = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
        }

        String sql1 = "INSERT INTO user_roles(id_user, id_role) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql1);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, userDTO.getIdRoleUser());

            preparedStatement.execute();
        } catch (SQLException e) {
        }
        String sql2 = "INSERT INTO user_restaurant(id_restaurant, id_user) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql2);

            preparedStatement.setInt(1, userDTO.getIdRestaurantUser());
            preparedStatement.setInt(2, idUser);

            preparedStatement.execute();
        } catch (SQLException e) {
        }
    }

    public void createTables(int idRestaurant) throws SQLException {
        for (int i = 1; i < 10; i++) {

            String sql = "INSERT INTO dinning_table(table_number, id_restaurant) VALUES (?,?)";
            try {
                PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, i);
                preparedStatement.setInt(2, idRestaurant);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new SQLException("Data not saved in table " + e.getMessage());
            }
        }



    }

    public List<Ingredient> getFullIngredientsList() {

        List<Ingredient>listRequested = new ArrayList<>();

        Ingredient ingredient = null;
        String sql = "SELECT * FROM ingredients";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredient = new Ingredient (resultSet.getInt("id_ingredient"), resultSet.getString("ingredient_name"),
                        resultSet.getDouble("purchase_price"), resultSet.getString("quantity_measure"));
                listRequested.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRequested;


    }

    public boolean confirmUsername(String userName) {
        boolean exists = false;
        int numberOfUsernameRepetitions = 0;
        String sql = "SELECT count(username) as numberOfUsers FROM user where user.username = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                numberOfUsernameRepetitions = resultSet.getInt("numberOfUsers");
                if (numberOfUsernameRepetitions>0) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return  exists;
    }

    public String getManagerNameBasedOnWaiterID(int getid_user) {
        String managerName = null;
        int idRestaurant = 0;
        int idManager = 0;
        int numberOfUsernameRepetitions = 0;
        String sql = "SELECT id_restaurant FROM user_restaurant WHERE id_user = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, getid_user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                idRestaurant = resultSet.getInt("id_restaurant");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Integer>listUserID = new ArrayList<>();

        Integer idUser = 0;
//        sql = "SELECT id_user FROM user_restaurant WHERE id_restaurant = ?";
//
//        try {
//            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
//            preparedStatement.setInt(1, idRestaurant);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                idUser = resultSet.getInt("id_user");
//                listUserID.add(idUser);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        sql = "SELECT ur.id_user from user_roles as ur INNER JOIN user as uu on uu.id_user = ur.id_user INNER JOIN user_restaurant as ut on uu.id_user=ut.id_user INNER JOIN restaurants as re " +
                "on re.id_restaurant = ut.id_restaurant where ut.id_restaurant = ? and ur.id_role = 3";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idRestaurant);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                idManager = resultSet.getInt("id_user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "SELECT userFirstName FROM user WHERE user.id_user = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idManager);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                managerName = resultSet.getString("userFirstName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  managerName;


    }


    public Integer startLoggingUserReturnID(String getuserFirstName) throws SQLException {
        int id_usersLogging = 0;
        String sql = "INSERT INTO usersLogging (userFirstName) VALUES (?)";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement =
                    SQLConnection.getInstance().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, getuserFirstName);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected != 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.first()) {
                    id_usersLogging = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
        }
        return id_usersLogging;
    }



    public void logOffUserWithID(Timestamp timeLogoff, int idLoggedIn) throws SQLException {
        String sql = "UPDATE usersLogging SET userLoggedOff = ? WHERE id_usersLogging = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, timeLogoff);
            preparedStatement.setString(2, String.valueOf(idLoggedIn));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException("Data not saved in table " + e.getMessage());
        }
    }

    public Object getLoggingUserList(String userFirstName) {
        List<DTOUserLogging>listUserLogging = new ArrayList<>();
        DTOUserLogging dtoUserLogging = null;

        String sql = "SELECT * FROM usersLogging where userFirstName = ?";

        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, userFirstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dtoUserLogging = new DTOUserLogging (resultSet.getInt("id_usersLogging"), resultSet.getString("userFirstName"),
                        resultSet.getTimestamp("userLoggedIn"), resultSet.getTimestamp("userLoggedOff"));
                listUserLogging.add(dtoUserLogging);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return listUserLogging;
    }

    public void transferOrder(int idOrder, int idTableNew) throws SQLException {
        String sql = "UPDATE order_offers SET id_table=? WHERE id_order = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idTableNew);
            preparedStatement.setInt(2, idOrder);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
         sql = "DELETE from deleted_orders WHERE id_order = ?";
        try {
            PreparedStatement preparedStatement = SQLConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, idOrder);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }







    }

}
