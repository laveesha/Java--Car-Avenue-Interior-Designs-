
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;


public class DesignItem extends Item {
    
    
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs =null;
    
        
        public DesignItem()
        {
            conn = dbConnect.connect();
            
            super.itemNo = null;
            super.itemName = null;
            super.unitPrice = 0;
            super.stockCount =0;
            super.supplierId = null;
//            super()
        }
        
        public DesignItem(String dItemNo, String dItemName, double dUnitPrice, int dSCount, String dSupplier)
        {
            super.itemNo = dItemNo;
            super.itemName = dItemName;
            super.unitPrice = dUnitPrice;
            super.stockCount =dSCount;
            super.supplierId = dSupplier;
           // super(dItemNo,dItemName,dUnitPrice,dSCount,dSupplier);
        }
         
        //Set methods
        public void setDesignItemNo(String pitemNo)
        {
                super.itemNo= pitemNo;
        }
        
        public void setDesignItemName(String pitemName)
        {
                super.itemName= pitemName;
        }
        
        public void setItemUnitPrice(double punitPrice)
        {
                super.unitPrice= punitPrice;
        }

        public void setItemStockCount(int pstockCount)
        {
                super.stockCount= pstockCount;
        }

        public void setItemSupplier(String psupplierId)
        {
                super.supplierId= psupplierId;
        }

        
        //Get methods
        public String getDesignItemNo()
        {
            return itemNo;
        }
        
        public String getDesignItemName()
        {
                return itemName;
        }
        
        public int getItemStockCount()
        {
            return stockCount;
        }
        
        public double getItemUnitPrice()
        {
            return unitPrice;
        }
        
        public String getItemSupplier()
        {
            return supplierId;
        }
        
        
        //Methods
        public void notifyLowQuantity(JLabel lable)
        {
            int count =0;
            int qty;
            try 
            {
                 String sql = "SELECT stockCount FROM designitems";
                 pst =conn.prepareStatement(sql);
                 rs =pst.executeQuery();
                 
                 while(rs.next())
                 {
                     qty = rs.getInt("stockCount");
                     
                     if(qty<15)
                     {
                         count++;
                     }
                 }
                 
                 if(count!=0)
                 {
                     String msg = count + " No of Design Items"
                             + "are out of stock!";
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
       
        
        

        public void loadDesignItemTable(JTable tableName)
        {
           try 
            {
                String sql = "SELECT * FROM designitems";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        
        
        public void loadDesignItemForDesign(JTable tableName)
        {
           try 
            {
                String sql = "SELECT itemNo,itemName,unitPrice FROM designitems";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        public void addDesignItemEntry()
        {
            
            try 
            {
                String sql = "INSERT INTO designitems(itemName,unitPrice,stockCount,supplierCode) VALUE('"+itemName+"','"+unitPrice+"','"+stockCount+"','"+supplierId+"')";
                pst=conn.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "You have successfully record new design item.");
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        
        }
       
        public void updateDesignItem(String ditemNo)
        {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to update the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "UPDATE designitems SET unitPrice='"+unitPrice+"', stockCount='"+stockCount+"', supplierCode='"+supplierId+"' WHERE itemNo='"+ditemNo+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated design item  "+itemNo);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

         public void updateRemainingItemQty(String ditemNo,int usedStock)
        {
                int oldStock=0;
                int newStock;
                try 
                {
                    String sql1="SELECT stockCount FROM designitems WHERE itemNo='"+ditemNo+"'";
                    pst=conn.prepareStatement(sql1);
                    rs = pst.executeQuery();
                
                    if(rs.next())
                    {
                        oldStock = rs.getInt("stockCount");    
                    }
                    
                    newStock = oldStock-usedStock;
                    
                    if(newStock>0)
                    {
                    
                            String sql2 = "UPDATE designitems SET stockCount='"+newStock+"' WHERE itemNo='"+ditemNo+"' ";
                            pst=conn.prepareStatement(sql2);
                            pst.execute();
                    }
                    else
                    {
                        int val =JOptionPane.showConfirmDialog(null,"Out of stock. Do you wish to continue?");
                        if(val==0)
                        {
                            String sql2 = "UPDATE designitems SET stockCount='"+newStock+"' WHERE itemNo='"+ditemNo+"' ";
                            pst=conn.prepareStatement(sql2);
                            pst.execute();
                        }
                    }
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
        }
        
        
        public void removeDesignItem(String ditemNo)
        {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to remove the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "DELETE FROM designitems WHERE itemNo='"+ditemNo+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, itemNo+" Removed Successfully");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        
         
        public void searchItemResultTable(String ditemNo, JTable tableName)
        {
            try 
            {
                String sql = "SELECT * FROM designitems WHERE itemNo like '%"+ditemNo+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        public void searchItemForDesign(String ditemNo, JTable tableName)
        {
            try 
            {
                String sql = "SELECT itemNo,itemName,unitPrice FROM designitems WHERE itemNo like '%"+ditemNo+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        public String findSupplier(String itemNo)
        {
            String supplier = null;
            
            try 
            {
                String sql = "SELECT supplierCode FROM designitems WHERE itemNo='"+itemNo+"'";
                pst = conn.prepareStatement(sql);
                rs=pst.executeQuery();
                
                if(rs.next())
                {
                    supplier = rs.getString("supplierCode");
                }
            } 
            catch (Exception e) 
            {
            }
            
            return supplier;
        }
        
       
}
