package it.unicam.cs.ids.papcteam.c3;

import java.util.List;

public interface CreatoreOrdine {
    void setEmittente(Negozio emittente);
    void addProdotto(Prodotto prodotto);

    Locker getDestinazione();

    void setDestinazione(Locker destinazione);

    List<Prodotto> getProdotti();

    Ordine creaOrdine();
    Negozio getEmittente();

}
