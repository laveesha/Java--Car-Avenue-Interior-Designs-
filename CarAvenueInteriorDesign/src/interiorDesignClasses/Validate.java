
package interiorDesignClasses;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;

public class Validate {
    
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs =null;
    
    
    public Validate() 
    {
        conn = dbConnect.connect();
    }
    
    
    public boolean validateNIC(String nic)
    {
        boolean result= true;
        
        if(!(nic.trim().matches("^[0-9]{9}[vVxX]$")))
        {
            result =false;
        }
        
        return result;
    }
    
    public boolean validateEmail(String email)
    {
        boolean result = true;
        
          if(!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
          {
              result = false;
          } 
          
        return result;
    }
    
    public boolean ValidateCustomer(String nic)
    {
            boolean result =true;
            String sql = "SELECT NIC FROM register WHERE NIC = '"+nic+"'";
            
            try 
            {
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               if(!rs.next())
               {
                    JOptionPane.showMessageDialog(null,"Invalid Customer");
                    result=false;
               }
        
            } 
            
           catch (SQLException | HeadlessException e) 
            {
                JOptionPane.showMessageDialog(null,e);
           }
        return result;
    }
        
    public boolean ValidPrice(double price)
    {
            
            return price>=0;
        
    }
    
    public boolean ValidQuantity(int qty)
    {
            return qty >= 0;
        
    }
    
        public boolean isCurrentDate(String date)
        {
            boolean status =  false;
            LocalDate currentDate = java.time.LocalDate.now();
            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate newdate =  LocalDate.parse(date, format);
            
            if(currentDate.isEqual(newdate))
            {
                status = true;
            }
            
            return status;

        }
        
        public boolean isValidDate(String date)
        {
            boolean status =  false;
            LocalDate currentDate = java.time.LocalDate.now();
            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate newdate =  LocalDate.parse(date, format);
            
            if(currentDate.isBefore(newdate))
            {
                status = true;
            }
            
            return status;

        }
    
    
    
}




