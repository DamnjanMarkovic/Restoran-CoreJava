package com.comtrade.controlerBL;

import com.code.transferClass.TransferClass;
import com.comtrade.systemOperation.GenericSystemOperation;
import com.comtrade.systemOperation.restaurantOffer.*;


public class ControlerRestaurantMenu implements CommandBase {
    @Override
    public void execute(TransferClass transferClass) {
        GenericSystemOperation genericSystemOperation = null;
        switch (transferClass.getConstantsBLC()){
            case GET_RESTAURANT_MENU:
                genericSystemOperation = new GetRestaurantMenuService();
                break;
            case GET_ALL_MENU_OFFERS:
                genericSystemOperation = new GetAllMenuOffersService();
                break;
            case GET_AVAILABLE_MENU_OFFERS:
                genericSystemOperation = new GetAvailablellMenuOffersService();
                break;
            case POST_NEW_MENU_OFFERS:
                genericSystemOperation = new PostNewMenuService();
                break;
            case RETURN_MENU_BASED_ON_BILL_ID:
                genericSystemOperation = new ReturnMenuBasedOnBillIdService();
                break;
            case DELETE:
                genericSystemOperation = new DeleteRestaurantMenuService();
                break;
            case SET_PHOTO_LOCATION:
                genericSystemOperation = new SetPhotoOfferService();
                break;
            default:
                break;
        }
        genericSystemOperation.executeSystemOperation(transferClass);
    }
}
