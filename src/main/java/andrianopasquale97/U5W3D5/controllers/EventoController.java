package andrianopasquale97.U5W3D5.controllers;


import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.entities.Utente;
import andrianopasquale97.U5W3D5.exceptions.BadRequestException;
import andrianopasquale97.U5W3D5.payloads.EventoDTO;
import andrianopasquale97.U5W3D5.payloads.EventoRespDTO;
import andrianopasquale97.U5W3D5.services.EventoService;
import andrianopasquale97.U5W3D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    private EventoService eventoService;
    @GetMapping("")
    public Page<Evento> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventoService.getAll(page, size, sortBy);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventoRespDTO saveEvento( @RequestBody @Validated EventoDTO evento, BindingResult validation) {
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        this.eventoService.save(evento);
        return new EventoRespDTO(evento.nome(), evento.data(), evento.luogo(), evento.descrizione(), evento.nMaxPartecipanti());
    }
    // 3. - GET http://localhost:3001/dipendenti/{id}
    @GetMapping("/{eventoId}")
    public Evento findById(@PathVariable int eventoId) throws Exception {
        return eventoService.getById(eventoId);
    }
    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventoRespDTO updateEvento(@PathVariable int eventoId,@Validated @RequestBody EventoDTO evento, BindingResult validation) {
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        this.eventoService.findByIdAndUpdate(eventoId, evento);
        return new EventoRespDTO(evento.nome(), evento.data(), evento.luogo(), evento.descrizione(), evento.nMaxPartecipanti());
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE_EVENTI','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int utenteId) {
        eventoService.findByIdAndDelete(utenteId);
    }
}
