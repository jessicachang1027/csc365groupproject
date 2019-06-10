public class CreditCard {

    private String customerId;
    private String cardNum;
    private double creditLim;
    private double  balance;
    private boolean active;

    public CreditCard(String customerId, String cardNum, double creditLim, double balance, boolean active) {
        this.customerId = customerId;
        this.cardNum = cardNum;
        this.creditLim = creditLim;
        this.balance = balance;
        this.active = active;
    }

    public double getBalance() {
        return balance;
    }

    public double getCreditLim() {
        return creditLim;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public boolean isActive() {
        return active;
    }
}
