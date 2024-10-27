package org.Xivix.Utils;

import java.awt.*;
import java.util.ArrayList;

public class Constants {

    public final static Color[] colors = new Color[]{
            new Color(251, 88, 88),   // Red
            new Color(255, 134, 92),  // Orange
            new Color(255, 165, 0),   // Deep Orange
            new Color(255, 173, 92),  // Light Orange
            new Color(251, 251, 85),  // Yellow
            new Color(199, 253, 88),  // Light Green
            new Color(170, 253, 88),  // Green
            new Color(127, 255, 0),    // Neon Green
            new Color(88, 251, 169),  // Light Turquoise
            new Color(87, 196, 251),  // Sky Blue
            new Color(93, 135, 251),  // Blue
            new Color(93, 93, 255),   // Indigo
            new Color(171, 90, 253),  // Violet
            new Color(251, 90, 251),  // Magenta
            new Color(255, 105, 180), // Hot Pink
            new Color(255, 182, 193), // Light Pink
            new Color(232, 169, 178), // Light Pink
    };


    public static ArrayList<String> getAllTransactions() {
        ArrayList<String> transactions = new ArrayList<>();
        if (allModules().getTransaction() != null && !transactions.contains(allModules().getTransaction())) {
            transactions.add(allModules().getTransaction());
        }
        if (allModules().getChildren() != null) {
            for (Xivix child : allModules().getChildren()) {
                transactions.addAll(getTransactions(child));
            }
        }
        return removeDuplicates(transactions);
    }

    public static ArrayList<String> getTransactions(Xivix Xivix) {
        ArrayList<String> transactions = new ArrayList<>();
        if (Xivix.getTransaction() != null && !transactions.contains(Xivix.getTransaction())) {
            transactions.add(Xivix.getTransaction());
        }
        if (Xivix.getChildren() != null) {
            for (Xivix child : Xivix.getChildren()) {
                transactions.addAll(getTransactions(child));
            }
        }
        return removeDuplicates(transactions);
    }

    private static ArrayList<String> removeDuplicates(ArrayList<String> transactions) {
        ArrayList<String> uniqueTransactions = new ArrayList<>();
        for (String transaction : transactions) {
            if (!uniqueTransactions.contains(transaction)) {
                uniqueTransactions.add(transaction);
            }
        }
        return uniqueTransactions;
    }

    public static Xivix allModules() {
        return new Xivix(Engine.getConfiguration().getInstance_name() + " – All Actions", true, "/", new Xivix[]{
                new Xivix("People", true, "/PPL", new Xivix[]{
                        new Xivix("Find", false, "/PPL/F", null),
                        new Xivix("Create", false, "/PPL/NEW", null),
                        new Xivix("View", false, "/PPL", null),
                        new Xivix("Modify", true, "/PPL/MOD", new Xivix[]{
                                new Xivix("Suspend", false, "/PPL/MOD/SP", null),
                                new Xivix("Change Access", false, "/PPL/MOD/CHGAC", null),
                                new Xivix("Remove", false, "/PPL/MOD/DEL", null),
                        }),
                }),
                new Xivix("Locations", true, "/LOCS", new Xivix[]{
                        new Xivix("Find with ID", false, "/LOCS/F", null),
                        new Xivix("Create a Location", false, "/LOCS/NEW", null),
                        new Xivix("Modify", false, "/LOCS/MOD", null),
                        new Xivix("Remove", false, "/LOCS/DEL", null),
                }),
                new Xivix("Invoicing", true, "/INVS", new Xivix[]{
                        new Xivix("Find", false, "/INVS/F", null),
                        new Xivix("Create an Invoice", false, "/INVS/NEW", null),
                        new Xivix("Modify", true, "/INVS/MOD", new Xivix[]{
                                new Xivix("Invoice", false, "/INVS/", null),
                        }),
                }),
                new Xivix("Users", true, "USRS", new Xivix[]{
                        new Xivix("Find", false, "/USRS/F", null),
                        new Xivix("Create", false, "/USRS/NEW", null),
                        new Xivix("View", false, "/USRS", null),
                        new Xivix("Modify", true, "/USRS/MOD", new Xivix[]{
                                new Xivix("Suspend", false, "/USRS/MOD/SP", null),
                                new Xivix("Change Access", false, "/USRS/MOD/CHGAC", null),
                                new Xivix("Remove", false, "/USRS/MOD/DEL", null),
                        }),
                }),
                new Xivix("Businesses", true, "/VEND", new Xivix[]{
                        new Xivix("Find", false, "/VEND/F", null),
                        new Xivix("Create", false, "/VEND/NEW", null),
                        new Xivix("Remove", false, "/VEND/DEL", null),
                        new Xivix("Modify", true, "/VEND/MOD", new Xivix[]{
                                new Xivix("Order", false, "/ORDS/NEW", null),
                                new Xivix("Stock Check", false, "", null),
                                new Xivix("Block", false, "", null)
                        }),
                }),
                new Xivix("Items", true, "/ITS", new Xivix[]{
                        new Xivix("Find", false, "/ITS/F", null),
                        new Xivix("Create", false, "/ITS/NEW", null),
                        new Xivix("Modify", false, "/ITS/MOD", null),
                        new Xivix("Remove", false, "/ITS/DEL", null),
                }),
                new Xivix("Employees", true, "/EMPS", new Xivix[]{
                        new Xivix("Find", false, "/EMPS", null),
                        new Xivix("Create", false, "/EMPS/NEW", null),
                        new Xivix("Modify", false, "/EMPS/MOD", null),
                        new Xivix("Remove", false, "/EMPS/DEL", null),
                }),
                new Xivix("Xivix Settings", false, "/CNL", null),
                new Xivix("Leave Xivix", false, "/CNL/EXIT", null),
        });
    }

    public static boolean isXivixAssigned(){
        return Engine.client == null;
    }
}