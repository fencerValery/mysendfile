package com.fencer.su;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author Valery Klimko
 */

public class MySendMail {

   public final  String from = "val-klimovch@yandex.ru";
   public final String host = "smtp.yandex.ru";
   public final int port = 465;
   public final String login = "val-klimovch";
   private Properties props;
   public static String filename = "F:\\MySendMail.java";
  
   public MySendMail(){
        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
		props.put("mail.mime.charset",  "UTF-8");  
   }



    public  synchronized void sendMailText(String to) {

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, "(V1967765v)");
			}
		});

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new java.util.Date());
            msg.setText("");
						
            Transport.send(msg);
			System.out.println("GOOOD!!");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
	
   public  synchronized void sendMailFile(String to){ 
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, "");
			}
		});

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new java.util.Date());
            MimeBodyPart p1 = new MimeBodyPart();
            p1.setText("This is part one of a test multipart e-mail." +
                    "The second part is file as an attachment");
            MimeBodyPart p2 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(filename);
            p2.setDataHandler(new DataHandler(fds));
            p2.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(p1);
            mp.addBodyPart(p2);
            msg.setContent(mp);
            Transport.send(msg);
		
			System.out.println("GOOOD!!");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	 }
	 
	 public static void main(String args[]){
	    SwingUtilities.invokeLater(new Runnable(){
         public void run(){
             new BookForMail();
         }
     }); 
  }
}



class DataBase {

    private static final String url = "jdbc:mysql://localhost:3306/NewVeb";
    private static final String user = "root";
    private static final String password = "1234";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
	private static ArrayList<ContactSql> list = new ArrayList<ContactSql>();
	
	 public  static ArrayList<ContactSql> getList(){
          return list;
      }  
    
    public static synchronized void  createDataBase(){   
        try {
            
            String s = "CREATE DATABASE IF NOT EXISTS newveb;";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306", user, password);
            stmt = con.createStatement();
            stmt.execute(s); 
            System.out.println("База создана или уже существует.");
            con.close();
            stmt.close();
                  
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }     
    }
	
	
	
    public static synchronized void  createTable(){   
        try {
            
            String s = "CREATE TABLE IF NOT EXISTS NameMail (mail varchar(200) NOT NULL UNIQUE,"
                    + " name varchar(150))"
                    + " ENGINE InnoDB CHARACTER SET utf8;";
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt.execute(s); 
            System.out.println("Таблица создана или уже существует.");
            con.close();
            stmt.close();
                  
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }     
    }
    
     public static synchronized void  deleteFromTable(String s){   
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt.executeUpdate(s); 
            con.close();
            stmt.close();       
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }     
    }
	
	  public  static synchronized ArrayList<ContactSql> selectFromTable(){
         String query = "select * from namemail ";  
        try {
		
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
			
               while (rs.next()) {
                String a = rs.getString("mail"); 
                String b = rs.getString("name");      				
                list.add(new ContactSql(a, b));
               }
			   
             con.close();
             stmt.close();  
             rs.close();
            
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
         return list;
    }
    
     
      public static synchronized void  insertToTable(String s){   
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt.executeUpdate(s); 
            con.close();
            stmt.close();       
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }     
    }
	
}


	
	
 class BookForMail{
   private JFrame jfr;
   private JButton jbuttonContact;
   private JButton jbuttonAdd;
   private JButton jbuttonDelete;
   private JButton jbuttonSend;
   private JButton jbuttonExit;
   private JLabel allContact;
   
   public BookForMail(){
          DataBase.createTable();
          DataBase.selectFromTable();
	  jfr = new JFrame("My contacts");
          jfr.setSize(250, 350);
          jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   JPanel pan = new JPanel();
	   pan.setSize(200, 300);
	   pan.setLayout(new GridLayout(6, 1));
	   jbuttonContact = new JButton("show all Book");
	   jbuttonAdd = new JButton("add");
	   jbuttonDelete = new JButton("delete");
	   jbuttonSend = new JButton("send");
	   jbuttonExit = new JButton("exit");
	   allContact = new JLabel("", SwingConstants.CENTER);
	   JScrollPane scroll = new JScrollPane(allContact);
           scroll.setViewportBorder(BorderFactory.createLineBorder(Color.GREEN, 7));
	   
	   pan.add(jbuttonContact);
	   pan.add(jbuttonAdd);
	   pan.add(jbuttonDelete);
	   pan.add(jbuttonSend);
	   pan.add(scroll);
	   pan.add(jbuttonExit);
           
   jbuttonContact.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
                if (ae.getActionCommand().equals("show all Book")){
                    String s, g = "";
                    int lst = DataBase.getList().size();
                    for (int i = 0; i < lst; i++){
                     s = "" + (i + 1) + "<br>mail:    " + DataBase.getList().get(i).getMailSql()
		         + "<br>name:    " + DataBase.getList().get(i).getNameSql() + "<br>"; 
                     g = g + s;
                    }
                    allContact.setText("<html>" + g); 
                 }
                
            }
        });
		
		
   jbuttonAdd.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
              if (ae.getActionCommand().equals("add")){
                String mail = JOptionPane.showInputDialog("Enter mail");
                String name = JOptionPane.showInputDialog("Enter name");
               
                ContactSql javaContact = new ContactSql(mail, name);
                String pol = "insert into namemail values ('" + javaContact.getMailSql() + "', "
				+ "'" + javaContact.getNameSql() + "');";
               
                DataBase.insertToTable(pol);
                DataBase.getList().add(javaContact);
                jbuttonContact.doClick(); 
              }
          }
      });
	  
	  
   jbuttonDelete.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
           if (ae.getActionCommand().equals("delete")){
                String number;
                int numb;
                number = JOptionPane.showInputDialog("<html>Enter number.<br>"
                 + "MIN >= 1"      + " MAX <= " + DataBase.getList().size());
                numb = Integer.parseInt(number); 
               
                String pol = "delete from namemail where mail='" + DataBase.getList().get(numb - 1).getMailSql() + "';";
                DataBase.deleteFromTable(pol);
                DataBase.getList().remove(numb - 1);
                jbuttonContact.doClick();
               }            
           }            
      });

   jbuttonSend.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae){
              if (ae.getActionCommand().equals("send")){
                String mail = JOptionPane.showInputDialog("Enter number bookForMail");
				int intMail = Integer.parseInt(mail);
                MySendMail fileSend = new MySendMail();
				fileSend.sendMailFile(DataBase.getList().get(intMail - 1).getMailSql()); 
              }
          }
      });	  
   
  jbuttonExit. addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent ae){
        System.exit(0);
           }  
        });    
   
        jfr.add(pan);   
        jfr.setVisible(true); 
   }
   
}

class ContactSql{
  private String mailSql = null;
  private String nameSql = null;
  
  ContactSql(){}
  
  ContactSql(String mailSql, String nameSql){
     this.mailSql = mailSql;
     this.nameSql = nameSql;
  }
  
  public String getMailSql(){
    return mailSql;
  }
 
  public void  setMailSql(String mailSql){
     this.mailSql = mailSql;  
  }
  
  public String getNameSql(){
    return nameSql;
  }
 
  public void  setNameSql(String nameSql){
     this.nameSql = nameSql;  
  }
}

    