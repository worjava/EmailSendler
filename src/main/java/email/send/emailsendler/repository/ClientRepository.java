package email.send.emailsendler.repository;

import email.send.emailsendler.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.isEmailSent = false")
    List<Client> findByIsEmailSentFalse();

  Client findByEmail(String email);


}


