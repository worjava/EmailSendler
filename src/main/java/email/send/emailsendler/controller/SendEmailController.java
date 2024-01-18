package email.send.emailsendler.controller;

import email.send.emailsendler.request.EmailRequest;
import email.send.emailsendler.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendEmailController {

    private final EmailSenderService senderService;


    @SneakyThrows
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest request) {

        senderService.sendEmail(request.getSubject(), request.getEmail());
        return "Email sent successfully";
    }

    @PostMapping("/sendAll")
    public String sendMassEmail(@RequestBody EmailRequest request) {
        senderService.sendMassEmail(request.getSubject());

        return "Emails sent successfully";
    }


}
