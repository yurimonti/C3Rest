package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.NegozioEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreatoreOrdine {
    void setEmittente(NegozioEntity emittente);
    void addProdotto(ProdottoEntity prodotto);

    LockerEntity getDestinazione();

    void setDestinazione(LockerEntity destinazione);

    List<ProdottoEntity> getProdotti();

    OrdineEntity creaOrdine();
    NegozioEntity getEmittente();

}
