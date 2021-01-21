package it.unicam.cs.ids.papcteam.c3Rest.controller;


import it.unicam.cs.ids.papcteam.c3Rest.entity.CommercianteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/register")
public class RegistrationRestController {

    @Autowired
    RegistrationService registrationService;
    public RegistrationRestController(){}

    @PostMapping("/registrationCommerciante")
    public void doRegistrationCommerciante(@RequestParam String nome,@RequestParam String cognome,
            @RequestParam String email,@RequestParam String password,@RequestParam String nomeNegozio,
            @RequestParam String descrizione,@RequestParam String indirizzo,
            @RequestParam String orario ){
        this.registrationService.addCommerciante(new CommercianteEntity(nome,cognome,email,password),
                new NegozioEntity(nomeNegozio,descrizione,indirizzo,orario));
    }

    @PostMapping
    public void register(@RequestParam String nome, @RequestParam String cognome,
                         @RequestParam String email,@RequestParam String password,@RequestParam String type){
        registrationService.register(nome,cognome,email,password,type);
    }
}
