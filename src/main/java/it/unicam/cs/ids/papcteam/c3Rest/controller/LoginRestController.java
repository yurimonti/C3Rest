package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CommercianteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.CorriereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginRestController {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CommercianteRepository commercianteRepository;
    @Autowired
    private CorriereRepository corriereRepository;

    public LoginRestController() {
    }

    private long isCliente(String email, String password){
        if(this.clienteRepository.findAll().stream().anyMatch(clienteEntity ->
                (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password))))
            return this.clienteRepository.findAll().stream().filter(clienteEntity ->
                    (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password)))
                    .findFirst().orElseThrow().getId();
        else throw new NullPointerException("cliente inesistente");
    }

    private long isCommerciante(String email,String password){
        if(this.commercianteRepository.findAll().stream().anyMatch(clienteEntity ->
                (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password))))
            return this.commercianteRepository.findAll().stream().filter(clienteEntity ->
                    (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password)))
                    .findFirst().orElseThrow().getId();
        else throw new NullPointerException("commerciante inesistente");
    }
    private long isCorriere(String email,String password){
        if(this.corriereRepository.findAll().stream().anyMatch(clienteEntity ->
                (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password))))
            return this.corriereRepository.findAll().stream().filter(clienteEntity ->
                    (clienteEntity.getEmail().equals(email) && clienteEntity.getPassword().equals(password)))
                    .findFirst().orElseThrow().getId();
        else throw new NullPointerException("corriere inesistente");
    }

    @GetMapping
    public long isCorrect(@RequestParam String type,@RequestParam String email,@RequestParam String password){
        long id;
        switch (type){
            case "cliente": id = isCliente(email,password);
            break;
            case "commerciante":id = isCommerciante(email,password);
            break;
            case "corriere":id = isCorriere(email,password);
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return id;
    }
}
