package org.Xivix.UI.Elements;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Label extends JLabel {

    public Label(String text){
        super(text);
        setFont(UIManager.getFont("h2.font"));
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public Label(String text, Color color){
        super(text);
        setFont(UIManager.getFont("h5.font"));
        setPreferredSize(new Dimension(120, 25));
        Border emptyBorder = new EmptyBorder(5, 5, 5, 5);
        Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, color);
        Border compoundBorder = BorderFactory.createCompoundBorder(matteBorder, emptyBorder);
        setBorder(compoundBorder);
    }
}