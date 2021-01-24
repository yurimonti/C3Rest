package it.unicam.cs.ids.papcteam.c3Rest.util;

import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteCreatoreOrdineTest {
    private NegozioEntity emittente;
    private CreatoreOrdine creatore;

    @BeforeEach
    void init(){
        this.emittente = new NegozioEntity("ferramenta","ferramenta","Via Roma 10","Chiuso Sabato e Domenica");
        this.emittente.getProdotti().add(new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10));
        this.creatore = new ConcreteCreatoreOrdine();
    }

    @Test
    void verificaProdttoNumeroEmittente() {
        this.creatore.setEmittente(this.emittente);
        ProdottoEntity prodottoOrdine = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
        prodottoOrdine.setSerialCode(creatore.getEmittente().getProdotti().get(0).getSerialCode());
        this.creatore.addProdotto(prodottoOrdine,2);
        OrdineEntity ordine = this.creatore.creaOrdine();
        assertTrue(ordine.getEmittente().getProdotti().get(0).getNumero()==8&&ordine.getProdotti().get(0).getNumero()==2);
    }

    @Test
    void addStessoProdotto(){
        this.creatore.setEmittente(this.emittente);
        ProdottoEntity prodottoOrdine = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
        prodottoOrdine.setSerialCode(creatore.getEmittente().getProdotti().get(0).getSerialCode());
        this.creatore.addProdotto(prodottoOrdine,2);
        this.creatore.addProdotto(prodottoOrdine,3);
        OrdineEntity ordine = this.creatore.creaOrdine();
        assertTrue(ordine.getEmittente().getProdotti().get(0).getNumero()==5&&ordine.getProdotti().get(0).getNumero()==5);
    }



    @Test
    void addProdottoNumeroEccessivo(){
        this.creatore.setEmittente(this.emittente);
        ProdottoEntity prodottoOrdine = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
        prodottoOrdine.setSerialCode(creatore.getEmittente().getProdotti().get(0).getSerialCode());
        this.creatore.addProdotto(prodottoOrdine,2);
        this.creatore.addProdotto(prodottoOrdine,3);
        assertThrows(IllegalArgumentException.class,() -> this.creatore.addProdotto(prodottoOrdine,6));
    }

    @Test
    void addProdottoNumeroEccessivo1(){
        this.creatore.setEmittente(this.emittente);
        ProdottoEntity prodottoOrdine = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
        prodottoOrdine.setSerialCode(creatore.getEmittente().getProdotti().get(0).getSerialCode());
        assertThrows(IllegalArgumentException.class,() -> this.creatore.addProdotto(prodottoOrdine,11));
    }
    @Test
    void createOrdineTest(){
        OrdineEntity expected = new OrdineEntity(this.emittente);
        ProdottoEntity prodottoOrdine = new ProdottoEntity("vite","vite 5mm al pezzo",0.02,10);
        prodottoOrdine.setSerialCode(emittente.getProdotti().get(0).getSerialCode());
        prodottoOrdine.setNumero(5);
        expected.getProdotti().add(prodottoOrdine);
        this.creatore.setEmittente(this.emittente);
        this.creatore.addProdotto(prodottoOrdine,5);
        OrdineEntity actual = creatore.creaOrdine();
        assertTrue(expected.getEmittente().equals(actual.getEmittente())&&expected.getProdotti().get(0).getNumero()
                ==(actual.getProdotti().get(0).getNumero()));
    }

}