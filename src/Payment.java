public class Payment {

    private String paymentId;
    private String reservationId;
    private String customerId;
    private String cardNum;

    public Payment(String paymentId, String reservationId, String customerId, String cardNum) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.cardNum = cardNum;
    }
}
