package com.fencer.su;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;



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
	 
}





	
	
 