package org.Xivix.UI.Views.Singleton;

import org.Xivix.Models.*;
import org.Xivix.UI.Elements.Frame;
import org.Xivix.UI.Elements.IconButton;
import org.Xivix.Utils.Xivix;
import org.Xivix.Utils.Engine;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DCView extends Frame implements RefreshListener {

    private Location location;
    private JTree dataTree;

    public DCView(Location loc) {
        super(new Transaction("DC", "/DCSS"));
        this.location = loc;
        setTitle("DC / " + loc.getId() + " - " + loc.getName());
        setLayout(new BorderLayout());
        JPanel tb = createToolBar();
        add(tb, BorderLayout.NORTH);
        JTable table = createTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        JPanel dataView = new JPanel(new BorderLayout());
        JTextField cmd = new JTextField("/CCS/" + location.getId());
        dataView.add(cmd, BorderLayout.NORTH);
        dataView.add(tableScrollPane, BorderLayout.CENTER);
        dataTree = createTree();
        dataTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = dataTree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        Xivix orgNode = (Xivix) node.getUserObject();
                        String tcode = orgNode.getTransaction();
                        Engine.router(orgNode.getTransaction());
                    }
                }
            }
        });
        JScrollPane treeScrollPane = new JScrollPane(dataTree);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, dataView);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createToolBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton acceptPayment = new IconButton("Accept $", "order", "Receiving an order or taking payment");
        IconButton payBill = new IconButton("Pay Bill", "bill", "Receiving a bill from a vendor");
        IconButton inventory = new IconButton("Inventory", "inventory", "Inventory of items in cost center");
        IconButton areas = new IconButton("+ Areas", "areas", "Add an area cost center");
        IconButton label = new IconButton("", "label", "Print labels for properties");
        IconButton pos = new IconButton("", "pos", "Launch Point-of-Sale");
        IconButton refresh = new IconButton("", "refresh", "Reload from store");
        refresh.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            Engine.load();
            Xivix rootNode = createRootNode();
            DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
            DefaultTreeModel model = (DefaultTreeModel) dataTree.getModel();
            model.setRoot(rootTreeNode);
            expandAllNodes(dataTree);
            revalidate();
            repaint();
            }
        });
        tb.add(acceptPayment);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(payBill);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(inventory);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(areas);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(label);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(refresh);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(pos);
        return tb;
    }

    private JTable createTable() {
        String[] columns = new String[]{"Property", "Value"};
        String[][] data = {
                {"Id", location.getId()},
                {"Tie", location.getTie()},
                {"Name", location.getName()},
                {"Address Line 1", location.getLine1()},
                {"City", location.getCity()},
                {"State", location.getState()},
                {"Postal", location.getPostal()},
                {"Country", location.getCountry()},
                {"Tax Exempt Status", String.valueOf(location.isTaxExempt())}
        };
        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Engine.adjustColumnWidths(table);
        return table;
    }

    private JTree createTree() {
        Xivix rootNode = createRootNode();
        DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootTreeNode);
        JTree tree = new JTree(treeModel);
        tree.setCellRenderer(new CustomTreeCellRenderer());
        expandAllNodes(tree);
        return tree;
    }

    private Xivix createRootNode() {
        Xivix[] areas = new Xivix[Engine.getAreas(location.getId()).size()];
        for (int i = 0; i < Engine.getAreas(location.getId()).size(); i++) {
            Flex l = Engine.getAreas(location.getId()).get(i);
            areas[i] = new Xivix(l.getId() + " - " + l.getValue("name"), false, "/ITS/" + l.getId(), new Color(147, 70, 3), null);
        }

        return new Xivix(location.getId() + " - " + location.getName(), true, "/ORGS", new Xivix[]{
                new Xivix("Areas", true, "/AREAS", areas),
                new Xivix("Bins", true, "/BNS", null),
                new Xivix("Materials", true, "/MTS", null),
                new Xivix("Orders", true, "/ORDS", null),
                new Xivix("Employees", true, "/EMPS", null),
                new Xivix("Reports", true, "/RPTS", new Xivix[]{
                    new Xivix("Annual Ledger Report", false, "/RPTS/LGS/ANNUM", null),
                    new Xivix("Monthly Ledger Report", false, "/RPTS/MONTH", null),
                    new Xivix("CC Ledger", false, "/RPTS/CCS/LGS", null),
                    new Xivix("Annual Labor", false, "/RPTS/ANUM_LBR", null),
                    new Xivix("Monthly Labor", false, "/RPTS/MONTH_LBR", null),
                    new Xivix("Daily Labor", false, "/RPTS/DL_LBR", null),
                    new Xivix("Current Inventory", false, "/RPTS/CRNT_INV", null),
                    new Xivix("Inventory Count", false, "/RPTS/COUNT_INV", null),
                }),
        });
    }

    private DefaultMutableTreeNode createTreeNodes(Xivix node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node);
        if (node.getChildren() != null) {
            for (Xivix child : node.getChildren()) {
                treeNode.add(createTreeNodes(child));
            }
        }
        return treeNode;
    }

    @Override
    public void onRefresh() {

    }

    static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Xivix orgNode = (Xivix) treeNode.getUserObject();
            if (orgNode.getStatus()) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            } else {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
            setForeground(orgNode.getColor());
            return component;
        }
    }

    private void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }
}