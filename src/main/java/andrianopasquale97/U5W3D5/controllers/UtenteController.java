package andrianopasquale97.U5W3D5.controllers;

import andrianopasquale97.U5W3D5.entities.Evento;
import andrianopasquale97.U5W3D5.entities.Utente;
import andrianopasquale97.U5W3D5.payloads.UtenteDTO;
import andrianopasquale97.U5W3D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.getAll(page, size, sortBy);
    }
    // 3. - GET http://localhost:3001/dipendenti/{id}
    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findById(@PathVariable int utenteId) throws Exception {
        return utenteService.getById(utenteId);
    }


    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int utenteId) {
        utenteService.findByIdAndDelete(utenteId);
    }


    @GetMapping("/me")
    public Utente getMe(@AuthenticationPrincipal Utente utente) {
        return utente;
    }

    @PutMapping("/me")
    public Utente updateMe(@AuthenticationPrincipal Utente utente, @Validated @RequestBody UtenteDTO body) {
        this.utenteService.findByIdAndUpdate(utente.getId(), body);
        return utente;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser){
        this.utenteService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }
    @PostMapping("/{utenteId}/{eventoId}")
    public Utente findByIdAndUtente(@PathVariable int utenteId, @PathVariable int eventoId) throws IOException {
        return utenteService.findByIdAndAddUtente(utenteId, eventoId);
    }


}
