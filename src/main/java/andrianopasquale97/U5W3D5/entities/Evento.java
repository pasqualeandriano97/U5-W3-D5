package andrianopasquale97.U5W3D5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String data;
    private String luogo;
    private String descrizione;
    private int nMaxPartecipanti;

    public Evento(String nome, String data, String luogo, String descrizione, int nMaxPartecipanti) {
        this.nome = nome;
        this.data = data;
        this.luogo = luogo;
        this.descrizione = descrizione;
        this.nMaxPartecipanti = nMaxPartecipanti;
    }
}
