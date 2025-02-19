import java.util.ArrayList;
import java.util.List;

class TicketBookingSystem {
    private final List<Boolean> seats; 
    private final int totalSeats;

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        this.seats = new ArrayList<>(totalSeats);
        for (int i = 0; i < totalSeats; i++) {
            seats.add(false); 
        }
    }

    public synchronized boolean bookSeat(int seatNumber) {
        if (seatNumber < 0 || seatNumber >= totalSeats) {
            System.out.println("Invalid seat number: " + seatNumber);
            return false;
        }
        if (!seats.get(seatNumber)) {
            seats.set(seatNumber, true); 
            System.out.println("Seat " + seatNumber + " booked successfully.");
            return true;
        } else {
            System.out.println("Seat " + seatNumber + " is already booked.");
            return false;
        }
    }

    public synchronized void showAvailableSeats() {
        System.out.print("Available seats: ");
        for (int i = 0; i < totalSeats; i++) {
            if (!seats.get(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;

    public BookingThread(TicketBookingSystem bookingSystem, int seatNumber) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
    }

    public int getSeatNumber() {
        return seatNumber; 
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber);
    }
}

class VIPBookingThread extends BookingThread {
    public VIPBookingThread(TicketBookingSystem bookingSystem, int seatNumber) {
        super(bookingSystem, seatNumber);
    }

    @Override
    public void run() {
      
        System.out.println("VIP booking in process for seat " + getSeatNumber());
        super.run();
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10); 

      
        BookingThread[] bookingThreads = new BookingThread[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                // Even indexed threads are VIP bookings
                bookingThreads[i] = new VIPBookingThread(bookingSystem, i);
                bookingThreads[i].setPriority(Thread.MAX_PRIORITY); 
            } else {
                bookingThreads[i] = new BookingThread(bookingSystem, i);
                bookingThreads[i].setPriority(Thread.NORM_PRIORITY);
        }

    
        for (BookingThread thread : bookingThreads) {
            thread.start();
        }

    
        for (BookingThread thread : bookingThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

     
        bookingSystem.showAvailableSeats();
    }
}
