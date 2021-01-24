package it.unicam.cs.ids.papcteam.c3Rest.util;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CreatoreOrdine {
    void setEmittente(NegozioEntity emittente);
    void addProdotto(ProdottoEntity prodotto);

    void addProdotto(ProdottoEntity prodotto, int numero);

    LockerEntity getDestinazione();

    void setDestinazione(LockerEntity destinazione);

    List<ProdottoEntity> getProdotti();
    ProdottoEntity getProdottoBySerialCode(long code);
    OrdineEntity creaOrdine();
    NegozioEntity getEmittente();

    void gestisciProdottoEmittente();
}
