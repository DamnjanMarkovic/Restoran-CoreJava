package com.comtrade.systemOperation.ingredients;

import com.code.domain.Ingredient;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetIngredientsMissingService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        List<Ingredient>listIngredientsInRestaurant = (List<Ingredient>) transferClass.getRequest();

        int idRestaurant = Integer.parseInt(transferClass.getMessage());
        Broker broker = new Broker();
        List<Ingredient>fullList = broker.getFullIngredientsList();


//        List<Ingredient>fullList = broker.getAllIngredients();
        transferClass.setResponse(returnRequesterdList(fullList, idRestaurant, listIngredientsInRestaurant));
    }

    private List<Ingredient> returnRequesterdList(List<Ingredient> fullList, int idRestaurant, List<Ingredient> listIngredientsInRestaurant) {
        List<String>finalListNames = new ArrayList<>();
        List<Ingredient>finalReturningList = new ArrayList<>();
        List<String> namesAvailable = new ArrayList<>();
        List<String>finalListNamesMissingIngr = new ArrayList<>();

        for (Ingredient ingAll: fullList){
            finalListNames.add(ingAll.getIngredient_name());
        }

        for (Ingredient ingAvailable: listIngredientsInRestaurant){
            if (ingAvailable.getQuantity()>0) {
                namesAvailable.add(ingAvailable.getIngredient_name());
            }
        }

        for (String namesAll: finalListNames) {
            if (namesAvailable.contains(namesAll)){

            } else {
                finalListNamesMissingIngr.add(namesAll);
            }
        }

        for (Ingredient ing: fullList             ) {
            for (String name: finalListNamesMissingIngr                 ) {
                if (ing.getIngredient_name().equalsIgnoreCase(name)){
                    finalReturningList.add(ing);
                }
            }
        }


        return finalReturningList;
    }


//    private List<Ingredient> returnRequesterdList(List<Ingredient> fullList, int idRestaurant, List<Ingredient> listIngredientsInRestaurant) {
//        List<Ingredient>finalList = new ArrayList<>();
//        List<Ingredient>list121 = new ArrayList<>();
//        for (Ingredient ingr:fullList         ) {
//                if (ingr.getIdRestaurant()!=idRestaurant){
//                    list121.add(ingr);
//                    finalList.add(ingr);
//                }
//        }
//
//        for (Ingredient ingrid:list121             ) {
//            for (Ingredient inf:listIngredientsInRestaurant                 ) {
//                if (ingrid.getIngredient_name().equalsIgnoreCase(inf.getIngredient_name())){
//                finalList.remove(ingrid);
//                }
//            }
//        }
//        return finalList;
//    }
}
