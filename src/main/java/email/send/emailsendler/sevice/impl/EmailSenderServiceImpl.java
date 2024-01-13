package email.send.emailsendler.sevice.impl;


import email.send.emailsendler.model.Client;
import email.send.emailsendler.repository.ClientRepository;
import email.send.emailsendler.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class EmailSenderServiceImpl implements EmailSenderService {

    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mail;

    @SneakyThrows
    @Override
    public void sendEmail(String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mail);
            helper.setTo(clientRepository.findById(1L).get().getEmail());
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("An error occurred while sending the email", e);
        }

    }

    @Override
    public void sendMassEmail(String subject, String htmlContent) {
        List<Client> clients = clientRepository.findByIsEmailSentFalse();
        for (Client client : clients) {
            sendEmail(subject, htmlContent);
            client.setEmailSent(true);
            clientRepository.save(client);

        }
    }
}