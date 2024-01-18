package email.send.emailsendler.sevice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmailSenderService {
    Mono<Void> sendEmail(String subject, String email);// single send message


    Flux<Void> sendMassEmail(String subject);// mass send messages


}
