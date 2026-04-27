import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookingService {
    public static void viewAvailableSeats(int trainId) {
        String sql = "SELECT seat_id, seat_number, class_type FROM seats WHERE train_id = ? AND is_available = TRUE";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Available Seats ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Seat ID: " + rs.getInt("seat_id")
                        + " | Seat No: " + rs.getString("seat_number")
                        + " | Class: " + rs.getString("class_type"));
            }

            if (!found) {
                System.out.println("No seats available for this train.");
            }

        } catch (Exception e) {
            System.out.println("Error viewing seats: " + e.getMessage());
        }
    }

    public static void bookTicket(int passengerId, int trainId, int seatId, String journeyDate) {
        String bookingSql = "INSERT INTO bookings(passenger_id, train_id, seat_id, booking_date, journey_date, status) " +
                "VALUES (?, ?, ?, CURDATE(), ?, 'Booked')";
        String seatSql = "UPDATE seats SET is_available = FALSE WHERE seat_id = ? AND is_available = TRUE";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement seatPs = con.prepareStatement(seatSql)) {
                seatPs.setInt(1, seatId);
                int updated = seatPs.executeUpdate();

                if (updated == 0) {
                    System.out.println("Seat is not available.");
                    con.rollback();
                    return;
                }
            }

            int bookingId = -1;
            try (PreparedStatement bookingPs = con.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS)) {
                bookingPs.setInt(1, passengerId);
                bookingPs.setInt(2, trainId);
                bookingPs.setInt(3, seatId);
                bookingPs.setString(4, journeyDate);
                bookingPs.executeUpdate();

                ResultSet rs = bookingPs.getGeneratedKeys();
                if (rs.next()) {
                    bookingId = rs.getInt(1);
                }
            }

            con.commit();
            NotificationService.addNotification(passengerId, bookingId, "Your railway ticket has been booked successfully.");
            System.out.println("Ticket booked successfully. Booking ID: " + bookingId);

        } catch (Exception e) {
            System.out.println("Booking error: " + e.getMessage());
        }
    }

    public static void cancelTicket(int bookingId) {
        String findSql = "SELECT seat_id, passenger_id FROM bookings WHERE booking_id = ? AND status = 'Booked'";
        String cancelSql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";
        String seatSql = "UPDATE seats SET is_available = TRUE WHERE seat_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            int seatId = -1;
            int passengerId = -1;

            try (PreparedStatement findPs = con.prepareStatement(findSql)) {
                findPs.setInt(1, bookingId);
                ResultSet rs = findPs.executeQuery();

                if (rs.next()) {
                    seatId = rs.getInt("seat_id");
                    passengerId = rs.getInt("passenger_id");
                } else {
                    System.out.println("Valid booked ticket not found.");
                    con.rollback();
                    return;
                }
            }

            try (PreparedStatement cancelPs = con.prepareStatement(cancelSql)) {
                cancelPs.setInt(1, bookingId);
                cancelPs.executeUpdate();
            }

            try (PreparedStatement seatPs = con.prepareStatement(seatSql)) {
                seatPs.setInt(1, seatId);
                seatPs.executeUpdate();
            }

            con.commit();
            NotificationService.addNotification(passengerId, bookingId, "Your railway ticket has been cancelled successfully.");
            System.out.println("Ticket cancelled successfully.");

        } catch (Exception e) {
            System.out.println("Cancellation error: " + e.getMessage());
        }
    }
}
