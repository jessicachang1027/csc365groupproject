import dao.Dao;
import dao.DaoManager;
import dao.impl.CustomerDaoImpl;
import dao.impl.PaymentDaoImpl;
import dao.impl.ReservationDaoImpl;
import dao.impl.RoomDaoImpl;
import objects.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static String user = "";
    private static Boolean success = false;

    private static void printHomeMenu() {
        System.out.println("Hotel Reservation System");
        System.out.println("Your options:");
        System.out.println("    Sign Up (s)");
        System.out.println("    Log In (l)");
        System.out.println("    Quit (q)");
        System.out.print("Enter option from list: ");
    }

    private static void printUserMenu() {
        System.out.println();
        System.out.println("Your options: ");
        System.out.println("    Search Room Availability (a)");
        System.out.println("    Change/Cancel Reservation (c)");
        System.out.println("    View Reservations (v)");
        System.out.println("    Quit (q)");
        System.out.print("Enter option from list: ");
    }

    private static void printManagerMenu()
    {
        System.out.println("Your options: ");
        System.out.println("    View Revenue Statistics (v)");
        System.out.println("    Quit (q)");
        System.out.print("Enter option from list: ");
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        String choice;
        String firstName;
        String lastName;
        String username;
        String password;
        Customer customer = null;
        try {
            DaoManager dm = DaoManager.getInstance();
            Dao<Customer> customerDao = dm.getCustomerDao();
            Dao<CreditCard> creditCardDao = dm.getCreditCardDao();
            while(!success) {
                printHomeMenu();
                choice = in.next();
                switch (choice) {
                    case "s":
                        System.out.println("Enter First Name: ");
                        firstName = in.next();
                        System.out.println("Enter Last Name: ");
                        lastName = in.next();
                        System.out.println("Enter username: ");
                        username = in.next();
                        System.out.println("Enter password: ");
                        password = in.next();
                        customer = new Customer(firstName, lastName, username, password);
                        success = customerDao.insert(customer);
                        if (!success) {
                            System.out.println("Username has already been taken. Please try again!");
                            break;
                        }
                        user = username;
                        System.out.println("Welcome " + firstName + "!");
                        dm.close();
                        break;
                    case "l":
                        System.out.println("Enter username: ");
                        username = in.next();
                        System.out.println("Enter password: ");
                        password = in.next();
                        customer = ((CustomerDaoImpl)customerDao).login(username, password);
                        if (customer == null) {
                            System.out.println("User not found/Incorrect password. Try again");
                            success = false;
                            break;
                        }
                        user = username;
                        success = true;
                        System.out.println("Welcome back " + customer.getFirstName() + "!");
                        break;
                    case "q":
                        System.out.println("Thank you, bye!");
                        System.exit(0);
                        break;
                }
            }

            Dao<Reservation> resDao = dm.getReservationDao();
            Dao<Room> roomDao = dm.getRoomDao();
            Dao<Payment> paymentDao = dm.getPaymentDao();
            while(true) {
                if(user.equals("manager"))
                {
                    printManagerMenu();
                    choice = in.next();
                    switch (choice) {
                        case "v":
                            System.out.println("Enter year #:");
                            while(true)
                            {
                                try {
                                    ((ReservationDaoImpl)resDao).printRevenue(Integer.parseInt(in.next()));
                                    break;
                                } catch (NumberFormatException e)
                                {
                                    System.out.println("Invalid year input. Try again");
                                }
                            }
                            break;
                        case "q":
                            System.out.println("Thank you, bye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Try again");
                            break;
                    }
                }
                else
                {
                    printUserMenu();
                    choice = in.next();
                    switch (choice) {
                        case "a":
                            showAvailability(in, resDao, roomDao, paymentDao, customer);
                            break;
                        case "c":
                            changeReservation(in, resDao, paymentDao);
                            break;
                        case "v":
                            showReservations(in, resDao, customer);
                            break;
                        case "q":
                            System.out.println("Thank you, bye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Try again");
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String getValidDate(Scanner in) {
        Boolean validDate = false;
        String sDate = in.next();
        java.util.Date javaDate;
        while(!validDate) {
            try {
                javaDate = new SimpleDateFormat("dd-MMM-yy").parse(sDate);
                validDate = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format try again: ");
                sDate = in.next();
            }
        }
        return sDate;
    }
    
    private static void makeReservation(Scanner in, Customer customer, Dao<Room> roomDao, Dao<Reservation> resDao, Dao<Payment> paymentDao) {
        System.out.println("Do you want to make a reservation (r) or start a new search (d)?");
        System.out.print("Enter choice: ");
        Payment payment;
        String choice = in.next();
        if (choice.equals("r")) {
            System.out.println("Enter room ID to reserve: ");
            String roomId = in.next();
            System.out.println("Enter date to check in (dd-MMM-yy): ");
            String checkIn = getValidDate(in);
            System.out.println("Enter date to check out (dd-MMM-yy): ");
            String checkOut = getValidDate(in);
            double rate = roomDao.getById(roomId).getPrice();
            System.out.println("Enter number of adults: ");
            int adults = in.nextInt();
            System.out.println("Enter number of kids: ");
            int kids = in.nextInt();
            Reservation newRes = new Reservation(roomId, checkIn, checkOut, rate,
                    customer.getFirstName(), customer.getLastName(), adults, kids);
            if(!newRes.checkinBeforeCheckout()) {
                System.out.println("Check out date must be before check in date");
            } else {
                payment = payForReservation(in, customer, newRes, resDao, paymentDao);
                boolean success = resDao.insert(newRes);
                if (success) {
                    int code = ((ReservationDaoImpl)resDao).getCode(newRes);
                    newRes.setReservationID(code);
                    ((PaymentDaoImpl)paymentDao).updateReservationCode(newRes.getReservationID());
                    System.out.println("Your new reservation information: ");
                    newRes.displayReservation();
                } else {
                    //TODO: Fix payment failing message
                    System.out.println("Your payment failed. Try again. ");
                    paymentDao.delete(payment);
                    makeReservation(in, customer, roomDao, resDao, paymentDao);
                }
            }
        }
    }

    private static Payment payForReservation(Scanner in, Customer customer, Reservation res, Dao<Reservation> resDao, Dao<Payment> paymentDao) {
        double price = res.getPrice();
        System.out.println("The total cost for your reservation is: " + price);
        System.out.println("Enter credit card number: ");
        String cardNum = in.next();
        Payment payment = new Payment(res.getReservationID(), customer.getUsername(), cardNum);
        System.out.println();
        System.out.println("Your final payment is:");
        payment.printFields();
        System.out.println();
        Boolean error = paymentDao.insert(payment);
        if (error){
            System.out.println("ERROR: This payment has already been made.");
        }
        return payment;
    }

    private static void showAvailability(Scanner in, Dao<Reservation> resDao, Dao<Room> roomDao, Dao<Payment> paymentDao, Customer customer) {
        System.out.println("How do you want to search?");
        System.out.println("    By Day (d)?");
        System.out.println("    By Specifications (s)?");
        System.out.print("Enter choice: ");
        String choice = in.next();
        if (choice.equals("d")) {
            System.out.println("Enter date (dd-mmm-yy) : ");
            String sDate = getValidDate(in);
            Set<Room> openRooms = ((RoomDaoImpl)roomDao).getAllWhere(sDate);
            Set<Room> allRooms = ((RoomDaoImpl)roomDao).getAll();
            for(Room room : allRooms) {
                room.displayRoom();
                double score = ((RoomDaoImpl)roomDao).getPopularity(sDate, room.getRoomId());
                System.out.println("    Popularity Score: " + score);
                if(openRooms.contains(room)){
                    System.out.println("    Available? Yes.");
                    int length = ((ReservationDaoImpl)resDao).getAvailableLength(sDate, room.getRoomId());
                    System.out.println("    Available Length of Stay: " + length + " days");
                }
                else{
                    System.out.println("    Available? No.");
                    String nextDate = ((ReservationDaoImpl)resDao).getNextAvailableDate(sDate, room.getRoomId());
                    System.out.println("    Next Date Available: " + nextDate);
                    int length = ((ReservationDaoImpl)resDao).getNextAvailableLength(sDate, room.getRoomId());
                    System.out.println("    Next Available Length of Stay: " + length + " days");
                }
                System.out.println();
            }
            makeReservation(in, customer, roomDao, resDao, paymentDao);

        }
        else if (choice.equals("s")) {
            System.out.print("Enter check in date (dd-MMM-yy): ");
            String checkIn = getValidDate(in);
            System.out.print("Enter check out date (dd-MMM-yy): ");
            String checkOut = getValidDate(in);
            System.out.print("Enter desired bed type: ");
            String bedType = in.next();
            System.out.print("Enter desired decor: ");
            String decor = in.next();
            System.out.print("Enter price range (low high): ");
            double low = in.nextDouble();
            double high = in.nextDouble();
            System.out.print("Enter number of beds: ");
            int numBeds = in.nextInt();
            System.out.print("Enter number of occupants: ");
            int numOccupants = in.nextInt();
            Set<Room> specificRooms = ((RoomDaoImpl)roomDao).getMatchingRooms(checkIn,
                    checkOut, bedType, decor, low, high,
                    numBeds, numOccupants);

            for(Room room : specificRooms) {
                System.out.println(room.getRoomName());
            }
            makeReservation(in, customer, roomDao, resDao, paymentDao);
            System.out.println();
        }
        else {
            System.out.println("Invalid choice. Try again");
        }

    }

    private static void changeReservation(Scanner in, Dao<Reservation> resDao, Dao<Payment> paymentDao) {
        System.out.print("Enter reservation number to change or cancel: ");
        int num = in.nextInt();

        Reservation res = ((ReservationDaoImpl)resDao).getByCode(num);
        System.out.println("Here is your current reservation: ");
        res.displayReservation();

        System.out.println("How do you want to change your reservation?");
        System.out.println("   Cancel (x) ");
        System.out.println("   Change checkin (i)");
        System.out.println("   Change checkout (o)");
        System.out.println("   Change room (r)");

        String choice = in.next();
        boolean success = false;
        switch(choice) {
            case "x":
                Boolean error = resDao.delete(res);
                if(error){
                    System.out.println("Error: Deleting reservation failed.");
                }
                else{
                    System.out.println("Reservation deleted!");
                }
                break;
            case "i":
                //prompt for new things:
                System.out.println("What is the new checkin date?");
                String newCheckin = in.next();
                res.setCheckIn(newCheckin);
                //res.setReservationID();
                success = resDao.update(res);
                break;
            case "o":
                System.out.println("What is the new checkout date?");
                String newCheckout = in.next();
                res.setCheckOut(newCheckout);
                success = resDao.update(res);
                break;
            case "r":
                System.out.println("What is the new room code you are asking for?");
                String newRoom = in.next();
                res.setRoomID(newRoom);
               // res.setReservationID();
                success = resDao.update(res);
                break;
        }
        if(success)
        {
            System.out.println("Here is your new reservation information: ");
            res.displayReservation();
        }
        else
        {
            changeReservation(in, resDao, paymentDao);
        }
    }

    private static void showReservations(Scanner in, Dao<Reservation> resDao, Customer customer) {
        Set<Reservation> reservations = ((ReservationDaoImpl)resDao)
                .getAllWhere(customer.getFirstName(), customer.getLastName());
        for ( Reservation res: reservations) {
            res.displayReservation();
        }
        if (reservations.isEmpty()) System.out.println("You have made no reservations.");
    }
}
