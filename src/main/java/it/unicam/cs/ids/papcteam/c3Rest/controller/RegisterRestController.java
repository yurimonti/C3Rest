package it.unicam.cs.ids.papcteam.c3Rest.controller;


import it.unicam.cs.ids.papcteam.c3Rest.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/register")
public class RegisterRestController {

    @Autowired
    RegisterService registerService;
    public RegisterRestController(){}

    @GetMapping
    public void register(@RequestParam String nome, @RequestParam String cognome,
                         @RequestParam String email,@RequestParam String password,@RequestParam String type){
        registerService.register(nome,cognome,email,password,type);
    }
}
