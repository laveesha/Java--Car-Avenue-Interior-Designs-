
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;


public class Payment {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs =null;
    
    
    protected String paymentId;
    private double amount;
    protected String paymentDate;
    protected String custNIC;
    
    
    public Payment()
    {
        conn = dbConnect.connect();
        
        this.paymentId = null;
        this.amount = 0;
        this.paymentDate= null;
        this.custNIC =null;
    }
    
    public Payment(String pId , double pAmount, String pNIC)
    {
        this.paymentId = pId;
        this.amount = pAmount;
        this.custNIC = pNIC;
    }
    
    public Payment(String pId,String pNIC)
    {
        this.paymentId = pId;
        this.custNIC= pNIC; 
    }
    

    
        //Set methods
     public void setPaymentID(String payid)
     {
        this.paymentId = payid;
     }
        
     public void setPaymentDate(String paydate)
     {
        this.paymentDate= paydate;
     }
        
     public void setAmount(double pAmount)
     {
        this.amount = pAmount;
     }

     public void setCustNIC(String nic)
     {
         this.custNIC = nic;
     }

 
     //Get methods
     public String getPaymentID()
     {
        return this.paymentId;
     }
        
     public String getPaymentDate()
     {
        return this.paymentDate;
     }
        
     public double getAmount()
     {
        return this.amount;
     }

     public String getCustNIC()
     {
         return this.custNIC;
     }
        
     
    
    public double calcFullPayment(double advance, double bal)
    {
        this.amount = advance + bal;
        return amount;
    }
    
    public void serachPayment(String payId, JTable tableName)
    {
        try 
        {
            String sql = "SELECT * FROM payment WHERE paymentID like '%"+payId+"%'";
            pst=conn.prepareStatement(sql);
            rs =pst.executeQuery();
                
            tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void loadPaymentTable(JTable tableName)
    {
        try 
            {
                String sql = "SELECT * FROM payment";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
        catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void addPaymentEntry()
    {
        try 
        {
            String sql = "INSERT INTO payment(paymentID,amount,paymentDate,NIC) VALUE('"+paymentId+"','"+amount+"','"+paymentDate+"','"+custNIC+"')";
            pst=conn.prepareStatement(sql);
            pst.execute();
                
            JOptionPane.showMessageDialog(null, "You have successfully record new Payment.");
        } 
        catch (Exception e) 
        {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void removePayment(String payId)
    {
        int val=JOptionPane.showConfirmDialog(null, "Do you wish to remove the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "DELETE FROM payment WHERE paymentID='"+payId+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, payId+ " Removed Successfully ");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
    }
    
    
}
