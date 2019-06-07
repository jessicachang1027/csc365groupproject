import java.sql.Date;
import java.text.DateFormat;
import java.util.Scanner;

public class Main {

    private static void printHomeMenu() {
        System.out.println("Hotel Reservation System");
        System.out.println("Your options:");
        System.out.println("    Sign Up (s)");
        System.out.println("    Log In (l)");
        System.out.print("Enter option from list: ");
    }

    private static void printUserMenu() {
        System.out.println("Your options: ");
        System.out.println("    Search Room Availability (a)");
        System.out.println("    Change/Cancel Reservation (c)");
        System.out.println("    View Reservations (v)");
        System.out.print("Enter option from list: ");
    }

    public static void main(String args[]) {
        printHomeMenu();

        Scanner in = new Scanner(System.in);
        String choice = in.next();
        String firstName;
        String lastName;
        String username;
        String password;
        switch (choice) {
            case "s":
                //TODO: add sql statements to add customer to database
                System.out.println("Enter First Name: ");
                firstName = in.next();
                System.out.println("Enter Last Name: ");
                lastName = in.next();
                System.out.println("Enter username: ");
                username = in.next();
                System.out.println("Enter password: ");
                password = in.next();
                //Customer user = new Customer("1", firstName, lastName, username, password);
                break;
            case "l":
                //TODO: add sql statements to get customer from database
                System.out.println("Enter username: ");
                username = in.next();
                System.out.println("Enter password: ");
                password = in.next();
                System.out.println("Hello " + username);
                break;
        }

        printUserMenu();
        choice = in.next();
        switch (choice) {
            case "a":
                showAvailability(in);
                break;
            case "c":
                changeReservation(in);
                break;
            case "v":
                showReservations(in);
                break;
            default:
                System.out.println("Invalid choice. Try again");
                break;
        }

    }

    private static void showAvailability(Scanner in) {
        System.out.println("How do you want to search?");
        System.out.println("    By Day (d)?");
        System.out.println("    By Specifications (s)?");
        System.out.print("Enter choice: ");
        String choice = in.next();
        if (choice.equals("d")) {
            System.out.println("Enter date : ");
            Date day = new Date(in.nextLong());
            //TODO: get availability from database and print all rooms available
        }
        else if (choice.equals("s")) {
            System.out.print("Enter check in date: ");
            System.out.print("Enter check out date: ");
            System.out.print("Enter desired room type: ");
            System.out.print("Enter desired decor: ");
            System.out.print("Enter price range (low, high): ");
            System.out.print("Enter number of rooms: ");
            System.out.print("Enter number of occupants: ");
            //TODO: find rooms from database using specifications and print all available rooms
        }
        else {
            System.out.println("Invalid choice. Try again");
        }

    }

    private static void changeReservation(Scanner in) {
        System.out.print("Enter reservation number to change or cancel: ");
        String num = in.next();
        //TODO: find reservation in database

    }

    private static void showReservations(Scanner in) {
        //TODO: print all reservations the user has that are active or past

    }
}
