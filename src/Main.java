import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Railway Reservation System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. View Trains");
            System.out.println("3. View Train Route");
            System.out.println("4. View Available Seats");
            System.out.println("5. Book Ticket");
            System.out.println("6. Cancel Ticket");
            System.out.println("7. View Passenger Notifications");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    adminMenu(sc);
                    break;
                case 2:
                    TrainService.viewTrains();
                    break;
                case 3:
                    System.out.print("Enter train ID: ");
                    int routeTrainId = sc.nextInt();
                    TrainService.viewRoute(routeTrainId);
                    break;
                case 4:
                    System.out.print("Enter train ID: ");
                    int seatTrainId = sc.nextInt();
                    BookingService.viewAvailableSeats(seatTrainId);
                    break;
                case 5:
                    bookTicketMenu(sc);
                    break;
                case 6:
                    System.out.print("Enter booking ID: ");
                    int bookingId = sc.nextInt();
                    BookingService.cancelTicket(bookingId);
                    break;
                case 7:
                    System.out.print("Enter passenger ID: ");
                    int passengerId = sc.nextInt();
                    NotificationService.viewNotifications(passengerId);
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using Railway Reservation System.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

    public static void adminMenu(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (!AdminService.login(username, password)) {
            System.out.println("Invalid admin credentials.");
            return;
        }

        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Train");
            System.out.println("2. Add Station");
            System.out.println("3. Update Train Status");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter train name: ");
                    String trainName = sc.nextLine();
                    System.out.print("Enter train type: ");
                    String trainType = sc.nextLine();
                    TrainService.addTrain(trainName, trainType);
                    break;
                case 2:
                    System.out.print("Enter station name: ");
                    String stationName = sc.nextLine();
                    System.out.print("Enter city: ");
                    String city = sc.nextLine();
                    TrainService.addStation(stationName, city);
                    break;
                case 3:
                    System.out.print("Enter train ID: ");
                    int trainId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter status message: ");
                    String message = sc.nextLine();
                    System.out.print("Enter delay in minutes: ");
                    int delay = sc.nextInt();
                    TrainService.updateTrainStatus(trainId, message, delay);
                    break;
                case 4:
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void bookTicketMenu(Scanner sc) {
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        int passengerId = PassengerService.addPassenger(name, age, gender, phone, email);
        if (passengerId == -1) {
            System.out.println("Passenger creation failed.");
            return;
        }

        TrainService.viewTrains();
        System.out.print("Enter train ID: ");
        int trainId = sc.nextInt();

        BookingService.viewAvailableSeats(trainId);
        System.out.print("Enter seat ID: ");
        int seatId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter journey date (YYYY-MM-DD): ");
        String journeyDate = sc.nextLine();

        BookingService.bookTicket(passengerId, trainId, seatId, journeyDate);
    }
}

//import java.sql.Connection;
//
//public class Main {
//    public static void main(String[] args) {
//
//        Connection con = DBConnection.getConnection();
//
//        if (con != null) {
//            System.out.println("Connection Working!");
//        }
//    }
//}
