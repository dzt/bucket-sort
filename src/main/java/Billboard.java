import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Billboard {

    private static HttpClient httpClient = new DefaultHttpClient();
    private String baseURL;

    public Billboard(String baseURL) {
        this.baseURL = baseURL;
    }

    private String fetchPageContent() throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        try {
            HttpGet httpget = new HttpGet("https://www.billboard.com/charts/hot-100");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);

            return responseBody;
        } catch (HttpResponseException e) {
            throw e;
        } catch (ClientProtocolException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

    }

    public ArrayList<Song> fetchSongList() throws IOException {

        ArrayList<Song> arr = new ArrayList<Song>();

        try {

            String html = this.fetchPageContent();
            Document document = Jsoup.parse(html);

            Elements songs = document.select("h2.chart-row__song");
            Elements peakRanks = document.select("div.chart-row__top-spot");
            Elements weeklyRanks = document.select("div.chart-row__weeks-on-chart");
            Elements images = document.select("div.chart-row__image");
            Elements artists = document.select(".chart-row__artist");

            for (int i = 0; i < songs.size(); i++) {

                String songName = songs.get(i).text();
                String artist = artists.get(i).text();
                String image;

                if (images.get(i).attr("data-imagesrc") != "") {
                    image = images.get(i).attr("data-imagesrc");
                } else if (images.get(i).attr("style") != "") {
                    image = images.get(i).attr("style").split("background-image: url\\(")[1].split("\\)")[0];
                } else {
                    image = "https://assets.billboard.com/assets/1520627229/images/chart-row-placeholder.jpg";
                }


                int ranking = i + 1;
                int peakPos = Integer.parseInt(peakRanks.get(i).select("span.chart-row__value").get(0).text());
                int weeks = Integer.parseInt(weeklyRanks.get(i).select("span.chart-row__value").get(0).text());

                System.out.println(artist);
                System.out.println(songName + " #" + ranking + " / Peak: " + peakPos + " / Weeks: " + weeks);
                System.out.println(image + "\n");

                arr.add(new Song(songName, artist, image, ranking, peakPos, weeks));

            }

            return arr;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }

}
