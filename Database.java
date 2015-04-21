import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static Book searchISBN(String ISBN) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM Book as B WHERE (B.Isbn = ?)",
                ISBN);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10)
                    );
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Book> searchBookTitles(String titleQuery) {
        ArrayList<Book> resultBooks = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM Book WHERE Book.Title LIKE ?");
            ) {
            ps.setString(1, "%" + titleQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultBooks.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10)
                    ));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return resultBooks;
    }

    public static ArrayList<Book> searchAuthors(String authorQuery) {
        ArrayList<Book> resultBooks = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM Book, Authors WHERE Book.Isbn = Authors.Book_Isbn AND Authors.Name = ?",
                authorQuery);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                resultBooks.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getString(10)
                    ));
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return resultBooks;
    }

    public static String[] trackLocation(String bookIsbn) {
        String[] location = new String[4];
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT B.Shelf_Num, B.Subject_Name, S.Aisle_Num, S.Floor_Num " +
                "From Shelf as S, Book as B WHERE S.Shelf_Num = B.Shelf_Num AND B.Isbn = ?",
                bookIsbn);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                location[0] = rs.getString(1);
                location[1] = rs.getString(2);
                location[2] = rs.getString(3);
                location[3] = rs.getString(4);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return location;
    }

    public static String[] returnBookInfo(String issueId) {
        String[] issueInfo = new String[4];
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT I.User_Username, I.Book_Isbn, I.Book_Copy_Num, I.Return_Date" +
                " From Issues as I WHERE I.Issue_ID = ?",
                issueId);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                issueInfo[0] = rs.getString(1);
                issueInfo[1] = rs.getString(2);
                issueInfo[2] = rs.getString(3);
                issueInfo[3] = rs.getString(4);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return issueInfo;
    }

    public static boolean returnBookAndSetPenalties(String isDamaged,
            String user, String isbn, String copyNum) {
        boolean success = true;
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(con, 
                "UPDATE StudentFaculty, Issues, BookCopy SET Penalty = IFNULL(Penalty, 0) + " +
                "(DATEDIFF(CURDATE(), Expected_Return_Date) * 50), Is_Debarred = IF(IFNULL(Penalty, " +
                "0) + (DATEDIFF(CURDATE(), Expected_Return_Date) * 50) >= 10000, 1, 0), Return_Date =" +
                "CURDATE(), Is_Checked_Out = 0, Is_Damaged = ? WHERE Username = ? AND Issues.Book_Isbn = ? " +
                "AND BookCopy.Book_Isbn = ? AND Issues.Book_Isbn = BookCopy.Book_Isbn " +
                "AND Book_Copy_Num = ? AND Copy_Num = ? AND Return_Date IS NULL AND " +
                "User_Username = ? AND Is_Checked_Out = 1", isDamaged, user, isbn, isbn, copyNum, copyNum, user);
            ) {
            int rs = ps.executeUpdate();
        } catch (Exception e) {
            success = false;
            System.err.println("Exception: " + e.getMessage());
        }
        return success;

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

    public static ArrayList<ArrayList<String>> frequentUsersReport() {
        ArrayList<String> janList = new ArrayList<>();
        ArrayList<String> febList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con,"(SELECT MONTH(Date_Of_Issue), COUNT(User_Username), User_Username FROM Issues WHERE MONTH(Date_Of_Issue) = 1 GROUP BY User_Username HAVING COUNT(User_Username) > 10)" +
                    "UNION" +
                    "(SELECT MONTH(Date_Of_Issue), COUNT(User_Username), User_Username FROM Issues WHERE MONTH(Date_Of_Issue) = 2 GROUP BY User_Username HAVING COUNT(User_Username) > 10)");
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                if (rs.getString(1).equals("1")) {
                    janList.add(rs.getString(3) + "     " + rs.getString(2));
                } else {
                    febList.add(rs.getString(3) + "     " + rs.getString(2));
                }
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        ArrayList<ArrayList<String>> both = new ArrayList<>();
        both.add(janList);
        both.add(febList);
        return both;
    }

    public static ArrayList<ArrayList<ArrayList<String>>> popularBookReport() {
        ArrayList<ArrayList<String>> janList = new ArrayList<>();
        ArrayList<ArrayList<String>> febList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con,"(SELECT MONTH(Date_Of_Issue), Title, Count(Isbn) "
                + "FROM Issues INNER JOIN Book ON Book.Isbn = Issues.Book_Isbn "
                + "Where MONTH(Date_Of_Issue) = 1 AND Isbn IN (SELECT Isbn FROM Issues GROUP BY Isbn) "
                + "GROUP BY Isbn Limit 3) "
                + "UNION "
                + "(SELECT MONTH(Date_Of_Issue), Title, Count(Isbn) "
                + "FROM Issues INNER JOIN Book ON Book.Isbn = Issues.Book_Isbn "
                + "Where MONTH(Date_Of_Issue) = 2 AND Isbn IN (SELECT Isbn FROM Issues GROUP BY Isbn) "
                + "GROUP BY Isbn Limit 3)");
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                if (rs.getString(1).equals("1")) {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(rs.getString(2));
                    temp.add(rs.getString(3));
                    janList.add(temp);
                } else {
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(rs.getString(2));
                    temp.add(rs.getString(3));
                    febList.add(temp);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        ArrayList<ArrayList<ArrayList<String>>> both = new ArrayList<>();
        both.add(janList);
        both.add(febList);
        return both;
    }

    private static PreparedStatement createPreparedStatement(Connection con, String sql, String ... args) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i + 1, args[i]);
        }
        return ps;
    }
}
