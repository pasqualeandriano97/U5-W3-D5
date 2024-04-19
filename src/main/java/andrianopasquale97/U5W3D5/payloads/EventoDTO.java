package andrianopasquale97.U5W3D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EventoDTO(
        @NotEmpty(message = "Inserisci il nome")
        @Size(message = "Il nome deve essere di almeno 3 caratteri e massimo 15", min = 3,max = 15)
        String nome,
        @NotEmpty(message = "Inserisci la data")
        @Pattern(regexp = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
        String data,
        @NotEmpty(message = "Inserisci il luogo")
        @Size(message = "Il nome del luogo deve essere di almeno 3 caratteri e massimo 15", min = 3,max = 15)
        String luogo,
        @NotEmpty(message = "Inserisci la descrizione")
        @Size(message = "La descrizione deve essere di almeno 10 caratteri e massimo 30", min = 10,max = 30)
        String descrizione,
        @NotNull
        int nMaxPartecipanti
) {
}
