import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BookingManager.java
 * Central service class that manages movies, seats, and bookings.
 *
 * Responsibilities:
 *   • Initialise the preloaded movie catalogue
 *   • Initialise the 100-seat theatre layout
 *   • Process bookings and cancellations
 *   • Maintain booking history
 *
 * Demonstrates use of ArrayList, HashMap and OOP design.
 */
public class BookingManager {

    // ── Collections ──────────────────────────────────────────────────────────────
    private List<Movie>           movieList;        // ordered catalogue
    private Map<String, Seat>     seatMap;          // "B7" → Seat object
    private List<Booking>         bookingHistory;   // all confirmed bookings
    private Map<String, Booking>  bookingIndex;     // bookingId → Booking (fast lookup)

    // ── Booking ID counter ───────────────────────────────────────────────────────
    private int bookingCounter = 1001;

    // ── Theatre row configuration ────────────────────────────────────────────────
    private static final char[] ROWS        = {'A','B','C','D','E','F','G','H','I','J'};
    private static final int    SEATS_PER_ROW = 10;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public BookingManager() {
        movieList      = new ArrayList<>();
        seatMap        = new HashMap<>();
        bookingHistory = new ArrayList<>();
        bookingIndex   = new HashMap<>();

        loadMovies();
        initSeats();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Initialisation helpers
    // ────────────────────────────────────────────────────────────────────────────

    /** Preloads 15 movies into the catalogue. */
    private void loadMovies() {
        movieList.add(new Movie( 1, "Avengers Endgame", "English", "Action"));
        movieList.add(new Movie( 2, "Interstellar",     "English", "Sci-Fi"));
        movieList.add(new Movie( 3, "Inception",         "English", "Thriller"));
        movieList.add(new Movie( 4, "Jawan",             "Hindi",   "Action"));
        movieList.add(new Movie( 5, "Kalki 2898 AD",     "Telugu",  "Sci-Fi"));
        movieList.add(new Movie( 6, "KGF Chapter 2",     "Kannada", "Action"));
        movieList.add(new Movie( 7, "Leo",               "Tamil",   "Action"));
        movieList.add(new Movie( 8, "Pushpa 2",          "Telugu",  "Action"));
        movieList.add(new Movie( 9, "Dune",              "English", "Sci-Fi"));
        movieList.add(new Movie(10, "The Batman",        "English", "Action"));
        movieList.add(new Movie(11, "Drishyam 2",        "Hindi",   "Thriller"));
        movieList.add(new Movie(12, "RRR",               "Telugu",  "Action"));
        movieList.add(new Movie(13, "Oppenheimer",       "English", "Drama"));
        movieList.add(new Movie(14, "Bahubali 2",        "Telugu",  "Action"));
        movieList.add(new Movie(15, "Joker",             "English", "Drama"));
    }

    /** Creates 100 Seat objects (A1–J10) and stores them in seatMap. */
    private void initSeats() {
        for (char row : ROWS) {
            for (int num = 1; num <= SEATS_PER_ROW; num++) {
                Seat seat = new Seat(row, num);
                seatMap.put(seat.getSeatId(), seat);
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Movie helpers
    // ────────────────────────────────────────────────────────────────────────────

    public List<Movie> getMovieList() { return movieList; }

    /** Returns a Movie by its 1-based index, or null if out of range. */
    public Movie getMovieByIndex(int index) {
        if (index < 1 || index > movieList.size()) return null;
        return movieList.get(index - 1);
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Seat helpers
    // ────────────────────────────────────────────────────────────────────────────

    /** Returns the Seat for the given id (case-insensitive), or null. */
    public Seat getSeat(String seatId) {
        return seatMap.get(seatId.toUpperCase());
    }

    public Map<String, Seat> getSeatMap() { return seatMap; }

    // ────────────────────────────────────────────────────────────────────────────
    //  Booking logic
    // ────────────────────────────────────────────────────────────────────────────

    /**
     * Attempts to create a new booking.
     *
     * @param movie   The selected Movie
     * @param seatId  The selected seat id (e.g. "B7")
     * @return        The new Booking object on success, or null on failure.
     */
    public Booking createBooking(Movie movie, String seatId) {
        Seat seat = getSeat(seatId);
        if (seat == null || !seat.book()) return null;   // invalid or already booked

        String    id      = "BMS" + bookingCounter++;
        Booking   booking = new Booking(id, movie, seat, seat.getPrice());

        bookingHistory.add(booking);
        bookingIndex.put(id, booking);
        return booking;
    }

    /**
     * Cancels an existing booking by ID.
     *
     * @param bookingId  e.g. "BMS1001"
     * @return           true if cancelled successfully, false otherwise.
     */
    public boolean cancelBooking(String bookingId) {
        Booking booking = bookingIndex.get(bookingId.toUpperCase());
        if (booking == null || booking.isCancelled()) return false;

        booking.cancel();
        return true;
    }

    /** Returns a copy of the full booking history list. */
    public List<Booking> getBookingHistory() {
        return new ArrayList<>(bookingHistory);
    }

    public int getAvailableSeatCount() {
    int count = 0;

    for (Seat seat : seatMap.values()) {
        if (!seat.isBooked()) {
            count++;
        }
    }
    

    return count;
}
public int getTotalRevenue() {
    int revenue = 0;

    for (Booking booking : bookingHistory) {
        if (!booking.isCancelled()) {
            revenue += booking.getAmountPaid();
        }
    }

    return revenue;
}
}
