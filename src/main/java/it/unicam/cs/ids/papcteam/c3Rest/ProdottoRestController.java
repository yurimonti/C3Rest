package it.unicam.cs.ids.papcteam.c3Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/prodotti")
public class ProdottoRestController {
    @Autowired
    private ProdottoRepository prodottoRepository;

    public ProdottoRestController() {
    }

    @GetMapping
    public List<Prodotto> getProdotti(){
        return this.prodottoRepository.findAll();
    }

    public ProdottoRepository getProdottoRepository() {
        return prodottoRepository;
    }

    @GetMapping("/{id}")
    public Prodotto getProdottoById(@PathVariable long id){
        return this.prodottoRepository.findById(id).orElseThrow();
    }

    /*@PatchMapping("/{id}")
    public void aggiornaNumeroProdotto(@PathVariable long id,@RequestParam int numero,@RequestParam boolean aggiungi){
        Prodotto p = prodottoRepository.findById(id).orElseThrow(NullPointerException::new);
        if(aggiungi)p.setNumero(p.getNumero()+numero);
        else p.setNumero(p.getNumero()-numero);
        prodottoRepository.save(p);
    }*/
}
