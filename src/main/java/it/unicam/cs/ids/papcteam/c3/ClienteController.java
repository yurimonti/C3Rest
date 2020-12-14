package it.unicam.cs.ids.papcteam.c3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienteProva")
public class ClienteController {
    @Autowired
    private final NegozioRepository repository;
    private Ordine ordine;
    private NegozioRestController negozioRestController;
    private OrdineRepository ordineRepository;
    private OrdineRestController ordineRestController;
    public ClienteController(NegozioRepository negozioRepository,OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
        this.repository = negozioRepository;
        this.negozioRestController = new NegozioRestController(negozioRepository);
        this.ordine = new Ordine();
    }

    @GetMapping("/{id}")
    public List<Prodotto> scegliNegozio(@PathVariable long id){
        return this.negozioRestController.getProdottiNegozio(id);
    }

    private void aggiungiEmittente(Negozio emittente){
        this.ordine.setEmittente(emittente);
    }

    private void aggiungiProdotto(Prodotto prodotto){
        this.ordine.getProdotti().add(prodotto);
    }


    @PatchMapping("/{idNegozio}/{idProdotto}")
    public void addProdottoOrdine(@PathVariable long idNegozio,@PathVariable long idProdotto){
        Negozio n = repository.findById(idNegozio).orElseThrow();
        Prodotto p = negozioRestController.getProdottiNegozio(n.getId()).stream()
                .filter(prodotto -> prodotto.getId()==idProdotto)
                .findFirst().orElseThrow();
        aggiungiEmittente(n);
        aggiungiProdotto(p);
        ordineRepository.save(this.ordine);
    }

    /*@GetMapping("/{idNegozio}/{idProdotto}")
    public boolean ugualeProdotti(@PathVariable long idNegozio,@PathVariable long idProdotto){
        Prodotto pr = scegliNegozio(idNegozio).getProdotti().stream().filter(prodotto -> prodotto.getId()==idProdotto)
                .findFirst().orElseThrow();
        pr.setNumero(pr.getNumero()+2);
        return scegliNegozio(idNegozio).getProdotti().stream().filter(prodotto -> prodotto.getId()==idProdotto)
                .findFirst().orElseThrow().getId()==pr.getId();
    }*/

    /*public void scegliProdotto(long id){
        Negozio n = scegliNegozio(1);
        Prodotto p = n.getProdotti().stream().filter(prodotto ->prodotto.getId()==id).findFirst()
                .orElseThrow(NullPointerException::new);
        this.ordine.setEmittente(n);
        this.ordine.getProdotti().add(p);
    }*/

}
