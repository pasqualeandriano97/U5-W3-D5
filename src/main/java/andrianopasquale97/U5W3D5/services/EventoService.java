package andrianopasquale97.U5W3D5.services;


import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.exceptions.BadRequestException;
import andrianopasquale97.U5W3D5.exceptions.CorrectDelete;
import andrianopasquale97.U5W3D5.exceptions.NotFoundException;
import andrianopasquale97.U5W3D5.payloads.EventoDTO;
import andrianopasquale97.U5W3D5.payloads.EventoRespDTO;
import andrianopasquale97.U5W3D5.repositories.EventoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class EventoService {
    @Autowired
    private EventoDAO eventoDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EventoRespDTO save(EventoDTO newEvento) {
        this.eventoDAO.findByNome(newEvento.nome()).ifPresent(
                author -> {
                    throw new BadRequestException("L'evento " + newEvento.nome() + " è già stato salvato!");
                }
        );

        Evento evento = new Evento(newEvento.nome(), newEvento.data(), newEvento.luogo(), newEvento.descrizione(), newEvento.nMaxPartecipanti());


        this.eventoDAO.save(evento);
        return new EventoRespDTO(newEvento.nome(), newEvento.data(), newEvento.luogo(), newEvento.descrizione(), newEvento.nMaxPartecipanti());
    }

    public Page<Evento> getAll(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventoDAO.findAll(pageable);
    }


    public Evento getById(int id) {
        return this.eventoDAO.findById(id).orElseThrow(() -> new NotFoundException("Evento non trovato"));
    }


    public void findByIdAndDelete(int id) {
        Evento found = this.getById(id);
        this.eventoDAO.delete(found);
        throw new CorrectDelete("Evento correttamente eliminato");
    }

    public EventoDTO findByIdAndUpdate(int id, EventoDTO modifiedEvent) {
        Evento found = this.getById(id);
        found.setNome(modifiedEvent.nome());
        found.setData(modifiedEvent.data());
        found.setLuogo(modifiedEvent.data());
        found.setDescrizione(modifiedEvent.descrizione());
        found.setNMaxPartecipanti(modifiedEvent.nMaxPartecipanti());
        this.eventoDAO.save(found);
        return modifiedEvent;
    }
}
