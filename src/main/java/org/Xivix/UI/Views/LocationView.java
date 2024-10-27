package org.Xivix.UI.Views;

import org.Xivix.Models.Location;
import org.Xivix.Models.Transaction;
import org.Xivix.UI.Elements.Frame;
import javax.swing.*;
import java.awt.*;

public class LocationView extends Frame {

    private Location location;

    public LocationView(Transaction t, Location loc) {
        super(t);
        this.location = loc;

        JPanel panel = new JPanel(new BorderLayout());
        JPanel tb = new JPanel();
        JButton save = new JButton("Unlock");
        save.setToolTipText("Unlock data table to modify info.");
        JButton order = new JButton("Order");
        JButton bill = new JButton("Bill");
        JButton transfer = new JButton("Transfer");
        JButton inventory = new JButton("Inventory");
        JButton label = new JButton("Label");
        tb.add(save);
        tb.add(order);
        tb.add(bill);
        tb.add(transfer);
        tb.add(inventory);
        tb.add(label);
        panel.add(tb, BorderLayout.NORTH);
        String[] columns = new String[]{"Property", "Value"};
        String[][] data = {
            {"Id", loc.getId()},
            {"Tie", loc.getTie()},
            {"Name", loc.getName()},
            {"Address Line 1", loc.getLine1()},
            {"City", loc.getCity()},
            {"State", loc.getState()},
            {"Postal", loc.getPostal()},
            {"Country", loc.getCountry()},
            {"Tax Exempt Status", String.valueOf(loc.isTaxExempt())}
        };
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
