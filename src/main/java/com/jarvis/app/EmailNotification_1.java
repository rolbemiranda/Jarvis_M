package com.jarvis.app;


import com.sun.mail.smtp.SMTPTransport;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.text.Normalizer;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author doraemon
 */
public class EmailNotification_1 {

    public void Send(String recipientEmail, String title, String message) throws AddressException, MessagingException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);
        Date date = new Date();

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("QATestTiger@gmail.com"));
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kassmiranda@tigerair.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
        msg.setSubject("[Jarvis Alert][" + date.toString().substring(4,11) + "}TEST RESULT for " + Global.TestCase_ID + ": "
                + Global.OverallTestingStatus);
        msg.setSentDate(new Date());

        //Result FileName
        String fileAttachment = Global.ScreenshotFileName;
        String fileAttachment2 = Global.ResultsFileName;

        //Creates the Body

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<font face=verdana size=2>Hi Team, <br><br>"
                + " Smoketest Test for today's build is COMPLETE. "
                + "Attached is the detailed result file (excel file) and the screenshot (docx file) for your reference. <br> <br> "
                + "   &nbsp&nbsp&nbsp  <b>URL:</b> " + Global.TigerUrl + "<br>"
                + "    &nbsp&nbsp&nbsp <b>PNR:</b> " + Global.Booking_PNR + "<br> <br>"
                + "<br><br> Thanks, <br> <b>Kass Miranda</b> </font>", "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        //Attach File
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(fileAttachment); // your file
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(source.getName());
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source2 = new FileDataSource(fileAttachment2); // your file
        messageBodyPart.setDataHandler(new DataHandler(source2));
        messageBodyPart.setFileName(source2.getName());
        multipart.addBodyPart(messageBodyPart);

        //Set Final Content
        msg.setContent(multipart);

        //Send Email
        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.gmail.com", "QATestTiger", "Tiger12345");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
        Global.Booking_PNR = "Not Available";
    }

    public void SendEmailHasCommenced(String recipientEmail, String title, String message) throws
            AddressException, MessagingException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("QATestTiger@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
        Date date = new Date();
        msg.setSubject("[Jarvis Alert]: TigerAir Smoketest has commenced at " + date);
        msg.setSentDate(new Date());

        //Creates the Body
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<font face=verdana size=2>Hi Team, <br><br>"
                + " Smoketest Test for today's build has commenced. "
                + "Jarvis will send the detailed results and the screenshots in a few minutes. <br>"
                + "<br><br> Thanks, <br> <b>Kass Miranda</b> </font>", "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        //Set Final Content
        msg.setContent(multipart);

        //Send Email
        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.gmail.com", "QATestTiger", "Tiger12345");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }

    public void SendEmailHasFailed(String recipientEmail, String Scenario_Filename, int idata, String Stepname, String ResultsFolder) throws
            AddressException, MessagingException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("QATestTiger@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
        msg.setSubject("[Jarvis Alert]: " + Scenario_Filename + " scenarios has been stopped due to errors. ");
        msg.setSentDate(new Date());

        //Creates the Body
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<font face=verdana size=2>Hi Team, <br><br>"
                + " Jarvis Test has stopped at line " + idata
                + " where current step " + Stepname + " is being executed."
                + "Check your current screen for possible errors.<br><br>"
                + "Check the screenshots <a href=" + ResultsFolder + ">HERE. </a> <br><br>"
                + "<br><br> Thanks, <br> <b>Jarvis</b> </font>", "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        //Set Final Content
        msg.setContent(multipart);

        //Send Email
        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.gmail.com", "QATestTiger", "Tiger12345");
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }
}
