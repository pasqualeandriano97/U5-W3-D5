package andrianopasquale97.U5W3D5.payloads;

public record EventoDTO(
        String nome,
        String data,
        String luogo,
        String descrizione,
        int nMaxPartecipanti
) {
}
