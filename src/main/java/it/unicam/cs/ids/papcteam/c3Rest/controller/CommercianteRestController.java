package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.service.CommercianteService;
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
