package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.service.CommercianteService;
import it.unicam.cs.ids.papcteam.c3Rest.service.GestoreNegozi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commercianti")
@CrossOrigin(origins = "*")
public class CommercianteRestController {
    @Autowired
    private CommercianteService commercianteService;
/*    @Autowired
    private GestoreNegozi gestoreNegozi;*/

    public CommercianteRestController() {
    }

    @GetMapping("/{id}")
    public CommercianteEntity getCommercinateById(@PathVariable long id){
        return this.commercianteService.getCommercianteById(id);
    }

    @GetMapping("/{id}/negozio")
    public NegozioEntity getNegozioCommerciante(@PathVariable long id){
        return getCommercinateById(id).getNegozio();
    }


    @GetMapping("/{id}/prodotti")
    public List<ProdottoEntity> getProdottiNegozio(@PathVariable long id){
        return this.commercianteService.getProdottiNegozio(id);
    }

    @PatchMapping("/{id}/modificaProdotto")
    public ProdottoEntity modificaProdotto(@PathVariable long id,@RequestParam long idProdotto,@RequestParam int numero,
                                           @RequestParam boolean aggiunta){
        return this.commercianteService.modificaNumeroProdotto(id,idProdotto,numero,aggiunta);
    }

    @PostMapping("/{id}/aggiungiProdotto")
    public void addProdotto(@PathVariable long id,@RequestParam String nome,@RequestParam String descrizione,
                            @RequestParam double prezzo,@RequestParam int numero){
        ProdottoEntity p = new ProdottoEntity(nome,descrizione,prezzo);
        if (numero>0) p.setNumero(numero);
        this.commercianteService.aggiungiProdotto(id,p);
    }

    @DeleteMapping("/{id}/eliminaProdotto")
    public void deleteProdotto(@PathVariable long id,@RequestParam long idProdotto){
        this.commercianteService.deleteProdotto(id,idProdotto);
    }

    @GetMapping("/{id}/ordiniInNegozio")
    public List<OrdineEntity> getOrdiniCommerciante(@PathVariable long id){
        return this.commercianteService.getOrdiniCommerciante(id,o->true);
    }
    @GetMapping("/{id}/ordiniInNegozioPerRitiro")
    public List<OrdineEntity> getOrdiniCommercianteInRitiro(@PathVariable long id){
        return this.commercianteService.getOrdiniCommerciante(id,ordineEntity ->
                ordineEntity.getStatoOrdine()== StatoOrdine.RITIRO_NEGOZIO);
    }
    @GetMapping("/{id}/ordiniInNegozioNonCompletati")
    public List<OrdineEntity> getOrdiniCommercianteNonCompletati(@PathVariable long id){
        return this.commercianteService.getOrdiniCommerciante(id,ordineEntity ->
                ordineEntity.getStatoOrdine()!= StatoOrdine.COMPLETATO);
    }

    @GetMapping("/{id}/ordiniDaSoddisfare")
    public List<OrdineEntity> getOrdiniDaSoddisfare(@PathVariable long id){
        return this.commercianteService.getOrdiniCommerciante(id,ordineEntity ->
                ordineEntity.getStatoOrdine()==StatoOrdine.ESEGUITO);
    }

    @PostMapping("/{id}/inviaChiamata")
    public void effettuaChiamata(@PathVariable long id,@RequestParam long idOrdine){
        this.commercianteService.effettuaChiamata(id,idOrdine);
    }

}
