package email.send.emailsendler.sevice;

import java.util.List;

public interface ClientService {
    void saveClient(List<String> emails);
    List<String> getEmails();



}
