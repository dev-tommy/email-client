package pl.devtommy.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.devtommy.controller.EmailSendingResult;
import pl.devtommy.model.EmailAccount;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;

public class EmailSenderService extends Service<EmailSendingResult> {
    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;

    private List<File> attachments;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content, List<File> attachments) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
        this.attachments = attachments;
    }

    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call() throws Exception {
                try {
                    if (recipient.isEmpty()) {
                        return EmailSendingResult.NO_RECIPIENT;
                    } else if (!validateEmail(recipient)) {
                        return EmailSendingResult.INVALID_RECIPIENT;
                    }
                    MimeMessage mimeMessage = createMimeMessage();
                    Multipart multipart = setMessageContent();
                    mimeMessage.setContent(multipart);
                    addAttachments(multipart);
                    sendMessage(mimeMessage);

                    return EmailSendingResult.SUCCESS;
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILED_BY_PROVIDER;
                } catch (Exception e) {
                    e.printStackTrace();
                    return EmailSendingResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }

    private void sendMessage(MimeMessage mimeMessage) throws MessagingException {
        Transport transport = emailAccount.getSession().getTransport();
        transport.connect(
                emailAccount.getProperties().getProperty("outgoingHost"),
                emailAccount.getAddress(),
                emailAccount.getPassword()
        );
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    private void addAttachments(Multipart multipart) throws MessagingException {
        if(attachments.size()>0){
            for( File file:attachments) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file.getAbsoluteFile());
                mimeBodyPart.setDataHandler(new DataHandler(source));
                mimeBodyPart.setFileName(file.getName());
                multipart.addBodyPart(mimeBodyPart);
            }
        }
    }

    private Multipart setMessageContent() throws MessagingException {
        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");
        multipart.addBodyPart(messageBodyPart);
        return multipart;
    }

    private MimeMessage createMimeMessage() throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
        mimeMessage.setFrom(emailAccount.getAddress());
        mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
        mimeMessage.setSubject(subject);
        return mimeMessage;
    }

    private boolean validateEmail(String email) {
        boolean isValid = false;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            //e.printStackTrace();
        }
        return isValid;
    }
}
