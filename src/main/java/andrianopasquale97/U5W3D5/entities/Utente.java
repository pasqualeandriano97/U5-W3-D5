package andrianopasquale97.U5W3D5.entities;

import andrianopasquale97.U5W3D5.enums.Role;
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
@Table(name = "Utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
