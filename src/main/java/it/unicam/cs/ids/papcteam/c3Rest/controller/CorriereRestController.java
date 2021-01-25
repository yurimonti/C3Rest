package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.StatoOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ChiamataEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.CorriereEntity;
import it.unicam.cs.ids.papcteam.c3Rest.service.CorriereService;
import it.unicam.cs.ids.papcteam.c3Rest.service.GestoreChiamate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corrieri")
@CrossOrigin(origins = "*")
public class CorriereRestController {
    @Autowired
    private CorriereService corriereService;
    @Autowired
    private GestoreChiamate gestoreChiamate;


    public CorriereRestController() {
    }

    @GetMapping
    public List<CorriereEntity> getCorrieri(){
        return this.corriereService.getCorrieri();
    }

    @GetMapping("/{id}")
    public CorriereEntity getCorriereById(@PathVariable long id){
        return this.corriereService.getCorriereById(id);
    }
    @PostMapping
    public CorriereEntity insertCorriere(@RequestBody CorriereEntity corriere){
        corriere.initUsername();
        this.corriereService.addCorriere(corriere);
        return corriere;
    }

    @GetMapping("/chiamate")
    public List<ChiamataEntity> getListaChiamate(){
        return this.gestoreChiamate.getChiamate();
    }

    @DeleteMapping("/{id}/accettaChiamata")
    public void accettaChiamata(@PathVariable long id,@RequestParam long idChiamata){
        CorriereEntity corriere = getCorriereById(id);
        ChiamataEntity chiamata = this.gestoreChiamate.getChiamataById(idChiamata);
        corriere.getOrdini().add(chiamata.getOrdine());
        this.gestoreChiamate.deleteChiamataById(idChiamata);
        this.corriereService.addCorriere(corriere);
    }

    @PostMapping("{id}/ordini/ritiraOrdine")
    public void ritiraOrdine(@PathVariable long id,@RequestParam long idOrdine){
        getCorriereById(id).getOrdini().stream().filter(o->o.getId()==idOrdine).findFirst()
                .orElseThrow(() -> new NullPointerException("ordine con questo id inesistente"))
                .setStatoOrdine(StatoOrdine.IN_TRASPORTO);
    }
    @PostMapping("{id}/ordini/consegnaOrdine")
    public void consegnaOrdine(@PathVariable long id,@RequestParam long idOrdine){
        getCorriereById(id).getOrdini().stream().filter(o->o.getId()==idOrdine).findFirst()
                .orElseThrow(() -> new NullPointerException("ordine con questo id inesistente"))
                .setStatoOrdine(StatoOrdine.CONSEGNATO);
    }

    @GetMapping("/{id}/ordini")
    public List<OrdineEntity> getOrdini(@PathVariable long id){
        return this.corriereService.getOrdini(id,o -> true);
    }
    @GetMapping("/{id}/ordini/daRitirare")
    public List<OrdineEntity> getOrdiniDaRitirare(@PathVariable long id){
        return this.corriereService.getOrdini(id,o->o.getStatoOrdine()== StatoOrdine.ESEGUITO);
    }
    @GetMapping("/{id}/ordini/inConsegna")
    public List<OrdineEntity> getOrdiniInConsegna(@PathVariable long id){
        return this.corriereService.getOrdini(id,o->o.getStatoOrdine()== StatoOrdine.IN_TRASPORTO);
    }
    @GetMapping("/{id}/ordini/consegnati")
    public List<OrdineEntity> getOrdiniConsegnati(@PathVariable long id){
        return this.corriereService.getOrdini(id,o->o.getStatoOrdine()== StatoOrdine.CONSEGNATO);
    }
    @GetMapping("/{id}/ordini/completati")
    public List<OrdineEntity> getOrdiniCompletati(@PathVariable long id){
        return this.corriereService.getOrdini(id,o->o.getStatoOrdine()== StatoOrdine.COMPLETATO);
    }

}
