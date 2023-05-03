package movie.suggestions;

public class Movie {
    
    private String name;
    private int year;
    private int rating;
    private int votes;
    
    public Movie(String name, int year, int rating, int votes) {
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

    public int getRating() {
        return rating;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }
}
