package objects;

public class Payment {

    private int reservationId;
    private String customerId;
    private String cardNum;

    public Payment(int reservationId, String customerId, String cardNum) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.cardNum = cardNum;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void printFields() {

        System.out.println("Reservation Id: " + reservationId);
        System.out.println("Customer Id: " + customerId);
        System.out.println("Card Number: " + cardNum);

    }
}
