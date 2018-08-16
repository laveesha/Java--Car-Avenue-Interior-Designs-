
package interiorDesignClasses;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;


public class Design {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs =null;
    
    
    private String designCode;
    private String designType;
    private String vehicleType;
    private double designCost;
    private String imageFile;
    
    public Design()
    {
        conn=dbConnect.connect();
        
        this.designCode =null;
        this.designType=null;
        this.vehicleType=null;
        this.designCost=0;
       // this.designColor[] =null;
    }
    
    public Design(String pdCode, String pdType, String pvType)
    {
        this.designCode = pdCode;
        this.designType=pdType;
        this.vehicleType=pvType;
    }
    
    
    //set methods
    public void setDesignCode(String dcode)
    {
        this.designCode=dcode;
    }
    
    public void setDesignType(String dtype)
    {
        this.designType=dtype;
    }
    
    public void setVehicleType(String vtype)
    {
        this.vehicleType=vtype;
    }
    public void setDesignCost(double dcost)
    {
        this.designCost=dcost;
    }
    public void setDesignImage(String imgfile)
    {
        this.imageFile=imgfile;
    }
    
    
    //set methods
    public String getDesignCode()
    {
        return this.designCode;
    }
    
    public String getDesignType()
    {
        return this.designType;
    }
    
    public String getVehicleType()
    {
        return this.vehicleType;
    }
    
    public double getDesignCost()
    {
        return this.designCost;
    }
    
    public String getDesignImage()
    {
        return this.imageFile;
    }
    
    
    
    //methods
    public double getUpdatedItemCost(String ditemNo,int qty)
    {
        double newCost=0;
        double uPrice;
        
        try 
        {
            String sql1="SELECT unitPrice FROM designitems WHERE itemNo='"+ditemNo+"'";
            pst=conn.prepareStatement(sql1);
            rs = pst.executeQuery();
                
            if(rs.next())
            {
                uPrice = rs.getDouble("unitPrice");   
                newCost= uPrice*qty;
            }
        } 
        catch (Exception e) 
        {
        }
      
        return newCost;
    }
    
    public void updateDesign(String dCode,double newCost)
    {
        try 
                {
                    String sql = "UPDATE design SET designCost='"+newCost+"' WHERE designCode='"+dCode+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated design  "+dCode);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    public void loadDesignTable(JTable tableName)
    {
        try 
            {
               String sql = "SELECT designCode,designType,vehicleType,designCost FROM design";
               //String sql = "SELECT * FROM design";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }        
    }
    
    public void searchDesignResultTable(String vehitype , JTable tableName)
    {
        try 
            {
                String sql = "SELECT designCode,designType,vehicleType,designCost FROM design WHERE vehicleType like '%"+vehitype+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }   
    }
    
    
    public void searchDesignView(String designCode , JTable tableName)
    {
        try 
            {
                String sql = "SELECT designCode,designType,vehicleType,designCost FROM design WHERE designCode like '%"+designCode+"%' ";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }   
    }
            
    public void updateDesign(String dCode)
    {
            int val=JOptionPane.showConfirmDialog(null, "Do you wish to update the record?");
            
            if(val==0)
            {
            
                try 
                {
                    String sql = "UPDATE design SET designCost='"+designCost+"' WHERE designCode='"+dCode+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully updated design  "+dCode);
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }        
    }
    
    
            
    public void addDesign(String fetchpath)
    { 
            try 
            {
                String sql = "INSERT INTO design(designType,vehicleType,designCost,designImage) VALUE(?,?,?,?)";
                pst=conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
                
                InputStream ins = new FileInputStream(new File(fetchpath ));
                
               
                pst.setString(1,designType);
                pst.setString(2, vehicleType);
                pst.setDouble(3, designCost);
                pst.setBinaryStream(4, ins, (int)fetchpath.length());
                
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "You have successfully record new design.");
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e + "Query Error");
            }
    }
    
    public String getLastInsertedDesignCode()
    {
        String dcode =null;
         try 
            {
                String sql = "SELECT designCode FROM design ORDER BY designCode DESC LIMIT 1";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                if(rs.next())
                {
                    dcode = rs.getString("designCode");    
                }
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e + "Query Error");
            }
         return dcode;
    }
            
    public void removeDesign(String dCode)
    {
                try 
                {
                    String sql = "DELETE FROM design WHERE designCode='"+dCode+"' ";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    //JOptionPane.showMessageDialog(null, dCode+" Removed Successfully");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    
            
    
  
    
   
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    //inner class DesignColor
        public static class DesignColor{
            
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs =null;
            
            //private String designColor[];

            public DesignColor() 
            {
                conn= dbConnect.connect();
            }
            
            
            public void loadColorTable(JTable tableName)
            {
                try 
                {
                    String sql = "SELECT * FROM designcolour";
                    pst=conn.prepareStatement(sql);
                    rs =pst.executeQuery();

                    tableName.setModel(DbUtils.resultSetToTableModel(rs));

                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }  
            }
            
            public void searchColorEntry(String dcode, JTable tableName)
            {
                try 
                {
                    String sql = "SELECT * FROM designcolour WHERE designCode like '%"+dcode+"%'";
                    pst=conn.prepareStatement(sql);
                    rs =pst.executeQuery();

                    tableName.setModel(DbUtils.resultSetToTableModel(rs));

                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }  
            }
            
            public String[] getColourArray(String dcode)
            {
                String clr;
                ArrayList<String> colorArr = new ArrayList<>();
                String color[] = new String[colorArr.size()];
                
                try 
                {
                    String sql = "SELECT colour FROM designcolour WHERE designCode='"+dcode+"'";
                    pst=conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                
                    while(rs.next())
                    {
                        clr = rs.getString("colour");
                        colorArr.add(clr);
                        
                        color = colorArr.toArray(color);
                    }
                    
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
                
                return color;
            }
            
            public String[] getSelectedCheckBoxColor(JPanel panel)
            {
                ArrayList<String> colorArr = new ArrayList<>();
        
                for(Component ch : panel.getComponents())
                {
                    if(ch instanceof JCheckBox)
                    {
                        JCheckBox chkbox = (JCheckBox) ch;

                        if(chkbox.isSelected())
                        {
                            colorArr.add(chkbox.getText());
                        }
                    }
                }

                String color[] = new String[colorArr.size()];
                color = colorArr.toArray(color);
                
                return color;
            }
            
//            public void updateDesignColor(String dCode)
//            {
//                int val=JOptionPane.showConfirmDialog(InteriorDesignScreens.ParentPanel, "Do you wish to update the record?");
//
//                if(val==0)
//                {
//
//                    try 
//                    {
//                        String sql = "UPDATE designcolour SET colour='"+designColor+"' WHERE designCode='"+dCode+"' ";
//                        pst=conn.prepareStatement(sql);
//                        pst.execute();
//
//                        JOptionPane.showMessageDialog(InteriorDesignScreens.ParentPanel, "You have successfully updated design  "+dCode);
//                    } 
//                    catch (Exception e) 
//                    {
//                        JOptionPane.showMessageDialog(InteriorDesignScreens.ParentPanel, e);
//                    }
//                } 
//            }
            
            public void addDesignColor(String dCode, String arr[])
            {
                try 
                {
                    for (String clr : arr) {
                        String sql = "INSERT INTO designcolour(designCode,colour) VALUE('"+dCode+"','"+clr+"')";
                        pst=conn.prepareStatement(sql);
                        pst.execute();
                    }
                    //JOptionPane.showMessageDialog(null, "You have successfully record new designColor.");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e + "Add Colour error");
                }
                
           
            }
            
            
            
            public void removeDesignColor(String dCode)
            {
                    try 
                    {
                        String sql = "DELETE FROM designcolour WHERE designCode='"+dCode+"'";
                        pst=conn.prepareStatement(sql);
                        pst.execute();

                        //JOptionPane.showMessageDialog(null, dCode +" Removed Successfully");
                    } 
                    catch (Exception e) 
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
                
            }
            
            public void updateDesignColor(String dcode, JPanel panel)
            {
                    //first remove all the relavant design colors then add again colors
                    this.removeDesignColor(dcode);
                    String color[] = this.getSelectedCheckBoxColor(panel);
                    this.addDesignColor(dcode, color);
            }

        }
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //inner class ItemUsage
        public static class ItemUsage
        {
            
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs =null;
            
            private int usedQty;

            
            public ItemUsage() 
            {
                conn= dbConnect.connect();
            }
            
            
            //set methods
            public void setUsedQuantity(int qty)
            {
                this.usedQty=qty;
            }
            
            //get methods
            public int getUsedQuantity()
            {
                return this.usedQty;
            }
            
            
            public void loadItemUsageTable(JTable tableName)
            {
                try 
                {
                    String sql = "SELECT * FROM itemusage";
                    pst=conn.prepareStatement(sql);
                    rs =pst.executeQuery();

                    tableName.setModel(DbUtils.resultSetToTableModel(rs));

                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }  
            }
            
            public void searchItemUsage(String dcode,JTable tableName)
            {
                try 
                {
                    String sql = "SELECT * FROM itemusage WHERE designCode like '%"+dcode+"%'";
                    pst=conn.prepareStatement(sql);
                    rs =pst.executeQuery();

                    tableName.setModel(DbUtils.resultSetToTableModel(rs));

                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }  
            }
            
            
//            public void searchItemUsageUpdate(String dcode, JTable tableName)
//            {
//                try 
//                {
//                    String sql = "SELECT itemNo,usedQuantity FROM itemusage WHERE designCode like '%"+dcode+"%'";
//                    pst=conn.prepareStatement(sql);
//                    rs =pst.executeQuery();
//
//                    int row = tableName.getRowCount();
//                    while(rs.next())
//                    {
//                        tableName.setValueAt(rs.getString("itemNo"),row,0);
//                        tableName.setValueAt(rs.getString("usedQuantity"),row,2);
//                        row--;
//                    }
//                    tableName.setModel(DbUtils.resultSetToTableModel(rs));
//                } 
//                catch (Exception e) 
//                {
//                    JOptionPane.showMessageDialog(null, e);
//                }  
//            }
//            
            
            
            
            
            
            public void addItemUsage(String dcode, JTable getResultTable)
            { 
                try 
                {
                    for(int i=0; i<getResultTable.getRowCount(); i++)
                    {

                            String itemNo = getResultTable.getValueAt(i, 0).toString();
                            int qty = Integer.parseInt(getResultTable.getValueAt(i, 2).toString());

                            String sql = "INSERT INTO itemusage(designCode,itemNo,usedQuantity) VALUES('"+dcode+"', '"+itemNo+"', '"+qty+"')";
                            pst=conn.prepareStatement(sql);
                            pst.execute();
                    }


                    JOptionPane.showMessageDialog(null, "You have successfully record itemUsage.");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e + "Query Error ItemUsage");
                }
            }
                        
            
            public void removeItemUsage(String dCode)
            {
                        try 
                        {
                            String sql = "DELETE FROM itemUsage WHERE designCode='"+dCode+"'";
                            pst=conn.prepareStatement(sql);
                            pst.execute();

                            //JOptionPane.showMessageDialog(null, dCode +" Removed Successfully");
                        } 
                        catch (Exception e) 
                        {
                            JOptionPane.showMessageDialog(null, e);
                        } 
            }
            
            
        }
    
    
    
    
    
}
