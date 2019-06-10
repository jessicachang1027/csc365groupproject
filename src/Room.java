public class Room {
    private String roomId;
    private String roomName;
    private String bedType;
    private int beds;
    private int maxOcc;
    private String decor;
    private double price;

    public Room(String roomId, String roomName, String bedType,
                int beds, int maxOcc, String decor, double price) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.bedType = bedType;
        this.beds = beds;
        this.maxOcc = maxOcc;
        this.decor = decor;
        this.price = price;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getMaxOcc() {
        return maxOcc;
    }

    public void setMaxOcc(int maxOcc) {
        this.maxOcc = maxOcc;
    }

    public String getDecor() {
        return decor;
    }

    public void setDecor(String decor) {
        this.decor = decor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void displayRoom() {
        System.out.println("Room Name: " + roomName);
        System.out.println("Type of bed: " + bedType);
        System.out.println("Number of beds: " + beds);
        System.out.println("Max occupants allowed: " + maxOcc);
        System.out.println("Decor: " + decor);
        System.out.println("Base Price: " + price);
    }
}
