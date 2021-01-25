package it.unicam.cs.ids.papcteam.c3Rest.controller;


import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.service.GestoreNegozi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/negozi")
public class NegozioRestController {
    @Autowired
    private GestoreNegozi gestoreNegozi;

    public NegozioRestController() {
    }

    @GetMapping
    public List<NegozioEntity> getNegozi(){
        return this.gestoreNegozi.getNegozi();
    }

    @GetMapping("/{id}")
    public NegozioEntity getNegozioById(@PathVariable long id){
        return this.gestoreNegozi.getNegozioById(id);
    }

    @GetMapping("/negozio/{id}")
    public List<ProdottoEntity> getProdottiNegozio(@PathVariable long id){
        return this.gestoreNegozi.getNegozioById(id).getProdotti();
    }

    @GetMapping("/negozio/{id}/{idProdotto}")
    public ProdottoEntity getProdottoById(@PathVariable long id, @PathVariable long idProdotto){
        return this.gestoreNegozi.getNegozioById(id).getProdotti().stream().filter(prodotto -> prodotto.getId()==idProdotto).findFirst().orElse(null);
    }

    @PostMapping("/negozio/{id}")
    public void addNewProdotto(@PathVariable long id,@RequestBody ProdottoEntity prodotto){
        NegozioEntity n = this.gestoreNegozi.getNegozioById(id);
        n.getProdotti().add(prodotto);
        this.gestoreNegozi.addOrUpdateNegozio(n);
    }
}
