import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ReservationDaoImpl implements Dao<Reservation> {
    private Connection conn;

    public ReservationDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Reservation getById(String code) {
        Reservation reservation = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE code=?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            Set<Reservation> reservations = unpackResultSet(resultSet);
            reservation = (Reservation) reservations.toArray()[0];
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
        return reservation;
    }

    public Set<Reservation> getAll() {
        Set<Reservation> reservations = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations");
            resultSet = preparedStatement.executeQuery();
            reservations = unpackResultSet(resultSet);
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
        return reservations;
    }

    public Set<Reservation> getAllWhere(String query) {
        Set<Reservation> reservations = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE " + query);
            resultSet = preparedStatement.executeQuery();
            reservations = unpackResultSet(resultSet);
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
        return reservations;
    }

    public Boolean insert(Reservation obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Reservations (code, room, checkin, checkout, " +
                            "Rate, lastname, firstname, Adults, Kids) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, obj.getReservationID());
            preparedStatement.setString(2, obj.getRoomID());
            preparedStatement.setString(3, obj.getCheckIn());
            preparedStatement.setString(4, obj.getCheckOut());
            preparedStatement.setDouble(5, obj.getRate());
            preparedStatement.setString(6, obj.getLastname());
            preparedStatement.setString(7, obj.getFirstname());
            preparedStatement.setInt(8, obj.getAdults());
            preparedStatement.setInt(9, obj.getKids());

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

    public Boolean update(Reservation obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Reservation SET room=?, checkin=?, checkout=?, Rate=?, lastname=?, " +
                            "firstname=?, Adults=?, Kids=? WHERE code=?");
            preparedStatement.setString(1, obj.getRoomID());
            preparedStatement.setString(2, obj.getCheckIn());
            preparedStatement.setString(3, obj.getCheckOut());
            preparedStatement.setDouble(4, obj.getRate());
            preparedStatement.setString(5, obj.getLastname());
            preparedStatement.setString(6, obj.getFirstname());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());
            preparedStatement.setString(9, obj.getReservationID());

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

    public Boolean delete(Reservation obj) {
        Boolean successful = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "DELETE FROM Reservation WHERE code=?");
            preparedStatement.setString(1, obj.getReservationID());
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

    private Set<Reservation> unpackResultSet(ResultSet rs) throws SQLException {
        Set<Reservation> reservations = new HashSet<Reservation>();

        while(rs.next()) {
            Reservation reservation = new Reservation(
                    rs.getString("code"),
                    rs.getString("room"),
                    rs.getString("checkin"),
                    rs.getString("checkout"),
                    rs.getInt("Rate"),
                    rs.getString("lastname"),
                    rs.getString("firstname"),
                    rs.getInt("Adults"),
                    rs.getInt("Kids")
            );


                    reservations.add(reservation);
        }
        return reservations;
    }

    public static void main(String[] args) {
        try {
            //Connection conn = ConnectionFactory.getConnection();
            //ReservationDaoImpl reservationDao = new ReservationDaoImpl(conn);
            DaoManager dm = DaoManager.getInstance();
            Dao<Reservation> reservationDao = dm.getReservationDao();
            //Reservation reservation = reservationDao.getById(1);
            //System.out.println(reservation);
            //Set<Reservation> reservations = reservationDao.getAll();
            //System.out.println(reservations);

            //Date.valueOf("2010-01-31")

            Reservation newReservation
                    = new Reservation("3","RND", "23-SEP-10",
                    "26-SEP-10", 100, "Alex", "Arriola", 1, 1);
            reservationDao.insert(newReservation);

            newReservation
                    = new Reservation("69","HBB", "23-SEP-11",
                    "26-SEP-11", 150, "Alex", "Arriola", 1, 1);
            reservationDao.insert(newReservation);

            newReservation
                    = new Reservation("69","HBB", "26-SEP-11",
                    "29-SEP-11", 155, "Alex", "Arriola", 1, 1);
            reservationDao.insert(newReservation);

//            reservations = reservationDao.getAll();
//            System.out.println(reservations);
//            reservation.setPhone("8052223456");
//            reservationDao.update(reservation);
//            reservations = reservationDao.getAll();
//            System.out.println(reservations);
//            Iterator<Reservation> reservationIterator = reservations.iterator();
//            while (reservationIterator.hasNext()) {
//                Reservation delReservation = reservationIterator.next();
//                if (delReservation.getName().equals("Tom")) {
//                    reservationDao.delete(delReservation);
//                }
//            }
//            reservations = reservationDao.getAll();
//            System.out.println(reservations);
            dm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}