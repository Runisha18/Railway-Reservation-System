import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainService {
    public static void addTrain(String trainName, String trainType) {
        String sql = "INSERT INTO trains(train_name, train_type) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trainName);
            ps.setString(2, trainType);
            ps.executeUpdate();
            System.out.println("Train added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding train: " + e.getMessage());
        }
    }

    public static void addStation(String stationName, String city) {
        String sql = "INSERT INTO stations(station_name, city) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, stationName);
            ps.setString(2, city);
            ps.executeUpdate();
            System.out.println("Station added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding station: " + e.getMessage());
        }
    }

    public static void viewTrains() {
        String sql = "SELECT * FROM trains";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Train List ---");
            while (rs.next()) {
                System.out.println(rs.getInt("train_id") + ". "
                        + rs.getString("train_name") + " - "
                        + rs.getString("train_type"));
            }

        } catch (Exception e) {
            System.out.println("Error viewing trains: " + e.getMessage());
        }
    }

    public static void viewRoute(int trainId) {
        String sql = "SELECT r.stop_number, s.station_name, s.city, r.arrival_time, r.departure_time " +
                "FROM routes r JOIN stations s ON r.station_id = s.station_id " +
                "WHERE r.train_id = ? ORDER BY r.stop_number";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Route Details ---");
            while (rs.next()) {
                System.out.println("Stop " + rs.getInt("stop_number") + ": "
                        + rs.getString("station_name") + " (" + rs.getString("city") + ")"
                        + " | Arrival: " + rs.getString("arrival_time")
                        + " | Departure: " + rs.getString("departure_time"));
            }

        } catch (Exception e) {
            System.out.println("Error viewing route: " + e.getMessage());
        }
    }

    public static void updateTrainStatus(int trainId, String message, int delayMinutes) {
        String sql = "INSERT INTO train_status(train_id, status_message, delay_minutes) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, trainId);
            ps.setString(2, message);
            ps.setInt(3, delayMinutes);
            ps.executeUpdate();
            System.out.println("Train status updated successfully.");

        } catch (Exception e) {
            System.out.println("Error updating train status: " + e.getMessage());
        }
    }
}
