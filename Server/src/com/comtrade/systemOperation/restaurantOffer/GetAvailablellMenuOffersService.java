package com.comtrade.systemOperation.restaurantOffer;

import com.code.domain.Ingredient;
import com.code.domain.Offer;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetAvailablellMenuOffersService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        List<Offer>listAllOffers = (List<Offer>) transferClass.getRequest();
        int idRestaurant = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        List<Ingredient> listAvailableIngredients = broker.getIngredientsOnRestaurant(idRestaurant);
        List<Offer>listAvailableMenues = returnListOfferAvailable(listAvailableIngredients, listAllOffers);
        transferClass.setResponse(listAvailableMenues);

    }


    private List<Offer> returnListOfferAvailable(List<Ingredient> listAvailableIngredient, List<Offer> listRestaurantMenues) {
        List<Offer> listOffersAvailable = new ArrayList<>();
        for (Offer rstm: listRestaurantMenues             ) {
            if (checkIfExist(rstm.getMenuIngredientList(), listAvailableIngredient)) listOffersAvailable.add(rstm);
        }return listOffersAvailable;
    }

    private boolean checkIfExist(List<Ingredient> listMenuIngredient, List<Ingredient> listAvailableIngredient) {
        boolean[] bool = new boolean[listMenuIngredient.size()];
        for (int i = 0; i < listMenuIngredient.size(); i++) {
            for (Ingredient ingredient : listAvailableIngredient) {
                if (ingredient.getIngredient_name().equals(listMenuIngredient.get(i).getIngredient_name())) {
                    if (ingredient.getQuantity() >= listMenuIngredient.get(i).getQuantity()) {
                        bool[i] = true;
                        break;
                    }
                }
            }
        }
        boolean value1 = checkBooleanValues(bool);
        return value1;
    }

    private boolean checkBooleanValues(boolean[] bool) {
        boolean value1 = true;
        for (int i = 0; i < bool.length; i++) {
            if (bool[i] == false) {
                value1 = false;
                break;
            }
        }
        return value1;
    }


}
