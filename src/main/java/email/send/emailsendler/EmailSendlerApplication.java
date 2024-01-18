package email.send.emailsendler;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAdminServer
@EnableAsync
public class EmailSendlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailSendlerApplication.class, args);
    }

}
