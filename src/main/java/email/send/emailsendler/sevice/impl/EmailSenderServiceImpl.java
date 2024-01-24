package email.send.emailsendler.sevice.impl;


import email.send.emailsendler.model.Client;
import email.send.emailsendler.repository.ClientRepository;
import email.send.emailsendler.sevice.EmailSenderService;
import email.send.emailsendler.sevice.HtmlLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
@Log4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final ClientRepository clientRepository;

    private final HtmlLoaderService htmlContent;
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String mail;


    @SneakyThrows
    @Override
    public Mono<Void> sendEmail(String subject, String email) {
        return Mono.fromRunnable(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(mail);
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(htmlContent.loadHtmlContent(), true);

                javaMailSender.send(message);
                writeSuccessfulEmailToFile(email);
                logger.info("Email sent OK");
            } catch (MailSendException e) {
                logger.error("An error occurred while sending email");
                deleteClientIfNoValid(email);
            } catch (MessagingException e) {
                deleteClientIfNoValid(email);
                logger.error("Error in sending email: " + e.getMessage(), e);
            } catch (RuntimeException e) {
                // Обработка RuntimeException, выброшенного из writeSuccessfulEmailToFile
                logger.error("Error in asynchronous operation: " + e.getMessage(), e);
                deleteClientIfNoValid(email);
            } catch (IOException e) {
                deleteClientIfNoValid(email);
                // Обработка IOException
                logger.error("Error in writing to file: " + e.getMessage(), e);
            } catch (Exception с) {
                throw new RuntimeException("EXIT ");
            }
        });
    }

    @SneakyThrows
    @Override
    public Flux<Void> sendMassEmail(String subject) {
        return Flux.fromIterable(clientRepository.findByIsEmailSentFalse())
                .flatMap(client -> {
                    client.setEmailSent(true);
                    return sendEmail(subject, client.getEmail())
                            .then(Mono.fromRunnable(() -> clientRepository.save(client)))
                            .onErrorResume(error -> {
                                deleteClientIfNoValid(client.getEmail());
                                return Mono.empty();
                            })
                            .then();  // Приведение к Flux<Void>
                });
    }


    public Mono<Void> deleteClientIfNoValid(String email) {
        return Mono.fromRunnable(() -> {

            Client noValidClient = clientRepository.findByEmail(email);
            if (noValidClient != null) {
                clientRepository.delete(noValidClient);
            }
        });
    }

    private void writeSuccessfulEmailToFile(String email) {
        try {
            Files.write(Paths.get("logs/emails.txt"), (email + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Error writing successful email to file", e);
            // или бросить RuntimeException, чтобы прервать выполнение
            throw new RuntimeException("Error writing successful email to file", e);
        }
    }
}