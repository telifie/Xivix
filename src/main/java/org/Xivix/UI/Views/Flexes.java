package org.Xivix.UI.Views;

import org.Xivix.Models.Flex;
import org.Xivix.Models.RefreshListener;
import org.Xivix.Models.Transaction;
import org.Xivix.UI.Elements.Frame;
import org.Xivix.Utils.Engine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Flexes extends Frame implements RefreshListener {

    private String tcode;
    private DefaultListModel<Flex> listModel;

    public Flexes(String type, String tcode) {
        super(new Transaction(type, tcode));
        this.tcode = tcode;
        listModel = new DefaultListModel<>();
        JList<Flex> list = new JList<>(listModel);
        list.setCellRenderer(new ItemRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = list.locationToIndex(e.getPoint());
                    if (selectedIndex != -1) {
                        Flex item = listModel.getElementAt(selectedIndex);
                        Engine.router(tcode + "/" + item.getId());
                    }
                }
            }
        });
        JTextField direct = new JTextField();
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    System.out.println(inputText);
                    if (!inputText.isEmpty()) {
                        Engine.router(tcode + "/" + inputText);
                    }
                }
            }
        });
        JButton nla = new JButton("Add");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(direct, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(nla, BorderLayout.SOUTH);
        add(mainPanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        loadFlexes();
    }

    @Override
    public void onRefresh() {
        loadFlexes();
    }

    private void loadFlexes(){
        ArrayList<Flex> found = new ArrayList<>();
        listModel.removeAllElements();
        Engine.load();
        switch (tcode){
            case "/AREAS" -> found = Engine.getAreas();
            case "/EMPS" -> found = Engine.getEmployees();
        }
        for (Flex f : found) {
            listModel.addElement(f);
        }
    }

    class ItemRenderer extends JPanel implements ListCellRenderer<Flex> {

        private JLabel ccName;
        private JLabel ccId;
        private JLabel line1;

        public ItemRenderer() {
            setLayout(new GridLayout(4, 1));
            ccName = new JLabel();
            ccId = new JLabel();
            line1 = new JLabel();
            ccName.setFont(new Font("Arial", Font.BOLD, 16));
            ccId.setFont(new Font("Arial", Font.PLAIN, 12));
            line1.setFont(new Font("Arial", Font.PLAIN, 12));
            add(ccName);
            add(ccId);
            add(line1);
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Flex> list, Flex value, int index, boolean isSelected, boolean cellHasFocus) {
            ccName.setText(value.getValue("name"));
            ccId.setText(value.getId());
            line1.setText(value.getValue("color"));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}