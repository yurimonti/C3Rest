package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ClienteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clienti")
public class ClienteRestController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OrdineRestController ordineRestController;

    public ClienteRestController() {
        this.ordineRestController = new OrdineRestController();
    }

    @PostMapping("/{id}/aggiungiOrdine")
    public ClienteEntity addOrdineToCliente(@PathVariable long id){
        ClienteEntity cliente = getClienteById(id);
        OrdineEntity ordine = this.ordineRestController.createOrdine();
        cliente.getOrdini().add(ordine);
        this.clienteRepository.save(cliente);
        return cliente;
    }

    @GetMapping("/{id}")
    public ClienteEntity getClienteById(@PathVariable long id){
        return this.clienteRepository.getOne(id);
    }


    @GetMapping
    public List<ClienteEntity> getClienti(){
        return this.clienteRepository.findAll();
    }

    @GetMapping("/{id}/ordini")
    public List<OrdineEntity> getOrdini(@PathVariable long id){
        return clienteRepository.getOne(id).getOrdini();
    }
    @PostMapping
    public void createCliente(@RequestBody ClienteEntity cliente){
        cliente.initUsername();
        clienteRepository.save(cliente);
    }
    @DeleteMapping("/{id}")
    public void deleteCliente(@RequestParam long id){
        clienteRepository.deleteById(id);
    }

}