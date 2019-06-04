import java.sql.Date;

public class Reservation {

    private String reservationID;
    private Date checkIn;
    private Date checkOut;
    private String roomID;
    private double rate;
    private String customerId;
    private int occupants;
    private String paymentId;

    public Reservation(String reservationID, Date checkIn,
                       Date checkOut, String roomID, double rate,
                       String customerId, int occupants, String paymentId) {
        this.reservationID = reservationID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomID = roomID;
        this.rate = rate;
        this.customerId = customerId;
        this.occupants = occupants;
        this.paymentId = paymentId;
    }

    private void displayReservation() {
        System.out.println("Reservation Number: " + reservationID);
        System.out.println("Check in date: " + checkIn);
        System.out.println("Check out date: " + checkOut);
        System.out.println("Rate: " + rate);
        System.out.println("Number of Occupants: " + occupants);
        System.out.println();
    }
}
