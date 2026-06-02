/**
 * User.java
 * Abstract base class for all users of the system.
 * Demonstrates Abstraction and Inheritance.
 *
 * Concrete subclasses: Customer, Admin
 */
public abstract class User {

    // ── Private Fields ──────────────────────────────────────────────────────────
    private String userId;
    private String name;

    // ── Constructor ──────────────────────────────────────────────────────────────
    public User(String userId, String name) {
        this.userId = userId;
        this.name   = name;
    }

    // ── Getters / Setters ────────────────────────────────────────────────────────
    public String getUserId() { return userId; }
    public String getName()   { return name;   }
    public void   setName(String name) { this.name = name; }

    /**
     * Abstract method – each user type shows its own menu.
     * Demonstrates Polymorphism (method overriding).
     */
    public abstract void showMenu();

    @Override
    public String toString() {
        return "User[" + userId + ", " + name + "]";
    }
}


// ── Customer subclass ────────────────────────────────────────────────────────────
/**
 * Customer.java (inner / same-file class for simplicity)
 * Represents a regular customer who browses movies and books tickets.
 */
class Customer extends User {

    public Customer(String userId, String name) {
        super(userId, name);
    }

    /**
     * Overrides showMenu() to display customer-specific options.
     * Polymorphism in action.
     */
    @Override
    public void showMenu() {
        System.out.println("\n  === Customer Menu ===");
        System.out.println("  Hello, " + getName() + "!");
        System.out.println("  1. View Movies");
        System.out.println("  2. Book Ticket");
        System.out.println("  3. Cancel Ticket");
        System.out.println("  4. View My Bookings");
        System.out.println("  5. Exit");
    }
}


// ── Admin subclass ───────────────────────────────────────────────────────────────
/**
 * Admin represents a theatre administrator (extensible for future use).
 */
class Admin extends User {

    public Admin(String userId, String name) {
        super(userId, name);
    }

    @Override
    public void showMenu() {
        System.out.println("\n  === Admin Menu ===");
        System.out.println("  Hello, Admin " + getName() + "!");
        System.out.println("  1. View All Bookings");
        System.out.println("  2. View Seat Layout");
        System.out.println("  3. Exit");
    }
}
