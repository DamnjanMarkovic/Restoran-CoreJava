package com.code.constants;

import java.util.Arrays;
import java.util.List;

public enum ConstantsImages {

    LOGIN, LOGIN_MANAGER, LOGIN_WAITER, SERVER, OWNER, WRONG_INPUT, IMAGE_FOLDER_LOCATION;

    public String serverLogin(){
        return "Server/src/com/comtrade/images/serverImage.jpeg";
    }
    public String imageLoginKey(){
        return "Server/src/com/comtrade/images/LoginKey.png";
    }
    public String imageLoginUser(){
        return "Server/src/com/comtrade/images/LoginUses.png";
    }


    public String imageManagerLoginbtnAvailableOffers(){
        return "Server/src/com/comtrade/images/offers.png";
    }
    public String imageManagerLoginbtnTables(){
        return "Server/src/com/comtrade/images/restaurant_1.png";
    }
    public String imageManagerLoginbtnSpecialOffers(){
        return "Server/src/com/comtrade/images/special_offers.png";
    }
    public String imageManagerLoginbtnIngredients(){
        return "Server/src/com/comtrade/images/ingredients_3.png";
    }
    public String imageManagerLoginbtnCancel(){
        return "Server/src/com/comtrade/images/Cancelled.png";
    }
    public String imageManagerLoginTransfer(){
        return "Server/src/com/comtrade/images/TransferLogo.png";
    }
    public String imageManagerIngredient(){
        return "Server/src/com/comtrade/images/ingredients_3.png";
    }


    public String imageWaiterTableAvailable(){
        return "Server/src/com/comtrade/images/table_green.jpg";
    }
    public String imageWaiterTableOccupied(){
        return "Server/src/com/comtrade/images/table_buttons_1.png";
    }


    public String ownerLoginAllRestaurants(){
        return "Server/src/com/comtrade/images/single-multiple-restaurants-listings-restaurants-restaurant-website-food-websites.png";
    }
    public String ownerRestaurants(){
        return "Server/src/com/comtrade/images/single-multiple-restaurants-listings-restaurants-restaurant-website-food-websites.png";
    }
    public String ownerUsers(){
        return "Server/src/com/comtrade/images/simpsonsAll1.jpg";
    }


    public List<String> infoWrongInput() {
        return Arrays.asList(
                "Funny!", "Try again!", "Good effort", "That's pathetic", "Why would you wanna do that?", "Please enter valid input", "That's just not cool",
                "Good effort!", "Again? Seriously?", "Please please please...stop", "And again Charlie Brown, and again, and again.",
                "Expecting the world to treat you fairly because you are a good person is a little like expecting the bull not to attack you because you are a vegetarian.",
                "If you think you are too small to make a difference, try sleeping with a mosquito.",
                "The greatest glory in living lies not in never falling, but in rising every time we fall.",
                "The way to get started is to quit talking and begin doing.",
                "Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking."
        );
    }
    public String locationImageFolder(){
        return "Server/src/com/comtrade/images/NewImages";
    }


}
