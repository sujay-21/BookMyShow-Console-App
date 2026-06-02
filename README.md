# BookMyShow Console Clone
A console-based movie ticket booking system in Java, demonstrating core OOP principles.

## Project Structure
```
BookMyShow/
├── Main.java              ← Entry point
├── Movie.java             ← Movie entity (Encapsulation)
├── Seat.java              ← Seat entity with category logic
├── Booking.java           ← Booking record + ticket printer
├── User.java              ← Abstract User, Customer & Admin (Inheritance + Polymorphism)
├── BookingManager.java    ← Business logic (ArrayList + HashMap)
├── BookMyShowSystem.java  ← Console UI / controller
└── README.md
```

## How to Compile & Run
```bash
javac *.java
java Main
```
Requires Java 14+ (uses switch expressions).

## Features
| Feature | Details |
|---|---|
| Movies | 15 preloaded (English, Hindi, Tamil, Telugu, Kannada) |
| Seats | 100 seats, rows A–J, 10 per row |
| Categories | Diamond (A–B ₹500), Gold (C–F ₹300), Silver (G–J ₹150) |
| Booking | Movie select → Seat select → Summary → Payment → Ticket |
| Payment | Animated progress bar with Thread.sleep() |
| Booking ID | Auto-incrementing BMS1001, BMS1002… |
| Cancellation | Cancel by Booking ID; seat freed immediately |
| History | Full table with status (CONFIRMED / CANCELLED) |

## OOP Concepts Demonstrated
- **Encapsulation** — All fields are `private`; accessed via getters/setters
- **Inheritance** — `Customer` and `Admin` extend abstract `User`
- **Polymorphism** — `showMenu()` overridden in each subclass
- **Abstraction** — `User` is abstract; concrete classes implement behaviour
- **Collections** — `ArrayList<Movie>`, `ArrayList<Booking>`, `HashMap<String,Seat>`, `HashMap<String,Booking>`
