package vn.compedia.website.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class EmailUtil implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private static EmailUtil INSTANCE = null;
    private final SmtpAuthenticator smtpAuthenticator;
    private final Queue<MailDto> mailDtoQueue;

    public EmailUtil() {
        String email = PropertiesUtil.getEmailProperty("mail.user");
        String password = PropertiesUtil.getEmailProperty("mail.password");
        smtpAuthenticator = new SmtpAuthenticator(email, password);
        mailDtoQueue = new LinkedList<>();
    }

    public static EmailUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailUtil();
            new Thread(INSTANCE).start();
        }
        return INSTANCE;
    }

    public void sendCreateUser(String emailTo, String userName, String password) {
        String subject = PropertiesUtil.getProperty("email.createUser.subject");
        String content = PropertiesUtil.getProperty("email.createUser.content")
                .replace("{TEN_TAI_KHOAN}", userName)
                .replace("{MAT_KHAU}", password);
        mailDtoQueue.add(new MailDto(emailTo, subject, content));
    }

    public void sendResetPassword(String emailTo, String password) {
        String subject = PropertiesUtil.getProperty("email.resetPassword.subject");
        String content = PropertiesUtil.getProperty("email.resetPassword.content")
                .replace("{MAT_KHAU}", password);
        mailDtoQueue.add(new MailDto(emailTo, subject, content));
    }

    private boolean send(MailDto mailDto) {
        try {
            Properties emailProps = new Properties();
            emailProps.load(PropertiesUtil.class.getResourceAsStream("/email.properties"));
            // Get the default Session object.
            Session session = Session.getDefaultInstance(emailProps, smtpAuthenticator);
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(PropertiesUtil.getEmailProperty("mail.user")));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDto.getEmailTo()));
            // Set Subject: header field
            message.setSubject(mailDto.getSubject(), "UTF-8");

            // Send the actual HTML message, as big as you like
            //message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setContent(mailDto.getContent(), "text/html; charset=UTF-8");
            // Send message
            Transport.send(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                MailDto mailDto = mailDtoQueue.poll();
                if (mailDto != null) {
                    String rs = send(mailDto) ? "success" : "fail";
                    log.info("Send mail is " + rs + " (" + mailDto + ")");
                }
            } catch (Exception e) {
                log.error("Lỗi", e);
            }
        }
    }
}
