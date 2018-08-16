
package interiorDesignClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public abstract class Sales {
    
    protected String salesId;
    protected String salesDate;
    protected String custNIC;
    protected double salesPrice;
    
    Connection conn=null;
    PreparedStatement pst = null;
    ResultSet rs =null;
    
    
    public Sales()
    {
        conn= dbConnect.connect();
        
        this.salesId =null;
        this.salesDate=null;
        this.custNIC= null;
        this.salesPrice=0;
    }
    
    public Sales(String psalesId, String pNIC)
    {
        this.salesId = psalesId;
        this.salesDate= setSalesDate();
        this.custNIC= pNIC;
        
        
    }
       
    //set methods
    public void setSalesID(String sid)
    {
        this.salesId = sid;
    }
    
    public void setSalesDate(String sdate)
    {
        this.salesDate = sdate;
    }
    public void setcustNIC(String nic)
    {
        this.custNIC = nic;
    }
    
    public void setSalesPrice(double sprice)
    {
        this.salesPrice = sprice;
    }
    
    
    //get Methods
    public String getSalesId()
    {
        return this.salesId;
    }
    
    public String getSalesDate()
    {
        return this.salesDate;
    }
    
    public String getcustNIC()
    {
        return this.custNIC;
    }
    
    public double getSalesPrice()
    {
        return this.salesPrice;
    }
    
    
    private String setSalesDate()
    {
        this.salesDate=java.time.LocalDate.now().toString();
        return this.salesDate;
    }
      
}
