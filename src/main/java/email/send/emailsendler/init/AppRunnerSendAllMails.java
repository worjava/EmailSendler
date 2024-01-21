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
        senderService.sendMassEmail("Поздравляем! Подготовка к ЕГЭ и ОГЭ: " +
                "Ваш путь к высоким баллам начинается здесь!").subscribe();
    }

}