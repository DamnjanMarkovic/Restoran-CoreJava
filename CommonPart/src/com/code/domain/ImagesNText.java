package com.code.domain;

import javax.swing.*;

public class ImagesNText {

    private Icon img;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public ImagesNText(Icon img, String name) {
        this.img = img;
        this.name = name;
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

}
