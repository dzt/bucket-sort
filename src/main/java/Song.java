import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Song {

    private String name, artist, imageURL;
    private int peak, weeks, rank;

    public Song(String name, String artist, String imageURL, int rank, int peak, int weeks) {
        this.name = name;
        this.artist = artist;
        this.imageURL = imageURL;
        this.peak = peak;
        this.weeks = weeks;
        this.rank = rank;
    }

    public String toString() {
        String text = "Song[";
        text += "name=" + this.name + ",";
        text += "artist=" + this.artist + ",";
        text += "imageURL=" + this.imageURL + ",";
        text += "rank=" + this.rank + ",";
        text += "peak=" + this.peak + ",";
        text += "weeks=" + this.weeks + "]";
        return text;
    }

    public int getRank() {
        return this.rank;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public int getWeeklyCount() {
        return this.weeks;
    }

    public ImageIcon getImage() {

        ImageIcon img = new ImageIcon();

        try {

            URL url = new URL(this.imageURL);
            img = new ImageIcon(ImageIO.read(url));
            Image image = img.getImage();
            Image newImg = image.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
            img = new ImageIcon(newImg);
            return img;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;

    }

}
