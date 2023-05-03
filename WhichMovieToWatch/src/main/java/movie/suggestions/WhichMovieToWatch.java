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
            
            List<Element> h3s = movie.select("h3").stream()
                    .collect(Collectors.toList());
            for(Element h3 : h3s) {
                Elements links = h3.select("a[href]");
                System.out.println(links.text());
            }
        }
    }
}
