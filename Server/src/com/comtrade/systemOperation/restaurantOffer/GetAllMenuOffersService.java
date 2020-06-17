package com.comtrade.systemOperation.restaurantOffer;

import com.code.domain.Ingredient;
import com.code.domain.Offer;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllMenuOffersService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException {
        Broker broker = new Broker();
        List<Offer> listFullNotGood = broker.getAllMenuOffers();
        transferClass.setResponse(correctReceivedList(listFullNotGood));
    }

    private List<Offer> correctReceivedList(List<Offer> listFullNotGood) {
        List<Offer> finalList = new ArrayList<>();
        Ingredient ingredient = new Ingredient();
        List<Ingredient>listIngredients = new ArrayList<>();
        List<Ingredient>listIngredients1 = new ArrayList<>();
        Map<Offer, List<Ingredient>>map = new HashMap<>();
        Offer rstm = new Offer();


        for (Offer rsd: listFullNotGood             ) {
            rstm = new Offer(rsd.getId_restaurant_menu(), rsd.getRestaurant_menu_name(), rsd.getRestaurant_menu_price(), rsd.getRestaurant_menu_type());
            listIngredients = new ArrayList<>();
            listIngredients.add(rsd.getIngredient());
            if (!map.containsKey(rstm)) {
                map.put(rstm, new ArrayList<Ingredient>());
            }map.get(rstm).add(rsd.getIngredient());
       }

        for (Map.Entry<Offer, List<Ingredient>> map12: map.entrySet()){
            finalList.add(new Offer(map12.getKey().getId_restaurant_menu(),
                    map12.getKey().getRestaurant_menu_name(),
                    map12.getKey().getRestaurant_menu_price(),
                    map12.getKey().getRestaurant_menu_type(),
                    new ArrayList<Ingredient>(map12.getValue())));
        }
        return  finalList;
    }
}
