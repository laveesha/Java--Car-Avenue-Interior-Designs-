




package interiorDesignClasses;

import com.mysql.jdbc.Connection;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class report {
    
    private JasperReport jr;
    private JasperPrint jp;
    Map JMap = null;
    private FileInputStream file;

    public report() 
    {
    }
    private BufferedInputStream buff;
    private Connection conn;
    
    //constructor
    public report(String reportLocation, Connection dbConnection) throws FileNotFoundException
    {
        this.file = new FileInputStream(reportLocation);
        this.conn = dbConnect.connect();
        this.buff = new BufferedInputStream(this.file);
    }
    
    //use this method to get report using praticular input(String value)
    public void getTextBoxParametes(String HashParameter,String TextBoxParameter)
    {
        this.JMap = new HashMap();
        this.JMap.put(HashParameter, TextBoxParameter);
    }
    
    
    //generate & view report
    public void generateReport()
    {
       try
       {
           this.jr = JasperCompileManager.compileReport(this.buff);
           this.jp = JasperFillManager.fillReport(this.jr, this.JMap, this.conn);
           JasperViewer.viewReport(jp,false);
       }
       catch(Exception e)
       {
           System.out.println("Report Generation Failed : "+e);
       }
        
    }
    

    
}
