package objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {

    private int reservationID;
    private String checkIn;
    private String checkOut;
    private String roomID;
    private double rate;
    private String firstname;
    private String lastname;
    private int adults;
    private int kids;
    //private String paymentId;

    public Reservation(String roomID, String checkIn,
                       String checkOut, double rate,
                       String lastname, String firstname, int adults, int kids) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomID = roomID;
        this.rate = rate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adults = adults;
        this.kids = kids;
    }

    public Reservation(int reservationID, String roomID, String checkIn,
                       String checkOut, double rate,
                       String lastname, String firstname, int adults, int kids) {
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

    public double getPrice() {
        try {
            Date dateIn = new SimpleDateFormat("dd-MMM-yy").parse(checkIn);
            Date dateOut = new SimpleDateFormat("dd-MMM-yy").parse(checkOut);
            long diffInMillies = Math.abs(dateIn.getTime() - dateOut.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return rate * (diff);
        } catch (ParseException e) {
            System.out.println("Incorrect date format for reservation");
            return Integer.MAX_VALUE;
        }
    }

    public Boolean checkinBeforeCheckout() {
        try {
            return dateDiffMillis() < 0;
        } catch (ParseException e) {
            System.out.println("Incorrect date format for reservation");
            return false;
        }
    }

    private long dateDiffMillis() throws ParseException {
        Date dateIn = new SimpleDateFormat("dd-MMM-yy").parse(checkIn);
        Date dateOut = new SimpleDateFormat("dd-MMM-yy").parse(checkOut);
        return dateIn.getTime() - dateOut.getTime();

    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
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
