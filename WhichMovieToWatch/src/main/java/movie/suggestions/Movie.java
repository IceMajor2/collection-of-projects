package movie.suggestions;

public class Movie {
    
    private String name;
    private int year;
    private double rating;
    private int votes;
    
    public Movie(String name, int year, double rating, int votes) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getVotes() {
        return votes;
    }

    public double getRating() {
        return rating;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "%s (%d): %.1f - %d votes".formatted(name, year, rating, votes);
    }
}
