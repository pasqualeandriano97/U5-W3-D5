package andrianopasquale97.U5W3D5.services;

import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.entities.Utente;
import andrianopasquale97.U5W3D5.exceptions.BadRequestException;
import andrianopasquale97.U5W3D5.exceptions.CorrectDelete;
import andrianopasquale97.U5W3D5.exceptions.NotFoundException;
import andrianopasquale97.U5W3D5.payloads.UtenteDTO;
import andrianopasquale97.U5W3D5.payloads.UtenteRespDTO;
import andrianopasquale97.U5W3D5.repositories.EventoDAO;
import andrianopasquale97.U5W3D5.repositories.UtenteDAO;
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
import java.util.Objects;

@Service
public class UtenteService {
    @Autowired
    private UtenteDAO utenteDAO;
    @Autowired
    private EventoDAO eventoDAO;
    @Autowired
    private EventoService eventoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UtenteRespDTO save(UtenteDTO newUser) {
        this.utenteDAO.findByEmail(newUser.email()).ifPresent(
                author -> {
                    throw new BadRequestException("L'email " + newUser.email() + " è già in uso!");
                }
        );

        Utente utente = new Utente(newUser.nome(), newUser.cognome(), newUser.email(),passwordEncoder.encode(newUser.password()));

        this.utenteDAO.save(utente);
        return new UtenteRespDTO(utente.getEmail());
    }

    public Page<Utente> getAll(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteDAO.findAll(pageable);
    }


    public Utente getById(int id) {
        return this.utenteDAO.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }


    public void findByIdAndDelete(int id) {
        Utente found = this.getById(id);
        this.utenteDAO.delete(found);
        throw new CorrectDelete("Utente correttamente eliminato");
    }

    public UtenteDTO findByIdAndUpdate(int id, UtenteDTO modifiedAuthor) {
        Utente found = this.getById(id);
        found.setNome(modifiedAuthor.nome());
        found.setCognome(modifiedAuthor.cognome());
        found.setEmail(modifiedAuthor.email());
        this.utenteDAO.save(found);
        return modifiedAuthor;
    }


    public Utente findByEmail(String email) {
        return this.utenteDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }
    public Utente findByIdAndAddUtente(int utenteId , int eventoId) throws IOException {
        Utente utente = this.getById(eventoId);
        Evento evento = eventoService.getById(utenteId);
        if (Objects.isNull(utente)) {
            throw new NotFoundException(utenteId);
        }
        if (Objects.isNull(evento)) {
            throw new NotFoundException(eventoId);
        }
        if (evento.getUtenti().contains(utente)) {
            throw new BadRequestException("Utente già presente nel evento");
        }if(evento.getUtenti().size() >= evento.getNMaxPartecipanti()) {
            throw new BadRequestException("Evento pieno");}
        evento.getUtenti().add(utente);
        utente.getEventi().add(evento);

        eventoDAO.save(evento);
        return utenteDAO.save(utente);
    }
}
