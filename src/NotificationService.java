import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NotificationService {
    public static void addNotification(int passengerId, int bookingId, String message) {
        String sql = "INSERT INTO notifications(passenger_id, booking_id, message) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, passengerId);
            ps.setInt(2, bookingId);
            ps.setString(3, message);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error adding notification: " + e.getMessage());
        }
    }

    public static void viewNotifications(int passengerId) {
        String sql = "SELECT message, sent_at FROM notifications WHERE passenger_id = ? ORDER BY sent_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, passengerId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Notifications ---");
            while (rs.next()) {
                System.out.println(rs.getString("sent_at") + " : " + rs.getString("message"));
            }

        } catch (Exception e) {
            System.out.println("Error viewing notifications: " + e.getMessage());
        }
    }
}
