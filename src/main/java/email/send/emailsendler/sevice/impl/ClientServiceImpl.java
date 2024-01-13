package email.send.emailsendler.sevice.impl;

import email.send.emailsendler.model.Client;
import email.send.emailsendler.repository.ClientRepository;
import email.send.emailsendler.sevice.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService, CommandLineRunner {

    @Value("${file.path}")
    private String filePath;

    private final ClientRepository clientRepository;


    @Override
    public void saveClient(List<String> emails) {
        long start = System.currentTimeMillis();

        for (String email : emails) {
            Client client = new Client(email);
            clientRepository.save(client);
        }

        long end = System.currentTimeMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(end - start);
        log.info ("email клиентов записаны в бд. Время выполнения: " + seconds + " секунд");
    }

    public List<String> getEmails() {
        List<String> emails = new ArrayList<>();

        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String email;



            while ((email = reader.readLine()) != null) {
                emails.add(email);
            }

            long end = System.currentTimeMillis();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(end - start);
            log.info ("Документ прочитан и email клиентов записаны в List. Время выполнения: " + seconds + " секунд");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return emails;
    }

    public void customerRecord() {
        if (clientRepository.count() == 0) {

            List<String> emails = getEmails();
            saveClient(emails);
        } else {
            log.info("Данные уже присутввуют");
        }
    }
    @Override
    public void run(String... args) {
        customerRecord();
    }
}
