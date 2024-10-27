package org.Xivix.Utils;

import javax.swing.*;
import java.awt.*;

public class Xivix {

    private String name, transaction;
    private boolean status;
    private Color color;
    private Xivix[] children;

    public Xivix(String name, boolean status, String transaction, Color color, Xivix[] children) {
        this.name = name;
        this.status = status;
        this.transaction = transaction;
        this.color = (color == null ? UIManager.getColor("Label.foreground") : color);
        this.children = children;
    }

    public Xivix(String name, boolean status, String transaction, Xivix[] Xivixs) {
        this.name = name;
        this.status = status;
        this.transaction = transaction;
        this.color = UIManager.getColor("Label.foreground");
        this.children = Xivixs;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }

    public Color getColor() {
        return color;
    }

    public Xivix[] getChildren() {
        return children;
    }

    public String getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return name;
    }
}