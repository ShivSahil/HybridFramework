package com.personal.utilities;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.personal.base.BaseClass;
 
 
 
public class MailUtility extends BaseClass
{
  
	
	public void mailSend() throws AddressException, MessagingException
	{
		if(configuration.getProperty("SendMail").contains("true"))
		{
			sendMail(MailDetails.server, MailDetails.from, MailDetails.to, MailDetails.subject, MailDetails.messageBody, MailDetails.attachmentPath, MailDetails.attachmentName);
		}
	}
	
	
	
	
  // public void sendMail(String mailServer, String from, String[] to, String subject, String messageBody)  - you may use it, if not sending any attachments
    private void  sendMail(String mailServer, String from, String[] to, String subject, String messageBody, String attachmentPath, String attachmentName) throws MessagingException, AddressException
    {
        boolean debug = false;
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable","true");
        props.put("mail.smtp.auth", "true");
 
        props.put("mail.smtp.host", mailServer); 
        props.put("mail.debug", "true");
        
         props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
         props.setProperty("mail.smtp.socketFactory.fallback", "false");   
         props.setProperty("mail.smtp.port", "465");   
         props.setProperty("mail.smtp.socketFactory.port", "465"); 
 
        
          Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getDefaultInstance(props, auth);
 
            session.setDebug(debug);
        
        try
        {
            
            
            Transport bus = session.getTransport("smtp");
            bus.connect();
            Message message = new MimeMessage(session);
        
         //X-Priority values are generally numbers like 1 (for highest priority), 3 (normal) and 5 (lowest).
            
             message.addHeader("X-Priority", "1");
             message.setFrom(new InternetAddress(from));
             InternetAddress[] addressTo = new InternetAddress[to.length];
             for (int i = 0; i < to.length; i++)
             addressTo[i] = new InternetAddress(to[i]);
             message.setRecipients(Message.RecipientType .TO, addressTo);
             message.setSubject(subject);
                  
             
             BodyPart body = new MimeBodyPart();
 
            // body.setText(messageBody);
            body.setContent(messageBody,"text/html");
 
             BodyPart attachment = new MimeBodyPart();
             DataSource source = new FileDataSource(attachmentPath);
             attachment.setDataHandler(new DataHandler(source));
             attachment.setFileName(attachmentName);
             MimeMultipart multipart = new MimeMultipart();
             multipart.addBodyPart(body);
             multipart.addBodyPart(attachment);
             message.setContent(multipart);
             Transport.send(message);
             logger.debug("Mail sent successully");
             bus.close();
        
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        } 
        
        
       
    } 
    
    private class SMTPAuthenticator extends javax.mail.Authenticator
    {
 
        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = MailDetails.from;
            String password = MailDetails.password;
            return new PasswordAuthentication(username, password);
        }
    }
    
    
   
}

