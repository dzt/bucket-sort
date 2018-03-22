import org.apache.http.client.ClientProtocolException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SortingApp extends JFrame {


    static class ActiveComboItem {
        private final Object item;

        public ActiveComboItem(Object item) { this.item = item; }

        @Override
        public boolean equals(Object other) {
            return item == null ? other == null : item.equals(other);
        }

        @Override
        public String toString() { return String.format("Animal: %s", item); }
    }

    DefaultTableModel model = new DefaultTableModel();
    JComboBox combo;

    JTable table = new JTable(model) {
        @Override
        public Class getColumnClass(int column) {
            return (column == 0) ? Icon.class : Object.class;
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };

    public SortingApp() {

        setTitle("Billboard App");
        setSize(750, 630);
        setLocation(new Point(250,250));
        setLayout(null);
        setResizable(false);

        final JScrollPane scroll = new JScrollPane(table);

        final String[] comboOptions = { "Top 100 Data (1-100)", "Top 100 Data (Weekly Appearance)", "Graph" };
        JComboBox combo = new JComboBox(comboOptions);

        // Position based off of center position minus half of the width.
        combo.setBounds(375 - 125, 25, 250, 20);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                ItemSelectable is = (ItemSelectable)actionEvent.getSource();
                String selectedItem = (String) is.getSelectedObjects()[0];

                if (selectedItem.equalsIgnoreCase("graph")) {
                    table.setVisible(false);
                    scroll.setVisible(false);
                } else if (selectedItem.equalsIgnoreCase("Top 100 Data (Weekly Appearance)")) {
                    table.setVisible(true);
                    scroll.setVisible(true);
                } else {
                    table.setVisible(true);
                    scroll.setVisible(true);
                }

            }
        };

        combo.addActionListener(actionListener);

        scroll.setBounds(25, 60, 700, 480);

        add(combo);
        add(scroll);


        model.addColumn("Image");
        model.addColumn("Rank");
        model.addColumn("Weeks on Chart");
        model.addColumn("Artist");
        model.addColumn("Song");

        table.setRowHeight(93);
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(35);
        table.getColumnModel().getColumn(2).setPreferredWidth(55);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );

        JMenuBar menubar = new JMenuBar();
        JMenu mainMenu = new JMenu("Billboard");

        JMenuItem refresh = new JMenuItem("Refresh", KeyEvent.VK_R);
        refresh.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        refresh.setToolTipText("Reload Billboard Chart Data");
        refresh.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                // TODO: Refresh Method
            }

        });

        mainMenu.add(refresh);
        menubar.add(mainMenu);
        setJMenuBar(menubar);
        setupTable();

    }

    public void setupTable() {

        Billboard billboard = new Billboard("https://www.billboard.com/charts/hot-100");
        ArrayList<Song> songs = new ArrayList<Song>();

        try {
            songs = billboard.fetchSongList();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Song song: songs) {
            System.out.println(song);
            model.addRow(new Object[]{
                    song.getImage(),
                    song.getRank(),
                    song.getWeeklyCount(),
                    song.getArtist(),
                    song.getName()
            });
        }

    }

    public static void main(String[] args){
        SortingApp app = new SortingApp();
        app.setVisible(true);
    }


}