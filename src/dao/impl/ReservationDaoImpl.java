package dao.impl;

import dao.Dao;
import dao.DaoManager;
import objects.Reservation;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.lang.*;

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

    public Reservation getByCode(int code) {
        Reservation reservation = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE code=?");
            preparedStatement.setInt(1, code);
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

    public Set<Reservation> getAllWhere(String firstname, String lastname) {
        Set<Reservation> reservations = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT * FROM Reservations WHERE firstname = ? AND lastname = ?" );
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
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

    public int getNextAvailableLength(String date, String room){
        int length = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("select duration from " +
                    "(select X.checkout,  DATEDIFF(STR_TO_DATE(R.checkin, '%d-%b-%y'), STR_TO_DATE(X.checkout, '%d-%b-%y')) as duration FROM" +
                    "(SELECT *, DATEDIFF(STR_TO_DATE(checkin, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as length from Reservations  " +
                    "WHERE STR_TO_DATE(?, '%d-%b-%y')  BETWEEN STR_TO_DATE(checkin, '%d-%b-%y') AND STR_TO_DATE(checkout, '%d-%b-%y') " +
                    "AND room = ?) AS X  " +
                    "JOIN Reservations as R " +
                    "ON R.room = X.room AND str_to_date(R.checkin, '%d-%b-%y') > str_to_date(X.checkout, '%d-%b-%y')) as X " +
                    "WHERE X.duration <= ALL (select DATEDIFF(STR_TO_DATE(R.checkin, '%d-%b-%y'), STR_TO_DATE(X.checkout, '%d-%b-%y')) as duration FROM " +
                    "(SELECT *, DATEDIFF(STR_TO_DATE(checkin, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as length from Reservations  " +
                    "WHERE STR_TO_DATE(?, '%d-%b-%y')  BETWEEN STR_TO_DATE(checkin, '%d-%b-%y') AND STR_TO_DATE(checkout, '%d-%b-%y') " +
                    "AND room = ?) AS X  " +
                    "JOIN Reservations as R " +
                    "ON R.room = X.room AND str_to_date(R.checkin, '%d-%b-%y') > str_to_date(X.checkout, '%d-%b-%y'))");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, room);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, room);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            length = resultSet.getInt("duration");

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
        return length;
    }

    public String getNextAvailableDate(String date, String room){
        String nextDate = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT X.checkout FROM " +
                    "(SELECT *, DATEDIFF(STR_TO_DATE(checkin, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as next from Reservations " +
                    "WHERE STR_TO_DATE(checkin, '%d-%b-%y') >= STR_TO_DATE(?, '%d-%b-%y') " +
                    "AND room = ?) AS X " +
                    "WHERE next <= ALL (SELECT DATEDIFF(STR_TO_DATE(checkout, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as next from Reservations " +
                    "WHERE STR_TO_DATE(checkin, '%d-%b-%y') >= STR_TO_DATE(?, '%d-%b-%y') " +
                    "AND room = ?)");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, room);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, room);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            nextDate = resultSet.getString("checkout");
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
        return nextDate;
    }

    public int getAvailableLength(String date, String room){
        int length = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement("SELECT X.next FROM " +
                    "(SELECT *, DATEDIFF(STR_TO_DATE(checkin, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as next from Reservations " +
                    "WHERE STR_TO_DATE(checkin, '%d-%b-%y') >= STR_TO_DATE(?, '%d-%b-%y') " +
                    "AND room = ?) AS X " +
                    "WHERE next <= ALL (SELECT DATEDIFF(STR_TO_DATE(checkout, '%d-%b-%y'), STR_TO_DATE(?, '%d-%b-%y')) as next from Reservations " +
                    "WHERE STR_TO_DATE(checkin, '%d-%b-%y') >= STR_TO_DATE(?, '%d-%b-%y') " +
                    "AND room = ?)");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, room);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, room);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            length = resultSet.getInt("next");
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
        return length;
    }

    public int getCode(Reservation obj)
    {
        Integer code = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "SELECT code FROM Reservations WHERE room = ? AND checkin = ? AND checkout = ? " +
                            "AND Rate = ? AND lastname = ? AND firstname=? AND Adults=? AND Kids=?");

            preparedStatement.setString(1, obj.getRoomID());
            preparedStatement.setString(2, obj.getCheckIn());
            preparedStatement.setString(3, obj.getCheckOut());
            preparedStatement.setDouble(4, obj.getRate());
            preparedStatement.setString(5, obj.getLastname());
            preparedStatement.setString(6, obj.getFirstname());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            code = resultSet.getInt("code");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return code;
    }

    public Boolean insert(Reservation obj)
    {
        boolean successful = true;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.conn.prepareStatement(
                    "INSERT INTO Reservations (room, checkin, checkout, " +
                            "Rate, lastname, firstname, Adults, Kids) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, obj.getRoomID());
            preparedStatement.setString(2, obj.getCheckIn());
            preparedStatement.setString(3, obj.getCheckOut());
            preparedStatement.setDouble(4, obj.getRate());
            preparedStatement.setString(5, obj.getLastname());
            preparedStatement.setString(6, obj.getFirstname());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());

            preparedStatement.execute();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            successful = false;
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
        Boolean successful = true;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(
                    "UPDATE Reservations SET room=?, checkin=?, checkout=?, Rate=?, lastname=?, " +
                            "firstname=?, Adults=?, Kids=? WHERE code=?");
            preparedStatement.setString(1, obj.getRoomID());
            preparedStatement.setString(2, obj.getCheckIn());
            preparedStatement.setString(3, obj.getCheckOut());
            preparedStatement.setDouble(4, obj.getRate());
            preparedStatement.setString(5, obj.getLastname());
            preparedStatement.setString(6, obj.getFirstname());
            preparedStatement.setInt(7, obj.getAdults());
            preparedStatement.setInt(8, obj.getKids());
            preparedStatement.setInt(9, obj.getReservationID());

            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            successful = false;
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
                    "DELETE FROM Reservations WHERE code=?");
            preparedStatement.setInt(1, obj.getReservationID());
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
                    rs.getInt("code"),
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

    public void printRevenue(int year)
    {
        String sqlStatement = " (SELECT RoomName, JanRev, FebRev, MarRev, AprRev, MayRev, JunRev, " +
                "\t\tJulRev, AugRev, SepRev, OctRev, NovRev, DecRev," +
                "        RoomName + JanRev + FebRev + MarRev + AprRev + MayRev + JunRev + " +
                "\t\tJulRev + AugRev + SepRev + OctRev + NovRev + DecRev as TotalRev" +
                " FROM" +
                "\t(SELECT RoomName, getMonthRev(1, RoomId, inYear) as JanRev, getMonthRev(2, RoomId, inYear) as FebRev," +
                "\t\t\tgetMonthRev(3, RoomId, inYear) as MarRev, getMonthRev(4, RoomId, inYear) as AprRev," +
                "\t\t\tgetMonthRev(5, RoomId, inYear) as MayRev, getMonthRev(6, RoomId, inYear) as JunRev," +
                "\t\t\tgetMonthRev(7, RoomId, inYear) as JulRev, getMonthRev(8, RoomId, inYear) as AugRev," +
                "\t\t\tgetMonthRev(9, RoomId, inYear) as SepRev, getMonthRev(10, RoomId, inYear) as OctRev," +
                "\t\t\tgetMonthRev(11, RoomId, inYear) as NovRev, getMonthRev(12, RoomId, inYear) as DecRev" +
                "\tFROM Rooms" +
                "    UNION SELECT 'Total:', getTotalRev(1, inYear) as JanRev, getTotalRev(2, inYear) as FebRev," +
                "\t\t\tgetTotalRev(3, inYear) as MarRev, getTotalRev(4, inYear) as AprRev," +
                "\t\t\tgetTotalRev(5, inYear) as MayRev, getTotalRev(6, inYear) as JunRev," +
                "\t\t\tgetTotalRev(7, inYear) as JulRev, getTotalRev(8, inYear) as AugRev," +
                "\t\t\tgetTotalRev(9, inYear) as SepRev, getTotalRev(10, inYear) as OctRev," +
                "\t\t\tgetTotalRev(11, inYear) as NovRev, getTotalRev(12, inYear) as DecRev) as revs)";
        sqlStatement = sqlStatement.replace("inYear", Integer.toString(year));
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.conn.prepareStatement(sqlStatement);
            resultSet = preparedStatement.executeQuery();
            System.out.println(String.format("%-25s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s",
                    "Room", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total"));
            while(resultSet.next())
            {
                System.out.print(String.format("%-25s ", resultSet.getNString(1)));
                for(int i = 2; i <= 14; i++)
                {
                    System.out.print(String.format("%-10s ", resultSet.getInt(i)));
                }
                System.out.println();
            }
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
    }

}