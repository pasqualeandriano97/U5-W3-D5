package andrianopasquale97.U5W3D5.services;


import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.exceptions.BadRequestException;
import andrianopasquale97.U5W3D5.exceptions.CorrectDelete;
import andrianopasquale97.U5W3D5.exceptions.NotFoundException;
import andrianopasquale97.U5W3D5.payloads.EventoDTO;
import andrianopasquale97.U5W3D5.payloads.EventoRespDTO;
import andrianopasquale97.U5W3D5.repositories.EventoDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EventoService {
    @Autowired
    private EventoDAO eventoDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EventoRespDTO save(EventoDTO newEvento) {
        this.eventoDAO.findByName(newEvento.nome()).ifPresent(
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


    public Evento getDipendenteById(int id) {
        return this.eventoDAO.findById(id).orElseThrow(() -> new NotFoundException("Dipendente non trovato"));
    }


    public void findByIdAndDelete(int id) {
        Evento found = this.getDipendenteById(id);
        this.eventoDAO.delete(found);
        throw new CorrectDelete("Dipendente correttamente eliminato");
    }

    public EventoDTO findByIdAndUpdate(int id, EventoDTO modifiedAuthor) {
        Evento found = this.getDipendenteById(id);
        found.setNome(modifiedAuthor.nome());
        found.setData(modifiedAuthor.data());
        found.setLuogo(modifiedAuthor.data());
        found.setDescrizione(modifiedAuthor.descrizione());
        found.setNMaxPartecipanti(modifiedAuthor.nMaxPartecipanti());
        this.eventoDAO.save(found);
        return modifiedAuthor;
    }
}
