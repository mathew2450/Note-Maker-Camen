import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	String eol = System.getProperty("line.separator");

    SendEmail(String body, String noteID, String name) {
        final String username = "camentech@yahoo.com";
        final String password = "136MwC448";
        final String recipient = "holycow2450@gmail.com";
        final String subject = "Note Needs Revising " + noteID;
        final String emailmessage = "Hello " + name + "," + eol + "     " + body + eol + "Thank you," + eol + "     Camen Tech";
 
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(emailmessage);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}