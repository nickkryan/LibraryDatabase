import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JdbcExample {
    public static void main(String args[]) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_25",
            "cs4400_Group_25",
            "S3UAsEET");
            if(!con.isClosed())
                System.out.println("Successfully connected to " +
                    "MySQL server using TCP/IP...");
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(con != null)
                    con.close();
            } catch(SQLException e) {}
        }
    }
}
