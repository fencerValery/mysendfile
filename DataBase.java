package com.fencer.su;



import java.util.*;
import java.io.*;
import java.sql.*;

public class DataBase {

      // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/NewVeb";
    private static final String user = "root";
    private static final String password = "1234";
 
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
	//ArrayList for record email
	private static ArrayList<ContactSql> list = new ArrayList<ContactSql>();
	
	 public  static ArrayList<ContactSql> getList(){
          return list;
      }  
    
    public static synchronized void  createDataBase(){   
        try {
            
            String s = "CREATE DATABASE IF NOT EXISTS newveb;";
            // opening database connection to MySQL server
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306", user, password);
 
            // getting Statement object to execute query
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
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
 
            // getting Statement object to execute query
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
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
 
            // getting Statement object to execute query
            stmt = con.createStatement();
           
            // executing SELECT query
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


	
	
 