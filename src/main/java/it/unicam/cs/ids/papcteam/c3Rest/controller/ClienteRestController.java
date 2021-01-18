package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ClienteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ClienteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ProdottoRepository;
import it.unicam.cs.ids.papcteam.c3Rest.service.ClienteService;
import it.unicam.cs.ids.papcteam.c3Rest.service.ConcreteCreatoreOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.service.CreatoreOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/clienti")
public class ClienteRestController {
    @Autowired
    private ClienteService clienteService;

    public ClienteRestController() {
    }

    @PostMapping("/setEmittente")
    public String setEmittenteOrdine(@RequestParam long idNegozio){
        NegozioEntity n = this.clienteService.setEmittenteOrdine(idNegozio);
        if (Objects.isNull(n)) return "nessun Negozio con questo Id";
        else return n.toString();
    }

    @PostMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        ProdottoEntity p = this.clienteService.setProdottoOrdine(idProdotto,number);
        if(Objects.isNull(p)) return "nessun Prodotto con questo Id";
        else return p.toString();
    }

    @PostMapping("/{id}/aggiungiOrdine")
    public String addOrdineToCliente(@PathVariable long id){
        OrdineEntity o = this.clienteService.addOrdineToCliente(id);
        if(Objects.isNull(o)) return "nessun Ordine con questo Id";
        else return o.toString();
    }

    /*@Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;

    private CreatoreOrdine creatoreOrdine;

    public ClienteRestController() {
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
    }

    @PostMapping("/setEmittente")
    public String setEmittenteOrdine(@RequestParam long idNegozio){
        if (this.negozioRepository.findAll().stream().noneMatch(negozioEntity -> negozioEntity.getId()==idNegozio))
            throw new NullPointerException("id negozio inesistente");
        NegozioEntity n = negozioRepository.getOne(idNegozio);
        this.creatoreOrdine.setEmittente(n);
        return n.toString();
    }*/

    /*@PostMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        if(this.creatoreOrdine.getEmittente().getProdotti().stream().noneMatch(prodottoEntity -> prodottoEntity.getId()==idProdotto))
            return "nessun Prodotto con questo Id";
        ProdottoEntity prodotto = this.prodottoRepository.getOne(idProdotto);
        ProdottoEntity prodottoIn;
        if(this.creatoreOrdine.getProdotti().stream().anyMatch(prodottoEntity -> prodottoEntity.getSerialCode()==prodotto.getSerialCode())){
            prodottoIn = this.creatoreOrdine.getProdottoBySerialCode(prodotto.getSerialCode());
            prodottoIn.setNumero(prodottoIn.getNumero()+number);
        }else {
            prodottoIn = new ProdottoEntity(prodotto.getNome(),prodotto.getDescrizione(),prodotto.getPrezzo());
            prodottoIn.setSerialCode(prodotto.getSerialCode());
            this.creatoreOrdine.addProdotto(prodottoIn,number);
        }
        return prodottoIn.toString();
    }

    *//*@PostMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        if(this.negozioRepository.getOne(this.creatoreOrdine.getEmittente().getId()).getProdotti().stream()
                .noneMatch(prodottoEntity -> prodottoEntity.getId()==idProdotto))return "prodotto con questo id non disponibile";
        ProdottoEntity prodottoNegozio = this.creatoreOrdine.getEmittente().getProdotti().stream()
                .filter(prodottoEntity -> prodottoEntity.getId()==idProdotto).findFirst().orElseThrow();
        ProdottoEntity prodottoOrdine = new ProdottoEntity(prodottoNegozio.getNome(),prodottoNegozio.getDescrizione(),
                prodottoNegozio.getPrezzo());
        prodottoOrdine.setSerialCode(prodottoNegozio.getSerialCode());
        this.creatoreOrdine.addProdotto(prodottoOrdine,number);
        return "prodotto inserito";
    }*//*

    @PostMapping("/{id}/aggiungiOrdine")
    public List<OrdineEntity> addOrdineToCliente(@PathVariable long id){
        if (this.clienteRepository.findAll().stream().noneMatch(clienteEntity -> clienteEntity.getId()==id))
            throw new NullPointerException("cliente con questo id inesistente");
        ClienteEntity cliente = getClienteById(id);
        if(negozioRepository.findAll().stream().noneMatch(negozioEntity -> negozioEntity.getId()==creatoreOrdine.getEmittente().getId()))
            throw new NullPointerException("negozio inesistente");
        NegozioEntity negozioEntity = this.negozioRepository.getOne(creatoreOrdine.getEmittente().getId());
        creatoreOrdine.getProdotti().forEach(prodottoOrdine-> {
            negozioEntity.getProdotti().stream()
                    .filter(prodottoNegozio -> prodottoNegozio.getSerialCode()==prodottoOrdine.getSerialCode())
                    .forEach(p -> p.setNumero(p.getNumero()-prodottoOrdine.getNumero()));
        });
        this.creatoreOrdine.setEmittente(negozioEntity);
        OrdineEntity ordine = this.creatoreOrdine.creaOrdine();
        cliente.getOrdini().add(ordine);
        this.negozioRepository.save(negozioEntity);
        this.clienteRepository.save(cliente);
        return cliente.getOrdini();
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
    }*/

}