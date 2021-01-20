package it.unicam.cs.ids.papcteam.c3Rest.controller;


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

    @GetMapping
    public void register(@RequestParam String nome, @RequestParam String cognome,
                         @RequestParam String email,@RequestParam String password,@RequestParam String type){
        registrationService.register(nome,cognome,email,password,type);
    }
}
