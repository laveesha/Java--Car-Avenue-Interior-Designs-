
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class Profit {
   
   Connection conn = null;
   PreparedStatement pst = null;
   ResultSet rs =null; 
    
   
   DesignSales d1 = new DesignSales();
    
   private int index;
   private double profit;
   private String salesDate;
   private String salesID;
   
   
    public Profit()
    {
        
        conn = dbConnect.connect();
        
        this.index= 0;
        this.profit=0;
        this.salesDate=null;
        this.salesID=null;
    }
    
    
    public Profit(int ino, double profitamount, String date,String salesID)
    {
        this.index= ino;
        this.profit= profitamount;
        this.salesDate= date;
        this.salesID= salesID;
    }
    
    
    //set methods
        public void setIndex(int ino)
        {
                this.index=ino;
        }
        
        public void setSalesProfit(double profitAmt)
        {
                this.profit=profitAmt;
        }
        
        public void setSalesDate(String sdate)
        {
                this.salesDate=sdate;
        }

        public void setSalesID(String sid)
        {
               this.salesID =sid;
        }

        
        //get methods
        public int getIndex()
        {
                return this.index;
        }
        
        public double getSalesProfit()
        {
                return this.profit;
        }
        
        public String getSalesDate()
        {
                return this.salesDate;
        }

        public String getSalesID()
        {
               return this.salesID;
        }
    
    
        
        
    public double calcProfit(double totDesignCost, double salesCost)
    {   
        this.profit = salesCost-totDesignCost;
        
        return  this.profit;
    }
    
    //method overloading
    public double calcProfit(double totDesigncCost)
    {
       this.profit = totDesigncCost*20/100.0;
       return this.profit;
    }
    
    public void loadProfitTable(JTable tableName)
    {
            try 
            {
                String sql = "SELECT * FROM profit";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void addProfit()
    {
            try 
            {
                String sql = "INSERT INTO profit(pIndex,profit,salesDate,salesID) VALUE('"+index+"','"+profit+"','"+salesDate+"','"+salesID+"')";
                pst=conn.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "You have successfully record new profit.");
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void searchProfit(String sdate , JTable tableName)
    {
            try 
            {                
                String sql = "SELECT * FROM profit WHERE salesDate like '%"+sdate+"%' ";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
        public void searchProfitEntry(String salesID, JTable tableName)
    {
            try 
            {                
                String sql = "SELECT * FROM profit WHERE salesID like '%"+salesID+"%'";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }
        
        public String findMonth(String month)
        {
            String date;
                
                switch(month)
                {
                    case "jan" : date ="-01-";
                            break;
                    case "feb" : date ="-02-";
                            break;  
                    case "mar" : date ="-03-";
                            break;
                    case "april" : date="-04-";
                            break;
                    case "may" : date ="-05-";
                            break;
                    case "june" : date ="-06-";
                            break;
                    case "july" : date ="-07-";
                            break;
                    case "aug" : date ="-08-";
                            break;
                    case "sep" : date ="-09-";
                            break;
                    case "oct" : date ="-10-";
                            break;
                    case "nov" : date ="-11-";
                            break;
                    case "dec" : date ="-12-";
                            break;
                    default : date ="Invalid Month";
                }
                
                return date;
            
        }
        
        
         public void displayRecordsByMonthYear(String month,String year,JTable tableName)
            {
                
                String date =this.findMonth(month);
              
                if(!(date.equalsIgnoreCase("Invalid Month")))
                {
                    try 
                    {      
                        String profitDate = year+date;
                        
                        String sql = "SELECT * FROM profit WHERE salesDate like '%"+profitDate+"%'";
                        pst=conn.prepareStatement(sql);
                        rs =pst.executeQuery();

                        tableName.setModel(DbUtils.resultSetToTableModel(rs));

                    } 
                    catch (Exception e) 
                    {
                        JOptionPane.showMessageDialog(null, e);
                    }
                 }
                else
                {
                    JOptionPane.showMessageDialog(null, date);
                    DefaultTableModel model=(DefaultTableModel)tableName.getModel();  
                    while(tableName.getRowCount()>0)
                    {
                        model.removeRow(0);
                    }
                }
         
            }
         
         public boolean isMonthInt(String month)
         {
            boolean result=false ;
            
            try 
            {
                int mon = Integer.parseInt(month);
                
                if(mon>0 && mon<13)
                {
                     result = true;
                }

            } 
            catch (Exception e) 
            {
                result =false;
            }
        
            return result;
        }

    
    
    
    
    
//inner class monthly profit
        public static class MonthlyProfit{
            
           Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs =null;  
            

           private String month;
           private String profitID;
           private double totProfit;


           public MonthlyProfit()
           {
               conn=dbConnect.connect();
               
               this.profitID =null;
               this.month=null;
               this.totProfit=0;
           }

           public MonthlyProfit(String pid, String pmonth, double ptotal)
           {
               this.profitID = pid;
               this.month= pmonth;
               this.totProfit= ptotal;
           }
           
           
           //set methods
           public void setProfitID(String pid)
           {
               this.profitID = pid;
           }
           
           public void setTotalProfit(double profit)
           {
               this.totProfit = profit;
           }
           
           public void setProfitMonth(String month)
           {
               this.month = month;
           }
           
           //get methods
           public String getProfitID()
           {
               return this.profitID;
           }
           
           public double getTotalProfit()
           {
               return this.totProfit;
           }
           
           public String getProfitMonth()
           {
               return this.month;
           }

           
           //other methods
            
            public double calcTotProfit(JTable table, int column)
            {
                double tot=0;
                for(int i=0; i<table.getRowCount(); i++)
                {
                    {
                        double profit = Double.parseDouble(table.getValueAt(i, column).toString());
                        tot = tot + profit;
                    }
                }

                return tot;
            }

            
  
            public void loadMonthlyProfitTable(JTable tableName)
            {
                try 
                {
                String sql = "SELECT * FROM monthlyprofit";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                
                tableName.setModel(DbUtils.resultSetToTableModel(rs));
                
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

            public void addMonthlyProfit()
            {
                try 
                {
                    String sql = "INSERT INTO monthlyprofit(profitID,totalProfit,profitMonth) VALUE('"+profitID+"','"+totProfit+"','"+month+"')";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully record new monthly profit.");
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

            public void searchMonthlyProfit(String month, JTable tableName)
            {
                try 
                {
                    String sql = "SELECT * FROM monthlyprofit WHERE profitMonth like '%"+month+"%' ";
                    pst=conn.prepareStatement(sql);
                    rs =pst.executeQuery();

                    tableName.setModel(DbUtils.resultSetToTableModel(rs));

                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

                        
            public String findProfitMonthString(String month)
            {
                String newMonth=null;
  
                Profit p1 = new Profit();
               
                if(p1.isMonthInt(month))
                {
                    
                    switch(Integer.parseInt(month))
                    {
                        case 1 : newMonth ="Jan";
                                break;
                        case 2 : newMonth ="Feb";
                                break;
                        case 3 : newMonth ="Mar";
                                break;
                        case 4 : newMonth ="April";
                                break;
                        case 5 : newMonth ="May";
                                break;
                        case 6 : newMonth ="June";
                                break;
                        case 7 : newMonth ="July";
                                break;
                        case 8 : newMonth ="Aug";
                                break;
                        case 9 : newMonth ="Sep";
                                break;
                        case 10 : newMonth ="Oct";
                                break;
                        case 11 : newMonth ="Nov";
                                break;
                        case 12 : newMonth ="Dec";
                                break;
                    }
                    
                }
                else
                {
                    newMonth = month;    
                }
                
                return newMonth;
            }

        


        }

    
    
}
