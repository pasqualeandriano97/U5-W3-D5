package andrianopasquale97.U5W3D5.repositories;

import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoDAO extends JpaRepository<Evento, Integer> {
    Optional<Evento> findByName(String name);

}
