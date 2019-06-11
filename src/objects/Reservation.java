package objects;

public class Reservation {

    private String reservationID;
    private String checkIn;
    private String checkOut;
    private String roomID;
    private double rate;
    private String firstname;
    private String lastname;
    private int adults;
    private int kids;
    //private String paymentId;

    public Reservation(String reservationID, String roomID, String checkIn,
                       String checkOut, double rate,
                       String firstname, String lastname, int adults, int kids) {
        this.reservationID = reservationID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomID = roomID;
        this.rate = rate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adults = adults;
        this.kids = kids;
    }

    public void displayReservation() {
        System.out.println("Reservation Number: " + reservationID);
        System.out.println("Check in date: " + checkIn);
        System.out.println("Check out date: " + checkOut);
        System.out.println("Rate: " + rate);
        System.out.println("Number of Adults: " + adults);
        System.out.println("Number of Kids: " + kids);
        System.out.println();
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getKids() {
        return kids;
    }

    public void setKids(int kids) {
        this.kids = kids;
    }

    /*
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }*/
}
