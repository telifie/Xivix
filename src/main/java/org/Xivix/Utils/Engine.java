package org.Xivix.Utils;

import org.Xivix.Models.*;
import org.Xivix.UI.Views.*;
import org.Xivix.UI.Views.New.*;
import org.Xivix.UI.Views.Singleton.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Engine {

    private static Configuration configuration;
    public static User client;
    public static ArrayList<Flex> areas = new ArrayList<>();
    public static ArrayList<Flex> employees = new ArrayList<>();
    public static ArrayList<Flex> users = new ArrayList<>();

    public static void load(){
        areas.clear();
        File[] areasDir = Pipe.list("AREAS");
        for (File file : areasDir) {
            if (!file.isDirectory()) {
                Flex a = Json.load(file.getPath(), Flex.class);
                areas.add(a);
            }
        }
        areas.sort(Comparator.comparing(Flex::getId));
        employees.clear();
        File[] empDir = Pipe.list("EMPS");
        for (File file : empDir) {
            if (!file.isDirectory()) {
                Flex a = Json.load(file.getPath(), Flex.class);
                employees.add(a);
            }
        }
        employees.sort(Comparator.comparing(Flex::getId));
        users.clear();
        File[] usrsDir = Pipe.list("USRS");
        for (File file : usrsDir) {
            if (!file.isDirectory()) {
                Flex a = Json.load(file.getPath(), Flex.class);
                users.add(a);
            }
        }
        users.sort(Comparator.comparing(Flex::getId));
    }

    public static void setTheme(String themePath){
        configuration.setTheme(themePath);
        System.out.println(configuration.getTheme());
        Pipe.saveConfiguration(themePath);
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        Engine.configuration = configuration;
    }

    public static ArrayList<Flex> getAreas() {
        return areas;
    }

    public static List<Flex> getAreas(String id) {
        return areas.stream().filter(location -> location.getValue("cost_center").equals(id)).collect(Collectors.toList());
    }

    public static ArrayList<Flex> getEmployees() {
        return employees;
    }

    public static List<Flex> getEmployees(String id) {
        return employees.stream().filter(location -> location.getValue("org").equals(id)).collect(Collectors.toList());
    }

    public static ArrayList<Flex> getUsers() {
        return users;
    }

    public static void router(String transactionCode){
        transactionCode = transactionCode.toUpperCase().trim();
        if(!transactionCode.startsWith("/")){
            transactionCode = "/" + transactionCode;
        }
        switch (transactionCode) {
            case "/" -> new Controller();
            case "/PPL" -> new CreatePerson();
            case "/PPL/NEW" -> new CreatePerson();
            case "/LOCS" -> new Locations("Locations", "/CCS");
            case "/AREAS" -> new Flexes("Areas", "/AREAS");
            case "/EMPS" -> new Flexes("Employees", "/EMPS");
            case "/USRS" -> new Flexes("Users", "/USRS");
            case "/ITS" -> new Flexes("Item", "/ITS");
            case "/CNL" -> new XivixSettings();
            case "/CNL/EXIT" -> System.exit(-1);
            default -> {
                String[] chs = transactionCode.split("/");
                String t = chs[1];
                String oid = chs[2];
                System.out.println(oid);
                switch (t) {
                    case "AREAS" -> {
                        for(Flex l : Engine.getAreas()){
                            if(l.getId().equals(oid)){

                            }
                        }
                    }
                    case "EMPS" -> new Flexes("Employees", "/EMPS");
                    case "USRS" -> new Flexes("Users", "/USRS");
                }
            }
        }
    }

    public static void adjustColumnWidths(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int maxWidth;
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComponent = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, col);
            maxWidth = headerComponent.getPreferredSize().width;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component cellComponent = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col);
                int cellWidth = cellComponent.getPreferredSize().width;
                maxWidth = Math.max(maxWidth, cellWidth);
            }
            column.setPreferredWidth(maxWidth + 10);
        }
    }
}