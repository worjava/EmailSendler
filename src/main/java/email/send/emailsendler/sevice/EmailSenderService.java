package email.send.emailsendler.sevice;

public interface EmailSenderService {
    void sendEmail(String subject, String htmlContent);// single send message


    void sendMassEmail(String subject, String htmlContent);// mass send messages
}
