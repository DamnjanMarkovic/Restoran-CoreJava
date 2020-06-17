package com.comtrade.view;

import com.code.domain.ImagesNText;

import javax.swing.*;
import java.awt.*;

class ImagesTextRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean selected, boolean focused) {
        ImagesNText it = (ImagesNText) value;
        if (it != null) {
            setIcon(it.getImg());
            setText(it.getName());

        } return this;

    }
}

