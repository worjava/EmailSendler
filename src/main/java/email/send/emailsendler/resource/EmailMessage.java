package email.send.emailsendler.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmailMessage {

    private String to;
    private String subject;

    private String message;
}
