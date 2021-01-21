package it.unicam.cs.ids.papcteam.c3Rest.controller;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import it.unicam.cs.ids.papcteam.c3Rest.repository.LockerRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.NegozioRepository;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import it.unicam.cs.ids.papcteam.c3Rest.util.ConcreteCreatoreOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.util.CreatoreOrdine;
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
    private List<ProdottoEntity> prodotti;
    //private List<Long> prodottiId;
    private long destinazioneId;

    public OrdineRestController() {
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
        this.prodottoRestController = new ProdottoRestController();
        this.negozioRestController = new NegozioRestController();
        this.prodotti = new ArrayList<>();
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

    private void resetParams(){
        this.negozioId = 0;
        this.prodotti.clear();
        this.destinazioneId =0;
    }

    @PostMapping
    public OrdineEntity createOrdine(){
        this.creatoreOrdine.setEmittente(this.negozioRestController.getNegozioById(this.negozioId));
        return this.creatoreOrdine.creaOrdine();
    }

    /*@PostMapping
    public OrdineEntity createOrdine(){
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
        NegozioEntity n = this.negozioRestController.getNegozioById(this.negozioId);
        this.creatoreOrdine.setEmittente(n);
        this.creatoreOrdine.setDestinazione(this.lockerRepository.findById(this.destinazioneId).orElse(null));
        this.prodotti.forEach(prodotto -> {
            this.creatoreOrdine.addProdotto(prodotto);
            this.prodottoRestController.getProdottoRepository().findAll().stream()
                    .filter(prodotto1 -> prodotto.getSerialCode()==prodotto1.getSerialCode())
                    .forEach(prodotto1 -> prodotto1.setNumero(prodotto1.getNumero()-prodotto.getNumero()));
        });
        OrdineEntity ordine = this.creatoreOrdine.creaOrdine();
        n.getOrdini().add(ordine);
        this.ordineRepository.save(ordine);
        this.negozioRepository.save(n);
        resetParams();
        return ordine;
    }*/

    @PostMapping("/setEmittente")
    public NegozioEntity setEmittenteOrdine(@RequestParam long idNegozio){
        NegozioEntity n = negozioRestController.getNegozioById(idNegozio);
        this.negozioId = idNegozio;
        return n;
    }

    /*@GetMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        if(!this.negozioRestController.getNegozioById(this.negozioId).getProdotti()
                .contains(prodottoRestController.getProdottoById(idProdotto))) return "prodotto con questo ID inesistente";
        ProdottoEntity p = this.negozioRestController.getProdottoById(this.negozioId,idProdotto);
        if (p.getNumero()<number) return "inserisci un numero minore o uguale di "+p.getNumero();
        ProdottoEntity p1;
        if(this.prodotti.stream().anyMatch(prodottoEntity -> prodottoEntity.getSerialCode()==p.getSerialCode())){
            p1 = this.prodotti.stream().filter(prodottoEntity -> prodottoEntity.getSerialCode()==p.getSerialCode()).findFirst().orElseThrow();
            p1.setNumero(p1.getNumero()+number);
        }else {
            p1 = new ProdottoEntity(p.getNome(), p.getDescrizione(), p.getPrezzo());
            p1.setSerialCode(p.getSerialCode());
            p1.setNumero(number);
        }
        this.prodotti.add(p1);
        return p1.toString();
    }*/
    @GetMapping("/setProdotto")
    public String setProdottoOrdine(@RequestParam long idProdotto, @RequestParam int number){
        if(this.negozioRestController.getNegozioById(this.negozioId).getProdotti().stream()
                .noneMatch(prodottoEntity -> prodottoEntity.getId()==idProdotto))return "prodotto con questo id non disponibile";
        ProdottoEntity prodottoNegozio = this.negozioRestController.getProdottoById(this.negozioId,idProdotto);
        ProdottoEntity prodottoOrdine = new ProdottoEntity(prodottoNegozio.getNome(),prodottoNegozio.getDescrizione(),
                prodottoNegozio.getPrezzo());
        prodottoOrdine.setSerialCode(prodottoNegozio.getSerialCode());
        this.creatoreOrdine.addProdotto(prodottoOrdine,number);
        return "prodotto inserito";
    }

    @GetMapping("/setDestinazione")
    public LockerEntity setDestinazioneOrdine(@RequestParam long idDestinazione){
        LockerEntity locker = this.lockerRepository.findById(idDestinazione).orElse(null);
        this.destinazioneId = idDestinazione;
        return locker;
    }

    @GetMapping("prezzoOrdine/{id}")
    public double getPrezzoOrdine(@PathVariable long id){
        return getOrdineById(id).getSoldi();
    }

    @GetMapping("/{id}")
    public OrdineEntity getOrdineById(@PathVariable long id){
        return this.ordineRepository.findById(id).orElseThrow(NullPointerException::new);
    }

}
