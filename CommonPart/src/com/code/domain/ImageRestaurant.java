package com.code.domain;

import javax.swing.*;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRestaurant implements Serializable, CommonDomain {

    private int id_image;
    private int id_offer;
    private String imageLocation;
    private String imageName;
    private Icon img;
    private String name;

    public ImageRestaurant(Icon img, String name) {
        this.img = img;
        this.name = name;
    }

    public static ImageIcon getPhoto(String imageLocation) {
        return new ImageIcon(new ImageIcon((imageLocation)).getImage().
                getScaledInstance(100, 60, java.awt.Image.SCALE_SMOOTH));
    }
    public static ImageIcon getPhotoInTable(String imageLocation) {
        return new ImageIcon(new ImageIcon((imageLocation)).getImage().
                getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH));
    }


    @Override
    public String toString() {
        return name;
    }

    public Icon getImg() {
        return img;
    }

    public void setImg(Icon img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageRestaurant(String imageLocation, String imageName) {
        this.imageLocation = imageLocation;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public ImageRestaurant(int id_image, int id_offer, String imageLocation) {
        this.id_image = id_image;
        this.id_offer = id_offer;
        this.imageLocation = imageLocation;
    }


    public ImageRestaurant() {

    }


    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public int getId_offer() {
        return id_offer;
    }

    public void setId_offer(int id_offer) {
        this.id_offer = id_offer;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    @Override
    public String returnTableName() {
        return "images";
    }

    @Override
    public String saveReturnColums() {
        return null;
    }

    @Override
    public String saveReturnQuestionMarks() {
        return null;
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        return null;
    }

    @Override
    public Object readObjects(ResultSet resultSet) {
        ImageRestaurant imageRestaurant = null;
        try {
            imageRestaurant = new ImageRestaurant(resultSet.getInt("id_image"),
                    resultSet.getInt("id_restaurant_offer"),
                    resultSet.getString("imageLocation"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageRestaurant;
    }

    @Override
    public String returnIDQuestionMarks() {
        return null;
    }

    @Override
    public String idColumnName() {
        return null;
    }

    @Override
    public String familiarColumns() {
        return null;
    }

    @Override
    public PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement) {
        return null;
    }

    @Override
    public String returnColumnforDelete() {
        return null;
    }

    @Override
    public String return1QuestionMark() {
        return null;
    }

    @Override
    public PreparedStatement returnDeletePreparedStatement(PreparedStatement preparedStatement) {
        return null;
    }
}
