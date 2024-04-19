package andrianopasquale97.U5W3D5.services;

import andrianopasquale97.U5W3D5.entities.Utente;
import andrianopasquale97.U5W3D5.exceptions.UnauthorizedException;
import andrianopasquale97.U5W3D5.payloads.UtenteLoginDTO;
import andrianopasquale97.U5W3D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndGenerateToken(UtenteLoginDTO payload){
        Utente utente = this.utenteService.findByEmail(payload.email());
        if(passwordEncoder.matches(payload.password(),utente.getPassword() )) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }


    }
}
