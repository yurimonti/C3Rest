package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.*;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import it.unicam.cs.ids.papcteam.c3Rest.util.ConcreteCreatoreOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.util.CreatoreOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestoreOrdini {
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private GestoreNegozi gestoreNegozi;
    @Autowired
    private GestoreLockers gestoreLockers;
    private CreatoreOrdine creatoreOrdine;

    public GestoreOrdini() {
        this.creatoreOrdine = new ConcreteCreatoreOrdine();
    }

    public CreatoreOrdine getCreatoreOrdine() {
        return creatoreOrdine;
    }

    public OrdineEntity getOrdineById(long id){
        return this.ordineRepository.findById(id).orElseThrow(NullPointerException::new);
    }



    public List<OrdineEntity> getOrdini(){
        return this.ordineRepository.findAll();
    }

    public NegozioEntity setEmittenteOrdine(long idEmittente){
        this.creatoreOrdine.setEmittente(this.gestoreNegozi.getNegozioById(idEmittente));
        return this.creatoreOrdine.getEmittente();
    }
    public LockerEntity setDestinazione(long idDestinazione){
        this.creatoreOrdine.setDestinazione(this.gestoreLockers.getLockerById(idDestinazione));
        return this.creatoreOrdine.getDestinazione();
    }
    public ProdottoEntity setProdottoOrdine(long idProdotto,int numero){
        if(this.creatoreOrdine.getEmittente().getProdotti().stream().noneMatch(prodottoEntity -> prodottoEntity.getId()==idProdotto))
            throw new NullPointerException("nessun Prodotto con questo Id");
        ProdottoEntity prodotto = this.creatoreOrdine.getEmittente().getProdotti().stream().filter(p->p.getId()==idProdotto)
                .findFirst().orElseThrow();
        ProdottoEntity p = new ProdottoEntity(prodotto.getNome(),prodotto.getDescrizione(),prodotto.getPrezzo());
        p.setSerialCode(prodotto.getSerialCode());
        this.creatoreOrdine.addProdotto(p,numero);
        return this.creatoreOrdine.getProdotti().stream().filter(prodottoEntity ->
                prodottoEntity.getSerialCode()==prodotto.getSerialCode()).findFirst().orElseThrow(NullPointerException::new);
    }

    public OrdineEntity creaOrdine(){
        return this.creatoreOrdine.creaOrdine();
    }

    public void clearCreatore(){
        this.creatoreOrdine.setEmittente(null);
        this.creatoreOrdine.setDestinazione(null);
        this.creatoreOrdine.getProdotti().clear();
    }

    public void ritiraOrdine(long idOrdine) {
        OrdineEntity ordineEntity =getOrdineById(idOrdine);
        ordineEntity.setStatoOrdine(StatoOrdine.COMPLETATO);
        this.ordineRepository.save(ordineEntity);
    }
}
