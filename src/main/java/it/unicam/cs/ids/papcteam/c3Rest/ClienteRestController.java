package it.unicam.cs.ids.papcteam.c3Rest;

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
    public Cliente addOrdineToCliente(@PathVariable long id){
        Cliente cliente = getClienteById(id);
        Ordine ordine = this.ordineRestController.createOrdine();
        cliente.getOrdini().add(ordine);
        this.clienteRepository.save(cliente);
        return cliente;
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable long id){
        return this.clienteRepository.getOne(id);
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