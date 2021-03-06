package com.code.constants;

public enum ConstantsBLC {

    POST,                   //upisi u bazu
    GET,
    PUT,
    DELETE,
    GET_LOGIN,            //vrati sve role korisnika koji se loguje

    //Artikli
    GET_INGREDIENTS_BASED_ON_USER, POST_INGREDIENTS, PUT_INGREDIENTS, GET_INGREDIENTS_ON_RESTAURANT, PUT_EXISTING_INGREDIENTS,


    GET_RESTAURANT_MENU, GET_ALL_MENU_OFFERS,POST_NEW_MENU_OFFERS, GET_AVAILABLE_MENU_OFFERS,

    CONFIRM_USERNAME,

    GET_RESTAURANT,GET_RESTAURANTS,RETURN_MENU_BASED_ON_BILL_ID,

    GET_ORDERS_BASED_ON_TABLE,OWNER_SENDING_MESSAGE,


    POST_ORDER_AND_RETURN_IDS,REMOVE_ORDERS,


    POST_MESSAGE, CHAT_RETURN_RESTAURANT_STAFF,DELETE_ORDERS,RETURN_DELETED_ORDERS,GET_INGREDIENTS_MISSING,SET_PHOTO_LOCATION,

    RETURN_DINNING_TABLE,
    RETURN_TAKEN_ORDERS,RETURN_ORDERS_FOR_DELETION,

    POST_BILL,RETURN_BILLS,RETURN_AVAILABLE_TABLES, RETURN_USERS,RETURN_ROLES,

    RETURN_IMAGES, INSERT_ORDERS_FOR_DELETION, EMPTY_TEST,
    CHAT_LEAVING_MESSAGE, CHAT_TESTING, GET_LOGGED_USERS,REMOVE_WRONG_ORDER,GET_WAITER_NAME,
    LOGGING_OFF,GET_LOGIN_INFO,UPDATE_LOGGED_USERS_NAMES, TRANSFER_ORDERS,




}
