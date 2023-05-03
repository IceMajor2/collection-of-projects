package movie.suggestions;

import java.util.HashMap;
import java.util.Map;

public class Database {
    
    private static Map<String, Movie> allMovies = new HashMap<>();
    
    public static boolean containsMovie(String title) {
        return allMovies.containsKey(title);
    }
    
    public static Movie getMovie(String title) {
        return allMovies.get(title);
    }
    
    public static void addMovie(Movie movie) {
        String title = movie.getName();
        allMovies.put(title, movie);
    }

    public static Map<String, Movie> getAllMovies() {
        return allMovies;
    }
}