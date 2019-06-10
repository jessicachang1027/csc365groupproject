import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RoomDaoImpl implements Dao<Room> {
    private Connection conn;

    public RoomDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Room getById(String code) {
        Room room = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Rooms WHERE code=?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            Set<Room> rooms = unpackResultSet(resultSet);
            room = (Room) rooms.toArray()[0];
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return room;
    }

    public Set<Room> getAll() {
        Set<Room> rooms = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Rooms");
            resultSet = preparedStatement.executeQuery();
            rooms = unpackResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public Set<Room> getAllWhere(String query) {
        Set<Room> rooms = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Rooms WHERE " + query);
            resultSet = preparedStatement.executeQuery();
            rooms = unpackResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public Boolean insert(Room obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Rooms (roomId, roomName, type, popularity, " +
                            "popularity, bedType, num, Adults, Kids) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, obj.getRoomId());
            preparedStatement.setString(2, obj.getRoomName());
            preparedStatement.setString(3, obj.getBedType());
            preparedStatement.setInt(4, obj.getBeds());
            preparedStatement.setInt(5, obj.getMaxOcc());
            preparedStatement.setString(6, obj.getDecor());
            preparedStatement.setDouble(7, obj.getPrice());

            successful = preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    public Boolean update(Room obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Room SET room=?, checkin=?, checkout=?, Rate=?, lastname=?, " +
                            "firstname=?, Adults=?, Kids=? WHERE code=?");
            preparedStatement.setString(1, obj.getRoomId());
            preparedStatement.setString(2, obj.getRoomName());
            preparedStatement.setString(3, obj.getBedType());
            preparedStatement.setInt(4, obj.getBeds());
            preparedStatement.setInt(5, obj.getMaxOcc());
            preparedStatement.setString(6, obj.getDecor());
            preparedStatement.setDouble(7, obj.getPrice());

            successful = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    public Boolean delete(Room obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "DELETE FROM Room WHERE roomId=?");
            preparedStatement.setString(1, obj.getRoomId());
            successful = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return successful;
    }

    private Set<Room> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Room> rooms = new HashSet<Room>();

        while(rs.next()) {
            Room room = new Room(
                    rs.getString("RoomId"),
                    rs.getString("roomName"),
                    rs.getString("bedType"),
                    rs.getInt("beds"),
                    rs.getInt("maxOccupancy"),
                    rs.getString("decor"),
                    rs.getDouble(   "basePrice")
            );

            rooms.add(room);
        }
        return rooms;
    }

    public static void main(String[] args) {
        try {
            //Connection conn = ConnectionFactory.getConnection();
            //RoomDaoImpl roomDao = new RoomDaoImpl(conn);
            DaoManager dm = DaoManager.getInstance();
            Dao<Room> roomDao = dm.getRoomDao();
            //Room room = roomDao.getById(1);
            //System.out.println(room);
            //Set<Room> rooms = roomDao.getAll();
            //System.out.println(rooms);

            //Date.valueOf("2010-01-31")


//            rooms = roomDao.getAll();
//            System.out.println(rooms);
//            room.setPhone("8052223456");
//            roomDao.update(room);
//            rooms = roomDao.getAll();
//            System.out.println(rooms);
//            Iterator<Room> roomIterator = rooms.iterator();
//            while (roomIterator.hasNext()) {
//                Room delRoom = roomIterator.next();
//                if (delRoom.getName().equals("Tom")) {
//                    roomDao.delete(delRoom);
//                }
//            }
//            rooms = roomDao.getAll();
//            System.out.println(rooms);
            dm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}