package andrianopasquale97.U5W3D5.repositories;

import andrianopasquale97.U5W3D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteDAO extends JpaRepository<Utente, Integer> {
    boolean existsByEmail(String email);
    Optional<Utente> findByEmail(String email);
}
