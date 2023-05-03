package movie.suggestions;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to MovieSuggestions. (based on IMdB data)");
        while (true) {
            System.out.println("1. 50 most popular movies of all time");
            System.out.println("2. 50 best rated movies of all time");
            System.out.println("3. 20 most popular movies in a given year");
            System.out.println("4. 20 best rated movies in a given year");
            System.out.println("0. Exit");

            System.out.print("> ");
            String input = scanner.nextLine();

            if ("0".equals(input)) {
                System.out.println("Bye!");
                break;
            }
            if ("1".equals(input)) {
                printAllTimePopular();
                continue;
            }
            if("2".equals(input)) {
                printAllTimeBest();
                continue;
            }
            System.out.println("ERROR! Please input a valid number.");
        }
    }

    public void printAllTimePopular() {
        List<Movie> movies = null;
        try {
            movies = ImdbScraper.allTimePopular();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int pos = 1;
        for(Movie mov : movies) {
            System.out.println("%d. %s".formatted(pos, mov.toString()));
            pos++;
        }
    }
    
    public void printAllTimeBest() {
        List<Movie> movies = null;
        try {
            movies = ImdbScraper.allTimeBest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int pos = 1;
        for(Movie mov : movies) {
            System.out.println("%d. %s".formatted(pos, mov.toString()));
            pos++;
        }
    }
}
