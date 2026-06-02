/**
 * Seat.java
 * Represents a single seat in the theatre.
 * Encapsulates seat identity, availability, category and price.
 */
public class Seat {

    // ── Seat Category Constants ──────────────────────────────────────────────────
    public static final String DIAMOND = "Diamond";
    public static final String GOLD    = "Gold";
    public static final String SILVER  = "Silver";

    // ── Price Constants ──────────────────────────────────────────────────────────
    public static final int PRICE_DIAMOND = 500;
    public static final int PRICE_GOLD    = 300;
    public static final int PRICE_SILVER  = 150;

    // ── Private Fields ──────────────────────────────────────────────────────────
    private String  seatId;    // e.g. "B7"
    private char    row;       // e.g. 'B'
    private int     number;    // e.g.  7
    private boolean isBooked;
    private String  category;
    private int     price;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public Seat(char row, int number) {
        this.row      = row;
        this.number   = number;
        this.seatId   = String.valueOf(row) + number;
        this.isBooked = false;
        determineCategory();
    }

    /**
     * Determines seat category and price based on row letter.
     * Diamond : A – B  → ₹500
     * Gold    : C – F  → ₹300
     * Silver  : G – J  → ₹150
     */
    private void determineCategory() {
        char r = Character.toUpperCase(row);
        if (r == 'A' || r == 'B') {
            this.category = DIAMOND;
            this.price    = PRICE_DIAMOND;
        } else if (r >= 'C' && r <= 'F') {
            this.category = GOLD;
            this.price    = PRICE_GOLD;
        } else {
            this.category = SILVER;
            this.price    = PRICE_SILVER;
        }
    }

    // ── Booking helpers ──────────────────────────────────────────────────────────
    /** Marks the seat as booked. Returns false if already booked. */
    public boolean book() {
        if (isBooked) return false;
        isBooked = true;
        return true;
    }

    /** Frees the seat on cancellation. */
    public void cancel() {
        isBooked = false;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public String  getSeatId()   { return seatId;    }
    public char    getRow()      { return row;        }
    public int     getNumber()   { return number;     }
    public boolean isBooked()    { return isBooked;   }
    public String  getCategory() { return category;   }
    public int     getPrice()    { return price;      }

    /**
     * Returns a compact display token: "B7(O)" or "B7(X)".
     */
    public String displayToken() {
        return seatId + (isBooked ? "(X)" : "(O)");
    }
}
