package email.send.emailsendler.controller;

import email.send.emailsendler.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class SendEmailController {

    private final EmailSenderService senderService;
    private final ResourceLoader resourceLoader;

    @SneakyThrows
    @PostMapping("/send")
    public String sendEmail(@RequestBody String subject) {
        Resource resource = resourceLoader.getResource("classpath:templates/offer_123.html");
        String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        senderService.sendEmail(subject, htmlContent);
        return "Email sent successfully";
    }


}
