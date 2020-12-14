package it.unicam.cs.ids.papcteam.c3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/negozi")
public class NegozioRestController {
    @Autowired
    private final NegozioRepository negozioRepository;

    public NegozioRestController(NegozioRepository negozioRepository) {
        this.negozioRepository = negozioRepository;
    }

    @GetMapping
    public List<Negozio> getNegozi(){
        return negozioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Negozio getNegozioById(@PathVariable long id){
        return this.negozioRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @GetMapping("/negozio/{id}")
    public List<Prodotto> getProdottiNegozio(@PathVariable long id){
        return negozioRepository.findById(id).orElseThrow(NullPointerException::new).getProdotti();
    }

    @GetMapping("/negozio/{id}/{idProdotto}")
    public Prodotto getProdottoById(@PathVariable long id,@PathVariable long idProdotto){
        return negozioRepository.findById(id).orElseThrow(NullPointerException::new)
                .getProdotti().stream().filter(prodotto -> prodotto.getId()==idProdotto).findFirst().orElseThrow(NullPointerException::new);
    }

    @PostMapping("/negozio/{id}")
    public void addNewProdotto(@PathVariable long id,@RequestBody Prodotto prodotto){
        Negozio n = negozioRepository.findById(id).orElseThrow(NullPointerException::new);
        n.getProdotti().add(prodotto);
        negozioRepository.save(n);
    }

    @PostMapping
    public void createNegozio(@RequestBody Negozio negozio){
        negozioRepository.save(negozio);
    }

    @DeleteMapping("/id")
    public void deleteNegozioById(@PathVariable long id){
        this.negozioRepository.deleteById(id);
    }

    @PatchMapping("/{idNegozio}/{idProdotto}")
    public void deleteProdottoNegozioById(@PathVariable long idNegozio,@PathVariable long idProdotto){
       Negozio n =  getNegozioById(idNegozio);
       n.getProdotti().removeIf(prodotto -> prodotto.getId()==idProdotto);
       this.negozioRepository.save(n);
    }
}
