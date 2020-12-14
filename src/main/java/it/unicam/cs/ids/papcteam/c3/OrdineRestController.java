package it.unicam.cs.ids.papcteam.c3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ordini")
public class OrdineRestController {
    @Autowired
    private final OrdineRepository ordineRepository;
    private ProdottoRepository prodottoRepository;
    private ProdottoRestController prodottoRestController;
    private NegozioRestController negozioRestController;
    private CreatoreOrdine creatoreOrdine;
    private NegozioRepository negozioRepository;
    private LockerRepository lockerRepository;

    public OrdineRestController(OrdineRepository ordineRepository,ProdottoRepository prodottoRepository,
                                NegozioRepository negozioRepository,LockerRepository lockerRepository) {
        this.ordineRepository = ordineRepository;
        this.prodottoRepository = prodottoRepository;
        this.prodottoRestController = new ProdottoRestController(prodottoRepository);
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
        //this.ordine = new Ordine();
        this.negozioRepository = negozioRepository;
        this.negozioRestController = new NegozioRestController(negozioRepository);
        this.lockerRepository = lockerRepository;
    }

    public CreatoreOrdine getCreatoreOrdine() {
        return creatoreOrdine;
    }

    /*@PostMapping
      public void createOrdine(){
         this.ordineRepository.save(this.ordine);
    }*/
    @PostMapping
    public Ordine createOrdine(){
        Ordine ordine = getCreatoreOrdine().creaOrdine();
        this.ordineRepository.save(ordine);
        return ordine;
    }

    @PostMapping("/setEmittente")
    public Negozio setEmittenteOrdine(@RequestParam long idNegozio){
        Negozio n = negozioRestController.getNegozioById(idNegozio);
        getCreatoreOrdine().setEmittente(n);
        return n;
        /*Negozio negozio = negozioRepository.findById(idNegozio).orElseThrow();
        getCreatoreOrdine().setEmittente(negozio);
        return negozio.getProdotti();*/
    }

    @GetMapping("/setProdotto")
    public Prodotto setProdottoOrdine(@RequestParam long idProdotto){
        Prodotto p = this.negozioRestController.getProdottoById(getCreatoreOrdine().getEmittente().getId(),idProdotto);
        //negozioRestController.deleteProdottoNegozioById(this.creatoreOrdine.getEmittente().getId(),idProdotto);
        return p;
    }

    @GetMapping("/setDestinazione")
    public void setDestinazioneOrdine(@RequestParam long idDestinazione){
        Locker locker = this.lockerRepository.findById(idDestinazione).orElseThrow();
        getCreatoreOrdine().setDestinazione(locker);
    }

    @GetMapping("prezzoOrdine/{id}")
    public double getPrezzoOrdine(@PathVariable long id){
        return getOrdineById(id).getSoldi();
    }

    @GetMapping("/{id}")
    public Ordine getOrdineById(@PathVariable long id){
        return this.ordineRepository.findById(id).orElseThrow();
    }

    /*@PatchMapping("{idOrdine}")
    public void aggiungiProdotto(@PathVariable long idOrdine,@RequestParam long prodottoId){
        Ordine o = this.ordineRepository.findById(idOrdine).orElseThrow();
        o.getProdotti().add(this.prodottoRestController.getProdotti().stream().filter(prodotto -> prodotto.getId()==prodottoId).findFirst().orElseThrow());
        this.ordineRepository.save(o);
    }*/

    /*@GetMapping("/setEmittente")
    public List<Prodotto> setEmittenteOrdine(@RequestParam long idNegozio){
        Negozio negozio = negozioRestController.getNegozioById(idNegozio);
        this.ordine = new Ordine(negozio);
        return negozio.getProdotti();
    }*/

/*    @PutMapping("/setEmittente")
    public Ordine setEmittenteOrdine(@RequestParam long idNegozio){
        Negozio negozio = negozioRestController.getNegozioById(idNegozio);
        this.creatoreOrdine.setEmittente(negozio);
        return this.creatoreOrdine.creaOrdine();
    }*/

    /*@GetMapping("/setProdotto")
    public Prodotto setProdottoOrdine(@RequestParam long idProdotto){
        Prodotto p = this.negozioRestController.getProdottoById(this.ordine.getEmittente().getId(),idProdotto);
        this.ordine.getProdotti().add(p);
        //negozioRestController.deleteProdottoNegozioById(this.creatoreOrdine.getEmittente().getId(),idProdotto);
        return p;
    }*/

    /*@GetMapping("/setDestinazione")
    public void setDestinazioneOrdine(@RequestParam long idDestinazione){
        Locker locker = this.lockerRepository.findById(idDestinazione).orElseThrow();
        this.ordine.setDestinazione(locker);
    }*/

    /*@GetMapping("prezzoOrdine/{id}")
    public double getPrezzoOrdine(@PathVariable long id){
        return getOrdineById(id).getSoldi();
    }*/

}
