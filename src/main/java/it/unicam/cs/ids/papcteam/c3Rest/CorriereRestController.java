package it.unicam.cs.ids.papcteam.c3Rest;

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
    public List<Corriere> getCorrieri(){
        return this.corriereRepository.findAll();
    }

    @GetMapping("/{id}")
    public Corriere getCorriereById(@PathVariable long id){
        return this.corriereRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    @PostMapping
    public Corriere insertCorriere(@RequestBody Corriere corriere){
        corriere.initUsername();
        this.corriereRepository.save(corriere);
        return corriere;
    }
    @PostMapping("/{id}/aggiungiOrdine")
    public Corriere addOrdine(@PathVariable long id,@RequestBody long idOrdine){
        Corriere corriere = getCorriereById(id);
        corriere.getOrdini().add(ordineRestController.getOrdineById(idOrdine));
        this.corriereRepository.save(corriere);
        return corriere;
    }

    @DeleteMapping("/{id}/accettaChiamata")
    public void accettaChiamata(@PathVariable long id,@RequestParam long idChiamata){
        Corriere corriere = getCorriereById(id);
        Chiamata chiamata = this.chiamataRestController.getChiamataById(idChiamata);
        corriere.getOrdini().add(chiamata.getOrdine());
        this.chiamataRestController.deleteChiamataById(idChiamata);
        this.corriereRepository.save(corriere);
    }

}
