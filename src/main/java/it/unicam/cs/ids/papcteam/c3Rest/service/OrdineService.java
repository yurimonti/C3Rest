package it.unicam.cs.ids.papcteam.c3Rest.service;

import it.unicam.cs.ids.papcteam.c3Rest.entity.OrdineEntity;
import it.unicam.cs.ids.papcteam.c3Rest.entity.StatoOrdine;
import it.unicam.cs.ids.papcteam.c3Rest.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepository ordineRepository;
    public OrdineService() {
    }

    public OrdineEntity getOrdineById(long id){
        return this.ordineRepository.findById(id).orElseThrow(NullPointerException::new);

    }

    public void ritiraOrdine(long idOrdine) {
        OrdineEntity ordineEntity =getOrdineById(idOrdine);
        ordineEntity.setStatoOrdine(StatoOrdine.COMPLETATO);
        this.ordineRepository.save(ordineEntity);
    }
}
