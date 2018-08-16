
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;


public class DesignOrder {
    
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs =null;
    
    
        private String designCode;
        private String custNIC;
        private String returnDate;
        private String orderDate;
        private String eid;
        private String Description;
        private double extraOrderCost;


        public DesignOrder()
        {
            conn = dbConnect.connect();
            
            
            this.designCode =null;
            this.custNIC =null;
            this.returnDate=null;
            this.orderDate=null;
            this.eid=null;
            this.Description=null;
            this.extraOrderCost=0;

        }

        public DesignOrder(String dCode, String cNIC, String rDate, String pEid, String pDes, double ecost)
        {
            this.designCode = dCode;
            this.custNIC =cNIC;
            this.returnDate=rDate;
            this.orderDate=null;
            this.eid=pEid;
            this.Description=pDes;
            this.extraOrderCost= ecost;

        }

        //Set Method
        public void setDesignCode(String dcode)
        {
            this.designCode = dcode;
        }
        
        public void setCustIC(String cnic)
        {
            this.custNIC = cnic;
        }

        public void setReturnDate(String rdate)
        {
            this.returnDate = rdate;
        }
        
        public void setOrderDate(String odate)
        {
            this.orderDate= odate;
        }
        
        public void setTechnician(String teid)
        {
            this.eid = teid;
        }

        public void setOrderDescription(String des)
        {
            this.Description = des;
        }
        
        public void setExtraCost(double ecost)
        {
            this.extraOrderCost = ecost;
        }


         //Set Method
        public String getDesignCode()
        {
            return this.designCode;
        }
        
        public String getCustIC()
        {
            return this.custNIC;
        }

        public String getReturnDate()
        {
            return this.returnDate;
        }
        
        public String getOrderDate()
        {
            return this.orderDate;
        }
        
        public String getTechnician()
        {
            return this.eid;
        }

        public String getOrderDescription()
        {
            return this.Description;
        }
        
        public double getExtraCost()
        {
            return this.extraOrderCost;
        }

             
        
        //Other Methods implimentations
        public double calcTotalDesignOrdercost(double designCost, double extra)
        {
            double tot = designCost + extra;
            return tot;
        }
        
        
        public void notifyUpComingOrders(JLabel lable)
        {
            int count =0;
            String date;
            long daysBetween;
            
            LocalDate currentDate = java.time.LocalDate.now();
            
            try 
            {
                String sql = "SELECT returnDate FROM designorder";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while(rs.next())
                {
                    date = rs.getString("returnDate");
                    
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                    LocalDate newdate =  LocalDate.parse(date, format); 
       
                    if(currentDate.isBefore(newdate))
                    {
                         daysBetween = ChronoUnit.DAYS.between(currentDate, newdate);
                         
                         if(daysBetween < 15)
                         {
                             count++;
                         }
                    }
                }
                
                if(count!=0)
                 {
                     String msg = count + " Up Coming Oders";
                     lable.setText(msg);
                 }
                 else
                 {
                     lable.setText(null);
                 }
                
                
            } 
            catch (Exception e) 
            {
            }
        }
        
        public void searchOnGoingOrders(String custNIC,JTable tableName)
        {
            try 
            {
                String sql = "SELECT * FROM designorder WHERE custNIC like '%"+custNIC+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        public void searchDesignOrders(String custNIC,JTable tableName)
        {
             try 
            {
                String sql = "SELECT designCode,custNIC,returnDate,eid FROM designorder WHERE custNIC like '%"+custNIC+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        
        public void loadDesignOrderHisoryTable(JTable tableName)
        {
            try 
            {
                String sql = "SELECT designCode,custNIC,returnDate,eid FROM designorder";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        public void loadOngoingOrdersTable(JTable tableName)
        {
            try 
            {
                String sql = "SELECT * FROM designorder";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        public void addDesignOrder()
        {
            try 
            {
                String sql = "INSERT INTO designorder(designCode,custNIC,returnDate,orderDate,eid,description,extraCost) VALUE('"+designCode+"','"+custNIC+"','"+returnDate+"','"+orderDate+"','"+eid+"','"+Description+"','"+extraOrderCost+"')";
                pst=conn.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "You have successfully record new design order.");
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
       
        public void updateDesignOrder(String dcode, String nic)
        {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to update the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "UPDATE designorder SET returnDate='"+returnDate+"', eid='"+eid+"', extraCost='"+extraOrderCost+"',description='"+Description+"' WHERE designCode='"+dcode+"' AND custNIC='"+nic+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated design order" + dcode + nic);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        public void removeDesignOrder(String dcode, String nic)
        {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to remove the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "DELETE FROM designorder WHERE designCode='"+dcode+"' AND custNIC='"+nic+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null," Removed Successfully");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    
   
    
   
     
    
    
}
