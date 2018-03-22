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

}
