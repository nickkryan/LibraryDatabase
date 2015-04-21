import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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

    public static HashMap<String, Integer> damagedBookReport(String month, String sub1, String sub2, String sub3) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT month.Subject_Name, COUNT( month.Subject_Name ) " +
                "FROM ( " +
                    "SELECT damagedDate.Subject_Name, damagedDate.Isbn, damagedDate.Copy_Num " +
                    "FROM ( " +
                        "SELECT Book.Subject_Name, MAX(damagedBooks.Return_Date) AS LastReturned, Book.Isbn, damagedBooks.Copy_Num " +
                        "FROM Book " +
                        "INNER JOIN ( " +
                            "SELECT Issues.Return_Date, Issues.Book_Isbn, BookCopy.Copy_Num " +
                            "FROM Issues " +
                            "INNER JOIN BookCopy ON Issues.Book_Isbn = BookCopy.Book_Isbn AND Issues.Book_Copy_Num = BookCopy.Copy_Num " +
                            "WHERE BookCopy.Is_Damaged = 1 " +
                        ") damagedBooks ON damagedBooks.Book_Isbn = Book.Isbn " +
                        "GROUP BY Book.Isbn, damagedBooks.Copy_Num " +
                    ") damagedDate " +
                    "WHERE MONTH(damagedDate.LastReturned) = ? " +
                    "AND ( " +
                        "damagedDate.Subject_Name = ? " +
                        "OR damagedDate.Subject_Name = ? " +
                        "OR damagedDate.Subject_Name = ? " +
                    ") " +
                ") " +
                "month GROUP BY month.Subject_Name",
                month, sub1, sub2, sub3);
            ResultSet rs = ps.executeQuery();) {
            HashMap<String, Integer> ans = new HashMap<>();
            ans.put(sub1, 0);
            ans.put(sub2, 0);
            ans.put(sub3, 0);
            while (rs.next()) {
                ans.put(rs.getString(1), rs.getInt(2));
            }
            return ans;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }


    private static PreparedStatement createPreparedStatement(Connection con, String sql, String ... args) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i + 1, args[i]);
        }
        return ps;
    }
}
