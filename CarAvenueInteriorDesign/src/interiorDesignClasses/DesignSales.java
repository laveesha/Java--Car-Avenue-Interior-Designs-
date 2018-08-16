
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class DesignSales extends Sales{
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs =null;
   
    
    private String designCode;
    private double serviceCharge;
    
    Design d1 =new Design();
    
    public DesignSales()
    {
        super();
        this.designCode=null;  
        
        conn= dbConnect.connect();
    }
    
    public DesignSales(String sid, String pNIC, String dCode)
    {
        super(sid, pNIC);
        this.designCode = dCode;
    }
    
    
    //set Method
    public void setDesignCode(String dcode)
    {
        d1.setDesignCode(dcode);
    }

    public void setServiceCharge(double serviceCharge) 
    {
        this.serviceCharge = serviceCharge;
    }
    
    
    //get method
    public String getDesignCode()
    {
        
        this.designCode=d1.getDesignCode();
        return this.designCode;
    }

    public double getServiceCharge() 
    {
        return serviceCharge;
    }
    
 
//method overload  

    public double calcSalesPrice(double totalDesignCost) 
    {
        this.salesPrice = totalDesignCost + (totalDesignCost*20/100.0);
        return salesPrice;
    }
    
    public double calcSeviceCharges(double totcost , double sales )
    {
        double serviceCharges = sales -totcost;
        return serviceCharges;
    }
    
    
//    @Override
//    public void setSalesPrice(double salesPrice)
//    {
//       super.salesPrice =salesPrice;
//    }
    
    public void searchDesignSales(String nic, JTable tableName)
    {
         try 
            {
                String sql = "SELECT * FROM designsales WHERE custNIC like '%"+nic+"%' ";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e + "Sales Search Error");
            }
    }
    
    public void loadDesignSalesTable(JTable tableName)
    {
        try 
            {
                String sql = "SELECT * FROM designsales";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                //System.out.print(rs);

                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e +"Sales Load Error");
            }
    }
    
    public void addDesignSalesEntry()
    {
         try 
            {
                String sql = "INSERT INTO designsales(salesDate,salingPrice,custNIC,designCode,serviceCharges) VALUE('"+salesDate+"','"+salesPrice+"','"+custNIC+"','"+designCode+"','"+serviceCharge+"')";
                pst=conn.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "You have successfully record new Sales.");
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e + "Sales Add Error");
            }
    }
    
    public void updateDesignSales(String sid)
        {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to update the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "UPDATE designsales SET salingPrice='"+salesPrice+"' WHERE salesID ='"+sid+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated sales " + sid);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    
}
