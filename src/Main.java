import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        System.out.println("Hotel Reservation System");
        System.out.println("Your options:");
        System.out.println("    Sign Up (s)");
        System.out.println("    Log In (l)");
        System.out.println("Enter option from list: ");

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
                Customer user = new Customer("1", firstName, lastName, username, password);
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

    }
}
