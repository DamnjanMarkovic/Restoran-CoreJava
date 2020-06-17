package com.code.domain;

import java.io.Serializable;
import java.util.Objects;


public class UserRole implements Serializable {

    private int id_role;
    private String label;

    public UserRole() {
    }

    public UserRole(int id_role, String label) {
        this.id_role = id_role;
        this.label = label;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(label, userRole.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

}
