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

        final String[] comboOptions = { "Top 100 Data", "Graph" };
        JComboBox combo = new JComboBox(comboOptions);
        combo.setBounds(300, 25, 150, 20);

        combo.setModel(new DefaultComboBoxModel(comboOptions) {
            private Object selected;

            public void setSelectedItem(Object anItem) {
                selected = anItem;
            }

//            public Object getSelectedItem() {
//                return new ActiveComboItem(selected);
//            }
        });

        JScrollPane scroll = new JScrollPane(table);

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
        table.getColumnModel().getColumn(2).setPreferredWidth(45);
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