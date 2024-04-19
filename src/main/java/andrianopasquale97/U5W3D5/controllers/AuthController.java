package andrianopasquale97.U5W3D5.controllers;

import andrianopasquale97.U5W3D5.exceptions.BadRequestException;
import andrianopasquale97.U5W3D5.payloads.UtenteDTO;
import andrianopasquale97.U5W3D5.payloads.UtenteLoginDTO;
import andrianopasquale97.U5W3D5.payloads.UtenteLoginRespDTO;
import andrianopasquale97.U5W3D5.payloads.UtenteRespDTO;
import andrianopasquale97.U5W3D5.services.AuthService;
import andrianopasquale97.U5W3D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody UtenteLoginDTO payload){
        return new UtenteLoginRespDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new UtenteRespDTO(this.utenteService.save(body).email());
    }

}
