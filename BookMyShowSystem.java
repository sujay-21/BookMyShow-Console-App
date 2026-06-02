import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * BookMyShowSystem.java
 * Controller class – reads user input and delegates to BookingManager.
 * Keeps UI logic separate from business logic (clean architecture).
 */
public class BookMyShowSystem {

    // ── Dependencies ─────────────────────────────────────────────────────────────
    private final BookingManager manager;
    private final Scanner        sc;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public BookMyShowSystem() {
        manager = new BookingManager();
        sc      = new Scanner(System.in);
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Application entry
    // ────────────────────────────────────────────────────────────────────────────

    /** Starts the main application loop. */
    public void run() {
        printBanner();
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = readInt("  Enter choice: ");
            switch (choice) {
                case 1  -> viewMovies();
                case 2  -> bookTicket();
                case 3  -> cancelTicket();
                case 4  -> viewBookingHistory();
                case 5  -> { running = false; printGoodbye(); }
                default -> System.out.println("\n  ⚠  Invalid choice. Please try again.\n");
            }
        }
        sc.close();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Menu display helpers
    // ────────────────────────────────────────────────────────────────────────────

    private void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║         WELCOME TO BOOK MY SHOW       ║");
        System.out.println("  ║    Your Ultimate Ticket Booking App   ║");
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.println();
    }

    private void showMainMenu() {
        System.out.println("  ┌─────────────────────────────┐");
        System.out.println("  │         MAIN MENU           │");
        System.out.println("  ├─────────────────────────────┤");
        System.out.println("  │  1. View Movies             │");
        System.out.println("  │  2. Book Ticket             │");
        System.out.println("  │  3. Cancel Ticket           │");
        System.out.println("  │  4. View Booking History    │");
        System.out.println("  │  5. Exit                    │");
        System.out.println("  └─────────────────────────────┘");
    }

    private void printGoodbye() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║  Thank you for using BookMyShow!      ║");
        System.out.println("  ║         See you at the movies!        ║");
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.println();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Feature: View Movies
    // ────────────────────────────────────────────────────────────────────────────

    private void viewMovies() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║               NOW SHOWING – 15 MOVIES                ║");
        System.out.println("  ╠═════╦══════════════════════════╦════════════╦════════╣");
        System.out.println("  ║ No  ║ Title                    ║ Language   ║ Genre  ║");
        System.out.println("  ╠═════╬══════════════════════════╬════════════╬════════╣");

        for (Movie m : manager.getMovieList()) {
            System.out.println(m);
        }
        System.out.println("  ╚═════╩══════════════════════════╩════════════╩════════╝");
        System.out.println();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Feature: Book Ticket
    // ────────────────────────────────────────────────────────────────────────────

    private void bookTicket() {
        System.out.println("\n  ──────── BOOK YOUR TICKET ────────\n");

        // Step 1 – Select movie
        viewMovies();
        int movieChoice = readInt("  Select Movie (1-15): ");
        Movie movie = manager.getMovieByIndex(movieChoice);
        if (movie == null) {
            System.out.println("  ⚠  Invalid movie selection.\n");
            return;
        }
        System.out.println("  ✔  Movie selected: " + movie.getTitle());

        // Step 2 – Display seat layout
        System.out.println();
        System.out.println("  Available Seats : " + manager.getAvailableSeatCount()+ "/100");
        System.out.println();

        displaySeatLayout();

        // Step 3 – Select seat
        System.out.print("  Enter Seat Number (e.g. B7): ");
        String seatId = sc.nextLine().trim().toUpperCase();

        Seat seat = manager.getSeat(seatId);
        if (seat == null) {
            System.out.println("  ⚠  Invalid seat number. Please enter a valid seat (e.g. A1, J10).\n");
            return;
        }
        if (seat.isBooked()) {
            System.out.println("  ✘  Sorry, seat " + seatId + " is already booked. Please choose another seat.\n");
            return;
        }

        // Step 4 – Booking summary
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────┐");
        System.out.println("  │           BOOKING SUMMARY            │");
        System.out.println("  ├──────────────────────────────────────┤");
        System.out.printf ("  │  Movie Selected : %-20s│%n", movie.getTitle());
        System.out.printf ("  │  Seat Selected  : %-20s│%n", seatId);
        System.out.printf ("  │  Category       : %-20s│%n", seat.getCategory());
        System.out.printf ("  │  Price          : Rs.%-19d│%n", seat.getPrice());
        System.out.println("  └──────────────────────────────────────┘");
        System.out.println();

        // Step 5 – Payment confirmation
        System.out.print("  Proceed to Payment? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("\n  Booking cancelled by user.\n");
            return;
        }

        // Step 6 – Simulate payment
        simulatePayment();

        // Step 7 – Create booking and print ticket
        Booking booking = manager.createBooking(movie, seatId);
        if (booking == null) {
            // Rare race-condition guard (shouldn't happen in single-threaded console)
            System.out.println("  ✘  Booking failed. Seat may have just been taken.\n");
            return;
        }
        booking.printTicket();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Feature: Cancel Ticket
    // ────────────────────────────────────────────────────────────────────────────

    private void cancelTicket() {
        System.out.println("\n  ──────── CANCEL BOOKING ────────\n");
        System.out.print("  Enter Booking ID (e.g. BMS1001): ");
        String bookingId = sc.nextLine().trim().toUpperCase();

        boolean success = manager.cancelBooking(bookingId);
        if (success) {
            System.out.println("\n  ✔  Booking " + bookingId + " cancelled successfully.");
            System.out.println("     The seat is now available for booking again.\n");
        } else {
            System.out.println("\n  ⚠  Booking ID not found or already cancelled.\n");
        }
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Feature: View Booking History
    // ────────────────────────────────────────────────────────────────────────────

    private void viewBookingHistory() {
        List<Booking> history = manager.getBookingHistory();
        System.out.println();

        if (history.isEmpty()) {
            System.out.println("  ℹ  No bookings found yet.\n");
            return;
        }

        System.out.println("  ╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                        BOOKING HISTORY                                ║");
        System.out.println("  ╠════════════╦═══════════════════════════╦════════╦══════════╦══════════╣");
        System.out.println("  ║ Booking ID ║ Movie                     ║ Seat   ║ Category ║ Amount   ║");
        System.out.println("  ╠════════════╬═══════════════════════════╬════════╬══════════╬══════════╣");

        for (Booking b : history) {
            System.out.println(b.toTableRow());
        }
        System.out.println("  ╚════════════╩═══════════════════════════╩════════╩══════════╩══════════╝");
        System.out.printf ("  Total bookings: %d%n%n", history.size());
        System.out.println();
        System.out.println( "Total Revenue : Rs."+ manager.getTotalRevenue());
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Seat Layout display
    // ────────────────────────────────────────────────────────────────────────────

    /** Prints the full 100-seat theatre layout with category labels. */
    private void displaySeatLayout() {
        Map<String, Seat> seatMap = manager.getSeatMap();

        System.out.println("  ╔══════════════════════════════════════════════════════════╗");
        System.out.println("  ║                        SCREEN                            ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════╣");
        System.out.println("  ║           O = Available   X = Booked                     ║");
        System.out.println("  ╠══════════════════════════════════════════════════════════╣");

        char[] rows = {'A','B','C','D','E','F','G','H','I','J'};
        char currentCategory = ' ';

        for (char row : rows) {
            // Print category separator
            char catKey = (row <= 'B') ? 'D' : (row <= 'F') ? 'G' : 'S';
            if (catKey != currentCategory) {
                currentCategory = catKey;
                String label = "";
                String price = "";
                if (catKey == 'D') { label = "Diamond Class"; price = "Rs.500"; }
                else if (catKey == 'G') { label = "Gold Class";    price = "Rs.300"; }
                else              { label = "Silver Class";  price = "Rs.150"; }
                System.out.println("  ╠══════════════════════════════════════════════════════════╣");
                System.out.printf ("  ║  %-20s  %s%-32s║%n", label, price, "");
                System.out.println("  ╠══════════════════════════════════════════════════════════╣");
            }

            // Print seats for this row
            System.out.print("  ║  ");
            for (int num = 1; num <= 10; num++) {
                String token = seatMap.get(String.valueOf(row) + num).displayToken();
                System.out.printf("%-7s", token);
            }
            System.out.println(" ║");
        }
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Payment simulation
    // ────────────────────────────────────────────────────────────────────────────

    /**
     * Simulates payment processing with a progress display.
     * Uses Thread.sleep() to create realistic delays.
     */
    private void simulatePayment() {
        System.out.println();
        System.out.println("  Processing Payment...");
        System.out.println();

        for (int i = 10; i <= 100; i += 10) {
            // Build a visual progress bar
            int filled = i / 10;
            StringBuilder bar = new StringBuilder("  [");
            for (int j = 0; j < 10; j++) {
                bar.append(j < filled ? "█" : "░");
            }
            bar.append("] ").append(i).append("%");
            System.out.println(bar);

            try {
                Thread.sleep(250);   // 250 ms per step → ~2.5 s total
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println();
        System.out.println("   Payment Successful!");
        System.out.println();
    }

    // ────────────────────────────────────────────────────────────────────────────
    //  Input helpers
    // ────────────────────────────────────────────────────────────────────────────

    /**
     * Prompts for an integer, handles non-numeric input gracefully.
     * Returns -1 if parsing fails.
     */
    private int readInt(String prompt) {
        System.out.print(prompt);
        try {
            String line = sc.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
