public class Room {
    private String roomId;
    private String roomName;
    private String type;
    private double popularity;
    private String bedType;
    private int numBeds;
    private int maxOcc;
    private String decor;
    private double price;

    public Room(String roomId, String roomName,
                String type, double popularity, String bedType,
                int numBeds, int maxOcc, String decor, double price) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.type = type;
        this.popularity = popularity;
        this.bedType = bedType;
        this.numBeds = numBeds;
        this.maxOcc = maxOcc;
        this.decor = decor;
        this.price = price;
    }
}
