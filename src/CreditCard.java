public class CreditCard {

    private String customerId;
    private String cardNum;
    private double limit;
    private double  balance;
    private boolean active;

    public CreditCard(String customerId, String cardNum, double limit, double balance, boolean active) {
        this.customerId = customerId;
        this.cardNum = cardNum;
        this.limit = limit;
        this.balance = balance;
        this.active = active;
    }
}
