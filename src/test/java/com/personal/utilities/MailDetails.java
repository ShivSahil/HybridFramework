package com.personal.utilities;

public class MailDetails{
    public static String server="smtp.gmail.com";
    public static String from = "sahilguleri.9@gmail.com";
    public static String password = "Bran07Broken#";
    public static String[] to ={"shivguleri@tuta.io"}; // can sent mail to multiple addresses
    public static String subject = " Subject";
    
    public static String messageBody ="messageBody";
    public static String attachmentPath=System.getProperty("user.dir")+"\\src\\test\\resources\\"+ZipUtility.Reports+".zip";;
    public static String attachmentName=ZipUtility.Reports+".zip";
   
    
   
}
