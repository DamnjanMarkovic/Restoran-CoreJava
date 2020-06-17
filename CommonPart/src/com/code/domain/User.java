package com.code.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class User implements CommonDomain, Serializable {

    private int id_user;
    private String username;
    private String password;
    private String userFirstName;
    private int status;
    private String imageLocation;

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public User(int id_user, String userFirstName) {
        this.id_user = id_user;
        this.userFirstName = userFirstName;
    }

    private Set<UserRole> setUserRole = new HashSet<>();

    public User() {

    }

    public int getid_user() {
        return id_user;
    }

    public void setid_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuserFirstName() {
        return userFirstName;
    }

    public void setuserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<UserRole> getSetUserRole() {
        return setUserRole;
    }

    public void setSetUserRole(Set<UserRole> setUserRole) {
        this.setUserRole = setUserRole;
    }








    @Override
    public String returnTableName() {
        return " user ";
    }

    @Override
    public String saveReturnColums() {
        return " (username, password, userFirstName, status) ";
    }

    @Override
    public String saveReturnQuestionMarks() {
        return " (?,?,?,?) ";
    }

    @Override
    public PreparedStatement setInsertValues(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userFirstName);
            preparedStatement.setInt(4, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    public Object readObjects(ResultSet resultSet) {
        return null;
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
