package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.repository.CorriereRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ChiamataEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.CorriereEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corrieri")
@CrossOrigin(origins = "*")
public class CorriereRestController {
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private OrdineRestController ordineRestController;
    @Autowired
    private ChiamataRestController chiamataRestController;

    public CorriereRestController() {
    }

    @GetMapping
    public List<CorriereEntity> getCorrieri(){
        return this.corriereRepository.findAll();
    }

    @GetMapping("/{id}")
    public CorriereEntity getCorriereById(@PathVariable long id){
        return this.corriereRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    @PostMapping
    public CorriereEntity insertCorriere(@RequestBody CorriereEntity corriere){
        corriere.initUsername();
        this.corriereRepository.save(corriere);
        return corriere;
    }
    @PostMapping("/{id}/aggiungiOrdine")
    public CorriereEntity addOrdine(@PathVariable long id, @RequestBody long idOrdine){
        CorriereEntity corriere = getCorriereById(id);
        corriere.getOrdini().add(ordineRestController.getOrdineById(idOrdine));
        this.corriereRepository.save(corriere);
        return corriere;
    }

    @DeleteMapping("/{id}/accettaChiamata")
    public void accettaChiamata(@PathVariable long id,@RequestParam long idChiamata){
        CorriereEntity corriere = getCorriereById(id);
        ChiamataEntity chiamata = this.chiamataRestController.getChiamataById(idChiamata);
        corriere.getOrdini().add(chiamata.getOrdine());
        this.chiamataRestController.deleteChiamataById(idChiamata);
        this.corriereRepository.save(corriere);
    }

}
