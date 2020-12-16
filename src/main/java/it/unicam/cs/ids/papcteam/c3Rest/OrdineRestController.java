package it.unicam.cs.ids.papcteam.c3Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ordini")
public class OrdineRestController {
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private final ProdottoRestController prodottoRestController;
    @Autowired
    private final NegozioRestController negozioRestController;
    private CreatoreOrdine creatoreOrdine;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private LockerRepository lockerRepository;
    private long negozioId;
    private List<Long> prodottiId;
    private long destinazioneId;

    public OrdineRestController() {
        this.prodottoRestController = new ProdottoRestController();
        this.negozioRestController = new NegozioRestController();
    }


    /*@PostMapping
    public Ordine createOrdine(){
        Negozio n = negozioRestController.getNegozioById(this.creatoreOrdine.getEmittente().getId());
        this.creatoreOrdine.setEmittente(n);
        Ordine ordine = this.creatoreOrdine.creaOrdine();
        n.getOrdini().add(ordine);
        this.ordineRepository.save(ordine);
        this.negozioRepository.save(n);
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
        return ordine;
    }*/

    private void resetId(){
        this.negozioId = 0;
        this.prodottiId.clear();
        this.destinazioneId =0;
    }

    @PostMapping
    public Ordine createOrdine(){
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
        Negozio n = this.negozioRestController.getNegozioById(this.negozioId);
        this.creatoreOrdine.setEmittente(n);
        this.creatoreOrdine.setDestinazione(this.lockerRepository.findById(this.destinazioneId).orElse(null));
        this.prodottiId.forEach(aLong -> this.creatoreOrdine.addProdotto(this.prodottoRestController.getProdottoById(aLong)));
        Ordine ordine = this.creatoreOrdine.creaOrdine();
        n.getOrdini().add(ordine);
        this.ordineRepository.save(ordine);
        this.negozioRepository.save(n);
        resetId();
        return ordine;
    }

    @PostMapping("/setEmittente")
    public Negozio setEmittenteOrdine(@RequestParam long idNegozio){
        Negozio n = negozioRestController.getNegozioById(idNegozio);
        this.negozioId = idNegozio;
        return n;
    }

    @GetMapping("/setProdotto")
    public Prodotto setProdottoOrdine(@RequestParam long idProdotto){
        Prodotto p = this.negozioRestController.getProdottoById(this.negozioId,idProdotto);
        this.prodottiId = new ArrayList<>();
        if(p!=null)this.prodottiId.add(p.getId());
        return p;
    }

    @GetMapping("/setDestinazione")
    public Locker setDestinazioneOrdine(@RequestParam long idDestinazione){
        Locker locker = this.lockerRepository.findById(idDestinazione).orElse(null);
        this.destinazioneId = idDestinazione;
        return locker;
    }

    @GetMapping("prezzoOrdine/{id}")
    public double getPrezzoOrdine(@PathVariable long id){
        return getOrdineById(id).getSoldi();
    }

    @GetMapping("/{id}")
    public Ordine getOrdineById(@PathVariable long id){
        return this.ordineRepository.findById(id).orElseThrow(NullPointerException::new);
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
