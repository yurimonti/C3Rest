package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.repository.CommercianteRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ChiamataEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.CommercianteEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commercianti")
@CrossOrigin(origins = "*")
public class CommercianteRestController {
    @Autowired
    private CommercianteRepository commercianteRepository;
    @Autowired
    private NegozioRestController negozioRestController;
    @Autowired
    private ChiamataRestController chiamataRestController;

    public CommercianteRestController() {

    }

    @GetMapping("/{id}")
    public CommercianteEntity getCommercinateById(@PathVariable long id){
        return this.commercianteRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @PostMapping
    public void addCommerciante(@RequestBody CommercianteEntity commerciante){
        commerciante.initUsername();
        this.commercianteRepository.save(commerciante);
    }
    @PatchMapping("/{id}")
    public void setNegozioAssociato(@PathVariable long id,@RequestParam long idNegozio){
        CommercianteEntity commerciante = this.commercianteRepository.findById(id).orElseThrow(NullPointerException::new);
        commerciante.setNegozio(this.negozioRestController.getNegozioById(idNegozio));
        this.commercianteRepository.save(commerciante);
    }

    @GetMapping("/{id}/ordiniInNegozio")
    public List<OrdineEntity> getOrdiniCommerciante(@PathVariable long id){
        return getCommercinateById(id).getNegozio().getOrdini();
    }

    @PostMapping("/{id}/inviaChiamata")
    public void effettuaChiamata(@PathVariable long id,@RequestParam long idOrdine){
        CommercianteEntity commerciante = getCommercinateById(id);
        OrdineEntity ordine = getOrdiniCommerciante(id).stream().filter(o -> o.getId()==idOrdine).findFirst().orElseThrow(NullPointerException::new);
        ChiamataEntity chiamata = new ChiamataEntity();
        chiamata.setNegozio(commerciante.getNegozio());
        chiamata.setOrdine(ordine);
        chiamataRestController.addChiamata(chiamata);
    }

}
