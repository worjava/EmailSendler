package email.send.emailsendler.init;

import email.send.emailsendler.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunnerSendAllMails implements CommandLineRunner {

    private final EmailSenderService senderService;


    @Override
    public void run(String... args) throws Exception {
        senderService.sendMassEmail("В России три пути! Один из них - войти в Айти!Открой дорогу к новым возможностям!").subscribe();
    }

}