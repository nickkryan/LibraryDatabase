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
            return 1 == ps.executeUpdate();
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
            return rs == 1;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static Book searchISBN(String isbn) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM Book as B WHERE (B.Isbn = ?)",
                isbn);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                Book b = new Book(
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
                String numAvailable = numAvailableCopy(String.valueOf(b.getIsbn()));
                b.setNumAvailableCopies(numAvailable);
                return b;
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
            ArrayList<String> queryIsbns = new ArrayList<String>();
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
                        rs.getString(10),
                        numAvailableCopy(rs.getString(1))
                    ));

            }

            rs.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return resultBooks;
    }

    public static String numAvailableCopy(String bookIsbn) {
        String resultAvailableCopies = "";
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(con,
                "SELECT COUNT(BookCopy.Copy_Num) FROM BookCopy WHERE BookCopy.Is_Checked_Out = 0 " +
                "AND BookCopy.Is_Damaged = 0 AND BookCopy.Is_On_Hold = 0 AND BookCopy.Book_Isbn = ?", bookIsbn);
            ResultSet rs = ps.executeQuery();
            ) {
            if (rs.next()) {
                resultAvailableCopies = rs.getString(1);
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return resultAvailableCopies;
    }

    public static HashMap<String, String> numAvailableCopies(ArrayList<String> bookIsbn) {
        HashMap<String, String> resultAvailableCopies = new HashMap<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");

            ) {
            String query = "SELECT BookCopy.Book_Isbn, COUNT(BookCopy.Copy_Num) FROM BookCopy WHERE BookCopy.Is_Checked_Out = 0 " +
                "AND BookCopy.Is_Damaged = 0 AND BookCopy.Is_On_Hold = 0 AND BookCopy.Book_Isbn IN ? GROUP BY BookCopy.Book_Isbn";

            String values = "(" + bookIsbn.get(0);
            for (int i = 1; i < bookIsbn.size(); i++) {
                values += ", " + bookIsbn.get(i);
            }
            values += ")";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.println("values: " + values);
            System.out.println("ps: " + ps);
            ps.setString(1, values);
            ResultSet rs = ps.executeQuery();
            ps.close();
            rs.close();
            while (rs.next()) {
                resultAvailableCopies.put(rs.getString(1), rs.getString(2));
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return resultAvailableCopies;

    }

    public static ArrayList<Book> searchAuthors(String authorQuery) {
        ArrayList<Book> resultBooks = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT * FROM Book, Authors WHERE Book.Isbn = Authors.Book_Isbn AND Authors.Name = ?",
                authorQuery);
            ResultSet rs = ps.executeQuery();) {
            ArrayList<String> queryIsbns = new ArrayList<String>();
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
                        rs.getString(10),
                        numAvailableCopy(rs.getString(1))
                    ));
                queryIsbns.add(rs.getString(1));
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

    public static String[] checkoutBookInfo(String issueId) {
        String[] issueInfo = new String[5];
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT I.User_Username, I.Book_Isbn, I.Book_Copy_Num, I.Return_Date, I.Date_Of_Issue" +
                " From Issues as I WHERE I.Issue_ID = ?",
                issueId);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                issueInfo[0] = rs.getString(1);
                issueInfo[1] = rs.getString(2);
                issueInfo[2] = rs.getString(3);
                issueInfo[3] = rs.getString(4);
                issueInfo[4] = rs.getString(5); // date of issue
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return issueInfo;
    }

    public static boolean checkoutBookAndUpdateDb(String currentDate, String isbn, String copyNum,
        String user) {
        boolean success = true;
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");

            PreparedStatement ps = createPreparedStatement(con,
                "INSERT INTO Issues SELECT 0, ?, NULL, DATE_ADD(?, INTERVAL 14 DAY), NULL, 0, " +
            "?, ?, ? FROM StudentFaculty, BookCopy WHERE Username = ? AND " +
            "Is_Debarred = 0 AND Book_Isbn = ? AND Copy_Num = ? AND Is_Damaged = 0 AND ((Is_On_Hold = 0)" +
            " OR ((Is_On_Hold = 1) AND (DATEDIFF(?, Hold_Date) > 3)))",
                currentDate, currentDate, isbn, copyNum, user, user, isbn, copyNum, currentDate);

            PreparedStatement bookSetPs = createPreparedStatement(con,
                "UPDATE BookCopy SET Is_Checked_Out = 1, Is_On_Hold = 0 WHERE Book_Isbn = ? AND Copy_Num = ?",
                isbn, copyNum);
            ){
            int rs = ps.executeUpdate();
            int bsResult = bookSetPs.executeUpdate();
        } catch (Exception e) {
            success = false;
            System.err.println("Exception: " + e.getMessage());
        }
        return success;
    }

    public static String copyNumOfHoldRequest(String isbn) {
        String copyNum = "";
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(con,
                "SELECT MIN(Copy_Num) FROM BookCopy WHERE Book_Isbn = ? AND ((Is_On_Hold = 0) " +
                "OR (Is_On_Hold = 1 AND (DATEDIFF(CURDATE(), Hold_Date) >= 3))) AND Is_Checked_Out = 0 AND Is_Damaged = 0", isbn);
            ResultSet rs = ps.executeQuery();
            ){
            if (rs.next()) {
                copyNum = rs.getString(1);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return copyNum;
    }

    public static int requestHoldUpdateDb(String isbn, String copyNum, String user) {
        int issue_id = -1;
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement isUserDebarred = createPreparedStatement(con,
                "SELECT * FROM StudentFaculty WHERE Username = ? AND Is_Debarred = 0",
                user);

            PreparedStatement ps = createPreparedStatement(con,
                "INSERT INTO Issues SELECT 0, CURDATE(), NULL, DATE_ADD(CURDATE(), INTERVAL 17 DAY), " +
                "NULL, 0, ?, ?, ? FROM StudentFaculty, BookCopy WHERE Username = ?" +
                " AND Is_Debarred = 0 AND Book_Isbn = ? AND Copy_Num = ? AND Is_Damaged = 0" +
                " AND ((Is_On_Hold = 0) OR ((Is_On_Hold = 1) AND (DATEDIFF(CURDATE(), Hold_Date) >= 3)))",
                isbn, copyNum, user, user, isbn, copyNum);

            PreparedStatement bookSetPs = createPreparedStatement(con,
                "UPDATE BookCopy SET Is_Checked_Out = 0, Is_On_Hold = 1, Hold_Date = CURDATE()" +
                " WHERE Book_Isbn = ? AND Copy_Num = ?",
                isbn, copyNum);

            PreparedStatement returnIssue_ID = createPreparedStatement(con,
                "SELECT MAX(Issue_ID) FROM Issues");
            ){
            ResultSet isDebarred = isUserDebarred.executeQuery();
            if (!isDebarred.next()) {
                return -2;
            }
            int rs = ps.executeUpdate();
            int bsResult = bookSetPs.executeUpdate();
            ResultSet idSet = returnIssue_ID.executeQuery();
            if (idSet.next()) {
                issue_id = idSet.getInt(1);
                System.out.println("DB:" + issue_id);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return issue_id;
    }

    public static boolean returnBookAndSetPenalties(String isDamaged,
            String user, String isbn, String copyNum) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(con,
                "UPDATE StudentFaculty, Issues, BookCopy SET Penalty = IFNULL(Penalty, 0) + " +
                "(DATEDIFF(CURDATE(), Expected_Return_Date) * 50), Is_Debarred = IF(IFNULL(Penalty, " +
                "0) + (DATEDIFF(CURDATE(), Expected_Return_Date) * 50) >= 10000, 1, 0), Return_Date = " +
                "NOW(), Is_Checked_Out = 0, Is_Damaged = ? WHERE Username = ? AND Issues.Book_Isbn = ? " +
                "AND BookCopy.Book_Isbn = ? AND Issues.Book_Isbn = BookCopy.Book_Isbn " +
                "AND Book_Copy_Num = ? AND Copy_Num = ? AND Return_Date IS NULL AND " +
                "User_Username = ? AND Is_Checked_Out = 1",
                isDamaged, user, isbn, isbn, copyNum, copyNum, user);
            ) {
            return 3 == ps.executeUpdate();
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

    public static ArrayList<ArrayList<ArrayList<String>>> popularSubjectReport() {
        ArrayList<ArrayList<String>> janList = new ArrayList<>();
        ArrayList<ArrayList<String>> febList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con,"(SELECT MONTH(Date_Of_Issue), Subject_Name, Count(Isbn) "
                + "FROM Issues INNER JOIN Book ON Book.Isbn = Issues.Book_Isbn "
                + "Where MONTH(Date_Of_Issue) = 1 AND Isbn IN (SELECT Isbn FROM Issues GROUP BY Isbn) "
                + "GROUP BY Isbn Limit 3) "
                + "UNION "
                + "(SELECT MONTH(Date_Of_Issue), Subject_Name, Count(Isbn) "
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

    public static String lastUser(String isbn, String copyNum) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT I.User_Username FROM Issues I INNER JOIN ( "
                    + "SELECT MAX(Issues.Return_Date) AS LastReturned FROM "
                    + "Issues WHERE Book_Isbn = ? AND Book_Copy_Num = ?) "
                    + "lastReturned ON lastReturned.LastReturned = I.Return_Date",
                isbn, copyNum);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public static boolean lostDamagedBook(String isbn, String copyNum, String username, String penalty) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "UPDATE StudentFaculty, BookCopy SET Is_Debarred = IF( "
                    + "IFNULL(Penalty, 0) + ? >= 10000, 1, 0), "
                    + "Penalty = IFNULL(Penalty, 0) + ?, Is_Damaged = 1 "
                    + "WHERE StudentFaculty.Username = ? AND Book_Isbn = ? "
                    + "AND Copy_Num = ?",
                penalty, penalty, username, isbn, copyNum);) {
            return 2 == ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static String[] requestExtension(String id) {
        String newDate = extensionNewDate(id);
        String[] result = new String[5];
        System.out.println(newDate);
        if (newDate == null) return null;
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT I.Date_Of_Issue, I.Extension_Date, I.Expected_Return_Date," +
                " CURDATE() AS New_Extension_Date, ? AS New_Estimated_Return_Date" +
                " FROM Issues AS I, BookCopy AS B WHERE I.Issue_ID = ? AND " +
                "B.Is_Checked_Out = 1",
                    newDate, id);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                result[0] = rs.getString(1); //date of issue
                result[1] = rs.getString(2); //extension date
                result[2] = rs.getString(3); //expected return
                result[3] = rs.getString(4); //new extension
                result[4] = rs.getString(5); //new est_return
                return result;
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }


    public static boolean updateExtensionDate(String id, String newDate) {
        System.out.println(id + " " + newDate);
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "UPDATE Issues, BookCopy, StudentFaculty SET Issues.Count_Of_Extensions = " +
                "Issues.Count_Of_Extensions +1, Issues.Extension_Date = CURDATE(), Issues.Expected_Return_Date = " +
                "? WHERE Issues.User_Username IN (SELECT Username FROM StudentFaculty WHERE Issues.Issue_ID = ? " +
                    "AND Issues.User_Username = StudentFaculty.Username AND Issues.Count_Of_Extensions < 2 AND " +
                    "StudentFaculty.Is_Faculty = 0) OR Issues.User_Username IN (SELECT Username FROM StudentFaculty " +
                    "WHERE Issues.Issue_ID = ? AND Issues.User_Username = StudentFaculty.Username AND Issues.Count_Of_Extensions < 5 " +
                    "AND StudentFaculty.Is_Faculty = 1) AND Issues.Issue_ID = ? AND Issues.Book_Copy_Num AND Issues.Book_Isbn IN " +
                "(SELECT Book_Isbn AND Copy_Num FROM BookCopy WHERE Is_On_Hold = 0)",
                    newDate, id, id, id);) {
            return 3 == ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static String extensionNewDate(String id) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, " (SELECT DATE_ADD(CURDATE() , INTERVAL 28 DAY) AS New_Date" +
                     " From Issues INNER JOIN StudentFaculty ON Issues.User_Username " +
                     "= StudentFaculty.Username WHERE Issue_Id = ? AND Is_Faculty" +
                     " = 0) UNION (SELECT DATE_ADD(CURDATE() , INTERVAL 56 DAY) AS New_Date" +
                     " From Issues INNER JOIN StudentFaculty ON Issues.User_Username = StudentFaculty.Username" +
                     " WHERE Issue_Id = ? AND Is_Faculty = 1)",
                    id, id);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public static String[] getFutureHoldRequest(String isbn) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT DISTINCT I.Book_Copy_Num, earliest.AvailableDate FROM "
                + "Issues I INNER JOIN (SELECT MIN(available.Expected_Return_Date) AS "
                + "AvailableDate FROM (SELECT issuedCopies.* FROM (SELECT BookCopy.Copy_Num "
                + "AS CopyNum, Issues.Expected_Return_Date, Issues.Book_Isbn, "
                + "BookCopy.Is_On_Hold, Future_Requester, Hold_Date FROM Issues "
                + "INNER JOIN BookCopy ON Issues.Book_Isbn = BookCopy.Book_Isbn "
                + "AND Issues.Book_Copy_Num = BookCopy.Copy_Num WHERE "
                + "BookCopy.Is_Checked_Out = 1 OR (BookCopy.Is_On_Hold = 1 AND "
                + "DATEDIFF(CURDATE(), BookCopy.Hold_Date) < 3)) issuedCopies WHERE "
                + "issuedCopies.Book_Isbn = ? AND issuedCopies.Future_Requester IS NULL) available) "
                + "earliest ON earliest.AvailableDate = I.Expected_Return_Date",
                    isbn);
            ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return new String[]{rs.getString(1), rs.getString(2)};
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }

    public static boolean setFutureHoldRequest(String user, String isbn, String copyNum) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "UPDATE BookCopy SET Future_Requester = ? WHERE "
                + "Book_Isbn = ? AND Copy_Num = ?",
                    user, isbn, copyNum);) {
            return 1 == ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }

    public static boolean checkIfUserIsStaff(String user) {
        try (Connection con = DriverManager.getConnection(conString,
                "cs4400_Group_25", "S3UAsEET");
            PreparedStatement ps = createPreparedStatement(
                con, "SELECT Is_Staff FROM User WHERE User.Username = ?", user);
             ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                return (1 == rs.getInt(1));
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
