package movie.suggestions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbScraper {
    
    public static void allTimePopular() throws IOException {
        // store movies in Map
        Map<Integer, Movie> everPopular = new HashMap<>();
        
        // get HTML parsed
        String url = "https://www.imdb.com/search/title/?title_type=feature&sort=num_votes,desc";
        Document doc = Jsoup.connect(url).get();
        
        Elements moviesInfo = doc.select("div.lister-item.mode-advanced");
        int count = 0;
        for(Element movie : moviesInfo) {
            if(count == 50) {
                break;
            }
            Elements header = movie.select("h3");
            String title = header.select("a[href]").first().text();
            System.out.println(title);
            count++;
        }
    }
}
//        
//        Document doc = Jsoup.connect(url).get();
//        for(Element movie : doc.select("div.lister-item.mode-advanced")) {
//            
//            List<Element> h3s = movie.select("h3").stream()
//                    .collect(Collectors.toList());
//            for(Element h3 : h3s) {
//                Elements links = h3.select("a[href]");
//                System.out.println(links.text());
//            }
//        }