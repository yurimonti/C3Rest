package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConcreteCreatoreOrdine implements CreatoreOrdine{
    private NegozioEntity emittente;
    private LockerEntity destinazione;
    private List<ProdottoEntity> prodotti;

    public ConcreteCreatoreOrdine() {
        this.emittente = null;
        this.destinazione = null;
        this.prodotti = new ArrayList<>();
    }
    @Override
    public NegozioEntity getEmittente() {
        return this.emittente;
    }
    @Override
    public void setEmittente(NegozioEntity emittente) {
        this.emittente = emittente;
    }
    @Override
    public void addProdotto(ProdottoEntity prodotto) {
        this.prodotti.add(prodotto);
    }
    @Override
    public LockerEntity getDestinazione(){
        return this.destinazione;
    }
    @Override
    public void setDestinazione(LockerEntity destinazione) {
        this.destinazione = destinazione;
    }

    @Override
    public List<ProdottoEntity> getProdotti(){
        return this.prodotti;
    }

    @Override
    public OrdineEntity creaOrdine() {
        OrdineEntity o = new OrdineEntity();
        if(this.destinazione!=null){
            o.setEmittente(this.emittente);
            o.setDestinazione(this.destinazione);
        }
        else {
            o.setEmittente(this.emittente);
        }
        if(!this.prodotti.isEmpty()) {
            o.setProdotti(this.prodotti);
            o.calcoloSoldi();
        }
        return o;
    }

}
