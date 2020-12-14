package it.unicam.cs.ids.papcteam.c3;

import org.aspectj.weaver.ast.Or;

import java.util.ArrayList;
import java.util.List;

public class ConcreteCreatoreOrdine implements CreatoreOrdine{
    private Negozio emittente;
    private Locker destinazione;
    private List<Prodotto> prodotti;

    public ConcreteCreatoreOrdine() {
        this.emittente = null;
        this.destinazione = null;
        this.prodotti = new ArrayList<>();
    }
    @Override
    public Negozio getEmittente() {
        return this.emittente;
    }
    @Override
    public void setEmittente(Negozio emittente) {
        this.emittente = emittente;
    }
    @Override
    public void addProdotto(Prodotto prodotto) {
        this.prodotti.add(prodotto);
    }
    @Override
    public Locker getDestinazione(){
        return this.destinazione;
    }
    @Override
    public void setDestinazione(Locker destinazione) {
        this.destinazione = destinazione;
    }

    @Override
    public List<Prodotto> getProdotti(){
        return this.prodotti;
    }

    @Override
    public Ordine creaOrdine() {
        Ordine o = new Ordine();
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
