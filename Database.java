import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String conString =
        "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_25";

    public static boolean login(String user, String pass) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM User WHERE Username = ? AND Password = ?",
                user, pass);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                return user.equals(rs.getString(1)) && pass.equals(rs.getString(2));
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static boolean register(String user, String pass) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "INSERT INTO User VALUES (?, ?, false)",
                user, pass);) {
            int rs = ps.executeUpdate();
            System.out.println(rs);
            return rs == 1;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateProfile(String name, String dob, String gender,
        String email, String address, boolean facultyStatus, String department,
        String user) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "INSERT INTO StudentFaculty VALUES (?, ?, ?, 0, ?, ?, ?, 0, ?, ?)",
                name, dob, gender, email, address, facultyStatus ? "1" : "0",
                department, user);) {
            int rs = ps.executeUpdate();
            System.out.println(rs);
            return rs == 1;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static int searchISBN(String ISBN) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM Book as B WHERE (B.Isbn = ?)",
                ISBN);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return -1;
    }

    public static boolean searchTitle(String user, String pass) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM User WHERE Username = ? AND Password = ?",
                user, pass);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                return user.equals(rs.getString(1)) && pass.equals(rs.getString(2));
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static boolean searchAuthor(String user, String pass) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM User WHERE Username = ? AND Password = ?",
                user, pass);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                return user.equals(rs.getString(1)) && pass.equals(rs.getString(2));
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    private static PreparedStatement createPreparedStatement(Connection con, String sql, String ... args) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i + 1, args[i]);
        }
        return ps;
    }
}
