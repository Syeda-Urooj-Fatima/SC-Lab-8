import java.util.Random;
import java.sql.*;

public class StudentDataEntry {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/students?useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    protected String getSaltName() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected int getSaltRegistration(){
        Random rnd = new Random();
        return 123111+rnd.nextInt(199999);
    }

    protected int getSaltSemester(){
        Random rnd = new Random();
        return 1+rnd.nextInt(8);
    }

    protected String getSaltAddress() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz 123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static void main(String args[])
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            conn.setAutoCommit(false);

            long startTime = System.currentTimeMillis();
            StudentDataEntry dataEntry=new StudentDataEntry();
            String name="";
            String address="";
            int registration=0;
            int semester=1;
            for (int i=0; i<5000; i++)
            {
                name = dataEntry.getSaltName();
                registration = dataEntry.getSaltRegistration();
                semester = dataEntry.getSaltSemester();
                address = dataEntry.getSaltAddress();

                String sql = "INSERT INTO Student(Name, Registration_number, Semester, Address) VALUES('"+name+"',"+registration+","+semester+",'"+address+"')";
                stmt.executeUpdate(sql);
            }

            conn.commit();
            long stopTime = System.currentTimeMillis();
            stmt.close();
            conn.close();
            System.out.println(stopTime-startTime);
        }

        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }

        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

        finally
        {
            //finally block used to close resources
            try
            {
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2)
            {
            }// nothing we can do

            try
            {
                if(conn!=null)
                    conn.close();
            }
            catch(SQLException se)
            {
                se.printStackTrace();
            }//end finally try
        }//end try

    }//end main
}
