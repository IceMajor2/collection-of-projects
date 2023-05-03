package movie.suggestions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbScraper {

    public static List<Movie> allTimePopular() throws IOException {
        // store movies in List
        List<Movie> everPopular = new ArrayList<>();

        // get HTML parsed
        String url = "https://www.imdb.com/search/title/?title_type=feature&sort=num_votes,desc";
        Elements moviesInfo = getMoviesGrid(url);

        int count = 0;
        for (Element movie : moviesInfo) {
            
            if (count == 50) {
                break;
            }
            
            String title = parseTitle(movie);
            // if the movie has been already parsed, then skip another parse
            // by just getting info from DB
            if (Database.containsMovie(title)) {
                Movie mov = Database.getMovie(title);
                everPopular.add(mov);
                continue;
            }

            // get the rest of movie properties
            int year = parseYear(movie);
            double rating = parseRating(movie);
            int votes = parseVotes(movie);

            Movie mov = new Movie(title, year, rating, votes);
            everPopular.add(mov);
            Database.addMovie(mov);

            count++;
        }
        return everPopular;
    }

    public static List<Movie> allTimeBest() throws IOException {
        // store movies in List
        List<Movie> everBest = new ArrayList<>();

        // get HTML parsed
        String url = "https://www.imdb.com/search/title/?title_type=feature&num_votes=50000,&sort=user_rating,desc";
        Elements moviesInfo = getMoviesGrid(url);

        int count = 0;
        for (Element movie : moviesInfo) {
            
            if (count == 50) {
                break;
            }
            
            String title = parseTitle(movie);
            
            if(Database.containsMovie(title)) {
                Movie mov = Database.getMovie(title);
                everBest.add(mov);
                continue;
            }
            
            int year = parseYear(movie);
            double rating = parseRating(movie);
            int votes = parseVotes(movie);
            
            Movie mov = new Movie(title, year, rating, votes);
            everBest.add(mov);
            Database.addMovie(mov);

            count++;
        }
        return everBest;
    }
    
    public static List<Movie> yearPopular(int year) throws IOException {
        List<Movie> yearPop = new ArrayList<>();
        String url = "https://www.imdb.com/search/title/?title_type=feature&release_date=%d&sort=num_votes,desc".formatted(year);
        Elements moviesInfo = getMoviesGrid(url);
        return yearPop;
    }

    private static int parseYear(Element movie) {
        Element header = movie.select("h3").first();
        String yearStr = header.select("span.lister-item-year.text-muted.unbold").first().text();
        return yearToInt(yearStr);
    }
    
    private static String parseTitle(Element movie) {
        Element header = movie.select("h3").first();
        String title = header.select("a[href]").first().text();
        return title;
    }
    
    private static double parseRating(Element movie) {
        String ratingStr = movie.select("[name='ir']").first().text();
        return Double.valueOf(ratingStr);
    }
    
    private static int parseVotes(Element movie) {
        String votesStr = movie.select("[name='nv']").first().text();
        return removeCommasFromVotes(votesStr);
    }

    private static Elements getMoviesGrid(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements movies = doc.select("div.lister-item.mode-advanced");
        return movies;
    }

    private static int yearToInt(String yearStr) {
        int indexOfDigit = -1;

        int index = 0;
        for (char ch : yearStr.toCharArray()) {
            if (Character.isDigit(ch)) {
                indexOfDigit = index;
                break;
            }
            index++;
        }

        yearStr = yearStr.substring(indexOfDigit, indexOfDigit + 4);
        return Integer.valueOf(yearStr);
    }

    private static int removeCommasFromVotes(String votesStr) {
        StringBuilder sb = new StringBuilder("");
        for (char ch : votesStr.toCharArray()) {
            if (ch == ',') {
                continue;
            }
            sb.append(ch);
        }
        return Integer.valueOf(sb.toString());
    }
}
