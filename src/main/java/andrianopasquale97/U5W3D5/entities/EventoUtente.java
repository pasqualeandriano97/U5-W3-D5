package andrianopasquale97.U5W3D5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "evento_utente")
public class EventoUtente {
    @EmbeddedId
    private EventoUtenteKey id;

    // Costruttori, getter e setter

    @Embeddable
    private static class EventoUtenteKey implements Serializable {
        @Column(name = "utente_id")
        int utenteId;

        @Column(name = "evento_id")
        int eventoId;


    }
}