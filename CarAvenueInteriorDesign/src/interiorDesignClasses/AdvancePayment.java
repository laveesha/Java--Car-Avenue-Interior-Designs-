
package interiorDesignClasses;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;


public class AdvancePayment extends Payment {
    
    private double advance;
    private double balance;

    public AdvancePayment() 
    {
        conn = dbConnect.connect();
        
        super.paymentId = null;
        this.advance = 0;
        this.balance = 0;
        super.paymentDate = null;
        super.custNIC = null;
    }
    
    public AdvancePayment(String pId , double pAdvance ,double pBal, String pNIC )
    {
        
        super(pId ,pNIC);
        this.advance = pAdvance;
        this.balance = pBal; 
    }
    

    //Set Methods
    public void setAdvance(double pAdvance)
    {
       this.advance = pAdvance;
    }
    
    public void setBalance(double pbal)
    {
       this.balance = pbal;
    }
    
    
    //get methods
    public double getAdvance()
    {
        return this.advance;
    }
    
    public double getBalance()
    {
        return this.balance;
    }
    
    
    //other methods
    public double calcAdvance(double salesPrice)
    {
        this.advance = salesPrice*25/100.0;
        return this.advance;
    }
    
    public double calcBalance(double sales , double advance)
    {
        this.balance = sales - advance;
        return balance;
    }
    
    public void searchAdvance(String payId, JTable tableName)
    {
        try 
            {
                String sql = "SELECT * FROM advance WHERE paymentID like '%"+payId+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void searchAdvanceByCustomer(String nic, JTable tableName)
    {
        try 
            {
                String sql = "SELECT * FROM advance WHERE NIC like '%"+nic+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void loadAdvanceTable(JTable tableName)
    {
        try 
            {
                String sql = "SELECT * FROM advance";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void addAdvanceEntry()
    {
        try 
        {
            String sql = "INSERT INTO advance(advance,paymentDate,balance,NIC) VALUE('"+advance+"','"+paymentDate+"','"+balance+"','"+custNIC+"')";
            pst=conn.prepareStatement(sql);
            pst.execute();
                
            JOptionPane.showMessageDialog(null, "You have successfully record new  advance payment.");
        } 
        catch (Exception e) 
        {
             JOptionPane.showMessageDialog(null, e);
        }        
    }
   
    
    public void removeAdvance(String payId)
    {
        int val=JOptionPane.showConfirmDialog(null, "Do you wish to remove the record?");
            
            if(val==0)
            {
                try 
                {
                    String sql = "DELETE FROM advance WHERE paymentID='"+payId+"' ";
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
    
    public void updateAdvance(String payId)
    {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to update the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "UPDATE advance SET advance='"+advance+"', balance='"+balance+"' WHERE paymentID='"+payId+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated advance payment  "+payId);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }    
    }
    
    
    
}
