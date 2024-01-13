package email.send.emailsendler.model;

import lombok.*;
import jakarta.persistence.*;


@Table(name = "client")
@NoArgsConstructor
@Entity
@Data
public class Client {
    @Id
    @GeneratedValue()
    private long id;

    private String email;

    private boolean isEmailSent;

    public Client(String email) {
        this.email = email;
    }
}
