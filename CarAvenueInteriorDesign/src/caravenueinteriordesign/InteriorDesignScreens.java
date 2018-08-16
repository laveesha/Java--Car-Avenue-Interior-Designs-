/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caravenueinteriordesign;

import interiorDesignClasses.AdvancePayment;
import interiorDesignClasses.Design;
import interiorDesignClasses.DesignItem;
import interiorDesignClasses.DesignOrder;
import interiorDesignClasses.DesignSales;
import interiorDesignClasses.Email;
import interiorDesignClasses.Payment;
import interiorDesignClasses.Profit;
import interiorDesignClasses.Validate;
import interiorDesignClasses.dbConnect;
import interiorDesignClasses.report;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JDialog;
import javax.swing.UnsupportedLookAndFeelException;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author User
 */
public class InteriorDesignScreens extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs =null;
    
    DesignItem t1 =new DesignItem();
    DesignOrder t2 = new DesignOrder();
    DesignSales ds1 = new DesignSales();
    Payment pt = new Payment();
    AdvancePayment a1 = new AdvancePayment();
    Profit p1 =new Profit();
    Profit.MonthlyProfit mp = new Profit.MonthlyProfit();
    Design d1 =new Design();
    Design.ItemUsage di =new Design.ItemUsage();
    Design.DesignColor dc = new Design.DesignColor();
    
   
    JDialog dialog =new JDialog();
    
    String fetchpath;
    
    /**
     * Creates new form InteriorDesignScreens
     */
    public InteriorDesignScreens() {
        conn = dbConnect.connect();
        
        initComponents();
        
        ManageItemsPanel.setVisible(false);
        DesignOrderPanelScreen.setVisible(false);
        SalesDesignPanel.setVisible(false);
        DesignProfitPanel.setVisible(false);
        
        t1.notifyLowQuantity(lowqtyLable);
        t2.notifyUpComingOrders(closeorderLable);
        
    
          t1.loadDesignItemTable(itemtableDesign);
          t2.loadOngoingOrdersTable(designorderTable);
          t2.loadOngoingOrdersTable(orderTable);
          t2.loadOngoingOrdersTable(onGoingtable);
          ds1.loadDesignSalesTable(salesTable);
          ds1.loadDesignSalesTable(salesTableProfit);
          a1.loadAdvanceTable(advanceTableView);
          p1.loadProfitTable(SalesIncomeTable);
          p1.loadProfitTable(salesProfit);
          mp.loadMonthlyProfitTable(profitTable);
          d1.loadDesignTable(designTable);
          d1.loadDesignTable(designTableProfit);
          pt.loadPaymentTable(paymentTable);
          a1.loadAdvanceTable(viewAdvanceTable);
          di.loadItemUsageTable(itemUsageTable);
          dc.loadColorTable(designColour);
 
          FillComboBox(sql,"supplierCode" ,supplierBox);
          FillComboBox(sql1,"designCode", designcmb);
          
          
        
   }

    
    private void resetDesignItems()
    {
        itemCodelable.setText(null);
        itemNametxt.setText(null);
        unitPricetxt.setText(null);
        qtytxt.setText(null);
        supplierBox.setSelectedIndex(0);
        
    }
    
    private void resetOrder()
    {
        custnictxt.setText(null);
        orderdate.setDate(null);
        returndate.setDate(null);
        desttxt.setText(null);
        extraacostlbl.setText(null);
        techlbl.setText(null);
        designcmb.setSelectedIndex(0);
    }

    private void resetSales()
    {
       viewSalesDatelbl.setText(null); 
       viewNIClbl.setText(null);
       salesPriceTxt.setText(null);
       viewDesignCodelbl.setText(null);
       viewSalesLable.setText(null);
    }
    
    private void resetPayment()
    {
        paymentIDlbl.setText(null);
        fullpaidDatelbl.setText(null);
        amountlbl.setText(null);
        custnic.setText(null);
    }
    
     private void resetAdvancePayment()
    {
        advancePayview.setText(null);
        viewPaidDate.setText(null);
        advanceUpdatetxt.setText(null);
        balanceUpdate.setText(String.valueOf(0));
        viewcustNic.setText(null);
    }
    
    private void resetProfit()
    {
        salesDatelbl.setText(null);
        salesID.setText(null);
        profitlbl.setText(null);
        
        ecostLable.setText(null);
        totcostLable.setText(null);
        salesIncomeLable.setText(null);
    }
    
    private void resetMonthlyProfit()
    {
        profitAmount.setText(null);
        monthtxt.setText(null);
        profitIDLable.setText(null);
    }
    
    private void resetDesign()
    {
        designCodelable.setText(null);
        designComboBox.setSelectedIndex(0);
        vehicleTypeComboBox.setSelectedIndex(0);
        imageFiletxt.setText(null);
        totDesignCostlable.setText(null);
        imagelable.setIcon(null);
        
        DefaultTableModel model=(DefaultTableModel)additemUsageTable.getModel();  
        while(additemUsageTable.getRowCount()>0)
        {
            model.removeRow(0);
        }
        
        bluechk.setSelected(false);
        redchk.setSelected(false);
        blackchk.setSelected(false);
        goldchk.setSelected(false);
        brownchk.setSelected(false);
        pearlchk.setSelected(false);
    }
    
    private void resetEmail()
    {
        emailRecieverlbl.setText(null);
        subjecttxt.setText(null);
        msgbodyarea.setText(null);
    }
    
    private String getSystemDate()
    {
        return java.time.LocalDate.now().toString();
    }
    
    private Date setStringtoDate(String sdate)
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate newdate =  LocalDate.parse(sdate, format);
        
        Date date = java.sql.Date.valueOf(newdate);
      
        return date;
    }
    
    private double getDecimal(double no)
    {
        DecimalFormat def = new DecimalFormat("#.00");
        double newNo = Double.valueOf(def.format(no));
        return newNo;
    }
    
    private void FillComboBox(String sql ,String column, JComboBox cmbBox)
       {
           try 
           {
               pst= conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               String defaultitem = cmbBox.getItemAt(0).toString();
               cmbBox.removeAllItems();
               cmbBox.addItem(defaultitem);
               
               while(rs.next())
               {
                   
                   String fclass = rs.getString(column);
                   cmbBox.addItem(fclass);
                   cmbBox.setVisible(true);
                   
               }
               
           } 
           catch (Exception e) 
           {
               JOptionPane.showMessageDialog(null, e);
           }
       }

     String sql = "SELECT * FROM supplier";
     String sql1 ="SELECT * FROM design";
         
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SidebarPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        notificationPanel = new javax.swing.JPanel();
        lowqtyLable = new javax.swing.JLabel();
        closeorderLable = new javax.swing.JLabel();
        ParentPanel = new javax.swing.JPanel();
        ManageItemsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        itemSearchtxt = new javax.swing.JTextField();
        itemSearchbtn = new javax.swing.JButton();
        jScrollPane29 = new javax.swing.JScrollPane();
        itemtableDesign = new javax.swing.JTable();
        remainingQtybtn1 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        itemCodelable = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        itemNametxt = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        unitPricetxt = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        qtytxt = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        supplierBox = new javax.swing.JComboBox<>();
        addItembtn = new javax.swing.JButton();
        itemUpdatebtn = new javax.swing.JButton();
        removeItembtn = new javax.swing.JButton();
        resetItembtn = new javax.swing.JButton();
        DesignOrderPanelScreen = new javax.swing.JPanel();
        DesignOrderTabbedPane = new javax.swing.JTabbedPane();
        InteriorDesignPane = new javax.swing.JPanel();
        designPanel = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        designSearch = new javax.swing.JTextField();
        jButton39 = new javax.swing.JButton();
        jLabel125 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jScrollPane30 = new javax.swing.JScrollPane();
        designColour = new javax.swing.JTable();
        jScrollPane18 = new javax.swing.JScrollPane();
        itemUsageTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        designTable = new javax.swing.JTable();
        jButton29 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        designCodelable = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        designComboBox = new javax.swing.JComboBox<>();
        jLabel129 = new javax.swing.JLabel();
        vehicleTypeComboBox = new javax.swing.JComboBox<>();
        jLabel134 = new javax.swing.JLabel();
        imageFiletxt = new javax.swing.JTextField();
        imagelable = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        itemNoSearch = new javax.swing.JTextField();
        itemusageSearchBtn = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        additemUsageTable = new javax.swing.JTable();
        jLabel131 = new javax.swing.JLabel();
        removeDesignbtn = new javax.swing.JButton();
        updateDesignbtn = new javax.swing.JButton();
        addRowBtn = new javax.swing.JButton();
        jScrollPane24 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jButton57 = new javax.swing.JButton();
        addDesignbtn = new javax.swing.JButton();
        removeRowBtn = new javax.swing.JButton();
        attachbtn = new javax.swing.JButton();
        resetDesign = new javax.swing.JButton();
        checkboxPanel = new javax.swing.JPanel();
        bluechk = new javax.swing.JCheckBox();
        blackchk = new javax.swing.JCheckBox();
        redchk = new javax.swing.JCheckBox();
        pearlchk = new javax.swing.JCheckBox();
        goldchk = new javax.swing.JCheckBox();
        brownchk = new javax.swing.JCheckBox();
        totDesignCostlable = new javax.swing.JLabel();
        DesignOrderPane = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        onGogingSearch = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        designcmb = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        custnictxt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        orderdate = new com.toedter.calendar.JDateChooser();
        jLabel32 = new javax.swing.JLabel();
        returndate = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        desttxt = new javax.swing.JTextArea();
        jLabel136 = new javax.swing.JLabel();
        techlbl = new javax.swing.JLabel();
        jButton45 = new javax.swing.JButton();
        jLabel108 = new javax.swing.JLabel();
        extraacostlbl = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane36 = new javax.swing.JScrollPane();
        onGoingtable = new javax.swing.JTable();
        orderUpdatebtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        AdvancePayPane = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        viewAdvanceSearch = new javax.swing.JTextField();
        jButton28 = new javax.swing.JButton();
        jLabel97 = new javax.swing.JLabel();
        advancePayview = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        viewPaidDate = new javax.swing.JLabel();
        viewcustNic = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        balanceUpdate = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        advanceUpdatetxt = new javax.swing.JTextField();
        jButton24 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        viewAdvanceTable = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        SalesDesignPanel = new javax.swing.JPanel();
        DesignSalesTabbedPanel = new javax.swing.JTabbedPane();
        DesignOrdersViewPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jLabel68 = new javax.swing.JLabel();
        designOrderSearch = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        designTable2 = new javax.swing.JTable();
        jLabel71 = new javax.swing.JLabel();
        dcLable = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        niclable = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        dcostLable = new javax.swing.JLabel();
        extraLable = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        sellButton = new javax.swing.JButton();
        orderSearchbtn = new javax.swing.JButton();
        SalesPanel = new javax.swing.JPanel();
        saleslable = new javax.swing.JLabel();
        salesSearch = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        viewSalesLable = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        viewSalesDatelbl = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        viewDesignCodelbl = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        viewNIClbl = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        salesPriceTxt = new javax.swing.JTextField();
        addSalesbtn = new javax.swing.JButton();
        resetSalesbtn = new javax.swing.JButton();
        salesSearchbtn = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        salesTable = new javax.swing.JTable();
        updateSalesbtn = new javax.swing.JButton();
        jLabel80 = new javax.swing.JLabel();
        serviceChargetxt = new javax.swing.JTextField();
        PaymentPane = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        viewFullPaySearch = new javax.swing.JTextField();
        paymentSearchbtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        advanceSearch = new javax.swing.JTextField();
        advanceSearchbtn = new javax.swing.JButton();
        jLabel92 = new javax.swing.JLabel();
        paymentIDlbl = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        fullpaidDatelbl = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        amountlbl = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        custnic = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        advanceTableView = new javax.swing.JTable();
        settlePaymentbtn = new javax.swing.JButton();
        removePaymentbtn = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        DesignProfitPanel = new javax.swing.JPanel();
        DesignOrderTabbedPane1 = new javax.swing.JTabbedPane();
        InteriorDesignPane1 = new javax.swing.JPanel();
        designPanel1 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        profitdesignsearch = new javax.swing.JTextField();
        profitAddSearchbtn = new javax.swing.JButton();
        jScrollPane38 = new javax.swing.JScrollPane();
        designorderTable = new javax.swing.JTable();
        jScrollPane39 = new javax.swing.JScrollPane();
        designTableProfit = new javax.swing.JTable();
        jScrollPane40 = new javax.swing.JScrollPane();
        salesTableProfit = new javax.swing.JTable();
        jLabel124 = new javax.swing.JLabel();
        indexlbl = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        profitlbl = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        salesDatelbl = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        salesID = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        ecostLable = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        salesIncomeLable = new javax.swing.JLabel();
        addProfitbtn = new javax.swing.JButton();
        jLabel140 = new javax.swing.JLabel();
        totcostLable = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel170 = new javax.swing.JLabel();
        designprofitSearchtxt = new javax.swing.JTextField();
        profitSearchbtn = new javax.swing.JButton();
        jScrollPane41 = new javax.swing.JScrollPane();
        salesProfit = new javax.swing.JTable();
        designprofitViewPanel = new javax.swing.JPanel();
        jLabel171 = new javax.swing.JLabel();
        indexlbl1 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        profitIdLbl1 = new javax.swing.JLabel();
        jLabel173 = new javax.swing.JLabel();
        salesDatelbl1 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        salesID1 = new javax.swing.JLabel();
        DesignOrderPane1 = new javax.swing.JPanel();
        jLabel148 = new javax.swing.JLabel();
        profitSearchtxt = new javax.swing.JTextField();
        monthlyProfitbtn = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        profitTable = new javax.swing.JTable();
        jButton53 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel150 = new javax.swing.JLabel();
        yearsearch = new javax.swing.JTextField();
        salesincomeSearchbtn = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        SalesIncomeTable = new javax.swing.JTable();
        profitCalcPanel = new javax.swing.JPanel();
        jLabel151 = new javax.swing.JLabel();
        profitAmount = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        profitIDLable = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        monthtxt = new javax.swing.JTextField();
        jButton55 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        monthsearch = new javax.swing.JTextField();
        jLabel155 = new javax.swing.JLabel();
        assignTechnicianPanel = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        empSearch = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        empTable = new javax.swing.JTable();
        technicianPanel1 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        eidlable = new javax.swing.JLabel();
        fameLbl = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lnameLbl = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        phonelbl = new javax.swing.JLabel();
        assignTechnicianBtn = new javax.swing.JButton();
        exitTechnicianDialog = new javax.swing.JButton();
        remainQuantityPanel = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        itemSearchRemain = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        jLabel154 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        remainQtyTable = new javax.swing.JTable();
        exitRemainQty = new javax.swing.JButton();
        contactSupplierBtn = new javax.swing.JButton();
        supplierContact = new javax.swing.JPanel();
        supplierRecordPanel1 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        scodeLable = new javax.swing.JLabel();
        companyNamelbl = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        fnameLable = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        lnameLable = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        phonelable = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        emailSupplier = new javax.swing.JButton();
        ExitSupplierContact = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        emailLable = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        supplierSearch = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        supplierTable = new javax.swing.JTable();
        emailPanel = new javax.swing.JPanel();
        emailRecieverlbl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        subjecttxt = new javax.swing.JTextField();
        emailSendBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgbodyarea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Car Avenue Interior Designing");

        SidebarPanel.setBackground(new java.awt.Color(51, 51, 51));
        SidebarPanel.setPreferredSize(new java.awt.Dimension(311, 325));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Order Design");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Design Sales");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Design Items & Materials");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Profit Handling");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/FB_IMG_1506112010423.jpg"))); // NOI18N

        notificationPanel.setBackground(new java.awt.Color(51, 51, 51));
        notificationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notification", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        lowqtyLable.setFont(lowqtyLable.getFont().deriveFont(lowqtyLable.getFont().getStyle() | java.awt.Font.BOLD));
        lowqtyLable.setForeground(new java.awt.Color(255, 255, 51));

        closeorderLable.setFont(closeorderLable.getFont().deriveFont(closeorderLable.getFont().getStyle() | java.awt.Font.BOLD));
        closeorderLable.setForeground(new java.awt.Color(255, 255, 51));

        javax.swing.GroupLayout notificationPanelLayout = new javax.swing.GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowqtyLable, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeorderLable, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        notificationPanelLayout.setVerticalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lowqtyLable, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closeorderLable, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SidebarPanelLayout = new javax.swing.GroupLayout(SidebarPanel);
        SidebarPanel.setLayout(SidebarPanelLayout);
        SidebarPanelLayout.setHorizontalGroup(
            SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarPanelLayout.createSequentialGroup()
                .addGroup(SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SidebarPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SidebarPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(notificationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        SidebarPanelLayout.setVerticalGroup(
            SidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarPanelLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(notificationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1227, Short.MAX_VALUE))
        );

        ParentPanel.setMaximumSize(new java.awt.Dimension(1920, 9970));

        ManageItemsPanel.setMaximumSize(new java.awt.Dimension(1280, 1080));
        ManageItemsPanel.setPreferredSize(new java.awt.Dimension(1280, 1080));

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize()+1));
        jLabel2.setText("Manage Design Items");

        jLabel34.setFont(jLabel34.getFont().deriveFont(jLabel34.getFont().getSize()-1f));
        jLabel34.setText("Item No");

        itemSearchtxt.setFont(itemSearchtxt.getFont().deriveFont(itemSearchtxt.getFont().getSize()-1f));

        itemSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        itemSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSearchbtnActionPerformed(evt);
            }
        });

        itemtableDesign.setFont(itemtableDesign.getFont().deriveFont(itemtableDesign.getFont().getSize()-1f));
        itemtableDesign.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "itemNo", "itemName", "unitPrice", "stockCount", "suppllierId"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemtableDesign.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemtableDesignMouseClicked(evt);
            }
        });
        jScrollPane29.setViewportView(itemtableDesign);

        remainingQtybtn1.setFont(remainingQtybtn1.getFont());
        remainingQtybtn1.setText("Remaining Quantity");
        remainingQtybtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remainingQtybtn1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(jLabel26.getFont().deriveFont(jLabel26.getFont().getSize()-1f));
        jLabel26.setText("ItemNo");

        itemCodelable.setFont(itemCodelable.getFont().deriveFont(itemCodelable.getFont().getSize()-1f));

        jLabel36.setFont(jLabel36.getFont().deriveFont(jLabel36.getFont().getSize()-1f));
        jLabel36.setText("Item Name");

        itemNametxt.setFont(itemNametxt.getFont().deriveFont(itemNametxt.getFont().getSize()-1f));

        jLabel38.setFont(jLabel38.getFont().deriveFont(jLabel38.getFont().getSize()-1f));
        jLabel38.setText("Unit Price");

        unitPricetxt.setFont(unitPricetxt.getFont().deriveFont(unitPricetxt.getFont().getSize()-1f));

        jLabel43.setFont(jLabel43.getFont().deriveFont(jLabel43.getFont().getSize()-1f));
        jLabel43.setText("StockCount");

        qtytxt.setFont(qtytxt.getFont().deriveFont(qtytxt.getFont().getSize()-1f));

        jLabel44.setFont(jLabel44.getFont().deriveFont(jLabel44.getFont().getSize()-1f));
        jLabel44.setText("SupplierID");

        supplierBox.setFont(supplierBox.getFont().deriveFont(supplierBox.getFont().getSize()-1f));
        supplierBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Supplier" }));

        addItembtn.setFont(addItembtn.getFont());
        addItembtn.setText("Add");
        addItembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItembtnActionPerformed(evt);
            }
        });

        itemUpdatebtn.setFont(itemUpdatebtn.getFont());
        itemUpdatebtn.setText("Update");
        itemUpdatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUpdatebtnActionPerformed(evt);
            }
        });

        removeItembtn.setFont(removeItembtn.getFont());
        removeItembtn.setText("Remove");
        removeItembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItembtnActionPerformed(evt);
            }
        });

        resetItembtn.setText("Reset");
        resetItembtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetItembtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ManageItemsPanelLayout = new javax.swing.GroupLayout(ManageItemsPanel);
        ManageItemsPanel.setLayout(ManageItemsPanelLayout);
        ManageItemsPanelLayout.setHorizontalGroup(
            ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageItemsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(itemCodelable, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(itemNametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                                        .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(unitPricetxt)
                                            .addComponent(qtytxt)
                                            .addComponent(supplierBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(remainingQtybtn1)
                                    .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(itemSearchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(itemSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                                .addComponent(addItembtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemUpdatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeItembtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetItembtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(335, 335, 335))
        );
        ManageItemsPanelLayout.setVerticalGroup(
            ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ManageItemsPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itemSearchbtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(itemSearchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane29, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remainingQtybtn1)
                .addGap(8, 8, 8)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemCodelable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ManageItemsPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(itemNametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitPricetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qtytxt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplierBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(ManageItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeItembtn)
                    .addComponent(itemUpdatebtn)
                    .addComponent(addItembtn)
                    .addComponent(resetItembtn))
                .addGap(284, 284, 284))
        );

        DesignOrderPanelScreen.setMaximumSize(new java.awt.Dimension(1280, 1080));
        DesignOrderPanelScreen.setPreferredSize(new java.awt.Dimension(1280, 1080));

        DesignOrderTabbedPane.setFont(DesignOrderTabbedPane.getFont().deriveFont(DesignOrderTabbedPane.getFont().getStyle() | java.awt.Font.BOLD, DesignOrderTabbedPane.getFont().getSize()+1));

        InteriorDesignPane.setFont(InteriorDesignPane.getFont().deriveFont((float)17));

        designPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Interior Designs")), "Interior Designs"));

        jLabel123.setFont(jLabel123.getFont().deriveFont(jLabel123.getFont().getSize()-1f));
        jLabel123.setText("Vehicle Type");

        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jLabel125.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel125.setText("Item Usage");

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel128.setText("Design Colours");

        designColour.setFont(designColour.getFont().deriveFont(designColour.getFont().getStyle() & ~java.awt.Font.BOLD, designColour.getFont().getSize()-1));
        designColour.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "designCode", "colour"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane30.setViewportView(designColour);

        itemUsageTable.setFont(itemUsageTable.getFont().deriveFont(itemUsageTable.getFont().getStyle() & ~java.awt.Font.BOLD, itemUsageTable.getFont().getSize()-1));
        itemUsageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "designCode", "itemNo", "usedQuantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane18.setViewportView(itemUsageTable);

        designTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "designCode", "designType", "vehicleType", "designCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        designTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                designTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(designTable);

        jButton29.setText("Order Design");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout designPanelLayout = new javax.swing.GroupLayout(designPanel);
        designPanel.setLayout(designPanelLayout);
        designPanelLayout.setHorizontalGroup(
            designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, designPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, designPanelLayout.createSequentialGroup()
                .addGroup(designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(designPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(designPanelLayout.createSequentialGroup()
                                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(221, 221, 221))
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(designPanelLayout.createSequentialGroup()
                                .addGroup(designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(designPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel123)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(designSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(designPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(84, 84, 84))
        );
        designPanelLayout.setVerticalGroup(
            designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(designPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(designPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(designSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton29)
                .addGap(31, 31, 31))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Manage Designs"));

        designCodelable.setFont(designCodelable.getFont().deriveFont(designCodelable.getFont().getStyle() & ~java.awt.Font.BOLD, designCodelable.getFont().getSize()-1));

        jLabel126.setFont(jLabel126.getFont().deriveFont(jLabel126.getFont().getSize()-1f));
        jLabel126.setText("Design Code");

        jLabel127.setFont(jLabel127.getFont().deriveFont(jLabel127.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel127.getFont().getSize()-1));
        jLabel127.setText("Design Type");

        designComboBox.setFont(designComboBox.getFont().deriveFont(designComboBox.getFont().getStyle() & ~java.awt.Font.BOLD, designComboBox.getFont().getSize()-1));
        designComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose  Design Type", "Sports Type", "Luxury Type", "Normal" }));

        jLabel129.setFont(jLabel129.getFont().deriveFont(jLabel129.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel129.getFont().getSize()-1));
        jLabel129.setText("Vehicle Type");

        vehicleTypeComboBox.setFont(vehicleTypeComboBox.getFont().deriveFont(vehicleTypeComboBox.getFont().getStyle() & ~java.awt.Font.BOLD, vehicleTypeComboBox.getFont().getSize()-1));
        vehicleTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Vehicle Type", "Sedan", "Hatchback", "SUV", "MV4", "Van" }));

        jLabel134.setFont(jLabel134.getFont().deriveFont(jLabel134.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel134.getFont().getSize()-1));
        jLabel134.setText("Upload Image File ");

        imageFiletxt.setFont(imageFiletxt.getFont().deriveFont(imageFiletxt.getFont().getStyle() & ~java.awt.Font.BOLD, imageFiletxt.getFont().getSize()-1));

        jLabel149.setFont(jLabel149.getFont().deriveFont(jLabel149.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel149.getFont().getSize()-1));
        jLabel149.setText("Available Colours");

        jLabel130.setFont(jLabel130.getFont().deriveFont(jLabel130.getFont().getStyle() & ~java.awt.Font.BOLD));
        jLabel130.setText("Item Usage");

        jLabel132.setFont(jLabel132.getFont().deriveFont(jLabel132.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel132.getFont().getSize()-1));
        jLabel132.setText("Item No");

        itemNoSearch.setFont(itemNoSearch.getFont().deriveFont(itemNoSearch.getFont().getStyle() & ~java.awt.Font.BOLD, itemNoSearch.getFont().getSize()-1));

        itemusageSearchBtn.setFont(itemusageSearchBtn.getFont().deriveFont(itemusageSearchBtn.getFont().getStyle() & ~java.awt.Font.BOLD, itemusageSearchBtn.getFont().getSize()-1));
        itemusageSearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        itemusageSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemusageSearchBtnActionPerformed(evt);
            }
        });

        additemUsageTable.setFont(additemUsageTable.getFont().deriveFont(additemUsageTable.getFont().getStyle() & ~java.awt.Font.BOLD, additemUsageTable.getFont().getSize()-1));
        additemUsageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "itemNo", "unitPrice", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane20.setViewportView(additemUsageTable);

        jLabel131.setFont(jLabel131.getFont().deriveFont(jLabel131.getFont().getStyle() & ~java.awt.Font.BOLD, jLabel131.getFont().getSize()-1));
        jLabel131.setText("Design Cost");

        removeDesignbtn.setFont(removeDesignbtn.getFont());
        removeDesignbtn.setText("Remove");
        removeDesignbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDesignbtnActionPerformed(evt);
            }
        });

        updateDesignbtn.setFont(updateDesignbtn.getFont());
        updateDesignbtn.setText("Update");
        updateDesignbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDesignbtnActionPerformed(evt);
            }
        });

        addRowBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Add-icon.png"))); // NOI18N
        addRowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowBtnActionPerformed(evt);
            }
        });

        itemTable.setFont(itemTable.getFont().deriveFont(itemTable.getFont().getStyle() & ~java.awt.Font.BOLD, itemTable.getFont().getSize()-1));
        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "itemNo", "itemName", "unitPrice"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemTableMouseClicked(evt);
            }
        });
        jScrollPane24.setViewportView(itemTable);

        jButton57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/161-calculator-icon.png"))); // NOI18N
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        addDesignbtn.setText("Add Design");
        addDesignbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDesignbtnActionPerformed(evt);
            }
        });

        removeRowBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Remove-icon.png"))); // NOI18N
        removeRowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowBtnActionPerformed(evt);
            }
        });

        attachbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Editing-Attach-icon.png"))); // NOI18N
        attachbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachbtnActionPerformed(evt);
            }
        });

        resetDesign.setText("Reset");
        resetDesign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetDesignActionPerformed(evt);
            }
        });

        bluechk.setFont(bluechk.getFont().deriveFont(bluechk.getFont().getSize()-1f));
        bluechk.setText("Blue");

        blackchk.setFont(blackchk.getFont().deriveFont(blackchk.getFont().getSize()-1f));
        blackchk.setText("Black");

        redchk.setFont(redchk.getFont().deriveFont(redchk.getFont().getSize()-1f));
        redchk.setText("Red");

        pearlchk.setFont(pearlchk.getFont().deriveFont(pearlchk.getFont().getSize()-1f));
        pearlchk.setText("Pearl");

        goldchk.setFont(goldchk.getFont().deriveFont(goldchk.getFont().getSize()-1f));
        goldchk.setText("Gold");

        brownchk.setFont(brownchk.getFont().deriveFont(brownchk.getFont().getSize()-1f));
        brownchk.setText("Brown");

        javax.swing.GroupLayout checkboxPanelLayout = new javax.swing.GroupLayout(checkboxPanel);
        checkboxPanel.setLayout(checkboxPanelLayout);
        checkboxPanelLayout.setHorizontalGroup(
            checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkboxPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bluechk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pearlchk, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blackchk, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(goldchk, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(redchk, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brownchk)))
        );
        checkboxPanelLayout.setVerticalGroup(
            checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkboxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(checkboxPanelLayout.createSequentialGroup()
                        .addComponent(blackchk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goldchk))
                    .addGroup(checkboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(checkboxPanelLayout.createSequentialGroup()
                            .addComponent(bluechk)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pearlchk))
                        .addGroup(checkboxPanelLayout.createSequentialGroup()
                            .addComponent(redchk)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(brownchk))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        totDesignCostlable.setFont(totDesignCostlable.getFont().deriveFont(totDesignCostlable.getFont().getSize()-1f));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(resetDesign)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addDesignbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateDesignbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeDesignbtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(itemNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(itemusageSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addRowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(removeRowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(totDesignCostlable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkboxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(designComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(designCodelable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(vehicleTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(imagelable, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(imageFiletxt, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(attachbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(designCodelable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(designComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vehicleTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel134)
                        .addComponent(attachbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(imageFiletxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagelable, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkboxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itemusageSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(itemNoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(addRowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeRowBtn)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel131, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton57, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(totDesignCostlable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeDesignbtn)
                    .addComponent(updateDesignbtn)
                    .addComponent(addDesignbtn)
                    .addComponent(resetDesign))
                .addContainerGap())
        );

        javax.swing.GroupLayout InteriorDesignPaneLayout = new javax.swing.GroupLayout(InteriorDesignPane);
        InteriorDesignPane.setLayout(InteriorDesignPaneLayout);
        InteriorDesignPaneLayout.setHorizontalGroup(
            InteriorDesignPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InteriorDesignPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(designPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        InteriorDesignPaneLayout.setVerticalGroup(
            InteriorDesignPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InteriorDesignPaneLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(InteriorDesignPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(designPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        DesignOrderTabbedPane.addTab("Interior Designs    ", InteriorDesignPane);

        jLabel37.setFont(jLabel37.getFont().deriveFont(jLabel37.getFont().getSize()-1f));
        jLabel37.setText("Customer NIC");

        onGogingSearch.setFont(onGogingSearch.getFont().deriveFont(onGogingSearch.getFont().getSize()-1f));

        jLabel27.setFont(jLabel27.getFont().deriveFont(jLabel27.getFont().getSize()-1f));
        jLabel27.setText("Design Code");

        designcmb.setFont(designcmb.getFont().deriveFont(designcmb.getFont().getSize()-1f));
        designcmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Design", "d1", "d2", "d3" }));

        jLabel28.setFont(jLabel28.getFont().deriveFont(jLabel28.getFont().getSize()-1f));
        jLabel28.setText("Customer NIC");

        custnictxt.setFont(custnictxt.getFont().deriveFont(custnictxt.getFont().getSize()-1f));

        jLabel29.setFont(jLabel29.getFont().deriveFont(jLabel29.getFont().getSize()-1f));
        jLabel29.setText("Order Date");

        orderdate.setDateFormatString("yyyy-MM-dd");
        orderdate.setFont(orderdate.getFont().deriveFont(orderdate.getFont().getSize()-1f));

        jLabel32.setFont(jLabel32.getFont().deriveFont(jLabel32.getFont().getSize()-1f));
        jLabel32.setText("Return Date");

        returndate.setDateFormatString("yyyy-MM-dd");
        returndate.setFont(returndate.getFont().deriveFont(returndate.getFont().getSize()-1f));

        jLabel31.setFont(jLabel31.getFont().deriveFont(jLabel31.getFont().getSize()-1f));
        jLabel31.setText("Description");

        desttxt.setColumns(20);
        desttxt.setFont(desttxt.getFont().deriveFont(desttxt.getFont().getSize()-1f));
        desttxt.setRows(5);
        jScrollPane22.setViewportView(desttxt);

        jLabel136.setFont(jLabel136.getFont().deriveFont(jLabel136.getFont().getSize()-1f));
        jLabel136.setText("Assigned Technician");

        techlbl.setFont(techlbl.getFont().deriveFont(techlbl.getFont().getSize()-1f));

        jButton45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Tools-icon.png"))); // NOI18N
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel108.setFont(jLabel108.getFont().deriveFont(jLabel108.getFont().getSize()-1f));
        jLabel108.setText("Extra Order Cost");

        extraacostlbl.setFont(extraacostlbl.getFont().deriveFont(extraacostlbl.getFont().getSize()-1f));

        jButton3.setFont(jButton3.getFont());
        jButton3.setText("Order Design");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        onGoingtable.setFont(onGoingtable.getFont().deriveFont(onGoingtable.getFont().getSize()-1f));
        onGoingtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "designCode", "custNIC", "returnDate", "orderDate", "eid", "description", "extraCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        onGoingtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onGoingtableMouseClicked(evt);
            }
        });
        jScrollPane36.setViewportView(onGoingtable);

        orderUpdatebtn.setText("Update");
        orderUpdatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderUpdatebtnActionPerformed(evt);
            }
        });

        jButton6.setText("Remove");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton9.setText("Reset");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DesignOrderPaneLayout = new javax.swing.GroupLayout(DesignOrderPane);
        DesignOrderPane.setLayout(DesignOrderPaneLayout);
        DesignOrderPaneLayout.setHorizontalGroup(
            DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane36, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(onGogingSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                            .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(designcmb, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(custnictxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(returndate, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(orderdate, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                            .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(extraacostlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                        .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(techlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(205, 205, 205)
                                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(orderUpdatebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(270, Short.MAX_VALUE))))
        );
        DesignOrderPaneLayout.setVerticalGroup(
            DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(onGogingSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane36, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(designcmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(custnictxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(orderdate, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returndate, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(techlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel136, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DesignOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(extraacostlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DesignOrderPaneLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderUpdatebtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        DesignOrderTabbedPane.addTab("Order Design    ", DesignOrderPane);

        jLabel96.setFont(jLabel96.getFont().deriveFont(jLabel96.getFont().getSize()-1f));
        jLabel96.setText("Payment ID");

        viewAdvanceSearch.setFont(viewAdvanceSearch.getFont().deriveFont(viewAdvanceSearch.getFont().getSize()-1f));

        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jLabel97.setFont(jLabel97.getFont().deriveFont(jLabel97.getFont().getSize()-1f));
        jLabel97.setText("Payment ID");

        advancePayview.setFont(advancePayview.getFont().deriveFont(advancePayview.getFont().getSize()-1f));

        jLabel98.setFont(jLabel98.getFont().deriveFont(jLabel98.getFont().getSize()-1f));
        jLabel98.setText("Payment Date");

        viewPaidDate.setFont(viewPaidDate.getFont().deriveFont(viewPaidDate.getFont().getSize()-1f));

        viewcustNic.setFont(viewcustNic.getFont().deriveFont(viewcustNic.getFont().getSize()-1f));

        jLabel101.setFont(jLabel101.getFont().deriveFont(jLabel101.getFont().getSize()-1f));
        jLabel101.setText("Customer NIC");

        jLabel100.setFont(jLabel100.getFont().deriveFont(jLabel100.getFont().getSize()-1f));
        jLabel100.setText("Balance");

        balanceUpdate.setFont(balanceUpdate.getFont().deriveFont(balanceUpdate.getFont().getSize()-1f));

        jLabel99.setFont(jLabel99.getFont().deriveFont(jLabel99.getFont().getSize()-1f));
        jLabel99.setText("Advance Amount");

        advanceUpdatetxt.setFont(advanceUpdatetxt.getFont().deriveFont(advanceUpdatetxt.getFont().getSize()-1f));

        jButton24.setFont(jButton24.getFont());
        jButton24.setText("Receipt");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        viewAdvanceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "paymentID", "advance", "paymentDate", "balance", "NIC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        viewAdvanceTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewAdvanceTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(viewAdvanceTable);

        jButton13.setText("Update");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton17.setText("Remove");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton2.setText("Pay Advance");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton11.setText("Reset");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdvancePayPaneLayout = new javax.swing.GroupLayout(AdvancePayPane);
        AdvancePayPane.setLayout(AdvancePayPaneLayout);
        AdvancePayPaneLayout.setHorizontalGroup(
            AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                        .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewAdvanceSearch)
                        .addGap(18, 18, 18)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                                .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(advancePayview, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                                            .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(viewPaidDate, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(viewcustNic, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AdvancePayPaneLayout.createSequentialGroup()
                                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(balanceUpdate))))
                                .addGap(119, 119, 119))
                            .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(advanceUpdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116)))
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane8))
                .addGap(389, 389, 389))
        );
        AdvancePayPaneLayout.setVerticalGroup(
            AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(viewAdvanceSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                        .addGap(485, 485, 485)
                        .addComponent(jButton2)
                        .addGap(7, 7, 7)
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24))
                    .addGroup(AdvancePayPaneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(advancePayview, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewPaidDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(advanceUpdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(balanceUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AdvancePayPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewcustNic, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DesignOrderTabbedPane.addTab("Advace Payment    ", AdvancePayPane);

        javax.swing.GroupLayout DesignOrderPanelScreenLayout = new javax.swing.GroupLayout(DesignOrderPanelScreen);
        DesignOrderPanelScreen.setLayout(DesignOrderPanelScreenLayout);
        DesignOrderPanelScreenLayout.setHorizontalGroup(
            DesignOrderPanelScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPanelScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignOrderTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        DesignOrderPanelScreenLayout.setVerticalGroup(
            DesignOrderPanelScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPanelScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignOrderTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SalesDesignPanel.setMaximumSize(new java.awt.Dimension(1280, 1080));
        SalesDesignPanel.setPreferredSize(new java.awt.Dimension(1280, 1080));

        DesignSalesTabbedPanel.setFont(DesignSalesTabbedPanel.getFont().deriveFont(DesignSalesTabbedPanel.getFont().getStyle() | java.awt.Font.BOLD, DesignSalesTabbedPanel.getFont().getSize()+1));

        DesignOrdersViewPanel.setFont(DesignOrdersViewPanel.getFont().deriveFont(DesignOrdersViewPanel.getFont().getSize()-1f));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Design Orders", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N
        jPanel1.setFont(jPanel1.getFont().deriveFont(jPanel1.getFont().getSize()-1f));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "desgnCode", "custNIC", "returnDate", "orderDate", "eid", "description", "extraCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(orderTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(253, 253, 253))
        );

        jLabel68.setFont(jLabel68.getFont().deriveFont(jLabel68.getFont().getSize()-1f));
        jLabel68.setText("Customer NIC");

        designOrderSearch.setFont(designOrderSearch.getFont().deriveFont(designOrderSearch.getFont().getSize()-1f));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Design", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        designTable2.setFont(designTable2.getFont().deriveFont(designTable2.getFont().getSize()-1f));
        designTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "designCode", "designType", "vehicleType", "designCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        designTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                designTable2MouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(designTable2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel71.setFont(jLabel71.getFont().deriveFont(jLabel71.getFont().getSize()-1f));
        jLabel71.setText("Design Code");

        dcLable.setFont(dcLable.getFont().deriveFont(dcLable.getFont().getSize()-1f));

        jLabel133.setFont(jLabel133.getFont().deriveFont(jLabel133.getFont().getSize()-1f));
        jLabel133.setText("Customer");

        niclable.setFont(niclable.getFont().deriveFont(niclable.getFont().getSize()-1f));

        jLabel135.setFont(jLabel135.getFont().deriveFont(jLabel135.getFont().getSize()-1f));
        jLabel135.setText("Design Cost");

        dcostLable.setFont(dcostLable.getFont().deriveFont(dcostLable.getFont().getSize()-1f));

        extraLable.setFont(extraLable.getFont().deriveFont(extraLable.getFont().getSize()-1f));

        jLabel147.setFont(jLabel147.getFont().deriveFont(jLabel147.getFont().getSize()-1f));
        jLabel147.setText("Extra Cost");

        sellButton.setText("Sell");
        sellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellButtonActionPerformed(evt);
            }
        });

        orderSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        orderSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderSearchbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DesignOrdersViewPanelLayout = new javax.swing.GroupLayout(DesignOrdersViewPanel);
        DesignOrdersViewPanel.setLayout(DesignOrdersViewPanelLayout);
        DesignOrdersViewPanelLayout.setHorizontalGroup(
            DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(designOrderSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(orderSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                                .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(niclable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                                .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extraLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153)
                                .addComponent(sellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(279, Short.MAX_VALUE))
        );
        DesignOrdersViewPanelLayout.setVerticalGroup(
            DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrdersViewPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(designOrderSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orderSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(niclable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DesignOrdersViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(extraLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sellButton))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        DesignSalesTabbedPanel.addTab("View Order   ", DesignOrdersViewPanel);

        saleslable.setFont(saleslable.getFont().deriveFont(saleslable.getFont().getSize()-1f));
        saleslable.setText("Customer NIC");

        salesSearch.setFont(salesSearch.getFont().deriveFont(salesSearch.getFont().getSize()-1f));

        jLabel75.setFont(jLabel75.getFont().deriveFont(jLabel75.getFont().getSize()-1f));
        jLabel75.setText("Sales ID");

        viewSalesLable.setFont(viewSalesLable.getFont().deriveFont(viewSalesLable.getFont().getSize()-1f));

        jLabel76.setFont(jLabel76.getFont().deriveFont(jLabel76.getFont().getSize()-1f));
        jLabel76.setText("Sales Date");

        viewSalesDatelbl.setFont(viewSalesDatelbl.getFont().deriveFont(viewSalesDatelbl.getFont().getSize()-1f));

        jLabel77.setFont(jLabel77.getFont().deriveFont(jLabel77.getFont().getSize()-1f));
        jLabel77.setText("Design Code");

        viewDesignCodelbl.setFont(viewDesignCodelbl.getFont().deriveFont(viewDesignCodelbl.getFont().getSize()-1f));

        jLabel78.setFont(jLabel78.getFont().deriveFont(jLabel78.getFont().getSize()-1f));
        jLabel78.setText("Customer NIC");

        viewNIClbl.setFont(viewNIClbl.getFont().deriveFont(viewNIClbl.getFont().getSize()-1f));

        jLabel79.setFont(jLabel79.getFont().deriveFont(jLabel79.getFont().getSize()-1f));
        jLabel79.setText("Sales Price");

        jButton21.setFont(jButton21.getFont());
        jButton21.setText("View Sales Report");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        addSalesbtn.setText("Add");
        addSalesbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSalesbtnActionPerformed(evt);
            }
        });

        resetSalesbtn.setText("Reset");
        resetSalesbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSalesbtnActionPerformed(evt);
            }
        });

        salesSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        salesSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesSearchbtnActionPerformed(evt);
            }
        });

        salesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "salesID", "salesDate", "sallingPrice", "custNIC", "designCode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(salesTable);

        updateSalesbtn.setText("Update");
        updateSalesbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSalesbtnActionPerformed(evt);
            }
        });

        jLabel80.setFont(jLabel80.getFont().deriveFont(jLabel80.getFont().getSize()-1f));
        jLabel80.setText("Service Charges");

        javax.swing.GroupLayout SalesPanelLayout = new javax.swing.GroupLayout(SalesPanel);
        SalesPanel.setLayout(SalesPanelLayout);
        SalesPanelLayout.setHorizontalGroup(
            SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesPanelLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton21)
                        .addGroup(SalesPanelLayout.createSequentialGroup()
                            .addComponent(saleslable, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(salesSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(salesSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SalesPanelLayout.createSequentialGroup()
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salesPriceTxt))
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewNIClbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDesignCodelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewSalesLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewSalesDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SalesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serviceChargetxt)))
                        .addGap(225, 225, 225)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addSalesbtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetSalesbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateSalesbtn))))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        SalesPanelLayout.setVerticalGroup(
            SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesPanelLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(saleslable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(salesSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                    .addComponent(salesSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SalesPanelLayout.createSequentialGroup()
                        .addComponent(jButton21)
                        .addGap(143, 143, 143)
                        .addComponent(addSalesbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateSalesbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetSalesbtn))
                    .addGroup(SalesPanelLayout.createSequentialGroup()
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewSalesLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewSalesDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewDesignCodelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewNIClbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salesPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serviceChargetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(213, Short.MAX_VALUE))
        );

        DesignSalesTabbedPanel.addTab("Sales    ", SalesPanel);

        jLabel102.setFont(jLabel102.getFont().deriveFont(jLabel102.getFont().getSize()-1f));
        jLabel102.setText("Payment ID");

        paymentSearchbtn.setFont(paymentSearchbtn.getFont());
        paymentSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        paymentSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentSearchbtnActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Payment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        jLabel91.setFont(jLabel91.getFont().deriveFont(jLabel91.getFont().getSize()-1f));
        jLabel91.setText("Payment ID");

        advanceSearchbtn.setFont(advanceSearchbtn.getFont());
        advanceSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        advanceSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advanceSearchbtnActionPerformed(evt);
            }
        });

        jLabel92.setFont(jLabel92.getFont().deriveFont(jLabel92.getFont().getSize()-1f));
        jLabel92.setText("Payment ID");

        paymentIDlbl.setFont(paymentIDlbl.getFont().deriveFont(paymentIDlbl.getFont().getSize()-1f));

        jLabel93.setFont(jLabel93.getFont().deriveFont(jLabel93.getFont().getSize()-1f));
        jLabel93.setText("Payment Date");

        fullpaidDatelbl.setFont(fullpaidDatelbl.getFont().deriveFont(fullpaidDatelbl.getFont().getSize()-1f));

        jLabel94.setFont(jLabel94.getFont().deriveFont(jLabel94.getFont().getSize()-1f));
        jLabel94.setText("Total Amount");

        amountlbl.setFont(amountlbl.getFont().deriveFont(amountlbl.getFont().getSize()-1f));

        jLabel95.setFont(jLabel95.getFont().deriveFont(jLabel95.getFont().getSize()-1f));
        jLabel95.setText("Customer NIC");

        custnic.setFont(custnic.getFont().deriveFont(custnic.getFont().getSize()-1f));

        jButton27.setFont(jButton27.getFont());
        jButton27.setText("Receipt");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        advanceTableView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "paymentID", "advance", "paymentDate", "balance", "NIC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        advanceTableView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                advanceTableViewMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(advanceTableView);

        settlePaymentbtn.setText("Settle Payment");
        settlePaymentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settlePaymentbtnActionPerformed(evt);
            }
        });

        removePaymentbtn.setText("Remove");
        removePaymentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePaymentbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(advanceSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(advanceSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(custnic, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                    .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(paymentIDlbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fullpaidDatelbl, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(amountlbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(settlePaymentbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removePaymentbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paymentIDlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fullpaidDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(amountlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(custnic, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton27)
                            .addComponent(settlePaymentbtn)
                            .addComponent(removePaymentbtn)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(advanceSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel91, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(advanceSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "paymentID", "amount", "paymentDate", "NIC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        paymentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentTableMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(paymentTable);

        javax.swing.GroupLayout PaymentPaneLayout = new javax.swing.GroupLayout(PaymentPane);
        PaymentPane.setLayout(PaymentPaneLayout);
        PaymentPaneLayout.setHorizontalGroup(
            PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentPaneLayout.createSequentialGroup()
                .addGroup(PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaymentPaneLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaymentPaneLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PaymentPaneLayout.createSequentialGroup()
                                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(viewFullPaySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paymentSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane9))))
                .addGap(141, 141, 141))
        );
        PaymentPaneLayout.setVerticalGroup(
            PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentPaneLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PaymentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewFullPaySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paymentSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        DesignSalesTabbedPanel.addTab("Payment    ", PaymentPane);

        javax.swing.GroupLayout SalesDesignPanelLayout = new javax.swing.GroupLayout(SalesDesignPanel);
        SalesDesignPanel.setLayout(SalesDesignPanelLayout);
        SalesDesignPanelLayout.setHorizontalGroup(
            SalesDesignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesDesignPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignSalesTabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        SalesDesignPanelLayout.setVerticalGroup(
            SalesDesignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesDesignPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignSalesTabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        DesignProfitPanel.setMaximumSize(new java.awt.Dimension(1280, 1080));
        DesignProfitPanel.setPreferredSize(new java.awt.Dimension(1280, 1080));

        DesignOrderTabbedPane1.setFont(DesignOrderTabbedPane1.getFont().deriveFont(DesignOrderTabbedPane1.getFont().getStyle() | java.awt.Font.BOLD, DesignOrderTabbedPane1.getFont().getSize()+1));

        InteriorDesignPane1.setFont(InteriorDesignPane1.getFont().deriveFont((float)17));

        designPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Interior Designs")), "Manage Design Profit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        jLabel114.setFont(jLabel114.getFont().deriveFont(jLabel114.getFont().getSize()-1f));
        jLabel114.setText("Customer NIC");

        profitAddSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        profitAddSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profitAddSearchbtnActionPerformed(evt);
            }
        });

        designorderTable.setFont(designorderTable.getFont().deriveFont(designorderTable.getFont().getSize()-1f));
        designorderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "designCode", "custNIC", "returnDate", "orderDate", "eid", "description", "extraCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        designorderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                designorderTableMouseClicked(evt);
            }
        });
        jScrollPane38.setViewportView(designorderTable);

        designTableProfit.setFont(designTableProfit.getFont().deriveFont(designTableProfit.getFont().getSize()-1f));
        designTableProfit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "designCode", "designType", "vehicleModel", "designCost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        designTableProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                designTableProfitMouseClicked(evt);
            }
        });
        jScrollPane39.setViewportView(designTableProfit);

        salesTableProfit.setFont(salesTableProfit.getFont().deriveFont(salesTableProfit.getFont().getSize()-1f));
        salesTableProfit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "salesID", "salesDate", "salesPrice", "custNIC", "designCode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salesTableProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesTableProfitMouseClicked(evt);
            }
        });
        jScrollPane40.setViewportView(salesTableProfit);

        jLabel124.setFont(jLabel124.getFont().deriveFont(jLabel124.getFont().getSize()-1f));
        jLabel124.setText("Index");

        indexlbl.setFont(indexlbl.getFont().deriveFont(indexlbl.getFont().getSize()-1f));

        jLabel138.setFont(jLabel138.getFont().deriveFont(jLabel138.getFont().getSize()-1f));
        jLabel138.setText("Profit");

        profitlbl.setFont(profitlbl.getFont().deriveFont(profitlbl.getFont().getSize()-1f));

        jLabel168.setFont(jLabel168.getFont().deriveFont(jLabel168.getFont().getSize()-1f));
        jLabel168.setText("Sales Date");

        salesDatelbl.setFont(salesDatelbl.getFont().deriveFont(salesDatelbl.getFont().getSize()-1f));

        jLabel169.setFont(jLabel169.getFont().deriveFont(jLabel169.getFont().getSize()-1f));
        jLabel169.setText("Sales ID");

        salesID.setFont(salesID.getFont().deriveFont(salesID.getFont().getSize()-1f));

        jLabel137.setFont(jLabel137.getFont().deriveFont(jLabel137.getFont().getSize()-1f));
        jLabel137.setText("Extra Cost");

        ecostLable.setFont(ecostLable.getFont().deriveFont(ecostLable.getFont().getSize()-1f));

        jLabel139.setFont(jLabel139.getFont().deriveFont(jLabel139.getFont().getSize()-1f));
        jLabel139.setText("Sales Income");

        salesIncomeLable.setFont(salesIncomeLable.getFont().deriveFont(salesIncomeLable.getFont().getSize()-1f));

        addProfitbtn.setFont(addProfitbtn.getFont());
        addProfitbtn.setText("Add Profit");
        addProfitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProfitbtnActionPerformed(evt);
            }
        });

        jLabel140.setFont(jLabel140.getFont().deriveFont(jLabel140.getFont().getSize()-1f));
        jLabel140.setText("Total Design Cost");

        totcostLable.setFont(totcostLable.getFont().deriveFont(totcostLable.getFont().getSize()-1f));

        javax.swing.GroupLayout designPanel1Layout = new javax.swing.GroupLayout(designPanel1);
        designPanel1.setLayout(designPanel1Layout);
        designPanel1Layout.setHorizontalGroup(
            designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, designPanel1Layout.createSequentialGroup()
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(designPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profitdesignsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profitAddSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(designPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(indexlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(profitlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel168, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salesDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(addProfitbtn)
                                .addGroup(designPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(salesID, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ecostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totcostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane40, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(designPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salesIncomeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(32, 32, 32))
            .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(designPanel1Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(35, Short.MAX_VALUE)))
        );
        designPanel1Layout.setVerticalGroup(
            designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(designPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(profitAddSearchbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profitdesignsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane39, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane40, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ecostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel140, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totcostLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel139, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesIncomeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(indexlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profitlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel168, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProfitbtn)
                .addGap(8, 8, 8))
            .addGroup(designPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(designPanel1Layout.createSequentialGroup()
                    .addGap(68, 68, 68)
                    .addComponent(jScrollPane38, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(709, Short.MAX_VALUE)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Design Profit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        jLabel170.setFont(jLabel170.getFont().deriveFont(jLabel170.getFont().getSize()-1f));
        jLabel170.setText("salesID");

        designprofitSearchtxt.setFont(designprofitSearchtxt.getFont().deriveFont(designprofitSearchtxt.getFont().getSize()-1f));

        profitSearchbtn.setFont(profitSearchbtn.getFont());
        profitSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        profitSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profitSearchbtnActionPerformed(evt);
            }
        });

        salesProfit.setFont(salesProfit.getFont());
        salesProfit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "index", "profit", "salesDate", "salesID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        salesProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesProfitMouseClicked(evt);
            }
        });
        jScrollPane41.setViewportView(salesProfit);

        jLabel171.setFont(jLabel171.getFont().deriveFont(jLabel171.getFont().getSize()-1f));
        jLabel171.setText("Index");

        indexlbl1.setFont(indexlbl1.getFont().deriveFont(indexlbl1.getFont().getSize()-1f));

        jLabel172.setFont(jLabel172.getFont().deriveFont(jLabel172.getFont().getSize()-1f));
        jLabel172.setText("Profit");

        profitIdLbl1.setFont(profitIdLbl1.getFont().deriveFont(profitIdLbl1.getFont().getSize()-1f));

        jLabel173.setFont(jLabel173.getFont().deriveFont(jLabel173.getFont().getSize()-1f));
        jLabel173.setText("Sales Date");

        salesDatelbl1.setFont(salesDatelbl1.getFont().deriveFont(salesDatelbl1.getFont().getSize()-1f));

        jLabel174.setFont(jLabel174.getFont().deriveFont(jLabel174.getFont().getSize()-1f));
        jLabel174.setText("Sales ID");

        salesID1.setFont(salesID1.getFont().deriveFont(salesID1.getFont().getSize()-1f));

        javax.swing.GroupLayout designprofitViewPanelLayout = new javax.swing.GroupLayout(designprofitViewPanel);
        designprofitViewPanel.setLayout(designprofitViewPanelLayout);
        designprofitViewPanelLayout.setHorizontalGroup(
            designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
            .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                            .addComponent(jLabel171, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(indexlbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                            .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(profitIdLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                            .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(salesDatelbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                            .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(salesID1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        designprofitViewPanelLayout.setVerticalGroup(
            designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
            .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(designprofitViewPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel171, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(indexlbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profitIdLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(salesDatelbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(designprofitViewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(salesID1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel170, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(designprofitSearchtxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(profitSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane41, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(designprofitViewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(profitSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(designprofitSearchtxt)
                    .addComponent(jLabel170, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane41, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(designprofitViewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );

        javax.swing.GroupLayout InteriorDesignPane1Layout = new javax.swing.GroupLayout(InteriorDesignPane1);
        InteriorDesignPane1.setLayout(InteriorDesignPane1Layout);
        InteriorDesignPane1Layout.setHorizontalGroup(
            InteriorDesignPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InteriorDesignPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(designPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        InteriorDesignPane1Layout.setVerticalGroup(
            InteriorDesignPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InteriorDesignPane1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(InteriorDesignPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(designPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DesignOrderTabbedPane1.addTab("Design Profit", InteriorDesignPane1);

        jLabel148.setFont(jLabel148.getFont().deriveFont(jLabel148.getFont().getSize()-1f));
        jLabel148.setText("Month");

        profitSearchtxt.setFont(profitSearchtxt.getFont().deriveFont(profitSearchtxt.getFont().getSize()-1f));

        monthlyProfitbtn.setFont(monthlyProfitbtn.getFont());
        monthlyProfitbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        monthlyProfitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyProfitbtnActionPerformed(evt);
            }
        });

        profitTable.setFont(profitTable.getFont().deriveFont(profitTable.getFont().getSize()-1f));
        profitTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "profitID", "totalProfit", "month"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        profitTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profitTableMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(profitTable);

        jButton53.setFont(jButton53.getFont());
        jButton53.setText("View Reports");
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Monthly Profit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15))); // NOI18N

        jLabel150.setFont(jLabel150.getFont().deriveFont(jLabel150.getFont().getSize()-1f));
        jLabel150.setText("Sales Date  (Year)");

        yearsearch.setFont(yearsearch.getFont().deriveFont(yearsearch.getFont().getSize()-1f));

        salesincomeSearchbtn.setFont(salesincomeSearchbtn.getFont());
        salesincomeSearchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        salesincomeSearchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesincomeSearchbtnActionPerformed(evt);
            }
        });

        SalesIncomeTable.setFont(SalesIncomeTable.getFont().deriveFont(SalesIncomeTable.getFont().getSize()-1f));
        SalesIncomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "index", "profit", "salesDate", "salesID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane15.setViewportView(SalesIncomeTable);

        jLabel151.setFont(jLabel151.getFont().deriveFont(jLabel151.getFont().getSize()-1f));
        jLabel151.setText("Total Profit");

        profitAmount.setFont(profitAmount.getFont().deriveFont(profitAmount.getFont().getSize()-1f));

        jLabel152.setFont(jLabel152.getFont().deriveFont(jLabel152.getFont().getSize()-1f));
        jLabel152.setText("Profit ID");

        profitIDLable.setFont(profitIDLable.getFont().deriveFont(profitIDLable.getFont().getSize()-1f));

        jLabel153.setFont(jLabel153.getFont().deriveFont(jLabel153.getFont().getSize()-1f));
        jLabel153.setText("Month");

        monthtxt.setFont(monthtxt.getFont().deriveFont(monthtxt.getFont().getSize()-1f));

        jButton55.setFont(jButton55.getFont());
        jButton55.setText("Add Profit Record");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jButton4.setText("Calc");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profitCalcPanelLayout = new javax.swing.GroupLayout(profitCalcPanel);
        profitCalcPanel.setLayout(profitCalcPanelLayout);
        profitCalcPanelLayout.setHorizontalGroup(
            profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profitCalcPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profitCalcPanelLayout.createSequentialGroup()
                        .addComponent(profitAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton55)
                        .addComponent(monthtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profitCalcPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(profitIDLable, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(33, Short.MAX_VALUE)))
        );
        profitCalcPanelLayout.setVerticalGroup(
            profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profitCalcPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(profitAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(58, 58, 58)
                .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profitCalcPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(profitCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel152, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profitIDLable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(106, Short.MAX_VALUE)))
        );

        monthsearch.setFont(monthsearch.getFont().deriveFont(monthsearch.getFont().getSize()-1f));

        jLabel155.setFont(jLabel155.getFont().deriveFont(jLabel155.getFont().getSize()-1f));
        jLabel155.setText("Month");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel150)
                            .addComponent(jLabel155, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(monthsearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salesincomeSearchbtn))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(yearsearch)
                                .addGap(66, 66, 66)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(profitCalcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(monthsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(salesincomeSearchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profitCalcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DesignOrderPane1Layout = new javax.swing.GroupLayout(DesignOrderPane1);
        DesignOrderPane1.setLayout(DesignOrderPane1Layout);
        DesignOrderPane1Layout.setHorizontalGroup(
            DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPane1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton53)
                        .addGroup(DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(DesignOrderPane1Layout.createSequentialGroup()
                                .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(profitSearchtxt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthlyProfitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        DesignOrderPane1Layout.setVerticalGroup(
            DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignOrderPane1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monthlyProfitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DesignOrderPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profitSearchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton53)
                .addGap(14, 14, 14)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        DesignOrderTabbedPane1.addTab("Monthly Profit   ", DesignOrderPane1);

        javax.swing.GroupLayout DesignProfitPanelLayout = new javax.swing.GroupLayout(DesignProfitPanel);
        DesignProfitPanel.setLayout(DesignProfitPanelLayout);
        DesignProfitPanelLayout.setHorizontalGroup(
            DesignProfitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignProfitPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignOrderTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        DesignProfitPanelLayout.setVerticalGroup(
            DesignProfitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesignProfitPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DesignOrderTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jLabel49.setFont(jLabel49.getFont().deriveFont(jLabel49.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel49.setText("Assign Technician");

        jLabel56.setFont(jLabel56.getFont().deriveFont(jLabel56.getFont().getSize()-1f));
        jLabel56.setText("EmployeeID");

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        empTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Pemp_id", "fname", "lname", "Mobile"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        empTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(empTable);

        jLabel58.setFont(jLabel58.getFont().deriveFont(jLabel58.getFont().getSize()-1f));
        jLabel58.setText("Employee ID");

        eidlable.setFont(eidlable.getFont().deriveFont(eidlable.getFont().getSize()-1f));

        fameLbl.setFont(fameLbl.getFont().deriveFont(fameLbl.getFont().getSize()-1f));

        jLabel60.setFont(jLabel60.getFont().deriveFont(jLabel60.getFont().getSize()-1f));
        jLabel60.setText("First Name");

        lnameLbl.setFont(lnameLbl.getFont().deriveFont(lnameLbl.getFont().getSize()-1f));

        jLabel62.setFont(jLabel62.getFont().deriveFont(jLabel62.getFont().getSize()-1f));
        jLabel62.setText("Last Name");

        jLabel64.setFont(jLabel64.getFont().deriveFont(jLabel64.getFont().getSize()-1f));
        jLabel64.setText("Phone");

        phonelbl.setFont(phonelbl.getFont().deriveFont(phonelbl.getFont().getSize()-1f));

        assignTechnicianBtn.setFont(assignTechnicianBtn.getFont());
        assignTechnicianBtn.setText("Assign Technician");
        assignTechnicianBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignTechnicianBtnActionPerformed(evt);
            }
        });

        exitTechnicianDialog.setText("Exit");
        exitTechnicianDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitTechnicianDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout technicianPanel1Layout = new javax.swing.GroupLayout(technicianPanel1);
        technicianPanel1.setLayout(technicianPanel1Layout);
        technicianPanel1Layout.setHorizontalGroup(
            technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technicianPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, technicianPanel1Layout.createSequentialGroup()
                        .addComponent(assignTechnicianBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitTechnicianDialog))
                    .addGroup(technicianPanel1Layout.createSequentialGroup()
                        .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(technicianPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eidlable, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(technicianPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(technicianPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lnameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(technicianPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(phonelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        technicianPanel1Layout.setVerticalGroup(
            technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(technicianPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eidlable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lnameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phonelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(technicianPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assignTechnicianBtn)
                    .addComponent(exitTechnicianDialog))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout assignTechnicianPanelLayout = new javax.swing.GroupLayout(assignTechnicianPanel);
        assignTechnicianPanel.setLayout(assignTechnicianPanelLayout);
        assignTechnicianPanelLayout.setHorizontalGroup(
            assignTechnicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignTechnicianPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(assignTechnicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(assignTechnicianPanelLayout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(empSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(technicianPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(assignTechnicianPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(380, Short.MAX_VALUE))
        );
        assignTechnicianPanelLayout.setVerticalGroup(
            assignTechnicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assignTechnicianPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(assignTechnicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assignTechnicianPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(technicianPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel67.setFont(jLabel67.getFont().deriveFont(jLabel67.getFont().getSize()-1f));
        jLabel67.setText("Item No");

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jLabel154.setFont(jLabel154.getFont().deriveFont(jLabel154.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel154.setText("Remaining Quantity");

        remainQtyTable.setFont(remainQtyTable.getFont().deriveFont(remainQtyTable.getFont().getSize()-1f));
        remainQtyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "itemNo", "stockCount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane27.setViewportView(remainQtyTable);
        if (remainQtyTable.getColumnModel().getColumnCount() > 0) {
            remainQtyTable.getColumnModel().getColumn(1).setResizable(false);
        }

        exitRemainQty.setText("Exit");
        exitRemainQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitRemainQtyActionPerformed(evt);
            }
        });

        contactSupplierBtn.setText("Contact Supplier");
        contactSupplierBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactSupplierBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout remainQuantityPanelLayout = new javax.swing.GroupLayout(remainQuantityPanel);
        remainQuantityPanel.setLayout(remainQuantityPanelLayout);
        remainQuantityPanelLayout.setHorizontalGroup(
            remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, remainQuantityPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel154)
                .addGap(575, 575, 575))
            .addGroup(remainQuantityPanelLayout.createSequentialGroup()
                .addGroup(remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(remainQuantityPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(contactSupplierBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitRemainQty))
                    .addGroup(remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(remainQuantityPanelLayout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(itemSearchRemain, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(remainQuantityPanelLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        remainQuantityPanelLayout.setVerticalGroup(
            remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(remainQuantityPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel154)
                .addGap(18, 18, 18)
                .addGroup(remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(itemSearchRemain, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(remainQuantityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitRemainQty)
                    .addComponent(contactSupplierBtn))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel81.setFont(jLabel81.getFont().deriveFont(jLabel81.getFont().getSize()-1f));
        jLabel81.setText("Supplier Code");

        scodeLable.setFont(scodeLable.getFont().deriveFont(scodeLable.getFont().getSize()-1f));

        companyNamelbl.setFont(companyNamelbl.getFont().deriveFont(companyNamelbl.getFont().getSize()-1f));

        jLabel83.setFont(jLabel83.getFont().deriveFont(jLabel83.getFont().getSize()-1f));
        jLabel83.setText("Company Name");

        fnameLable.setFont(fnameLable.getFont().deriveFont(fnameLable.getFont().getSize()-1f));

        jLabel84.setFont(jLabel84.getFont().deriveFont(jLabel84.getFont().getSize()-1f));
        jLabel84.setText("First Name");

        lnameLable.setFont(lnameLable.getFont().deriveFont(lnameLable.getFont().getSize()-1f));

        jLabel85.setFont(jLabel85.getFont().deriveFont(jLabel85.getFont().getSize()-1f));
        jLabel85.setText("Last Name");

        phonelable.setFont(phonelable.getFont().deriveFont(phonelable.getFont().getSize()-1f));

        jLabel86.setFont(jLabel86.getFont().deriveFont(jLabel86.getFont().getSize()-1f));
        jLabel86.setText("Phone");

        emailSupplier.setFont(emailSupplier.getFont());
        emailSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/GMail-icon.png"))); // NOI18N
        emailSupplier.setText("Email");
        emailSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailSupplierActionPerformed(evt);
            }
        });

        ExitSupplierContact.setText("Exit");
        ExitSupplierContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitSupplierContactActionPerformed(evt);
            }
        });

        jLabel88.setFont(jLabel88.getFont().deriveFont(jLabel88.getFont().getSize()-1f));
        jLabel88.setText("Email");

        emailLable.setFont(emailLable.getFont().deriveFont(emailLable.getFont().getSize()-1f));

        javax.swing.GroupLayout supplierRecordPanel1Layout = new javax.swing.GroupLayout(supplierRecordPanel1);
        supplierRecordPanel1.setLayout(supplierRecordPanel1Layout);
        supplierRecordPanel1Layout.setHorizontalGroup(
            supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supplierRecordPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scodeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(companyNamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fnameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lnameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phonelable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(emailSupplier)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExitSupplierContact, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailLable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        supplierRecordPanel1Layout.setVerticalGroup(
            supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierRecordPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scodeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(companyNamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fnameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phonelable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLable, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(supplierRecordPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(emailSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExitSupplierContact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Details"));

        jLabel87.setFont(jLabel87.getFont().deriveFont(jLabel87.getFont().getSize()-1f));
        jLabel87.setText("Supplier Code");

        jButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caravenueinteriordesign/IconImages/Search-icon (1).png"))); // NOI18N
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        supplierTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "supplierCode", "companyName", "fname", "lname", "phone", "email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        supplierTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supplierTableMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(supplierTable);
        if (supplierTable.getColumnModel().getColumnCount() > 0) {
            supplierTable.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton33))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(supplierSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout supplierContactLayout = new javax.swing.GroupLayout(supplierContact);
        supplierContact.setLayout(supplierContactLayout);
        supplierContactLayout.setHorizontalGroup(
            supplierContactLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierContactLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(supplierContactLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(supplierRecordPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58))
        );
        supplierContactLayout.setVerticalGroup(
            supplierContactLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierContactLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(supplierRecordPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        emailRecieverlbl.setFont(emailRecieverlbl.getFont().deriveFont(emailRecieverlbl.getFont().getSize()-1f));

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()-1f));
        jLabel7.setText("To");

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getSize()-1f));
        jLabel8.setText("Subject");

        subjecttxt.setFont(subjecttxt.getFont().deriveFont(subjecttxt.getFont().getSize()-1f));
        subjecttxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjecttxtActionPerformed(evt);
            }
        });

        emailSendBtn.setText("Send");
        emailSendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailSendBtnActionPerformed(evt);
            }
        });

        msgbodyarea.setColumns(20);
        msgbodyarea.setRows(5);
        jScrollPane1.setViewportView(msgbodyarea);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout emailPanelLayout = new javax.swing.GroupLayout(emailPanel);
        emailPanel.setLayout(emailPanelLayout);
        emailPanelLayout.setHorizontalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emailPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(emailPanelLayout.createSequentialGroup()
                        .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(emailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(emailRecieverlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(emailPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subjecttxt, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailSendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        emailPanelLayout.setVerticalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emailPanelLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailRecieverlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(emailSendBtn)))
                .addGap(18, 18, 18)
                .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(subjecttxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ParentPanelLayout = new javax.swing.GroupLayout(ParentPanel);
        ParentPanel.setLayout(ParentPanelLayout);
        ParentPanelLayout.setHorizontalGroup(
            ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ParentPanelLayout.createSequentialGroup()
                .addGroup(ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ManageItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DesignOrderPanelScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SalesDesignPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DesignProfitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 320, Short.MAX_VALUE))
            .addGroup(ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ParentPanelLayout.createSequentialGroup()
                    .addGap(0, 424, Short.MAX_VALUE)
                    .addGroup(ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(assignTechnicianPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(remainQuantityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(supplierContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(emailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 424, Short.MAX_VALUE)))
        );
        ParentPanelLayout.setVerticalGroup(
            ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ParentPanelLayout.createSequentialGroup()
                .addComponent(ManageItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DesignProfitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DesignOrderPanelScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalesDesignPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10023, Short.MAX_VALUE))
            .addGroup(ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ParentPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(assignTechnicianPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(supplierContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(emailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(remainQuantityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ParentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ParentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(SidebarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 2099, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        ManageItemsPanel.setVisible(false);
        DesignOrderPanelScreen.setVisible(true);
        SalesDesignPanel.setVisible(false);
        DesignProfitPanel.setVisible(false);
       
               
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        Design d2=new Design();
       
       d2.setVehicleType(designSearch.getText());
       String vtype = d2.getVehicleType();
       
       d2.searchDesignResultTable(vtype,designTable);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
       //Update stock when ordering design. 
       DesignItem di2=new DesignItem();
       int rowCount = itemUsageTable.getRowCount();
       int usedStock;
       String ditemNo;
       
       while(rowCount>0)
       {
           usedStock = Integer.parseInt(itemUsageTable.getValueAt(rowCount-1,2).toString());
           ditemNo = itemUsageTable.getValueAt(rowCount-1,1).toString();
           
           di2.updateRemainingItemQty(ditemNo, usedStock);
           rowCount--;
           
       }
        
        di2.notifyLowQuantity(lowqtyLable);
        DesignOrder do1 = new DesignOrder();
        do1.setDesignCode(designCodelable.getText());
        String dcode = do1.getDesignCode();
       
        designcmb.setSelectedItem(dcode);
        
        DesignOrderTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void itemusageSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemusageSearchBtnActionPerformed
        //if the comonents are disabled.
        itemTable.setEnabled(true);
        addRowBtn.setEnabled(true);
        removeRowBtn.setEnabled(true);
        additemUsageTable.setEnabled(true);
        
        
        DesignItem d5 = new DesignItem();
        
        d5.setDesignItemNo(itemNoSearch.getText());
        String itemno=d5.getDesignItemNo();
        
        d5.searchItemForDesign(itemno, itemTable);
    }//GEN-LAST:event_itemusageSearchBtnActionPerformed

    private void removeDesignbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDesignbtnActionPerformed
        Design de1 = new Design();
        Design.ItemUsage du1 = new Design.ItemUsage();
        Design.DesignColor dc1 = new Design.DesignColor();
    
        de1.setDesignCode(designCodelable.getText());
        String dcode =de1.getDesignCode();
       
        int val = JOptionPane.showConfirmDialog(null,"Do you wish to remove design"+dcode);
        
        if(val==0)
        {
            de1.removeDesign(dcode);
            de1.loadDesignTable(designTable);

            dc1.removeDesignColor(dcode);
            dc1.loadColorTable(designColour);

            du1.removeItemUsage(dcode);
            du1.loadItemUsageTable(itemUsageTable);

            this.resetDesign();
            
            JOptionPane.showMessageDialog(null,"You have successfully remove record");
        }
        
        
    }//GEN-LAST:event_removeDesignbtnActionPerformed

    private void updateDesignbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDesignbtnActionPerformed
        Design de1 = new Design();
        de1.setDesignCode(designCodelable.getText());
        String dcode =de1.getDesignCode();
        
        Design.DesignColor dc1 = new Design.DesignColor();
        dc1.updateDesignColor(dcode, checkboxPanel);
        dc1.loadColorTable(designColour);
        
        this.resetDesign();
    }//GEN-LAST:event_updateDesignbtnActionPerformed

    private void addRowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowBtnActionPerformed
        TableModel m1= itemTable.getModel();
        int[] index = itemTable.getSelectedRows();
        Object[] row = new Object[2];
        DefaultTableModel model=(DefaultTableModel)additemUsageTable.getModel();
        
        for(int i =0; i<itemTable.getSelectedRowCount(); i++)
        {
            row[0] =m1.getValueAt(index[i], 0);
            row[1] =m1.getValueAt(index[i], 2);
            model.addRow(row); 
        }
    }//GEN-LAST:event_addRowBtnActionPerformed

    private void itemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_itemTableMouseClicked

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
          //calculate designcost using table column
        double tot=0;
        for(int i=0; i<additemUsageTable.getRowCount(); i++)
        {
            {
                double unitcost = Double.parseDouble(additemUsageTable.getValueAt(i, 1).toString());
                int qty = Integer.parseInt(additemUsageTable.getValueAt(i, 2).toString());
                
                double cost = unitcost*qty;
                tot = tot + cost;
            }
        }
        
        totDesignCostlable.setText(String.valueOf(tot));
        
    }//GEN-LAST:event_jButton57ActionPerformed

    private void addDesignbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDesignbtnActionPerformed
        //Insert into design table
        Design d2 = new Design();
        
        d2.setDesignType(designComboBox.getSelectedItem().toString());
        String dtype = d2.getDesignType();
        
        d2.setVehicleType(vehicleTypeComboBox.getSelectedItem().toString());
        String vtype = d2.getVehicleType();
        
        d2.setDesignCost(Double.parseDouble(totDesignCostlable.getText()));
        double cost = d2.getDesignCost();
        
        
        d2.addDesign(fetchpath);
        d2.loadDesignTable(designTable);
        
        
        String dcode = d2.getLastInsertedDesignCode();
        
        //add entries to itemusage table
        Design.ItemUsage du = new Design.ItemUsage();
        du.addItemUsage(dcode, additemUsageTable);
        du.loadItemUsageTable(itemUsageTable);
        

         //add entries to designColor table
//        ArrayList<String> colorArr = new ArrayList<String>();
//        
//        for(Component ch : checkboxPanel.getComponents())
//        {
//            if(ch instanceof JCheckBox)
//            {
//                JCheckBox chkbox = (JCheckBox) ch;
//                
//                if(chkbox.isSelected())
//                {
//                    colorArr.add(chkbox.getText());
//                }
//            }
//        }
//
//        String color[] = new String[colorArr.size()];
//        color = colorArr.toArray(color);

        Design.DesignColor dd1 = new Design.DesignColor();
        
        String color[] = dd1.getSelectedCheckBoxColor(checkboxPanel);
        dd1.addDesignColor(dcode, color);
        dd1.loadColorTable(designColour);
        
        
        this.resetDesign();
        
    }//GEN-LAST:event_addDesignbtnActionPerformed

    private void removeRowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowBtnActionPerformed
        TableModel m1= itemTable.getModel();
        Object[] row = new Object[2];
        DefaultTableModel model=(DefaultTableModel)additemUsageTable.getModel();
        
        for(int i =0; i<itemTable.getSelectedRowCount(); i++)
        {
            model.removeRow(i);
        }
    }//GEN-LAST:event_removeRowBtnActionPerformed

    private void attachbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachbtnActionPerformed
        JFileChooser imageChoose = new JFileChooser();
        imageChoose.showOpenDialog(null); //Display the dialog
        
        File f = imageChoose.getSelectedFile();
        
        String path = f.getAbsolutePath(); // get the path of file image
        
        fetchpath = path; //fetching path to add the image file to db
        
        imageFiletxt.setText(path); //display path inside a textbox
        
        //display image inside a lable
        ImageIcon imgIcon = new ImageIcon(path); //get image as an icon
        Image img = imgIcon.getImage(); //convert icon into image
        
        Image newImage = img.getScaledInstance(224, 110, java.awt.Image.SCALE_SMOOTH); //change current image size according to the lable size
        imgIcon = new ImageIcon(newImage); // change image icon into new image
        this.imagelable.setIcon(imgIcon);
    }//GEN-LAST:event_attachbtnActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        //table load
        try
        {
            String query1="SELECT Pemp_Id,First_Name,Last_Name,Mobile FROM permenent_employee WHERE Position_Title='Technician'";
            pst=conn.prepareStatement(query1);
            rs=pst.executeQuery();
            
            empTable.setModel(DbUtils.resultSetToTableModel(rs));
            

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }  
        

          dialog.remove(remainQuantityPanel);
          dialog.remove(supplierContact);
          dialog.remove(emailPanel);
          
          dialog.add(assignTechnicianPanel);
          dialog.setSize(660, 640);
          dialog.setLocationRelativeTo(null);
          dialog.setTitle("Assign Technician");
          dialog.setVisible(true);
       
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DesignOrder o1 = new DesignOrder();
        
        o1.setCustIC(custnictxt.getText());
        o1.setOrderDate(((JTextField)orderdate.getDateEditor().getUiComponent()).getText());
        o1.setReturnDate(((JTextField)returndate.getDateEditor().getUiComponent()).getText());
        o1.setOrderDescription(desttxt.getText());
        o1.setExtraCost(Double.parseDouble(extraacostlbl.getText()));
        o1.setTechnician(techlbl.getText());
        o1.setDesignCode(designcmb.getSelectedItem().toString());
        
        String dcode = o1.getDesignCode();
        String odate = o1.getOrderDate();
        String nic = o1.getCustIC();
        String rdate =o1.getReturnDate();
        String des = o1.getOrderDescription();
        String technician = o1.getTechnician();
        double exfee = o1.getExtraCost();
        
        
        
        
        Validate v3 = new Validate();
        
        
        if(v3.validateNIC(nic))
        {
            if(v3.ValidateCustomer(nic))
            {
                if(v3.isCurrentDate(odate))
                {
                    if(v3.isValidDate(rdate))
                    {
                        if(v3.ValidPrice(exfee))
                        {
                            o1.addDesignOrder();
                            o1.loadDesignOrderHisoryTable(onGoingtable);
                            resetOrder();
                            
                            String dedate = getSystemDate();
                            double dcost = Double.parseDouble(totDesignCostlable.getText());
                            double totcost = this.getDecimal(o1.calcTotalDesignOrdercost(dcost, exfee));

                            DesignSales s1 =new DesignSales();
                            double totprice = this.getDecimal(s1.calcSalesPrice(totcost));

                            AdvancePayment ad1 = new AdvancePayment();
                            double advance = this.getDecimal(ad1.calcAdvance(totprice));
                            double bal =this.getDecimal(ad1.calcBalance(totprice, advance));

                            viewcustNic.setText(nic);
                            advanceUpdatetxt.setText(String.valueOf(advance));
                            balanceUpdate.setText(String.valueOf(bal));
                            viewPaidDate.setText(dedate);

                            DesignOrderTabbedPane.setSelectedIndex(2);

                            //AssignTechnician
                            String fname= fameLbl.getText();

                            try
                            {
                                String q ="INSERT INTO job_technician(employee,job_id,catagory_no,isFinished) VALUE('"+fname+"','"+dcode+"',5,0)";

                                pst=conn.prepareStatement(q);
                                pst.execute();

                            } catch (Exception e)
                            {
                                JOptionPane.showMessageDialog(null, e);
                            } 
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Invalid Price.");
                            extraacostlbl.setText(null);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid return date.");
                        returndate.setDate(null);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Order date should be current date");
                    orderdate.setDate(null);
                }
            }   
             else
            {
                JOptionPane.showMessageDialog(null, "Customer is not a registered customer.");
                custnictxt.setText(null);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid NIC");
            custnictxt.setText(null);
        }
        
        

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DesignOrder o2 = new DesignOrder();
        
        o2.setCustIC(onGogingSearch.getText());
        String nic=o2.getCustIC();
        
        o2.searchOnGoingOrders(nic, onGoingtable);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void onGoingtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onGoingtableMouseClicked
        int row = onGoingtable.getSelectedRow();
        
        String designCode = onGoingtable.getValueAt(row, 0).toString();
        String nic = onGoingtable.getValueAt(row, 1).toString();
        String returnDate = onGoingtable.getValueAt(row, 2).toString();
        String orderDate = onGoingtable.getValueAt(row, 3).toString();
        String technician = onGoingtable.getValueAt(row, 4).toString();
        String des = onGoingtable.getValueAt(row, 5).toString();
        double extracost = (double) onGoingtable.getValueAt(row, 6);
       
        Date rdate = this.setStringtoDate(returnDate);
        Date odate =this.setStringtoDate(orderDate);
        
        designcmb.setSelectedItem(designCode);
        custnictxt.setText(nic);
        orderdate.setDate(odate);
        returndate.setDate(rdate); 
        techlbl.setText(technician);
        extraacostlbl.setText(String.valueOf(extracost));
        desttxt.setText(des);
        
        //disable update some component
        designcmb.setEnabled(false);
        orderdate.setEnabled(false);
        custnictxt.setEnabled(false);
        
        //returndate.setDate(this.setStringtoDate(returnDate));
    }//GEN-LAST:event_onGoingtableMouseClicked

    private void orderUpdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderUpdatebtnActionPerformed
        DesignOrder o4 = new DesignOrder();
        
        o4.setDesignCode(designcmb.getSelectedItem().toString());
        o4.setCustIC(custnictxt.getText());
        o4.setReturnDate(((JTextField)returndate.getDateEditor().getUiComponent()).getText());
        o4.setOrderDescription(desttxt.getText());
        o4.setExtraCost(Double.parseDouble(extraacostlbl.getText()));
        o4.setTechnician(techlbl.getText());
       
        
        String dcode = o4.getDesignCode();
        String nic = o4.getCustIC();
        String rdate =o4.getReturnDate();
        String des = o4.getOrderDescription();
        String technician = o4.getTechnician();
        double exfee = o4.getExtraCost();
        
        Validate v4 = new Validate();
                
        if(v4.isValidDate(rdate))
        {
            if(v4.ValidPrice(exfee))
            {
                o4.updateDesignOrder(dcode, nic);
                o4.loadOngoingOrdersTable(onGoingtable);
                this.resetOrder();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Price");
                extraacostlbl.setText(null);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid return date");
            returndate.setDate(null);
        }
        
        
    }//GEN-LAST:event_orderUpdatebtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       DesignOrder o5 = new DesignOrder();
        
        o5.setDesignCode(designcmb.getSelectedItem().toString());
        o5.setCustIC(custnictxt.getText());
        
        String dcode = o5.getDesignCode();
        String nic = o5.getCustIC();
        
        o5.removeDesignOrder(dcode, nic);
        o5.loadOngoingOrdersTable(onGoingtable);
        this.resetOrder();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        //enable components
        designcmb.setEnabled(true);
        orderdate.setEnabled(true);
        custnictxt.setEnabled(true);
        
        this.resetOrder();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        AdvancePayment a2= new AdvancePayment();
        
        a2.setPaymentID(viewAdvanceSearch.getText());
        String payid = a2.getPaymentID();
        
        a2.searchAdvance(payid, viewAdvanceTable);
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        String location ="E:\\ITP Original\\CarAvenueInteriorDesignOriginal\\src\\InteriorDesignReports\\advancereceipt.jrxml";
        String para = this.advancePayview.getText();
        
        try 
        {
            report re = new report(location ,dbConnect.connect());
            re.getTextBoxParametes("paymentid", para);
            re.generateReport();
        } 
        catch (Exception e) 
        {
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void viewAdvanceTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewAdvanceTableMouseClicked
        int row = viewAdvanceTable.getSelectedRow();
        
        String payid = viewAdvanceTable.getValueAt(row,0).toString();
        double advance = (double) viewAdvanceTable.getValueAt(row, 1);
        double balance = (double) viewAdvanceTable.getValueAt(row, 3);
        String custNic = viewAdvanceTable.getValueAt(row, 4).toString();
        String payDate = viewAdvanceTable.getValueAt(row,2).toString();
        
        advancePayview.setText(payid);
        viewPaidDate.setText(payDate);
        advanceUpdatetxt.setText(String.valueOf(advance));
        balanceUpdate.setText(String.valueOf(balance));
        viewcustNic.setText(custNic);
    }//GEN-LAST:event_viewAdvanceTableMouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        AdvancePayment a3= new AdvancePayment();
        
        a3.setPaymentID(advancePayview.getText());
        a3.setAdvance(Double.parseDouble(advanceUpdatetxt.getText()));
        a3.setBalance(Double.parseDouble( balanceUpdate.getText()));
        
        String payid= a3.getPaymentID();
        double adv = a3.getAdvance();
        double bal = a3.getBalance();
        
        Validate v6 = new Validate();
        if(v6.ValidPrice(adv))
       {
            if(v6.ValidPrice(bal))
            {
                a3.updateAdvance(payid);
                a3.loadAdvanceTable(viewAdvanceTable);
                this.resetAdvancePayment();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Balance Price.");
                balanceUpdate.setText(null);
            }
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Invalid Advance Price.");
           advanceUpdatetxt.setText(null);
       }
 
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
         AdvancePayment a4 = new AdvancePayment();
        
         a4.setPaymentID(advancePayview.getText());
         String payid= a4.getPaymentID();
         
         a4.removeAdvance(payid);
         a4.loadAdvanceTable(viewAdvanceTable);
         this.resetAdvancePayment();
         
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       AdvancePayment a2= new AdvancePayment();
       
       a2.setPaymentDate(viewPaidDate.getText());
       a2.setAdvance(Double.parseDouble(advanceUpdatetxt.getText()));
       a2.setBalance(Double.parseDouble(balanceUpdate.getText()));
       a2.setCustNIC(viewcustNic.getText());
       
       String pdate =a2.getPaymentDate();
       double adv = a2.getAdvance();
       double bal = a2.getBalance();
       String nic=a2.getCustNIC();
       
       Validate v5 = new Validate();
       
       if(v5.ValidPrice(adv))
       {
            if(v5.ValidPrice(bal))
            {
                a2.addAdvanceEntry();
                a2.loadAdvanceTable(viewAdvanceTable);
                this.resetAdvancePayment();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Balance Price.");
                balanceUpdate.setText(null);
            }
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Invalid Advance Price.");
           advanceUpdatetxt.setText(null);
       }

       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        int row = orderTable.getSelectedRow();
        
        String dCode = orderTable.getValueAt(row, 0).toString();
        String nic = orderTable.getValueAt(row, 1).toString();
        double extra= Double.parseDouble(orderTable.getValueAt(row, 6).toString());
        
        dcLable.setText(dCode);
        niclable.setText(nic);
        extraLable.setText(String.valueOf(extra));
        designOrderSearch.setText(nic);
        
        Design des1 = new Design();
        des1.setDesignCode(dCode);
        String designCode = des1.getDesignCode();
        
        des1.searchDesignView(designCode, designTable2);
       
    }//GEN-LAST:event_orderTableMouseClicked

    private void designTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_designTable2MouseClicked
        int row = designTable2.getSelectedRow();
       
       double dcost = Double.parseDouble(designTable2.getValueAt(row, 3).toString());
       
       dcostLable.setText(String.valueOf(dcost));
       
    }//GEN-LAST:event_designTable2MouseClicked

    private void sellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellButtonActionPerformed
        Design d = new Design();
        d.setDesignCost(Double.parseDouble(dcostLable.getText()));
        double dcost = d.getDesignCost();
        
        DesignOrder do1 = new DesignOrder();
        do1.setDesignCode(dcLable.getText());
        do1.setCustIC( niclable.getText());
        do1.setExtraCost(Double.parseDouble(extraLable.getText()));
        
        String dCode = do1.getDesignCode();
        String nic = do1.getCustIC();
        double ecost =do1.getExtraCost();
        
        //Ca;c total design cost and sales price
        double totCost = this.getDecimal(do1.calcTotalDesignOrdercost(dcost, ecost));
       
        DesignSales ds = new DesignSales();
        double salesPrice = this.getDecimal(ds.calcSalesPrice(totCost));
        
        double service = this.getDecimal(ds.calcSeviceCharges(totCost, salesPrice));
       
        String sdate = getSystemDate();
        
        //set data to lables
        viewSalesDatelbl.setText(sdate);
        viewDesignCodelbl.setText(dCode);
        viewNIClbl.setText(nic);
        salesPriceTxt.setText(String.valueOf(salesPrice));
        serviceChargetxt.setText(String.valueOf(service));
        
        DesignSalesTabbedPanel.setSelectedIndex(1);
    }//GEN-LAST:event_sellButtonActionPerformed

    private void orderSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderSearchbtnActionPerformed
        DesignOrder o3 = new DesignOrder();
        
        o3.setCustIC(designOrderSearch.getText());
        String nicSearch = o3.getCustIC();
        
        o3.searchOnGoingOrders(nicSearch, orderTable);
    }//GEN-LAST:event_orderSearchbtnActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        String location ="E:\\ITP Original\\CarAvenueInteriorDesignOriginal\\src\\InteriorDesignReports\\report1.jrxml";
       
        try 
        {
            report re = new report(location ,dbConnect.connect());
            re.generateReport();
        } 
        catch (Exception e) 
        {
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void addSalesbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSalesbtnActionPerformed
       DesignSales desl = new DesignSales();
       
       desl.setSalesDate(viewSalesDatelbl.getText());
       desl.setcustNIC(viewNIClbl.getText());
       desl.setSalesPrice(Double.parseDouble(salesPriceTxt.getText()));
       desl.setDesignCode(viewDesignCodelbl.getText());
       desl.setServiceCharge(Double.parseDouble(serviceChargetxt.getText()));
       
       String salesDate=desl.getSalesDate();
       String nic = desl.getcustNIC();
       String dcode = desl.getDesignCode();
       double sprice = desl.getSalesPrice();
       double service = desl.getServiceCharge();
       
       Validate v7 = new Validate();
       
       if(v7.ValidPrice(sprice))
       {
            desl.addDesignSalesEntry();
            desl.loadDesignSalesTable(salesTable);

            resetSales();

            int val = JOptionPane.showConfirmDialog(null, "Do you wish to make payment?");

            if(val==0)
            {
                 AdvancePayment ap = new AdvancePayment();
                 ap.setCustNIC(nic);
                 String customer = ap.getCustNIC();

                 ap.searchAdvanceByCustomer(customer, advanceTableView);
                 custnic.setText(nic);

                 DesignSalesTabbedPanel.setSelectedIndex(2);
            }
       }
       else
       {
           JOptionPane.showMessageDialog(null, "Invalid Price");
           salesPriceTxt.setText(null);
       }
       
       
    }//GEN-LAST:event_addSalesbtnActionPerformed

    private void resetSalesbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSalesbtnActionPerformed
        resetSales();
    }//GEN-LAST:event_resetSalesbtnActionPerformed

    private void salesSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesSearchbtnActionPerformed
       DesignSales ds2=new DesignSales();
       
       ds2.setcustNIC(salesSearch.getText());
       String nicSearch = ds2.getcustNIC();
       
       ds2.searchDesignSales(nicSearch, salesTable);
    }//GEN-LAST:event_salesSearchbtnActionPerformed

    private void salesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableMouseClicked
        int row = salesTable.getSelectedRow();
       
       String sid = salesTable.getValueAt(row,0).toString();
       String sdate = salesTable.getValueAt(row,1).toString();
       String dcode = salesTable.getValueAt(row,4).toString();
       double sprice = (double) salesTable.getValueAt(row,2);
       String nic = salesTable.getValueAt(row,3).toString();
       double service = (double) salesTable.getValueAt(row,5);
       
       viewSalesLable.setText(sid);
       viewSalesDatelbl.setText(sdate);
       salesPriceTxt.setText(String.valueOf(sprice));
       viewDesignCodelbl.setText(dcode);
       viewNIClbl.setText(nic);
       salesSearch.setText(nic);
       serviceChargetxt.setText(String.valueOf(service));
    }//GEN-LAST:event_salesTableMouseClicked

    private void updateSalesbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSalesbtnActionPerformed
        DesignSales desl2 = new DesignSales();
        
        desl2.setSalesID(viewSalesLable.getText());
        String sid = desl2.getSalesId();
        
        desl2.setSalesPrice(Double.parseDouble(salesPriceTxt.getText()));
        double sprice = desl2.getSalesPrice();
        
        Validate v8 = new Validate();
        
        if(v8.ValidPrice(sprice))
        {
            desl2.updateDesignSales(sid);
            desl2.loadDesignSalesTable(salesTable);
            resetSales();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid Price");
            salesPriceTxt.setText(null);
        }
        
    }//GEN-LAST:event_updateSalesbtnActionPerformed

    private void paymentSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentSearchbtnActionPerformed
        Payment p2= new Payment();
        
        p2.setPaymentID(viewFullPaySearch.getText());
        String payid = p2.getPaymentID();
        
        p2.serachPayment(payid, paymentTable);
    }//GEN-LAST:event_paymentSearchbtnActionPerformed

    private void advanceSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_advanceSearchbtnActionPerformed
        AdvancePayment a2= new AdvancePayment();
        
        a2.setPaymentID(advanceSearch.getText());
        String payid = a2.getPaymentID();
        
        a2.searchAdvance(payid, advanceTableView);
    }//GEN-LAST:event_advanceSearchbtnActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        String location ="E:\\ITP Original\\CarAvenueInteriorDesignOriginal\\Reports\\paymentNew.jrxml";
        String para = this.paymentIDlbl.getText();
        
        try 
        {
            report re = new report(location ,dbConnect.connect());
            re.getTextBoxParametes("paymentid", para);
            re.generateReport();
        } 
        catch (Exception e) 
        {
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void advanceTableViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_advanceTableViewMouseClicked
        int row = advanceTableView.getSelectedRow();
        
        Payment pay= new Payment();
        
        String payDate = getSystemDate();
        
        String payid = advanceTableView.getValueAt(row,0).toString();
        double advance = (double) advanceTableView.getValueAt(row, 1);
        double balance = (double) advanceTableView.getValueAt(row, 3);
        String custNic = advanceTableView.getValueAt(row, 4).toString();
        
        Double totamount = pay.calcFullPayment(advance, balance);
        
        
        paymentIDlbl.setText(payid);
        fullpaidDatelbl.setText(payDate);
        amountlbl.setText(String.valueOf(totamount));
        custnic.setText(custNic);
        advanceSearch.setText(payid);
        
    }//GEN-LAST:event_advanceTableViewMouseClicked

    private void settlePaymentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settlePaymentbtnActionPerformed
        Payment p2 = new Payment();
        
        p2.setPaymentID(paymentIDlbl.getText());
        p2.setAmount(Double.parseDouble(amountlbl.getText()));
        p2.setPaymentDate(fullpaidDatelbl.getText());
        p2.setCustNIC(custnic.getText());
        
        String payid = p2.getPaymentID();
        String payDate = p2.getPaymentDate();
        double amount = p2.getAmount();
        String nic = p2.getCustNIC();
        
        p2.addPaymentEntry();
        p2.loadPaymentTable(paymentTable);
        
        resetPayment();
        
        int val = JOptionPane.showConfirmDialog(null, "Your Payment has done. Do you wish to calculate profit?");
        if(val==0)
        {
            SalesDesignPanel.setVisible(false);
            DesignProfitPanel.setVisible(true);
            
        }

                
    }//GEN-LAST:event_settlePaymentbtnActionPerformed

    private void removePaymentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePaymentbtnActionPerformed
        Payment p4 = new Payment();
        
        p4.setPaymentID(paymentIDlbl.getText());
        String payid = p4.getPaymentID();
        
        p4.removePayment(payid);
        p4.loadPaymentTable(paymentTable);
        resetPayment();
       
    }//GEN-LAST:event_removePaymentbtnActionPerformed

    private void paymentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentTableMouseClicked
        int row = paymentTable.getSelectedRow();
        
        String payid = paymentTable.getValueAt(row,0).toString();
        double amount =  Double.parseDouble(paymentTable.getValueAt(row, 1).toString());
        String custNic = paymentTable.getValueAt(row, 3).toString();
        String payDate = paymentTable.getValueAt(row,2).toString();
         
        paymentIDlbl.setText(payid);
        fullpaidDatelbl.setText(payDate);
        amountlbl.setText(String.valueOf(amount));
        custnic.setText(custNic);
        viewFullPaySearch.setText(payid);
    }//GEN-LAST:event_paymentTableMouseClicked

    private void profitAddSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitAddSearchbtnActionPerformed
        DesignOrder o6 = new DesignOrder();
        
        o6.setCustIC(profitdesignsearch.getText());
        String nic=o6.getCustIC();
        
        o6.searchOnGoingOrders(nic, designorderTable);
    }//GEN-LAST:event_profitAddSearchbtnActionPerformed

    private void designorderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_designorderTableMouseClicked
        int row = designorderTable.getSelectedRow();
        
        String custNIC = designorderTable.getValueAt(row,1).toString();
        double extrafee = (double) designorderTable.getValueAt(row, 6);
        String dcode = designorderTable.getValueAt(row,0).toString();
        
        
        profitdesignsearch.setText(custNIC);
        ecostLable.setText(String.valueOf(extrafee));
        

        DesignSales s1 = new DesignSales();
        s1.setcustNIC(profitdesignsearch.getText());
        String nicSearch=s1.getcustNIC();
        
        s1.searchDesignSales(nicSearch, salesTableProfit);
        
        Design d3 = new Design();
        d3.setDesignCode(dcode);
        String dcodeSearch=d3.getDesignCode();
        
        d3.searchDesignView(dcode, designTableProfit);
    }//GEN-LAST:event_designorderTableMouseClicked

    private void designTableProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_designTableProfitMouseClicked
        int row=designTableProfit.getSelectedRow();
        
        double dcost = (double) designTableProfit.getValueAt(row, 3);
       
        double extrafee = Double.parseDouble(ecostLable.getText());
        
        DesignOrder do1 = new DesignOrder();
        double totDesignCost= do1.calcTotalDesignOrdercost(dcost, extrafee);
        totcostLable.setText(String.valueOf(totDesignCost));
        
        
    }//GEN-LAST:event_designTableProfitMouseClicked

    private void salesTableProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesTableProfitMouseClicked
        int row = salesTableProfit.getSelectedRow();
         
        String sid = salesTableProfit.getValueAt(row,0).toString();
        String sdate = salesTableProfit.getValueAt(row,1).toString();
        double salesprice = (double) salesTableProfit.getValueAt(row, 2);
        
        salesIncomeLable.setText(String.valueOf(salesprice));
        
        double totDesinCost = Double.parseDouble(totcostLable.getText());
        
        Profit pro1= new Profit();
        double profit = this.getDecimal(pro1.calcProfit(totDesinCost, salesprice));
         
        
        salesDatelbl.setText(sdate);
        salesID.setText(sid);
        profitlbl.setText(String.valueOf(profit));
    }//GEN-LAST:event_salesTableProfitMouseClicked

    private void profitSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitSearchbtnActionPerformed
        Profit pro2 = new Profit();
        
        pro2.setSalesID(designprofitSearchtxt.getText());
        String sid = pro2.getSalesID();
        
        pro2.searchProfit(sid, salesProfit);
    }//GEN-LAST:event_profitSearchbtnActionPerformed

    private void salesProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesProfitMouseClicked
         int row = salesProfit.getSelectedRow();
        
         int index = Integer.parseInt(salesProfit.getValueAt(row,0).toString());
         double profit = Double.parseDouble(salesProfit.getValueAt(row,1).toString());
         String sdate = salesProfit.getValueAt(row,2).toString();
         String sid = salesProfit.getValueAt(row,3).toString();
         
         indexlbl1.setText(String.valueOf(index));
         profitIdLbl1.setText(String.valueOf(profit));
         salesDatelbl1.setText(sdate);
         salesID1.setText(sid);
         designprofitSearchtxt.setText(sid);
         
         designprofitViewPanel.setVisible(true);
    }//GEN-LAST:event_salesProfitMouseClicked

    private void addProfitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProfitbtnActionPerformed
        Profit p2 = new Profit();
        
        p2.setSalesProfit(Double.parseDouble(profitlbl.getText()));
        p2.setSalesDate(salesDatelbl.getText());
        p2.setSalesID(salesID.getText());
        
        double sprofit = p2.getSalesProfit();
        String sdate = p2.getSalesDate();
        String sid = p2.getSalesID();
        
        p2.addProfit();
        p2.loadProfitTable(salesProfit);
        resetProfit();
    }//GEN-LAST:event_addProfitbtnActionPerformed

    private void monthlyProfitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyProfitbtnActionPerformed
        Profit.MonthlyProfit mp1 = new Profit.MonthlyProfit();
        
        mp1.setProfitMonth(profitSearchtxt.getText());
        String month = mp1.getProfitMonth();
        
        mp1.searchMonthlyProfit(month, profitTable);
    }//GEN-LAST:event_monthlyProfitbtnActionPerformed

    private void profitTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profitTableMouseClicked
        int row = profitTable.getSelectedRow();
        
        String pid = profitTable.getValueAt(row,0).toString();
        double totprofit =(Double) profitTable.getValueAt(row,1);
        String month = profitTable.getValueAt(row,2).toString();
        
        profitIDLable.setText(pid);
        profitAmount.setText(String.valueOf(totprofit));
        monthtxt.setText(month);
        monthtxt.setEditable(false);
        
        profitSearchtxt.setText(month);
        
    }//GEN-LAST:event_profitTableMouseClicked

    private void salesincomeSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesincomeSearchbtnActionPerformed
       
       Profit p4 = new Profit();
       
       String year = yearsearch.getText();
       String month = monthsearch.getText();
       
       if(!" ".equals(year))
       {
           if(!" ".equals(month))
           {
                boolean result = p4.isMonthInt(month);
                if(result==true)
                {
                    String sdate = year+"-"+month;
                    p4.searchProfit(sdate, SalesIncomeTable);
                }
                else
                {
                    p4.displayRecordsByMonthYear(month, year, SalesIncomeTable);
                }
           }
           else
           {
               p4.searchProfit(year, SalesIncomeTable);
           }
       }
       else
       {
           if(!" ".equals(month))
           {
                boolean result = p4.isMonthInt(month);
                if(result==true)
                {
                    p4.searchProfit(month, SalesIncomeTable);
                }
                else
                {
                    p4.displayRecordsByMonthYear(month, year, SalesIncomeTable);
                }
           }
           else
           {
               p4.searchProfit(year, SalesIncomeTable);
           }
       }

       monthtxt.setEditable(true);
       this.resetMonthlyProfit();
    }//GEN-LAST:event_salesincomeSearchbtnActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        Profit.MonthlyProfit m2=new Profit.MonthlyProfit();
        
        m2.setProfitMonth(monthtxt.getText());
        String month =m2.getProfitMonth();
        
        m2.setTotalProfit(Double.parseDouble(profitAmount.getText()));
        double tot = m2.getTotalProfit();
        
        m2.addMonthlyProfit();
        m2.loadMonthlyProfitTable(profitTable);
        resetMonthlyProfit();
       
        
    }//GEN-LAST:event_jButton55ActionPerformed

    private void itemSearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSearchbtnActionPerformed
        DesignItem d3 = new DesignItem();
        
        d3.setDesignItemNo(itemSearchtxt.getText());
        String itemSearch = d3.getDesignItemNo();
        
        d3.searchItemResultTable(itemSearch, itemtableDesign);
    }//GEN-LAST:event_itemSearchbtnActionPerformed

    private void itemtableDesignMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemtableDesignMouseClicked
        int row = itemtableDesign.getSelectedRow();
        
        String itemNo = itemtableDesign.getValueAt(row, 0).toString();
        String itemName = itemtableDesign.getValueAt(row, 1).toString();
        double unitPrice = (double) itemtableDesign.getValueAt(row, 2);
        int qty = (int) itemtableDesign.getValueAt(row, 3);
        String supplier = itemtableDesign.getValueAt(row, 4).toString();
        
        
        itemCodelable.setText(itemNo);
        itemNametxt.setText(itemName);
        unitPricetxt.setText(String.valueOf(unitPrice));
        qtytxt.setText(String.valueOf(qty));
        supplierBox.setSelectedItem(supplier);
        itemSearchtxt.setText(itemNo);
        
    }//GEN-LAST:event_itemtableDesignMouseClicked

    private void remainingQtybtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remainingQtybtn1ActionPerformed
        
        try 
            {
                String sqli = "SELECT itemNo,stockCount FROM designitems";
                pst=conn.prepareStatement(sqli);
                rs =pst.executeQuery();
                
                remainQtyTable.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
        
      //display dialog box
        dialog.remove(assignTechnicianPanel);
        dialog.remove(supplierContact);
        dialog.remove(emailPanel);
        
        dialog.add(remainQuantityPanel);
        dialog.setSize(640, 620);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Design Item Remain Quantity");
        dialog.setVisible(true);
    }//GEN-LAST:event_remainingQtybtn1ActionPerformed

    private void addItembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItembtnActionPerformed
        DesignItem dei1 = new DesignItem();
        
        dei1.setDesignItemName(itemNametxt.getText());
        dei1.setItemUnitPrice(Double.parseDouble(unitPricetxt.getText()));
        dei1.setItemStockCount(Integer.parseInt(qtytxt.getText()));
        dei1.setItemSupplier(supplierBox.getSelectedItem().toString());
        
        String iname = dei1.getDesignItemName();
        double unitPrice = dei1.getItemUnitPrice();
        int qty = dei1.getItemStockCount();
        String supplier = dei1.getItemSupplier();
        
        Validate v1 = new Validate();
        if(v1.ValidPrice(unitPrice))
        {
            if(v1.ValidQuantity(qty))
            {
                //Insert entries to the database
                dei1.addDesignItemEntry();
                dei1.loadDesignItemTable(itemtableDesign);
                resetDesignItems();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Stock Count");
                qtytxt.setText(null);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid Price");
            unitPricetxt.setText(null);
        }

        
        
               
    }//GEN-LAST:event_addItembtnActionPerformed

    private void itemUpdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUpdatebtnActionPerformed
        DesignItem d4 = new DesignItem();
        
        d4.setDesignItemNo(itemCodelable.getText());
        d4.setDesignItemName(itemNametxt.getText());
        d4.setItemUnitPrice(Double.parseDouble(unitPricetxt.getText()));
        d4.setItemStockCount(Integer.parseInt(qtytxt.getText()));
        d4.setItemSupplier(supplierBox.getSelectedItem().toString());
        
        String itemNo = d4.getDesignItemNo();
        String itemname= d4.getDesignItemName();
        double unitPrice = d4.getItemUnitPrice();
        int qty = d4.getItemStockCount();
        String supplier = d4.getItemSupplier();
     
        Validate v2 = new Validate();
        
        if(v2.ValidPrice(unitPrice))
        {
            if(v2.ValidQuantity(qty))
            {
                //update entries of database
                d4.updateDesignItem(itemNo);
                d4.loadDesignItemTable(itemtableDesign);
                this.resetDesignItems();
                
                d4.notifyLowQuantity(lowqtyLable);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Stock Count");
                qtytxt.setText(null);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid Price");
            unitPricetxt.setText(null);
        }
        
        
        
    }//GEN-LAST:event_itemUpdatebtnActionPerformed

    private void removeItembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItembtnActionPerformed
       DesignItem d5 = new DesignItem();
       
       d5.setDesignItemNo(itemCodelable.getText());
       String itemNo = d5.getDesignItemNo();
       
       //Remove record from the dtabase
       d5.removeDesignItem(itemNo);
       d5.loadDesignItemTable(itemtableDesign);
       this.resetDesignItems();
       
    }//GEN-LAST:event_removeItembtnActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        ManageItemsPanel.setVisible(false);
        DesignOrderPanelScreen.setVisible(false);
        SalesDesignPanel.setVisible(true);
        DesignProfitPanel.setVisible(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        ManageItemsPanel.setVisible(true);
        DesignOrderPanelScreen.setVisible(false);
        SalesDesignPanel.setVisible(false);
        DesignProfitPanel.setVisible(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        ManageItemsPanel.setVisible(false);
        DesignOrderPanelScreen.setVisible(false);
        SalesDesignPanel.setVisible(false);
        DesignProfitPanel.setVisible(true);
            designprofitViewPanel.setVisible(false);
            //profitCalcPanel.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void resetItembtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetItembtnActionPerformed
        this.resetDesignItems();
    }//GEN-LAST:event_resetItembtnActionPerformed

    private void designTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_designTableMouseClicked
        this.resetDesign();
        
        int row= designTable.getSelectedRow();
        
        String dcode = designTable.getValueAt(row, 0).toString();
        String dtype = designTable.getValueAt(row, 1).toString();
        String vtype = designTable.getValueAt(row, 2).toString();
        double dcost = (Double)designTable.getValueAt(row, 3);
        
        designCodelable.setText(dcode);
        designComboBox.setSelectedItem(dtype);
        vehicleTypeComboBox.setSelectedItem(vtype);

        Design.DesignColor dc1 = new Design.DesignColor();
        dc1.searchColorEntry(dcode, designColour);
        
        Design.ItemUsage du = new Design.ItemUsage();
        du.searchItemUsage(dcode, itemUsageTable);
       
        designComboBox.setEnabled(false);
        vehicleTypeComboBox.setEnabled(false);
        attachbtn.setEnabled(false);
        
        //diable updating item usage
        itemTable.setEnabled(false);
        addRowBtn.setEnabled(false);
        removeRowBtn.setEnabled(false);
        additemUsageTable.setEnabled(false);
        
        //if there are updated item prices, design cost should be updated.
        int rowCount = itemUsageTable.getRowCount();
        int qty;
        String ditemNo;
        double itemCost;
        double totCost=0;
        
        Design de=new Design();
    
        while(rowCount!=0)
        {
            qty = Integer.parseInt(itemUsageTable.getValueAt(rowCount-1, 2).toString());
            ditemNo = itemUsageTable.getValueAt(rowCount-1, 1).toString();
            
            itemCost = de.getUpdatedItemCost(ditemNo, qty);
            totCost = totCost + itemCost;
            
            rowCount--;
        }



        if(dcost!=totCost)
        {
            JOptionPane.showMessageDialog(null,"Design cost should be updated due to updated item prices.You need to update");
            
            de.updateDesign(dcode, totCost);
            de.loadDesignTable(designTable);
            totDesignCostlable.setText(String.valueOf(totCost));
        }
        else
        {
            totDesignCostlable.setText(String.valueOf(dcost));
        }
        
        
        //view design colours
        String color[] = dc1.getColourArray(dcode);
        
        for(String clr : color)
        {
             switch(clr)
             {
                 case "Blue" : bluechk.setSelected(true);
                               break;
                 case "Black" : blackchk.setSelected(true);
                               break;
                 case "Red" : redchk.setSelected(true);
                               break;
                 case "Pearl" : pearlchk.setSelected(true);
                               break;
                 case "Gold" : goldchk.setSelected(true);
                               break;
                 case "Brown" : brownchk.setSelected(true);
                               break;
                 default : System.out.println("No colors");
                                
             }
        }
        
        
        try 
        {
            String query ="SELECT designImage FROM design WHERE designCode='"+dcode+"'";
            pst=conn.prepareStatement(query);
            rs =pst.executeQuery();
            
            if(rs.next())
            {


                //System.out.print("Image"+image);


//                 InputStream ins = rs.getBinaryStream("designImage");
//                 BufferedImage buff = ImageIO.read(ins);
                
//                 BufferedImage outimage = new BufferedImage(imagelable.getWidth(), imagelable.getHeight(), BufferedImage.TYPE_INT_RGB);                 
//                 Image img = outimage.getScaledInstance(imagelable.getWidth(), imagelable.getHeight(), Image.SCALE_SMOOTH);
//                 ImageIcon icon = new ImageIcon(img);
//                 imagelable.setIcon(icon);

                InputStream ins = rs.getBinaryStream("designImage");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int a = ins.read();
                
                while(a >=0)
                {
                    out.write((char)a);
                    a = ins.read();
                }
                
                Image img = Toolkit.getDefaultToolkit().createImage(out.toByteArray());
                ImageIcon icon = new ImageIcon(img);
                    imagelable.setIcon(icon);
                 
                 
                
//                    Blob blob = rs.getBlob("designImage");
//                    int blobLength = (int) blob.length();  
//                    byte[] bytes = blob.getBytes(1, blobLength);
//                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
//                    Image image = img;
//                    ImageIcon icon = new ImageIcon(image);
//                    imagelable.setIcon(icon);
                
            }
            
           else
            {
                JOptionPane.showMessageDialog(null, "Error has ocured");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e + "Image Loading error");
        }
        
        
    }//GEN-LAST:event_designTableMouseClicked

    private void resetDesignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetDesignActionPerformed
        designComboBox.setEnabled(true);
        vehicleTypeComboBox.setEnabled(true);
        attachbtn.setEnabled(true);
        addRowBtn.setEnabled(true);
        removeRowBtn.setEnabled(true);

        this.resetDesign();
    }//GEN-LAST:event_resetDesignActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        this.resetAdvancePayment();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            
            Profit.MonthlyProfit m1= new Profit.MonthlyProfit();
            double tot = m1.calcTotProfit(SalesIncomeTable, 1);
            profitAmount.setText(String.valueOf(tot));

            Profit pr1 = new Profit();
            
            String year = yearsearch.getText();
            String month =m1.findProfitMonthString( monthsearch.getText());
            
            String pdate=year+" "+month;
 
            monthtxt.setText(pdate);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        String emp =empSearch.getText();
        
        try
        {
            String sql3="SELECT Pemp_Id,First_Name,Last_Name,Mobile FROM permenent_employee WHERE Position_Title='Technician' AND Pemp_Id like '%"+emp+"%'";
            pst=conn.prepareStatement(sql3);
            rs=pst.executeQuery();
            
            empTable.setModel(DbUtils.resultSetToTableModel(rs));
            

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_jButton18ActionPerformed

    private void empTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empTableMouseClicked
        int row = empTable.getSelectedRow();

        String eid = empTable.getValueAt(row, 0).toString();
        String fname = empTable.getValueAt(row, 1).toString();
        String lname = empTable.getValueAt(row, 2).toString();
        String phone = empTable.getValueAt(row, 3).toString();
      
        eidlable.setText(eid);
        fameLbl.setText(fname);
        lnameLbl.setText(lname);
        phonelbl.setText(phone);
    }//GEN-LAST:event_empTableMouseClicked

    private void assignTechnicianBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignTechnicianBtnActionPerformed
    
        String empid = eidlable.getText();
        techlbl.setText(empid);
        dialog.dispose();
            
    }//GEN-LAST:event_assignTechnicianBtnActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        DesignItem d4 = new DesignItem();
        
        d4.setDesignItemNo(itemSearchRemain.getText());
        String itemSearch = d4.getDesignItemNo();
        
        try 
            {
                String query2 = "SELECT itemNo,stockCount FROM designitems WHERE itemNo like '%"+itemSearch+"%'";
                pst=conn.prepareStatement(query2);
                rs =pst.executeQuery();
                
                remainQtyTable.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void contactSupplierBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactSupplierBtnActionPerformed
        //load supplier table
        int row =remainQtyTable.getSelectedRow();
        String itemno = remainQtyTable.getValueAt(row, 0).toString();
        
        DesignItem di2 = new DesignItem();
        String supCode = di2.findSupplier(itemno);
        
        try 
        {
            String q = "SELECT * FROM supplier WHERE supplierCode ='"+supCode+"'";
            pst = conn.prepareStatement(q);
            rs= pst.executeQuery();
            
            supplierTable.setModel(DbUtils.resultSetToTableModel(rs));
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,e);
        }
        
        
        dialog.remove(remainQuantityPanel);
        dialog.remove(assignTechnicianPanel);
        dialog.remove(emailPanel);
        
        dialog.add(supplierContact);
        dialog.setSize(712,701);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Contact Supplier");
        dialog.setVisible(true);
    }//GEN-LAST:event_contactSupplierBtnActionPerformed

    private void exitRemainQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitRemainQtyActionPerformed
        dialog.dispose();
    }//GEN-LAST:event_exitRemainQtyActionPerformed

    private void emailSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailSupplierActionPerformed
        dialog.remove(remainQuantityPanel);
        dialog.remove(assignTechnicianPanel);
        dialog.remove(supplierContact);
        
        dialog.add(emailPanel);
        dialog.setSize(683,587);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Email");
        dialog.setVisible(true);
        
        emailRecieverlbl.setText(emailLable.getText());
    }//GEN-LAST:event_emailSupplierActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        String supCode = supplierSearch.getText();
        
        try 
            {
                String query3 = "SELECT supplierCode,companyName,fname,lname,phone,email FROM supplier WHERE supplierCode like '%"+supCode+"%'";
                pst=conn.prepareStatement(query3);
                rs =pst.executeQuery();
                
                supplierTable.setModel(DbUtils.resultSetToTableModel(rs));
                
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, e);
            }
    }//GEN-LAST:event_jButton33ActionPerformed

    private void exitTechnicianDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitTechnicianDialogActionPerformed
        dialog.dispose();
    }//GEN-LAST:event_exitTechnicianDialogActionPerformed

    private void ExitSupplierContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitSupplierContactActionPerformed
        dialog.dispose();
    }//GEN-LAST:event_ExitSupplierContactActionPerformed

    private void supplierTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierTableMouseClicked
        int row = supplierTable.getSelectedRow();
        
        String scode = supplierTable.getValueAt(row,0).toString();
        String cname = supplierTable.getValueAt(row,1).toString();
        String fname = supplierTable.getValueAt(row,2).toString();
        String lname = supplierTable.getValueAt(row,3).toString();
        int phone = Integer.parseInt(supplierTable.getValueAt(row,4).toString());
        String email = supplierTable.getValueAt(row,5).toString();
        
        scodeLable.setText(scode);
        companyNamelbl.setText(cname);
        fnameLable.setText(fname);
        lnameLable.setText(lname);
        phonelable.setText(String.valueOf(phone));
        emailLable.setText(email);
        
    }//GEN-LAST:event_supplierTableMouseClicked

    private void subjecttxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjecttxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subjecttxtActionPerformed

    private void emailSendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailSendBtnActionPerformed
        Email e1 = new Email();
        
        e1.setReceiver(emailRecieverlbl.getText());
        e1.setSubj(subjecttxt.getText());
        e1.setMsgBody(msgbodyarea.getText());
        
        String receiver = e1.getReceiver();
        String subject = e1.getSubj();
        String msg = e1.getMsgBody();
        
        Validate v8 = new Validate();
        
        if(v8.validateEmail(receiver))
        {
            e1.emailSupplier();
            this.resetEmail();

        }
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid Email");
            emailRecieverlbl.setText(null);
        }
        
            dialog.remove(remainQuantityPanel);
            dialog.remove(assignTechnicianPanel);
            dialog.remove(emailPanel);

            dialog.add(supplierContact);
            dialog.setSize(715,871);
            dialog.setLocationRelativeTo(null);
            dialog.setTitle("Contact Supplier");
            dialog.setVisible(true);
        
        
        
        
    }//GEN-LAST:event_emailSendBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dialog.remove(remainQuantityPanel);
        dialog.remove(assignTechnicianPanel);
        dialog.remove(emailPanel);
        
        dialog.add(supplierContact);
        dialog.setSize(715,871);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Contact Supplier");
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
      
        //String location ="E:\\ITP Original\\CarAvenueInteriorDesignOriginal\\Reports\\monthlyProfit.jrxml";
        String location ="E:\\ITP Original\\CarAvenueInteriorDesignOriginal\\src\\InteriorDesignReports\\monthlyProfit.jrxml";
        //String location =".\\monthlyProfit.jrxml";
        
        try 
        {
            report re = new report(location ,dbConnect.connect());
            re.generateReport();
        } 
        catch (Exception e) 
        {
        }
    }//GEN-LAST:event_jButton53ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InteriorDesignScreens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InteriorDesignScreens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InteriorDesignScreens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InteriorDesignScreens.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        
        try 
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
        {
        }
        
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InteriorDesignScreens().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdvancePayPane;
    private javax.swing.JPanel DesignOrderPane;
    private javax.swing.JPanel DesignOrderPane1;
    private javax.swing.JPanel DesignOrderPanelScreen;
    private javax.swing.JTabbedPane DesignOrderTabbedPane;
    private javax.swing.JTabbedPane DesignOrderTabbedPane1;
    private javax.swing.JPanel DesignOrdersViewPanel;
    private javax.swing.JPanel DesignProfitPanel;
    private javax.swing.JTabbedPane DesignSalesTabbedPanel;
    private javax.swing.JButton ExitSupplierContact;
    private javax.swing.JPanel InteriorDesignPane;
    private javax.swing.JPanel InteriorDesignPane1;
    private javax.swing.JPanel ManageItemsPanel;
    public static javax.swing.JPanel ParentPanel;
    private javax.swing.JPanel PaymentPane;
    private javax.swing.JPanel SalesDesignPanel;
    private javax.swing.JTable SalesIncomeTable;
    private javax.swing.JPanel SalesPanel;
    private javax.swing.JPanel SidebarPanel;
    private javax.swing.JButton addDesignbtn;
    private javax.swing.JButton addItembtn;
    private javax.swing.JButton addProfitbtn;
    private javax.swing.JButton addRowBtn;
    private javax.swing.JButton addSalesbtn;
    private javax.swing.JTable additemUsageTable;
    private javax.swing.JLabel advancePayview;
    private javax.swing.JTextField advanceSearch;
    private javax.swing.JButton advanceSearchbtn;
    private javax.swing.JTable advanceTableView;
    private javax.swing.JTextField advanceUpdatetxt;
    private javax.swing.JLabel amountlbl;
    private javax.swing.JButton assignTechnicianBtn;
    private javax.swing.JPanel assignTechnicianPanel;
    private javax.swing.JButton attachbtn;
    private javax.swing.JTextField balanceUpdate;
    private javax.swing.JCheckBox blackchk;
    private javax.swing.JCheckBox bluechk;
    private javax.swing.JCheckBox brownchk;
    private javax.swing.JPanel checkboxPanel;
    private javax.swing.JLabel closeorderLable;
    private javax.swing.JLabel companyNamelbl;
    private javax.swing.JButton contactSupplierBtn;
    private javax.swing.JLabel custnic;
    private javax.swing.JTextField custnictxt;
    private javax.swing.JLabel dcLable;
    private javax.swing.JLabel dcostLable;
    private javax.swing.JLabel designCodelable;
    private javax.swing.JTable designColour;
    private javax.swing.JComboBox<String> designComboBox;
    private javax.swing.JTextField designOrderSearch;
    private javax.swing.JPanel designPanel;
    private javax.swing.JPanel designPanel1;
    private javax.swing.JTextField designSearch;
    private javax.swing.JTable designTable;
    private javax.swing.JTable designTable2;
    private javax.swing.JTable designTableProfit;
    private javax.swing.JComboBox<String> designcmb;
    private javax.swing.JTable designorderTable;
    private javax.swing.JTextField designprofitSearchtxt;
    private javax.swing.JPanel designprofitViewPanel;
    private javax.swing.JTextArea desttxt;
    private javax.swing.JLabel ecostLable;
    private javax.swing.JLabel eidlable;
    private javax.swing.JLabel emailLable;
    private javax.swing.JPanel emailPanel;
    private javax.swing.JTextField emailRecieverlbl;
    private javax.swing.JButton emailSendBtn;
    private javax.swing.JButton emailSupplier;
    private javax.swing.JTextField empSearch;
    private javax.swing.JTable empTable;
    private javax.swing.JButton exitRemainQty;
    private javax.swing.JButton exitTechnicianDialog;
    private javax.swing.JLabel extraLable;
    private javax.swing.JTextField extraacostlbl;
    private javax.swing.JLabel fameLbl;
    private javax.swing.JLabel fnameLable;
    private javax.swing.JLabel fullpaidDatelbl;
    private javax.swing.JCheckBox goldchk;
    private javax.swing.JTextField imageFiletxt;
    private javax.swing.JLabel imagelable;
    private javax.swing.JLabel indexlbl;
    private javax.swing.JLabel indexlbl1;
    private javax.swing.JLabel itemCodelable;
    private javax.swing.JTextField itemNametxt;
    private javax.swing.JTextField itemNoSearch;
    private javax.swing.JTextField itemSearchRemain;
    private javax.swing.JButton itemSearchbtn;
    private javax.swing.JTextField itemSearchtxt;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton itemUpdatebtn;
    private javax.swing.JTable itemUsageTable;
    private javax.swing.JTable itemtableDesign;
    private javax.swing.JButton itemusageSearchBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane36;
    private javax.swing.JScrollPane jScrollPane38;
    private javax.swing.JScrollPane jScrollPane39;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane40;
    private javax.swing.JScrollPane jScrollPane41;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lnameLable;
    private javax.swing.JLabel lnameLbl;
    public static javax.swing.JLabel lowqtyLable;
    private javax.swing.JButton monthlyProfitbtn;
    private javax.swing.JTextField monthsearch;
    private javax.swing.JTextField monthtxt;
    private javax.swing.JTextArea msgbodyarea;
    private javax.swing.JLabel niclable;
    private javax.swing.JPanel notificationPanel;
    private javax.swing.JTextField onGogingSearch;
    private javax.swing.JTable onGoingtable;
    private javax.swing.JButton orderSearchbtn;
    private javax.swing.JTable orderTable;
    private javax.swing.JButton orderUpdatebtn;
    private com.toedter.calendar.JDateChooser orderdate;
    private javax.swing.JLabel paymentIDlbl;
    private javax.swing.JButton paymentSearchbtn;
    private javax.swing.JTable paymentTable;
    private javax.swing.JCheckBox pearlchk;
    private javax.swing.JLabel phonelable;
    private javax.swing.JLabel phonelbl;
    private javax.swing.JButton profitAddSearchbtn;
    private javax.swing.JLabel profitAmount;
    private javax.swing.JPanel profitCalcPanel;
    private javax.swing.JLabel profitIDLable;
    private javax.swing.JLabel profitIdLbl1;
    private javax.swing.JButton profitSearchbtn;
    private javax.swing.JTextField profitSearchtxt;
    private javax.swing.JTable profitTable;
    private javax.swing.JTextField profitdesignsearch;
    private javax.swing.JLabel profitlbl;
    private javax.swing.JTextField qtytxt;
    private javax.swing.JCheckBox redchk;
    private javax.swing.JTable remainQtyTable;
    private javax.swing.JPanel remainQuantityPanel;
    private javax.swing.JButton remainingQtybtn1;
    private javax.swing.JButton removeDesignbtn;
    private javax.swing.JButton removeItembtn;
    private javax.swing.JButton removePaymentbtn;
    private javax.swing.JButton removeRowBtn;
    private javax.swing.JButton resetDesign;
    private javax.swing.JButton resetItembtn;
    private javax.swing.JButton resetSalesbtn;
    private com.toedter.calendar.JDateChooser returndate;
    private javax.swing.JLabel salesDatelbl;
    private javax.swing.JLabel salesDatelbl1;
    private javax.swing.JLabel salesID;
    private javax.swing.JLabel salesID1;
    private javax.swing.JLabel salesIncomeLable;
    private javax.swing.JTextField salesPriceTxt;
    private javax.swing.JTable salesProfit;
    private javax.swing.JTextField salesSearch;
    private javax.swing.JButton salesSearchbtn;
    private javax.swing.JTable salesTable;
    private javax.swing.JTable salesTableProfit;
    private javax.swing.JButton salesincomeSearchbtn;
    private javax.swing.JLabel saleslable;
    private javax.swing.JLabel scodeLable;
    private javax.swing.JButton sellButton;
    private javax.swing.JTextField serviceChargetxt;
    private javax.swing.JButton settlePaymentbtn;
    private javax.swing.JTextField subjecttxt;
    private javax.swing.JComboBox<String> supplierBox;
    private javax.swing.JPanel supplierContact;
    private javax.swing.JPanel supplierRecordPanel1;
    private javax.swing.JTextField supplierSearch;
    private javax.swing.JTable supplierTable;
    private javax.swing.JLabel techlbl;
    private javax.swing.JPanel technicianPanel1;
    private javax.swing.JLabel totDesignCostlable;
    private javax.swing.JLabel totcostLable;
    private javax.swing.JTextField unitPricetxt;
    private javax.swing.JButton updateDesignbtn;
    private javax.swing.JButton updateSalesbtn;
    private javax.swing.JComboBox<String> vehicleTypeComboBox;
    private javax.swing.JTextField viewAdvanceSearch;
    private javax.swing.JTable viewAdvanceTable;
    private javax.swing.JLabel viewDesignCodelbl;
    private javax.swing.JTextField viewFullPaySearch;
    private javax.swing.JLabel viewNIClbl;
    private javax.swing.JLabel viewPaidDate;
    private javax.swing.JLabel viewSalesDatelbl;
    private javax.swing.JLabel viewSalesLable;
    private javax.swing.JLabel viewcustNic;
    private javax.swing.JTextField yearsearch;
    // End of variables declaration//GEN-END:variables
}
