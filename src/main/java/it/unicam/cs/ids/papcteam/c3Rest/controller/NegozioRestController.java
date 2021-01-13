package it.unicam.cs.ids.papcteam.c3Rest.controller;


import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/negozi")
public class NegozioRestController {
    @Autowired
    private NegozioRepository negozioRepository;

    public NegozioRestController() {
    }

    @GetMapping
    public List<NegozioEntity> getNegozi(){
        return negozioRepository.findAll();
    }

    @GetMapping("/{id}")
    public NegozioEntity getNegozioById(@PathVariable long id){
        return this.negozioRepository.findById(id).orElse(null);
    }

    @GetMapping("/negozio/{id}")
    public List<ProdottoEntity> getProdottiNegozio(@PathVariable long id){
        return negozioRepository.findById(id).orElseThrow(NullPointerException::new).getProdotti();
    }

    @GetMapping("/negozio/{id}/{idProdotto}")
    public ProdottoEntity getProdottoById(@PathVariable long id, @PathVariable long idProdotto){
        return negozioRepository.findById(id).orElseThrow(NullPointerException::new)
                .getProdotti().stream().filter(prodotto -> prodotto.getId()==idProdotto).findFirst().orElse(null);
    }

    @PostMapping("/negozio/{id}")
    public void addNewProdotto(@PathVariable long id,@RequestBody ProdottoEntity prodotto){
        NegozioEntity n = negozioRepository.findById(id).orElseThrow(NullPointerException::new);
        n.getProdotti().add(prodotto);
        negozioRepository.save(n);
    }

    @PostMapping
    public void createNegozio(@RequestBody NegozioEntity negozio){
        negozioRepository.save(negozio);
    }

    @DeleteMapping("/id")
    public void deleteNegozioById(@PathVariable long id){
        this.negozioRepository.deleteById(id);
    }

    @PatchMapping("/{idNegozio}/{idProdotto}")
    public void deleteProdottoNegozioById(@PathVariable long idNegozio,@PathVariable long idProdotto){
        NegozioEntity n =  getNegozioById(idNegozio);
        n.getProdotti().removeIf(prodotto -> prodotto.getId()==idProdotto);
        this.negozioRepository.save(n);
    }
}
