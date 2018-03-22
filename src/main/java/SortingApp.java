import org.apache.http.client.ClientProtocolException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SortingApp extends JFrame {

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    public SortingApp() {

        setTitle("Billboard App");
        setSize(600,600);
        setLocation(new Point(250,250));
        setLayout(null);
        setResizable(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(25,10,550,550);

        add(scroll);

        model.addColumn("Rank");
        model.addColumn("Artist");
        model.addColumn("Song");

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
                    song.getRank(),
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