package movie.suggestions;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WhichMovieToWatch {

    public static void main(String[] args) throws IOException {
        String url = "http://www.imdb.com/search/title/?title_type=feature&release_date=%d".formatted(1990);
        Document doc = Jsoup.connect(url).get();
        var movies = doc.select("div.lister-item.mode-advanced").eachText();
        for(String movie : movies) {
            System.out.println(movie);
        }
        
    }
}
