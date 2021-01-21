package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.CommercianteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.service.CommercianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    /*@PatchMapping("/{id}")
    public void setNegozioAssociato(@PathVariable long id,@RequestParam long idNegozio){
        this.commercianteService.setNegozioAssociato(id,idNegozio);
    }*/

    @GetMapping("/{id}/ordiniInNegozio")
    public List<OrdineEntity> getOrdiniCommerciante(@PathVariable long id){
        return this.commercianteService.getOrdiniCommerciante(id);
    }

    @PostMapping("/{id}/inviaChiamata")
    public void effettuaChiamata(@PathVariable long id,@RequestParam long idOrdine){
        this.commercianteService.effettuaChiamata(id,idOrdine);
    }

}
