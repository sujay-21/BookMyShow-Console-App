/**
 * Booking.java
 * Stores all details for a single confirmed booking.
 * Demonstrates Encapsulation.
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Booking {

    // ── Private Fields ──────────────────────────────────────────────────────────
    private String bookingId;
    private Movie  movie;
    private Seat   seat;
    private int    amountPaid;
    private boolean isCancelled;
    private LocalDateTime bookingTime;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public Booking(String bookingId, Movie movie, Seat seat, int amountPaid) {
        this.bookingId   = bookingId;
        this.movie       = movie;
        this.seat        = seat;
        this.amountPaid  = amountPaid;
        this.isCancelled = false;
        this.bookingTime = LocalDateTime.now();
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public String  getBookingId()   { return bookingId;   }
    public Movie   getMovie()       { return movie;        }
    public Seat    getSeat()        { return seat;         }
    public int     getAmountPaid()  { return amountPaid;   }
    public boolean isCancelled()    { return isCancelled;  }

    /** Marks this booking as cancelled and frees the seat. */
    public void cancel() {
        this.isCancelled = true;
        seat.cancel();
    }

    // ── Display ──────────────────────────────────────────────────────────────────
    /** Compact one-line summary used in booking history table. */
    public String toTableRow() {
        String status = isCancelled ? "CANCELLED" : "CONFIRMED";
        return String.format("  %-10s | %-25s | %-6s | %-8s | Rs.%-5d | %s",
                bookingId,
                movie.getTitle(),
                seat.getSeatId(),
                seat.getCategory(),
                amountPaid,
                status);
    }

    /** Full ticket printout shown after successful payment. */
    public void printTicket() {
        System.out.println();
        System.out.println("  =================================");
        System.out.println("       PVR CINEMAS BENGALURU");
        System.out.println("         BOOKING CONFIRMED         ");
        System.out.println("  =================================");
        System.out.printf ("  Booking ID : %s%n",  bookingId);
        System.out.printf ("  Movie      : %s%n",  movie.getTitle());
        System.out.printf ("  Seat       : %s%n",  seat.getSeatId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.printf("  Booked On  : %s%n", bookingTime.format(formatter));
        System.out.printf ("  Amount     : Rs.%d%n", amountPaid);
        System.out.println("  =================================");
        System.out.println("        Enjoy Your Show!           ");
        System.out.println("  =================================");
        System.out.println();
    }
}
