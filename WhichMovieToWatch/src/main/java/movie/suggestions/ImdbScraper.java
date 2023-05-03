package movie.suggestions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbScraper {

    public static void allTimePopular() throws IOException {
        // store movies in Map
        Map<Integer, List<Movie>> everPopular = new HashMap<>();

        // get HTML parsed
        String url = "https://www.imdb.com/search/title/?title_type=feature&sort=num_votes,desc";
        Document doc = Jsoup.connect(url).get();

        Elements moviesInfo = doc.select("div.lister-item.mode-advanced");
        int count = 0;
        for (Element movie : moviesInfo) {
            if (count == 50) {
                break;
            }
            Elements header = movie.select("h3");
            String title = header.select("a[href]").first().text();
            String yearStr = header.select("span.lister-item-year.text-muted.unbold").first().text();
            int year = yearToInt(yearStr);
            count++;
        }
    }
    
    private static int yearToInt(String extractedYr) {
        int indexOfDigit = -1;
        
        int index = 0;
        for(char ch : extractedYr.toCharArray()) {
            if(Character.isDigit(ch)) {
                indexOfDigit = index;
                break;
            }
            index++;
        }
        
        String yearStr = extractedYr.substring(indexOfDigit, indexOfDigit + 4);
        return Integer.valueOf(yearStr);
    }
}
