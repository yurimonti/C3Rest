package it.unicam.cs.ids.papcteam.c3Rest.util;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


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
    public void addProdotto(ProdottoEntity prodotto, int numero){
        Predicate<ProdottoEntity> predicate = prodottoEntity -> prodottoEntity.getSerialCode()==prodotto.getSerialCode();
        if (this.prodotti.stream().anyMatch(predicate)){
            ProdottoEntity prodottoIn = this.prodotti.stream().filter(predicate).findFirst().orElseThrow(NullPointerException::new);
            prodottoIn.setNumero(prodottoIn.getNumero()+numero);
            this.prodotti.add(prodottoIn);
        } else{
            prodotto.setNumero(numero);
            this.prodotti.add(prodotto);
        }
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
    public ProdottoEntity getProdottoBySerialCode(long code) {
        return this.prodotti.stream().filter(prodottoEntity -> prodottoEntity.getSerialCode()==code)
                .findAny().orElseThrow(NullPointerException::new);
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
        //this.clear();
        return o;
    }

    /*private void clear() {
        setEmittente(null);
        setDestinazione(null);
        this.prodotti.clear();
    }*/

}
