package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.ProdottoRepository;
import it.unicam.cs.ids.papcteam.c3Rest.service.ClienteService;
import it.unicam.cs.ids.papcteam.c3Rest.service.GestoreOrdini;
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
    @Autowired
    private GestoreOrdini gestoreOrdini;
    @Autowired
    private ProdottoRepository prodottoRepository;

    public ClienteRestController() {
    }

    @PostMapping("/annullaOrdine")
    public String clearOrdineInCorso(){
        this.gestoreOrdini.clearCreatore();
        return "ordine annullato";
    }

    @PostMapping("/setEmittente")
    public String setEmittenteOrdine(@RequestParam long idNegozio){
        NegozioEntity n = this.clienteService.setEmittenteOrdine(idNegozio);
        if (Objects.isNull(n)) return "nessun Negozio con questo Id";
        else return n.toString();
    }

    @PostMapping("/setDestinazione")
    public String setDestinazioneOrdine(@RequestParam long idLocker){
        LockerEntity l = this.clienteService.setDestinazioneOrdine(idLocker);
        if (Objects.isNull(l)) return "nessun Negozio con questo Id";
        else return l.toString();
    }


    @PostMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        String s ="";
        ProdottoEntity p = this.clienteService.setProdottoOrdine(idProdotto,number);
        if(Objects.isNull(p)) s= "nessun Prodotto con questo Id";
        else {
            if(p.getNumero()>this.prodottoRepository.getOne(idProdotto).getNumero()){
                s = "numero superiore da quello disponibile";
                clearOrdineInCorso();
            }else
            s = p.toString();
        }
        return s;
    }

    @PostMapping("/{id}/aggiungiOrdine")
    public String addOrdineToCliente(@PathVariable long id){
        OrdineEntity o = this.clienteService.addOrdineToCliente(id);
        if(Objects.isNull(o)) return "nessun Ordine con questo Id";
        else return o.toString();
    }

    @GetMapping("/{id}/ordini")
    public List<OrdineEntity> getOrdiniCliente(@PathVariable long id){
        return this.clienteService.getOrdiniCliente(id);
    }

    @GetMapping("/{id}/ordiniNonCompletati")
    public List<OrdineEntity> getOrdiniClienteNonCompletati(@PathVariable long id){
        return this.clienteService.getOrdiniNonCompletati(id);
    }
    @GetMapping("/{id}/ordiniDaRitirare")
    public List<OrdineEntity> getOrdiniClienteDaRitirare(@PathVariable long id) {
        return this.clienteService.getOrdiniDaRitirare(id);
    }

    @PatchMapping("/{id}/ritiraOrdine/{idOrdine}")
    public String ritiraOrdine(@PathVariable long id,@PathVariable long idOrdine){
        if(!this.clienteService.getClienteById(id).getOrdini().contains(this.gestoreOrdini.getOrdineById(idOrdine)))
            return "ordine con questo id inesistente";
        else {
            this.gestoreOrdini.ritiraOrdine(idOrdine);
            return "ordine ritirato";
        }
    }
}