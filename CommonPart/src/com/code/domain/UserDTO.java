package com.code.domain;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private int idUser;
    private String userName;
    private String userLoginName;
    private String userPassword;
    private String photoLocation;
    private int idRoleUser;
    private String nameRole;
    private int idRestaurantUser;
    private String nameRestaurant;

    public UserDTO(String userName, String userLoginName, String userPassword, String photoLocation, int idRestaurantUser, int idRoleUser) {
        this.userName = userName;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
        this.photoLocation = photoLocation;
        this.idRestaurantUser = idRestaurantUser;
        this.idRoleUser = idRoleUser;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserDTO(String userName, String photoLocation, int idRoleUser, String nameRole, int idRestaurantUser, String nameRestaurant) {
        this.userName = userName;
        this.photoLocation = photoLocation;
        this.idRoleUser = idRoleUser;
        this.nameRole = nameRole;
        this.idRestaurantUser = idRestaurantUser;
        this.nameRestaurant = nameRestaurant;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public int getIdRoleUser() {
        return idRoleUser;
    }

    public void setIdRoleUser(int idRoleUser) {
        this.idRoleUser = idRoleUser;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public int getIdRestaurantUser() {
        return idRestaurantUser;
    }

    public void setIdRestaurantUser(int idRestaurantUser) {
        this.idRestaurantUser = idRestaurantUser;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }
}
