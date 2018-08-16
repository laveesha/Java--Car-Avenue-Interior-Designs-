
package interiorDesignClasses;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class dbConnect {
    
    public static Connection connect()
    {
        Connection conn = null;
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/caravenue","root","");
        } 
        catch (Exception e) 
        {
        }
        
        return conn;
    }
    
}
