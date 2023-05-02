package movie.suggestions;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WhichMovieToWatch {

    public static void main(String[] args) throws IOException {
        String url = "http://www.imdb.com/search/title/?title_type=feature&release_date=%d".formatted(1990);
        Document doc = Jsoup.connect(url).get();
        for(Element movie : doc.select("div.lister-item.mode-advanced")) {
            List<String> h3s = movie.select("h3").stream()
                    .map(Element::text)
                    .collect(Collectors.toList());
            for(String h3 : h3s) {
                System.out.println(h3);
            }
        }
    }
}
