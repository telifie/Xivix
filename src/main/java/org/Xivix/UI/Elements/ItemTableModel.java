package org.Xivix.UI.Elements;

import org.Xivix.Models.Flex;
import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    private final List<Flex> items;
    private final String[] columnNames = {"Item Name", "Item", "Quantity", "Price", "Total"};
    private final Class<?>[] columnTypes = {String.class, Flex.class, Integer.class, Double.class, Double.class}; // Corrected to use Double
    private final List<Object[]> data;

    public ItemTableModel(List<Flex> items) {
        this.items = items;
        data = new ArrayList<>();
        for (Flex item : items) {
            data.add(new Object[]{item.getValue("name"), item, 1, item.getValue("price"), item.getValue("price")});
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 1) { // Item changed
            Flex selectedItem = (Flex) value;
            data.get(rowIndex)[columnIndex] = selectedItem; // Update item
            data.get(rowIndex)[0] = selectedItem.getValue("name"); // Update item name
            data.get(rowIndex)[3] = selectedItem.getValue("price"); // Update price

            // Update total
            double price = Double.valueOf(data.get(rowIndex)[3].toString());
            int quantity = Integer.valueOf(data.get(rowIndex)[2].toString());
            data.get(rowIndex)[4] = price * quantity; // Update total
        } else if (columnIndex == 2) { // Quantity changed
            int quantity = Integer.valueOf(value.toString());
            data.get(rowIndex)[2] = quantity; // Update quantity

            // Update total
            double price = Double.valueOf(data.get(rowIndex)[3].toString());
            data.get(rowIndex)[4] = price * quantity; // Update total
        } else {
            data.get(rowIndex)[columnIndex] = value; // Normal behavior for other columns
        }
        fireTableCellUpdated(rowIndex, columnIndex); // Notify table of changes
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    public void addRow(Flex item) {
        data.add(new Object[]{item.getValue("name"), item, 1, item.getValue("price"), item.getValue("price")});
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public String getTotalPrice() {
        double total = 0.0;
        for (Object[] row : data) {
            total += Double.valueOf(row[4].toString()); // Summing up the total from each row
        }
        return new DecimalFormat("#.00").format(total);
    }
}