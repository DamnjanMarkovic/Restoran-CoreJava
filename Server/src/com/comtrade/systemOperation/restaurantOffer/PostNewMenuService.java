package com.comtrade.systemOperation.restaurantOffer;

import com.code.domain.Ingredient;
import com.code.domain.Offer;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostNewMenuService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        List<Ingredient> listIngredient = new ArrayList<>();
        Offer offer = (Offer) transferClass.getRequest();
        Broker broker = new Broker();
        int idRestaurantOffer = broker.saveAndReturnID(offer);
        for (Ingredient ingr : offer.getMenuIngredientList() ) {
                    listIngredient.add(ingr);
        }
        broker.saveOfferIngredients(idRestaurantOffer, listIngredient);

    }
}
