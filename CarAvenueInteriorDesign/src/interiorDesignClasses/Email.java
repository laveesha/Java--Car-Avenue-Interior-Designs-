/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interiorDesignClasses;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;


/**
 *
 * @author User
 */
public class Email {
    
    private String to;
    private String msgBody;
    private String subj;

    public Email() 
    {
        this.to = null;
        this.msgBody = null;
        this.subj = null;
    }

    //set methods
    public void setReceiver(String receiver) 
    {
        this.to = receiver;
    }

    public void setMsgBody(String msg) 
    {
        this.msgBody = msg;
    }

    public void setSubj(String subj) 
    {
        this.subj = subj;
    }
    
    //get methods

    public String getReceiver() {
        return to;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public String getSubj() {
        return subj;
    }
  
    
    public void emailSupplier()
    {
        try 
        {
            String host ="smtp.gmail.com";
            String user ="laveesha.chandrasiri@my.sliit.lk";
            String pwd ="laveesha@95";
            String sender = "laveesha.chandrasiri@my.sliit.lk";
            String receiver= this.to;
            String subject = this.subj;
            String messageBody = this.msgBody;
            boolean session = false;
            
            //set different types of properties
            Properties pro = System.getProperties();
            
            pro.put("mail.smtp.auth", "true");
            pro.put("mail.smtp.starttls.enable", "true");
            pro.put("mail.smtp.host", host);
            pro.put("mail.smtp.port", "587");
            pro.put("mail.smtp.starttls.required", "true");
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(pro, null);
            mailSession.setDebug(session);
            
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(sender)); //sender email
            InternetAddress[] addr = {new InternetAddress(receiver)};
            msg.setRecipients(Message.RecipientType.TO, addr);  //receiver email
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageBody);
            
            Transport trans = mailSession.getTransport("smtp"); //mail server that used to send mail
            trans.connect(host, user, pwd); //authenticate user
            trans.sendMessage(msg, msg.getAllRecipients());
            trans.close();
            
            JOptionPane.showMessageDialog(null,"Email sent successfully");
            
           
        } 
        catch(AddressException e2)
        {
            JOptionPane.showMessageDialog(null,"Invalid Address");
        }
        catch(MessagingException e1)
        {
            JOptionPane.showMessageDialog(null,"Connection Failed.");
        }
        catch(Exception e3)
        {
            JOptionPane.showMessageDialog(null, e3);
        }

    }
    
}
