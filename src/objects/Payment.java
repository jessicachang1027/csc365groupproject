package objects;

public class Payment {

    private String reservationId;
    private String customerId;
    private String cardNum;

    public Payment(String reservationId, String customerId, String cardNum) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.cardNum = cardNum;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
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
}
