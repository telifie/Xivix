package org.Xivix.UI.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Selectable extends JPanel {

    private JComboBox<String> comboBox;
    private HashMap<String, String> itemsMap;

    public Selectable(HashMap<String, String> itemsMap) {
        this.itemsMap = itemsMap;
        initializeComponent();
    }

    public void editable(){
        comboBox.setEditable(true);
    }

    private void initializeComponent() {
        comboBox = new JComboBox<>(itemsMap.keySet().stream().sorted().toArray(String[]::new));
        this.setLayout(new BorderLayout());
        this.add(comboBox, BorderLayout.CENTER);
    }

    public String getSelectedValue() {
        String selectedKey = (String) comboBox.getSelectedItem();
        return itemsMap.get(selectedKey);
    }

    public void setSelectedValue(String value) {
        for (Map.Entry<String, String> entry : itemsMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                comboBox.setSelectedItem(entry.getKey());
                break;
            }
        }
    }

    public void addActionListener(ActionListener l){
        comboBox.addActionListener(l);
    }

    public Object getSelected(){
        return comboBox.getSelectedItem();
    }
}