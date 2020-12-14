package it.unicam.cs.ids.papcteam.c3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clienti")
public class ClienteRestController {

    @Autowired
    private final ClienteRepository clienteRepository;

    public ClienteRestController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Cliente> getClienti(){
        return this.clienteRepository.findAll();
    }

    @PostMapping
    public void createCliente(@RequestBody Cliente cliente){
        clienteRepository.save(cliente);
    }
    @DeleteMapping("/{id}")
    public void deleteCliente(@RequestParam long id){
        clienteRepository.deleteById(id);
    }

}
