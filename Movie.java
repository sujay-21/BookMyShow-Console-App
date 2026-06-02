/**
 * Movie.java
 * Represents a movie available for booking in the BookMyShow system.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 */
public class Movie {

    // ── Private Fields ──────────────────────────────────────────────────────────
    private int    movieId;
    private String title;
    private String language;
    private String genre;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public Movie(int movieId, String title, String language, String genre) {
        this.movieId  = movieId;
        this.title    = title;
        this.language = language;
        this.genre    = genre;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public int    getMovieId()  { return movieId;  }
    public String getTitle()    { return title;    }
    public String getLanguage() { return language; }
    public String getGenre()    { return genre;    }

    // ── Setters ──────────────────────────────────────────────────────────────────
    public void setTitle(String title)       { this.title    = title;    }
    public void setLanguage(String language) { this.language = language; }
    public void setGenre(String genre)       { this.genre    = genre;    }

    // ── Display ──────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("  %-3d| %-25s | %-10s | %s",
                movieId, title, language, genre);
    }
}
