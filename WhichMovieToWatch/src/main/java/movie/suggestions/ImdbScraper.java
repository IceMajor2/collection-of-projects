package movie.suggestions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbScraper {

    public static List<Movie> allTimePopular() throws IOException {
        // store movies in Map
        List<Movie> everPopular = new ArrayList<>();

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
            
            // if the movie has been already parsed, then skip another parse
            // by just getting info from DB
            if(Database.allMovies.containsKey(title)) {
                Movie mov = Database.allMovies.get(title);
                everPopular.add(mov);
                break;
            }
            
            String yearStr = header.select("span.lister-item-year.text-muted.unbold").first().text();
            String ratingStr = movie.select("[name='ir']").first().text();
            String votesStr = movie.select("[name='nv']").first().text();

            int year = yearToInt(yearStr);
            double rating = Double.valueOf(ratingStr);
            int votes = removeCommasFromVotes(votesStr);
            
            Movie mov = new Movie(title, year, rating, votes);
            everPopular.add(mov);
            
            count++;
        }
        return everPopular;
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
    
    private static int removeCommasFromVotes(String votesStr) {
        StringBuilder sb = new StringBuilder("");
        for(char ch : votesStr.toCharArray()) {
            if(ch == ',') {
                continue;
            }
            sb.append(ch);
        }
        return Integer.valueOf(sb.toString());
    }
}
