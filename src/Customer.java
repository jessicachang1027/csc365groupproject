public class Customer {

    private String customerId;
    private String name;
    private String username;
    private String password;
    private String ssn;
    private String address;
    private String phone;

    public Customer(String id, String ssn, String name, String address, String phone){
        this.customerId = id;
        this.phone = phone;
        this.name = name;
        this.ssn = ssn;
        this.address = address;
    }

    public Customer() {

    }

    public Customer(String customerId, String name, String username, String password, String ssn, String address, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.ssn = ssn;
        this.address = address;
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
